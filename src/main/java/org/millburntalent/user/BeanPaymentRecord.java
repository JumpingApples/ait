package org.millburntalent.user;

public class BeanPaymentRecord {

	private int id;
	private int fam_id;
	private String date_received;
	private String type;
	private int check_num;
	private int logged_by;
	private float amount;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFam_id() {
		return fam_id;
	}
	public void setFam_id(int fam_id) {
		this.fam_id = fam_id;
	}
	public String getDate_received() {
		return date_received;
	}
	public void setDate_received(String date_received) {
		this.date_received = date_received;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCheck_num() {
		return check_num;
	}
	public void setCheck_num(int check_num) {
		this.check_num = check_num;
	}
	public int getLogged_by() {
		return logged_by;
	}
	public void setLogged_by(int logged_by) {
		this.logged_by = logged_by;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}

}
