package com.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
 *用于按行读入文件，filename为文件的路径+名称 
 */
public class ReadFileLine {

	public ReadFileLine() {
		super();
	}

	
	public List<String> readFile(String filename){

		List<String> list = new ArrayList<String>();
		BufferedReader r = null;
		try{
			InputStream in = ReadFileLine.class.getResourceAsStream(filename);
			r = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			//InputStreamReader in = new InputStreamReader(new FileInputStream(filename),"UTF-8");
			//r=new BufferedReader(in);  
			String line = null;
			while((line=r.readLine())!=null){
				//System.out.println(line);
				list.add(line);
			}
			in.close();
			r.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
}
