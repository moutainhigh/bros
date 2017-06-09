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
import bros.p.bankmanage.facade.service.IPMallInfoServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.channel.service.IChannelSysGroupService;
import bros.provider.parent.bankmanage.channel.service.IChannelSysInfoService;
import bros.provider.parent.bankmanage.mall.service.IMallInfoService;
import bros.provider.parent.bankmanage.mall.service.IMallStyleService;
import bros.provider.parent.bankmanage.shelf.service.IShelfInfoService;
/**
 * 
 * @ClassName: PMallInfoServiceFacadeImpl 
 * @Description: 商城信息维护对外接口实现类
 * @author gaoyongjing 
 * @date 2016年6月30日 下午15:35:28 
 * @version 1.0
 */
@Component("pmallInfoServiceFacade")
public class PMallInfoServiceFacadeImpl implements IPMallInfoServiceFacade {
	/**
	 * 商城信息服务
	 */
	@Autowired
	private IMallInfoService mallInfoService;
	/**
	 * 商城样式服务
	 */
	@Autowired
	private IMallStyleService mallStyleService;
	/**
	 * 货架信息服务
	 */
	@Autowired
	private IShelfInfoService shelfInfoService;
	/**
	 * 渠道系统服务
	 */
	@Autowired
	private IChannelSysInfoService channelSysInfoService;
	/**
	 * 渠道系统分组服务
	 */
	@Autowired
	private IChannelSysGroupService channelSysGroupService;
	/**
	 * 
	 * @Title: addMallInfoMethod
	 * @Description: 新增商城信息
	 * @param headMap  头信息
	 * @param paramMap  商城信息，包括以下
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param mallDesc 商城描述
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @param pageName 登录首页页面名称
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="p0000106")
	@Override
	public ResponseEntity addMallInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		String mallCode = (String) (paramMap.get("mallCode")==null?"":paramMap.get("mallCode"));//商城编码  
		String mallName = (String) (paramMap.get("mallName")==null?"":paramMap.get("mallName"));//商城名称
		String mallDesc = (String) (paramMap.get("mallDesc")==null?"":paramMap.get("mallDesc"));//商城描述
		String chlId = (String) (paramMap.get("chlId")==null?"":paramMap.get("chlId"));         //渠道唯一id
		String branchNo = (String) (paramMap.get("branchNo")==null?"":paramMap.get("branchNo"));//机构号  
		String pageName = (String) (paramMap.get("pageName")==null?"":paramMap.get("pageName"));//登录页名称
		
		//temp1 查询渠道信息是否存在
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("chlId", chlId);
		Map<String,Object> chlSysInfoMap = new HashMap<String,Object>();
		chlSysInfoMap = channelSysInfoService.queryChannelSysInfoByObjectIdMethod(param);
		if(chlSysInfoMap == null || chlSysInfoMap.size() <= 0){
			//渠道系统信息不存在，不允许添加商城
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0116);
		}
		//temp2 查询当前渠道中商城编码是否存在
		Map<String,Object> mallInfoMap1 = new HashMap<String,Object>();
		mallInfoMap1 = mallInfoService.queryMallInfoByObjectIdMethod(mallCode,"",chlId,"");
		if(mallInfoMap1 != null && mallInfoMap1.size() > 0){
			//已经存在不允许添加
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0101);
		}
				
		//temp3 查询商城信息是否存在
		Map<String,Object> mallInfoMap = new HashMap<String,Object>();
		mallInfoMap = mallInfoService.queryMallInfoByBranchNoMethod(branchNo,chlId);
		if(mallInfoMap != null && mallInfoMap.size() > 0){
			//该渠道机构对应的商城信息已存在,不允许重复添加
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0125);
		}
		
		//temp4 新增商城信息
		mallInfoService.addMallInfoMethod(mallCode, mallName, mallDesc, chlId, branchNo, pageName);
		
		return entity;
		
	}
	/**
	 * 
	 * @Title: updateMallInfoMethod
	 * @Description: 修改商城信息
	 * @param headMap  头信息
	 * @param paramMap 商城信息包含以下参数
	 * @param mallId 商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param mallDesc 商城描述
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @param pageName 登录首页页面名称
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="p0000107")
	@Override
	public ResponseEntity updateMallInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		
		String mallId = (String) (paramMap.get("mallId")==null?"":paramMap.get("mallId"));      //商城唯一id
		String mallCode = (String) (paramMap.get("mallCode")==null?"":paramMap.get("mallCode"));//商城编码  
		String mallName = (String) (paramMap.get("mallName")==null?"":paramMap.get("mallName"));//商城名称  
		String mallDesc = (String) (paramMap.get("mallDesc")==null?"":paramMap.get("mallDesc"));//商城描述  
		String chlId = (String) (paramMap.get("chlId")==null?"":paramMap.get("chlId"));         //渠道唯一id
		String branchNo = (String) (paramMap.get("branchNo")==null?"":paramMap.get("branchNo"));//机构号   
		String pageName = (String) (paramMap.get("pageName")==null?"":paramMap.get("pageName"));//登录页名称 
		
		//校验必输项
		//判空条件：当商城唯一标识为空时，商城编码、渠道标识都不能为空
		//        当商城唯一标识不为空时，商城编码、渠道标识则不需要校验空值
		if("".equals(mallId)){
			if("".equals(mallCode) || "".equals(chlId)){
				//商城唯一标识为空时商城编码、渠道标识不能为空
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0122);
			}
		}
				
		//temp1 查询商城信息是否存在
		Map<String,Object> mallInfoMap = new HashMap<String,Object>();
		
		mallInfoMap = mallInfoService.queryMallInfoByObjectIdMethod(mallCode,branchNo,chlId,mallId);
		
		if(mallInfoMap == null || mallInfoMap.size() <= 0){
			//商城信息不存在，不允许修改
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0117);
		}
		
		//temp2 修改商城信息
		mallInfoService.updateMallInfoMethod(mallId, mallCode, mallName, mallDesc, chlId, branchNo, pageName);
		
		return entity;
	}

	/**
	 * 
	 * @Title: deleteMallInfoMethod
	 * @Description: 删除商城信息
	 * @param headMap  头信息
	 * @param paramMap 商城信息包含以下参数
	 * @param mallId 商城ID
	 * @param mallCode 商城编号
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity deleteMallInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		
		String mallId = (String) (paramMap.get("mallId")==null?"":paramMap.get("mallId"));      //商城唯一id
		String mallCode = (String) (paramMap.get("mallCode")==null?"":paramMap.get("mallCode"));//商城编码
		String chlId = (String) (paramMap.get("chlId")==null?"":paramMap.get("chlId"));         //渠道唯一id
		String branchNo = (String) (paramMap.get("branchNo")==null?"":paramMap.get("branchNo"));//机构号
		
		//校验必输项
		//判空条件：当商城唯一标识为空时，商城编码、渠道标识都不能为空
		//        当商城唯一标识不为空时，商城编码、渠道标识则不需要校验空值
		if("".equals(mallId)){
			if("".equals(mallCode) || "".equals(chlId)){
				//商城唯一标识为空时商城编码、渠道标识不能为空
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0122);
			}
		}
				
		//temp1 查询商城信息是否存在
		Map<String,Object> mallInfoMap = new HashMap<String,Object>();
		mallInfoMap = mallInfoService.queryMallInfoByObjectIdMethod(mallCode,branchNo,chlId,mallId);
		
		if(mallInfoMap == null || mallInfoMap.size() <= 0){
			//商城信息不存在，不允许删除
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0118);
		}
				
		//temp2 判断商城下是否有货架信息
		Map<String,Object> shelfInfoInputMap = new HashMap<String, Object>();
		List<Map<String,Object>> shelfInfoList = new ArrayList<Map<String,Object>>();
		//获取删除商城的ID值
		String queryShelfMallId = (String) (mallInfoMap.get("mallId")==null?"":mallInfoMap.get("mallId"));//商城唯一标识
		shelfInfoInputMap.put("mallId", queryShelfMallId);
		//根据商城id查询货架信息
		shelfInfoList = shelfInfoService.queryShelfInfoMethod(shelfInfoInputMap);
		
		if(shelfInfoList != null && shelfInfoList.size() > 0){
			//存在货架信息,不允许删除商城
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0108);
		}
		//temp3 删除商城信息
		mallInfoService.deleteMallInfoMethod(mallId, mallCode, chlId, branchNo);
		
		return entity;
	}

	/**
	 * 
	 * @Title: queryMallInfoMethod
	 * @Description: 查询商城信息
	 * @param headMap  头信息
	 * @param paramMap 商城信息包含以下参数
	 * @param mallId  商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @return ResponseEntity Map<String,Object> returnList商城信息列表
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryMallInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		Map<String,Object> mallInfoMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> mallInfoList = new ArrayList<Map<String,Object>>();
		
		mallInfoList = mallInfoService.queryMallInfoMethod(paramMap);
		
		mallInfoMap.put("returnList", mallInfoList);
		
		ResponseEntity entity = new ResponseEntity(mallInfoMap);
		
		return entity;
	}
	/**
	 * 
	 * @Title: queryMallInfoPageMethod
	 * @Description: 分页查询商城信息
	 * @param headMap  头信息
	 * @param paramMap 商城信息包含以下参数
	 * @param mallId  商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return ResponseEntity Map<String,Object> returnList商城信息列表 totalNum 总条数
	 * @throws ServiceException
	 */
	@Validation(value="p0000131")
	@Override
	public ResponseEntity queryMallInfoPageMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException {
		Map<String,Object> mallInfoMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> mallInfoList = new ArrayList<Map<String,Object>>();
		
		mallInfoList = mallInfoService.queryMallInfoPageMethod(paramMap);
		
		int totalNum = mallInfoService.queryMallTotalNumMethod(paramMap);
		
		mallInfoMap.put("returnList", mallInfoList);
		
		mallInfoMap.put("totalNum", totalNum);
		
		ResponseEntity entity = new ResponseEntity(mallInfoMap);
		
		return entity;
	}
	/**
	 * 
	 * @Title: addMallStyleMethod
	 * @Description: 新增商城样式
	 * @param headMap 头信息
	 * @param paramMap 商城样式信息包含以下参数
	 * @param mallId 商城ID
	 * @param styleId 样式ID
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="p0000108")
	@Override
	public ResponseEntity addMallStyleMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		
		String mallId = (String) (paramMap.get("mallId")==null?"":paramMap.get("mallId"));//商城id
		String styleId = (String) (paramMap.get("styleId")==null?"":paramMap.get("styleId")); //样式id
		
		//temp1 查询商城是否存在
		Map<String,Object> mallInfoMap = new HashMap<String,Object>();
		mallInfoMap = mallInfoService.queryMallInfoByObjectIdMethod("","","",mallId);
		
		if(mallInfoMap == null || mallInfoMap.size() <= 0){
			//商城信息不存在，不允许添加样式
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0123);
		}
		
		//temp2 查询商城样式是否存在
		Map<String,Object> mallStyleMap = new HashMap<String,Object>();
		
		mallStyleMap = mallStyleService.queryMallStyleByStyleIdAndMallIdMethod(mallId, styleId);
		
		if(mallStyleMap != null && mallStyleMap.size() > 0){
			//已经存在不允许添加
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0102);
		}
		
		//temp3 新增商城样式
		mallStyleService.addMallStyleMethod(mallId, styleId);
		return entity;
	}

	/**
	 * 
	 * @Title: deleteMallStyleMethod
	 * @Description: 删除商城样式
	 * @param headMap 头信息
	 * @param paramMap 商城样式信息包含以下参数
	 * @param mallId 商城ID
	 * @param styleId 样式ID
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="p0000108")
	@Override
	public ResponseEntity deleteMallStyleMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		
		String mallId = (String) (paramMap.get("mallId")==null?"":paramMap.get("mallId"));//商城id
		String styleId = (String) (paramMap.get("styleId")==null?"":paramMap.get("styleId")); //样式id
		
		//查询商城样式是否存在
		Map<String,Object> mallStyleMap = new HashMap<String,Object>();
		
		mallStyleMap = mallStyleService.queryMallStyleByStyleIdAndMallIdMethod(mallId, styleId);
		
		if(mallStyleMap == null || mallStyleMap.size() <= 0){
			//商城样式信息不存在，不允许删除
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0119);
		}
		mallStyleService.deleteMallStyleMethod(mallId, styleId);
		
		return entity;
	}
	/**
	 * 
	 * @Title: queryMallStyleMethod
	 * @Description: 查询商城样式
	 * @param headMap  头信息
	 * @param paramMap 商城样式信息包含以下参数
	 * @param mallId 商城ID
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return ResponseEntity Map<String,Object> returnList商城样式列表 totalNum 总条数
	 * @throws ServiceException
	 */
	@Validation(value="p0000109")
	@Override
	public ResponseEntity queryMallStyleMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		Map<String,Object> mallStyleMap = new HashMap<String,Object>();
		
		String mallId = (String) (paramMap.get("mallId")==null ? "" :paramMap.get("mallId")); // 商城id
		int pageNo = (Integer) (paramMap.get("pageNo")==null ? 0 :paramMap.get("pageNo")); //页码
		int pageSize = (Integer) (paramMap.get("pageSize")==null ? 0 :paramMap.get("pageSize"));//每页条数 
		
		mallStyleMap = mallStyleService.queryMallStyleMethod(mallId, pageNo, pageSize);
		
		ResponseEntity entity = new ResponseEntity(mallStyleMap);
		
		return entity;
	}

	/**
	 * 
	 * @Title: queryMallInfoByChlCodeLegalIdBranchNoMethod
	 * @Description: 根据法人id、渠道编码、机构号查询商城信息
	 * @param headMap  头信息
	 * @param paramMap 商城信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @param chlCode 渠道编码
	 * @param branchNo 机构号
	 * @return ResponseEntity Map<String,Object> mallInfoMap商城信息
	 * @throws ServiceException
	 */
	@Validation(value="p0000117")
	@Override
	public ResponseEntity queryMallInfoByChlCodeLegalIdBranchNoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		Map<String,Object> mallInfoMap = new HashMap<String,Object>();
		
		mallInfoMap = mallInfoService.queryMallInfoByChlCodeLegalIdBranchNoMethod(paramMap);
		
		if(mallInfoMap != null && mallInfoMap.size() > 0){
			ResponseEntity entity = new ResponseEntity(mallInfoMap);
			return entity;
		}else{
			ResponseEntity entity = new ResponseEntity();
			return entity;
		}
	}
	
	/**
	 * 
	 * @Title: queryChlSysGroupChlInfoAndMallInfoMethod
	 * @Description: 查询渠道及商场信息
	 * @param headMap  头信息
	 * @param paramMap 上送报文
	 * @param legalPersonId  法人ID
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	@Validation(value="p0000127")
	public ResponseEntity queryChlSysGroupChlInfoAndMallInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap)
			throws ServiceException {
		Map<String,Object> returnMap = new HashMap<String, Object>();
		
		String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));//法人ID
		//temp1 根据法人id查询所有渠道分组信息
		List<Map<String,Object>> channelSysGroupList = new ArrayList<Map<String,Object>>();
		
		channelSysGroupList = channelSysGroupService.queryChannelSysGroupMethod(legalPersonId);
		
		returnMap.put("channelSysGroupList", channelSysGroupList);//渠道分组信息
		
//		temp2 根据法人id查询所有渠道信息 
		paramMap.put("chlFlag", "1");
		paramMap.put("legalPersonId", legalPersonId);
		List<Map<String,Object>> chlInfoList = channelSysInfoService.queryChannelSysInfoMethod(paramMap);
		
		returnMap.put("chlInfoList", chlInfoList);//渠道信息
		
		//temp3 查询商城信息
		List<Map<String,Object>> mallInfoList = new ArrayList<Map<String,Object>>();
		
		mallInfoList = mallInfoService.queryMallInfoMethod(paramMap);
		 
		returnMap.put("mallInfoList", mallInfoList);//商城信息
		
		ResponseEntity entity = new ResponseEntity(returnMap);
		
		return entity;
	}
	
}
