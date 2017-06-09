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
import bros.p.bankmanage.facade.service.IPTellerRoleServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.teller.service.ITellerService;
import bros.provider.parent.bankmanage.tellerrole.service.ITellerRoleService;

/** 
 * @ClassName: PTellerRoleServiceFacadeImpl  
 * @Description:柜员角色对外接口实现类
 * @author  haojinhui
 * @date 2016年6月30日 下午3:34:11 
 * @version V1.0  
 */
@Component("ptellerRoleServiceFacade")
public class PTellerRoleServiceFacadeImpl implements IPTellerRoleServiceFacade {
	
	/**
	 * 柜员角色实现类
	 */
	@Autowired
	private ITellerRoleService tellerRoleService;
	/**
	 * 柜员系统实现类
	 */
	@Autowired
	private ITellerService tellerService;

	/**
	 * 
	 * @Title: addTellerRole
	 * @Description: 增加柜员角色
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000043")
	public ResponseEntity addTellerRole(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = tellerRoleService.queryTellerRole(headMap,contextMap);
		
		if((Integer)tellerMap.get("totalNum") != 0){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0014);
		}else{
			tellerRoleService.addTellerRole(headMap,contextMap);
		}
		return entity;
	}

	/**
	 * 
	 * @Title: updateTellerRole
	 * @Description: 更改柜员角色
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000044")
	public ResponseEntity updateTellerRole(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = tellerRoleService.queryTellerRole(headMap,contextMap);
		
		if((Integer)tellerMap.get("totalNum") != 0){
			tellerRoleService.updateTellerRole(headMap,contextMap);
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0008);		
		}
		return entity;
	}

	/**
	 * 
	 * @Title: deleteTellerRole
	 * @Description: 删除柜员角色
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000045")
	public ResponseEntity deleteTellerRole(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		
		tellerMap = tellerRoleService.queryTellerRole(headMap,contextMap);
		String breId = (String) (contextMap.get("breId")==null?"":contextMap.get("breId"));//角色id
		String legalPersonId = (String) contextMap.get("legalPersonId");//法人id
		if((Integer)tellerMap.get("totalNum") != 0){
			tellerRoleService.deleteTellerRole(headMap,breId,legalPersonId);
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0008);		
		}
		return entity;
	}
	
	/**
	 * 
	 * @Title: setTellerRole
	 * @Description: 设置柜员角色关联关系
	 * @param headMap  头信息
	 * @param burlTrllerno  柜员号
	 * @param burlRoleid 角色编号
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000046")
	public ResponseEntity setTellerRole(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();	
		
		String legalPersonId = (String) bodyMap.get("legalPersonId");//法人id
		String tellerCode = (String) (bodyMap.get("tellerCode")==null?"":bodyMap.get("tellerCode"));//柜员号
		//根据柜员号查询柜员id
		Map<String, Object> parmIn = new HashMap<String,Object>();
		parmIn.put("btrCode", tellerCode);
		parmIn.put("legalPersonId", legalPersonId);
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = tellerService.queryTellerById(headMap, parmIn);
		List<Map<String, Object>> tellerList  =  new ArrayList<Map<String, Object>>();
		tellerList = (List<Map<String, Object>>) tellerMap.get("returnList");
		//判断柜员是否存在
		if(tellerList!=null){
			String tellerId = (String) tellerList.get(0).get("btrId");
			Map<String,Object>  tellerRoleMap=tellerRoleService.querySetTellerRole(legalPersonId,tellerId);
			if(tellerRoleMap != null && tellerRoleMap.size() > 0){
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0015);
			}else{
				bodyMap.put("tellerId", tellerId);
				tellerRoleService.setTellerRole(headMap,bodyMap);
			}
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0003);
		}
		return entity;
	}

	/**
	 * 
	 * @Title: queryTellerRole
	 * @Description: 查询柜员角色
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000047")
	public ResponseEntity queryTellerRole(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = tellerRoleService.queryTellerRole(headMap,contextMap);
		if(tellerMap != null && tellerMap.size() > 0){			
			entity = new ResponseEntity(tellerMap);				
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0008);
		}
		return entity;
	}

	/**
	 * 
	 * @Title: updateSetTellerRole
	 * @Description: 更新柜员角色关联关系
	 * @param headMap  头信息
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000122")
	public ResponseEntity updateSetTellerRole(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();	
		
			tellerRoleService.updateSetTellerRole(headMap,bodyMap);
		
		return entity;
	}

	/**
	 * 
	 * @Title: querySetTellerRole
	 * @Description: 查询柜员已分配角色信息
	 * @param headMap  头信息
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000123")
	public ResponseEntity querySetTellerRole(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		
		ResponseEntity entity = new ResponseEntity();	
		String legalPersonId = (String) bodyMap.get("legalPersonId");//法人id
		String tellerCode = (String) (bodyMap.get("tellerCode")==null?"":bodyMap.get("tellerCode"));//柜员号
		//根据柜员号查询柜员id
		Map<String, Object> parmIn = new HashMap<String,Object>();
		parmIn.put("btrCode", tellerCode);
		parmIn.put("legalPersonId", legalPersonId);
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = tellerService.queryTellerById(headMap, parmIn);
		List<Map<String, Object>> tellerList  =  new ArrayList<Map<String, Object>>();
		tellerList = (List<Map<String, Object>>) tellerMap.get("returnList");
		//判断柜员是否存在
		if(tellerList!=null&&tellerList.size()>0){
			String tellerId = (String) tellerList.get(0).get("btrId");
			Map<String,Object>  tellerRoleMap=tellerRoleService.querySetTellerRole(legalPersonId,tellerId);
			
			//查询全部角色，合并list，roleType 0-已分配,1-未分配
			Map<String, Object> tellerMapallRole = new HashMap<String, Object>();
			tellerMapallRole = tellerRoleService.queryTellerRole(headMap,bodyMap);
			List<Map<String, Object>> tellerallRoleList = (List<Map<String, Object>>) tellerMapallRole.get("returnList");
			List<Map<String, Object>> tellerRoleList = (List<Map<String, Object>>) tellerRoleMap.get("returnList");
			for (int i = 0; i < tellerallRoleList.size(); i++) {
				Map<String,Object> roleHaveMap=tellerallRoleList.get(i);
				String breId = (String)roleHaveMap.get("breId");
				if(null!=tellerRoleList && tellerRoleList.size()>0){
					for(int j=0;tellerRoleList.size()>0;){
						Map<String,Object> roleMap=tellerRoleList.get(j);
						if(roleMap.get("breId").equals(breId)){
							roleHaveMap.put("roleType", "0");
							break;
						}else{
							roleHaveMap.put("roleType", "1");
							break;
						}
						
					}
				}else{
					roleHaveMap.put("roleType", "1");
				}
		   }
			
			entity=new ResponseEntity(tellerMapallRole);
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0003);
		}
		return entity;
	}

}
