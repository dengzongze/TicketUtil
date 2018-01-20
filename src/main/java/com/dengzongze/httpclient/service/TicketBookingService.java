package com.dengzongze.httpclient.service;

import org.springframework.stereotype.Service;

/**
 * Created by dengzongze on 2017/10/2.
 */
public interface TicketBookingService {
    public void sayHello();

    public boolean login(String username, String password);

    public String query(String conditions);

    public void order(String args);
}
