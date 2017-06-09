package bros.p.bankmanage.facade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPChannelSysGroupServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.channel.service.IChannelSysGroupService;
import bros.provider.parent.bankmanage.channel.service.IChannelSysInfoService;
/**
 * 
 * @ClassName: PChannelSysGroupServiceFacadeImpl 
 * @Description: 渠道系统分组对外接口实现类
 * @author gaoyongjing 
 * @date 2016年6月27日 下午15:35:28 
 * @version 1.0
 */

@Component("pchannelSysGroupServiceFacade")
public class PChannelSysGroupServiceFacadeImpl implements IPChannelSysGroupServiceFacade {
	/**
	 * 渠道系统分组服务
	 */
	@Autowired
	private IChannelSysGroupService channelSysGroupService;
	/**
	 * 渠道系统服务
	 */
	@Autowired
	private IChannelSysInfoService channelSysInfoService;
	
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
	@Validation(value="p0000101")
	@Override
	public ResponseEntity addChannelSysGroupMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		
		String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));//法人ID
		String chlSysCode = (String) (paramMap.get("chlSysCode")==null?"":paramMap.get("chlSysCode"));//渠道系统分组编号
		String chlSysName = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("chlSysName"));//渠道系统分组名称
		String chlSysDesc = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("chlSysDesc"));//渠道系统分组描述
		
		//查询渠道分组信息是否存在
		Map<String,Object> chlSysGroupMap = new HashMap<String,Object>();
		chlSysGroupMap = channelSysGroupService.queryChannelSysGroupBySysCodeMethod(legalPersonId,chlSysCode);
		
		if(chlSysGroupMap != null && chlSysGroupMap.size() > 0){
			//已经存在不允许添加
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0001);
		}else{
			//新增渠道分组信息
			channelSysGroupService.addChannelSysGroupMethod(legalPersonId, chlSysCode, chlSysName, chlSysDesc);
		}
		return entity;
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
	@Validation(value="p0000102")
	@Override
	public ResponseEntity updateChannelSysGroupMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		//查询渠道分组信息是否存在
		Map<String,Object> chlSysGroupMap = new HashMap<String,Object>();
		String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));//法人ID
		String chlSysCode = (String) (paramMap.get("chlSysCode")==null?"":paramMap.get("chlSysCode"));//渠道系统分组编号
		chlSysGroupMap = channelSysGroupService.queryChannelSysGroupBySysCodeMethod(legalPersonId,chlSysCode);
		
		if(chlSysGroupMap == null || chlSysGroupMap.size() <= 0){
			//渠道分组信息不存在 不允许修改
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0111);
		}
		
		channelSysGroupService.updateChannelSysGroupMethod(paramMap);
		
		return entity;
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
	@Validation(value="p0000103")
	@Override
	public ResponseEntity deleteChannelSysGroupMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		
		String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));//法人ID
		String chlSysCode = (String) (paramMap.get("chlSysCode")==null?"":paramMap.get("chlSysCode"));//渠道系统分组编号
		
		//temp1 查询渠道分组信息是否存在
		Map<String,Object> chlSysGroupMap = new HashMap<String,Object>();
		chlSysGroupMap = channelSysGroupService.queryChannelSysGroupBySysCodeMethod(legalPersonId,chlSysCode);
		
		if(chlSysGroupMap == null || chlSysGroupMap.size() <= 0){
			//渠道分组信息不存在 不允许删除
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0112);
		}
		
		//temp2 查询渠道分组下是否有渠道系统信息
		Map<String,Object> parMap = new HashMap<String,Object>();
		List<Map<String,Object>> chlSysList = new ArrayList<Map<String,Object>>();
		
		parMap.put("legalPersonId", legalPersonId);
		parMap.put("chlSysCode", chlSysCode);
		
		chlSysList = channelSysInfoService.queryChannelSysInfoMethod(parMap);
		
		if(chlSysList != null && chlSysList.size() > 0){
			//存在下级渠道系统信息,不允许删除
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0152);
		} 
		
		//删除渠道分组信息
		channelSysGroupService.deleteChannelSysGroupMethod(legalPersonId, chlSysCode);
		
		return entity;
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
	@Override
	public ResponseEntity queryChannelSysGroupMethod(Map<String, Object> headMap,Map<String, Object> paramMap)
			throws ServiceException {
		
		String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));//法人ID
		
		Map<String, Object> chlSysGroupMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> chlSysGroupList = new ArrayList<Map<String,Object>>();
		
		chlSysGroupList = channelSysGroupService.queryChannelSysGroupMethod(legalPersonId);
		
		chlSysGroupMap.put("returnList", chlSysGroupList);
		
		ResponseEntity entity = new ResponseEntity(chlSysGroupMap);
		
		return entity;
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
	@Validation(value="p0000129")
	@Override
	public ResponseEntity queryChannelSysGroupPageMethod(Map<String, Object> headMap,Map<String, Object> paramMap)
			throws ServiceException {
		String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));//法人ID
		int pageNo = (Integer)paramMap.get("pageNo");  //  页码
		int pageSize = (Integer)paramMap.get("pageSize");  //  每页条数
		
		Map<String, Object> chlSysGroupMap = new HashMap<String, Object>();
		List<Map<String,Object>> chlSysGroupList = new ArrayList<Map<String,Object>>();
		//渠道分组列表
		chlSysGroupList = channelSysGroupService.queryChannelSysGroupPageMethod(legalPersonId, pageNo, pageSize);
		//总条数
		int totalNum = channelSysGroupService.queryChannelSysGroupTotalNumMethod(legalPersonId);
		
		chlSysGroupMap.put("returnList", chlSysGroupList);
		chlSysGroupMap.put("totalNum", totalNum);
		
		ResponseEntity entity = new ResponseEntity(chlSysGroupMap);
		
		return entity;
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
	@Validation(value="p0000124")
	public ResponseEntity queryChlSysGroupAndChlInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap)
			throws ServiceException {
		Map<String,Object> returnMap = new HashMap<String, Object>();
		
		String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));//法人ID
		//temp1 根据法人id查询所有渠道分组信息
		List<Map<String,Object>> channelSysGroupList = new ArrayList<Map<String,Object>>();
		
		channelSysGroupList = channelSysGroupService.queryChannelSysGroupMethod(legalPersonId);
		
		returnMap.put("channelSysGroupList", channelSysGroupList);//渠道分组信息
		
		//temp2 根据法人id查询所有渠道信息 
		paramMap.put("chlFlag", "1");
		paramMap.put("legalPersonId", legalPersonId);
		
		List<Map<String,Object>> chlInfoList = channelSysInfoService.queryChannelSysInfoMethod(paramMap);
		
		returnMap.put("chlInfoList", chlInfoList);//渠道信息
		
		ResponseEntity entity = new ResponseEntity(returnMap);//渠道信息
		
		return entity;
	}
	
	
	/********************pub_channel*************************/
	/**
	 * 
	 * @Title: queryChlSysGroupAndChlInfoMethod
	 * @Description:查询pub_channel渠道系统信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	//@Validation(value="p0000124")
	public ResponseEntity queryChannelSysGroupBaseInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap)
			throws ServiceException {
		Map<String,Object> returnMap = new HashMap<String, Object>();
		
		//temp1 根据法人id查询所有渠道分组信息
		List<Map<String,Object>> channelSysGroupList = new ArrayList<Map<String,Object>>();
		
		channelSysGroupList = channelSysGroupService.queryChannelSysGroupBaseInfoMethod();
		
		returnMap.put("returnList", channelSysGroupList);//渠道分组信息
		
		ResponseEntity entity = new ResponseEntity(returnMap);//渠道信息
		
		return entity;
	}
	
	/**
	 * 
	 * @Title: deleteChlSysGroupAndChlInfoMethod
	 * @Description:删除pub_channel渠道系统信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	@Validation(value="p0000135")
	public ResponseEntity deleteChannelSysGroupBaseInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap)
			throws ServiceException {
		Map<String,Object> returnMap = new HashMap<String, Object>();
		
		String channelCode = (String) (paramMap.get("channelCode")==null?"":paramMap.get("channelCode"));//渠道系统分组编号
		
		//temp1 查询渠道分组信息是否存在
		Map<String,Object> chlSysGroupMap = new HashMap<String,Object>();
		chlSysGroupMap = channelSysGroupService.queryChannelSysGroupBaseInfoByCode(channelCode);
		
		if(chlSysGroupMap == null || chlSysGroupMap.size() <= 0){
			//渠道分组信息不存在 不允许删除
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0112);
		}

		 channelSysGroupService.deleteChannelSysGroupBaseInfoMethod(paramMap);
		
		
		ResponseEntity entity = new ResponseEntity(returnMap);//渠道信息
		
		return entity;
	}
	
	/**
	 * 
	 * @Title: updateChlSysGroupAndChlInfoMethod
	 * @Description:修改pub_channel渠道系统信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	@Validation(value="p0000136")
	public ResponseEntity updateChannelSysGroupBaseInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap)
			throws ServiceException {
		Map<String,Object> returnMap = new HashMap<String, Object>();
		
		String channelCode = (String) (paramMap.get("channelCode")==null?"":paramMap.get("channelCode"));//渠道系统分组编号
		
		//temp1 查询渠道分组信息是否存在
		Map<String,Object> chlSysGroupMap = new HashMap<String,Object>();
		chlSysGroupMap = channelSysGroupService.queryChannelSysGroupBaseInfoByCode(channelCode);
		
		if(chlSysGroupMap == null || chlSysGroupMap.size() <= 0){
			//渠道分组信息不存在 不允许跟新
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0111);
		}
				
		 channelSysGroupService.updateChannelSysGroupBaseInfoMethod(paramMap);
		
		
		ResponseEntity entity = new ResponseEntity(returnMap);//渠道信息
		
		return entity;
	}
	
	/**
	 * 
	 * @Title: addChlSysGroupAndChlInfoMethod
	 * @Description:新增pub_channel渠道系统信息
	 * @param headMap  头信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	@Validation(value="p0000134")
	public ResponseEntity addChannelSysGroupBaseInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap)
			throws ServiceException {
		Map<String,Object> returnMap = new HashMap<String, Object>();
		
		String channelCode = (String) (paramMap.get("channelCode")==null?"":paramMap.get("channelCode"));//渠道系统分组编号
		
		//temp1 查询渠道分组信息是否存在
		Map<String,Object> chlSysGroupMap = new HashMap<String,Object>();
		chlSysGroupMap = channelSysGroupService.queryChannelSysGroupBaseInfoByCode(channelCode);
		
		if(chlSysGroupMap == null ){
			channelSysGroupService.addChannelSysGroupBaseInfoMethod(paramMap);
		}else{
			//渠道分组信息不存在 不允许跟新
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0113);
		}
		
		
		
		
		ResponseEntity entity = new ResponseEntity(returnMap);//渠道信息
		
		return entity;
	}
}
