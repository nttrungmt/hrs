package usm.hrs.model;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomTypeBean {
	String h_id,name;
	int max_pp,nRooms,nAvRooms;
	double price;
	
	public String getHotelId() {return h_id;}
	public void setHotelId(String id) {this.h_id = id;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public int getMaxPP() {return max_pp;}
	public void setMaxPP(int max) {this.max_pp = max;}
	
	public int getNoRooms() {return nRooms;}
	public void setNoRooms(int nRooms) {this.nRooms = nRooms;}
	
	public int getNoAvRooms() {return nAvRooms;}
	public void setNoAvRooms(int nRooms) {this.nAvRooms = nRooms;}
	
	public double getPrice() {return price;}
	public void setPrice(double pr) {this.price = pr;}
}
