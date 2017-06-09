package bros.consumer.init.entity;

import java.util.List;
/**
 * 
 * @ClassName: Menu 
 * @Description: 菜单实体类
 * @author 何鹏
 * @date 2016年5月16日 上午12:00:33 
 * @version 1.0
 */
public class Menu {
	/**
	 * 父菜单
	 */
	private Menu topMenu;
	/**
	 * 子菜单
	 */
	private List<Menu> nextMenuList;
	/**
	 * 当前菜单项
	 */
	private List<String> list;

	/**
	 * 本级菜单调用对象
	 */
	private Object obj;
	/**
	 * 执行方法名
	 */
	private String methodName;
	
	public Menu(Menu topMenu, List<Menu> nextMenuList, List<String> list) {
		super();
		this.topMenu = topMenu;
		this.nextMenuList = nextMenuList;
		this.list = list;
	}
	public Menu getTopMenu() {
		return topMenu;
	}
	public void setTopMenu(Menu topMenu) {
		this.topMenu = topMenu;
	}
	public List<Menu> getNextMenuList() {
		return nextMenuList;
	}
	public void setNextMenuList(List<Menu> nextMenuList) {
		this.nextMenuList = nextMenuList;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
}
