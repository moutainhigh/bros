package bros.manage.init.tools;

import java.util.List;

import bros.manage.init.Main;
import bros.manage.init.entity.Menu;

/**
 * 
 * @ClassName: MenuUtil 
 * @Description: 菜单工具类
 * @author 何鹏
 * @date 2016年5月16日 上午12:46:39 
 * @version 1.0
 */
public class MenuUtil {
	/**
	 * 
	 * @Title: showCurrencyMenu 
	 * @Description: 显示当前菜单
	 */
	public static void showCurrencyMenu(Menu menu){
		if(menu != null){
			List<String> list = menu.getList();
			if(list != null && list.size()>0){
				for(int i=0;i<list.size();i++){
					System.out.println(list.get(i));
				}
			}
		}
	}
	
	/**
	 * 
	 * @Title: setAndShowMenu 
	 * @Description: 设置展示当初菜单
	 * @param currencyMenu
	 */
	public static void setAndShowCurrencyMenu(Menu currencyMenu){
		Main.setCurrencyMenu(currencyMenu);
		MenuUtil.showCurrencyMenu(currencyMenu);
	}
	
	/**
	 * 
	 * @Title: setAndShowTopCurrencyMenu 
	 * @Description: 设置上一级菜单为当前菜单并展示
	 * @param currencyMenu
	 */
	public static void setAndShowTopCurrencyMenu(Menu currencyMenu){
		if(currencyMenu != null){
			Menu menu = currencyMenu.getTopMenu();
			setAndShowCurrencyMenu(menu);
		}
	}
}
