package cs4740p1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class bigram {
	HashMap<String, Integer> map = new HashMap<>();
	HashMap<String, Integer> bimap = new HashMap<>();
	HashMap<String, Double> biprobmap = new HashMap<>();
	ArrayList<String> words = new ArrayList<>();
	ArrayList<String> biWords = new ArrayList<>();
	//public static int countall = 1;

	public void stringToAl(String s){
		for(int i=0, j=0; i<s.length(); i++){
			if(s.charAt(i) == ' ' || i == s.length()-1){
				String temp;
				if(i == s.length() - 1){
					temp =s.substring(j,i+1);
				}
				else{
					temp =s.substring(j,i);
				}
				//System.out.println(temp);
				words.add(temp);
				j = i + 1;

			}
		}

	}


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
	}

	// transfer the string to array, each string in the array has two continuous words
	public void biStringToAl(String s){
		int i=0, j=0, num=0;
		while(i<s.length() - 1){  //avoid index out of range exception			
			while(i < s.length()){
				i++;
				if(s.charAt(i) == ' ' || i == s.length()-1){  //a word ends
					num++;
					break;
				}
			}

			while(num >2 && i < s.length()){
				j++;
				if(s.charAt(j) == ' '){   // j is 2 words slower than i
					j = j + 1;
					break;
				}
			}
			String temp;
			if(num >= 2){
				if(i == s.length() - 1){
					temp =s.substring(j,i+1);
				}
				else{
					temp =s.substring(j,i);
				}
				biWords.add(temp);
				//System.out.println("temp:"+temp);
			}
		}
	}		



	public void biAdd(String s){
		biStringToAl(s);
		for(String ss:biWords){
			String sl = ss.toLowerCase();
			int count = bimap.containsKey(sl) ? bimap.get(sl) : 0;
			bimap.put(sl, count + 1); 
		}
	}


	public void biprob(){  //s1 s2 ¡ª¡ª P(s2 | s1)
		for (HashMap.Entry<String, Integer> entry : bimap.entrySet()) {
			String st = entry.getKey();
			
			double num = bimap.get(st);
			double dom = map.get(st.split(" ")[0]);
			double prob = num / dom;
			biprobmap.put(st, prob);
			//System.out.println("key:" + st+" prob:"+prob);
		}
	}

	public String chooseRandom(String last){

		double r = Math.random();
		double cumulativeProb = 0.0;
		for (Entry<String, Double> entry : biprobmap.entrySet()) {
			String st = entry.getKey();
			if(st.split(" ")[0].equals(last)){
				cumulativeProb += biprobmap.get(st);
				if(r <= cumulativeProb){
					return st;
				}
			}
		}
		return "";
	}

	public String generate(String s){
		add(s);   //put map
		biAdd(s);  // put bimap
		biprob();  // put biprobmap
		String re = "<s>";    //first pick up a <s>
		while(true){
			String last = re.substring(re.lastIndexOf(" ")+1); // get the last word in the sentence
			String rs = chooseRandom(last);       //get the next word according to its probability
			String next = rs.split(" ")[1];
			//System.out.println("picked words:" + rs);
			if(next.equals("</s>")){  //as long as it's not </s>, keep doing
				re = re + " " + next;
				break;
			}
			else{
				re = re + " " + next;
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
		add(s);   //put map
		biAdd(s);  // put bimap
		biprob();  // put biprobmap
		String re = given;    
		while(true){
			String last = re.substring(re.lastIndexOf(" ")+1).toLowerCase(); // get the last word in the sentence
			//System.out.println("picked words:" + last);
			if(!map.containsKey(last)){
				return re;
			}
			String rs = chooseRandom(last);       //get the next word according to its probability
			
			String next = rs.split(" ")[1];
			//System.out.println("picked words:" + rs);
			if(next.equals("</s>")){  //as long as it's not </s>, keep doing
				re = re + " " + next;
				break;
			}
			else{
				re = re + " " + next;
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
//		String s = "<s> I saw him i saw her It saw I Saw them I . </s>";
//		bigram a = new bigram();
//		//System.out.println(a.generate(s));
//		String given = "They are him";
//		System.out.println("given word:" + a.generate(given,s));
//	}

}
