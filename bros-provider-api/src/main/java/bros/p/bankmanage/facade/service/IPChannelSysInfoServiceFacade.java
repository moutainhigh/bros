package bros.p.bankmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPChannelSysInfoServiceFacade 
 * @Description: 渠道系统对外接口
 * @author gaoyongjing 
 * @date 2016年6月28日 下午15:35:28 
 * @version 1.0
 */
public interface IPChannelSysInfoServiceFacade {
	/**
	 * 
	 * @Title: addChannelSysInfoMethod
	 * @Description: 新增渠道系统
	 * @param headMap  头信息
	 * @param paramMap  渠道系统信息，包括以下
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlName 系统名称
	 * @param chlDesc 系统描述
	 * @param chlSysCode 系统分组编号
	 * @param chlFlag 启停状态
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity addChannelSysInfoMethod(Map<String, Object> headMap ,Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateChannelSysInfoMethod
	 * @Description: 修改渠道系统信息
	 * @param headMap  头信息
	 * @param paramMap 渠道信息包含以下参数
	 * @param chlId 渠道系统唯一标识
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlName 系统名称
	 * @param chlDesc 系统描述
	 * @param chlSysCode 系统分组编号
	 * @param chlFlag 启停状态
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity updateChannelSysInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateChannelSysStatusMethod
	 * @Description: 修改渠道系统状态
	 * @param headMap  头信息
	 * @param paramMap 渠道信息包含以下参数
	 * @param chlId 渠道唯一标识
	 * @param chlCode 渠道编号
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 系统分组编号
	 * @param chlFlag 启停状态
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity updateChannelSysStatusMethod(Map<String, Object> headMap, Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteChannelSysInfoMethod
	 * @Description: 删除渠道系统信息
	 * @param headMap  头信息
	 * @param paramMap 渠道信息包含以下参数
	 * @param chlId 渠道系统唯一标识
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 系统分组编号
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity deleteChannelSysInfoMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryChannelSysInfoMethod
	 * @Description: 查询渠道系统信息
	 * @param headMap  头信息
	 * @param paramMap 渠道信息包含以下参数
	 * @param chlId 渠道系统唯一标识
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 系统分组编号
	 * @return ResponseEntity Map<String,Object> returnList渠道系统信息列表
	 * @throws ServiceException
	 */
	public ResponseEntity queryChannelSysInfoMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
	/**
	 * 
	 * @Title: queryChannelSysInfoPageMethod
	 * @Description: 根据条件分页查询渠道系统信息
	 * @param headMap  头信息
	 * @param paramMap 渠道信息包含以下参数
	 * @param chlId 渠道唯一标识
	 * @param chlCode 渠道编号
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 系统分组编号
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return ResponseEntity Map<String,Object> returnList渠道信息列表 totalNum 总条数
	 * @throws ServiceException
	 */
	public ResponseEntity queryChannelSysInfoPageMethod(Map<String, Object> headMap, Map<String, Object> paramMap)throws ServiceException;
}
