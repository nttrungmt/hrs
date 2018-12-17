package usm.hrs.model;

import java.util.ArrayList;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BookingBean extends CassandraData {
	//private static final Logger logger = Logger.getLogger(BookingBean.class.getName());
	String emailId; UUID g_id;
	int hotelId;
	int bookingId;
	String _id;	
	String startDate,endDate,bookingName;
	String bookingDate;
	ArrayList<Integer> roomId;	
	HotelBean hotel;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public UUID getGuestId() {
		return g_id;
	}
	public void setGuestId(UUID uuid) {
		this.g_id = uuid;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public String get_id() {
		return _id;
	}
	@JsonIgnore
	public void set_id(String _id) {
		this._id = _id;
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getBookingName() {
		return bookingName;
	}
	public void setBookingName(String bookingName) {
		this.bookingName = bookingName;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public ArrayList<Integer> getRoomId() {
		return roomId;
	}
	public void setRoomId(ArrayList<Integer> roomId) {
		this.roomId = roomId;
	}
	public HotelBean getHotel() {
		return hotel;
	}
	public void setHotel(HotelBean hotel) {
		this.hotel = hotel;
	}
}
