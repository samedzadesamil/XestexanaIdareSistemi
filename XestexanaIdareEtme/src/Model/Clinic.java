package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBconnection;

public class Clinic {
	private int id;
	private String name;

	DBconnection connect = new DBconnection();

	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public Clinic() {
	}

	public Clinic(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// ---------------------------------------------------------------------------------------------------------
	public ArrayList<Clinic> getClinicList() throws SQLException {
		Connection con = connect.connectDb();
		ArrayList<Clinic> list = new ArrayList<Clinic>();
		Clinic obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from clinic");
			while (rs.next()) {
				obj = new Clinic(rs.getInt("id"), rs.getString("name"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
			st.close();
			rs.close();
		}
		return list;
	}

	// -------------------------------------------------------------------------------------
	public boolean addClinic(String name) throws SQLException {
		boolean a = false;
		String query = "insert into clinic(name) values (?)";
		Connection con = connect.connectDb();
		try {
			pst = con.prepareStatement(query);
			pst.setString(1, name);
			pst.executeUpdate();
			a = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
			pst.close();
		}
		return a;
	}

	// ------------------------------------------------------------------------------------------------
	public boolean deleteClinic(int id) throws SQLException {
		boolean key = false;
		Connection con = connect.connectDb();
		String query = "delete from clinic where id=?";
		try {
			pst = con.prepareStatement(query);
			pst.setInt(1, id);
			pst.executeUpdate();
			key = true;
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			con.close();
			pst.close();
		}
		return key;
	}

	// ---------------------------------------------------------------------------------------
	public boolean updateClinic(int id, String name) throws SQLException {
		boolean key = false;
		String query = "update clinic set name=? where id=?";
		Connection con = connect.connectDb();
		try {

			pst = con.prepareStatement(query);
			pst.setString(1, name);
			pst.setInt(2, id);
			pst.executeUpdate();
			key = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
			pst.close();
		}
		return key;
	}
	//------------------------------------------------------------------------------------------------
	public Clinic getFech(int id) throws SQLException {
		Connection con= connect.connectDb();
		Clinic c =new Clinic();
		try {
			st=con.createStatement();
			rs=st.executeQuery("select * from clinic where id="+id);
			while(rs.next()) {
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}	finally {
			st.close();
			rs.close(); 
			con.close();
		}
		return c;
	}
}
