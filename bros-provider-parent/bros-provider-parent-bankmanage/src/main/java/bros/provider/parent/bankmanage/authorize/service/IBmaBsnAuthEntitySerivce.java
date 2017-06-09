package bros.provider.parent.bankmanage.authorize.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IBmaBsnAuthEntitySerivce 
 * @Description: 开通功能与授权模型关系实体服务接口
 * @author pengxiangnan 
 * @date 2016年7月27日 下午6:51:55 
 * @version 1.0
 */
public interface IBmaBsnAuthEntitySerivce {
	
	/**
	 * 
	 * @Title: queryBmaBsnAuthListByChannelAndLegalId 
	 * @Description: 根据渠道标识与法人唯一标识查找功能关系列表
	 * @param legalId 法人记录唯一标识
	 * @param channel 渠道便是
	 * @return List<Map<String, Object>> 功能与授权模型关系列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryBmaBsnAuthListByChannelAndLegalId(String channel, String legalId) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateBmaBsnAuth 
	 * @Description: 修改开通功能与授权模型关系
	 * @param funcAuthList 开通功能与授权模型关系列表
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int updateBmaBsnAuth(List<Map<String, Object>> funcAuthList) throws ServiceException;
	/**
	 * 
	 * @Title: insertBmaBsnAuth 
	 * @Description: 添加授权配置信息
	 * @param funcAuthList
	 * @return
	 * @throws ServiceException
	 */
	public int insertBmaBsnAuth(List<Map<String, Object>> funcAuthList) throws ServiceException;
	/**
	 * 
	 * @Title: queryBsbAuth 
	 * @Description: 查询模型分配信息
	 * @param channel
	 * @param legalId
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryBsbAuth(String channel, String legalId) throws ServiceException;
	/**
	 * 
	 * @Title: queryBsbAuthNum 
	 * @Description: 授权模型配置信息是否存在
	 * @param channel
	 * @param legalId
	 * @param bsnCode
	 * @return
	 * @throws ServiceException
	 */
	
	public int queryBsbAuthNum(String channel, String legalId,String bsnCode) throws ServiceException;
}
