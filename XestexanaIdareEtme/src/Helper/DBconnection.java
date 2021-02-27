package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

	String url = "jdbc:postgresql://localhost:5432/hosbital";
	Connection connect = null;

	public DBconnection() {
	}

	public Connection connectDb() {
		try {
			this.connect = DriverManager.getConnection(url, "postgres", "12345");
			return connect;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect;

	}

}
