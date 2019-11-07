package com.amc.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amc.dao.AppointmentDao;
import com.amc.dao.CustomerDao;
import com.amc.dao.DoctorDao;
import com.amc.dao.RateDao;
import com.amc.dao.UserDao;
import com.amc.entity.Appointment;
import com.amc.entity.Invoice;
import com.amc.entity.Rate;
import com.amc.entity.User;

public class StaffServlet extends HttpServlet {

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

		case "customer_register":
			registerUser(request, response);
			break;

		case "update_customer":
			updateUser(request, response);
			break;

		case "add_appointment":
			try {
				addAppointment(request, response);
			} catch (ParseException e) {
				e.printStackTrace();
				dispatcher = request.getRequestDispatcher("/error.jsp");
				dispatcher.forward(request, response);
			}
			break;

		case "update_appointment":
			try {
				updateAppointment(request, response);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				dispatcher = request.getRequestDispatcher("/error.jsp");
				dispatcher.forward(request, response);
			}
			break;

		case "delete_appointment":
			deleteAppointment(request, response);
			break;

		case "rate_customer":
			addRate(request, response);
			break;
			
		case "set_invoice":
			setInvoice(request, response);
			break;

		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		switch (action) {

		case "view_customers":
			viewUsers(request, response);
			break;

		case "load_customer":
			loadUser(request, response);
			break;

		case "delete_customer":
			deleteUser(request, response);
			break;

		case "load_appointment_details":
			loadAppointmentDetails(request, response);
			break;

		case "view_appointments":
			viewAppointments(request, response);
			break;

		case "load_appointment":
			try {
				loadAppointment(request, response);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				dispatcher = request.getRequestDispatcher("/error.jsp");
				dispatcher.forward(request, response);
			}
			break;

		case "delete_appointment":
			deleteAppointment(request, response);
			break;

		case "view_rate":
			viewCustomerRates(request, response);
			break;

		case "go_to_invoice":
			goToInvoice(request, response);
			break;
			
		case "view_paid_appointments":
			viewPaidAppointments(request,response);
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
		user.setRole("Customer");

		UserDao ud = new UserDao();
		if (ud.addUser(user)) {
			request.setAttribute("NOTIFICATION", "Customer added Successfully!");
			dispatcher = request.getRequestDispatcher("/staff-dashboard.jsp");
			dispatcher.forward(request, response);
		}
	}

	public void viewUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> list = new ArrayList<>();

		CustomerDao cd = new CustomerDao();
		list = cd.getAllCustomers();

		request.setAttribute("list", list);
		dispatcher = request.getRequestDispatcher("/staff-customer-view.jsp");
		dispatcher.forward(request, response);

	}

	public void loadUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDao ud = new UserDao();
		// System.out.println(request.getParameter("id"));
		User user = ud.loadUser(request.getParameter("id"));

		request.setAttribute("editUser", user);
		dispatcher = request.getRequestDispatcher("/staff-customer-update.jsp");
		dispatcher.forward(request, response);
		// System.out.println(user.getName());

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
		user.setRole("Customer");

		UserDao ud = new UserDao();
		if (ud.updateUser(user)) {
			request.setAttribute("NOTIFICATION", "User update Successfully!");
			dispatcher = request.getRequestDispatcher("/staff-dashboard.jsp");
			dispatcher.forward(request, response);
		}

	}

	public void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDao ud = new UserDao();
		if (ud.deleteUser(request.getParameter("id"))) {

			request.setAttribute("NOTIFICATION", "User delete Successfully!");
			dispatcher = request.getRequestDispatcher("/staff-dashboard.jsp");
			dispatcher.forward(request, response);
		}

	}

	public void loadAppointmentDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DoctorDao dd = new DoctorDao();
		// System.out.println(request.getParameter("id"));
		List<String> docList = dd.getDoctorIdList();

		CustomerDao cd = new CustomerDao();
		List<String> cusList = cd.getCustomerIdList();

		request.setAttribute("docList", docList);
		request.setAttribute("cusList", cusList);
		dispatcher = request.getRequestDispatcher("/staff-appointment-add.jsp");
		dispatcher.forward(request, response);
		// System.out.println(user.getName());

	}

	public void addAppointment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {

		Appointment appointment = new Appointment();
		appointment.setAppointmentId(request.getParameter("appointmentId"));
		appointment.setCustomerId(request.getParameter("customerId"));
		appointment.setDoctorId(request.getParameter("doctorId"));
		appointment.setDate(new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("date")));
		appointment.setTime(request.getParameter("time"));

		AppointmentDao ad = new AppointmentDao();

		if (ad.addAppointment(appointment)) {
			// request.setAttribute("NOTIFICATION", "Appointment added Successfully!");
			request.setAttribute("appId", request.getParameter("appointmentId"));
			request.setAttribute("cusId", request.getParameter("customerId"));
			dispatcher = request.getRequestDispatcher("/staff-customer-rate-add.jsp");
			dispatcher.forward(request, response);
		}
	}

	public void viewAppointments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Appointment> list = new ArrayList<>();

		AppointmentDao ad = new AppointmentDao();
		list = ad.getAllAppointments();

		request.setAttribute("list", list);
		dispatcher = request.getRequestDispatcher("/staff-appointment-view.jsp");
		dispatcher.forward(request, response);

	}

	public void loadAppointment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		DoctorDao dd = new DoctorDao();
		// System.out.println(request.getParameter("id"));
		List<String> docList = dd.getDoctorIdList();

		CustomerDao cd = new CustomerDao();
		List<String> cusList = cd.getCustomerIdList();

		request.setAttribute("docList", docList);
		request.setAttribute("cusList", cusList);

		AppointmentDao ad = new AppointmentDao();
		// System.out.println(request.getParameter("id"));
		Appointment appointment = ad.loadAppointment(request.getParameter("id"));
		
		//String dateFormat = String.valueOf(appointment.getDate());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(appointment.getDate());
		//System.out.println(appointment.getDate());
		//System.out.println(strDate);
		
		String[] datePart = strDate.split("-");
		String formattedDateStr  = datePart[1]+"/"+datePart[2]+"/"+datePart[0];
		//Date formattedDate = new SimpleDateFormat("MM/dd/yyyy").parse(formattedDateStr);

		request.setAttribute("editAppointment", appointment);
		request.setAttribute("app_date",formattedDateStr );
		
		dispatcher = request.getRequestDispatcher("/staff-appointment-update.jsp");
		dispatcher.forward(request, response);
		// System.out.println(user.getName());

	}

	public void updateAppointment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {

		Appointment appointment = new Appointment();

		appointment.setAppointmentId(request.getParameter("id"));
		appointment.setCustomerId(request.getParameter("customerId"));
		appointment.setDoctorId(request.getParameter("doctorId"));
		appointment.setDate(new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("date")));
		appointment.setTime(request.getParameter("time"));
		
		String isPaid = request.getParameter("ispaid");
		if(isPaid.equalsIgnoreCase("true")) {
			appointment.setIsPaid(true);
		}else {
			appointment.setIsPaid(false);
		}

		System.out.println(
				appointment.getComment() + " " + appointment.getDurationLeft() + " " + appointment.getMedicine());

		AppointmentDao ad = new AppointmentDao();

		if (ad.updateAppointment(appointment)) {
			request.setAttribute("NOTIFICATION", "Appointment update Successfully!");
			dispatcher = request.getRequestDispatcher("/staff-dashboard.jsp");
			dispatcher.forward(request, response);
		}
	}

	public void deleteAppointment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AppointmentDao ad = new AppointmentDao();
		if (ad.deleteAppointment(request.getParameter("id"))) {

			request.setAttribute("NOTIFICATION", "Appointment delete Successfully!");
			dispatcher = request.getRequestDispatcher("/staff-dashboard.jsp");
			dispatcher.forward(request, response);
		}

	}

	public void addRate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Rate rate = new Rate();
		rate.setAppointmentId(request.getParameter("app_id"));
		rate.setCustomerId(request.getParameter("cus_id"));
		rate.setCustomerRate(Double.valueOf(request.getParameter("rate")));

		RateDao rd = new RateDao();

		if (rd.addRate(rate)) {

			dispatcher = request.getRequestDispatcher("/staff-dashboard.jsp");
			dispatcher.forward(request, response);
		}
	}

	public void viewCustomerRates(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Rate> list = new ArrayList<>();

		RateDao rd = new RateDao();
		list = rd.getRateList();

		request.setAttribute("list", list);
		dispatcher = request.getRequestDispatcher("/staff-customer-rate-view.jsp");
		dispatcher.forward(request, response);

	}

	public void goToInvoice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("id",request.getParameter("id"));
		dispatcher = request.getRequestDispatcher("/staff-invoice-add.jsp");
		dispatcher.forward(request, response);
	}

	public void setInvoice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("setInvoice occured");
		AppointmentDao ad = new AppointmentDao();
		// System.out.println(request.getParameter("id"));
		Appointment appointment = ad.loadAppointment(request.getParameter("id"));

		CustomerDao cd = new CustomerDao();

		User customer = cd.getCustomer(appointment.getCustomerId());

		Invoice invoice = new Invoice();
		invoice.setInvoiceId(request.getParameter("invoiceId"));
		invoice.setAppointmentId(request.getParameter("id"));
		invoice.setInvoiceDate();
		invoice.setCustomerAddress(customer.getAddress());
		invoice.setCustomerId(customer.getUserId());
		invoice.setCustomerEmail(customer.getEmail());
		invoice.setPaymentMethod(request.getParameter("paymentMethod"));
		invoice.setCash(Double.valueOf(request.getParameter("cash")));
		invoice.setCharge(appointment.getCharge());
		invoice.setBalance(invoice.getCash() - invoice.getCharge());

		request.setAttribute("invoice", invoice);
		dispatcher = request.getRequestDispatcher("/staff-invoice-view.jsp");
		dispatcher.forward(request, response);
		// System.out.println(user.getName());

	}
	
	public void viewPaidAppointments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Appointment> list = new ArrayList<>();

		AppointmentDao ad = new AppointmentDao();
		list = ad.getPaidAppointments();

		request.setAttribute("list", list);
		dispatcher = request.getRequestDispatcher("/staff-invoice-appointment-view.jsp");
		dispatcher.forward(request, response);

	}

}
