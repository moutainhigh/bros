package bros.provider.custmanage;


import org.springframework.context.support.FileSystemXmlApplicationContext;


public class Provider {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		new FileSystemXmlApplicationContext("classpath:spring/spring-context.xml");
		
		System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
		
	}
}