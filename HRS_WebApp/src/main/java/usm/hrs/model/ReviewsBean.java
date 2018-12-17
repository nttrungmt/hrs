package usm.hrs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReviewsBean extends CassandraData {
	int hotelId;
	String _id;	
	String reviewTitle,reviewText,hotelName,occupation,gender,userName,userEmail;
	int age;
	double reviewRating;

	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public String get_id() {
		return _id;
	}
	@JsonIgnore
	public void set_id(String _id) {
		this._id = _id;
	}
	
	public String getReviewtitle() {
		return reviewTitle;
	}
	public void setReviewtitle(String reviewtitle) {
		this.reviewTitle = reviewtitle;
	}
	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	public double getReviewRating() {
		return reviewRating;
	}
	public void setReviewRating(double reviewRating) {
		this.reviewRating = reviewRating;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
