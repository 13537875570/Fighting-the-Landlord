package thread;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import app.MainFrame;
import model.Player;
import model.Poker;

public class ReceiveThread extends Thread{
	
	private Socket socket;
	
	private MainFrame mainf;
	
	private int step=0;
	
	public ReceiveThread(Socket socket, MainFrame mainf) {
		
		this.socket=socket;
		this.mainf=mainf;
		
		
	}
	
	
	public void run() {
		
		try {
			DataInputStream data=new DataInputStream(socket.getInputStream());
			
			while(true) {
				
				//receive msg from server
				String js=data.readUTF();
				
				if(step==0) {
				
				
				
					List<Player> players=new ArrayList<Player>();
					
					
					System.out.println(js);
					//json string:[{},{}]
					//transfer json string to json array
					JSONArray ja=JSONArray.parseArray(js);
					
					for(int i=0;i<ja.size();i++) {
						
						//single json object
						JSONObject playerj=(JSONObject) ja.get(i);
						int id=playerj.getInteger("id");
						String name=playerj.getString("name");
						
						
						List<Poker> pokers=new ArrayList<Poker>();
						JSONArray pokerja=playerj.getJSONArray("poker");
						
						
						System.out.println(id);
						System.out.println(name);
						System.out.println(pokerja);
						
						for(int j=0;j<pokerja.size();j++) {
							JSONObject pokerj=(JSONObject) pokerja.get(j);
							int pid=pokerj.getInteger("id");
							String pname=pokerj.getString("name");
							int num=pokerj.getInteger("num");
							Poker poker=new Poker(pid,pname,num);
							pokers.add(poker);
							
						}
						
						Player player=new Player(id, name, pokers);
						
						players.add(player);
						
					}
					
					
					if(players.size()==3) {
						mainf.showPlayers(players);
						step=1;//3 players are ready
					}
				}
				else if(step==1) {
					
					//express msg
					JSONObject msgJ=JSONObject.parseObject(js);
					int type=msgJ.getInteger("typeid");
					int pid=msgJ.getInteger("pid");
					String content=msgJ.getString("content");
					
					//msg of not call lord
					if(type==1) {
						
						//mainframe shows msg
						mainf.showMsg(1);
						
						//
						if(pid+1==mainf.current1.getId()) {
							mainf.getlord();
						}
						
					}
					
					
					
				    //msg of call lord
					if(type==2) {
						
						//obtain lord cards
						JSONArray pokerja=msgJ.getJSONArray("pokers");
						
						List<Poker> lordpoker=new ArrayList<Poker>();
						
						
						
						for(int i=0;i<pokerja.size();i++) {
							
							JSONObject pokero=(JSONObject) pokerja.get(i);
							
							int id=pokero.getInteger("id");
							
							String name=pokero.getString("name");
							
							int num=pokero.getInteger("num");
							
							Poker poker=new Poker(id,name,num);
							
							lordpoker.add(poker);
							
						}
						
						//if current player calls lord
						if(mainf.current1.getId()==pid) {				
							
							//send lord cards
							mainf.addLordpoker(lordpoker);
							
							mainf.playcards();
							
						}
						//show lord icon
						mainf.showlord(pid);
						
						mainf.msgL.setVisible(false);
						
						mainf.clickpoker();
						
						
					}
					
					if(type==3) {//not play this turn
						mainf.showMsg(3);
						
						//check whether the current player is the next one to play
						//if it is, display "play" button
						
						if(pid+1==mainf.current1.getId()||pid-2==mainf.current1.getId()) {
							mainf.playcards();
						}
						
						
						
						
					}
					
					if(type==4) {//play this turn
						
						//System.out.println(js);
						//obtain played poker list
						JSONArray pokerja=msgJ.getJSONArray("pokers");
						
						List<Poker> outpoker=new ArrayList<Poker>();					
						for(int i=0;i<pokerja.size();i++) {
							
							JSONObject pokero=(JSONObject) pokerja.get(i);
							
							int id=pokero.getInteger("id");
							
							String name=pokero.getString("name");
							
							int num=pokero.getInteger("num");
							
							Poker poker=new Poker(id,name,num);
							
							outpoker.add(poker);
							
						}
						
						//show played poker list
						mainf.outList(pid,outpoker);
						
						
						//check whether the current player is the next one to play
						//if it is, display "play" button
						
						if(pid+1==mainf.current1.getId()||pid-2==mainf.current1.getId()) {
							mainf.playcards();
						}
						
						mainf.prepid=pid;//record the previous played player id
						
					}
					
					
					
					
				}
				
				
				
				
				
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
