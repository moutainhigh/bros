package bros.provider.parent.manage.corporate.cstmanage.limit.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;


/**
 * 
 * @ClassName: ICorporateCstLimitService 
 * @Description: 企业客户限额服务
 * @author zsg 
 * @date 2017年1月12日 下午3:04:29 
 * @version 1.0
 */
public interface ITtpCstLimitEntityService {
	/**
	 * 
	 * @Title: addTtpLimit 
	 * @Description: 添加客户限额
	 * @param cstNo
	 * @param channel
	 * @param bizType
	 * @param legalId
	 * @param dayMax
	 * @param singleMax
	 * @return
	 * @throws ServiceException
	 */
	public int addTtpCstLimit(String cstNo,String channel,String bizType,String legalId,String dayMax,String singleMax)throws ServiceException;
	/**
	 * 
	 * @Title: queryTtpCstLimitList 
	 * @Description: 根据客户号+法人id+渠道+业务类型查询客户限额信息
	 * @param cstNo
	 * @param legalId
	 * @param channel
	 * @param bizType
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryTtpCstLimitList(String cstNo,String legalId,String channel,String bizType)throws ServiceException;
	/**
	 * 
	 * @Title: updateTtpCstLimit 
	 * @Description: 更新客户限额
	 * @param cstNo
	 * @param channel
	 * @param bizType
	 * @param legalId
	 * @param dayMax
	 * @param singleMax
	 * @return
	 * @throws ServiceException
	 */
	public int updateTtpCstLimit(String cstNo,String channel,String bizType,String legalId,String dayMax,String singleMax)throws ServiceException;
	/**
	 * 
	 * @Title: deleteTtpCstLimit 
	 * @Description: 根据客户号+渠道+业务类型+法人id删除客户限额
	 * @param cstNo
	 * @param channel
	 * @param bizType
	 * @param legalId
	 * @return
	 * @throws ServiceException
	 */
	public int deleteTtpCstLimit(String cstNo,String channel,String bizType,String legalId)throws ServiceException;
	/**
	 * 
	 * @Title: queryTtpCstLimitList 
	 * @Description: 查询所有客户限额信息
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryTtpCstLimitList()throws ServiceException;
}
