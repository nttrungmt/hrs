package usm.hrs.model;
import java.util.ArrayList;
import java.util.HashMap;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.core.JsonProcessingException;

public class HotelsDao extends CassandraData {
	//MongoClient mongoClient = new MongoClient();
	//MongoDatabase database = mongoClient.getDatabase("Hotelpedia");
	//MongoCollection<Document> collection = database.getCollection("Hotels");
	
	public void insertOrder(HotelBean hotel) throws JsonProcessingException{
		//ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		//String json = ow.writeValueAsString(hotel);
		//collection.insertOne(Document.parse(json));
		//TODO:
	}
	
	public int getCount(){
		//return (int) collection.count();
		String queryText = "SELECT count(*) FROM hotel ALLOW FILTERING";
	    PreparedStatement preparedStatement = getSession().prepare(queryText);
	    BoundStatement boundStatement = preparedStatement.bind();
	    Row userRow = getSession().execute(boundStatement).one();
	    if (userRow == null) {
	      return -1;
	    }
	    return userRow.getInt(0);
	}
	
	public ArrayList<HotelBean> getHotelDetails(){
		/*ArrayList<Document> filters = new ArrayList<Document>();
		filters.add(new Document("$match",new Document("hotelId",new Document("$gt",0))));
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<HotelBean> hotels = new ArrayList<>();
		AggregateIterable<Document> cursor = collection.aggregate(filters);
		cursor.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		        try {
		        	HotelBean hotel = new HotelBean();
		        	hotel = mapper.readValue(document.toJson(), HotelBean.class);
		        	hotels.add(hotel);
				} catch (Exception e) {
					e.printStackTrace();}
		    }
		});
		return hotels;*/
		return new ArrayList<HotelBean>();
	}
	
	public void deleteFromArray(long hotelId,int roomId){
		//collection.updateOne(new Document("hotelId",hotelId),
		//		new Document("$pull",new Document("rooms",new Document("roomId",roomId))));
		//TODO:
	}
	
	public ArrayList<String> getHotelFeatures(String id) {
		String queryText = "SELECT * FROM features_by_hotel where h_id=? ALLOW FILTERING";
	    PreparedStatement preparedStatement = getSession().prepare(queryText);
	    BoundStatement boundStatement = preparedStatement.bind(id);
	    ResultSet rs = getSession().execute(boundStatement);
	    ArrayList<String> rsFeatures = new ArrayList<String>();
	    for(Row ftRow : rs) {
	    	rsFeatures.add(ftRow.getString("ft_name"));
	    }
	    return rsFeatures;
	}
	
	public ArrayList<RoomTypeBean> getHotelRoomTypes(String id) {
		String queryText = "SELECT * FROM room_type where h_id=? ALLOW FILTERING";
	    PreparedStatement preparedStatement = getSession().prepare(queryText);
	    BoundStatement boundStatement = preparedStatement.bind(id);
	    ResultSet rs = getSession().execute(boundStatement);
	    ArrayList<RoomTypeBean> rsRts = new ArrayList<RoomTypeBean>();
	    for(Row ftRow : rs) {
	    	RoomTypeBean rtBean = new RoomTypeBean();
	    	rtBean.setHotelId(ftRow.getString("h_id"));
	    	rtBean.setName(ftRow.getString("name"));
	    	rtBean.setMaxPP(ftRow.getInt("max_people"));
	    	rtBean.setNoRooms(ftRow.getInt("no_rooms"));
	    	rtBean.setNoAvRooms(ftRow.getInt("no_av_rooms"));
	    	rtBean.setPrice(ftRow.getDouble("price"));
	    	rsRts.add(rtBean);
	    }
	    return rsRts;
	}
	
	public HashMap<String,String> getRoomFeatures(String h_id, int r_id) {
		String queryText = "SELECT * FROM amenities where h_id=? and r_id=? ALLOW FILTERING";
	    PreparedStatement preparedStatement = getSession().prepare(queryText);
	    BoundStatement boundStatement = preparedStatement.bind(h_id, r_id);
	    ResultSet rs = getSession().execute(boundStatement);
	    HashMap<String,String> rsFts = new HashMap<String, String>();
	    for(Row ftRow : rs) {	    	
	    	rsFts.put(ftRow.getString("name"),"1");
	    }
	    return rsFts;
	}
	
	public ArrayList<HashMap<String, Long>> getRoomAvDates(String h_id, int r_id) {
		String queryText = "SELECT * FROM room_availabilities where h_id=? and r_id=? ALLOW FILTERING";
	    PreparedStatement preparedStatement = getSession().prepare(queryText);
	    BoundStatement boundStatement = preparedStatement.bind(h_id, r_id);
	    ResultSet rs = getSession().execute(boundStatement);
	    ArrayList<HashMap<String, Long>> rsFts = new ArrayList<HashMap<String,Long>>();
	    for(Row ftRow : rs) {
	    	HashMap<String, Long> hm = new HashMap<String, Long>();
	    	hm.put("startDate", ftRow.getLong("startDate"));
	    	hm.put("endDate", ftRow.getLong("endDate"));
	    	hm.put("status", ftRow.getLong("status"));
	    	rsFts.add(hm);
	    }
	    return rsFts;
	}
	
	public ArrayList<RoomBean> getHotelRooms(String h_id) {
		String queryText = "SELECT * FROM room where h_id=? ALLOW FILTERING";
	    PreparedStatement preparedStatement = getSession().prepare(queryText);
	    BoundStatement boundStatement = preparedStatement.bind(h_id);
	    ResultSet rs = getSession().execute(boundStatement);
	    ArrayList<RoomBean> rsRts = new ArrayList<RoomBean>();
	    for(Row ftRow : rs) {
	    	RoomBean rtBean = new RoomBean();
	    	rtBean.setHotelId(ftRow.getString("h_id"));
	    	rtBean.setTypeName(ftRow.getString("rt_name"));
	    	rtBean.setRoomId(ftRow.getInt("id"));
	    	rtBean.setHotelName(ftRow.getString("hotelName"));
	    	rtBean.setCity(ftRow.getString("hotelCity"));
	    	rtBean.setZip(ftRow.getString("hotelZip"));
	    	rtBean.setAddress(ftRow.getString("hotelStreet"));
	    	rtBean.setFloor(ftRow.getInt("floor"));	    	
	    	rtBean.setPrice(ftRow.getDouble("price"));
	    	rtBean.setFeatures(getRoomFeatures(rtBean.getHotelId(),rtBean.getRoomId()));
	    	ArrayList<String> lstImage = new ArrayList<String>();
	    	lstImage.add("/Hotels/"+ftRow.getString("image"));
	    	rtBean.setImages(lstImage);
	    	rtBean.setDates(getRoomAvDates(rtBean.getHotelId(),rtBean.getRoomId()));
	    	rsRts.add(rtBean);
	    }
	    return rsRts;
	}
	
	public HotelBean getHotelObj(int h_id) {
		String queryText = "SELECT * FROM hotel where id=? ALLOW FILTERING";
	    PreparedStatement preparedStatement = getSession().prepare(queryText);
	    BoundStatement boundStatement = preparedStatement.bind(h_id);
	    Row hotelRow = getSession().execute(boundStatement).one();
		HotelBean hotel = new HotelBean();
		hotel.set_id(hotelRow.getString("id"));
		hotel.setHotelId(hotelRow.getInt("id"));
		hotel.setHotelName(hotelRow.getString("name"));
		hotel.setAddress(hotelRow.getString("street"));
		hotel.setCity(hotelRow.getString("city"));
		hotel.setState(hotelRow.getString("state"));
		hotel.setZip(hotelRow.getString("zip"));
		hotel.setPhone(hotelRow.getString("phone"));
		hotel.setUrl(hotelRow.getString("url"));
		hotel.setRating(hotelRow.getDouble("rating"));
		hotel.setTax(hotelRow.getDouble("tax"));
		hotel.setBalance(hotelRow.getDouble("balance"));
		hotel.setMinPrice(hotelRow.getDouble("min_price"));
		hotel.setDescription(hotelRow.getString("street"));
		ArrayList<String> lstImg = new ArrayList<String>();				
		lstImg.add("/Hotels/"+hotelRow.getString("image_id"));
		hotel.setImages(lstImg);
		hotel.setCancellationPolicy(hotelRow.getString("cancelPolicy"));
		hotel.setTotalRooms(hotelRow.getInt("no_rooms"));
		
		hotel.setFeatures(getHotelFeatures(hotel.get_id()));
		
		ArrayList<RoomTypeBean> lstRT = getHotelRoomTypes(hotel.get_id());
		ArrayList<String> lstRtNames = new ArrayList<String>();
		HashMap<String, Integer> roomTypes = new HashMap<>();
		for(RoomTypeBean rtb: lstRT) {
			lstRtNames.add(rtb.getName());
			roomTypes.put(rtb.getName(), rtb.getNoRooms());
		}
		hotel.setRoomTypes(lstRtNames);
		
		ArrayList<RoomBean> rooms = getHotelRooms(hotel.get_id());				
		hotel.setNumberOfRooms(roomTypes);
		hotel.setRooms(rooms);
		return hotel;
	}
	
	public ArrayList<HotelBean> findHotel(Long startDate,Long endDate,String cityName){
		String queryText = "SELECT * FROM hotel where city=? ALLOW FILTERING";
	    PreparedStatement preparedStatement = getSession().prepare(queryText);
	    BoundStatement boundStatement = preparedStatement.bind(cityName);
	    ResultSet rs = getSession().execute(boundStatement);
		
		ArrayList<HotelBean> hotels = new ArrayList<>();
		for(Row hotelRow : rs) {
			HotelBean hotel = new HotelBean();
			hotel.set_id(hotelRow.getString("h_id"));
			hotel.setHotelId(hotelRow.getInt("id"));
			hotel.setHotelName(hotelRow.getString("name"));
			hotel.setAddress(hotelRow.getString("street"));
			hotel.setCity(hotelRow.getString("city"));
			hotel.setState(hotelRow.getString("state"));
			hotel.setZip(hotelRow.getString("zip"));
			hotel.setPhone(hotelRow.getString("phone"));
			hotel.setUrl(hotelRow.getString("url"));
			hotel.setRating(hotelRow.getDouble("rating"));
			hotel.setTax(hotelRow.getDouble("tax"));
			hotel.setBalance(hotelRow.getDouble("balance"));
			hotel.setMinPrice(hotelRow.getDouble("min_price"));
			hotel.setDescription(hotelRow.getString("street"));
			ArrayList<String> lstImg = new ArrayList<String>();				
			lstImg.add("/Hotels/" + hotelRow.getString("image_id"));
			hotel.setImages(lstImg);
			hotel.setCancellationPolicy(hotelRow.getString("cancelPolicy"));
			hotel.setTotalRooms(hotelRow.getInt("no_rooms"));
			
			hotel.setFeatures(getHotelFeatures(hotel.get_id()));
			
			ArrayList<RoomTypeBean> lstRT = getHotelRoomTypes(hotel.get_id());
			ArrayList<String> lstRtNames = new ArrayList<String>();
			HashMap<String, Integer> roomTypes = new HashMap<>();
			for(RoomTypeBean rtb: lstRT) {
				lstRtNames.add(rtb.getName());
				roomTypes.put(rtb.getName(), rtb.getNoRooms());
			}
			hotel.setRoomTypes(lstRtNames);
			
			ArrayList<RoomBean> rooms = getHotelRooms(hotel.get_id());				
			hotel.setNumberOfRooms(roomTypes);
			hotel.setRooms(rooms);
			ArrayList<Integer> lstRoomIds = new ArrayList<Integer>();
			for(RoomBean rb : rooms) {
				lstRoomIds.add(rb.getRoomId());
			}
			hotel.setRoomIdsList(lstRoomIds);
			hotels.add(hotel);
		}
		return hotels;
	}

	//RoomBean room = new RoomBean();
	public RoomBean getRoom(long hotelId,int roomId) {
		/*ArrayList<Document> filters = new ArrayList<Document>();
		filters.add(new Document("$match",new Document("hotelId",hotelId)));
		filters.add(new Document("$unwind","$rooms"));
		filters.add(new Document("$match",new Document("rooms.roomId",roomId)));
		filters.add(new Document("$project",new Document("rooms",1)
								.append("_id", 0)));
		ObjectMapper mapper = new ObjectMapper();
		AggregateIterable<Document> cursor = collection.aggregate(filters);
		cursor.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		        try {
		        	room = mapper.readValue(document.toJson().substring(11,document.toJson().length()-1), RoomBean.class);
				} catch (Exception e) {
					e.printStackTrace();}
		    }
		});*/
		String queryText = "SELECT * FROM room where h_id=? and id=? ALLOW FILTERING";
	    PreparedStatement preparedStatement = getSession().prepare(queryText);
	    BoundStatement boundStatement = preparedStatement.bind(String.valueOf(hotelId), roomId);
	    Row ftRow = getSession().execute(boundStatement).one();
    	RoomBean rtBean = new RoomBean();
    	rtBean.setHotelId(ftRow.getString("h_id"));
    	rtBean.setTypeName(ftRow.getString("rt_name"));
    	rtBean.setRoomId(ftRow.getInt("id"));
    	rtBean.setHotelName(ftRow.getString("hotelName"));
    	rtBean.setCity(ftRow.getString("hotelCity"));
    	rtBean.setZip(ftRow.getString("hotelZip"));
    	rtBean.setAddress(ftRow.getString("hotelStreet"));
    	rtBean.setFloor(ftRow.getInt("floor"));	    	
    	rtBean.setPrice(ftRow.getDouble("price"));
    	rtBean.setFeatures(getRoomFeatures(rtBean.getHotelId(),rtBean.getRoomId()));
    	ArrayList<String> lstImage = new ArrayList<String>();
    	lstImage.add("/Hotels/" + ftRow.getString("image"));
    	rtBean.setImages(lstImage);
    	rtBean.setDates(getRoomAvDates(rtBean.getHotelId(),rtBean.getRoomId()));
		return rtBean;
	}
	
	public void updateRoom(long hotelId,String room) {
		//collection.updateOne(new Document("hotelId",hotelId),
		//		new Document("$push",new Document("rooms",Document.parse(room))));
		//TODO: update a room
	}
	
	public void deleteHotel(int hotelId) {
		//collection.deleteOne(new Document("hotelId",hotelId));
		//TODO: delete a room in Cassandra
	}
}
