package cn.deng.project.ticket.booking.custom;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by dengzongze on 2017/10/8.
 */
public class QueryResult {
    private HashMap<String, Train> trains;
    private Date date;
    private String setOutStation;
    private String arrivalStation;

    public HashMap<String, Train> getTrains() {
        return trains;
    }

    public void setTrains(HashMap<String, Train> trains) {
        this.trains = trains;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSetOutStation() {
        return setOutStation;
    }

    public void setSetOutStation(String setOutStation) {
        this.setOutStation = setOutStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }
}
