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
import bros.p.bankmanage.facade.service.IPTellerServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.teller.service.ITellerService;

/** 
 * @ClassName: PTellerServiceFacadeImpl  
 * @Description:柜员系统对外接口实现类
 * @author  haojinhui
 * @date 2016年6月28日 上午9:45:58 
 * @version V1.0  
 */
@Component("ptellerServiceFacade")
public class PTellerServiceFacadeImpl implements IPTellerServiceFacade {
	
	/**
	 * 柜员系统实现类
	 */
	@Autowired
	private ITellerService tellerService;
	
	/**
	 * 
	 * @Title: addTeller
	 * @Description: 增加柜员
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000037")
	public ResponseEntity addTeller(Map<String,Object> headMap,Map<String, Object> contextMap) throws ServiceException {
		
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = tellerService.queryTellerById(headMap,contextMap);
		
		if((Integer)tellerMap.get("totalNum") != 0){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0002);
		}else{
			tellerService.addTeller(headMap,contextMap);
		}
		return entity;
	}
	
	/**
	 * 
	 * @Title: queryTellerById
	 * @Description: 柜员信息查询
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000038")
	public ResponseEntity queryTellerById(Map<String,Object> headMap,Map<String, Object> contextMap) throws ServiceException {
		
		ResponseEntity entity = new ResponseEntity();
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = tellerService.queryTellerById(headMap,contextMap);
		if(tellerMap != null && tellerMap.size() > 0){			
			entity = new ResponseEntity(tellerMap);				
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0003);
		}
		return entity;
	}
	
	/**
	 * 
	 * @Title: updateTeller
	 * @Description: 柜员信息修改
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000039")
	public ResponseEntity updateTeller(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = tellerService.queryTellerById(headMap,contextMap);
		
		if((Integer)tellerMap.get("totalNum") != 0){
			tellerService.updateTeller(headMap,contextMap);
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0003);		
		}
		return entity;
	}
	
	/**
	 * 
	 * @Title: deleteTeller
	 * @Description: 删除柜员信息
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000040")
	public ResponseEntity deleteTeller(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		List<Map<String, Object>> tellerList  =  new ArrayList<Map<String, Object>>();
		tellerMap = tellerService.queryTellerBybtrLegal(headMap,contextMap);
		tellerList = (List<Map<String, Object>>) tellerMap.get("tellerList");
		String btrId = (String)tellerList.get(0).get("btrId");
		if((Integer)tellerMap.get("totalNum") != 0){
			tellerService.deleteTeller(headMap,contextMap,btrId);
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0003);		
		}
		return entity;
	}
	
	/**
	 * 
	 * @Title: tellerLogin
	 * @Description: 柜员登录
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000041")
	public ResponseEntity tellerLogin(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		List<Map<String, Object>> tellerList  =  new ArrayList<Map<String, Object>>();
		tellerMap = tellerService.queryTellerBybtrLegal(headMap,contextMap);
		tellerList = (List<Map<String, Object>>) tellerMap.get("tellerList");
		if((Integer)tellerMap.get("totalNum") != 0){
			if(!((String)tellerList.get(0).get("btrValidateType")).equals((String)contextMap.get("btrValidateType")) ){
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0006);
			}
			if(((String)tellerList.get(0).get("btrStt")).equals("1") || ((String)tellerList.get(0).get("btrStt")).equals("3") || ((String)tellerList.get(0).get("btrStt")).equals("5")){
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0004);
			}
			if(((String)tellerList.get(0).get("btrLoginstt")).equals("0")){
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0005);
			}
			if(!((String)tellerList.get(0).get("btrPwd")).equals((String)contextMap.get("btrPwd"))){
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0007);
			}
			tellerService.tellerLogin(headMap,contextMap);
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0003);		
		}
		return entity;
	}
	
	/**
	 * 
	 * @Title: tellerLogout
	 * @Description: 柜员签退
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000042")
	public ResponseEntity tellerLogout(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		List<Map<String, Object>> tellerList  =  new ArrayList<Map<String, Object>>();
		tellerMap = tellerService.queryTellerBybtrLegal(headMap,contextMap);
		tellerList = (List<Map<String, Object>>) tellerMap.get("tellerList");
		
		if((Integer)tellerMap.get("totalNum") != 0){
			
			if(!((String)tellerList.get(0).get("btrStt")).equals("2")){
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0004);
			}
			if(((String)tellerList.get(0).get("btrLoginstt")).equals("1")){
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0005);
			}
			
			tellerService.tellerLogout(headMap,contextMap);
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0003);		
		}
		return entity;
	}

	/**
	 * 
	 * @Title: queryTellerRoleMenu
	 * @Description: 根据柜员查询菜单
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000120")
	public ResponseEntity queryTellerRoleMenu(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		
		ResponseEntity entity = new ResponseEntity();
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		
		tellerMap = tellerService.queryTellerBybtrLegal(headMap,bodyMap);
		if((Integer)tellerMap.get("totalNum") != 0){
			Map<String, Object> tellerRoleMenuMap = new HashMap<String, Object>();
			tellerRoleMenuMap = tellerService.queryTellerRoleMenu(headMap,bodyMap);
			entity = new ResponseEntity(tellerRoleMenuMap);
			
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0003);
		}
		return entity;
	}
	/**
	 * 
	 * @Title: queryMenudefPro
	 * @Description: 根据法人id，菜单性质，系统标识查询货架编码
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000121")
	public ResponseEntity queryMenudefPro(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		ResponseEntity entity = new ResponseEntity();
		Map<String, Object> menudefProMap = new HashMap<String, Object>();
		menudefProMap = tellerService.queryMenudefPro(headMap,bodyMap);
		if(menudefProMap != null && menudefProMap.size() > 0){			
			entity = new ResponseEntity(menudefProMap);				
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0003);
		}
		return entity;
	}

}
