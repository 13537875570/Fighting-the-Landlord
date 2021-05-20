//0.5change

package model;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PokerLabel extends JLabel implements Comparable{
	
	private int id;
	
	private String name;
	
	private int num;
	
	private boolean isOut;
	
	private boolean isUp;
	
	private boolean selected;//whether selected by mouse
	
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public boolean isOut() {
		return isOut;
	}
	public void setOut(boolean isOut) {
		this.isOut = isOut;
	}
	public boolean isUp() {
		return isUp;
	}
	public void setUp(boolean isUp) {
		this.isUp = isUp;
	}
	
	public PokerLabel() {
		this.setSize(105, 150);
		
		
	}
	
	public PokerLabel(int id,String name,int num) {
		this.id=id;
		this.name=name;
		this.num=num;
		this.setSize(105, 150);
	}
	
	public PokerLabel(int id,String name,int num,boolean isOut,boolean isUp) {
		this.id=id;
		this.name=name;
		this.num=num;
		this.isOut=isOut;
		this.isUp=isUp;
		
		if(isUp) turnUp();
		else turnDown();
		
		this.setSize(105, 150);
		
		
		
		
	}	
	public void turnUp() {
		this.setIcon(new ImageIcon("images/poker/"+id+".jpg"));
	}
	public void turnDown() {
		this.setIcon(new ImageIcon("images/poker/back.jpg"));
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		
		PokerLabel pl=(PokerLabel) o;
		
		
		if(this.num>pl.num) return 1;
		
		else if(pl.num>this.num) return -1;
		
		else return 0;
	}	
	
	
	
	
	
	
	

}







