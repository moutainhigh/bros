package bros.provider.parent.custmanage.operator.impl;

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
import bros.common.core.util.ValidateUtil;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;

/**
 * 
 * @ClassName: OperatorUpdateManage 
 * @Description: 更新操作员具体实现类
 * @author mazhilei 
 * @date 2016年7月6日 上午10:06:46 
 * @version 1.0
 */
@Component("operatorUpdateManage")
public class OperatorUpdateManage {
	@Autowired
	private MyBatisDaoSysDaoImpl myBatisDaoSysDao;
	private static final  Logger logger = LoggerFactory.getLogger(OperatorUpdateManage.class);
	/**
	 * 
	 * @Title: updateOperatorManageMethod 
	 * @Description: 更新操作员方法
	 * @param headMap
	 * @param bodyMap
	 * @return
	 */
	public ResponseEntity updateOperatorManageMethod(Map<String, Object> headMap, Map<String, Object> bodyMap) 
			throws ServiceException{
	
	try{
		/**
		 * step1校验操作员是否存在
		 */
		checkOperatorManageIsExist(headMap, bodyMap);
		
		/**
		 * step2跟新操作员基本信息
		 */
		updateOperatorInf(headMap, bodyMap);
		/**
		 * step3跟新操作员账户信息
		 */
		updateOperatorAccount(headMap, bodyMap);
		/**
		 * step4更新操作员角色信息
		 */
		updateOperatorRole(headMap, bodyMap);
	}catch(ServiceException be){
		logger.error("更新操作员信息失败 Exception from " + this.getClass().getName() + "'s updateOperatorManageMethod method.", be);
		throw be;
	}catch(Exception e) {
		logger.error("更新操作员信息失败 Exception from " + this.getClass().getName() + "'s updateOperatorManageMethod method.", e);
		throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",e);
	}	
		
		return null;
	}
	/**
	 * 
	 * @Title: checkOperatorManageIsExist 
	 * @Description: 校验操作员是否存在
	 * @param headMap
	 * @param bodyMap
	 * @return
	 */
	public Map<String,Object> checkOperatorManageIsExist(Map<String, Object> headMap, Map<String, Object> bodyMap) 
			throws ServiceException{
			String cstNo=(String)bodyMap.get("cstNo");
			String operatorUserno=(String)bodyMap.get("operatorUserno");
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("cstNo", cstNo);
			map.put("operatorUserno", operatorUserno);
			Map<String, Object> operatorInfMap=null;
			try {
				operatorInfMap =	myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.TtpUserInfTtpUserInfChannelSqlMapper.queryTtpUserInfManageByCstNoAndoperatorId", map);
				if (operatorInfMap==null || operatorInfMap.size()<=0) {
					throw new ServiceException(CustmanageErrorCodeConstants.PPCG0011,"操作员不存在");
				}
				/**
				 * 取值为后面需要
				 */
				bodyMap.put("operatorId",operatorInfMap.get("operatorId"));
			}catch(ServiceException e){
				logger.error("Exception from " + this.getClass().getName() + "'s checkOperatorManageIsExist method.", e);
				throw e;
			}catch(Exception ex){
				logger.error("Exception from " + this.getClass().getName() + "'s checkOperatorManageIsExist method.", ex);
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",ex);
			}
		return operatorInfMap;
}
	
	/**
	 * 
	 * @Title: updateOperatorInf 
	 * @Description: 更新操作员基本信息
	 * @param bodyMap
	 * @throws ServiceException
	 */
	private void updateOperatorInf(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		String operatorUserno = (String)bodyMap.get("operatorUserno");	//	操作员编号
		String operatorName = (String)bodyMap.get("operatorName");	//	操作员姓名
		String operatorEmail = (String)bodyMap.get("operatorEmail");	//	电子邮箱
		String operatorUserEn =(String) bodyMap.get("operatorUserEn");	//	英文名
		String operatorCtftype =(String) bodyMap.get("operatorCtftype");	//	证件类型
		String operatorCtfno =(String) bodyMap.get("operatorCtfno");	//	证件号码
		String operatorStt =(String) bodyMap.get("operatorStt");	//	操作员状态
		String operatorMobile =(String) bodyMap.get("operatorMobile");	//	联系电话
		String operatorJobtitle = (String)bodyMap.get("operatorJobtitle");	//	职位
		String operatorDepartment =(String) bodyMap.get("operatorDepartment");	//	部门
		String cstNo =(String) bodyMap.get("cstNo");	
//		String salaryAuth =(String) bodyMap.get("salaryAuth");	//	工资明细权限
		
		Map<String,Object> param = new HashMap<String,Object>();
		if (ValidateUtil.isEmpty(operatorEmail)) {
			operatorEmail="";
		}
		if (ValidateUtil.isEmpty(operatorUserEn)) {
			operatorUserEn="";
		}
		if (ValidateUtil.isEmpty(operatorMobile)) {
			operatorMobile="";
		}
		if (ValidateUtil.isEmpty(operatorJobtitle)) {
			operatorJobtitle="";
		}
		if (ValidateUtil.isEmpty(operatorDepartment)) {
			operatorDepartment="";
		}
		param.put("operatorUserno", operatorUserno);
		param.put("operatorName", operatorName);
		param.put("operatorEmail", operatorEmail);
		param.put("operatorUserEn", operatorUserEn);
		param.put("operatorCtftype", operatorCtftype);
		param.put("operatorCtfno", operatorCtfno);
		param.put("operatorStt", operatorStt);
		param.put("operatorMobile", operatorMobile);
		param.put("operatorJobtitle", operatorJobtitle);
		param.put("operatorDepartment", operatorDepartment);
		param.put("cstNo", cstNo);
		try {
			int updateOperatorFlag = myBatisDaoSysDao.update("mybatis.mapper.single.table.ttpuserinf.updateTtpUserInfByOperatorId", param);
			if(updateOperatorFlag < 0){
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0012,"修改操作员信息失败");		//修改操作员信息失败
			}
		} catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateOperator method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateOperator method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000);
		}
	}
	/**
	 * 
	 * @Title: updateOperatorAccount 
	 * @Description: 更新操作员账户信息
	 * @param bodyMap
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public void updateOperatorAccount(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		String legalcode=(String)bodyMap.get("legalcode");
		String cstNoString=(String)bodyMap.get("cstNo");
		String operatorId=(String)bodyMap.get("operatorId");
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("cstNo", cstNoString);
		param.put("operatorId", operatorId);
		try {
				
				/*
				 * 添加操作员可操作账号列表
				 */
				List<Map<String,Object>> operAcctInfoList =(List<Map<String, Object>>) bodyMap.get("operAcctInfoList");
				if(null!=operAcctInfoList && operAcctInfoList.size()>0){
					
					/*
					 * 删除操作员可操作账户
					 */
					myBatisDaoSysDao.delete("mybatis.mapper.single.table.ttpuseracc.deleteTtpUserAccByTuiId", param);
					
					for(Map<String,Object> operAccinfMap:operAcctInfoList){
						param.clear();
						param.put("operatorAccId", BaseUtil.createUUID());
						param.put("legalId",legalcode);
						param.put("cstNo", cstNoString);
						param.put("operatorId", operatorId); 
						param.put("operatorAccNo", operAccinfMap.get("operatorAccNo"));   
						param.put("operatorAccAuth", operAccinfMap.get("operatorAccAuth"));
						int k= myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.ttpuseracc.insertTtpUserAcc", param); //插入操作员下账号
						if(k<0){
							throw new ServiceException(CustmanageErrorCodeConstants.PPCG0009,"操作员添加失败 ttp_user_acc 插入失败");
						}
					}
				}
			
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateOperatorAccount method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateOperatorAccount method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000);
		}
	}
	
	
	
	
	/**
	 * 
	 * @Title: updateOperatorAccount 
	 * @Description: 更新角色信息
	 * @param bodyMap
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public void updateOperatorRole(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		String cstNoString=(String)bodyMap.get("cstNo");
		String operatorId=(String)bodyMap.get("operatorId");
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("cstNo", cstNoString);
		param.put("operatorId", operatorId);
		try {
				
				/*
				 * 添加操作员可操作账号列表
				 */
				List<Map<String,Object>> operatorRoleList =(List<Map<String, Object>>) bodyMap.get("operatorRoleList");
				if(null!=operatorRoleList && operatorRoleList.size()>0){
					
					/*
					 * 删除操作员可操作账户
					 */
					
					myBatisDaoSysDao.delete("mybatis.mapper.single.table.TtpUserRoleRel.deleteTtpUserRoleByTuiId", param);
					
					for(Map<String,Object> operRoleinfMap:operatorRoleList){
						param.clear();
						param.put("turrId", BaseUtil.createUUID());
						param.put("operatorId", operatorId); 
						param.put("operatorRoleId", operRoleinfMap.get("treId")); 
						int k= myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.TtpUserRoleRel.insertTtpUserRoleRel", param); //插入操作员下账号
						if(k<0){
							throw new ServiceException(CustmanageErrorCodeConstants.PPCG0009,"操作员添加失败 ttp_user_acc 插入失败");
						}
					}
				}
			
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateOperatorAccount method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateOperatorAccount method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000);
		}
	}

}
