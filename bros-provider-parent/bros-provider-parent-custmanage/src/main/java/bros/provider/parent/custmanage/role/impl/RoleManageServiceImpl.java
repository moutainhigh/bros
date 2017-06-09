package bros.provider.parent.custmanage.role.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.db.impl.MyBatisDaoSysDaoImpl;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.provider.parent.custmanage.bsndef.IBsnDefManagerService;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
import bros.provider.parent.custmanage.role.IRoleManageService;
/**
 * 
 * @ClassName: RoleManageServiceImpl 
 * @Description: 角色操作接口实现类
 * @author mazhilei 
 * @date 2016年6月28日 下午2:47:03 
 * @version 1.0
 */
@Component("roleManage")
public class RoleManageServiceImpl implements IRoleManageService {
	private static final  Logger logger = LoggerFactory.getLogger(RoleManageServiceImpl.class);
	@Autowired
	private MyBatisDaoSysDaoImpl myBatisDaoSysDao;
	@Autowired
	private IBsnDefManagerService bsnDefManager;
	
	
	/**
	 * 
	 * @Title: addRoleMethod 
	 * @Description: 添加角色
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void addRoleManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException {
		try{
		Map<String,Object>	roleMap=(Map<String, Object>) bodyMap.get("roleMap");
		/**
		 * 校验角色名称
		 */
		if(checkRoleRepetitionByName((String)roleMap.get("treCstno"), (String)roleMap.get("treName"))){
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0003,"角色已经存在");
		}
		/**
		 *  写入角色基本信息
		 */
		String roleId=addRoleTableMethod(headMap, bodyMap);
		/**
		 * 写入角色下功能
		 */
		/*
		 * 获取传入,增加角色时传入可操作功能码
		 */
		List<Map<String, Object>> roleFunctionList=(List<Map<String, Object>>) bodyMap.get("roleFunctionList");
		List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : roleFunctionList) {
			map.put("trbrId", BaseUtil.createUUID());
			map.put("trbrRoleid", roleId);
			map.put("trbrBsncode", map.get("roleFunctionId"));
			paramList.add(map);
		}
		/*
		 * 查询数据库中配置角色对应默认的功能
		 */
		//角色权限
		String roleAuth=(String) bodyMap.get("roleAuth");
		List<Map<String, Object>> funcIdList = bsnDefManager.querySysFuncId(roleAuth);
		for (Map<String, Object> map : funcIdList) {
			map.put("trbrId", BaseUtil.createUUID());
			map.put("trbrRoleid", roleId);
			map.put("trbrBsncode", map.get("bsnCode"));
			paramList.add(map);
		}
		int insertCbRoleBsnRel=	myBatisDaoSysDao.insertList("mybatis.mapper.single.table.ttprolebsnrel.insertTtpRoleBsnRel", paramList);
		if (insertCbRoleBsnRel!=paramList.size()) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0002,"添加角色对应功能信息失败");
		}
		}catch(ServiceException be){
			logger.error("添加角色信息失败 Exception from " + this.getClass().getName() + "'s addRoleManageMethod method.", be);
			throw be;
		}catch(Exception e) {
			logger.error("添加角色信息失败 Exception from " + this.getClass().getName() + "'s addRoleManageMethod method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",e);
		}
		
	}

	/**
	 * 
	 * @Title: queryRoleMethod 
	 * @Description: 查询企业客户下所有角色信息
	 * @return
	 * @throws ServiceException    
	 */
	@Override
	public List<Map<String, Object>> queryCorporationRoleMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException {
		List<Map<String,Object>> ttproleList=null;
		try{
			Map<String, Object> roleMap = new HashMap<String, Object>();
			
			Map<String, Object> param=new HashMap<String,Object>();
			param.put("treCstno", bodyMap.get("treCstno"));
			
			
			List<Map<String, Object>> dataList  =  new ArrayList<Map<String, Object>>();
			int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")==null?"1":bodyMap.get("pageSize")));//每页显示条数
			int pageNo =  Integer.parseInt(String.valueOf(bodyMap.get("pageNo")==null?"10":bodyMap.get("pageNo")));//第几页
			
			//查询角色总记录
			dataList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.ttprole.queryTtpRoleByCstNo", param, pageNo, pageSize);
			
		    //查询总条数
			int totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.ttprole.queryRoleNum", param);
			
			roleMap.put("dataList", dataList);
			roleMap.put("totalNum", totalNum);
			
//			ttproleList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.ttprole.queryTtpRoleByCstNo",param);
			
		}catch(ServiceException be){
			logger.error("查询企业客户下所有角色信息失败 Exception from " + this.getClass().getName() + "'s queryRoleMethod method.", be);
			throw be;
		}catch(Exception e) {
			logger.error("查询企业客户下所有角色信息信息失败 Exception from " + this.getClass().getName() + "'s queryRoleMethod method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",e);
		}
		return ttproleList;
	}

	/**
	 * 
	 * @Title: updateRoleManageMethod 
	 * @Description: 更新角色信息
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateRoleManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException {
	 try{
	   /**
	    * 取值
	    */
		 
		 String treCstno=(String)bodyMap.get("treCstno");
		 String treName=(String)bodyMap.get("treName");
		 String treDesc=(String)bodyMap.get("treDesc");
		 String treModitime=(String)bodyMap.get("treModitime");
		 String treModiuser=(String)bodyMap.get("treModiuser");
		 String treState=(String)bodyMap.get("treState");
		 String treType=(String)bodyMap.get("roleAuth");
		 
		/**
		 * 校验名称是否重复
		 */
		if(!"".equals(treName) && treName!=null){
			if(checkRoleRepetitionByName(treCstno, treName)){
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0003,"角色已经存在");
			}
		}
		String roleId=(String)bodyMap.get("roleId");
		/*
		 * 校验在途交易
		 */
		
		/*
		 * 根据角色ID 删除对应功能 ttp_bsn_def_relq
		 */
		deleteRoleBsnRelMethod(roleId);
		/**
		 * 写入角色下功能
		 */
		/*
		 * 获取传入,增加角色时传入可操作功能码
		 */
		List<Map<String, Object>> roleFunctionList=(List<Map<String, Object>>) bodyMap.get("roleFunctionList");
		List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : roleFunctionList) {
			map.put("trbrId", BaseUtil.createUUID());
			map.put("trbrRoleid", roleId);
			map.put("trbrBsncode", map.get("roleFunctionId"));
			paramList.add(map);
		}
		/*
		 * 查询数据库中配置角色对应默认的功能
		 */
		//角色权限
		String roleAuth=(String) bodyMap.get("roleAuth");
		List<Map<String, Object>> funcIdList = bsnDefManager.querySysFuncId(roleAuth);
		for (Map<String, Object> map : funcIdList) {
			map.put("trbrId", BaseUtil.createUUID());
			map.put("trbrRoleid", roleId);
			map.put("trbrBsncode", map.get("bsnCode"));
			paramList.add(map);
		}
		int insertCbRoleBsnRel=	myBatisDaoSysDao.insertList("mybatis.mapper.single.table.ttprolebsnrel.insertTtpRoleBsnRel", paramList);
		if (insertCbRoleBsnRel!=paramList.size()) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0002,"添加角色对应功能信息失败");
		}
		
		/*
		 * step6  更改角色基本信息
		 */
		updateRoleBasicManageMethod(roleId, treName, treDesc, treModitime, treModiuser, treState, treType);
	 }catch(ServiceException be){
			logger.error("查询企业客户下所有角色信息失败 Exception from " + this.getClass().getName() + "'s queryRoleMethod method.", be);
			throw be;
		}catch(Exception e) {
			logger.error("查询企业客户下所有角色信息信息失败 Exception from " + this.getClass().getName() + "'s queryRoleMethod method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",e);
		}
		
	}

	/**
	 * 
	 * @Title: deleteRoleManageMethod 
	 * @Description: 删除角色信息方法
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Override
	public void deleteRoleManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException {
		Map<String, Object>param=new HashMap<String, Object>();
		param.put("roleId",bodyMap.get("roleId"));
	 try {
		/*
		 * step1 判断操作员是否正在使用该角色
		 */
		
		/*
		 * step2 判断是否存在相关的在途交易
		 */
		
		/*
		 * step3 删除角色
		 */
		
		int deleteRoleById = myBatisDaoSysDao.delete("mybatis.mapper.single.table.ttprole.deleteTtpRoleByTreId", param);
		if (deleteRoleById!=1) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0006,"删除角色信息失败");
		}
		int deleteByRoleId = myBatisDaoSysDao.delete("mybatis.mapper.single.table.ttprolebsnrel.deleteRoleBsnByRoleId", param);
		if (deleteByRoleId<0) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0007,"删除角色对应功能信息失败");
		}
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteRoleTrans method.", e);
			throw e;
        }catch(Exception ex){
        	logger.error("Exception from " + this.getClass().getName() + "'s deleteRoleTrans method.", ex);
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000);
        }

	}
	/**
	 * 校验角色是否重复
	 */
	public boolean checkRoleRepetitionByName(String treCstno,String treName) throws ServiceException{
		List<Map<String,Object>> ttproleList=null;
		boolean resource=true;
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("treCstno", treCstno);
		param.put("treName", treName);
		try{
			ttproleList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.ttprole.queryTtpRoleByTppName",param);
			
			resource= ttproleList!=null && ttproleList.size()>0 ?true:false;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s checkRoleRepetitionByName method.", e);
			throw e;
        } catch(Exception ex){
        	logger.error("Exception from " + this.getClass().getName() + "'s checkRoleRepetitionByName method.", ex);
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"系统错误");
        }
		
		return resource;
		
	}
	/**
	 * 
	 * @Title: addRoleMethod 
	 * @Description:添加ttp_role表
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String addRoleTableMethod(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		String roleId=BaseUtil.createUUID();
	try{
		Map<String,Object> param=new HashMap<String,Object>();
		Map<String,Object>	roleMap=(Map<String, Object>) bodyMap.get("roleMap");
		param.put("treId",roleId);
		param.put("treCstno",roleMap.get("treCstno"));
		param.put("treName",roleMap.get("treName"));
		param.put("treDesc",roleMap.get("treDesc")); 
		param.put("treCreatetime",roleMap.get("treCreatetime")); 
		param.put("treCreateuser",roleMap.get("treCreateuser")); 
		param.put("treModitime",roleMap.get("treModitime")); 
		param.put("treModiuser",roleMap.get("treModiuser")); 
		param.put("treState",roleMap.get("treState")); 
		param.put("treType",roleMap.get("roleAuth"));
		
		myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.ttprole.insertTtpRole", param);
	}catch(ServiceException be){
		logger.error("查询角色信息失败   "+this.getClass().getName()+".queryRoleMethod",be);
		throw be;
	}catch(Exception e) {
		logger.error("查询角色信息失败 Exception from " + this.getClass().getName() + "'s addRoleTableMethod method.", e);
		throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",e);
	}
	return roleId;

	}

	/**
	 * 
	 * @Title: queryRoleDetailMethod 
	 * @Description: 查询角色详细信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    
	 */
	public ResponseEntity queryRoleDetailMethod(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		List<Map<String, Object>>  RoleDetailList=new ArrayList<Map<String, Object>>();
		try{
			String roleId=(String) bodyMap.get("roleId");
			/**
			 * 查询角色基本信息
			 */
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("treId", roleId);
			Map<String, Object> roleInfoMap=myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.ttprole.queryTtpRoleByRoleId", param);
			if (roleInfoMap==null || roleInfoMap.isEmpty()) {
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0004,"角色不存在");
			}
			/**
			 * 查询角色详细信息
			 */
			List<Map<String, Object>> roleDetailList=	myBatisDaoSysDao.selectList("queryFuncIdByRoleType", param);
			if (roleDetailList==null || roleDetailList.isEmpty()) {
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0005,"查询角色对应的功能编码失败");
			}
			
			for (Map<String, Object> map : roleDetailList) {
				Map<String, Object> mapFunction=new HashMap<String, Object>();
				mapFunction.put("roleFunctionId", map.get("tbfBsncode"));
				mapFunction.put("roleFunctionName", map.get("tbfName"));
				RoleDetailList.add(mapFunction);
			}
			/**
			 * 返回数据
			 */
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("roleInfoMap", roleInfoMap);//角色基本信息
			map.put("RoleDetailList", RoleDetailList);//角色详细信息
			ResponseEntity entity = new ResponseEntity(map);
			return entity;
		}catch(ServiceException be){
			logger.error("查询角色信息失败   "+this.getClass().getName()+".queryRoleMethod",be);
			throw be;
		}catch(Exception e) {
			logger.error("查询角色信息失败 Exception from " + this.getClass().getName() + "'s addRoleTableMethod method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",e);
		}
		
	}

	/**
	 * 
	 * @Title: deleteRoleBsnRelMethod 
	 * @Description: 删除角色对应的功能
	 * @param roleId
	 * @throws ServiceException    
	 */
	@Override
	public void deleteRoleBsnRelMethod(String roleId) throws ServiceException {
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("roleId",roleId);
			int i=myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.ttprolebsnrel.deleteRoleBsnByRoleId", param);
			if(i<0){
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0007,"删除角色对应功能信息失败");
			}
		}catch(ServiceException be){
			logger.error("删除角色对应的功能失败   "+this.getClass().getName()+"deleteRoleBsnRelMethod",be);
			throw be;
		}catch(Exception e) {
			logger.error("查询角色信息失败 Exception from " + this.getClass().getName() + "'s deleteRoleBsnRelMethod method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",e);
		}
		
	}

	/**
	 * 
	 * @Title: updateRoleBasicManageMethod 
	 * @Description: 更改角色基本信息方法
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    
	 */
	@Override
	public void updateRoleBasicManageMethod(String roleId,String treName,String treDesc,String treModitime,String treModiuser,String treState,String treType )
			throws ServiceException {
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("treName",treName);
			param.put("treDesc",treDesc); 
			param.put("treModitime",treModitime); 
			param.put("treModiuser",treModiuser); 
			param.put("treState",treState); 
			param.put("treType",treType);
			
			myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.ttprole.updateTtpRoleById", param);
		}catch(ServiceException be){
			logger.error("更改角色基本信息失败   "+this.getClass().getName()+".queryRoleMethod",be);
			throw be;
		}catch(Exception e) {
			logger.error("更改角色基本信息失败 Exception from " + this.getClass().getName() + "'s addRoleTableMethod method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",e);
		}
		
	}

	/**
	 * 
	 * @Title: queryCorporationRolePageMethod 
	 * @Description: 分页查询企业客户下所有角色信息
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    
	 */
	public Map<String, Object> queryCorporationRolePageMethod(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		Map<String, Object> paramOut = new HashMap<String, Object>();
		try{
			
			Map<String, Object> param=new HashMap<String,Object>();
			param.put("treCstno", bodyMap.get("treCstno"));
			
			
			List<Map<String, Object>> dataList  =  new ArrayList<Map<String, Object>>();
			int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")==null?1:bodyMap.get("pageSize")));//每页显示条数
			int pageNo =  Integer.parseInt(String.valueOf(bodyMap.get("pageNo")==null?1:bodyMap.get("pageNo")));//第几页
			
			//查询角色总记录
			dataList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.ttprole.queryTtpRoleByCstNo", param, pageNo, pageSize);
			
		    //查询总条数
			int totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.ttprole.queryTtpRoleNum", param);
			
			paramOut.put("dataList", dataList);
			paramOut.put("totalNum", totalNum);
			
		}catch(ServiceException be){
			logger.error("查询企业客户下所有角色信息失败 Exception from " + this.getClass().getName() + "'s queryRoleMethod method.", be);
			throw be;
		}catch(Exception e) {
			logger.error("查询企业客户下所有角色信息信息失败 Exception from " + this.getClass().getName() + "'s queryRoleMethod method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",e);
		}
		return paramOut;
	}
}
