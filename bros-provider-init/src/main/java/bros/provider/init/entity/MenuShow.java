package bros.provider.init.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: MenuShow 
 * @Description: 菜单展示
 * @author 何鹏
 * @date 2016年5月16日 上午12:02:36 
 * @version 1.0
 */
public class MenuShow {
	public static final String head = "*********************************************************";
	public static final String title = "***                渠道集成平台系统参数初始化到缓存                                                  ***";
	public static final String choice = "请选择操作类型：";
	
	//---------------------主菜单----------------------------------------
	/**
	 * 
	 * @Title: initMainMenu 
	 * @Description: 主菜单展示
	 * @return
	 */
	public static List<String> initMainMenu(){
		List<String> list = new ArrayList<String>();
		list.add(head);
		list.add(title);
		list.add(head);
		list.add(choice);
		list.add("      0：退出                                                                 1：返回");
		list.add("      2：初始化所有配置（非zip形式的工作流）");
		list.add("      3：初始化报文配置");
		list.add("      4： 初始化错误码");
		list.add("      5： 初始化转义数据");	
		list.add("      6： 初始化properties数据文件");	
		list.add("      7： 获取缓存中的数据");	
		list.add("      8： 初始化校验模板");	
		list.add("      9： 初始化工作流的流程实例");	
		//list.add("      10： 初始化授权详情数据模板");	
		return list;
	}
	//------------------初始化报文菜单-------------------------------------------
	/**
	 * 
	 * @Title: initFormatMenu 
	 * @Description: 报文菜单展示
	 * @return
	 */
	public static List<String> initFormatMenu(){
		List<String> list = new ArrayList<String>();
		list.add(head);
		list.add(title);
		list.add(head);
		list.add(choice);
		list.add("      0：退出                                                                 1：返回");
		list.add("      2：初始化所有报文");
		list.add("      3：初始化单个报文（报文文件名不带后缀）");
		list.add("      4： 初始化多个报文（报文文件名不带后缀，通过|进行分割）");
		return list;
	}
	
	/**
	 * 
	 * @Title: initFormatOneMenu 
	 * @Description: 初始化单个报文模板输入提示
	 * @return
	 */
	public static List<String> initFormatOneMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入单个报文模板文件名（不带后缀）：");
		return list;
	}
	
	/**
	 * 
	 * @Title: initFormatManyMenu 
	 * @Description: 初始化多个报文模板输入提示
	 * @return
	 */
	public static List<String> initFormatManyMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入多个报文模板文件名（不带后缀，通过|进行分割）：");
		return list;
	}
	//--------------------初始化错误码菜单-----------------------------------------
	/**
	 * 
	 * @Title: initErrorCodeMenu 
	 * @Description: 错误码菜单展示
	 * @return
	 */
	public static List<String> initErrorCodeMenu(){
		List<String> list = new ArrayList<String>();
		list.add(head);
		list.add(title);
		list.add(head);
		list.add(choice);
		list.add("      0：退出                                                                 1：返回");
		list.add("      2：初始化所有错误码");
		list.add("      3：初始化单个错误码");
		list.add("      4： 初始化多个错误码（多个错误码通过|进行分割）");
		return list;
	}
	
	/**
	 * 
	 * @Title: initErrorCodeOneMenu 
	 * @Description: 初始化单个错误码名输入提示
	 * @return
	 */
	public static List<String> initErrorCodeOneMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入单个错误码名：");
		return list;
	}
	
	/**
	 * 
	 * @Title: initErrorCodeManyMenu 
	 * @Description: 初始化多个错误码名输入提示
	 * @return
	 */
	public static List<String> initErrorCodeManyMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入多个错误码名（通过|进行分割）：");
		return list;
	}
	//------------------初始化转义菜单-------------------------------------------
	/**
	 * 
	 * @Title: initAppParMenu 
	 * @Description: 转义菜单展示
	 * @return
	 */
	public static List<String> initAppParMenu(){
		List<String> list = new ArrayList<String>();
		list.add(head);
		list.add(title);
		list.add(head);
		list.add(choice);
		list.add("      0：退出                                                                 1：返回");
		list.add("      2：初始化所有转义数据");
		list.add("      3：初始化单个转义数据");
		list.add("      4： 初始化多个转义数据（多个转义数据通过|进行分割）");
		return list;
	}
	
	/**
	 * 
	 * @Title: initAppParOneMenu 
	 * @Description: 初始化单个转义数据输入提示
	 * @return
	 */
	public static List<String> initAppParOneMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入单个转义数据名：");
		return list;
	}
	
	/**
	 * 
	 * @Title: initAppParManyMenu 
	 * @Description: 初始化多个转义数据输入提示
	 * @return
	 */
	public static List<String> initAppParManyMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入多个转义数据名（数据通过|进行分割）：");
		return list;
	}
	//-----------------初始化properties菜单--------------------------------------------
	/**
	 * 
	 * @Title: initPropertiesMenu 
	 * @Description: properties菜单展示
	 * @return
	 */
	public static List<String> initPropertiesMenu(){
		List<String> list = new ArrayList<String>();
		list.add(head);
		list.add(title);
		list.add(head);
		list.add(choice);
		list.add("      0：退出                                                                 1：返回");
		list.add("      2：初始化所有properties文件");
		list.add("      3：初始化单个properties文件（properties文件名不带后缀）");
		list.add("      4： 初始化多个properties（properties文件名不带后缀，多个文件之间通过|进行分割）");
		return list;
	}
	/**
	 * 
	 * @Title: initPropertiesOneMenu 
	 * @Description: 初始化单个properties文件输入提示
	 * @return
	 */
	public static List<String> initPropertiesOneMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入单个properties文件名（不带后缀）：");
		return list;
	}
	
	/**
	 * 
	 * @Title: initPropertiesManyMenu 
	 * @Description: 初始化多个properties文件输入提示
	 * @return
	 */
	public static List<String> initPropertiesManyMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入多个properties文件名（不带后缀，通过|进行分割）：");
		return list;
	}
	
	//------------------初始化校验模板菜单-------------------------------------------
	
	/**
	 * 
	 * @Title: initValidationMenu 
	 * @Description:validation菜单展示
	 * @return
	 */
	public static List<String> initValidationMenu(){
		List<String> list = new ArrayList<String>();
		list.add(head);
		list.add(title);
		list.add(head);
		list.add(choice);
		list.add("      0：退出                                                                 1：返回");
		list.add("      2：初始化所有校验模板");
		list.add("      3：初始化单个校验模板文件名（校验模板文件名不带后缀）");
		list.add("      4： 初始化多个校验模板文件名（校验模板文件名不带后缀，多个文件之间通过|进行分割）");
		return list;
	}
	/**
	 * 
	 * @Title: initValidationOneMenu 
	 * @Description: 初始化单个validation文件输入提示
	 * @return
	 */
	public static List<String> initValidationOneMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入单个校验模板文件名（不带后缀）：");
		return list;
	}
	
	/**
	 * 
	 * @Title: initValidationManyMenu 
	 * @Description: 初始化多个validation文件输入提示
	 * @return
	 */
	public static List<String> initValidationManyMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入多个校验模板文件名（不带后缀，通过|进行分割）：");
		return list;
	}

	//-----------------初始化工作流菜单--------------------------------------------
	

	/**
	 * 
	 * @Title: initActivitiMenu 
	 * @Description:工作流菜单展示
	 * @return
	 */
	public static List<String> initActivitiMenu(){
		List<String> list = new ArrayList<String>();
		list.add(head);
		list.add(title);
		list.add(head);
		list.add(choice);
		list.add("      0：退出                                                                 1：返回");
		list.add("      2：初始化所有流程实例（非zip）");
		list.add("      3：初始化单个流程实例（非zip）");
		list.add("      4：初始化多个流程实例（非zip）");
		list.add("      5：初始化单个流程实例（zip）");
		return list;
	}
	/**
	 * 
	 * @Title: initActivitiProcessZipMenu 
	 * @Description: 初始化流程实例（zip）
	 * @return
	 */
	public static List<String> initActivitiProcessZipMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入流程实例的全路径（zip文件全路径名）：");
		return list;
	}
	
	/**
	 * 
	 * @Title: initActivitiOneProcessMenu 
	 * @Description: 初始化单个流程实例
	 * @return
	 */
	public static List<String> initActivitiOneProcessMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入流程文件名（不带文件后缀）：");
		return list;
	}
	/**
	 * 
	 * @Title: initActivitiManyProcessMenu 
	 * @Description: 初始化多个流程实例
	 * @return
	 */
	public static List<String> initActivitiManyProcessMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入流程文件名（不带文件后缀，通过|进行分割）：");
		return list;
	}
	//------------------初始化授权详情数据模板菜单-------------------------------------------
	
	/**
	 * 
	 * @Title: initAuthTemplateMenu 
	 * @Description:授权详情数据模板菜单展示
	 * @return
	 */
	public static List<String> initAuthTemplateMenu(){
		List<String> list = new ArrayList<String>();
		list.add(head);
		list.add(title);
		list.add(head);
		list.add(choice);
		list.add("      0：退出                                                                 1：返回");
		list.add("      2：初始化所有授权详情数据模板");
		list.add("      3：初始化单个授权详情数据模板文件名（模板文件名不带后缀）");
		list.add("      4： 初始化多个授权详情数据模板文件名（模板文件名不带后缀，多个文件之间通过|进行分割）");
		return list;
	}
	/**
	 * 
	 * @Title: initAuthTemplateOneMenu 
	 * @Description: 初始化单个授权详情数据模板文件输入提示
	 * @return
	 */
	public static List<String> initAuthTemplateOneMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入单个授权详情数据模板文件名（不带后缀）：");
		return list;
	}
	
	/**
	 * 
	 * @Title: initAuthTemplateManyMenu 
	 * @Description: 初始化多个授权详情数据模板文件输入提示
	 * @return
	 */
	public static List<String> initAuthTemplateManyMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入多个授权详情数据模板文件名（不带后缀，通过|进行分割）：");
		return list;
	}
	//-------------------获取缓存数据------------------------------------------
	/**
	 * 
	 * @Title: getInitCacheDataMenu 
	 * @Description: 获取缓存数据菜单展示
	 * @return
	 */
	public static List<String> getInitCacheDataMenu(){
		List<String> list = new ArrayList<String>();
		list.add(head);
		list.add(title);
		list.add(head);
		list.add(choice);
		list.add("      0：退出                                                                 1：返回");
		list.add("      2：获取报文模板缓存数据");
		list.add("      3：获取错误码缓存数据");
		list.add("      4： 获取转义缓存数据");
		list.add("      5： 获取properties缓存数据");
		list.add("      6： 获取校验模板缓存数据");
		list.add("      7： 获取数据库中流程部署数据");
		list.add("      8： 获取授权详情数据模板数据");
		return list;
	}
	
	/**
	 * 
	 * @Title: getFormatsInitCacheDataMenu 
	 * @Description: 获取报文模板缓存数据输入提示
	 * @return
	 */
	public static List<String> getFormatsInitCacheDataMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入获取报文模板的名称：");
		return list;
	}
	
	/**
	 * 
	 * @Title: getErrorCodeInitCacheDataMenu 
	 * @Description: 获取错误码缓存数据输入提示
	 * @return
	 */
	public static List<String> getErrorCodeInitCacheDataMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入错误码：");
		return list;
	}
	
	/**
	 * 
	 * @Title: getAppParInitCacheDataMenu 
	 * @Description: 获取转义缓存数据输入提示
	 * @return
	 */
	public static List<String> getAppParInitCacheDataMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入转义数据名称：");
		return list;
	}
	/**
	 * 
	 * @Title: getPropertiesInitCacheDataMenu 
	 * @Description: 获取properties缓存数据输入提示
	 * @return
	 */
	public static List<String> getPropertiesInitCacheDataMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入properties文件中的主键值：");
		return list;
	}
	/**
	 * 
	 * @Title: getValidationInitCacheDataMenu 
	 * @Description: 获取校验模板缓存数据输入提示
	 * @return
	 */
	public static List<String> getValidationInitCacheDataMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入校验模板文件名：");
		return list;
	}
	
	/**
	 * 
	 * @Title: getDbActivitiMenu 
	 * @Description: 获取db中的activiti据输入提示
	 * @return
	 */
	public static List<String> getDbActivitiMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入部署的流程id：");
		return list;
	}
	/**
	 * 
	 * @Title: getAuthTemplateMenu 
	 * @Description: 获取授权详情数据模板数据提示
	 * @return
	 */
	public static List<String> getAuthTemplateMenu(){
		List<String> list = new ArrayList<String>();
		list.add("请输入授权详情数据模板文件名：");
		return list;
	}
	//-------------------------------------------------------------
}
