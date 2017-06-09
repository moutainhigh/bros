package bros.provider.login;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class Provider {
	@SuppressWarnings("unused")
	private static final  Logger logger = LoggerFactory.getLogger(Provider.class);
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		new FileSystemXmlApplicationContext("classpath:spring/spring-context.xml");

		System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
		
	}
}