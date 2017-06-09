package bros.provider.parent.bankmanage.channel.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IChannelSysGroupService 
 * @Description: 渠道系统分组接口
 * @author 高永静
 * @date 2016年6月27日 上午9:45:17 
 * @version 1.0
 */
public interface IChannelSysGroupService {
	/**
	 * 
	 * @Title: addChannelSysGroupMethod
	 * @Description: 新增渠道系统分组
	 * @param legalPersonId  法人ID
	 * @param chlSysCode  密码
	 * @param chlSysName
	 * @param chlSysDesc
	 * @return
	 * @throws ServiceException
	 */
	public void addChannelSysGroupMethod(String legalPersonId,String chlSysCode,String chlSysName,String chlSysDesc)throws ServiceException;
	/**
	 * 
	 * @Title: updateChannelSysGroupMethod
	 * @Description: 修改渠道系统分组信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编号
	 * @param chlSysName 渠道系统分组名称
	 * @param chlSysDesc 渠道系统分组描述
	 * @return
	 * @throws ServiceException
	 */
	public void updateChannelSysGroupMethod(Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteChannelSysGroupMethod
	 * @Description: 删除渠道系统分组信息
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编号
	 * @return
	 * @throws ServiceException
	 */
	public void deleteChannelSysGroupMethod (String legalPersonId,String chlSysCode)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryChannelSysGroupMethod
	 * @Description: 查询渠道系统分组信息
	 * @param legalPersonId  法人ID
	 * @return List<Map<String,Object>>
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryChannelSysGroupMethod (String legalPersonId)throws ServiceException;
	/**
	 * 
	 * @Title: queryChannelSysGroupPageMethod
	 * @Description: 分页查询渠道系统分组信息
	 * @param legalPersonId  法人ID
	 * @param pageNo 页码
	 * @param pageSize 每页条数
	 * @return List<Map<String,Object>>
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryChannelSysGroupPageMethod (String legalPersonId,int pageNo,int pageSize)throws ServiceException;
	/**
	 * 
	 * @Title: queryChannelSysGroupTotalNumMethod
	 * @Description: 查询渠道系统分组总条数
	 * @param legalPersonId  法人ID
	 * @return int
	 * @throws ServiceException
	 */
	public int queryChannelSysGroupTotalNumMethod (String legalPersonId)throws ServiceException;
	/**
	 * 
	 * @Title: queryChannelSysGroupMethod
	 * @Description:根据法人ID、渠道系统分组编码查询渠道系统分组信息
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编码
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	public  Map<String,Object> queryChannelSysGroupBySysCodeMethod (String legalPersonId,String chlSysCode)throws ServiceException;
	
	/***********************pub_channel*************************/
	/**
	 * 
	 * @Title: queryChannelSysGroupBaseInfoMethod
	 * @Description:查询pub_channel渠道系统信息(查询所有)
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编码
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryChannelSysGroupBaseInfoMethod ()throws ServiceException;
	/**
	 * 
	 * @Title: queryChannelSysGroupBaseInfoByCode
	 * @Description:查询pub_channel渠道系统信息 (查询单个)
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编码
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	public  Map<String,Object> queryChannelSysGroupBaseInfoByCode (String channelCode)throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteChannelSysGroupBaseInfoMethod
	 * @Description:删除pub_channel渠道系统信息
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编码
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	public  void deleteChannelSysGroupBaseInfoMethod (Map<String, Object> paramMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: updateChannelSysGroupBaseInfoMethod
	 * @Description:修改pub_channel渠道系统信息
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编码
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	public  void updateChannelSysGroupBaseInfoMethod (Map<String, Object> paramMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: addChannelSysGroupBaseInfoMethod
	 * @Description:增加pub_channel渠道系统信息
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编码
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	public  void addChannelSysGroupBaseInfoMethod (Map<String, Object> paramMap)throws ServiceException;
}
