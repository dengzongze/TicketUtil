package com.dengzongze.httpclient.custom;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

/**
 * Created by dengzongze on 2017/10/29.
 */
public class Passenger {
    private String code;
    private String passengerName;
    private String sexCode;
    private String sexName;
    private String bornDate;
    private String countryCode;
    private String passengerIdTypeCode;
    private String passengerIdTypeName;
    private String passengerIdNo;
    private String passengerType;
    private String passengerFlag;
    private String passengerTypeName;
    private String mobileNo;
    private String phoneNo;
    private String email;
    private String address;
    private String postalcode;
    private String firstLetter;
    private String recordCount;
    private String totalTimes;
    private String indexId;

    public Passenger(String json) {
        Map<String, String> map = new Gson().fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());
        this.code = map.get("code");
        this.passengerName = map.get("passenger_name");
        this.sexCode = map.get("sex_code");
        this.sexName = map.get("sex_name");
        this.bornDate = map.get("born_date");
        this.countryCode = map.get("country_code");
        this.passengerIdTypeCode = map.get("passenger_id_type_code");
        this.passengerIdTypeName = map.get("passenger_id_type_name");
        this.passengerIdNo = map.get("passenger_id_no");
        this.passengerType = map.get("passenger_type");
        this.passengerFlag = map.get("passenger_flag");
        this.passengerTypeName = map.get("passenger_type_name");
        this.mobileNo = map.get("mobile_no");
        this.phoneNo = map.get("phone_no");
        this.email = map.get("email");
        this.address = map.get("address");
        this.postalcode = map.get("postalcode");
        this.firstLetter = map.get("first_letter");
        this.recordCount = map.get("recordCount");
        this.totalTimes = map.get("total_times");
        this.indexId = map.get("index_id");
    }

    public String getCode() {
        return code;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getSexCode() {
        return sexCode;
    }

    public String getBornDate() {
        return bornDate;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getPassengerIdTypeCode() {
        return passengerIdTypeCode;
    }

    public String getPassengerIdTypeName() {
        return passengerIdTypeName;
    }

    public String getPassengerIdNo() {
        return passengerIdNo;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public String getPassengerFlag() {
        return passengerFlag;
    }

    public String getPassengerTypeName() {
        return passengerTypeName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public String getRecordCount() {
        return recordCount;
    }

    public String getTotalTimes() {
        return totalTimes;
    }

    public String getIndexId() {
        return indexId;
    }
}
