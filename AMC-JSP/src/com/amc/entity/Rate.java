package com.amc.entity;

public class Rate {
	
	private String appointmentId;
	private String customerId;
	private double customerRate;
	
	public String getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public double getCustomerRate() {
		return customerRate;
	}
	public void setCustomerRate(double customerRate) {
		this.customerRate = customerRate;
	}
}
