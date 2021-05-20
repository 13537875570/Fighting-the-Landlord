package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.PokerLabel;

public class Rule {
	//check whether type of play is valid
	public static PlayType checktype(List<PokerLabel> list) {
		
		Collections.sort(list);
		
		int len=list.size();
		
		if(len==1) {
			return PlayType.p1;
		}else if(len==2) {
			//double or joker bomb
			if(same(list)) return PlayType.p2;
			if(jokerbomb(list)) return PlayType.joker;
			return PlayType.invalid;
			
		}else if(len==3) {
			if(same(list)) return PlayType.p3;
			return PlayType.invalid;
						
		}else if(len==4) {
			if(same(list)) return PlayType.bomb;
			if(triple1(list)) return PlayType.p31;
			return PlayType.invalid;
		}else if(len>=5) {
			if(triple2(list)) {
				return PlayType.p32;
			}else if(consequent(list)) {
				return PlayType.pn;
			}else if(multidouble(list)) {
				return PlayType.p112233;
			}else if(multitriple(list)) {
				return PlayType.p111222;
			}
			
			
		}
		
	
		return PlayType.invalid;
		
		
	}
	
	
	//rule for joker bomb: play 2 joker cards
	public static boolean jokerbomb(List<PokerLabel> list) {
		
		if(list.get(0).getNum()==16 && list.get(1).getNum()==17 || list.get(1).getNum()==16 && list.get(0).getNum()==17) {
			return true;
		}
		
		return false;
	}
	
	//rule for double, triple
	public static boolean same(List<PokerLabel> list) {
		for(int i=0;i<list.size()-1;i++) {
			if(list.get(i).getNum()!=list.get(i+1).getNum()) {
				return false;
			}
		}
		
		return true;
	}
	
	//rule for triple plus 1
	//3+1 or 1+3
	public static boolean triple1(List<PokerLabel> list) {
		if(list.size()!=4) return false;
		
		List<PokerLabel> list1=new ArrayList<PokerLabel>();
		List<PokerLabel> list2=new ArrayList<PokerLabel>();
		
		list1.addAll(list);
		list2.addAll(list);
		list1.remove(0);
		list2.remove(3);
		
		if(same(list1)) return true;
		if(same(list2)) return true;
		
		return false;
	}
	
	
	
	//rule for triple plus 2
	//3+2 or 2+3
	public static boolean triple2(List<PokerLabel> list) {
		if(list.size()!=5) return false;
		
		List<PokerLabel> list1=new ArrayList<PokerLabel>();
		List<PokerLabel> list2=new ArrayList<PokerLabel>();
		
		list1.addAll(list);
		list2.addAll(list);
		
		list1.remove(0);
		list1.remove(0);
		list1.remove(0);
		list2.remove(4);
		list2.remove(3);
		
		if(same(list1) && same(list2)) {
			//3+2
			return true;
		}
		
		list1.clear();
		list2.clear();
		
		list1.addAll(list);
		list2.addAll(list);		
		list1.remove(0);
		list1.remove(0);
		list2.remove(4);
		list2.remove(3);
		list2.remove(2);
		
		if(same(list1) && same(list2)) {
			//2+3
			return true;
		}
		
		
		return false;
	}
	
	
	
	//continuous list
	public static boolean consequent(List<PokerLabel> list) {
		
		if(list.size()<5) return false;
		for(int i=0;i<list.size()-1;i++) {
			if(list.get(i+1).getNum()-list.get(i).getNum()!=1) {
				return false;
			}
		
		}
		return true;
	}
	
	
	//multi double(>=3*double)
	public static boolean multidouble(List<PokerLabel> list) {
		
		if(list.size()<6 || list.size()%2!=0) return false;
		
		for(int i=0;i<list.size();i+=2) {
			if(list.get(i).getNum()!=list.get(i+1).getNum()) {
				return false;
			}
			
		}
		for(int i=0;i<list.size()-2;i+=2) {
			if(list.get(i+2).getNum()-list.get(i).getNum()!=1) {
				return false;
			}
			
		}
		
		return true;
	}
	
	//multi triple 333444
	public static boolean multitriple(List<PokerLabel> list) {
		if(list.size()<6 || list.size()%3!=0) return false; 

		int i=0;
		while(i<list.size()) {
			if(list.get(i).getNum()!=list.get(i+1).getNum() || list.get(i).getNum()!=list.get(i+2).getNum()) {
				return false;
			}
			i+=3;
			
		}
		
		i=0;
		while(i<list.size()-3) {
			if(list.get(i+3).getNum()-list.get(i).getNum()!=1) return false;
		}
		
		return true;
	}
	
	
	//plane1,3311
	//plane2,3322
	//invalid
	
	
	
	//compare poker type
	public static boolean Greater(List<PokerLabel> pre,List<PokerLabel> cur) {
		PlayType type=checktype(pre);
		
		//check whether current and previous player plays the same poker type
		if(type.equals(checktype(cur))) {
			//compare size of type
			if(PlayType.p1.equals(type)) {
				if(compareLast(pre,cur)) {
					return true;
				}
				return false;
				
			}else if(PlayType.p2.equals(type)) {
				if(compareLast(pre,cur)) {
					return true;
				}
				return false;
				
			}else if(PlayType.joker.equals(type)) {
				return false;
				
			}else if(PlayType.p3.equals(type)) {
				if(compareLast(pre,cur)) {
					return true;
				}
				return false;
			}else if(PlayType.p31.equals(type)) {
				if(compareLast(pre,cur)) {
					return true;
				}
				return false;
			}else if(PlayType.bomb.equals(type)) {
				if(compareLast(pre,cur)) {
					return true;
				}
				return false;
			}else if(PlayType.p32.equals(type)) {
				//compare
				if(compareLast(pre,cur)) {
					return true;
				}
				return false;
			}else if(PlayType.pn.equals(type)) {
				if(compareLast(pre,cur)) {
					return true;
				}
				return false;
			}else if(PlayType.p112233.equals(type)) {
				if(compareLast(pre,cur)) {
					return true;
				}
				return false;
			}else if(PlayType.p111222.equals(type)) {
				//compare
				if(compareLast(pre,cur)) {
					return true;
				}
				return false;
			}
			

			
		}else if(jokerbomb(cur)) {
			//joker bomb is greater than any other type
			return true;
		}else if(same(cur) && cur.size()==4) {
			//regular bomb is greater than any other type except joker bomb
			return true;
		}
		
		
		
		
		return false;
	}
	
	
	
	
	public static boolean compareLast(List<PokerLabel> pre,List<PokerLabel> cur) {
		if(pre.get(pre.size()-1).getNum()<cur.get(cur.size()-1).getNum()) {
			return true;
		}	
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
