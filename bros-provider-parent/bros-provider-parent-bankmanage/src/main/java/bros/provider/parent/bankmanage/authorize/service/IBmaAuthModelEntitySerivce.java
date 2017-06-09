package bros.provider.parent.bankmanage.authorize.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IBmaAuthModelEntitySerivce 
 * @Description: 内部授权模型实体服务接口
 * @author pengxiangnan 
 * @date 2016年6月23日 下午10:49:06
 * @version 1.0
 */
public interface IBmaAuthModelEntitySerivce {
	
	/**
	 * 
	 * @Title: saveBmaAuthModel 
	 * @Description: 保存授权模型
	 * @param authorizeId 授权模型编号
	 * @param name 授权模型名称
	 * @param orderly 授权顺序（0：无序授权；1：有序授权）
	 * @param state 状态（0：停用；1：正常）
	 * @param legalId 法人唯一标识
	 * @param channel 渠道标识
	 * @param authMode 授权方式（0：额度，1：强制，2：条件）
	 * @param authType 授权类型（0：即时生效，无需授权；1：互为授权；2：指定授权 ）
	 * @param authShape 授权形式（0：审核式；1：临柜）
	 * @param authRole 用于存放指定授权时指定的角色
	 * @param authLevel -授权级别（0：单人；1：多人）
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int saveBmaAuthModel(String authorizeId, String name, String orderly, String state, String legalId, 
			String channel, String authMode, String authType, String authShape, String authRole, String authLevel) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateBmaAuthModel 
	 * @Description: 更新授权模型
	 * @param authorizeId 授权模型编号
	 * @param name 授权模型名称
	 * @param orderly 授权顺序（0：无序授权；1：有序授权）
	 * @param state 状态（0：停用；1：正常）
	 * @param legalId 法人唯一标识
	 * @param channel 渠道标识
	 * @param authMode 授权方式（0：额度，1：强制，2：条件）
	 * @param authType 授权类型（0：即时生效，无需授权；1：互为授权；2：指定授权 ）
	 * @param authShape 授权形式（0：审核式；1：临柜）
	 * @param authRole 用于存放指定授权时指定的角色
	 * @param authLevel -授权级别（0：单人；1：多人）
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int updateBmaAuthModel(String authorizeId, String name, String orderly, String state, String legalId, 
			String channel, String authMode, String authType, String authShape, String authRole, String authLevel) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteBmaAuthModel 
	 * @Description: 根据授权模型编号删除授权模型
	 * @param channel 渠道编号
	 * @param legalId 法人记录唯一标识
	 * @param authorizeId 授权模型编号
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int deleteBmaAuthModel(String channel, String legalId, String authorizeId) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryBmaAuthModelByName 
	 * @Description: 根据授权模型名称查询指定法人授权模型信息
	 * @param channel 渠道编号
	 * @param legalId 法人记录唯一标识
	 * @param authModelName 授权模型名称
	 * @return Map<String, Object> 
	 * @throws ServiceException 授权模型信息
	 */
	public Map<String, Object> queryBmaAuthModelByName(String channel,String legalId, String authModelName) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryBmaAuthModelById 
	 * @Description: 根据授权模型编号查询指定法人授权模型信息
	 * @param channel 渠道编号
	 * @param legalId 法人记录唯一标识
	 * @param authorizeId 授权模型编号
	 * @return Map<String, Object> 授权模型信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryBmaAuthModelById(String channel,String legalId, String authorizeId) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryBmaAuthModelListForPage 
	 * @Description: 根据渠道编号+法人记录ID分页查询授权模型列表
	 * @param channel 渠道编号
	 * @param legalId 法人记录ID
	 * @param pageNo 起始记录数
	 * @param pageSize 每页显示记录数
	 * @return List<Map<String, Object>> 授权模型列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryBmaAuthModelListForPage(String channel, String legalId, 
			int pageNo , int pageSize) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryBmaAuthModelTotalNum 
	 * @Description: 根据渠道编号+法人记录ID查询满足条件记录总数
	 * @param channel 渠道编号
	 * @param legalId 法人记录ID
	 * @return int 记录总数
	 * @throws ServiceException
	 */
	public int queryBmaAuthModelTotalNum(String channel, String legalId) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryBmaAuthModelList 
	 * @Description: 根据渠道编号+法人记录ID授权模型列表
	 * @param channel 渠道编号
	 * @param legalId 法人记录ID
	 * @return List<Map<String, Object>> 授权模型列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryBmaAuthModelList(String channel, String legalId) throws ServiceException ;
}
