package com.dengzongze.httpclient.custom;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.xml.crypto.URIDereferencer;
import java.io.*;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dengzongze on 2017/10/7.
 */
public class Train {
    private String code;
    private String name;
    private String scretStr;
    private String startingStation;
    private String terminalStation;
    private String setOutStation;
    private String arrivalStation;
    private String setOutTime;
    private String arrivalTime;
    private String duration;
    private String businessSeat;
    private String firstClassSeat;
    private String secondClassSeat;
    private String luxurySoftSleeper;
    private String softSleeper;
    private String CRHSleeper;
    private String hardSleeper;
    private String softSeat;
    private String hardSeat;
    private String standingSeat;
    private String repeatToken;
    private String trainDate;
    public Train() {

    }

    public Train(String json) throws UnsupportedEncodingException, ParseException {
        String[] trainDetail = json.split("\\|", 36);
        this.scretStr = URLDecoder.decode(trainDetail[0], "utf8").trim();
        this.repeatToken = URLDecoder.decode(trainDetail[12], "utf8").trim();
        this.trainDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(trainDetail[13]));
        this.name = trainDetail[3];
        this.startingStation = trainDetail[4];
        this.terminalStation = trainDetail[5];
        this.setOutStation = trainDetail[6];
        this.arrivalStation = trainDetail[7];
        this.setOutTime = trainDetail[8];
        this.arrivalTime = trainDetail[9];
        this.duration = trainDetail[10];
        this.luxurySoftSleeper = trainDetail[21];
        this.softSleeper = trainDetail[23];
        this.softSeat = trainDetail[24];
        this.standingSeat = trainDetail[26];
        this.hardSleeper = trainDetail[28];
        this.hardSeat = trainDetail[29];
        this.secondClassSeat = trainDetail[30];
        this.firstClassSeat = trainDetail[31];
        this.businessSeat = trainDetail[32];
        this.CRHSleeper = trainDetail[33];
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getScretStr() {
        return scretStr;
    }

    public String getStartingStation() {
        return startingStation;
    }

    public String getTerminalStation() {
        return terminalStation;
    }

    public String getSetOutStation() {
        return setOutStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public String getSetOutTime() {
        return setOutTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDuration() {
        return duration;
    }

    public String getBusinessSeat() {
        return businessSeat;
    }

    public String getFirstClassSeat() {
        return firstClassSeat;
    }

    public String getSecondClassSeat() {
        return secondClassSeat;
    }

    public String getLuxurySoftSleeper() {
        return luxurySoftSleeper;
    }

    public String getSoftSleeper() {
        return softSleeper;
    }

    public String getCRHSleeper() {
        return CRHSleeper;
    }

    public String getHardSleeper() {
        return hardSleeper;
    }

    public String getSoftSeat() {
        return softSeat;
    }

    public String getHardSeat() {
        return hardSeat;
    }

    public String getStandingSeat() {
        return standingSeat;
    }

    public String getRepeatToken() {
        return repeatToken;
    }

    public String getTrainDate() {
        return trainDate;
    }

    public static void main(String[] args) throws IOException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse("20171111"));
        System.out.println(dateString);
    }

}
