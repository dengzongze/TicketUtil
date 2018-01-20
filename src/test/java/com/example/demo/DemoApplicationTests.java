package com.example.demo;

import com.dengzongze.httpclient.service.TicketBookingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class DemoApplicationTests {

	@Autowired
	TicketBookingService ticketBookingService;

	@Test
	public void contextLoads() {
		ticketBookingService.sayHello();
	}

}
