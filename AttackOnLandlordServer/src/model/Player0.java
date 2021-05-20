//0.5change

package model;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import java.lang.module.*;

//Class: Player
public class Player0 {
	
	private int id;
	
	private String name;
	
	private Socket socket;
	
	private List<Poker> poker=new ArrayList<Poker>();
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public List<Poker> getPoker() {
		return poker;
	}
	public void setPoker(List<Poker> poker) {
		this.poker = poker;
	}
	
	
	public Player0() {}
	
	public Player0(int id,String name, Socket socket, List<Poker> poker) {
		this.id=id;
		this.name=name;
		this.socket=socket;
		this.poker=poker;
	}
	

	
	public Player0(int id) {
		this.id=id;
	}
	
	public Player0(int id,String name) {
		this.id=id;
		this.name=name;
	}
		

}









