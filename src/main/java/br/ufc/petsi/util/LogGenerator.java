package br.ufc.petsi.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogGenerator {
	
	private static LogGenerator instance;
	private FileWriter fwriter;
	private String fileName;
	private BufferedWriter bw;
	
	private LogGenerator(){
		
	}
	
	private void openFile(){
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		fileName = System.getProperty("user.dir")+"/siac_log_"+format.format(today)+".txt";
		System.out.println(fileName);
		try {
			File file = new File(fileName);
			if(!file.exists()){
				file.createNewFile();
			}
			fwriter = new FileWriter(file, true);
			bw = new BufferedWriter(fwriter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static LogGenerator getInstance(){
		if(instance == null)
			instance = new LogGenerator();
		return instance;
	}
		
	public void log(Exception ex, String description){
		createLog(ex.toString(), description);
	}
	
	public void log(String ex, String description){
		createLog(ex, description);
	}
	
	private void createLog(String ex, String description){
//		openFile();
//		try{
//			System.out.println(ex+" "+description);
//			Date now = new Date();
//			SimpleDateFormat format = new SimpleDateFormat("H:m:s");
//			bw.write("----------------- "+format.format(now)+" -----------------");
//			bw.newLine();
//			bw.write("Exception: "+ex.toString());
//			bw.newLine();
//			bw.write("Description: "+description);
//			bw.newLine();
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			try{
//				if(bw != null)
//					bw.close();
//				if(fwriter != null)
//					fwriter.close();
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
	}

}
