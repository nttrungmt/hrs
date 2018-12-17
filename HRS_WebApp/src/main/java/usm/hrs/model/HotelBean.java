package usm.hrs.model;

import com.datastax.driver.core.Row;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class HotelBean {
	String id,name,street,city,state,zip,phone,url;
	double rating,tax,balance,minPrice;
	ArrayList<String> images;
	String cancelPolicy,description;
	int hotelId;
	int totalRooms;
	
	ArrayList<String> roomTypes;
	HashMap<String, Integer> numberOfRooms;
	ArrayList<RoomBean> rooms;
	ArrayList<String> features;
	ArrayList<Integer> lstRoomIds;
	Boolean valid;

	public String get_id() {
		return id;
	}
	@JsonIgnore
	public void set_id(String _id) {
		this.id = _id;
	}
	public String getHotelName() {
		return name;
	}
	public void setHotelName(String hotelName) {
		this.name = hotelName;
	}
	public String getAddress() {
		return street;
	}
	public void setAddress(String address) {
		this.street = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double bl) {
		this.balance = bl;
	}
	public double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCancellationPolicy() {
		return cancelPolicy;
	}
	public void setCancellationPolicy(String cancellationPolicy) {
		this.cancelPolicy = cancellationPolicy;
	}
	public int getTotalRooms() {
		return totalRooms;
	}
	public void setTotalRooms(int totalRooms) {
		this.totalRooms = totalRooms;
	}
	
	public ArrayList<RoomBean> getRooms() {
		return rooms;
	}
	public void setRooms(ArrayList<RoomBean> rooms) {
		this.rooms = rooms;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public ArrayList<String> getImages() {
		return images;
	}
	public void setImages(ArrayList<String> images) {
		this.images = images;
	}
	public ArrayList<String> getRoomTypes() {
		return roomTypes;
	}
	public void setRoomTypes(ArrayList<String> roomTypes) {
		this.roomTypes = roomTypes;
	}
	public HashMap<String, Integer> getNumberOfRooms() {
		return numberOfRooms;
	}
	public void setNumberOfRooms(HashMap<String, Integer> numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}
	public ArrayList<String> getFeatures() {
		return features;
	}
	public void setFeatures(ArrayList<String> features) {
		this.features = features;
	}
	public ArrayList<Integer> getRoomIdsList() {
		return lstRoomIds;
	}
	public void setRoomIdsList(ArrayList<Integer> lst) {
		this.lstRoomIds = lst;
	}
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	
	public HotelBean() {}
	
	public HotelBean(Row hotelRow) {
		
	}
}
