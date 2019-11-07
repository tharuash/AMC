package com.amc.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amc.dao.AppointmentDao;
import com.amc.dao.CustomerDao;
import com.amc.dao.ManagerDao;
import com.amc.dao.UserDao;
import com.amc.entity.Appointment;
import com.amc.entity.Report;
import com.amc.entity.User;

public class ManagerServlet extends HttpServlet {

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

		case "register":
			registerUser(request, response);
			break;

		case "view":
			viewUsers(request, response);
			break;

		case "update":
			updateUser(request, response);
			break;
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		switch (action) {

		case "view":
			viewUsers(request, response);
			break;

		case "update":
			loadUser(request, response);
			break;

		case "delete":
			deleteUser(request, response);
			break;
			
		case "report_sells":
			viewSells(request, response);
			break;
		
		
		case "report_customers":
			viewCustomers(request, response);
			break;
		
	
		case "report_appointments":
			viewAppointments(request, response);
			break;
		}

	}

	public void registerUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = new User();
		user.setUserId(request.getParameter("userId"));
		user.setUserName(request.getParameter("userName"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setGender(request.getParameter("gender"));
		user.setPhone(Integer.parseInt(request.getParameter("phone")));
		user.setNic(Integer.parseInt(request.getParameter("nic")));
		user.setEmail(request.getParameter("email"));
		user.setAddress(request.getParameter("address"));
		user.setRole(request.getParameter("role"));

		UserDao ud = new UserDao();
		if (ud.addUser(user)) {
			request.setAttribute("NOTIFICATION", "User added Successfully!");
			dispatcher = request.getRequestDispatcher("/manager-dashboard.jsp");
			dispatcher.forward(request, response);
		}
	}

	public void viewUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> list = new ArrayList<>();

		UserDao ud = new UserDao();
		list = ud.getAllUsers();

		request.setAttribute("list", list);
		dispatcher = request.getRequestDispatcher("/manager-view.jsp");
		dispatcher.forward(request, response);

	}

	public void loadUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDao ud = new UserDao();
		System.out.println(request.getParameter("id"));
		User user = ud.loadUser(request.getParameter("id"));

		request.setAttribute("editUser", user);
		dispatcher = request.getRequestDispatcher("/manager-update.jsp");
		dispatcher.forward(request, response);
		//System.out.println(user.getName());

	}

	public void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = new User();
		user.setUserId(request.getParameter("id"));
		user.setUserName(request.getParameter("userName"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setGender(request.getParameter("gender"));
		user.setPhone(Integer.parseInt(request.getParameter("phone")));
		user.setNic(Integer.parseInt(request.getParameter("nic")));
		user.setEmail(request.getParameter("email"));
		user.setAddress(request.getParameter("address"));
		user.setRole(request.getParameter("role"));

		UserDao ud = new UserDao();
		if (ud.updateUser(user)) {
			request.setAttribute("NOTIFICATION", "User update Successfully!");
			dispatcher = request.getRequestDispatcher("/manager-dashboard.jsp");
			dispatcher.forward(request, response);
		}

	}

	public void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDao ud = new UserDao();
		if (ud.deleteUser(request.getParameter("id"))) {

			request.setAttribute("NOTIFICATION", "User delete Successfully!");
			dispatcher = request.getRequestDispatcher("/manager-dashboard.jsp");
			dispatcher.forward(request, response);
		}

	}
	
	public void viewSells(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Report> list = new ArrayList<>();
		
		System.out.println("view Sells occured");

		ManagerDao md = new ManagerDao();
		list = md.getReportList();

		request.setAttribute("list", list);
		dispatcher = request.getRequestDispatcher("/manager-report-sells.jsp");
		dispatcher.forward(request, response);

	}
	
	public void viewCustomers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> list = new ArrayList<>();

		CustomerDao cd = new CustomerDao();
		list = cd.getAllCustomers();
		
		request.setAttribute("list", list);
		dispatcher = request.getRequestDispatcher("/manager-report-customers.jsp");
		dispatcher.forward(request, response);

	}
	
	public void viewAppointments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Appointment> list = new ArrayList<>();

		AppointmentDao ad = new AppointmentDao();
		list = ad.getAllAppointments();
		
		request.setAttribute("list", list);
		dispatcher = request.getRequestDispatcher("/manager-report-appointments.jsp");
		dispatcher.forward(request, response);

	}

}
