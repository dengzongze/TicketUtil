package com.dengzongze.httpclient.custom;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dengzongze on 2017/10/29.
 */
public class Trains {
    private HashMap<String, Train> trainMap;
    private Map<String, String> stationMap;
    private String query_from_station_name;
    private String query_to_station_name;

    public HashMap<String, Train> getTrainMap() {
        return trainMap;
    }

    public Trains(String json) throws UnsupportedEncodingException, ParseException {
        this.trainMap = new HashMap<String, Train>();
        Map<String, Object> map = new Gson().fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
        Map data = (Map) map.get("data");
        String[] trainData = data.get("result").toString().substring(1, data.get("result").toString().length()).split(",");
        for (String str: trainData) {
            Train train = new Train(str);
            trainMap.put(train.getName(), train);
        }
        this.stationMap = (Map) map.get("map");
    }

    public Map getStationMap() {
        return stationMap;
    }

    public String getQuery_from_station_name() {
        return query_from_station_name;
    }

    public String getQuery_to_station_name() {
        return query_to_station_name;
    }
}
