package cs4740p1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class unigram {
	HashMap<String, Integer> map = new HashMap<>();
	HashMap<String, Double> probMap = new HashMap<>();
	ArrayList<String> words = new ArrayList<>();
	public static int countall = 0;

	//transfer string to arraylist
	public void stringToAl(String s){
		for(int i=0, j=0; i<s.length(); i++){
			if(s.charAt(i) == ' ' || i == s.length()-1){  //two condition of a word ends: get a space or get to the end. 
				String temp;
				if(i == s.length() - 1){  
					temp =s.substring(j,i+1);   //if i is the last char(not space), it needs to include i.
				}
				else{
					temp =s.substring(j,i);
				}
				//System.out.println(temp);
				words.add(temp);
				j = i + 1;
				countall++;
			}
		}

	}

	// put word types and the number of them into the map
	public void add(String s){
		stringToAl(s);
		for(String ss:words){
			String sl = ss.toLowerCase();
			int count = map.containsKey(sl) ? map.get(sl) : 0;
			map.put(sl, count + 1); 
		}
		for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
			String st = entry.getKey(); 
			//System.out.println("words:" + st + "; num:" + map.get(st));

		}
		//System.out.println("countall:" + countall);
	}

	// get the unigram probability
	public void uniprob(){
		for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
			String st = entry.getKey();			
			double num = map.get(st);
			double uniprob = num / countall;
			probMap.put(st, uniprob);
			
		}
	}

	public String chooseRandom(){

		double r = Math.random();
		double cumulativeProb = 0.0;
		for (Entry<String, Double> entry : probMap.entrySet()) {
			String st = entry.getKey();
			cumulativeProb += probMap.get(st);
			if(r <= cumulativeProb){
				return st;
			}
		}
		return "";
	}

	public String generate(String s){
		add(s);
		uniprob();
		String re = "<s>";    //first pick up a <s>
		while(true){
			String rs = chooseRandom();       //get the next word according to its probability
			//System.out.println(rs);
			if(rs.equals("</s>")){  //as long as it's not </s>, keep doing
				break;
			}
			else{
				re = re + " " + rs;
			}
		}
        re = re.replace("<s>", ""); // get rid of all markers
        re = re.replace("</s>", "");
        re = re.trim(); //get rid of the leading space 
        if(re.length()>0){   //in case the only word picked is </s>
           re = re.substring(0, 1).toUpperCase() + re.substring(1);
        }
        re = re.replaceAll("( )+", " ");  //<s> picked may cause more spaces, this will turn them into one space
		return re;
	}
	
	public String generate(String given, String s){
		add(s);
		uniprob();
		String re = given;    //first pick up a <s>
		while(true){
			String rs = chooseRandom();       //get the next word according to its probability
			//System.out.println(rs);
			if(rs.equals("</s>")){  //as long as it's not </s>, keep doing
				break;
			}
			else{
				re = re + " " + rs;
			}
		}
        re = re.replace("<s>", ""); // get rid of all markers
        re = re.replace("</s>", "");
        re = re.trim(); //get rid of the leading space 
        if(re.length()>0){   //in case the only word picked is </s>
           re = re.substring(0, 1).toUpperCase() + re.substring(1);
        }
        re = re.replaceAll("( )+", " ");  //<s> picked may cause more spaces, this will turn them into one space
		return re;
	}


//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String s = "<s> I saw him . </s> <s> He is faboulous . </s> I saw her She is adorable . </s>";
//		unigram a = new unigram();		
//		System.out.println(a.generate(s));
//		String given = "They are him";
//		System.out.println("given word:" + a.generate(given,s));
//
//	}

}
