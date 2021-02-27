package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.Helper;

public class Xeste extends Users {
	
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public Xeste() {

	}

	public Xeste(int id, String seria, String parol, String name, String type) {
		super(id, seria, parol, name, type);
	}

	// ----------------------------------------------------------------------------------------------
	public boolean addXeste(String seria, String parol, String name) throws SQLException {
		boolean key = false;
		boolean dublicate = true;
		Connection con = connect.connectDb();
		String query = "insert into users(seria,parol,name,type)values(?,?,?,'xeste')";
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from users where seria='" + seria + "'");
			while (rs.next()) {
				dublicate = false;
				Helper.showMsg("Girdiyiniz Seria nomresine aid xeste movcuddur.");
				break;
			}
			if (dublicate) {

				pst = con.prepareStatement(query);
				pst.setString(1, seria);
				pst.setString(2, parol);
				pst.setString(3, name);
				pst.executeUpdate();
				key = true;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			rs.close();
			st.close();
			pst.close();
			con.close();
		}
		return key;
	}

	// -------------------------------------------------------------------------------------------------------
	public boolean addAppointment(int doktorId, String doktorName, int xesteId, String xesteName, String appDate) throws SQLException {
		boolean key = false;
		String query = "insert into appointment(doktor_id,doktor_name,xeste_id,xeste_name,app_date)values(?,?,?,?,?)";
		Connection con = connect.connectDb();
		try {
			pst = con.prepareStatement(query);
			pst.setInt(1, doktorId);
			pst.setString(2, doktorName);
			pst.setInt(3, xesteId);
			pst.setString(4, xesteName);
			pst.setString(5, appDate);
			pst.executeUpdate();
			key = true;

		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			pst.close();
			con.close();
		}
		return key;
	}
	public boolean updateStatus(int doktorId,String date, String status) throws SQLException {
		boolean key=false;
		String query="update whour set status='"+status+"' where doktor_id=? and wdate=?";
		Connection con = connect.connectDb();
		try {
			pst=con.prepareStatement(query);
			pst.setInt(1, doktorId);
			pst.setString(2, date);
			pst.executeUpdate();
			key=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pst.close();
			con.close();
		}

		return key;
	}
	public boolean deleteAppoint(int id) throws SQLException {
		boolean key=false;
		String query="delete from appointment where id=?";
		Connection con = connect.connectDb();
		try {
			pst=con.prepareStatement(query);
			pst.setInt(1, id);
			pst.executeUpdate();
			key=true;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			pst.close();
			con.close();
		}
		
		return key;
	}
	//-------------------------------------------------------------------------------------
	public ArrayList<Whour> getWhourListForXeste(int doktor_id) {
		ArrayList<Whour> list = new ArrayList<Whour>();
		Whour obj;
		Connection con = connect.connectDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from whour where status='active'  and doktor_id="+doktor_id);
			while (rs.next()) {
				obj = new Whour(rs.getInt("id"), rs.getInt("doktor_id"), rs.getString("doktor_name"),
						rs.getString("wdate"), rs.getString("status"));
				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

}
