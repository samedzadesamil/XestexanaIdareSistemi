package Model;

import java.sql.*;
import java.util.ArrayList;

public class BasHekim extends Users {
	
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public BasHekim(int id, String seria, String parol, String name, String type) {
		super(id, seria, parol, name, type);

	}

	public BasHekim() {
	}

	// ---------------------------------------------------------------------------------------------------------------------------------

	public ArrayList<Users> getDoctorList() throws SQLException {
		Users obj;
		ArrayList<Users> list = new ArrayList<Users>();
		Connection con = connect.connectDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from users where type='doktor'");
			while (rs.next()) {
				obj = new Users(rs.getInt("id"), rs.getString("seria"), rs.getString("parol"), rs.getString("name"),
						rs.getString("type"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			st.close();
			rs.close();
			con.close();
		}
		return list;
	}

//---------------------------------------------------------------------------------------------------------------------------------
	public ArrayList<Users> getClinicDoctorList(int clinic_id) throws SQLException {
		Users obj;
		ArrayList<Users> list = new ArrayList<Users>();
		Connection con = connect.connectDb();
		try {
			st = con.createStatement();
        	rs = st.executeQuery(
			"select users.id, users.seria,users.parol,users.name, users.type from users inner join worker on users.id =worker.user_id where clinic_id="
					+ clinic_id);
			while (rs.next()) {
				obj = new Users(rs.getInt("id"), rs.getString("seria"), rs.getString("parol"), rs.getString("name"),
						rs.getString("type"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			st.close();
			rs.close();
			con.close();
		}
		return list;
	}
//---------------------------------------------------------------------------------------------------------------------------------

	public boolean addDoktor(String seria, String parol, String name) throws SQLException {
		boolean key = false;
		boolean dublicate=true;
		Connection con = connect.connectDb();
		String query = "insert into users (seria,parol,name,type) values (?,?,?,'doktor')";
		try {
			st = con.createStatement();
			rs = st.executeQuery("select* from users where seria='" + seria + "'");
			while (rs.next()) {
				dublicate=false;
				break;}
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				rs.close();
				st.close();
			}
			if (dublicate) {
				try {

					pst = con.prepareStatement(query);
					pst.setString(1, seria);
					pst.setString(2, parol);
					pst.setString(3, name);
					pst.executeUpdate();
					key = true;
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					pst.close();
					con.close();
				}
			}	
			
		return key;
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------

	public boolean deleteDoktor(int id) throws SQLException {
		boolean key = false;
		String query = "delete from users where id=?";
		Connection con = connect.connectDb();
		try {
			pst = con.prepareStatement(query);
			pst.setInt(1, id);
			pst.executeUpdate();
			key = true;
		} catch (SQLException e) {

			e.printStackTrace();
		}finally{
			pst.close();
			con.close();
		}
		return key;

	}

	// -----------------------------------------------------------------------------------------------------------------------------------

	public boolean updateDoktor(int id, String seria, String parol, String name) throws SQLException {
		boolean key = false;
		String query = "update users set name=?, seria=?, parol=? where id=?";
		Connection con = connect.connectDb();
		try {

			pst = con.prepareStatement(query);
			pst.setString(1, name);
			pst.setString(2, seria);
			pst.setString(3, parol);
			pst.setInt(4, id);
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

	// -----------------------------------------------------------------------------------------------------------------------------------
	public boolean addWorker(int user_id, int clinic_id) throws SQLException {
		boolean key = false;
		int count = 0;
		Connection con = connect.connectDb();
		String query = "insert into worker(user_id,clinic_id) values(?,?)";

		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from worker where user_id=" + user_id + "and clinic_id=" + clinic_id);
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally {
			st.close();
			rs.close();
		}

		if (count == 0) {
			try {
				pst = con.prepareStatement(query);
				pst.setInt(1, user_id);
				pst.setInt(2, clinic_id);
				pst.executeUpdate();
				key = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				pst.close();
				con.close();
			}
		}

		return key;
	}
}
