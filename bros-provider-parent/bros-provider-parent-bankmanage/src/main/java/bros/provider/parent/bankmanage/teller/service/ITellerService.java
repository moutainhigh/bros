package bros.provider.parent.bankmanage.teller.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName:ITellerService  
 * @Description:柜员系统分组接口
 * @author  haojinhui
 * @date 2016年6月28日 上午9:55:35 
 * @version V1.0  
 */
public interface ITellerService {
	/**
	 * 
	 * @Title: addTeller
	 * @Description: 新增柜员
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> addTeller(Map<String,Object> headMap,Map<String, Object> contextMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTellerById
	 * @Description: 柜员信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTellerById(Map<String,Object> headMap,Map<String, Object> contextMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTellerBybtrLegal
	 * @Description: 柜员信息(不用柜员状态查询)
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTellerBybtrLegal(Map<String,Object> headMap,Map<String, Object> contextMap)throws ServiceException;

	/**
	 * 
	 * @Title: updateTeller
	 * @Description: 修改柜员信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> updateTeller(Map<String,Object> headMap,Map<String, Object> contextMap)throws ServiceException;

	/**
	 * 
	 * @Title: deleteTeller
	 * @Description: 删除柜员信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> deleteTeller(Map<String,Object> headMap,Map<String, Object> contextMap,String btrId)throws ServiceException;
	
	/**
	 * 
	 * @Title: tellerLogin
	 * @Description: 柜员登录
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> tellerLogin(Map<String,Object> headMap,Map<String, Object> contextMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: tellerLogout
	 * @Description: 柜员签退
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> tellerLogout(Map<String,Object> headMap,Map<String, Object> contextMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTellerRoleMenu
	 * @Description: 根据柜员差菜单
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTellerRoleMenu(Map<String,Object> headMap,Map<String,Object> bodyMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryMenudefPro
	 * @Description: 根据法人id，菜单性质，系统标识查询货架编码
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> queryMenudefPro(Map<String,Object> headMap,Map<String,Object> bodyMap)throws ServiceException;

	
	
}
