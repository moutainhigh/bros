package bros.c.bankmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IChannelSysGroupServiceFacade 
 * @Description: 渠道系统分组对外接口
 * @author gaoyongjing 
 * @date 2016年7月5日 下午15:35:28 
 * @version 1.0
 */
public interface ICChannelSysGroupServiceFacade {
	/**
	 * 
	 * @Title: addChannelSysGroupMethod
	 * @Description: 新增渠道系统分组
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编号
	 * @param chlSysName 渠道系统分组名称
	 * @param chlSysDesc 渠道系统分组描述
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity addChannelSysGroupMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateChannelSysGroupMethod
	 * @Description: 修改渠道系统分组信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编号
	 * @param chlSysName 渠道系统分组名称
	 * @param chlSysDesc 渠道系统分组描述
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity updateChannelSysGroupMethod(Map<String, Object> headMap ,Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteChannelSysGroupMethod
	 * @Description: 删除渠道系统分组信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编号
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity deleteChannelSysGroupMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryChannelSysGroupMethod
	 * @Description: 查询渠道系统分组信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity 返回对象信息Map<String,Object> returnList渠道分组信息列表
	 * @throws ServiceException
	 */
	public ResponseEntity queryChannelSysGroupMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
	/**
	 * 
	 * @Title: queryChannelSysGroupPageMethod
	 * @Description: 分页查询渠道系统分组信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @param pageNo 页码
	 * @param pageSize 每页条数
	 * @return ResponseEntity Map<String, Object> returnList渠道系统分组列表totalNum总条数
	 * @throws ServiceException
	 */
	public ResponseEntity queryChannelSysGroupPageMethod(Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
	/**
	 * 
	 * @Title: queryChlSysGroupAndChlInfoMethod
	 * @Description:根据法人id查询渠道分组及渠道信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	public ResponseEntity queryChlSysGroupAndChlInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException;
	
	/**
	 * pub_channel
	 * @Title: deleteChannelSysGroupBaseInfoMethod
	 * @Description:删除pub_channel渠道分组信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	public ResponseEntity deleteChannelSysGroupBaseInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException;
	
	/**
	 * pub_channel
	 * @Title: updateChannelSysGroupBaseInfoMethod
	 * @Description:修改pub_channel渠道分组信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	public ResponseEntity updateChannelSysGroupBaseInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException;
	
	/**
	 * pub_channel
	 * @Title: addChannelSysGroupBaseInfoMethod
	 * @Description:新增pub_channel渠道分组信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	public ResponseEntity addChannelSysGroupBaseInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException;
	
	/**
	 * pub_channel
	 * @Title: queryChannelSysGroupBaseInfoMethod
	 * @Description:查询pub_channel渠道分组信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	public ResponseEntity queryChannelSysGroupBaseInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException;
}
