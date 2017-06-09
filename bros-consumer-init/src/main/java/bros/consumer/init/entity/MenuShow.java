package bros.consumer.init.entity;

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
	
	//-------------------------------------------------------------
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
		list.add("      2：初始化消费者所有配置");
		list.add("      3：初始化消费者校验模板");
		list.add("      4： 初始化消费者错误码");
		list.add("      5： 初始化消费者转义数据");	
		list.add("      6： 初始化消费者properties数据文件");	
		list.add("      7： 获取消费者缓存中的数据");	
		return list;
	}
	//-------------------------------------------------------------
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
		list.add("      2：初始化消费者所有错误码");
		list.add("      3：初始化消费者单个错误码");
		list.add("      4： 初始化消费者多个错误码（多个错误码通过|进行分割）");
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
		list.add("请输入消费者单个错误码名：");
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
		list.add("请输入消费者多个错误码名（通过|进行分割）：");
		return list;
	}
	//-------------------------------------------------------------
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
		list.add("      2：初始化消费者所有转义数据");
		list.add("      3：初始化消费者单个转义数据");
		list.add("      4： 初始化消费者多个转义数据（多个转义数据通过|进行分割）");
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
		list.add("请输入消费者单个转义数据名：");
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
		list.add("请输入消费者多个转义数据名（数据通过|进行分割）：");
		return list;
	}
	//-------------------------------------------------------------
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
		list.add("      2：初始化消费者所有properties文件");
		list.add("      3：初始化消费者单个properties文件（properties文件名不带后缀）");
		list.add("      4： 初始化消费者多个properties（properties文件名不带后缀，多个文件之间通过|进行分割）");
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
		list.add("请输入消费者单个properties文件名（不带后缀）：");
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
		list.add("请输入消费者多个properties文件名（不带后缀，通过|进行分割）：");
		return list;
	}
	
	
	
	
	
	
	
	//-------------------------------------------------------------
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
		list.add("      2：获取消费者校验模板缓存数据");
		list.add("      3：获取消费者错误码缓存数据");
		list.add("      4： 获取消费者转义缓存数据");
		list.add("      5： 获取消费者properties缓存数据");
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
		list.add("请输入消费者错误码：");
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
		list.add("请输入消费者转义数据名称：");
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
		list.add("请输入消费者properties文件中的主键值：");
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
		list.add("请输入消费者校验模板文件名：");
		return list;
	}
	//-------------------------------------------------------------
	
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
		list.add("      2：初始化消费者所有校验模板");
		list.add("      3：初始化消费者单个校验模板文件名（校验模板文件名不带后缀）");
		list.add("      4： 初始化消费者多个校验模板文件名（校验模板文件名不带后缀，多个文件之间通过|进行分割）");
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
		list.add("请输入消费者单个校验模板文件名（不带后缀）：");
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
		list.add("请输入消费者多个校验模板文件名（不带后缀，通过|进行分割）：");
		return list;
	}

	//-------------------------------------------------------------
}
