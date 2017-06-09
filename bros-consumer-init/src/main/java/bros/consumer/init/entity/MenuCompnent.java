package bros.consumer.init.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import bros.common.core.init.service.IGetCacheConfigService;
import bros.common.core.init.service.IInitAppParService;
import bros.common.core.init.service.IInitErrorCodeService;
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
	 * 初始化校验文件工具类
	 */
	@Autowired
	private IInitValidationService initValidationService;
	
	/**
	 * 
	 * @Title: mainMenuConstructor 
	 * @Description: 菜单构建
	 * @return
	 */
	public Menu mainMenuConstructor(){
		//主菜单
		Menu mainMenu = new Menu(null,null,MenuShow.initMainMenu());
		//-------------------------------------------------------------
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
		//-------------------------------------------------------------
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
		//-------------------------------------------------------------
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
		//-------------------------------------------------------------
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
		//-------------------------------------------------------------
		//获取缓存数据菜单                             每添加一个菜单都需要相应的添加服务
		Menu cacheDataMenu = new Menu(mainMenu,null,MenuShow.getInitCacheDataMenu());

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
		
		List<Menu> cacheDataList = new ArrayList<Menu>();
		cacheDataList.add(0, null);
		cacheDataList.add(1, null);
		cacheDataList.add(2, cacheDataValidationMenu);
		cacheDataList.add(3, cacheDataErrorCodeMenu);
		cacheDataList.add(4, cacheDataAppParMenu);
		cacheDataList.add(5, cacheDataPropertiesMenu);
		cacheDataMenu.setNextMenuList(cacheDataList);
		//-------------------------------------------------------------

		//构建菜单关联关系       明确存放顺序跟页面展示的顺序一样
		List<Menu> mainlist = new ArrayList<Menu>();
		mainlist.add(0, null);//退出
		mainlist.add(1,null);//返回上一级
		mainlist.add(2,null);//初始化所有配置
		mainlist.add(3,validationMenu);//初始化校验模板文件
		mainlist.add(4,errorCodeMenu);//初始化错误码
		mainlist.add(5,appParMenu);//初始化转义数据
		mainlist.add(6,propertiesMenu);//初始properties文件
		mainlist.add(7,cacheDataMenu);//初始化获取缓存数据
		mainMenu.setNextMenuList(mainlist);
		return mainMenu;
	}
}
