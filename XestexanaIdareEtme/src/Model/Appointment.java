package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBconnection;

public class Appointment {
	private int id;
	private int doktorId;
	private int xesteId;
	private String doktorName;
	private String xesteName;
	private String appDate;

	DBconnection connect = new DBconnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public Appointment() {
	}

	public Appointment(int id, int doktor_id, int xeste_id, String doktor_name, String xeste_name, String app_date) {
		this.id = id;
		this.doktorId = doktor_id;
		this.xesteId = xeste_id;
		this.doktorName = doktor_name;
		this.xesteName = xeste_name;
		this.appDate = app_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDoktorId() {
		return doktorId;
	}

	public void setDoktorId(int doktor_id) {
		this.doktorId = doktor_id;
	}

	public int getXesteId() {
		return xesteId;
	}

	public void setXesteId(int xeste_id) {
		this.xesteId = xeste_id;
	}

	public String getDoktorName() {
		return doktorName;
	}

	public void setDoktorName(String doktor_name) {
		this.doktorName = doktor_name;
	}

	public String getXesteName() {
		return xesteName;
	}

	public void setXesteName(String xeste_name) {
		this.xesteName = xeste_name;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String app_date) {
		this.appDate = app_date;
	}

	public ArrayList<Appointment> getAppointmentList(int xesteId) throws SQLException {
		Appointment obj;
		ArrayList<Appointment> list = new ArrayList<Appointment>();
		Connection con = connect.connectDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from appointment where xeste_id=" + xesteId);
			while (rs.next()) {
				obj = new Appointment(rs.getInt("id"), rs.getInt("doktor_id"), rs.getInt("xeste_id"),
						rs.getString("doktor_name"), rs.getString("xeste_name"), rs.getString("app_date"));
				list.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			st.close();
			rs.close();
			con.close();
		}
		return list;

	} 

}
