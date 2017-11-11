package cn.deng.project.ticket.booking.service;

import org.springframework.stereotype.Service;

/**
 * Created by dengzongze on 2017/10/2.
 */
@Service
public class TicketBookingServiceImpl implements TicketBookingService {

    public void sayHello() {
        System.out.println("hello");
    }

    public boolean login(String username, String password) {
        return false;
    }

    public String query(String conditions) {
        return null;
    }

    public void order(String args) {

    }
}
