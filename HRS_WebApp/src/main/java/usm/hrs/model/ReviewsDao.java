package usm.hrs.model;
import java.util.ArrayList;

import org.bson.Document;

import usm.hrs.model.ReviewsBean;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ReviewsDao extends CassandraData {
	public void insertReview(ReviewsBean review) throws JsonProcessingException{
		//ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		//String json = ow.writeValueAsString(review);
		//collection.insertOne(Document.parse(json));
		//TODO: insert new review to hotel
	}
	
	public ArrayList<ReviewsBean> getReviewsDetails(int hotelId){
		/*ArrayList<Document> filters = new ArrayList<Document>();
		filters.add(new Document("$match",new Document("hotelId",hotelId)));
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<ReviewsBean> reviews = new ArrayList<>();
		AggregateIterable<Document> cursor = collection.aggregate(filters);
		cursor.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		        try {
		        	ReviewsBean review = new ReviewsBean();
		        	review = mapper.readValue(document.toJson(), ReviewsBean.class);
		        	reviews.add(review);
				} catch (Exception e) {
					e.printStackTrace();}
		    }
		});
		return reviews;*/
		String queryText = "SELECT * FROM reviews_by_hotel where h_id=? ALLOW FILTERING";
	    PreparedStatement preparedStatement = getSession().prepare(queryText);
	    BoundStatement boundStatement = preparedStatement.bind(String.valueOf(hotelId));
	    ResultSet rs = getSession().execute(boundStatement);
	    ArrayList<ReviewsBean> rsRts = new ArrayList<ReviewsBean>();
	    for(Row ftRow : rs) {
	    	ReviewsBean rtBean = new ReviewsBean();
	    	rtBean.setHotelId(Integer.parseInt(ftRow.getString("h_id")));
	    	rtBean.set_id(ftRow.getString("review_id"));
	    	rtBean.setReviewtitle(ftRow.getString("reviewTitle"));
	    	rtBean.setReviewText(ftRow.getString("reviewText"));
	    	rtBean.setReviewRating(ftRow.getDouble("reviewRating"));
	    	rtBean.setHotelName(ftRow.getString("name"));
	    	rtBean.setOccupation(ftRow.getString("occupation"));
	    	rtBean.setGender(ftRow.getString("gender"));
	    	rtBean.setUserName(ftRow.getString("userName"));
	    	rtBean.setUserEmail(ftRow.getString("userEmail"));
	    	rtBean.setAge(ftRow.getInt("age"));
	    	rsRts.add(rtBean);
	    }
	    return rsRts;
	}
}
