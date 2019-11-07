package com.amc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amc.entity.Rate;
import com.amc.util.DbUtil;

public class RateDao {

	public boolean addRate(Rate rate) {
		boolean completed = false;
		Connection connection = null;

		PreparedStatement pstmt = null;

		try {
			connection = DbUtil.getConnection();
			String sql = "INSERT INTO ratings(app_id, cus_id, rate) VALUES (?,?,?)";

			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, rate.getAppointmentId());
			pstmt.setString(2, rate.getCustomerId());
			pstmt.setDouble(3, rate.getCustomerRate());

			int affectedRows = pstmt.executeUpdate();

			System.out.println(affectedRows + " row(s) affected !!");
			System.out.println("rate add by staff successfully...");

			completed = true;
		} catch (Exception e) {
			System.out.println("Error: " + e);
		} finally {
			try {

				pstmt.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return completed;

	}

	public List<Rate> getRateList() {
		List<Rate> list = new ArrayList<>();
		Connection connection = null;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = DbUtil.getConnection();
			stmt = connection.createStatement();
			String sql = "SELECT cus_id,  ROUND(AVG(rate)::numeric,2)   FROM ratings GROUP BY cus_id ORDER BY cus_id ASC";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Rate rate = new Rate();
				rate.setCustomerId(rs.getString(1));
				rate.setCustomerRate(rs.getDouble(2));
				

				list.add(rate);
			}

		} catch (Exception e) {
			System.out.println("Error: Manager " + e);
		} finally {
			try {
				rs.close();
				stmt.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return list;

	}

}
