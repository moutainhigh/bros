package bros.unified.receive.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bros.common.core.comm.business.detail.IBusinessProcessService;
import bros.common.core.comm.format.ISeverFormatService;
import bros.common.core.comm.netty.config.AppConfig;
import bros.common.core.comm.netty.core.BootServer;
import bros.common.core.comm.netty.handler.FixedLengthServerHandlerInitializer;
import bros.common.core.comm.netty.handler.HttpServerHandlerInitializer;
/**
 * 
 * @ClassName: Main 
 * @Description: 统一接入报文处理
 * @author 何鹏
 * @date 2016年6月30日 下午4:52:43 
 * @version 1.0
 */
public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		try {
			final ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
			//定长报文监听
			fixedLengthFormatStartListener(context);
			//http报文监听
			httpFormatInitStartListener(context);		
			
			//getFormatInformationInsertSql(context);  //sequence生成测试
			//getFormatInformationInsertSql(context,"80fb68c1-fce5-440d-85a3-9c392ba1ba83","All");
			//System.in.read();// 为保证服务一直开着，利用输入流的阻塞来模拟
		} catch (Exception e) {
			logger.error("统一接入启动失败", e);
		} 
	}
	
	/**
	 * 
	 * @Title: fixedLengthFormatStartListener 
	 * @Description: 定长报文启动监听
	 * @param context
	 */
	public static void fixedLengthFormatStartListener(final ApplicationContext context){
		new Thread(new Runnable() {
			@Override
			public void run() {
				AppConfig config = (AppConfig) context.getBean("fixedAppConfig");
				IBusinessProcessService businessProcessService = (IBusinessProcessService) context.getBean("businessProcessService");
				ISeverFormatService formatService = (ISeverFormatService) context.getBean("jsonFormatService");
				FixedLengthServerHandlerInitializer channelInitializer = new FixedLengthServerHandlerInitializer(context,businessProcessService,config,formatService);
				String poolName = "fixed-decode-worker-thread-pool";
				BootServer bootServer = new BootServer(config, context, channelInitializer, poolName);
				bootServer.start();
			}
		}).start();
	}
    
	/**
	 * 
	 * @Title: httpFormatInitStartListener 
	 * @Description: http报文启动监听
	 * @param context
	 */
	public static void httpFormatInitStartListener(final ApplicationContext context){
		new Thread(new Runnable() {
			@Override
			public void run() {
				AppConfig config = (AppConfig) context.getBean("httpAppConfig");//配置文件
				IBusinessProcessService businessProcessService = (IBusinessProcessService) context.getBean("businessProcessService");//业务处理器
				ISeverFormatService formatService = (ISeverFormatService) context.getBean("jsonFormatService");
				HttpServerHandlerInitializer channelInitializer = new HttpServerHandlerInitializer(context,businessProcessService,formatService,config);
				String poolName = "http-decode-worker-thread-pool";
				BootServer bootServer = new BootServer(config, context, channelInitializer, poolName);
				bootServer.start();
			}
		}).start();
	}
	
	/**
	 * 
	 * @Title: getFormatInformationInsertSql 
	 * @Description: 生成服务版本配置信息
	 * @param context
	 * @param legalId
	 * @param channel
	 */
	/*
	public static void getFormatInformationInsertSql(ApplicationContext context,String legalId,String channel){
		Map<String, ReferenceConfig> referenceConfigMap = context == null ? null  : BeanFactoryUtils.beansOfTypeIncludingAncestors(context, ReferenceConfig.class, false, false);
        if (referenceConfigMap != null && referenceConfigMap.size() > 0) {
            for (ReferenceConfig referenceConfig : referenceConfigMap.values()) {
            	
            	String id = referenceConfig.getId();
            	String[] str = id.split("_");
            	System.out.println("INSERT INTO bma_version_config (bvc_legalid, bvc_channelcode, bvc_servicename, bvc_version) VALUES ('"+legalId+"', '"+channel+"', '"+str[0]+"', '"+str[1]+"');");
            }            
        }
	}
	*/
	/*
	public static void getFormatInformationInsertSql(final ApplicationContext context){
		final long start = System.currentTimeMillis();
		 
		for(int i=0;i<1000;i++){
			final int k = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					int sceneCode = 1000;
					for(int j=0;j<10;j++){
						sceneCode = sceneCode+j;
						FlowGenerator flowGenerator = (FlowGenerator) context.getBean("flowGenerator");
						String flowSeq = "";
						
						try {
							flowSeq = flowGenerator.getGlobalSeqNo("2222", "9999", sceneCode+"", "02");
						} catch (ServiceException e) {
							e.printStackTrace();
						}
						
						System.out.println(k+"全局流水号["+j+"]:"+flowSeq);
						long end = System.currentTimeMillis();
				        System.out.println(end - start);
					}
					
					
				}
			}).start();
		}
	}
*/
}
