package cs4740p1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class readfile {

	public String txt2String(File file) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = br.readLine()) != null) {
				result.append(System.lineSeparator() + s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
    
	public String[] FilePathGet() {
		File file = new File("C:/Cornell/cs4740/project1/data_corrected/classification task/atheism/train_docs/");
		String filepath[];
		filepath = file.list();
//		for(String s : filepath){
//			System.out.println("path:" + s);
//		}
		return filepath;
	}
	
	public String preprocess(){
        String re = "";
		String filepath[] = FilePathGet();
		for (int k = 0; k < filepath.length; k++) {
			File file = new File("C:/Cornell/cs4740/project1/data_corrected/classification task/atheism/train_docs/" + filepath[k]);
			re = re + " " + txt2String(file);
			//System.out.println(re);
		}
		String[] ssplit = re.split(" ");
		String afterpre = "";
		for(String s: ssplit){
			
			if (s.length() == 0)  s = " ";
			if ( !Character.isLetterOrDigit(s.charAt(0)) 
					&& !s.equals(",") && !s.equals("?")
					&& !s.equals(".") && !s.equals("...") && !s.equals(":")
					&& !s.equals("(") && !s.equals(")") && !s.equals("!")
				    && !s.equals(";"))
				s = " ";
			if (s.contains("@"))
			{
				
			  	s = " ";
			}
			if(s.equals(".") || s.equals("?") || s.equals("...") || s.equals("!"))
			{				
				s += " </S> <S> ";				
			}
			afterpre = afterpre + " " + s;
		}
		afterpre = afterpre.replaceAll("( )+", " ");
		//System.out.println(afterpre);
		
		return afterpre;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readfile a = new readfile();
		String corpus = a.preprocess();
		unigram b = new unigram();		
		System.out.println("unimodel:" + b.generate(corpus));
		bigram c = new bigram();
		System.out.println("bimodel:" + c.generate(corpus));
		String given = "They are";
		System.out.println("seeding unimodel:" + b.generate(given, corpus));
		System.out.println("seeding bimodel:" + c.generate(given, corpus));
		
	}

}
