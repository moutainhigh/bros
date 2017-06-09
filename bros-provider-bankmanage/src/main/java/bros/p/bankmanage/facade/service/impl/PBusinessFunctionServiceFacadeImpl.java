package bros.p.bankmanage.facade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.ValidateUtil;
import bros.p.bankmanage.facade.service.IPBusinessFunctionServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.bsndef.service.IBusinessFunctionService;
@Component("pbusinessFunctionServiceFacade")
public class PBusinessFunctionServiceFacadeImpl implements
		IPBusinessFunctionServiceFacade {
	@Autowired
	IBusinessFunctionService businessFunctionService;
	/**
	 * 查询业务功能信息
	 */
	public ResponseEntity queryBsnFun(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		String bbfChannel = bodyMap.get("bbfChannel")==null?"":(String)bodyMap.get("bbfChannel");
		String bbfId = bodyMap.get("bbfId")==null?"":(String)bodyMap.get("bbfId");
		String bbfBsnCode = bodyMap.get("bbfBsnCode")==null?"":(String)bodyMap.get("bbfBsnCode");
		String bbfType = bodyMap.get("bbfType")==null?"":(String)bodyMap.get("bbfType");
		returnList=businessFunctionService.queryBsnFun(bbfChannel, bbfId, bbfBsnCode,bbfType);
		responseMap.put("returnList", returnList);
		ResponseEntity entity = new ResponseEntity(responseMap);
		return entity;
	}
	/**
	 * 查询业务与菜单关联功能
	 */
	public ResponseEntity queryBsnFunRelMenudef(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		String bbfChannel = bodyMap.get("bbfChannel")==null?"":(String)bodyMap.get("bbfChannel");
		String burlMenueId = bodyMap.get("burlMenueId")==null?"":(String)bodyMap.get("burlMenueId");
		String bbfType = bodyMap.get("bbfType")==null?"":(String)bodyMap.get("bbfType");
		String bbfId=bodyMap.get("bbfId")==null?"":(String)bodyMap.get("bbfId");
		returnList=businessFunctionService.queryBsnFunRelMenudef(bbfChannel, burlMenueId,bbfType,bbfId);
		responseMap.put("returnList", returnList);
		ResponseEntity entity = new ResponseEntity(responseMap);
		return entity;
	}
	/**
	 * 修改业务菜单关联
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public ResponseEntity updateBsnFunRelMenu(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> addList=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> updateList=new ArrayList<Map<String,Object>>();
		String bbfChannel = bodyMap.get("bbfChannel")==null?"":(String)bodyMap.get("bbfChannel");
		String burlMenueId = bodyMap.get("burlMenueId")==null?"":(String)bodyMap.get("burlMenueId");
		String bbfBsnCode = bodyMap.get("bbfBsnCode")==null?"":(String)bodyMap.get("bbfBsnCode");
		String bbfType = bodyMap.get("bbfType")==null?"":(String)bodyMap.get("bbfType");
		String bbfId="";
		if(!ValidateUtil.isEmpty(bbfBsnCode)){
			List<Map<String, Object>> tmpList=new ArrayList<Map<String,Object>>();
			tmpList=businessFunctionService.queryBsnFun(bbfChannel, "", bbfBsnCode,bbfType);
			if(tmpList!=null&&tmpList.size()>0){
				for(Map<String, Object> map:tmpList){
					bbfId=map.get("bbfId")==null?"":(String)map.get("bbfId");
				}
			}
		}
		//查询历史关联菜单
		returnList=businessFunctionService.queryBsnFunRelMenudef(bbfChannel, burlMenueId,bbfType,bbfId);
		List<Map<String, Object>> requestList=new ArrayList<Map<String,Object>>();
		requestList=(List<Map<String, Object>>)bodyMap.get("requestList");
		if(returnList!=null&&returnList.size()>0){
			//删除业务菜单关联信息
			businessFunctionService.deleteBsnFunRelMenu(returnList);
		}
		if(requestList!=null&&requestList.size()>0){
			//添加业务菜单关联信息
			businessFunctionService.insertBsnFunRelMenu(requestList);
		}
		responseMap.put("returnList", returnList);
		ResponseEntity entity = new ResponseEntity(responseMap);
		return entity;
	}
	/**
	 * 添加业务功能信息
	 */
	public ResponseEntity insertOneBsndef(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		String bbfBsnCode = bodyMap.get("bbfBsnCode")==null?"":(String)bodyMap.get("bbfBsnCode");
		String bbfName = bodyMap.get("bbfName")==null?"":(String)bodyMap.get("bbfName");
		String bbfAlias = bodyMap.get("bbfAlias")==null?"":(String)bodyMap.get("bbfAlias");
		String bbfDesc = bodyMap.get("bbfDesc")==null?"":(String)bodyMap.get("bbfDesc");
		String bbfType = bodyMap.get("bbfType")==null?"":(String)bodyMap.get("bbfType");
		String bbfBsnlv = bodyMap.get("bbfBsnlv")==null?"":(String)bodyMap.get("bbfBsnlv");
		String bbfGroup = bodyMap.get("bbfGroup")==null?"":(String)bodyMap.get("bbfGroup");
		String bbfUserlv = bodyMap.get("bbfUserlv")==null?"":(String)bodyMap.get("bbfUserlv");
		String bbfStt = bodyMap.get("bbfStt")==null?"":(String)bodyMap.get("bbfStt");
		String bbfUrl = bodyMap.get("bbfUrl")==null?"":(String)bodyMap.get("bbfUrl");
		String bbfRequireAuth = bodyMap.get("bbfRequireAuth")==null?"":(String)bodyMap.get("bbfRequireAuth");
		String bbfChannel = bodyMap.get("bbfChannel")==null?"":(String)bodyMap.get("bbfChannel");
		String bbfCanbatch = bodyMap.get("bbfCanbatch")==null?"":(String)bodyMap.get("bbfCanbatch");
		String bbfModel = bodyMap.get("bbfModel")==null?"":(String)bodyMap.get("bbfModel");
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		returnList=businessFunctionService.queryBsnFun(bbfChannel, "", bbfBsnCode,"");
		if(returnList!=null&&returnList.size()>0){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0010,"该业务编号已存在！");
		}else{
			businessFunctionService.insertOneBsndef(bbfBsnCode, bbfName, bbfAlias, bbfDesc, bbfType, bbfBsnlv, bbfGroup, bbfUserlv, bbfStt, bbfUrl, bbfRequireAuth, bbfChannel, bbfCanbatch, bbfModel);
		}
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	/**
	 * 修改业务功能信息
	 */
	public ResponseEntity updateOneBsndef(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		String bbfId = bodyMap.get("bbfId")==null?"":(String)bodyMap.get("bbfId");
		String bbfBsnCode = bodyMap.get("bbfBsnCode")==null?"":(String)bodyMap.get("bbfBsnCode");
		String bbfName = bodyMap.get("bbfName")==null?"":(String)bodyMap.get("bbfName");
		String bbfAlias = bodyMap.get("bbfAlias")==null?"":(String)bodyMap.get("bbfAlias");
		String bbfDesc = bodyMap.get("bbfDesc")==null?"":(String)bodyMap.get("bbfDesc");
		String bbfType = bodyMap.get("bbfType")==null?"":(String)bodyMap.get("bbfType");
		String bbfBsnlv = bodyMap.get("bbfBsnlv")==null?"":(String)bodyMap.get("bbfBsnlv");
		String bbfGroup = bodyMap.get("bbfGroup")==null?"":(String)bodyMap.get("bbfGroup");
		String bbfUserlv = bodyMap.get("bbfUserlv")==null?"":(String)bodyMap.get("bbfUserlv");
		String bbfStt = bodyMap.get("bbfStt")==null?"":(String)bodyMap.get("bbfStt");
		String bbfUrl = bodyMap.get("bbfUrl")==null?"":(String)bodyMap.get("bbfUrl");
		String bbfRequireAuth = bodyMap.get("bbfRequireAuth")==null?"":(String)bodyMap.get("bbfRequireAuth");
		String bbfChannel = bodyMap.get("bbfChannel")==null?"":(String)bodyMap.get("bbfChannel");
		String bbfCanbatch = bodyMap.get("bbfCanbatch")==null?"":(String)bodyMap.get("bbfCanbatch");
		String bbfModel = bodyMap.get("bbfModel")==null?"":(String)bodyMap.get("bbfModel");
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		returnList=businessFunctionService.queryBsnFun("", bbfId, "","");
		if(returnList!=null&&returnList.size()>0){
			businessFunctionService.updateOneBsndef(bbfId, bbfBsnCode, bbfName, bbfAlias, bbfDesc, bbfType, bbfBsnlv, bbfGroup, bbfUserlv, bbfStt, bbfUrl, bbfRequireAuth, bbfChannel, bbfCanbatch, bbfModel);
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0010,"该业务信息不存在！");
		}
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	/**
	 * 删除业务功能信息
	 */
	public ResponseEntity deleteOneBsndef(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		String bbfId = bodyMap.get("bbfId")==null?"":(String)bodyMap.get("bbfId");
		returnList=businessFunctionService.queryBsnFun("", bbfId, "","");
		if(returnList!=null&&returnList.size()>0){
			businessFunctionService.deleteOneBsndef(bbfId);
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0010,"该业务信息不存在！");
		}
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}

}
