package view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import model.Message;
import model.Player0;
import model.Poker;

import java.sql.*;
import java.lang.module.*;

public class MainFrame {
	
	//initiate player list
	public List<Player0> players=new ArrayList<Player0>();
	
	
	public int index=0;
	
	//initiate poker list
	public List<Poker> pokers=new ArrayList<Poker>();
	
	//leave 3 cards rest
	public List<Poker> rest=new ArrayList<Poker>();
	
	public int step=0;
		
	
	public void createPoker() {
		
		//create jokers
		
		Poker bigjoker=new Poker(0, "Big Joker", 17);
		Poker smalljoker=new Poker(1, "Small Joker", 16);
		
		pokers.add(bigjoker);
		pokers.add(smalljoker);
		
		//create other cards
		
		String[] names=new String[] {"2","A","K","Q","J","10","9","8","7","6","5","4","3"};
		String[] colors=new String[] {"spade","heart","club","diamond"};
		
		
		int id=2;
		int num=15;
		
		
		//poker type
		for(String name: names) {
			//suit type
			for(String color: colors) {
				
				//System.out.println(id+color+name+num);
				
				Poker poker=new Poker(id++,color+name,num);
				
				//System.out.println(poker);
				//System.out.println(poker.getId());
				//System.out.println(poker.getName());
				//System.out.println(poker.getNum());
				
				pokers.add(poker);
			}
			num--;
			
		}
		
		Collections.shuffle(pokers);
		
		//System.out.println(pokers);
		
		
		
	}
	
	//deal method
	public void deal() {
		
		for(int i=0;i<pokers.size();i++) {
			
			
			//3 landlord cards
			if(i>=51) {
				rest.add(pokers.get(i));
				
				
			//cards for 3 players	
			}else {
				if(i%3==0) 
					players.get(0).getPoker().add(pokers.get(i));
				else if(i%3==1) 
					players.get(1).getPoker().add(pokers.get(i));
				else 
					players.get(2).getPoker().add(pokers.get(i));
				
			}
			
			
			
		}
		
		
		for(int i=0;i<players.size();i++) {
			
			
			//System.out.println(i);
			//System.out.println(players.size());
			//System.out.println(players);
			//System.out.println(players.get(i).getPoker());
			
			
			try {
				DataOutputStream data=new DataOutputStream(players.get(i).getSocket().getOutputStream());
				
				//System.out.println(data);
				
				String js=JSON.toJSONString(players);
				
				//System.out.println(js);
				
				data.writeUTF(js);
				
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			
		}
			
		
	}
	
	
	
	
	
	
	
	
	public MainFrame() {
		
		createPoker();
		
		
		try {
			
			
			//create server socket
			ServerSocket server=new ServerSocket(52093);
			
			
			while(true) {

				//accept server socket
				Socket socket=server.accept();
				//start thread, deal with client socket
				AcceptThread accept=new AcceptThread(socket);
				accept.start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//accept client message
	class AcceptThread extends Thread {
		Socket socket;
		
		public AcceptThread(Socket socket) {
			this.socket=socket;
			
		}
		
		
		
		public void run() {
			
			try {
				DataInputStream datainput=new DataInputStream(socket.getInputStream());
				
				
				
				
				while(true) {
					String msg=datainput.readUTF();
					
					
					//finally no need print
					System.out.println(msg);
					
					if(step==0) {
						//create player
						Player0 player=new Player0(index++,msg);
						player.setSocket(socket);
						
						//save into player list
						players.add(player);
						
						
						System.out.println(msg+" loaded");
						
						System.out.println("Loaded#:"+players.size());
						
						
						//deal cards
						
						if(players.size()==3) {
							deal();
							step=1;
						}
					}
					else if(step==1) {//accept msg of calling lord
						
						System.out.println("Accepting message of calling lord");
						
						JSONObject msgJ=JSON.parseObject(msg);
						
						int type=msgJ.getInteger("typeid");
						int pid=msgJ.getInteger("pid");
						String content=msgJ.getString("content");
						
						//not call lord
						
						/*
						if(type==1) {
							
						}
						*/
						
						//call lord
						if(type==2) {
							
							//add lord cards
							Message send=new Message(type,pid,content,rest);
							msg=JSON.toJSONString(send);
							
							step=2;
							
						}
						
						//send msg back to client
						sendMsg(msg);
						
						
						
					}
					else if(step==2) {
						sendMsg(msg);
					}
					
					
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		
		//send msg to client
		public void sendMsg(String msg) {
			
			for(int i=0;i<players.size();i++) {
				
				try {
					DataOutputStream data=new DataOutputStream(players.get(i).getSocket().getOutputStream());
				    data.writeUTF(msg);
				
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		
		
		
		
		
		
		
	}
	
	
	

}
