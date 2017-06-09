package bros.c.bankmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICChannelSysGroupServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPChannelSysGroupServiceFacade;
/**
 * 
 * @ClassName: CChannelSysGroupServiceFacadeImpl 
 * @Description: 渠道系统分组对外接口实现类
 * @author gaoyongjing 
 * @date 2016年6月27日 下午15:35:28 
 * @version 1.0
 */

@Component("cchannelSysGroupServiceFacade")
public class CChannelSysGroupServiceFacadeImpl implements ICChannelSysGroupServiceFacade {
	/**
	 * 渠道分组
	 */
	@Autowired
	private IPChannelSysGroupServiceFacade pchannelSysGroupServiceFacade;
	
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
	@Validation(value="c0000101")
	@Override
	public ResponseEntity addChannelSysGroupMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException {
		return pchannelSysGroupServiceFacade.addChannelSysGroupMethod(headMap,paramMap);
	}

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
	@Validation(value="c0000102")
	@Override
	public ResponseEntity updateChannelSysGroupMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		return pchannelSysGroupServiceFacade.updateChannelSysGroupMethod(headMap, paramMap);
	}

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
	@Validation(value="c0000103")
	@Override
	public ResponseEntity deleteChannelSysGroupMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		return pchannelSysGroupServiceFacade.deleteChannelSysGroupMethod(headMap,paramMap);
	}

	/**
	 * 
	 * @Title: queryChannelSysGroupMethod
	 * @Description: 查询渠道系统分组信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity Map<String, Object> returnList渠道系统分组列表
	 * @throws ServiceException
	 */
	@Validation(value="c0000104")
	@Override
	public ResponseEntity queryChannelSysGroupMethod(Map<String, Object> headMap, Map<String, Object> paramMap)
			throws ServiceException {
		return pchannelSysGroupServiceFacade.queryChannelSysGroupMethod(headMap, paramMap);
	}
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
	@Validation(value="c0000129")
	@Override
	public ResponseEntity queryChannelSysGroupPageMethod(Map<String, Object> headMap,Map<String, Object> paramMap)
			throws ServiceException {
		return pchannelSysGroupServiceFacade.queryChannelSysGroupPageMethod(headMap, paramMap);
	}
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
	@Validation(value="c0000124")
	@Override
	public ResponseEntity queryChlSysGroupAndChlInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException{
		return pchannelSysGroupServiceFacade.queryChlSysGroupAndChlInfoMethod(headMap, paramMap);
	}
	
	/***************pub_channel**********************************************/
	/**
	 * 
	 * @Title: addChannelSysGroupBaseInfoMethod
	 * @Description:新增pub_channel渠道分组信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	@Validation(value="c0000134")
	@Override
	public ResponseEntity addChannelSysGroupBaseInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException{
		return pchannelSysGroupServiceFacade.addChannelSysGroupBaseInfoMethod(headMap, paramMap);
	}
	
	/**
	 * 
	 * @Title: deleteChannelSysGroupBaseInfoMethod
	 * @Description:删除pub_channel渠道分组信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	@Validation(value="c0000135")
	@Override
	public ResponseEntity deleteChannelSysGroupBaseInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException{
		return pchannelSysGroupServiceFacade.deleteChannelSysGroupBaseInfoMethod(headMap, paramMap);
	}
	
	/**
	 * 
	 * @Title: updateChannelSysGroupBaseInfoMethod
	 * @Description:修改pub_channel渠道分组信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	@Validation(value="c0000136")
	@Override
	public ResponseEntity updateChannelSysGroupBaseInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException{
		return pchannelSysGroupServiceFacade.updateChannelSysGroupBaseInfoMethod(headMap, paramMap);
	}
	/**
	 * 
	 * @Title: queryChannelSysGroupBaseInfoMethod
	 * @Description:查询pub_channel渠道分组信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	//@Validation(value="c0000134")
	@Override
	public ResponseEntity queryChannelSysGroupBaseInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException{
		return pchannelSysGroupServiceFacade.queryChannelSysGroupBaseInfoMethod(headMap, paramMap);
	}
	
	
}
