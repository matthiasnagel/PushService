package io.matthiasnagel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javapns.Push;
import javapns.notification.PushedNotification;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PushServiceController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 286879681662994746L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		try {
			InputStream inputStream = PushServiceController.class.getResourceAsStream("PushService.p12");
			
			String[] devices = {"yourDeviceToken"};
			List<PushedNotification> notifications = Push.combined(request.getParameter("alert-text"), Integer.parseInt(request.getParameter("badge-number")), "default", inputStream, "123456qwertz", false, devices);
			
			for (PushedNotification notification : notifications) {
			    if (notification.isSuccessful()) {
					System.out.println("Success");
			    } else {
			        String invalidToken = notification.getDevice().getToken();
					System.out.println("Error: Ivalid Token: " + invalidToken);
			    }
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
