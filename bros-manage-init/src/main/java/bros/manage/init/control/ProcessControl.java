package bros.manage.init.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bros.common.core.exception.ServiceException;
import bros.common.core.init.service.IActivitiDeployProcess;
import bros.common.core.init.service.IGetCacheConfigService;
import bros.common.core.init.service.IInitAllConfigService;
import bros.common.core.init.service.IInitAppParService;
import bros.common.core.init.service.IInitAuthTemplateService;
import bros.common.core.init.service.IInitErrorCodeService;
import bros.common.core.init.service.IInitFormatsService;
import bros.common.core.init.service.IInitPropertiesService;
import bros.common.core.init.service.IInitValidationService;
import bros.common.core.init.service.impl.ActivitiDeployProcessImpl;
import bros.common.core.init.service.impl.GetCacheConfigServiceImpl;
import bros.common.core.init.service.impl.InitAppParServiceImpl;
import bros.common.core.init.service.impl.InitAuthTemplateServiceImpl;
import bros.common.core.init.service.impl.InitErrorCodeServiceImpl;
import bros.common.core.init.service.impl.InitFormatsServiceImpl;
import bros.common.core.init.service.impl.InitPropertiesServiceImpl;
import bros.common.core.init.service.impl.InitValidationServiceImpl;
import bros.manage.init.Main;
import bros.manage.init.entity.Menu;
import bros.manage.init.tools.MenuUtil;

/**
 * 
 * @ClassName: ProcessControl 
 * @Description: 操作流程控制
 * @author 何鹏
 * @date 2016年5月16日 上午12:52:32 
 * @version 1.0
 */
public class ProcessControl {
	private static final Logger logger = LoggerFactory.getLogger(ProcessControl.class);
	/**
	 * 
	 * @Title: process 
	 * @Description: 流程流转
	 * @param currencyMenu   当前菜单
	 * @param input  输入值
	 * @throws ServiceException 
	 */
	public static void process(Menu currencyMenu,String input) throws ServiceException{
		Object obj = currencyMenu.getObj();
		String method =currencyMenu.getMethodName();
		
		if(obj == null || method == null){//无执行方法，只是菜单
			if(currencyMenu.getTopMenu() == null && "2".equals(input.trim())){//初始化所有配置
				IInitAllConfigService initAllConfigService = (IInitAllConfigService) Main.getContext().getBean("initAllConfigService");
				initAllConfigService.initAllConfigMethod();
				MenuUtil.setAndShowCurrencyMenu(currencyMenu);
			}else if(currencyMenu.getTopMenu() == null && ("3".equals(input.trim()) || 
					"4".equals(input.trim()) || "5".equals(input.trim()) ||"6".equals(input.trim()) || "7".equals(input.trim()) || "8".equals(input.trim()) 
					// ||"9".equals(input.trim()) || "10".equals(input.trim())
					)){//顶级目录
				currencyMenu = currencyMenu.getNextMenuList().get(Integer.parseInt(input));
				MenuUtil.setAndShowCurrencyMenu(currencyMenu);
			}else if(currencyMenu.getTopMenu() != null && "1".equals(input.trim())){//上一级
				currencyMenu = currencyMenu.getTopMenu();
				MenuUtil.setAndShowCurrencyMenu(currencyMenu);
			}else if(obj != null && "2".equals(input.trim())){//初始化当前操作的所有配置
				if(obj instanceof InitFormatsServiceImpl){
					IInitFormatsService initFormatsService = (IInitFormatsService) currencyMenu.getObj();
					initFormatsService.initAllFormats();
					MenuUtil.showCurrencyMenu(currencyMenu);
				}else if(obj instanceof InitErrorCodeServiceImpl){
					IInitErrorCodeService initErrorCodeService = (IInitErrorCodeService) obj;
					initErrorCodeService.initAllErrorCode();
					MenuUtil.showCurrencyMenu(currencyMenu);
				}else if(obj instanceof InitAppParServiceImpl){
					IInitAppParService initAppParService = (IInitAppParService) obj;
					initAppParService.initAllAppPar();
					MenuUtil.showCurrencyMenu(currencyMenu);
				}else if(obj instanceof InitPropertiesServiceImpl){
					IInitPropertiesService initPropertiesService = (IInitPropertiesService) obj;
					initPropertiesService.initAllPropertiesMethod();
					MenuUtil.showCurrencyMenu(currencyMenu);
				}else if(obj instanceof InitValidationServiceImpl){
					IInitValidationService initValidationService = (IInitValidationService) obj;
					initValidationService.initAllValidationMethod();
					MenuUtil.showCurrencyMenu(currencyMenu);
				}else if(obj instanceof InitAuthTemplateServiceImpl){
					IInitAuthTemplateService initAuthTemplateService = (IInitAuthTemplateService) obj;
					initAuthTemplateService.initAllAuthTemplateMethod();
				}else if(obj instanceof ActivitiDeployProcessImpl){
					IActivitiDeployProcess activitiDeployProcess = (IActivitiDeployProcess) obj;
					activitiDeployProcess.deployAllProcess();
				}
			}else if(currencyMenu.getTopMenu() != null && currencyMenu.getNextMenuList() != null){//顶级和子级都不等于空，显示当前输入数字的下一级输入项或者菜单
				int index = 0;
				try{
					index = Integer.parseInt(input);
					Menu currencyMenuIndex = currencyMenu.getNextMenuList().get(index);
					if(currencyMenuIndex != null){
						currencyMenu = currencyMenuIndex;
					}
				}catch(Exception e){
					logger.info("不支持的菜单选项",e);
				}
				MenuUtil.setAndShowCurrencyMenu(currencyMenu);
			}else if(currencyMenu.getTopMenu() == null){
				logger.info("不支持的菜单选项");
			}
		}else{//只考虑一个变量  只要配置了方法就执行方法
			
			if(obj instanceof InitFormatsServiceImpl){
				IInitFormatsService initFormatsService = (IInitFormatsService) currencyMenu.getObj();
				if("initOneFormats".equals(method)){
					initFormatsService.initOneFormats(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}else if("initManyFormats".equals(method)){
					initFormatsService.initManyFormats(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}
			}else if(obj instanceof InitErrorCodeServiceImpl){
				IInitErrorCodeService initErrorCodeService = (IInitErrorCodeService) obj;
				if("initOneErrorCode".equals(method)){
					initErrorCodeService.initOneErrorCode(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}else if("initManyErrorCode".equals(method)){
					initErrorCodeService.initManyErrorCode(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}
			}else if(obj instanceof InitAppParServiceImpl){
				IInitAppParService initAppParService = (IInitAppParService) obj;
				if("initOneAppPar".equals(method)){
					initAppParService.initOneAppPar(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}else if("initManyAppPar".equals(method)){
					initAppParService.initManyAppPar(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}
			}else if(obj instanceof GetCacheConfigServiceImpl){//从缓存中获取验证模板信息
				IGetCacheConfigService getCacheConfigService = (IGetCacheConfigService) obj;
				if("getCacheConfigFormats".equals(method)){
					logger.info(getCacheConfigService.getCacheConfigFormats(input).toString());
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}else if("getCacheConfigErrorCode".equals(method)){
					logger.info(getCacheConfigService.getCacheConfigErrorCode(input).toString());
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}else if("getCacheConfigAppPar".equals(method)){
					logger.info(getCacheConfigService.getCacheConfigAppPar(input).toString());
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}else if("getCacheConfigProperties".equals(method)){
					logger.info(getCacheConfigService.getCacheConfigProperties(input).toString());
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}else if("getCacheConfigValidation".equals(method)){
					logger.info(getCacheConfigService.getCacheConfigValidation(input).toString());
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}else if("getDbActivitiDeployProcess".equals(method)){
					getCacheConfigService.getDbActivitiDeployProcess(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}else if("getCacheConfigAuthTemplate".equals(method)){
					logger.info("授权缓存模板："+getCacheConfigService.getCacheConfigAuthTemplate(input));
					String fileNameCategroy = input+"categroy";//模板对应的转义字典名
					logger.info("授权缓存转义字典："+getCacheConfigService.getCacheConfigAuthTemplate(fileNameCategroy));
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}
			}else if(obj instanceof InitPropertiesServiceImpl){
				IInitPropertiesService initPropertiesService = (IInitPropertiesService) obj;
				if("initOneProperties".equals(method)){
					initPropertiesService.initOneProperties(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}else if("initManyProperties".equals(method)){
					initPropertiesService.initManyProperties(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}
			}else if(obj instanceof InitValidationServiceImpl){
				IInitValidationService initValidationService = (IInitValidationService) obj;
				if("initOneValidation".equals(method)){
					initValidationService.initOneValidation(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}else if("initManyValidation".equals(method)){
					initValidationService.initManyValidation(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}
			}else if(obj instanceof ActivitiDeployProcessImpl){
				IActivitiDeployProcess activitiDeployProcess = (IActivitiDeployProcess) obj;
				if("deployProcessByZip".equals(method)){
					activitiDeployProcess.deployProcessByZip(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}else if("deployOneProcess".equals(method)){
					activitiDeployProcess.deployOneProcess(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}else if("deployManyProcess".equals(method)){
					activitiDeployProcess.deployManyProcess(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}
			}else if(obj instanceof InitAuthTemplateServiceImpl){
				IInitAuthTemplateService initAuthTemplateService = (IInitAuthTemplateService) obj;
				if("initOneAuthTemplate".equals(method)){
					initAuthTemplateService.initOneAuthTemplate(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}else if("initManyAuthTemplate".equals(method)){
					initAuthTemplateService.initManyAuthTemplate(input);
					MenuUtil.setAndShowTopCurrencyMenu(currencyMenu);
				}
			}
			
		}
	}
}
