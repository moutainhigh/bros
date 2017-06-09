package bros.provider.parent.bankmanage.tellerrole.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.teller.service.impl.TellerServiceImpl;
import bros.provider.parent.bankmanage.tellerrole.service.ITellerRoleService;

/** 
 * @ClassName:TellerRoleServiceImpl  
 * @Description:柜员角色管理接口
 * @author  haojinhui
 * @date 2016年6月30日 下午3:40:56 
 * @version V1.0  
 */
@Repository(value = "tellerRoleService")
public class TellerRoleServiceImpl  implements ITellerRoleService {
	private static final Logger logger = LoggerFactory.getLogger(TellerServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/**
	 * 
	 * @Title: queryTellerRole
	 * @Description: 查询柜员角色信息
	 * @param contextMap  上送报文
	 * @return  Map<String, Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryTellerRole(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {
		Map<String, Object> tellerRoleMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			String breLegal = (String) contextMap.get("legalPersonId");//法人id
			String breCode = (String) (contextMap.get("breCode")==null?"":contextMap.get("breCode"));//角色编号
			String breBranchid = (String) (contextMap.get("breBranchid")==null?"":contextMap.get("breBranchid"));//机构编号
			String breChannel = (String) (contextMap.get("breChannel")==null?"":contextMap.get("breChannel"));//系统标志
			
			int pageSize = Integer.parseInt((contextMap.get("pageSize")==null?10000:contextMap.get("pageSize")).toString());//每页显示条数
			int pageNo =  Integer.parseInt((contextMap.get("pageNo")==null?1:contextMap.get("pageNo")).toString());//第几页
			parmIn.put("breLegal", breLegal);
			parmIn.put("breCode", breCode);
			parmIn.put("breBranchid", breBranchid);
			parmIn.put("breChannel", breChannel);
			
			List<Map<String, Object>> dataList  =  new ArrayList<Map<String, Object>>();
			//查询柜员角色信息
			dataList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.bmatellerrole.queryTellerRole", parmIn, pageNo, pageSize);
		    //查询总条数
			int totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.bmatellerrole.queryTellerRoleNum", parmIn);
			//组装返回数据
			tellerRoleMap.put("returnList", dataList);
			tellerRoleMap.put("totalNum", totalNum);
		} catch (ServiceException se) {
			logger.error("查询柜员角色信息失败   " + this.getClass() + ".queryTellerRole");
			throw se;
		} catch (Exception e) {
			logger.error("查询柜员角色信息失败   " + this.getClass() + ".queryTellerRole");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询柜员角色信息操作失败", e);
		}
		return tellerRoleMap;
	}

	/**
	 * 
	 * @Title: addTellerRole
	 * @Description: 新增柜员角色
	 * @param contextMap  上送报文
	 * @return  Map<String, Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> addTellerRole(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {
		Map<String, Object> tellerRoleMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			String breId = BaseUtil.createUUID();//角色编号主键
			String breLegal = (String) contextMap.get("legalPersonId");//法人id
			String breName = (String) (contextMap.get("breName")==null?"":contextMap.get("breName"));//角色名称
			String breDesc = (String) (contextMap.get("breDesc")==null?"":contextMap.get("breDesc"));//角色描述
			String breLevel = (String) (contextMap.get("breLevel")==null?"":contextMap.get("breLevel"));//角色级别
			String breBranchid = (String) (contextMap.get("breBranchid")==null?"":contextMap.get("breBranchid"));//机构编号
			String breCategory = (String) (contextMap.get("breCategory")==null?"":contextMap.get("breCategory"));//角色类型
			String brePrivilege = (String) (contextMap.get("brePrivilege")==null?"":contextMap.get("brePrivilege"));//角色权限
			String breModitime = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_TRANDATE));//最后修改时间
			String breModiuser = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO));//最后修改人
			String breAddbranch = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_BRANCHID));//创建角色的机构
			String breChannel = (String) (contextMap.get("breChannel")==null?"":contextMap.get("breChannel"));//系统标识
			String breCode = (String) (contextMap.get("breCode")==null?"":contextMap.get("breCode"));//备注1
			String breNote2 = (String) (contextMap.get("breNote2")==null?"":contextMap.get("breNote2"));//备注2
			parmIn.put("breId", breId);
			parmIn.put("breLegal", breLegal);
			parmIn.put("breName", breName);
			parmIn.put("breDesc", breDesc);
			parmIn.put("breLevel", breLevel);
			parmIn.put("breBranchid", breBranchid);
			parmIn.put("breCategory", breCategory);
			parmIn.put("brePrivilege", brePrivilege);
			parmIn.put("breModitime", breModitime);
			parmIn.put("breModiuser", breModiuser);
			parmIn.put("breAddbranch", breAddbranch);
			parmIn.put("breChannel", breChannel);
			parmIn.put("breCode", breCode);
			parmIn.put("breNote2", breNote2);

			//新增柜员角色
			myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.bmatellerrole.insertTellerRole",parmIn);
		} catch (ServiceException se) {
			logger.error("新增角色信息失败   " + this.getClass() + ".addTellerRole");
			throw se;
		} catch (Exception e) {
			logger.error("新增角色信息失败   " + this.getClass() + ".addTellerRole");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"新增角色信息操作失败", e);
		}
		return tellerRoleMap;
	}
	
	/**
	 * 
	 * @Title: updateTellerRole
	 * @Description: 修改柜员角色
	 * @param contextMap  上送报文
	 * @return  Map<String, Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> updateTellerRole(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {
		Map<String, Object> tellerRoleMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			String breId = (String) (contextMap.get("breId")==null?"":contextMap.get("breId"));//角色编号主键
			String breLegal = (String) contextMap.get("legalPersonId");//法人id
			String breName = (String) (contextMap.get("breName")==null?"":contextMap.get("breName"));//角色名称
			String breDesc = (String) (contextMap.get("breDesc")==null?"":contextMap.get("breDesc"));//角色描述		
			String breModitime = (String) headMap.get(HeadParameterDefinitionConstants.REC_TRANSTARTTIME);//最后修改时间
			String breModiuser = (String) headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO);//最后修改人
			parmIn.put("breId", breId);
			parmIn.put("breLegal", breLegal);
			parmIn.put("breName", breName);
			parmIn.put("breDesc", breDesc);
			parmIn.put("breModitime", breModitime);
			parmIn.put("breModiuser", breModiuser);

			//修改柜员信息
			myBatisDaoSysDao.update("mybatis.mapper.single.table.bmatellerrole.updateTellerRole",parmIn);
		} catch (ServiceException se) {
			logger.error("角色信息修改失败   " + this.getClass() + ".updateTellerRole");
			throw se;
		} catch (Exception e) {
			logger.error("角色信息修改失败   " + this.getClass() + ".updateTellerRole");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"角色信息修改操作失败", e);
		}
		return tellerRoleMap;
	}

	/**
	 * 
	 * @Title: deleteTellerRole
	 * @Description: 删除柜员角色
	 * @param contextMap  上送报文
	 * @return  Map<String, Object>
	 * @throws ServiceException
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public Map<String, Object> deleteTellerRole(Map<String, Object> headMap,
			String breId,String legalPersonId) throws ServiceException {
		Map<String, Object> tellerRoleMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			parmIn.put("breLegal", legalPersonId);
			parmIn.put("breId", breId);

			//删除角色信息
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.bmatellerrole.deleteTellerRole",parmIn);
			Map<String, Object> parmInrel = new HashMap<String,Object>();
			parmInrel.put("tellerId", "breId");
			//删除柜员角色关联
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.bmatellerrolerel.deleteTellerRolerel",parmInrel);
		} catch (ServiceException se) {
			logger.error("角色信息删除失败   " + this.getClass() + ".deleteTellerRole");
			throw se;
		} catch (Exception e) {
			logger.error("角色信息删除失败   " + this.getClass() + ".deleteTellerRole");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"角色信息删除操作失败", e);
		}
		return tellerRoleMap;
	}

	/**
	 * 
	 * @Title: setTellerRole
	 * @Description: 设置柜员角色关联
	 * @param burlTrllerno  柜员号
	 * @param burlRoleid  角色编号
	 * @return  Map<String, Object>
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> setTellerRole(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> tellerRoleMap = new HashMap<String, Object>();
		try {

			String tellerId = (String) (bodyMap.get("tellerId")==null?"":bodyMap.get("tellerId"));//柜员id

			List<Map<String,Object>> tellerRoleList = new ArrayList<Map<String,Object>>();
			tellerRoleList = (List<Map<String, Object>>) bodyMap.get("bodyList");
			
			for (int i = 0; i < tellerRoleList.size(); i++) {
				
				tellerRoleList.get(i).put("tellerId", tellerId);
				tellerRoleList.get(i).put("note1", "");
				tellerRoleList.get(i).put("note2", "");

			}
			
			//设置柜员角色关联
			myBatisDaoSysDao.insertBatchList("mybatis.mapper.single.table.bmatellerrolerel.setTellerRole",tellerRoleList);
		} catch (ServiceException se) {
			logger.error("设置柜员角色关联失败   " + this.getClass() + ".setTellerRole");
			throw se;
		} catch (Exception e) {
			logger.error("设置柜员角色关联失败   " + this.getClass() + ".setTellerRole");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"设置柜员角色关联操作失败", e);
		}
		return tellerRoleMap;
	}
	/**
	 * 
	 * @Title: updateSetTellerRole
	 * @Description: 更新柜员角色关联关系
	 * @param bodyMap  柜员号
	 * @return  Map<String, Object>
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public Map<String, Object> updateSetTellerRole(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> tellerRoleMap = new HashMap<String, Object>();
		try {

			Map<String, Object> parmInteller = new HashMap<String,Object>();
			Map<String, Object> parmIn = new HashMap<String,Object>();
			String breLegal = (String) bodyMap.get("legalPersonId");//法人id
			String tellerCode = (String) (bodyMap.get("tellerCode")==null?"":bodyMap.get("tellerCode"));//柜员号

			
			parmInteller.put("btrLegal", breLegal);
			parmInteller.put("btrCode", tellerCode);
			parmInteller.put("btrBrancode", "");
			//查询柜员uuid
			List<Map<String, Object>> dataList  =  new ArrayList<Map<String, Object>>();
			dataList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmateller.queryTellerBybtrLegal", parmInteller);
			
			parmIn.put("tellerId", dataList.get(0).get("btrId"));
			//删除柜员角色关联
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.bmatellerrolerel.deleteTellerRolerel",parmIn);
			
			List<Map<String,Object>> tellerRoleList = new ArrayList<Map<String,Object>>();
			tellerRoleList = (List<Map<String, Object>>) bodyMap.get("bodyList");
			
			for (int i = 0; i < tellerRoleList.size(); i++) {
				
				tellerRoleList.get(i).put("tellerId", dataList.get(0).get("btrId"));
				tellerRoleList.get(i).put("note1", "");
				tellerRoleList.get(i).put("note2", "");

			}
			
			//更新柜员角色关联
			myBatisDaoSysDao.insertBatchList("mybatis.mapper.single.table.bmatellerrolerel.setTellerRole",tellerRoleList);
		} catch (ServiceException se) {
			logger.error("更新柜员角色关联关系   " + this.getClass() + ".deleteSetTellerRole");
			throw se;
		} catch (Exception e) {
			logger.error("更新柜员角色关联关系   " + this.getClass() + ".deleteSetTellerRole");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"更新柜员角色关联关系操作失败", e);
		}
		return tellerRoleMap;
	}
	/**
	 * 
	 * @Title: querySetTellerRole
	 * @Description: 查询柜员已分配角色信息
	 * @param legalPersonId  法人id
	 * @param tellerId  柜员id
	 * @return  Map<String, Object>
	 * @throws ServiceException
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public Map<String, Object> querySetTellerRole(String legalPersonId,String tellerId) throws ServiceException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {

			Map<String, Object> parmIn = new HashMap<String,Object>();	
			parmIn.put("breLegal", legalPersonId);
			parmIn.put("tellerId", tellerId);
			//查询柜员已分配角色信息
			List<Map<String, Object>> dataList  =  new ArrayList<Map<String, Object>>();
			dataList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmatellerrole.querySetTellerRole",parmIn);
			returnMap.put("returnList", dataList);
		} catch (ServiceException se) {
			logger.error("查询柜员已分配角色信息   " + this.getClass() + ".deleteSetTellerRole");
			throw se;
		} catch (Exception e) {
			logger.error("查询柜员已分配角色信息   " + this.getClass() + ".deleteSetTellerRole");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询柜员已分配角色信息操作失败", e);
		}
		return returnMap;
	}
}
