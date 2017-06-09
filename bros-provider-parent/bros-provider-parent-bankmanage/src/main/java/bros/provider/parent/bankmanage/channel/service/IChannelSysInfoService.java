package bros.provider.parent.bankmanage.channel.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IChannelSysInfoService 
 * @Description: 渠道系统信息接口
 * @author 高永静
 * @date 2016年6月28日 上午9:45:17 
 * @version 1.0
 */
public interface IChannelSysInfoService {
	/**
	 * 
	 * @Title: addChannelSysInfoMethod
	 * @Description: 新增渠道系统信息
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlName 系统名称
	 * @param chlDesc 系统描述
	 * @param chlSysCode 系统分组编号
	 * @param chlFlag 启停状态
	 * @return
	 * @throws ServiceException
	 */
	public void addChannelSysInfoMethod(String chlCode,String legalPersonId,String chlName,String chlDesc,
			String chlSysCode,String chlFlag)throws ServiceException;
	/**
	 * 
	 * @Title: updateChannelSysInfoMethod
	 * @Description: 修改渠道系统信息
	 * @param chlId 渠道系统唯一标识
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlName 系统名称
	 * @param chlDesc 系统描述
	 * @param chlSysCode 系统分组编号
	 * @param chlFlag 启停状态
	 * @return
	 * @throws ServiceException
	 */
	public void updateChannelSysInfoMethod(String chlId,String chlCode,String legalPersonId,String chlName,String chlDesc,
			String chlSysCode,String chlFlag) throws ServiceException;
	/**
	 * 
	 * @Title: updateChannelSysStatusMethod
	 * @Description: 修改渠道系统状态
	 * @param chlId 渠道系统唯一标识
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 系统分组编号
	 * @param chlFlag 启停状态
	 * @return
	 * @throws ServiceException
	 */
	public void updateChannelSysStatusMethod(String chlId,String chlCode,String legalPersonId,String chlSysCode,String chlFlag)throws ServiceException;
	/**
	 * 
	 * @Title: deleteChannelSysInfoMethod
	 * @Description: 删除渠道系统信息
	 * @param chlId 渠道系统唯一标识
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 系统分组编号
	 * @return
	 * @throws ServiceException
	 */
	public void deleteChannelSysInfoMethod (String chlId,String chlCode,String legalPersonId,String chlSysCode)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryAllChannelSysInfoMethod
	 * @Description: 根据条件查询渠道系统信息
	 * @param paramMap  渠道信息
	 * @return List<Map<String,Object>> 渠道系统信息
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryChannelSysInfoMethod (Map<String, Object> paramMap)throws ServiceException;
	/**
	 * 
	 * @Title: queryChannelSysInfoPageMethod
	 * @Description: 根据条件分页查询渠道系统信息
	 *; @param paramMap 渠道信息包含以下参数
	 * @param chlId 渠道系统唯一标识
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 系统分组编号
	 * @param pageNo 页码
	 * @param pageSize 每页条数
	 * @return List<Map<String,Object>> 渠道系统信息
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryChannelSysInfoPageMethod(Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryChannelSysTotalNumMethod
	 * @Description: 根据条件查询渠道系统总条数
	 * @param paramMap 渠道信息包含以下参数
	 * @param chlId 渠道系统唯一标识
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 系统分组编号
	 * @return List<Map<String,Object>> 渠道系统信息
	 * @throws ServiceException
	 */
	public int queryChannelSysTotalNumMethod(Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryChannelSysInfoByObjectIdMethod
	 * @Description: 查询渠道系统信息是否存在
	 * @param paramMap 
	 * @return Map<String, Object> 渠道系统信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryChannelSysInfoByObjectIdMethod(Map<String, Object> paramMap)throws ServiceException;
}
