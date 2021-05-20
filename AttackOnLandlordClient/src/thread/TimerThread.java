package thread;

import com.alibaba.fastjson.JSON;

import app.MainFrame;
import model.Message;
//timer:count time
public class TimerThread extends Thread{
	
	private int i;
	
	private MainFrame main;
	
	private boolean isRun;
	
	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	public TimerThread(int i, MainFrame main) {
		isRun=true;
		this.i=i;
		this.main=main;
	}
	
	public void run() {
		while(i>=0 && isRun) {
			main.timer.setText(i+"");
			i--;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		Message msg=new Message();
		//time is up or player has already clicked not to be a lord 
		if(i<0 || isRun==false && main.lord==false) {
			msg=new Message(1,main.current1.getId(),"not call lord",null);
		}
		
		//player clicked to be a lord
		if(isRun==false && main.lord==true) {
			msg=new Message(2,main.current1.getId(),"call lord",null);
		}
		
		main.sendThread.setMsg(JSON.toJSONString(msg));
		
		
	}
	

}
