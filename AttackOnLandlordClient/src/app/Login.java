//0change

package app;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Login extends JFrame{
	
	private JLabel nameLabel;
	private JTextField nameTextfield;
	private JButton LoginButton;
	private JButton cancelButton;
	
	
	public Login() {
		
		//create component object
		this.nameLabel=new JLabel("Username");		
		this.nameTextfield=new JTextField();
		this.LoginButton=new JButton("Login");
		this.cancelButton=new JButton("Cancel");
		
		//window property
		this.setSize(400,300);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(new GridLayout(2,2));
		
		
		//put component to the window
		this.add(nameLabel);
		this.add(nameTextfield);
		this.add(LoginButton);
		this.add(cancelButton);
		theEvent event=new theEvent();
		this.LoginButton.addActionListener(event);
		
		
	}
	
	
	
	//create event
	
	class theEvent implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			//Login
			String user=nameTextfield.getText();
			
			
			
			
			//
			try {
				
				
	
				
				//192.168.0.168 8888
				//"192.168.0.168",52093 ok
				Socket socket=new Socket("192.168.0.168",52093);
				
				//go to main frame
				new MainFrame(user,socket);
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

			
			
			
			
		}
		
	}
	
	
	
	
	
	
	
	
	

}























