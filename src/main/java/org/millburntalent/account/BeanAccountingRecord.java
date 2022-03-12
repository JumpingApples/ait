package org.millburntalent.account;

public class BeanAccountingRecord {

	private String fam_id;
	private String f_name;
	private String m_name;
	private String email;
	
	private float tuition;
	private float fees;
	private float other_charges;
	private float discounts;
	
	private float payments;
	private float refunds;
	private float credit;
	
	private float balance;
	
	public String getFam_id() {
		return fam_id;
	}
	public void setFam_id(String fam_id) {
		this.fam_id = fam_id;
	}
	public String getF_name() {
		return f_name;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public float getTuition() {
		return tuition;
	}
	public void setTuition(float tuition) {
		this.tuition = tuition;
	}
	public float getFees() {
		return fees;
	}
	public void setFees(float fees) {
		this.fees = fees;
	}
	public float getOther_charges() {
		return other_charges;
	}
	public void setOther_charges(float other_charges) {
		this.other_charges = other_charges;
	}
	public float getDiscounts() {
		return discounts;
	}
	public void setDiscounts(float discounts) {
		this.discounts = discounts;
	}
	public float getPayments() {
		return payments;
	}
	public void setPayments(float payments) {
		this.payments = payments;
	}
	public float getRefunds() {
		return refunds;
	}
	public void setRefunds(float refunds) {
		this.refunds = refunds;
	}
	public float getCredit() {
		return credit;
	}
	public void setCredit(float credit) {
		this.credit = credit;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}

}
