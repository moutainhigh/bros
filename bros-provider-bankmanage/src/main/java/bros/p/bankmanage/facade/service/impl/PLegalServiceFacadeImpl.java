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
import bros.p.bankmanage.facade.service.IPLegalServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.legal.service.ILegalService;
/** 
 * @ClassName:PLegalServiceFacadeImpl  
 * @Description:法人系统对外接口实现类
 * @author  haojinhui
 * @date 2016年7月7日 下午3:09:35 
 * @version V1.0  
 */
@Component("plegalServiceFacade")
public class PLegalServiceFacadeImpl implements IPLegalServiceFacade  {
	/**
	 * 法人系统实现类
	 */
	@Autowired
	private ILegalService legalService;

	/**
	 * 
	 * @Title: addLegal
	 * @Description: 增加法人
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000027")
	public ResponseEntity addLegal(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> legalMap = new HashMap<String, Object>();
		legalMap = legalService.queryLegal(headMap,bodyMap);
		
		if((Integer)legalMap.get("totalNum") != 0){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0011);
		}else{
			legalService.addLegal(headMap,bodyMap);
		}
		return entity;
	}
	/**
	 * 
	 * @Title: queryLegal
	 * @Description: 法人查询
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryLegal(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		ResponseEntity entity = new ResponseEntity();
		Map<String, Object> legalMap = new HashMap<String, Object>();
		legalMap = legalService.queryLegal(headMap,bodyMap);
		if(legalMap != null && legalMap.size() > 0){			
			entity = new ResponseEntity(legalMap);				
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0012);
		}
		return entity;
	}
	
	/**
	 * 分页查询法人信息
	 */
	public ResponseEntity queryLegalPage(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String cllCode=bodyMap.get("cllCode")==null?"":bodyMap.get("cllCode").toString();
		String cllStatus=bodyMap.get("cllStatus")==null?"":bodyMap.get("cllStatus").toString();
		String pageNo=String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo"));
		String pageSize=String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize"));
		responseMap = legalService.queryLegalPage(cllCode, cllStatus, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		String totalNum=responseMap.get("totalNum")==null?"0":responseMap.get("totalNum").toString();
		if(!totalNum.equals("0")){			
			entity = new ResponseEntity(responseMap);				
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0012);
		}
		return entity;
	}
	/**
	 * 
	 * @Title: updateLegal
	 * @Description: 法人修改
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000029")
	public ResponseEntity updateLegal(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> legalMap = new HashMap<String, Object>();
		Map<String, Object> legalRequstMap = new HashMap<String, Object>();
		legalRequstMap.put("cllCode", (String)bodyMap.get("cllCode"));
		legalMap = legalService.queryLegal(headMap,legalRequstMap);
		List<Map<String, Object>> returnList  =  new ArrayList<Map<String, Object>>();
		returnList = (List<Map<String, Object>>) legalMap.get("returnList");
		
		if((Integer)legalMap.get("totalNum") != 0){
			if(!((String)returnList.get(0).get("cllStatus")).equals((String)bodyMap.get("cllStatus"))){
				if(!((String)returnList.get(0).get("cllStatus")).equals("2")){
					throw new ServiceException(BankManageErrorCodeConstants.PBAE0013);
				}
			}else{
				legalService.updateLegal(headMap,bodyMap);
			}
		}else{
			
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0012);		
		}
		return entity;
	}
	/**
	 * 
	 * @Title: deleteLegal
	 * @Description: 删除法人信息
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000030")
	public ResponseEntity deleteLegal(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> legalMap = new HashMap<String, Object>();
		legalMap = legalService.queryLegal(headMap,bodyMap);
		if((Integer)legalMap.get("totalNum") != 0){
			legalService.deleteLegal(headMap,bodyMap);
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0012);		
		}
		return entity;
	}

	/**
	 * 
	 * @Title: querytreeLegal
	 * @Description: 查询法人树形数据
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000026")
	public ResponseEntity querytreeLegal(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		ResponseEntity entity = new ResponseEntity();
		Map<String, Object> legalMap = new HashMap<String, Object>();
		legalMap = legalService.queryLegal(headMap,bodyMap);
		if(legalMap != null && legalMap.size() > 0){
			Map<String, Object> legalListMap = new HashMap<String, Object>();
			String cllCode = (String) (bodyMap.get("cllCode")==null?"":bodyMap.get("cllCode"));//法人编号
			String cllStatus = (String) (bodyMap.get("cllStatus")==null?"":bodyMap.get("cllStatus"));//法人状态
			legalListMap = legalService.querytreeLegal(cllCode,cllStatus);
			entity = new ResponseEntity(legalListMap);				
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0012);
		}
		return entity;
	}
}
