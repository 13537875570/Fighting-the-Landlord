package thread;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import app.MainFrame;
import model.Message;
import model.Poker;
import model.PokerLabel;

public class PlayThread extends Thread{
	
	private int time;
	
	private MainFrame mainf;
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public MainFrame getMainf() {
		return mainf;
	}

	public void setMainf(MainFrame mainf) {
		this.mainf = mainf;
	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	private boolean isRun;
	
	
	public PlayThread(int time, MainFrame mainf) {
		
		isRun=true;
		this.time=time;
		this.mainf=mainf;
	
	}
	
	public void run() {
		while(time>=0 && isRun) {
			mainf.timer.setText(time+"");
			time--;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		Message msg=null;
		
		//not play: choose not to play or time runs out
		if(time==-1 || isRun==false && mainf.isout==false) {
			msg=new Message(3,mainf.current1.getId(),"NOT PLAY",null);
			//transfer to json, send to server through sendthread
			String s=JSON.toJSONString(msg);
			mainf.sendThread.setMsg(s);
		}
		
		//play card
		if(isRun==false && mainf.isout==true) {
			msg=new Message(4,mainf.current1.getId(),"PLAY CARD",labelToPoker(mainf.selectpoker));
			
			//transfer to json, send to server through sendthread
			String s=JSON.toJSONString(msg);
			mainf.sendThread.setMsg(s);
			
			//delete played cards from current poker list
			mainf.removeOutpoker();
		}
		
		

		
		
	}
	
	
	
	//transfer pokerlabel to poker
	public List<Poker> labelToPoker(List<PokerLabel> pokers){
		
		List<Poker> list=new ArrayList<Poker>();
		
		for(int i=0;i<pokers.size();i++) {
			PokerLabel pl=pokers.get(i);
			Poker poker=new Poker(pl.getId(),pl.getName(),pl.getNum());
			list.add(poker);
			
		}
		
		
		
		return list;
		
	}
	
	
	
	
	

}









