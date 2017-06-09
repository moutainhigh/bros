package bros.provider.parent.bankmanage.authorize.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IBmaMenudefEntitySerivce 
 * @Description: 菜单综合接口实现
 * @author pengxiangnan 
 * @date 2016年7月27日 下午6:51:55 
 * @version 1.0
 */
public interface IBmaMenudefEntitySerivce {
	
	/**
	 * 
	 * @Title: queryMenudefToTree
	 * @Description: 菜单查询
	 * @param channel  渠道标识
	 * @param legalId  法人标识
	 * @param menuList  菜单ID列表
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryMenudefToTree(String channel, String legalId, List<String> menuList)  throws ServiceException;
	
	/**
	 * 
	 * @Title: queryBmaBsnDefAndBmaBsnAuthAndBmaMenuData
	 * @Description: 关联信息查询
	 * @param channel  渠道标识
	 * @param legalId  法人标识
	 * @param cstNo  客户号
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryBmaBsnDefAndBmaBsnAuthAndBmaMenuData(String channel, String legalId)  throws ServiceException;
	/**
	 * 
	 * @Title: queryMenuDefByBsnDef 
	 * @Description: 查询业务对应的菜单及菜单上级信息
	 * @param channel
	 * @param legalId
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryMenuDefByBsnDef(String channel, String legalId)  throws ServiceException;
	/**
	 * 
	 * @Title: queryBsnDefAndMenuByAll 
	 * @Description: 查询业务功能菜单信息
	 * @param channel
	 * @param legalId
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryBsnDefAndMenuByAll(String channel, String legalId)  throws ServiceException;
}
