package Model;

import Helper.DBconnection;

public class Users {
	int id;
	String seria, parol, name, type;
	DBconnection connect = new DBconnection();

	// --------------------------------------------------------------------------------------------------
	public Users(int id, String seria, String parol, String name, String type) {
		super();
		this.id = id;
		this.seria = seria;
		this.parol = parol;
		this.name = name;
		this.type = type;
	}

	public Users() {
	}

	// -----------------------------------------------------------------------------------------------------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSeria() {
		return seria;
	}

	public void setSeria(String seria) {
		this.seria = seria;
	}

	public String getParol() {
		return parol;
	}

	public void setParol(String parol) {
		this.parol = parol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
