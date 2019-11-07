package com.amc.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amc.dao.AppointmentDao;
import com.amc.entity.Appointment;

public class CustomerServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
	RequestDispatcher dispatcher = null;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		switch (action) {

		case "update_appointment":
			try {
				updateAppointment(request,response);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				dispatcher = request.getRequestDispatcher("/error.jsp");
				dispatcher.forward(request, response);
			}
			break;
		}
			
		

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		switch (action) {
		case "view_appointments":
			viewAppointments(request, response);
			break;
			
		case "load_appointment":
			loadAppointment(request, response);
			break;
		}
	}

	
	public void viewAppointments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Appointment> list = new ArrayList<>();
		
		HttpSession session = request.getSession();
		String currentUserId = String.valueOf(session.getAttribute("currentUserId"));

		AppointmentDao ad = new AppointmentDao();
		list = ad.loadAppointmentsById(currentUserId, "cus_id");
		
		request.setAttribute("list", list);
		dispatcher = request.getRequestDispatcher("/customer-appointment-view.jsp");
		dispatcher.forward(request, response);

	}
	
	public void loadAppointment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		
		AppointmentDao ad = new AppointmentDao();
		//System.out.println(request.getParameter("id"));
		Appointment appointment = ad.loadAppointment(request.getParameter("id"));

		request.setAttribute("editAppointment", appointment);
		dispatcher = request.getRequestDispatcher("/customer-appointment-update.jsp");
		dispatcher.forward(request, response);
		//System.out.println(user.getName());

	}
	
	public void updateAppointment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {

		AppointmentDao ad = new AppointmentDao();
		//System.out.println(request.getParameter("id"));
		Appointment appointment = ad.loadAppointment(request.getParameter("id"));
		
		appointment.setComment(request.getParameter("comment"));
		
		
		if (ad.updateAppointment(appointment)) {
			request.setAttribute("NOTIFICATION", "Appointment update Successfully!");
			dispatcher = request.getRequestDispatcher("/customer-dashboard.jsp");
			dispatcher.forward(request, response);
		}
	}

}
