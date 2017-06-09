package bros.provider.parent.bankmanage.tellerrole.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;


/** 
 * @ClassName:TellerRoleServiceImpl  
 * @Description:柜员角色管理接口
 * @author  haojinhui
 * @date 2016年6月30日 下午3:40:56 
 * @version V1.0  
 */
public interface ITellerRoleService {

	/**
	 * 
	 * @Title: queryTellerRole
	 * @Description: 柜员角色查询
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTellerRole(Map<String,Object> headMap,Map<String, Object> contextMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: addTellerRole
	 * @Description: 增加柜员角色
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> addTellerRole(Map<String,Object> headMap,Map<String, Object> contextMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: updateTellerRole
	 * @Description: 修改柜员角色
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> updateTellerRole(Map<String,Object> headMap,Map<String, Object> contextMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteTellerRole
	 * @Description: 删除柜员角色
	 * @param breId  角色编号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> deleteTellerRole(Map<String,Object> headMap,String breId,String legalId)throws ServiceException;
	
	/**
	 * 
	 * @Title: setTellerRole
	 * @Description: 设置柜员角色关联
	 * @param burlTrllerno  柜员号
	 * @param burlRoleid  角色编号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> setTellerRole(Map<String,Object> headMap,Map<String,Object> bodyMap)throws ServiceException;
	/**
	 * 
	 * @Title: updateSetTellerRole
	 * @Description: 更新柜员角色关联关系
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> updateSetTellerRole(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: querySetTellerRole
	 * @Description: 查询柜员已分配角色信息
	 * @param breLegal  法人id
	 * @param tellerId  柜员id
	 * @return  Map<String, Object>
	 * @throws ServiceException
	 */
	public Map<String, Object> querySetTellerRole(String breLegal,String tellerId) throws ServiceException;

}
