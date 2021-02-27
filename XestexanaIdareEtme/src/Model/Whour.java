package Model;



public class Whour {
	private int id;
	private int doktor_id;
	private String doktor_name;
	private String wdate;
	private String status;

	public Whour() {
	}

	public Whour(int id, int doktor_id, String doktor_name, String wdate, String status) {
		super();
		this.id = id;
		this.doktor_id = doktor_id;
		this.doktor_name = doktor_name;
		this.wdate = wdate;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDoktor_id() {
		return doktor_id;
	}

	public void setDoktor_id(int doktor_id) {
		this.doktor_id = doktor_id;
	}

	public String getDoktor_name() {
		return doktor_name;
	}

	public void setDoktor_name(String doktor_name) {
		this.doktor_name = doktor_name;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
