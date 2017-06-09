package bros.provider.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bros.common.core.exception.ServiceException;
import bros.common.core.util.FileReadPathUtil;
import bros.common.core.util.FileUtil;
import bros.common.core.util.ValidateUtil;
import bros.provider.init.control.ProcessControl;
import bros.provider.init.entity.Menu;
import bros.provider.init.entity.MenuCompnent;
import bros.provider.init.tools.MenuUtil;
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
	
	private static void listDirectory(File path, List<File> myfile, String fileType)
	{
		if (path.exists())
			if (path.isFile()) {
				if (ValidateUtil.isEmpty(fileType)) {
					myfile.add(path);
				} else {
					String fileName = path.getName();
					if ((!(ValidateUtil.isEmpty(fileName))) && (fileName.indexOf(".") != -1)) {
						String fileTypeSub = fileName.substring(fileName.lastIndexOf("."));
						if (fileTypeSub.equalsIgnoreCase(fileType))
							myfile.add(path);
	           }
	         }
	       }
	       else
	       {
	         File[] files = path.listFiles();
	         for (int i = 0; i < files.length; ++i)
	           listDirectory(files[i], myfile, fileType);
	       }
	   }
	
	public static void main(String[] args)  throws Exception{
		try{
				Main instance = getInstance();
				instance.init();//初始化spring容器
				
//				String x="bros-provider-config-validation-1.0.0";
//				String type=".xml";
//				URL s=FileReadPathUtil.class.getResource(x);//
//				//FileReadPathUtil.judgeIsJarOrFileModel(packageName)
//				
//				List<File> listFileTemp = FileReadPathUtil.getListAllFile(x, type);
//				
//				String path=s.getPath();
//				listFileTemp = FileUtil.getFormatFile(path, type);
//				listDirectory(new File(path),listFileTemp, type);
//				
//				String path = s.getPath();
//				listFileTemp = FileUtil.getXmlFile("E:/分布式/workspace/bros-provider-init/target/classes/bros-provider-config-validation-1.0.0");
				//构建所有的菜单
				ApplicationContext contextTemp = Main.getContext();
				//流程控制器
				MenuCompnent menuCompnent = (MenuCompnent) contextTemp.getBean("menuCompnent");
				Menu menu = menuCompnent.mainMenuConstructor();
				
				
				currencyMenu = menu;
				MenuUtil.showCurrencyMenu(currencyMenu);
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String line = null;
				while((line = br.readLine()) != null){
					//在最顶级菜单，输入退出和上一级表示直接退出程序
					if(("0".equals(line.trim())) || (currencyMenu.getTopMenu() == null && "1".equals(line.trim()))){
						break;
					}else{
						try{
							ProcessControl.process(currencyMenu,line);
						}catch(ServiceException se){
							logger.info("错误码："+se.getErrorCode()+"，错误信息："+se.getErrorMsg(),se);
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
			logger.info("----------------初始化异常：",e);
		}
	}
	
	public static Menu getCurrencyMenu() {
		return currencyMenu;
	}
	
	public static void setCurrencyMenu(Menu currencyMenu) {
		Main.currencyMenu = currencyMenu;
	}
	
}
