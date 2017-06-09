package bros.provider.financial;


import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bros.common.core.format.factory.JsonFormatConfigFactory;
import bros.common.core.util.XmlFileNameFilterUtil;
import bros.common.core.validation.factory.ValidationFactory;


public class Provider {
	private static final  Logger logger = LoggerFactory.getLogger(Provider.class);
	@SuppressWarnings({ "resource", "unused" })
	public static void main(String[] args) throws Exception {
		
		//ApplicationContext context = new FileSystemXmlApplicationContext("D:\\workspace\\channelWorkspace\\bros-provider-financial\\target\\classes\\spring\\spring-context.xml");
		ApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring/spring-context.xml");

		//初始化报文
		//initFormat();
		//初始化校验文件
		//initValidation();
		System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
		
	}
	
	/*
	public static void initFormat() {
		   logger.info("Initialize fixedLen formats......");
		   
		   //对定长格式报文模板文件路径进行转换处理
		   //String filePath = "D://workspace//channelWorkspace//bros-common-core//src//main//webapp//WEB-INF//formats//json//";
		   
		   String filePath = "F:/workspace/channel1/bros-provider-config-format/target/classes/format/json/";
		   
		   
		   filePath = filePath.replace('\\', '/');		
		   int idx = filePath.lastIndexOf( '/');
		   if( idx != -1 ){
			   filePath = filePath.substring(0,  idx + 1);
		   }
		   
		   try{
			   
			   File formatsDoc = new File(filePath);
			   String[] listFileName = formatsDoc.list(new XmlFileNameFilterUtil());
			   if(null==listFileName || listFileName.length==0){
					throw new Exception("There is no fixedLen format's file...");
			   }
			   File[] fileList = new File[listFileName.length];
			   for(int i=0; i<listFileName.length; i++){
				   if(listFileName[i].toLowerCase().endsWith(".xml")){
					   logger.info( "loading fixedLen format file [" + listFileName[i] + "]...");
					   fileList[i] = new File(filePath + listFileName[i]);
				   }
			   }	   
			   JsonFormatConfigFactory.initFormat(fileList);
			   
			   logger.info("initialize from [" + filePath + "]...");	
		   }catch(Exception e){
			   logger.error("Failed to initialize from [" + filePath + "]..." , e);	   
		   }
	}
	
	public static void initValidation() {
		   logger.info("Initialize fixedLen formats......");
		   
		   String filePath = "F:/workspace/channel1/bros-provider-config-validation/target/classes/validation/";
		   
		   
		   filePath = filePath.replace('\\', '/');		
		   int idx = filePath.lastIndexOf( '/');
		   if( idx != -1 ){
			   filePath = filePath.substring(0,  idx + 1);
		   }
		   
		   try{
			   
			   File formatsDoc = new File(filePath);
			   String[] listFileName = formatsDoc.list(new XmlFileNameFilterUtil());
			   if(null==listFileName || listFileName.length==0){
					throw new Exception("There is no  validation's file...");
			   }
			   File[] fileList = new File[listFileName.length];
			   for(int i=0; i<listFileName.length; i++){
				   if(listFileName[i].toLowerCase().endsWith(".xml")){
					   logger.info( "loading validation file [" + listFileName[i] + "]...");
					   fileList[i] = new File(filePath + listFileName[i]);
				   }
			   }	   
			   
			   ValidationFactory.initFormat(fileList);
			   
			   logger.info("initialize from [" + filePath + "]...");	
		   }catch(Exception e){
			   logger.error("Failed to initialize from [" + filePath + "]..." , e);	   
		   }
		}
		*/
}