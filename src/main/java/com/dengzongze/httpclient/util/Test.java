package com.dengzongze.httpclient.util;

import com.dengzongze.httpclient.custom.Passengers;
import com.dengzongze.httpclient.custom.Trains;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.*;

/**
 * Created by dengzongze on 2017/10/24.
 */
public class Test {
    static CookieStore cookieStore = new BasicCookieStore();
    static CloseableHttpClient httpclient = null;

    static void setHttpclient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            // 默认信任所有证书
            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                return true;
            }
        }).build();
        // AllowAllHostnameVerifier: 这种方式不对主机名进行验证，验证功能被关闭，是个空操作(域名验证)
        SSLConnectionSocketFactory sslcsf = new SSLConnectionSocketFactory(sslContext,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslcsf)
                .setDefaultCookieStore(cookieStore)
                .build();
    }

    public static String doMethod(CloseableHttpClient httpClient, HttpUriRequest httpMethod) throws IOException {
        CloseableHttpResponse response = httpClient.execute(httpMethod);
        HttpEntity entity = response.getEntity();
        System.out.println("request url: " + httpMethod.getURI());
        System.out.println(response.getStatusLine());
        String res = null;
        if (entity != null) {
            res = IOUtils.toString(entity.getContent(), "utf8");
            System.out.println(res);
        }
        response.close();
        return res;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, ParseException {
        HttpPost httpPost = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        CloseableHttpResponse response = null;
        setHttpclient();
        doMethod(httpclient, new HttpGet("https://kyfw.12306.cn/otn/login/init"));
        String res = "";
        while (res.indexOf("4") == -1) {
            response = httpclient.execute(new HttpGet("https://kyfw.12306.cn/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand"));
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                File png = new File("C:\\Users\\dengzongze\\Desktop\\test.png");
                png.createNewFile();
                FileUtils.copyInputStreamToFile(entity.getContent(), png);
            }
            response.close();
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入验证码：");
            String captcha = sc.nextLine();
            String[] captcha_arr = captcha.split(",");
            captcha = "";
            for (String temp : captcha_arr) {
                if (temp != null)
                    captcha += (Integer.parseInt(temp) * 65 + 60) + ",";
            }
            captcha = captcha.substring(0, captcha.length() - 1);
            System.out.println("answer: " + captcha);
            httpPost = new HttpPost("https://kyfw.12306.cn/passport/captcha/captcha-check");
            params.add(new BasicNameValuePair("answer", captcha));
            params.add(new BasicNameValuePair("login_site", "E"));
            params.add(new BasicNameValuePair("rand", "sjrand"));
            httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
            res = doMethod(httpclient, httpPost);
        }
        httpPost = new HttpPost("https://kyfw.12306.cn/passport/web/login");
        params.clear();

        params.add(new BasicNameValuePair("appid", "otn"));
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        doMethod(httpclient, httpPost);

        httpPost = new HttpPost("https://kyfw.12306.cn/passport/web/auth/uamtk");
        params.clear();
        params.add(new BasicNameValuePair("appid", "otn"));
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        String token = doMethod(httpclient, httpPost);
        Map<String, Object> map = new Gson().fromJson(token, new TypeToken<Map<String, String>>() {
        }.getType());
        token = map.get("newapptk").toString();
        httpPost = new HttpPost("https://kyfw.12306.cn/otn/uamauthclient");
        params.clear();
        params.add(new BasicNameValuePair("tk", token));
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        doMethod(httpclient, httpPost);
        doMethod(httpclient, new HttpGet("https://kyfw.12306.cn/otn/leftTicket/log?leftTicketDTO.train_date=2017-11-11&leftTicketDTO.from_station=BJP&leftTicketDTO.to_station=SHH&purpose_codes=ADULT"));
        res = doMethod(httpclient, new HttpGet("https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=2017-11-11&leftTicketDTO.from_station=BJP&leftTicketDTO.to_station=SHH&purpose_codes=ADULT"));
//        map = new Gson().fromJson(res, new TypeToken<Map<String, Object>>() {
//        }.getType());
//        Map data = (Map<String, Object>) map.get("data");
        Trains trains = new Trains(res);
        httpPost = new HttpPost("https://kyfw.12306.cn/otn/login/checkUser");
        params.clear();
        params.add(new BasicNameValuePair("_json_att", ""));
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        doMethod(httpclient, httpPost);
//        map = new Gson().fromJson(token, new TypeToken<Map<String, Object>>() {
//        }.getType());
//        token = map.get("apptk");
        httpPost = new HttpPost("https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest");
        params.clear();
        params.add(new BasicNameValuePair("secretStr", trains.getTrainMap().get("101").getScretStr()));
        params.add(new BasicNameValuePair("train_date", "2017-11-15"));
//        params.add(new BasicNameValuePair("back_train_date", "2017-10-29"));
        params.add(new BasicNameValuePair("tour_flag", "dc"));
        params.add(new BasicNameValuePair("purpose_codes", "ADULT"));
        params.add(new BasicNameValuePair("query_from_station_name", trains.getStationMap().get(trains.getTrainMap().get("101").getSetOutStation()).toString()));
        params.add(new BasicNameValuePair("query_to_station_name", trains.getQuery_to_station_name()));
        params.add(new BasicNameValuePair("undefined", ""));
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        doMethod(httpclient, httpPost);
        httpPost = new HttpPost("https://kyfw.12306.cn/otn/confirmPassenger/initDc");
        params.clear();
        params.add(new BasicNameValuePair("_json_att", ""));
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        doMethod(httpclient, httpPost);

        httpPost = new HttpPost("https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs");
        params.clear();
        params.add(new BasicNameValuePair("_json_att", ""));
        params.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", trains.getTrainMap().get("G101").getRepeatToken()));
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        res = doMethod(httpclient, httpPost);
        Passengers passengers = new Passengers(res);

        httpPost = new HttpPost("https://kyfw.12306.cn/otn/confirmPassenger/checkOrderInfo");
        params.clear();
        params.add(new BasicNameValuePair("cancel_flag", "2"));
        params.add(new BasicNameValuePair("bed_level_order_num", "000000000000000000000000000000"));
        params.add(new BasicNameValuePair("passengerTicketStr", "O,0,1,邓宗泽,1,45041119920624105X,18920529727,N"));
        params.add(new BasicNameValuePair("oldPassengerStr", "邓宗泽,1,45041119920624105X,1_"));
        params.add(new BasicNameValuePair("tour_flag", "dc"));
        params.add(new BasicNameValuePair("randCode", ""));
        params.add(new BasicNameValuePair("_json_att", ""));
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        doMethod(httpclient, httpPost);
        httpPost = new HttpPost("https://kyfw.12306.cn/otn/confirmPassenger/getQueueCount");

//        httpPost = new HttpPost("https://kyfw.12306.cn/otn/passengers/init");
//        params.clear();
//        params.add(new BasicNameValuePair("_json_att", ""));
//        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
//        doMethod(httpclient, httpPost);
//        httpPost = new HttpPost("https://kyfw.12306.cn/otn/passengers/query");
//        params.clear();
//        params.add(new BasicNameValuePair("pageIndex", "1"));
//        params.add(new BasicNameValuePair("pageSize", "10"));
//        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
//        doMethod(httpclient, httpPost);
//        doMethod(httpclient, new HttpGet("https://kyfw.12306.cn/otn/login/loginOut"));
    }
//    public static void doPost(CloseableHttpClient httpClient, HttpPost httpPost) throws IOException {
//        CloseableHttpResponse response = httpClient.execute(httpPost);
//        HttpEntity entity = response.getEntity();
//        System.out.println("----------------------------------------");
//        System.out.println(response.getStatusLine());
//        if (entity != null) {
//            System.out.println(IOUtils.toString(entity.getContent(), "utf8"));
//        }
//        response.close();
//    }
}
