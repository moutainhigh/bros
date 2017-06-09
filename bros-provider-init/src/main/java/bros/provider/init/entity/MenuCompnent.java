package bros.provider.init.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import bros.common.core.init.service.IActivitiDeployProcess;
import bros.common.core.init.service.IGetCacheConfigService;
import bros.common.core.init.service.IInitAppParService;
import bros.common.core.init.service.IInitAuthTemplateService;
import bros.common.core.init.service.IInitErrorCodeService;
import bros.common.core.init.service.IInitFormatsService;
import bros.common.core.init.service.IInitPropertiesService;
import bros.common.core.init.service.IInitValidationService;


/**
 * 
 * @ClassName: MenuCompnent 
 * @Description: 构造菜单之间的关系
 * @author 何鹏
 * @date 2016年5月16日 上午12:23:41 
 * @version 1.0
 */
public class MenuCompnent {
	/**
	 * 报文初始化工具类
	 */
	@Autowired
	private IInitFormatsService initFormatsService;
	/**
	 * 错误码初始化工具类
	 */
	@Autowired
	private IInitErrorCodeService initErrorCodeService;
	/**
	 * 数据转义初始化工具类
	 */
	@Autowired
	private IInitAppParService initAppParService;
	/**
	 * 获取缓存数据工具类
	 */
	@Autowired
	private IGetCacheConfigService getCacheConfigService;
	/**
	 * 初始化properties文件工具类
	 */
	@Autowired
	private IInitPropertiesService initPropertiesService;
	/**
	 * 初始化校验模板
	 */
	@Autowired
	private IInitValidationService initValidationService;
	/**
	 * 初始化工作流
	 */
	@Autowired
	private IActivitiDeployProcess activitiDeployProcess;
	/**
	 * 授权详情数据模板
	 */
	/*
	@Autowired
	private IInitAuthTemplateService initAuthTemplateService;
	*/
	/**
	 * 
	 * @Title: mainMenuConstructor 
	 * @Description: 菜单构建
	 * @return
	 */
	public Menu mainMenuConstructor(){
		//主菜单
		Menu mainMenu = new Menu(null,null,MenuShow.initMainMenu());
		//--------------------报文模板初始化-----------------------------------------
		//报文初始化菜单
		Menu formatMenu = new Menu(mainMenu,null,MenuShow.initFormatMenu());
	    formatMenu.setObj(initFormatsService);
		//初始化单个报文模板输入提示
		Menu formatOneMenu = new Menu(formatMenu, null, MenuShow.initFormatOneMenu());
		formatOneMenu.setObj(initFormatsService);
		formatOneMenu.setMethodName("initOneFormats");
		//初始化多个报文模板输入提示
		Menu formatManyMenu = new Menu(formatMenu,null,MenuShow.initFormatManyMenu());
		formatManyMenu.setObj(initFormatsService);
		formatManyMenu.setMethodName("initManyFormats");
		
		List<Menu> formatlist = new ArrayList<Menu>();
		formatlist.add(0, null);
		formatlist.add(1, null);
		formatlist.add(2, null);
		formatlist.add(3, formatOneMenu);
		formatlist.add(4,formatManyMenu);
		formatMenu.setNextMenuList(formatlist);
		
		//----------------------错误码初始化---------------------------------------
		//初始化错误码菜单
		Menu errorCodeMenu = new Menu(mainMenu,null,MenuShow.initErrorCodeMenu());
		errorCodeMenu.setObj(initErrorCodeService);
		//初始化单个错误码输入提示
		Menu errorCodeOneMenu = new Menu(errorCodeMenu, null, MenuShow.initErrorCodeOneMenu());
		errorCodeOneMenu.setObj(initErrorCodeService);
		errorCodeOneMenu.setMethodName("initOneErrorCode");
		//初始化多个错误码输入提示
		Menu errorCodeManyMenu = new Menu(errorCodeMenu,null,MenuShow.initErrorCodeManyMenu());
		errorCodeManyMenu.setObj(initErrorCodeService);
		errorCodeManyMenu.setMethodName("initManyErrorCode");
		
		List<Menu> errorCodelist = new ArrayList<Menu>();
		errorCodelist.add(0, null);
		errorCodelist.add(1, null);
		errorCodelist.add(2, null);
		errorCodelist.add(3, errorCodeOneMenu);
		errorCodelist.add(4,errorCodeManyMenu);
		errorCodeMenu.setNextMenuList(errorCodelist);
		//-----------------------转义数据初始化--------------------------------------
		//初始化转义菜单
		Menu appParMenu = new Menu(mainMenu,null,MenuShow.initAppParMenu());
		appParMenu.setObj(initAppParService);
		//初始化单个转义菜单输入提示
		Menu appParOneMenu = new Menu(appParMenu, null, MenuShow.initAppParOneMenu());
		appParOneMenu.setObj(initAppParService);
		appParOneMenu.setMethodName("initOneAppPar");
		//初始化多个转义菜单输入提示
		Menu appParManyMenu = new Menu(appParMenu,null,MenuShow.initAppParManyMenu());
		appParManyMenu.setObj(initAppParService);
		appParManyMenu.setMethodName("initManyAppPar");
		
		List<Menu> appParlist = new ArrayList<Menu>();
		appParlist.add(0, null);
		appParlist.add(1, null);
		appParlist.add(2, null);
		appParlist.add(3, appParOneMenu);
		appParlist.add(4,appParManyMenu);
		appParMenu.setNextMenuList(appParlist);
		//----------------------Properties文件初始化---------------------------------------
		//初始化Properties文件数据菜单
		Menu propertiesMenu = new Menu(mainMenu, null, MenuShow.initPropertiesMenu());
		propertiesMenu.setObj(initPropertiesService);
		//初始化单个properties文件菜单输入提示
		Menu propertiesOneMenu = new Menu(propertiesMenu, null, MenuShow.initPropertiesOneMenu());
		propertiesOneMenu.setObj(initPropertiesService);
		propertiesOneMenu.setMethodName("initOneProperties");
		//初始化多个properties文件菜单输入提示
		Menu proppertiesManyMenu = new Menu(propertiesMenu, null, MenuShow.initPropertiesManyMenu());		
		proppertiesManyMenu.setObj(initPropertiesService);
		proppertiesManyMenu.setMethodName("initManyProperties");
		
		List<Menu> proppertieslist = new ArrayList<Menu>();
		proppertieslist.add(0, null);
		proppertieslist.add(1, null);
		proppertieslist.add(2, null);
		proppertieslist.add(3, propertiesOneMenu);
		proppertieslist.add(4,proppertiesManyMenu);
		propertiesMenu.setNextMenuList(proppertieslist);
		//-----------------------------校验模板初始化--------------------------------
		//初始化校验模板文件数据菜单
		Menu validationMenu = new Menu(mainMenu, null, MenuShow.initValidationMenu());
		validationMenu.setObj(initValidationService);
		//初始化单个校验模板文件菜单输入提示
		Menu validationOneMenu = new Menu(validationMenu, null, MenuShow.initValidationOneMenu());
		validationOneMenu.setObj(initValidationService);
		validationOneMenu.setMethodName("initOneValidation");
		//初始化多个校验模板文件菜单输入提示
		Menu validationManyMenu = new Menu(validationMenu, null, MenuShow.initValidationManyMenu());		
		validationManyMenu.setObj(initValidationService);
		validationManyMenu.setMethodName("initManyValidation");
		
		List<Menu> validationlist = new ArrayList<Menu>();
		validationlist.add(0, null);
		validationlist.add(1, null);
		validationlist.add(2, null);
		validationlist.add(3, validationOneMenu);
		validationlist.add(4,validationManyMenu);
		validationMenu.setNextMenuList(validationlist);
		//------------------------------工作流初始化-------------------------------
		//初始化工作流
		Menu activitiMenu = new Menu(mainMenu, null, MenuShow.initActivitiMenu());
		activitiMenu.setObj(activitiDeployProcess);
		
		//初始化工作流zip文件
		Menu activitiDeployZipMenu = new Menu(activitiMenu, null, MenuShow.initActivitiProcessZipMenu());
		activitiDeployZipMenu.setObj(activitiDeployProcess);
		activitiDeployZipMenu.setMethodName("deployProcessByZip");
		
		//初始化多个工作流程
		Menu activitiDeployManyMenu = new Menu(activitiMenu, null, MenuShow.initActivitiManyProcessMenu());		
		activitiDeployManyMenu.setObj(activitiDeployProcess);
		activitiDeployManyMenu.setMethodName("deployManyProcess");
		//初始化单个工作流程
		Menu activitiDeployOneMenu = new Menu(activitiMenu, null, MenuShow.initActivitiOneProcessMenu());		
		activitiDeployOneMenu.setObj(activitiDeployProcess);
		activitiDeployOneMenu.setMethodName("deployOneProcess");
		
		List<Menu> activitilist = new ArrayList<Menu>();
		activitilist.add(0, null);
		activitilist.add(1, null);
		activitilist.add(2, null);
		activitilist.add(3, activitiDeployOneMenu);
		activitilist.add(4,activitiDeployManyMenu);
		activitilist.add(5,activitiDeployZipMenu);
		activitiMenu.setNextMenuList(activitilist);
		/*
		//------------------------授权详情模板初始化-------------------------------------
		//初始化授权详情数据模板文件数据菜单
		Menu authTemplateMenu = new Menu(mainMenu, null, MenuShow.initAuthTemplateMenu());
		authTemplateMenu.setObj(initAuthTemplateService);
		//初始化单个授权详情数据模板菜单输入提示
		Menu authTemplateOneMenu = new Menu(validationMenu, null, MenuShow.initAuthTemplateOneMenu());
		authTemplateOneMenu.setObj(initAuthTemplateService);
		authTemplateOneMenu.setMethodName("initOneAuthTemplate");
		//初始化多个授权详情数据模板菜单输入提示
		Menu authTemplateManyMenu = new Menu(validationMenu, null, MenuShow.initAuthTemplateManyMenu());		
		authTemplateManyMenu.setObj(initAuthTemplateService);
		authTemplateManyMenu.setMethodName("initManyAuthTemplate");
		
		List<Menu> authTemplatelist = new ArrayList<Menu>();
		authTemplatelist.add(0, null);
		authTemplatelist.add(1, null);
		authTemplatelist.add(2, null);
		authTemplatelist.add(3, authTemplateOneMenu);
		authTemplatelist.add(4,authTemplateManyMenu);
		authTemplateMenu.setNextMenuList(authTemplatelist);
		*/
		//-----------------------获取缓存数据--------------------------------------
		//获取缓存数据菜单                             每添加一个菜单都需要相应的添加服务
		Menu cacheDataMenu = new Menu(mainMenu,null,MenuShow.getInitCacheDataMenu());
		//获取报文缓存输入乡
		Menu cacheDataFormatMenu = new Menu(cacheDataMenu, null, MenuShow.getFormatsInitCacheDataMenu());
		cacheDataFormatMenu.setObj(getCacheConfigService);
		cacheDataFormatMenu.setMethodName("getCacheConfigFormats");
		//获取错误码缓存输入项
		Menu cacheDataErrorCodeMenu = new Menu(cacheDataMenu, null, MenuShow.getErrorCodeInitCacheDataMenu());
		cacheDataErrorCodeMenu.setObj(getCacheConfigService);
		cacheDataErrorCodeMenu.setMethodName("getCacheConfigErrorCode");
		//获取转移缓存输入项
		Menu cacheDataAppParMenu = new Menu(cacheDataMenu, null, MenuShow.getAppParInitCacheDataMenu());
		cacheDataAppParMenu.setObj(getCacheConfigService);
		cacheDataAppParMenu.setMethodName("getCacheConfigAppPar");
		//获取properties缓存输入项
		Menu cacheDataPropertiesMenu = new Menu(cacheDataMenu, null, MenuShow.getPropertiesInitCacheDataMenu());
		cacheDataPropertiesMenu.setObj(getCacheConfigService);
		cacheDataPropertiesMenu.setMethodName("getCacheConfigProperties");
		//获取校验模板缓存输入项
		Menu cacheDataValidationMenu = new Menu(cacheDataMenu, null, MenuShow.getValidationInitCacheDataMenu());
		cacheDataValidationMenu.setObj(getCacheConfigService);
		cacheDataValidationMenu.setMethodName("getCacheConfigValidation");
		
		//获取校验模板缓存输入项
		Menu dbDataActivitiMenu = new Menu(cacheDataMenu, null, MenuShow.getDbActivitiMenu());
		dbDataActivitiMenu.setObj(getCacheConfigService);
		dbDataActivitiMenu.setMethodName("getDbActivitiDeployProcess");
		
		//获取授权详情数据模板缓存输入项
		Menu authTemplateGetMenu = new Menu(cacheDataMenu, null, MenuShow.getAuthTemplateMenu());
		authTemplateGetMenu.setObj(getCacheConfigService);
		authTemplateGetMenu.setMethodName("getCacheConfigAuthTemplate");
		
		List<Menu> cacheDataList = new ArrayList<Menu>();
		cacheDataList.add(0, null);
		cacheDataList.add(1, null);
		cacheDataList.add(2, cacheDataFormatMenu);
		cacheDataList.add(3, cacheDataErrorCodeMenu);
		cacheDataList.add(4, cacheDataAppParMenu);
		cacheDataList.add(5, cacheDataPropertiesMenu);
		cacheDataList.add(6, cacheDataValidationMenu);
		cacheDataList.add(7, dbDataActivitiMenu);
		cacheDataList.add(8, authTemplateGetMenu);
		cacheDataMenu.setNextMenuList(cacheDataList);
		//-------------------------------------------------------------

		//构建菜单关联关系       明确存放顺序跟页面展示的顺序一样
		List<Menu> mainlist = new ArrayList<Menu>();
		mainlist.add(0, null);//退出
		mainlist.add(1,null);//返回上一级
		mainlist.add(2,null);//初始化所有配置
		mainlist.add(3,formatMenu);//初始化报文
		mainlist.add(4,errorCodeMenu);//初始化错误码
		mainlist.add(5,appParMenu);//初始化转义数据
		mainlist.add(6,propertiesMenu);//初始properties文件
		mainlist.add(7,cacheDataMenu);//初始化获取缓存数据
		mainlist.add(8,validationMenu);//初始化校验模板文件
		mainlist.add(9,activitiMenu);//初始化工作流
		//mainlist.add(10,authTemplateMenu);//授权详情数据
		mainMenu.setNextMenuList(mainlist);
		return mainMenu;
	}
}
