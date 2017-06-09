package bros.p.bankmanage.facade.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPBranchManageServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.branchmanage.service.IBranchManageService;

/** 
 * @ClassName: PBranchManageServiceFacadeImpl 
 * @Description: 机构管理对外接口实现类
 * @author MacPro 
 * @date 2016年6月28日 上午9:24:49 
 * @version 1.0 
 */
@Component("pbranchManageServiceFacade")
public class PBranchManageServiceFacadeImpl implements IPBranchManageServiceFacade{

	
	@Autowired
	IBranchManageService branchManageService;
	/** 
	 * @Title: addBranch 
	 * @Description: 添加机构
	 * @param headMap
	 * @param contextMap
	 * @throws ServiceException 异常信息
	 */
	
	@Validation(value="p0000048")
	public ResponseEntity addBranch(Map<String, Object> headMap, Map<String, Object> contextMap) throws ServiceException {
		
		ResponseEntity responseEntity = new ResponseEntity();
		
		// 判断机构是否存在入参
		Map<String,Object> parMap = new HashMap<String,Object>();
		parMap.putAll(headMap);
		parMap.putAll(contextMap);
		
		// 判断机构是否存在
		Map<String,Object> result = branchManageService.queryAllBranchByObjectId(parMap);
		
		if(result != null){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0051);
		}
		
		// 添加添加
		branchManageService.addBranch(headMap, contextMap);
		
		return responseEntity;
	}

	/** 
	 * @Title: updateBranch 
	 * @Description: 修改机构
	 * @param headMap
	 * @param contextMap
	 * @throws ServiceException 异常信息
	 */
	
	@Validation(value="p0000049")
	public ResponseEntity updateBranch(Map<String, Object> headMap, Map<String, Object> contextMap) throws ServiceException {
		
		ResponseEntity responseEntity = new ResponseEntity();
		
		// 判断机构是否存在入参
		Map<String,Object> parMap = new HashMap<String,Object>();
		parMap.putAll(headMap);
		parMap.putAll(contextMap);
		
		// 判断机构是否存在（存在允许修改）
		Map<String,Object> result = branchManageService.queryAllBranchByObjectId(parMap);
		
		if(result == null){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0052);
		}
		
		branchManageService.updateBranch(headMap, contextMap);
		
		return responseEntity;
		
	}

	/** 
	 * @Title: deleteBranch 
	 * @Description: 删除机构
	 * @param headMap
	 * @param contextMap
	 * @throws ServiceException  异常信息
	 */
	
	@Validation(value="p0000050")
	public ResponseEntity deleteBranch(Map<String, Object> headMap, Map<String, Object> contextMap) throws ServiceException {
		
		ResponseEntity responseEntity = new ResponseEntity();
		
		// 判断机构是否存在入参
		Map<String,Object> parMap = new HashMap<String,Object>();
		parMap.putAll(headMap);
		parMap.putAll(contextMap);
		
		// 判断机构是否存在（存在允许修改）
		Map<String,Object> result = branchManageService.queryAllBranchByObjectId(parMap);
		
		if(result == null){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0053);
		}
		
		branchManageService.deleteBranch(headMap, contextMap);
		
		return responseEntity;
		
	}

	/** 
	 * @Title: queryAllBaranch 
	 * @Description: 机构列表信息查询
	 * @param headMap
	 * @param contextMap
	 * @return
	 * @throws ServiceException 异常信息
	 */
	public ResponseEntity queryAllBaranch(Map<String, Object> headMap, Map<String, Object> contextMap) throws ServiceException {

		ResponseEntity responseEntity = new ResponseEntity();
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap = branchManageService.queryAllBaranch(headMap,contextMap);
		if(returnMap != null && returnMap.size() > 0){
			
			responseEntity = new ResponseEntity(returnMap);				
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0054);
		}
		return responseEntity;
	}

}
