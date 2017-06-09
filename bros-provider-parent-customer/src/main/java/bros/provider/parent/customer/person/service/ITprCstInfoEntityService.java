package bros.provider.parent.customer.person.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ITprCstInfoEntityService 
 * @Description: 个人客户基本信息实体服务接口
 * @author huangcanhui 
 * @date 2016年10月8日 下午2:15:14 
 * @version 1.0
 */
public interface ITprCstInfoEntityService {
	
	/**
	 * 
	 * @Title: queryTprCstInfoByCtfNo 
	 * @Description: 根据法人ID+渠道编号+证件号码查询个人客户基本信息
	 * @param legalId 法人ID
	 * @param channel 渠道编号
	 * @param ctfNo 证件号码
	 * @return Map<String, Object> 个人客户基本信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTprCstInfoByCtfNo(String legalId, String channel, String ctfNo) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTprCstInfoByCstId 
	 * @Description: 根据客户标识查询个人客户基本信息
	 * @param cstId 客户标识
	 * @return Map<String, Object> 个人客户基本信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTprCstInfoByCstId(String cstId) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTprCstInfoByCstNo 
	 * @Description: 根据法人ID+客户编号+渠道编号查询个人客户基本信息
	 * @param legalId 法人ID
	 * @param cstNo 客户编号
	 * @param channel 渠道编号
	 * @return Map<String, Object> 个人客户基本信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTprCstInfoByCstNo(String legalId, String cstNo, String channel) throws ServiceException;
	
}
