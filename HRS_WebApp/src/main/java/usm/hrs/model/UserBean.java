package usm.hrs.model;

import com.datastax.driver.core.*;
import usm.hrs.exceptions.UserExistsException;
import usm.hrs.exceptions.UserLoginException;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * DataStax Academy Sample Application
 *
 * Copyright 2013 DataStax
 *
 */


public class UserBean {
	String _id;
	String fullName,phoneNumber,type,emailId,password,occupation,gender;
	int age;
	int credit_lm; double discount, balance, ytd_pm;  
	String credit_st;

	public String get_id() {
		return _id;
	}
	@JsonIgnore
	public void set_id(String _id) {
		this._id = _id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public int getCreditLm() {
		return credit_lm;
	}

	public void setCreditLm(int lm) {
		this.credit_lm = lm;
	}	
	
	public String getCreditSt() {
		return credit_st;
	}

	public void setCreditSt(String st) {
		this.credit_st = st;
	}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double bl) {
		this.balance = bl;
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public UserBean() { }
	
	public UserBean(Row userRow) {
		_id = userRow.getUUID("id").toString();
		fullName = userRow.getString("full_name");
		phoneNumber = userRow.getString("phone");
		type = userRow.getString("type");
		emailId = userRow.getString("email");
		password  = userRow.getString("password");
		occupation =  userRow.getString("occupation");
		gender = userRow.getString("gender");
		age = userRow.getInt("age");
		credit_st = userRow.getString("credit_st");
		credit_lm = userRow.getInt("credit_lm");
		discount = userRow.getDouble("discount");
		balance = userRow.getDouble("balance");
		ytd_pm = userRow.getDouble("ytd_pm");
	}
}
