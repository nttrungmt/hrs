package usm.hrs.model;

import com.datastax.driver.core.*;
import usm.hrs.exceptions.UserExistsException;
import usm.hrs.exceptions.UserLoginException;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * DataStax Academy Sample Application
 *
 * Copyright 2013 DataStax
 *
 */


public class RoomBean extends CassandraData {
	String h_id,typeName;
	int roomId;
	String hotelName,city,zip,address;
	int floor; double price;
	HashMap<String,String> features;
	ArrayList<HashMap<String, Long>> dates;
	ArrayList<String> images;
	
	public String getHotelId() {
		return h_id;
	}
	public void setHotelId(String id) {
		this.h_id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public HashMap<String, String> getFeatures() {
		return features;
	}
	public void setFeatures(HashMap<String, String> features) {
		this.features = features;
	}
	public ArrayList<String> getImages() {
		return images;
	}
	public void setImages(ArrayList<String> images) {
		this.images = images;
	}
	public ArrayList<HashMap<String, Long>> getDates() {
		return dates;
	}
	public void setDates(ArrayList<HashMap<String, Long>> dates) {
		this.dates = dates;
	}
}
