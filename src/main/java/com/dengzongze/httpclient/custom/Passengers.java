package com.dengzongze.httpclient.custom;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dengzongze on 2017/11/5.
 */
public class Passengers {
    private HashMap<String, Passenger> passengerMap;
    private String[] twoIsOpenTick;
    private String[] otherIsOpenClick;
    private boolean isExist;

    public Passengers(String json) {
        Map<String, Object> map = new Gson().fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
        Map data = (Map) map.get("data");
        this.isExist = Boolean.getBoolean(data.get("isExist").toString());
        this.twoIsOpenTick = (String[]) data.get("two_isOpenClick");
        this.otherIsOpenClick = (String[]) data.get("other_isOpenClick");
        List<String> normalPassengers = (List<String>) data.get("normal_passengers");
        for (String jsonData : normalPassengers) {
            Passenger passenger = new Passenger(jsonData);
            passengerMap.put(passenger.getPassengerName(), passenger);
        }
    }

    public HashMap<String, Passenger> getPassengerMap() {
        return passengerMap;
    }

    public String[] getTwoIsOpenTick() {
        return twoIsOpenTick;
    }

    public String[] getOtherIsOpenClick() {
        return otherIsOpenClick;
    }

    public boolean isExist() {
        return isExist;
    }
}
