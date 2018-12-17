package usm.hrs;

import java.security.SecureRandom;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import usm.hrs.exceptions.UserExistsException;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.UserType;

import usm.hrs.model.CassandraData;

public class hrs_generate_data {
	static Session pSession = null;
	
	static final String strKeyspace = "hrs";
	static KeyspaceMetadata keyspaceMetadata = null;
	static UserType address = null;
	
	static final String[] states = {"MS", "TX", "AL", "LA", "NV", "MN", "MO", "CF"};
	static final String[] cities = {"Hattiesburg", "Dallas", "Austin", "SF", 
		"New Orleans", "Chicago", "Boston", "Orlando"};
	
	// Center constants
	static final int C_MIN_NAME = 10;
	static final int C_MAX_NAME = 20;
	static final int C_MIN_BALANCE = -1000;
	static final int C_MAX_BALANCE = 1000;

	// Hotel constants
	static final int HOTELS_PER_CENTER = 100;
	//static final double INITIAL_D_YTD = 30000.00;
	//static final int INITIAL_NEXT_O_ID = 3001;
	static final int MIN_STREET = 10;
	static final int MAX_STREET = 20;
	static final int MIN_CITY = 10;
	static final int MAX_CITY = 20;
	static final int STATE = 2;
	static final int ZIP_LENGTH = 9;
	static final String ZIP_SUFFIX = "11111";
	static final int H_MIN_NAME = 10;
	static final int H_MAX_NAME = 20;
	static final int PHONE = 10;
	static final double MIN_RATINGS = 1.0;
	static final double MAX_RATINGS = 5.0;
	static final double MIN_TAX = 0;
	static final double MAX_TAX = 0.2000;
	static final int TAX_DECIMALS = 4; 
	static final int H_BALANCE = 1000;
	static final int H_MIN_ROOMS = 50;
	static final int H_MAX_ROOMS = 200;
	
	static final String[] H_IMAGES = {"hotelImage1.jpg", "hotelImage2.jpg", "hotelImage3.jpg"
		, "hotelImage4.jpg", "hotelImage5.jpg", "hotelImage6.jpg"};
	static final String[] H_CANCELPOLICY = {"24 hours", "36 hours", "48 hours"};
	static final String[] H_SERVICES = {"Free parking", "Free WiFi", "Swimming pool", 
		"Non-smoking rooms", "Family rooms", "Facilities for disabled guests", 
		"Outdoor", "Business center", "Shuttle"};
	static final String[] H_FACILITIES = {"Restaurant", "Swimming Pool", "Gym", "Casino"};
	static final String[] H_POLICIES = {"Check-in time", "Check-out time", "Cancellation", 
		"Children and extra beds", "Age limitation", "Pets", "Group policy"};
	
	// Room Constants
	//static final int ROOMS_PER_HOTELS = 100;
	static final int R_MIN_CODE = 5;
	static final int R_MAX_CODE = 10;
	static final int R_MIN_FLOOR = 1;
	static final int R_MAX_FLOOR = 10;
	static final double R_MIN_PRICE = 20.00;
	static final double R_MAX_PRICE = 100.00;
	
	static final String[] R_AMENITIES = {"A/C", "TV", "Refrigerator", 
		"Coffee Maker", "Phone",
		"Microwave", "Hairdryer", "Iron", "Balcony", "Fan"};
	static final String[] R_IMAGES = {"roomImage1.jpg", "roomImage2.jpg", 
		"roomImage3.jpg" };

	// Room Type constants
	static final int RT_MIN_NAME = 10;
	static final int RT_MAX_NAME = 20;
	static final int RT_MIN_PEOPLE = 1;
	static final int RT_MAX_PEOPLE = 4;
	static final String[] RoomTypes_Names = {"Single", "Double", "Queen", "King"};
	static final int[] RoomTypes_MaxPP = {1, 2, 3, 4};

	//  Guest constants
	static final int GUESTS_PER_CENTER = 3000;
	static final int G_MIN_FIRST = 6;
	static final int G_MAX_FIRST = 10;
	static final double MIN_DISCOUNT = 0.0000;
	static final double MAX_DISCOUNT = 0.5000;
	static final double DISCOUNT_DECIMALS = 4;
	static final String GOOD_CREDIT = "GC";
	static final String BAD_CREDIT = "BC";
	static final double INITIAL_CREDIT_LIM = 50000.00;
	static final double INITIAL_BALANCE = -10.00;
	static final double INITIAL_YTD_PAYMENT = 10.00;
	static final int INITIAL_PAYMENT_CNT = 1;
	static final int INITIAL_DELIVERY_CNT = 0;
	static final String[] G_GENDER = {"Man", "Woman"};
	
	//  Reservation constants
	static final int RS_MIN_NIGHTS = 1;
	static final int RS_MAX_NIGHTS = 10;
	static final int RS_CF_NO_LEN = 12;

	//  Reservation Item constants
	static final int INITIAL_QUANTITY = 5;
	static final double MIN_AMOUNT = 0.01;

	//  History constants
	static final int MIN_DATA = 12;
	static final int MAX_DATA = 24;
	static final double INITIAL_AMOUNT = 10.00;

	//  Used to generate payment transactions
	static final double MIN_PAYMENT = 1.0;
	static final double MAX_PAYMENT = 5000.0;
	
	//generate random String
	static final String AB = "0123456789 ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	public static String randomString( int len ){
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
	
	public static int randomInt(int min, int max) {
	    int randomNum = rnd.nextInt(max - min+1) + min;
	    return randomNum;
	}
	
	public static double randomDouble(double min, double max) {
	    double randomNum = Math.random() * (max - min) + min;
	    return randomNum;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		pSession = CassandraData.getSession();
		keyspaceMetadata = pSession.getCluster().getMetadata().getKeyspace(strKeyspace);
		address = keyspaceMetadata.getUserType("address");
		
		int nCenter = 3;
		int nTotalHotels = 0;
		for(int i=0; i < nCenter; i++) {
			addCenter("C"+i,"Center_"+(i+1),randomDouble(C_MIN_BALANCE, C_MAX_BALANCE));
			System.out.println("Generate new center id=C"+i);
			int n_hotels = randomInt(20, 50);
			for(int j = 0; j < n_hotels; j++) {
				int n_rooms = 0;
				nTotalHotels++;
				int h_id = nTotalHotels;
				double minPrice = R_MAX_PRICE + 100;
				String hotelName = "Hotel "+randomString(H_MIN_NAME);
	    		String hotelStreet = "" + randomInt(0,999) + randomString(10) + " Str." ;
	    		String hotelCity = cities[randomInt(0,cities.length-1)];
	    		String hotelState = states[randomInt(0, states.length-1)];
	    		String hotelZip = ""+randomInt(10000,99999);
				//add features
				int n_features = randomInt(3,R_AMENITIES.length);
				for(int k=0; k < n_features; k++) {
					addFeatures(h_id, R_AMENITIES[k]);
				}
				//add roomtype
				int r_id = 0;
				for(int k=0; k < 4; k++) {
					int n_rt = randomInt(0, (3-k)*10);
					n_rooms += n_rt;
					double price = randomDouble(R_MIN_PRICE, R_MAX_PRICE);
					if(price < minPrice)
						minPrice = price;
					addRoomType(h_id,
							RoomTypes_Names[k],
							RoomTypes_MaxPP[k],
							n_rt,
							price);
					System.out.println("Generate new room type h_id=H_"+(h_id)
							+", rt_id="+k+", total_rooms="+n_rt);
					//add rooms
					for(int l=0; l < n_rt; l++) {
						r_id++;
						addRoom(h_id,r_id,RoomTypes_Names[k],
								hotelName, hotelCity, hotelState, hotelZip, hotelStreet,
								randomInt(1,10),
								price,
								"AV",
								R_IMAGES[randomInt(0,R_IMAGES.length-1)]);
						addRoomAvailability(String.valueOf(h_id),r_id);
						for(int amn=0; amn < n_features; amn++) {
							addRoomAmenities(String.valueOf(h_id), r_id, R_AMENITIES[amn]);
						}
						System.out.println("Generate new room h_id=H_"+(h_id)
								+", rt_id=RT_"+k+", r_id=R_"+l);
					}
				}
				//add hotel
				addHotel(nTotalHotels,n_rooms,minPrice);
				System.out.println("Generate new hotel h_id=H_"+(h_id)
						+ ", total rooms="+n_rooms);
			}
		}
		
		//generate guest
		for(int i=0; i < 1000; i++) {			
			UUID u_id = UUID.randomUUID();
			addGuest(u_id,i);
			System.out.println("Generate new guest id="+u_id);
		}
		
		/*//Fix no availability & amenities of rooms
		String queryText = "SELECT h_id, id FROM room;";
		PreparedStatement preparedStatement = pSession.prepare(queryText);
		BoundStatement bound = preparedStatement.bind();
		ResultSet resultSet = pSession.execute(bound);
		for(Row dtRow : resultSet) {
			//addRoomAvailability(dtRow.getString("h_id"), dtRow.getInt("id"));			
			for(int amn=0; amn < 3; amn++) {
				addRoomAmenities(dtRow.getString("h_id"), 
						dtRow.getInt("id"), 
						R_AMENITIES[amn]);
			}
		}*/
		System.out.println("Successfully generate data!");
	}
	
	public static boolean addCenter(String id, String name, double balance) {
	    String queryText = "INSERT INTO center (id, name, balance) values (?, ?, ?) IF NOT EXISTS";	
	    PreparedStatement preparedStatement = pSession.prepare(queryText);
	    // Because we use an IF NOT EXISTS clause, we get back a result set with 1 row containing 1 boolean column called "[applied]"
	    ResultSet resultSet = pSession.execute(preparedStatement.bind(id, name, balance));	
	    // Determine if the user was inserted.  If not, throw an exception.
	    boolean centerGotInserted = resultSet.one().getBool("[applied]");	
	    if (!centerGotInserted) {
	      return false;
	    }	
	    // Return the new user so the caller can get the userid
	    return true;
	}
	
	public static boolean addHotel(int id, int n_rooms,double minPrice) {
	    String queryText = "INSERT INTO hotel (h_id,id, name, street, city, state, zip, "+
	    		"phone, url, rating, tax, balance, min_price, " +
	    		"image_id, description, cancelPolicy, no_rooms) " +
	    		"values (:h_id,:id,:name,:str,:city,:state, :zip, :phone, :url, "+
	    		":rtg, :tax, :bl, :minPr, :img, :description, :canPl, :nr) "+
	    		"IF NOT EXISTS";	
	    PreparedStatement preparedStatement = pSession.prepare(queryText);
	    BoundStatement bound = preparedStatement.bind()
	    		  .setString("h_id", String.valueOf(id))
	    		  .setInt("id", id)
	    		  .setString("name", "Hotel "+randomString(H_MIN_NAME))
	    		  .setString("str", "Str. " + randomString(10))
	    		  .setString("city", cities[randomInt(0,cities.length-1)])
	    		  .setString("state", states[randomInt(0, states.length-1)])
	    		  .setString("zip", ""+randomInt(10000,99999))
	    		  .setString("phone", "601"+randomInt(1000000,9999999))
	    		  .setString("url", "www"+randomString(H_MIN_NAME)+".com")
	    		  .setDouble("rtg", randomDouble(MIN_RATINGS,MAX_RATINGS))
	    		  .setDouble("tax", randomDouble(MIN_TAX,MAX_TAX))
	    		  .setDouble("bl", H_BALANCE+randomInt(-1000,1000))
	    		  .setDouble("minPr", minPrice)
	    		  .setString("img", H_IMAGES[randomInt(0,H_IMAGES.length-1)])
	    		  .setString("description", "INFO: "+randomString(50))
	    		  .setString("canPl", H_CANCELPOLICY[randomInt(0,H_CANCELPOLICY.length-1)])
	    		  .setInt("nr", n_rooms);	    
	    //bound.setString("sv", ""+randomInt(10000,99999))
	    //		  .setString("fc", ""+randomInt(10000,99999))
	    //		  .setString("pl", ""+randomInt(10000,99999));	    
	    ResultSet resultSet = pSession.execute(bound);	
	    // Determine if the user was inserted.  If not, throw an exception.
	    boolean recordGotInserted = resultSet.one().getBool("[applied]");	
	    if (!recordGotInserted) {
	      return false;
	    }	
	    // Return the new user so the caller can get the userid
	    return true;
	}
	
	public static boolean addFeatures(int h_id, String name) {
	    String queryText = "INSERT INTO features_by_hotel (h_id, ft_name, status) "+
	    		"values (:h_id, :name, :status) IF NOT EXISTS";	
	    PreparedStatement preparedStatement = pSession.prepare(queryText);
	    BoundStatement bound = preparedStatement.bind()
	    		  .setString("h_id", String.valueOf(h_id))
	    		  .setString("name", name)
	    		  .setString("status", "1");	    
	    //bound.setString("sv", ""+randomInt(10000,99999))
	    //		  .setString("fc", ""+randomInt(10000,99999))
	    //		  .setString("pl", ""+randomInt(10000,99999));	    
	    ResultSet resultSet = pSession.execute(bound);	
	    // Determine if the user was inserted.  If not, throw an exception.
	    boolean recordGotInserted = resultSet.one().getBool("[applied]");	
	    if (!recordGotInserted) {
	      return false;
	    }	
	    // Return the new user so the caller can get the userid
	    return true;
	}
	
	public static boolean addRoomType(int h_id, String name, int max_pp, int n_rt_rooms, double price) {
	    String queryText = "INSERT INTO room_type (h_id, name, max_people, no_rooms, no_av_rooms, price) "+
	    		"values (:h_id, :name, :m_pp, :nr, :nav, :pr) IF NOT EXISTS";	
	    PreparedStatement preparedStatement = pSession.prepare(queryText);
	    BoundStatement bound = preparedStatement.bind()
	    		  .setString("h_id", String.valueOf(h_id))
	    		  .setString("name", name)
	    		  .setInt("m_pp", max_pp)
	    		  .setInt("nr", n_rt_rooms)
	    		  .setInt("nav", n_rt_rooms)
	    		  .setDouble("pr", price);	    
	    //bound.setString("sv", ""+randomInt(10000,99999))
	    //		  .setString("fc", ""+randomInt(10000,99999))
	    //		  .setString("pl", ""+randomInt(10000,99999));	    
	    ResultSet resultSet = pSession.execute(bound);	
	    // Determine if the user was inserted.  If not, throw an exception.
	    boolean recordGotInserted = resultSet.one().getBool("[applied]");	
	    if (!recordGotInserted) {
	      return false;
	    }	
	    // Return the new user so the caller can get the userid
	    return true;
	}
	
	public static boolean addRoomAvailability(String h_id, int r_id) {
	    String queryText = "INSERT INTO room_availabilities (h_id, r_id, startDate, endDate, status) "+
	    		"values (:h_id, :r_id, :sDt, :eDt, :status) IF NOT EXISTS";	
	    PreparedStatement preparedStatement = pSession.prepare(queryText);
	    long curDt = new java.util.Date().getTime();
	    long endDt = new GregorianCalendar(2018, 12, 31).getTime().getTime();
	    BoundStatement bound = preparedStatement.bind()
	    		  .setString("h_id", h_id)
	    		  .setInt("r_id", r_id)
	    		  .setLong("sDt", curDt)
	    		  .setLong("eDt", endDt)
	    		  .setLong("status", 1L);	    
	    //bound.setString("sv", ""+randomInt(10000,99999))
	    //		  .setString("fc", ""+randomInt(10000,99999))
	    //		  .setString("pl", ""+randomInt(10000,99999));	    
	    ResultSet resultSet = pSession.execute(bound);	
	    // Determine if the user was inserted.  If not, throw an exception.
	    boolean recordGotInserted = resultSet.one().getBool("[applied]");	
	    if (!recordGotInserted) {
	      return false;
	    }	
	    // Return the new user so the caller can get the userid
	    return true;
	}
	
	public static boolean addRoomAmenities(String h_id, int r_id, String name) {
	    String queryText = "INSERT INTO amenities (h_id, r_id, name, status) "+
	    		"values (:h_id, :r_id, :name, :status) IF NOT EXISTS";	
	    PreparedStatement preparedStatement = pSession.prepare(queryText);
	    BoundStatement bound = preparedStatement.bind()
	    		  .setString("h_id", h_id)
	    		  .setInt("r_id", r_id)
	    		  .setString("name", name)
	    		  .setString("status", "1");	    
	    //bound.setString("sv", ""+randomInt(10000,99999))
	    //		  .setString("fc", ""+randomInt(10000,99999))
	    //		  .setString("pl", ""+randomInt(10000,99999));	    
	    ResultSet resultSet = pSession.execute(bound);	
	    // Determine if the user was inserted.  If not, throw an exception.
	    boolean recordGotInserted = resultSet.one().getBool("[applied]");	
	    if (!recordGotInserted) {
	      return false;
	    }	
	    // Return the new user so the caller can get the userid
	    return true;
	}
	
	public static boolean addRoom(int h_id, int r_id, String rt_name, 
			String hotelName, String city, String state, String zip, String street, 
			int floor, double price, String status, String image) {
	    String queryText = "INSERT INTO room (h_id, id, rt_name, hotelName, "+
			"hotelCity, hotelState, hotelZip, hotelStreet, " +
	    	"floor, price, status, image) " +
	    	"values (:h_id, :id, :rt_name, :htlName, :htlCity, :htlSt, :htlZip, " +
	    	":htlStr, :fl, :pr, :st, :img) IF NOT EXISTS";
	    PreparedStatement preparedStatement = pSession.prepare(queryText);
	    BoundStatement bound = preparedStatement.bind()
	    		  .setString("h_id", String.valueOf(h_id))
	    		  .setInt("id", r_id)
	    		  .setString("rt_name", rt_name)
	    		  .setString("htlName", hotelName)
	    		  .setString("htlCity", city)
	    		  .setString("htlSt", state)
	    		  .setString("htlZip", zip)
	    		  .setString("htlStr", street)
	    		  .setInt("fl", floor)
	    		  .setDouble("pr", price)
	    		  .setString("st", status)
	    		  .setString("img", image);	    
	    //bound.setString("sv", ""+randomInt(10000,99999))
	    //		  .setString("fc", ""+randomInt(10000,99999))
	    //		  .setString("pl", ""+randomInt(10000,99999));	    
	    ResultSet resultSet = pSession.execute(bound);	
	    // Determine if the user was inserted.  If not, throw an exception.
	    boolean recordGotInserted = resultSet.one().getBool("[applied]");	
	    if (!recordGotInserted) {
	      return false;
	    }	
	    // Return the new user so the caller can get the userid
	    return true;
	}
	
	public static boolean addGuest(UUID u_id, int i) {
	    String queryText = "INSERT INTO guest (id, full_name, phone, type, "+
	    		"email, password, occupation, gender, age, since, "+
	    		"discount, credit_st, credit_lm, balance, ytd_pm) "+
	    		"values (:id, :fnm, :phone, :tp, :email, :pwd, :ocp, :sex, :age, "+
	    		":since, :discount, :crst, :crlm, :bl, :ytd) IF NOT EXISTS";	
	    PreparedStatement preparedStatement = pSession.prepare(queryText);
	    BoundStatement bound = preparedStatement.bind()
	    		  .setUUID("id", u_id)
	    		  .setString("fnm", randomString(G_MIN_FIRST+G_MAX_FIRST))	    		  
	    		  .setString("phone", ""+randomInt(0,9)+"123"+randomInt(10000,99999))
	    		  .setString("tp", "S")
	    		  .setString("email", "abc_"+i+"@gmail.com")
	    		  .setString("pwd", "1234")
	    		  .setString("ocp", "" + randomString(10))
	    		  .setString("sex", G_GENDER[randomInt(0,1)])
	    		  .setInt("age", randomInt(18, 70))
	    		  .setString("since", ""+randomInt(1000000,9999999))
	    		  .setDouble("discount", randomDouble(MIN_DISCOUNT,MAX_DISCOUNT))
	    		  .setString("crst", (randomInt(0,1)==0?BAD_CREDIT:GOOD_CREDIT))
	    		  .setInt("crlm", randomInt(-1000,1000))
	    		  .setDouble("bl", H_BALANCE+randomInt(-1000,1000))
	    		  .setDouble("ytd", H_BALANCE+randomInt(-2000,2000));	    
	    //bound.setString("sv", ""+randomInt(10000,99999))
	    //		  .setString("fc", ""+randomInt(10000,99999))
	    //		  .setString("pl", ""+randomInt(10000,99999));	    
	    ResultSet resultSet = pSession.execute(bound);	
	    // Determine if the user was inserted.  If not, throw an exception.
	    boolean recordGotInserted = resultSet.one().getBool("[applied]");	
	    if (!recordGotInserted) {
	      return false;
	    }	
	    // Return the new user so the caller can get the userid
	    return true;
	}
}
