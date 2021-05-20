package app;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import game.Game;
import game.PlayType;
import game.Rule;
import model.Player;
import model.Poker;
import model.PokerLabel;
import thread.PlayThread;
import thread.ReceiveThread;
import thread.SendThread;
import thread.TimerThread;



public class MainFrame extends JFrame{
	
	public Panel panel;
	
	public String username;
	
	public Socket socket;
	
	public SendThread sendThread;
	
	public ReceiveThread receiveThread;
	
	public Player current1;//current player
	
	public List<PokerLabel> pls=new ArrayList<PokerLabel>();// poker list
	

	
	public JButton call=new JButton("Call Lord");// choose call lord or not
	
	public JButton notcall=new JButton("Not Call");
	
	public JButton timer=new JButton();
	
	public TimerThread timer0;
	
	public boolean lord;//is lord or not
	
	public JLabel msgL=new JLabel();//save message
	
	public JButton lordicon=new JButton("L");
	
	public JButton playcard=new JButton();// the action of play out cards
	
	public JButton notplay=new JButton();
	
	public PlayThread playthread;
	
	public List<PokerLabel> selectpoker=new ArrayList<PokerLabel>();//save pokers selected by mouse
	
	public List<PokerLabel> outpoker=new ArrayList<PokerLabel>(); //save current played cards
	
	public boolean isout;
	
	public int prepid=-1;//previous played player id
	
	public MainFrame(String username,Socket socket) {
		this.username=username;
		this.socket=socket;
		
		
		//window property
		this.setSize(1200,700);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//panel
		
		panel=new Panel();
		panel.setBounds(0, 0, 1200, 700);
		this.add(panel);
		
		
		
		initial();
		
		//initiate thread
		sendThread=new SendThread(socket,username);
		sendThread.start();
		
		//accept thread
		
		receiveThread=new ReceiveThread(socket, this);
		receiveThread.start();
		
		
	}
	
	//initiate window
	public void initial() {
		
		msgL=new JLabel();
		
		playcard=new JButton("play");
		playcard.setBounds(330,360,100,50);
		playcard.setBackground(Color.green);
		playcard.addMouseListener(new setlord());
		playcard.setVisible(false);
		this.panel.add(playcard);
		
		notplay=new JButton("waive");
		notplay.setBounds(440,360,100,50);
		notplay.setBackground(Color.red);
		notplay.addMouseListener(new setlord());
		notplay.setVisible(false);
		this.panel.add(notplay);
		
		timer=new JButton();
		timer.setBounds(550,360,50,50);
		timer.setBackground(Color.pink);
		timer.setVisible(false);
		this.panel.add(timer);
	}
	
	
	public void showPlayers(List<Player> players) {
		
		for(int i=0;i<players.size();i++) {
			if(players.get(i).getName().equals(username)) {
				current1=players.get(i);
				break;

			}
			
			
		}
		
		
		
		
		List<Poker> pokers=current1.getPoker();
		
		for(int i=0;i<pokers.size();i++) {
			
			//create poker
			Poker poker=pokers.get(i);
			PokerLabel pl=new PokerLabel(poker.getId(),poker.getName(),poker.getNum());
			
			pl.turnUp();
			//add to panel
			this.panel.add(pl);
			
			
			this.pls.add(pl);
			
			//display
			this.panel.setComponentZOrder(pl, 0);
			
			Game.move(pl, 300+35*i, 450);
			
		}
		//sort
		Collections.sort(pls);
		
		for(int i=0;i<pls.size();i++) {
			this.panel.setComponentZOrder(pls.get(i), 0);
			Game.move(pls.get(i), 300+35*i, 450);
			
		}
		
		System.out.println(current1);
		
		
		if(current1.getId()==0) {
			getlord();
		}
		
		
	}
	
	
	
	/*
	public void addcall() {
		call.setBackground(Color.green);
		call.setOpaque(true);
		call.addActionListener((ActionListener) this);
	}
	*/
	
	

// if a player calls lord
	public void getlord() {
		// TODO Auto-generated method stub

		
		call.setBounds(300,400,100,50);
		call.setBackground(Color.green);
		call.addMouseListener(new setlord());
		this.panel.add(call);
		
		notcall.setBounds(400,400,100,50);
		notcall.setBackground(Color.blue);
		notcall.addMouseListener(new setlord());
		this.panel.add(notcall);
		
		//timer=new JButton();
		//timer.setBounds(350,350,50,50);
		timer=new JButton();
		timer.setBounds(550,360,50,50);
		timer.setBackground(Color.pink);
		//timer.setVisible(false);
		this.panel.add(timer);

		
		
		//repaint
		this.repaint();
		
		
		timer0=new TimerThread(10,this);
		timer0.start();
	
		
		
	}
	
	
	
	
	//players play cards
	public void playcards() {
		
		//shows covered buttons
		playcard.setVisible(true);
		notplay.setVisible(true);
		timer.setVisible(true);
		

		
		//repaint
		this.repaint();
		
		playthread=new PlayThread(30,this);
		playthread.start();
		
	}
	
	
	
	public void showMsg(int t) {
		msgL.setVisible(true);
		msgL.setBounds(500,300,120,80);
		if(t==1) {
			msgL=new JLabel("NOT CALL");
		}
		
		if(t==3) {
			msgL=new JLabel("NOT PLAY");
		}
		

		msgL.setBackground(Color.white);
		
		this.panel.add(msgL);
		this.repaint();
		
	}
	
	
	
	public void addLordpoker(List<Poker> pokers) {
		
		for(int i=0;i<pokers.size();i++) {
			Poker poker=pokers.get(i);

			PokerLabel pl=new PokerLabel(poker.getId(),poker.getName(),poker.getNum());
			
			pl.turnUp();

			this.pls.add(pl);
			
		}
		
		
		Collections.sort(pls);
		
		for(int i=0;i<pls.size();i++) {
			
			//add to panel
			this.panel.add(pls.get(i));
			
			//display
			this.panel.setComponentZOrder(pls.get(i), 0);
			
			Game.move(pls.get(i), 300+35*i, 450);
		}
		
		current1.getPoker().addAll(pokers);
		
	}
	
	
	
	//create landlord icon
	
	public void showlord(int pid) {
		lordicon.setSize(60,90);
		lordicon.setBackground(Color.yellow);
		
		
		if(pid==current1.getId()) {
			lordicon.setLocation(200,450);
		}
		else if(pid+1==current1.getId()||pid-2==current1.getId()) {
			//set location of forward player
			lordicon.setLocation(200,100);
		}
		else {
			//set location of backward player
			lordicon.setLocation(950,100);
		}
		
		
		this.panel.add(lordicon);
		this.repaint();
		
	}
	
	
	

	public void clickpoker() {
		// TODO Auto-generated method stub
		for(int i=0;i<pls.size();i++) {
			
			pls.get(i).addMouseListener(new pokerEvent());
		}
		
		
	}
	
	
	
	
	
	public void outList(int pid, List<Poker> pokers) {
		
		for(int i=0;i<outpoker.size();i++) {
			panel.remove(outpoker.get(i));
		}		
		
		//clear previous played poker list
		outpoker.clear();
			
		//hide as "not play" msg is sent
		msgL.setVisible(false);
		
		//display current played pokers
		for(int i=0;i<pokers.size();i++) {
			Poker poker=pokers.get(i);
			PokerLabel pl=new PokerLabel(poker.getId(),poker.getName(),poker.getNum());
			pl.setLocation(400+30*i,200);
			pl.turnUp();
			panel.add(pl);
			outpoker.add(pl);
			panel.setComponentZOrder(pl, 0);
			
		}
		
		this.repaint();
	}
	
	
	
	
	//remove played cards from current player's list
	public void removeOutpoker() {
		
		//remove from current player list
		pls.removeAll(selectpoker);
		
		//remove from panel
		for(int i=0;i<selectpoker.size();i++) {
			panel.remove(selectpoker.get(i));
		}
		
		//rearrange the rest poker list 
		for(int i=0;i<pls.size();i++) {
			panel.setComponentZOrder(pls.get(i), 0);
			Game.move(pls.get(i), 300+30*i, 450);
		}
		
		//clear selected poker list
		
		selectpoker.clear();
		
		this.repaint();
		
		
	}
	
	
	
	
	
	
	class setlord implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
			//call landlord
			if(e.getSource().equals(call)) {
				timer0.setRun(false);
				lord=true;
				call.setVisible(false);
				notcall.setVisible(false);
				timer.setVisible(false);
				
			}
			
			//not call landlord
			if(e.getSource().equals(notcall)) {
				timer0.setRun(false);
				lord=false;
				call.setVisible(false);
				notcall.setVisible(false);	
				timer.setVisible(false);
				
				
			}
			
			//play card
			if(e.getSource().equals(playcard)) {
				PlayType type=Rule.checktype(selectpoker);
				
				
				//check whether type is valid
				if(type.equals(PlayType.invalid)) {
					JOptionPane.showMessageDialog(null, "INVALID TYPE!!");
				}else {
				//check whether type is greater than previous one, or previous one is your self
					
					if(prepid==-1 || prepid==current1.getId() || Rule.Greater(outpoker, selectpoker)) {
						isout=true;
						playthread.setRun(false);
						playcard.setVisible(false);
						notplay.setVisible(false);
						timer.setVisible(false);
					}else {
						JOptionPane.showMessageDialog(null, "Please Follow the Rule");
					}
				}
				
			}
			
			
			if(e.getSource().equals(notplay)) {
				
				isout=false;
				playthread.setRun(false);
				playcard.setVisible(false);
				notplay.setVisible(false);
				timer.setVisible(false);				
			}
			
			
			
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
	class pokerEvent implements MouseListener {

		@Override
		
		//mouse selects poker to make it up or down
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			PokerLabel pl=(PokerLabel) e.getSource();
			
			//not yet selected, move up
			if(pl.isSelected()) {
				pl.setSelected(false);
				pl.setLocation(pl.getX(),pl.getY()+30);
				selectpoker.remove(pl);
				
				
			}
			//selected, move down
			else {
				pl.setSelected(true);
				pl.setLocation(pl.getX(),pl.getY()-30);
				selectpoker.add(pl);
				
			}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	} 
	





	
	
	
	

}
