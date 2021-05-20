//0change

package thread;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SendThread extends Thread {
	
	public String msg;
	
	
	
	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	private Socket socket;
	
	public Socket getSocket() {
		return socket;
	}



	public void setSocket(Socket socket) {
		this.socket = socket;
	}



	public SendThread(Socket socket) {
		this.socket=socket;
	}

	public SendThread(Socket socket,String msg) {
		this.socket=socket;
		this.msg=msg;
	}	


	public void run() {
		
		try {
			DataOutputStream data=new DataOutputStream(socket.getOutputStream());
			while(true) {
				
				
				if(msg!=null) {
					
					System.out.println("Message sending now: "+msg);
					data.writeUTF(msg);
					msg=null;
				}
				
				Thread.sleep(100);
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}












