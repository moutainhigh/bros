package bros.network.init;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bros.common.core.exception.ServiceException;
import bros.common.core.util.ValidateUtil;
import bros.network.init.control.ProcessControl;
import bros.network.init.entity.Menu;
import bros.network.init.entity.MenuCompnent;
import bros.network.init.tools.MenuUtil;
/**
 * 
 * @ClassName: Main 
 * @Description: 初始化入口
 * @author 何鹏
 * @date 2016年5月16日 下午4:40:10 
 * @version 1.0
 */
public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	/**
	 * 当前菜单
	 */
    private static Menu currencyMenu;
    
    private ApplicationContext context = null;
    private static Main instance = null;
    private Main(){}
	public static synchronized Main getInstance(){
		if(instance == null){
			instance = new Main();
		}
		return instance;
	}
	
	public static ApplicationContext getContext(){
		Main instance = getInstance();
		return instance.context;
	}
    
	/**
	 * 
	 * @Title: init 
	 * @Description: 初始化spring容器
	 */
	public void init(){
		context = new FileSystemXmlApplicationContext("classpath:spring/spring-context.xml");
	}
	
	public static void main(String[] args)  throws Exception{
		try{
				Main instance = getInstance();
				instance.init();//初始化spring容器
				
				//构建所有的菜单
				ApplicationContext contextTemp = Main.getContext();
				//流程控制器
				MenuCompnent menuCompnent = (MenuCompnent) contextTemp.getBean("menuCompnent");
				Menu menu = menuCompnent.mainMenuConstructor();
				
				
				currencyMenu = menu;
				MenuUtil.showCurrencyMenu(currencyMenu);
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String line = "";
				while((line = br.readLine()) != null){
					//在最顶级菜单，输入退出和上一级表示直接退出程序
					if(("0".equals(line.trim())) || (currencyMenu.getTopMenu() == null && "1".equals(line.trim()))){
						break;
					}else{
						try{
							ProcessControl.process(currencyMenu,line);
						}catch(ServiceException ex){
							logger.info("错误码："+ex.getErrorCode()+"，错误信息："+ex.getErrorMsg());
							logger.info("初始化异常",ex);
							if(currencyMenu.getObj() != null && !ValidateUtil.isEmpty(currencyMenu.getMethodName())){
								//返回上一级
								MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
							}else if(currencyMenu.getObj() != null && ValidateUtil.isEmpty(currencyMenu.getMethodName())){
								MenuUtil.showCurrencyMenu(currencyMenu);
							}else if(currencyMenu.getTopMenu() == null){//返回当前级别
								MenuUtil.showCurrencyMenu(currencyMenu);
							}
						}catch(Exception e){
							logger.info("操作异常",e);
							if(currencyMenu.getObj() != null && !ValidateUtil.isEmpty(currencyMenu.getMethodName())){
								//返回上一级
								MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
							}else if(currencyMenu.getObj() != null && ValidateUtil.isEmpty(currencyMenu.getMethodName())){
								MenuUtil.showCurrencyMenu(currencyMenu);
							}else if(currencyMenu.getTopMenu() == null){//返回当前级别
								MenuUtil.showCurrencyMenu(currencyMenu);
							}
							
							
						}
					}
				}
				logger.info("-------------初始化结束");
		}catch(Exception e){
			logger.info("-------------初始化异常",e);
		}
	}
	
	public static Menu getCurrencyMenu() {
		return currencyMenu;
	}
	
	public static void setCurrencyMenu(Menu currencyMenu) {
		Main.currencyMenu = currencyMenu;
	}
	
}
