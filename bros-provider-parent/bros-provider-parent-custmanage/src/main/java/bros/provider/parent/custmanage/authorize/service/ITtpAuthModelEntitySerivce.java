package bros.provider.parent.custmanage.authorize.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;


/**
 * 
 * @ClassName: ITtpAuthModelEntitySerivce 
 * @Description: 对客授权模型实体服务接口
 * @author pengxiangnan 
 * @date 2016年7月19日 下午3:27:00 
 * @version 1.0
 */
public interface ITtpAuthModelEntitySerivce {
	
	/**
	 * 
	 * @Title: saveTtpAuthModel 
	 * @Description: 保存授权模型
	 * @param authorizeId 授权模型编号
	 * @param name 授权模型名称
	 * @param legalId 法人记录唯一标识
	 * @param channel 渠道编号
	 * @param cstNo 客户编号
	 * @param moneyType 模式类型（0：无金额模式 1：有金额模式）
	 * @param orderly 授权顺序（0：无序授权，1：有序授权）
	 * @param send 发送类型（0：手工发送，1：自动发送）
	 * @param state 状态（0：停用；1：正常）
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int saveTtpAuthModel(String authorizeId, String name, String legalId, String channel, String cstNo, String moneyType, 
			String orderly, String send, String state) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateTtpAuthModel 
	 * @Description: 更新授权模型
	 * @param authorizeId 授权模型编号
	 * @param name 授权模型名称
	 * @param legalId 法人记录唯一标识
	 * @param channel 渠道编号
	 * @param cstNo 客户编号
	 * @param moneyType 模式类型（0：无金额模式 1：有金额模式）
	 * @param orderly 授权顺序（0：无序授权，1：有序授权）
	 * @param send 发送类型（0：手工发送，1：自动发送）
	 * @param state 状态（0：停用；1：正常）
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int updateTtpAuthModel(String authorizeId, String name, String legalId, String channel, String cstNo, String moneyType, 
			String orderly, String send, String state) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteTtpAuthModel 
	 * @Description: 删除授权模型
	 * @param channel 渠道编号
	 * @param legalId 法人记录唯一标识
	 * @param authorizeId 授权模型编号
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int deleteTtpAuthModel(String channel, String legalId, String authorizeId) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTtpAuthModelByName 
	 * @Description: 根据授权模型名称查询授权模型
	 * @param channel 渠道编号
	 * @param legalId 法人记录唯一标识
	 * @param cstNo 客户编号
	 * @param authModelName 授权模型名称
	 * @return Map<String, Object> 授权模型
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTtpAuthModelByName(String channel,String legalId, String cstNo, String authModelName) 
			throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTtpAuthModelListForPage 
	 * @Description: 根据渠道编号+法人记录ID+客户编号分页查询授权模型列表
	 * @param channel 渠道编号
	 * @param legalId 法人记录唯一标识
	 * @param cstNo 客户编号
	 * @param pageNo 起始记录数
	 * @param pageSize 每页显示记录数
	 * @return List<Map<String, Object>> 授权模型列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryTtpAuthModelListForPage(String channel, String legalId, String cstNo,
			int pageNo , int pageSize) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTtpAuthModelTotalNum 
	 * @Description: 根据渠道编号+法人记录ID+客户编号查询满足条件记录总数
	 * @param channel 渠道编号
	 * @param legalId 法人记录唯一标识
	 * @param cstNo 客户编号
	 * @return int 记录总数
	 * @throws ServiceException
	 */
	public int queryTtpAuthModelTotalNum(String channel, String legalId, String cstNo) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTtpAuthModelById 
	 * @Description: 根据授权模型编号查询指定法人授权模型信息
	 * @param channel 渠道编号
	 * @param legalId 法人记录唯一标识
	 * @param cstNo 客户编号
	 * @param authorizeId 记录总数
	 * @return Map<String, Object> 授权模型信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTtpAuthModelById(String channel, String legalId, String cstNo, String authorizeId) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTtpAuthModelList
	 * @Description: 根据渠道编号+法人记录ID+客户编号查询授权模型列表
	 * @param channel 渠道编号
	 * @param legalId 法人记录唯一标识
	 * @param cstNo 客户编号
	 * @return List<Map<String, Object>> 授权模型列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryTtpAuthModelList(String channel, String legalId, String cstNo) throws ServiceException;
}
