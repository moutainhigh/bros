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
import bros.p.bankmanage.facade.service.IPChannelSysInfoServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.channel.service.IChannelSysGroupService;
import bros.provider.parent.bankmanage.channel.service.IChannelSysInfoService;
import bros.provider.parent.bankmanage.mall.service.IMallInfoService;

/**
 * 
 * @ClassName: PChannelSysInfoServiceFacadeImpl 
 * @Description: 渠道系统对外接口
 * @author gaoyongjing 
 * @date 2016年6月28日 下午15:35:28 
 * @version 1.0
 */
@Component("pchannelSysInfoServiceFacade")
public class PChannelSysInfoServiceFacadeImpl implements IPChannelSysInfoServiceFacade {
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
	 * 商城信息服务
	 */
	@Autowired
	private IMallInfoService mallInfoService;
	/**
	 * 
	 * @Title: addChannelSysInfoMethod
	 * @Description: 新增渠道
	 * @param headMap  头信息
	 * @param paramMap  渠道信息，包括以下
	 * @param chlCode 渠道编号
	 * @param legalPersonId 法人ID
	 * @param chlName 系统名称
	 * @param chlDesc 系统描述
	 * @param chlSysCode 系统分组编号
	 * @param chlFlag 启停状态
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="p0000105")
	@Override
	public ResponseEntity addChannelSysInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		String chlCode = (String) (paramMap.get("chlCode")==null?"":paramMap.get("chlCode"));  // 渠道系统编号
		String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));  // 机构法人ID
		String chlName = (String) (paramMap.get("chlName")==null?"":paramMap.get("chlName"));  //  系统名称
		String chlDesc = (String) (paramMap.get("chlDesc")==null?"":paramMap.get("chlDesc"));  //  系统描述
		String chlSysCode = (String) (paramMap.get("chlSysCode")==null?"":paramMap.get("chlSysCode"));  //  系统分组编号
		String chlFlag = (String) (paramMap.get("chlFlag")==null?"1":paramMap.get("chlFlag"));  //  启停状态
		
		//temp1 查询渠道分组信息是否存在
		Map<String,Object> chlSysGroupMap = new HashMap<String,Object>();
		
		chlSysGroupMap = channelSysGroupService.queryChannelSysGroupBySysCodeMethod(legalPersonId, chlSysCode);
		
		if(chlSysGroupMap == null || chlSysGroupMap.size() <= 0){
			//渠道分组信息不存在 不允许新增渠道系统
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0113);
		}
		
		//temp2 查询渠道编码是否存在
		Map<String,Object> chlSysInfoMap = new HashMap<String,Object>();
		Map<String,Object> selectParamMap = new HashMap<String, Object>();
		selectParamMap.put("legalPersonId", legalPersonId);//法人id
		selectParamMap.put("chlCode", chlCode);//渠道编码
		chlSysInfoMap = channelSysInfoService.queryChannelSysInfoByObjectIdMethod(paramMap);
		
		if(chlSysInfoMap != null && chlSysInfoMap.size() > 0){
			//已经存在不允许添加
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0151);
		}
		
		//temp3 新增渠道信息
		channelSysInfoService.addChannelSysInfoMethod(chlCode,legalPersonId,chlName,chlDesc,chlSysCode,chlFlag);
		
		return entity;
	}

	/**
	 * 
	 * @Title: updateChannelSysInfoMethod
	 * @Description: 修改渠道信息
	 * @param headMap  头信息
	 * @param paramMap 渠道信息包含以下参数
	 * @param chlId 渠道唯一标识
	 * @param chlCode 渠道编号
	 * @param legalPersonId 法人ID
	 * @param chlName 系统名称
	 * @param chlDesc 系统描述
	 * @param chlSysCode 系统分组编号
	 * @param chlFlag 启停状态
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity updateChannelSysInfoMethod(
			Map<String, Object> headMap, Map<String, Object> paramMap)
			throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		String chlId = (String) (paramMap.get("chlId")==null?"":paramMap.get("chlId"));  //  渠道系统唯一标识
		String chlCode = (String) (paramMap.get("chlCode")==null?"":paramMap.get("chlCode"));  // 渠道系统编号
		String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));  // 机构法人ID
		String chlName = (String) (paramMap.get("chlName")==null?"":paramMap.get("chlName"));  //  系统名称
		String chlDesc = (String) (paramMap.get("chlDesc")==null?"":paramMap.get("chlDesc"));  //  系统描述
		String chlSysCode = (String) (paramMap.get("chlSysCode")==null?"":paramMap.get("chlSysCode"));  //  系统分组编号
		String chlFlag = (String) (paramMap.get("chlFlag")==null?"1":paramMap.get("chlFlag"));  //  启停状态
		//校验必输项
		//判空条件：当渠道唯一标识为空时，渠道编码、法人id、渠道系统分组都不能为空
		//        当渠道唯一标识不为空时，渠道编码、法人id、渠道系统分组则不需要校验空值
		if("".equals(chlId)){
			if("".equals(chlCode) || "".equals(legalPersonId) || "".equals(chlSysCode)){
				//渠道唯一标识与渠道编码、法人ID、渠道系统分组不能同时为空
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0121);
			}
		}
		//temp1 查询渠道信息是否存在
		Map<String,Object> chlSysInfoMap = new HashMap<String,Object>();
		
		chlSysInfoMap = channelSysInfoService.queryChannelSysInfoByObjectIdMethod(paramMap);
		
		if(chlSysInfoMap == null || chlSysInfoMap.size() <= 0){
			//渠道系统信息不存在，不允许修改
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0114);
		}
		
		channelSysInfoService.updateChannelSysInfoMethod(chlId,chlCode,legalPersonId,chlName,chlDesc,chlSysCode,chlFlag);
		
		return entity;
	}
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
	@Validation(value="p0000128")
	@Override
	public ResponseEntity updateChannelSysStatusMethod(Map<String, Object> headMap, Map<String, Object> paramMap)
			throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		String chlId = (String) (paramMap.get("chlId")==null?"":paramMap.get("chlId"));  //  渠道系统唯一标识
		String chlCode = (String) (paramMap.get("chlCode")==null?"":paramMap.get("chlCode"));  // 渠道系统编号
		String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));  // 机构法人ID
		String chlSysCode = (String) (paramMap.get("chlSysCode")==null?"":paramMap.get("chlSysCode"));  //  系统分组编号
		String chlFlag = (String) (paramMap.get("chlFlag")==null?"1":paramMap.get("chlFlag"));  //  启停状态
		//校验必输项
		//判空条件：当渠道唯一标识为空时，渠道编码、法人id、渠道系统分组都不能为空
		//        当渠道唯一标识不为空时，渠道编码、法人id、渠道系统分组则不需要校验空值
		if("".equals(chlId)){
			if("".equals(chlCode) || "".equals(legalPersonId) || "".equals(chlSysCode)){
				//渠道唯一标识与渠道编码、法人ID、渠道系统分组不能同时为空
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0121);
			}
		}
		//temp1 查询渠道信息是否存在
		Map<String,Object> chlSysInfoMap = new HashMap<String,Object>();
		
		chlSysInfoMap = channelSysInfoService.queryChannelSysInfoByObjectIdMethod(paramMap);
		
		if(chlSysInfoMap == null || chlSysInfoMap.size() <= 0){
			//渠道系统信息不存在，不允许修改
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0114);
		}
		
		channelSysInfoService.updateChannelSysStatusMethod(chlId, chlCode, legalPersonId, chlSysCode, chlFlag);
		
		return entity;
	}
	/**
	 * 
	 * @Title: deleteChannelSysInfoMethod
	 * @Description: 删除渠道信息
	 * @param headMap  头信息
	 * @param paramMap 渠道信息包含以下参数
	 * @param chlId 渠道系统唯一标识
	 * @param chlCode 渠道编号
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 系统分组编号
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity deleteChannelSysInfoMethod(
			Map<String, Object> headMap, Map<String, Object> paramMap)
			throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		//校验必输项
		String chlId = (String) (paramMap.get("chlId")==null?"":paramMap.get("chlId"));  //渠道系统唯一标识
		String chlCode = (String) (paramMap.get("chlCode")==null?"":paramMap.get("chlCode"));  //渠道系统编号
		String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));  //法人ID
		String chlSysCode = (String) (paramMap.get("chlSysCode")==null?"":paramMap.get("chlSysCode"));  //系统分组编号
		//判空条件：当渠道唯一标识为空时，渠道编码、法人id、渠道系统分组都不能为空
		//        当渠道唯一标识不为空时，渠道编码、法人id、渠道系统分组则不需要校验空值
		if("".equals(chlId)){
			if("".equals(chlCode) || "".equals(legalPersonId) || "".equals(chlSysCode)){
				//渠道唯一标识与渠道编码、法人ID、渠道系统分组不能同时为空
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0121);
			}
		}
		
		//temp1 查询渠道信息是否存在
		Map<String,Object> chlSysInfoMap = new HashMap<String,Object>();
		
		chlSysInfoMap = channelSysInfoService.queryChannelSysInfoByObjectIdMethod(paramMap);
		
		if(chlSysInfoMap == null || chlSysInfoMap.size() <= 0){
			//渠道信息不存在，不允许删除
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0115);
		}
		
		
		//temp2 查询渠道下是否有商城信息
		List<Map<String,Object>> mallInfoList = new ArrayList<Map<String,Object>>();
		Map<String,Object> mallInfoInputMap = new HashMap<String,Object>();
		
		String queryMallchlId = (String) (chlSysInfoMap.get("chlId")==null?"":chlSysInfoMap.get("chlId"));  //渠道系统唯一标识
		mallInfoInputMap.put("chlId", queryMallchlId);
		
		mallInfoList = mallInfoService.queryMallInfoMethod(mallInfoInputMap);
		
		if(mallInfoList != null && mallInfoList.size() > 0){
			//存在商城信息,不允许删除
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0103);
		}
				
		//temp3 删除渠道信息
		channelSysInfoService.deleteChannelSysInfoMethod(chlId,chlCode,legalPersonId,chlSysCode);
		
		return entity;
	}

	/**
	 * 
	 * @Title: queryChannelSysInfoMethod
	 * @Description: 查询渠道信息
	 * @param headMap  头信息
	 * @param paramMap 渠道信息包含以下参数
	 * @param chlId 渠道唯一标识
	 * @param chlCode 渠道编号
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 系统分组编号
	 * @return ResponseEntity Map<String,Object> returnList渠道信息列表
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryChannelSysInfoMethod(Map<String, Object> headMap, Map<String, Object> paramMap)
			throws ServiceException {
		Map<String,Object> chlInfoMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> chlList = new ArrayList<Map<String,Object>>();
		
		chlList = channelSysInfoService.queryChannelSysInfoMethod(paramMap);
		
		chlInfoMap.put("returnList", chlList);
		
		ResponseEntity entity = new ResponseEntity(chlInfoMap);
		
		return entity;
	}
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
	@Validation(value="p0000130")
	@Override
	public ResponseEntity queryChannelSysInfoPageMethod(Map<String, Object> headMap, Map<String, Object> paramMap)
			throws ServiceException {
		Map<String,Object> chlInfoMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> chlList = new ArrayList<Map<String,Object>>();
		//渠道信息列表
		chlList = channelSysInfoService.queryChannelSysInfoPageMethod(paramMap);
		//总条数
		int totalNum = channelSysInfoService.queryChannelSysTotalNumMethod(paramMap);
		
		chlInfoMap.put("returnList", chlList);
		
		chlInfoMap.put("totalNum", totalNum);
		
		ResponseEntity entity = new ResponseEntity(chlInfoMap);
		
		return entity;
	}
}
