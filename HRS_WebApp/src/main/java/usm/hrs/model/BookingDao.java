package usm.hrs.model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;

import usm.hrs.model.BookingBean;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class BookingDao extends CassandraData {
	public void insertBooking(BookingBean booking) throws JsonProcessingException{
		//ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		//String json = ow.writeValueAsString(booking);
		//collection.insertOne(Document.parse(json));
		//TODO: insert new booking to database
	}
	
	public ArrayList<Integer> getBookingDetailsRoomList(String email, String h_id) {
		String queryText = "SELECT * FROM reservation_item where g_email=? and h_id=? ALLOW FILTERING";
	    PreparedStatement preparedStatement = getSession().prepare(queryText);
	    BoundStatement boundStatement = preparedStatement.bind(email, h_id);
	    ResultSet rs = getSession().execute(boundStatement);
	    ArrayList<Integer> rsRts = new ArrayList<Integer>();
	    for(Row ftRow : rs) {
	    	rsRts.add(ftRow.getInt("r_id"));
	    }
	    return rsRts;
	}	
	
	public ArrayList<BookingBean> getBookingDetails(String email){
		/*ArrayList<Document> filters = new ArrayList<Document>();
		filters.add(new Document("$match",new Document("emailId",email)));
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<BookingBean> bookings = new ArrayList<>();
		AggregateIterable<Document> cursor = collection.aggregate(filters);
		cursor.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		        try {
		        	BookingBean booking = new BookingBean();
		        	booking = mapper.readValue(document.toJson(), BookingBean.class);
		        	bookings.add(booking);
				} catch (Exception e) {
					e.printStackTrace();}
		    }
		});
		return bookings;*/
		String queryText = "SELECT * FROM reservation where g_email=? ALLOW FILTERING";
	    PreparedStatement preparedStatement = getSession().prepare(queryText);
	    BoundStatement boundStatement = preparedStatement.bind(email);
	    ResultSet rs = getSession().execute(boundStatement);
	    ArrayList<BookingBean> rsRts = new ArrayList<BookingBean>();
	    for(Row ftRow : rs) {
	    	BookingBean rtBean = new BookingBean();
	    	rtBean.setEmailId(ftRow.getString("g_email"));
	    	rtBean.setGuestId(ftRow.getUUID("g_id"));
	    	rtBean.setHotelId(Integer.parseInt(ftRow.getString("h_id")));
	    	rtBean.setBookingId(ftRow.getInt(""));
	    	rtBean.setStartDate(ftRow.getString("startDate"));
	    	rtBean.setEndDate(ftRow.getString("endDate"));
	    	rtBean.setBookingDate(ftRow.getString("bookingDate"));
	    	rtBean.setBookingName(ftRow.getString("cf_no"));
	    	
	    	rtBean.setRoomId(getBookingDetailsRoomList(
	    			rtBean.getEmailId(),
	    			String.valueOf(rtBean.getHotelId())));
	    	rtBean.setHotel(new HotelsDao().getHotelObj(rtBean.getHotelId()));
	    	
	    	rsRts.add(rtBean);
	    }
	    return rsRts;
	}
	
	public BookingBean getBooking(int bookingId) 
			throws JsonParseException, JsonMappingException, IOException {
		//Document myDoc = collection.find(eq("bookingId", bookingId)).first();
		//ObjectMapper mapper = new ObjectMapper();
		//return mapper.readValue(myDoc.toJson(), BookingBean.class);
		//TODO:
		return new BookingBean();
	}
	
	public ArrayList<BookingBean> getBookingDetails(){
		/*ArrayList<Document> filters = new ArrayList<Document>();
		filters.add(new Document("$match",new Document("bookingId",new Document("$gt",0))));
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<BookingBean> bookings = new ArrayList<>();
		AggregateIterable<Document> cursor = collection.aggregate(filters);
		cursor.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		        try {
		        	BookingBean booking = new BookingBean();
		        	booking = mapper.readValue(document.toJson(), BookingBean.class);
		        	bookings.add(booking);
				} catch (Exception e) {
					e.printStackTrace();}
		    }
		});
		return bookings;*/
		return new ArrayList<BookingBean>();
	}
	
	public void deleteBooking(int bookinId)	{
		//collection.deleteOne(new Document("bookingId", bookinId));
		//TODO: delete a booking from database
	}
}
