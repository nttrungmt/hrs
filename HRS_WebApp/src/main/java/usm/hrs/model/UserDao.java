package usm.hrs.model;
import java.io.IOException;
import java.util.ArrayList;

import org.bson.Document;

import usm.hrs.exceptions.UserExistsException;
import usm.hrs.model.UserBean;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class UserDao extends CassandraData {
	public Boolean status = true;
	
	public void insertUser(UserBean user) throws JsonProcessingException{
		//TODO:
		String queryText = "INSERT INTO guest (email, password) values (?, ?) IF NOT EXISTS";

	    PreparedStatement preparedStatement = getSession().prepare(queryText);

	    // Because we use an IF NOT EXISTS clause, we get back a result set with 1 row containing 1 boolean column called "[applied]"
	    ResultSet resultSet = getSession().execute(preparedStatement.bind(user.getEmailId(), user.getPassword()));

	    // Determine if the user was inserted.  If not, throw an exception.
	    boolean userGotInserted = resultSet.one().getBool("[applied]");

	    if (!userGotInserted) {
	    	//throw new UserExistsException(); //TODO:
	    }

	    // Return the new user so the caller can get the userid
	    //return new UserDAO(username, password);
	}
	
	public boolean findUser(String emailId)	{
		String queryText = "SELECT * FROM guest where email = ? ALLOW FILTERING";
	    PreparedStatement preparedStatement = getSession().prepare(queryText);
	    BoundStatement boundStatement = preparedStatement.bind(emailId);
	    Row userRow = getSession().execute(boundStatement).one();	    
		if(userRow != null)
			status = true;
		else
			status = false;
		return status;
	}
	
	public boolean checkUser(String emailId,String password,String type) {
		String queryText = "SELECT * FROM guest where email = ? ALLOW FILTERING";
	    PreparedStatement preparedStatement = getSession().prepare(queryText);
	    BoundStatement boundStatement = preparedStatement.bind(emailId);
	    Row userRow = getSession().execute(boundStatement).one();	    
		if(userRow != null)
			if(password.equals(userRow.getString("password")))
			//&& type.equals(userRow.getString("type"))
				status = true;
			else
				status = false;
		else
			status = false;
		return status;
	}
	
	public UserBean getUser(String emailId) throws JsonParseException, JsonMappingException, IOException {
		String queryText = "SELECT * FROM guest where email = ? ALLOW FILTERING";
	    PreparedStatement preparedStatement = getSession().prepare(queryText);
	    BoundStatement boundStatement = preparedStatement.bind(emailId);

	    Row userRow = getSession().execute(boundStatement).one();

	    if (userRow == null) {
	      return null;
	    }
	    return new UserBean(userRow);
	}
}
