package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Doktor extends Users {
	
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public Doktor() {
		super();
	}

	public Doktor(int id, String seria, String parol, String name, String type) {
		super(id, seria, parol, name, type);

	}

	public boolean addWhour(int doktor_id, String doktor_name, String wdate) throws SQLException {
		boolean key = false;
		int count = 0;
		Connection con = connect.connectDb();
		String query = "insert into whour(doktor_id,doktor_name,wdate)values(?,?,?)";
		try {
			st = con.createStatement();
			rs = st.executeQuery("select* from whour where status='active' and doktor_id=" + doktor_id + "and wdate='"
					+ wdate + "'");
			while (rs.next()) {
				count++;
				break;
			}
			if (count == 0) {

				pst = con.prepareStatement(query);
				pst.setInt(1, doktor_id);
				pst.setString(2, doktor_name);
				pst.setString(3, wdate);
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
	//-----------------------------------------------------------------------------------------------------------
	public ArrayList<Whour> getWhourList(int doktor_id) {
		ArrayList<Whour> list = new ArrayList<Whour>();
		Whour obj;
		Connection con = connect.connectDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from whour where  doktor_id="+doktor_id);
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
//-----------------------------------------------------------------------------------------------
	public boolean deleteWhour(int id) throws SQLException {
		boolean key = false;
		String query = "delete from whour where id=?";
		Connection con = connect.connectDb();
		try {
			pst = con.prepareStatement(query);
			pst.setInt(1, id);
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
	//---------------------------------------------------------------------------------------------------
	public boolean deleteAppointFromDoktor(int docId,String date) throws SQLException {
		boolean key=false;
		
		String query="delete from appointment where doktor_id=? and app_date=?";
		Connection con = connect.connectDb();
		try {
			pst = con.prepareStatement(query);
			pst.setInt(1, id);
			pst.setString(2, date);
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

}
