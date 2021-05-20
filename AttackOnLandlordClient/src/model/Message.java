package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable{
	
	private int type;//msg type
	
	private int pid;//player id
	
	private String content;
	
	private List<Poker> pokers=new ArrayList<Poker>();//poker list
	
	public int getTypeid() {
		return type;
	}

	public void setTypeid(int type) {
		this.type = type;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Poker> getPokers() {
		return pokers;
	}

	public void setPokers(List<Poker> pokers) {
		this.pokers = pokers;
	}
	
	
	public Message() {
		
	}
	
	public Message(int type, int pid, String content, List<Poker> pokers) {
		this.type=type;
		this.pid=pid;
		this.content=content;
		this.pokers=pokers;
				
	}
	
	
	



}












