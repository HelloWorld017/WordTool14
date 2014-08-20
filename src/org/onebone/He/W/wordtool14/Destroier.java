package org.onebone.He.W.wordtool14;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Destroier {
	public static String delete(File f, boolean less, boolean less_less, boolean bracket, boolean number_annotation, boolean hanja, boolean except_eng_num_kor, String regex, String string) throws IOException{
	      FileInputStream fis = new FileInputStream(f);
	      InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
	      BufferedReader br = new BufferedReader(isr);
	      StringBuilder sb = new StringBuilder();
	      String b;
	      while((b = br.readLine()) != null){
	         sb.append(b);
	      }
	      br.close();
	      isr.close();
	      fis.close();
	      return delete_calc(sb.toString(), less, less_less, bracket, number_annotation, hanja, except_eng_num_kor, regex, string);
	   }
		public static String delete(String s, boolean less, boolean less_less, boolean bracket, boolean number_annotation, boolean hanja, boolean except_eng_num_kor, String regex, String string){
		      
		      return delete_calc(s, less, less_less, bracket, number_annotation, hanja, except_eng_num_kor, regex, string);
		      
		   }
	public static boolean[] checkCRNF(String s){
		boolean n = false;
		boolean r = false;
		if(s.contains("\n")){
			n = true;
		}
		if(s.contains("\r")){
			r = true;
		}
		boolean[] bt = {n,r};
		return bt;
	}
	
	public static String removeNewLines(String str){
		String[] strs = str.split("\n");
		String ret = "";
		for(String s : strs){
			if(s.trim().equals("") == false){
				ret += s+"\n";
			}
		}
		ret = ret.replaceAll("[(][ ]*[)]", "");
		
		return ret;
	}
	
	private static String delete_calc(String s, boolean less, boolean less_less, boolean bracket, boolean number_annotation, boolean hanja, boolean except_eng_num_kor, String customRegex, String customString){
		if(less){
			s = s.replaceAll("<.*>", "");
		}
		if(less_less){
			s = s.replaceAll("\u300A|\u300B", "");
		}
		if(bracket){
			Pattern pattern = Pattern.compile("\\(.*\\)");
			Matcher matcher = pattern.matcher(s);
			StringBuilder builder = new StringBuilder(s);
			int count = 0;
			while(matcher.find()){
				int location = matcher.start();
				
				int start = location;
				count = 0;
				while(true){
					if(location >= builder.length()) break;
					char character = builder.charAt(location);
					if(character == '('){
						count++;
					}else if(character == ')'){
						if(--count <= 0){
							builder.delete(start, location + 1);
							break;
						}
					}
					location++;
				}
				matcher = pattern.matcher(builder.toString());
			}
			s = builder.toString();
		}
		if(number_annotation){
			s = s.replaceAll("\\[[0-9]+\\]", "");
		}
		if(hanja){
	         s = s.replaceAll("(\\(|, |)[\u3400-\u9FB0\u4E00-\u9FFF𠀀-𪛖](,|\\)| )*", "");
		}
		if(except_eng_num_kor){
			s = s.replaceAll("(\\(|)[^\u3131-\u318Er\uAC00-\uD7A3\u0021-\u007E\u00A1-\u00BF\u02B0-\u037E ,!.;:\r\n\t](\\)|)", "");
		}
		
		if(customRegex.equals("") == false){
			String[] splits = customRegex.split("\n");
			for(String split : splits){
				s = s.replaceAll(split, "");
			}
		}
		
		if(customString.equals("") == false){
			String[] splits = customString.split("\n");
			for(String split : splits){
				s = s.replaceAll(split, "");
			}
		}
		
		return s;
	}
}

/*
 * package org.onebone.He.W.wordtool14;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Destroier {
   public static String delete(File f, boolean less, boolean less_less, boolean bracket, boolean number_annotation, boolean hanja, boolean except_eng_num_kor) throws IOException{
      FileInputStream fis = new FileInputStream(f);
      InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
      BufferedReader br = new BufferedReader(isr);
      StringBuilder sb = new StringBuilder();
      String b;
      while((b = br.readLine()) != null){
         sb.append(b);
      }
      br.close();
      isr.close();
      fis.close();
      return delete_calc(sb.toString(), less, less_less, bracket, number_annotation, hanja, except_eng_num_kor);
   }
   public static String delete(String s, boolean less, boolean less_less, boolean bracket, boolean number_annotation, boolean hanja, boolean except_eng_num_kor){
      
      return delete_calc(s, less, less_less, bracket, number_annotation, hanja, except_eng_num_kor);
      
   }
   public static boolean[] checkCRNF(String s){
      boolean n = false;
      boolean r = false;
      if(s.contains("\n")){
         n = true;
      }
      if(s.contains("\r")){
         r = true;
      }
      boolean[] bt = {n,r};
      return bt;
   }
   
   public static String removeNewLines(String str){
      String[] strs = str.split("\n");
      String ret = "";
      for(String s : strs){
         if(s.trim().equals("") == false){
            ret += s+"\n";
         }
      }
      
      return ret;
   }
   
   private static String delete_calc(String s, boolean less, boolean less_less, boolean bracket, boolean number_annotation, boolean hanja, boolean except_eng_num_kor){
      if(less){
         s = s.replaceAll("<.*>", "");
      }
      if(less_less){
         s = s.replaceAll("\\u300A|\\u300B", "");
      }
      if(bracket){
         s = s.replaceAll("[(].*[)]", "");
      }
      if(number_annotation){
         s = s.replaceAll("\\[[0-9]+\\]", "");
      }
      if(hanja){
         s = s.replaceAll("[(][\u3400-\u9FB0𠀀-𪛖]*[)]", "");
         s = s.replaceAll("[\u3400-\u9FB0𠀀-𪛖]", "");
         //s = s.replaceAll("[\u3400-\u9FB0\u20000-\u2A6D6]", "");
         s.replaceAll("[(][\\u4E00-\\u9FFF]", "");
      }
      if(except_eng_num_kor){
         s = s.replaceAll("[(][^\u3131-\u318E가-힣\u0021-\u007E\u00A1-\u00BF\u02B0-\u037E]*[)]","");
         s = s.replaceAll("[^\u3131-\u318Er가-힣\u0021-\u007E\u00A1-\u00BF\u02B0-\u037E]", "");
               
      }
      return s;
   }
}*/
