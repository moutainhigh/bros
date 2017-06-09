package bros.provider.parent.custmanage.operator.impl;

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
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.common.core.util.ValidateUtil;
import bros.provider.parent.custmanage.constants.CustmanageConstants;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
import bros.provider.parent.custmanage.operator.IOperatorManageService;

/**
 * 
 * @ClassName: OperatorManageServiceImpl 
 * @Description: 操作员业务实现类
 * @author mazhilei 
 * @date 2016年7月4日 上午9:36:03 
 * @version 1.0
 */
@Component("operatorManage")
public class OperatorManageServiceImpl implements IOperatorManageService {
	private static final  Logger logger = LoggerFactory.getLogger(OperatorManageServiceImpl.class);
	@Autowired
	private MyBatisDaoSysDaoImpl myBatisDaoSysDao;
	@Autowired
	private OperatorUpdateManage operatorUpdateManage;
	@Autowired
	private OperatorDeleteManage operatorDeleteManage;
	/**
	 * 
	 * @Title: addOperatorManageMethod 
	 * @Description: 添加操作员方法
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException
	 */
	@Override
	public void addOperatorManageMethod(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
	try{

		/**
		 * 根据操作员 证件类型、 证件号码  客户号  法人ID判断是否重复
		 */
		checkOperatorManageIsExist(headMap, bodyMap);
		
		/**
		 * 批量添加操作员信息
		 */
		addOperatorInfo(headMap, bodyMap);
		
		/**
		 * 组织返回数据
		 */
		
		
	}catch(ServiceException be){
		logger.error("Exception from " + this.getClass().getName() + "'s checkOperatorManageIsRepetition method.", be);
		throw be;
	}catch(Exception e) {
		logger.error("检查操作员信息是否重复失败 Exception from " + this.getClass().getName() + "'s checkOperatorManageIsRepetition method.", e);
		throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",e);
	}
	}

	/**
	 * 
	 * @Title: checkOperatorManageIsRepetition 
	 * @Description: 检查操作员信息是否重复
	 * @return IsRepetition true：false
	 */
	@SuppressWarnings("unchecked")
	public void checkOperatorManageIsExist (Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException{
		try{
		String legalcode=(String)bodyMap.get("legalcode");
		String cstNo=(String)bodyMap.get("cstNo");
		/**
		 * 批量查询
		 */
		List<Map<String,Object>> operatorAddList=(List<Map<String, Object>>) bodyMap.get("operatorAddList");
		Map<String,Object> param=new HashMap<String,Object>();
		for(Map<String,Object> operatorMap:operatorAddList){
			String operatorCtftype=(String)operatorMap.get("operatorCtftype");
			String operatorCtfno=(String)operatorMap.get("operatorCtfno");
			String operatorUsername=(String)operatorMap.get("operatorUsername");
			param.clear();
			param.put("operatorCtftype", operatorCtftype);
			param.put("operatorCtfno", operatorCtfno);
			param.put("legalcode", legalcode);
			param.put("cstNo", cstNo);
			Map<String, Object> operatorInfMap=myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.TtpUserInfTtpUserInfChannelSqlMapper.queryTtpUserInfByCstManage",param);
			if(null!=operatorInfMap && operatorInfMap.size()>0){
				logger.error("检查操作员信息重复 "+operatorUsername);
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0008,"操作员已经存在  "+operatorUsername); 
			}
		}
		}catch(ServiceException be){
			logger.error("检查操作员信息是否重复失败 Exception from " + this.getClass().getName() + "'s checkOperatorManageIsRepetition method.", be);
			throw be;
		}catch(Exception e) {
			logger.error("检查操作员信息是否重复失败 Exception from " + this.getClass().getName() + "'s checkOperatorManageIsRepetition method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",e);
		}
	}
	/**
	 * 
	 * @Title: checkOperatorManageIsRepetition 
	 * @Description: 添加操作员信息 ttp_user_inf AND ttp_user_inf_channel
	 * @return IsRepetition true：false
	 */
	@SuppressWarnings("unchecked")
	public void addOperatorInfo (Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException{
		try{
		String legalcode=(String)bodyMap.get("legalcode");
		String cstNo=(String)bodyMap.get("cstNo");
		/**
		 * 批量添加操作员信息
		 */
		List<Map<String,Object>> operatorAddList=(List<Map<String, Object>>) bodyMap.get("operatorAddList");
		Map<String,Object> param=new HashMap<String,Object>();
		
		for(Map<String,Object> operatorMap:operatorAddList){
			/*
			 * 添加ttp_user_inf
			 */
			String operatorId=BaseUtil.createUUID();
			param.clear();
			Map<String,Object> userMap=myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.ttpuserinf.queryMaxUserno");
			String userno = Integer.parseInt((String) userMap.get("operatorUserno"))+1+"" ;
			param.put("operatorId",operatorId);
			param.put("operatorUserno",userno);
			param.put("cstNo",cstNo);
			param.put("operatorUsername",operatorMap.get("operatorUsername"));
			param.put("operatorUserEn",operatorMap.get("operatorUserEn"));
			param.put("operatorCtftype",operatorMap.get("operatorCtftype")); 
			param.put("operatorCtfno",operatorMap.get("operatorCtfno"));
			param.put("operatorDepartment",operatorMap.get("operatorDepartment")); 
			param.put("operatorJobtitle",operatorMap.get("operatorJobtitle"));
			param.put("operatorMobile",operatorMap.get("operatorMobile"));
			param.put("operatorEmail",operatorMap.get("operatorEmail"));
			param.put("legalcode",legalcode);
			int i=myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.ttpuserinf.insertTtpUserInf",param);
			if(i<0){
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0009,"操作员添加失败 ttp_user_inf 插入失败");
			}
			/*
			 * 添加ttp_user_inf_channel
			 */
			param.clear();
			param.put("operatorId",operatorId);
			param.put("operatorAlias",operatorMap.get("operatorAlias"));
			param.put("operatorOpandate",operatorMap.get("operatorOpandate"));
			param.put("operatorClosedate",operatorMap.get("operatorClosedate"));
			param.put("operatorOpennode",operatorMap.get("operatorOpennode"));
			param.put("operatorLasedate",operatorMap.get("operatorLasedate"));
			param.put("operatorPassword",operatorMap.get("operatorPassword"));
			param.put("operatorStt",CustmanageConstants.OPERATORSTT_0);
			param.put("operatorChannel",String.valueOf(operatorMap.get("operatorChannel")));

			int j=myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.TtpUserInfChannelSqlMapper.insertTtpUserInfChannel",param);
			if(j<0){
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0009,"操作员添加失败 ttp_user_inf_channel 插入失败");
			}
			/*
			 * 添加操作员可操作账号列表
			 */
			List<Map<String,Object>> operAcctInfoList =(List<Map<String, Object>>) operatorMap.get("operAcctInfoList");
			if(null!=operAcctInfoList && operAcctInfoList.size()>0){
				for(Map<String,Object> operAccinfMap:operAcctInfoList){
					param.clear();
					param.put("operatorAccId", BaseUtil.createUUID());
					param.put("legalId",legalcode);
					param.put("cstNo", cstNo);
					param.put("operatorId", operatorId); 
					param.put("operatorAccNo", operAccinfMap.get("accountNo"));   
					param.put("operatorAccAuth", operAccinfMap.get("accountAuth"));
					int k= myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.ttpuseracc.insertTtpUserAcc", param); //插入操作员下账号
					if(k<0){
						throw new ServiceException(CustmanageErrorCodeConstants.PPCG0009,"操作员添加失败 ttp_user_acc 插入失败");
					}
				}
			}
			/*
			 * 添加操作员角色
			 */
			List<Map<String,Object>> operatorRoleList =(List<Map<String, Object>>) operatorMap.get("operatorRoleList");
			if(null!=operatorRoleList && operatorRoleList.size()>0){
				for(Map<String,Object> operatorRoleMap:operatorRoleList){
					param.clear();
					param.put("turrId", BaseUtil.createUUID());
					param.put("operatorId", operatorId); 
					param.put("operatorRoleId", operatorRoleMap.get("roleId"));
					int g= myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.TtpUserRoleRel.insertTtpUserRoleRel", param); 
					if(g<0){
						throw new ServiceException(CustmanageErrorCodeConstants.PPCG0009,"操作员添加失败 ttp_user_role_rel 插入失败");
					}
				}
			}
		}
		}catch(ServiceException be){
			logger.error("检查操作员信息是否重复失败 Exception from " + this.getClass().getName() + "'s addOperatorInfo method.", be);
			throw be;
		}catch(Exception e) {
			logger.error("添加操作员失败 Exception from " + this.getClass().getName() + "'s addOperatorInfo method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",e);
		}
	}
	/**
	 * 
	 * @Title: queryOperatorManageMethod 
	 * @Description: 查询操作员信息方法
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryOperatorManageMethod(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		ResponseEntity entity=null;
	 try{	
			/**
			 * 1.操作员编号+客户号；
			 * 2.操作员姓名、证件类型、证件号码；
			 * 二选一必输；根据以上两种选择查询操作员信息
			 */	
		    String legalcode=(String)bodyMap.get("legalcode");
			String operatorId   =(String)bodyMap.get("operatorId");
			String userno   =(String)bodyMap.get("operatorUserno");
			String cstNo   =(String)bodyMap.get("cstNo");
			String operatorName  =(String)bodyMap.get("operatorName");
			String operatorCertType =(String)bodyMap.get("operatorCertType");
			String operatorCertNo =(String)bodyMap.get("operatorCertNo");
			
			if(ValidateUtil.isEmpty(operatorId)){
				//操作员编号为空，根据证件及姓名查询
				if(ValidateUtil.isEmpty(operatorName) || ValidateUtil.isEmpty(operatorCertType) || ValidateUtil.isEmpty(operatorCertNo)){
					//请根据操作员编号或者证件+操作员姓名，两种方式进行查询
					throw new ServiceException(CustmanageErrorCodeConstants.PPCG0010,"请根据操作员编号或者证件+操作员姓名，两种方式进行查询");
				}
			}
			//根据上送条件进行查询
			Map<String,Object> paramMap= new HashMap<String,Object> ();
			paramMap.put("operatorId", operatorId);
			paramMap.put("operatorUserno", userno);
			paramMap.put("cstNo", cstNo);
			paramMap.put("operatorName",operatorName);
			paramMap.put("operatorCertType", operatorCertType);
			paramMap.put("operatorCertNo", operatorCertNo);		
			paramMap.put("legalId", legalcode);		
			
		   Map<String,Object>  operatorInfoMap= myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.TtpUserInfTtpUserInfChannelSqlMapper.selectOperatorInfoByParam", paramMap);
		   List<Map<String,Object>> roleList = myBatisDaoSysDao.selectList("mybatis.mapper.relational.table.ttprole-ttpuserrolerel.queryRoleListByOperatorId", paramMap);
		   List<Map<String,Object>> accList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.ttpuseracc.queryAccByUserAcc", paramMap);
			 /**
			  * 返回参数 
			  */
		   if(null==operatorInfoMap|| operatorInfoMap.size()==0){
			  
			  return  entity;
		   }
			String operatorUserno=(String)operatorInfoMap.get("operatorUserno");
			String operatorUsername=(String)operatorInfoMap.get("operatorUsername");
			String operatorCtftype=(String)operatorInfoMap.get("operatorCtftype");
			String operatorCtfno=(String)operatorInfoMap.get("operatorCtfno");
			//String legalcode=(String)operatorInfoMap.get("legalcode");
			String operatorStt=(String)operatorInfoMap.get("operatorStt");
			
			
			String operatorUserEn=(String)operatorInfoMap.get("operatorUserEn");
			String operatorDepartment=(String)operatorInfoMap.get("operatorDepartment");
			String operatorJobtitle=(String)operatorInfoMap.get("operatorJobtitle");
			String operatorMobile=(String)operatorInfoMap.get("operatorMobile");
			String operatorEmail=(String)operatorInfoMap.get("operatorEmail");
			
			
			
			
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("operatorUserno", operatorUserno);
			map.put("operatorUsername", operatorUsername);
			map.put("operatorCtftype", operatorCtftype);
			map.put("operatorCtfno", operatorCtfno);
			map.put("legalcode", legalcode);
			map.put("operatorStt", operatorStt);
			map.put("operatorUserEn", operatorUserEn);
			map.put("operatorDepartment", operatorDepartment);
			map.put("operatorJobtitle", operatorJobtitle);
			map.put("operatorMobile", operatorMobile);
			map.put("operatorEmail", operatorEmail);
			map.put("roleList", roleList);
			map.put("accList", accList);
			entity = new ResponseEntity(map);
			 
		}catch(ServiceException be){
			logger.error("查询操作员信息失败 Exception from " + this.getClass().getName() + "'s addOperatorInfo method.", be);
			throw be;
		}catch(Exception e) {
			logger.error("查询操作员信息失败 Exception from " + this.getClass().getName() + "'s addOperatorInfo method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",e);
		}	
		return entity;
	}

	/**
	 * 
	 * @Title: updateOperatorManageMethod 
	 * @Description: 更新操作员方法
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException
	 */
	@Override
	public void updateOperatorManageMethod(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
				
		operatorUpdateManage.updateOperatorManageMethod(headMap, bodyMap);
		
		}catch(ServiceException be){
			logger.error("更新操作员信息失败 Exception from " + this.getClass().getName() + "'s updateOperatorManageMethod method.", be);
			throw be;
		}catch(Exception e) {
			logger.error("更新操作员信息失败 Exception from " + this.getClass().getName() + "'s updateOperatorManageMethod method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",e);
		}	
	}

	/**
	 * 
	 * @Title: deleteOperatorManageMethod 
	 * @Description: 逻辑删除操作员
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException
	 */
	@Override
	public void updateOperatorSttManageMethod(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		operatorDeleteManage.updateOperatorSttManageMethod(headMap, bodyMap);
	}
    
	/**
	 * 
	 * @Title: queryOperatorListForPage 
	 * @Description: 查询操作员
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryOperatorListForPage(Map<String, Object> headMap, Map<String, Object> bodyMap) 	throws ServiceException {
		Map<String, Object> parmOut = new HashMap<String, Object>();
		Map<String, Object> parmIN = new HashMap<String, Object>();
		try{
			parmIN.put("operatorUserno", (String)bodyMap.get("operatorUserno"));
			List<Map<String, Object>> operatorList  =  new ArrayList<Map<String, Object>>();
			
			int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")==null?"1":bodyMap.get("pageSize")));//每页显示条数
			int pageNo =  Integer.parseInt(String.valueOf(bodyMap.get("pageNo")==null?"10":bodyMap.get("pageNo")));//第几页

			//查询列表
			operatorList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.TtpUserInfTtpUserInfChannelSqlMapper.queryTtpUserInfByCstNo", parmIN, pageNo, pageSize);
		    //查询总条数
			int totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.TtpUserInfTtpUserInfChannelSqlMapper.queryTtpUserInfByCstNoTotalNum", parmIN);
		    //组装返回数据
			parmOut.put("limitList", operatorList);
			parmOut.put("totalNum", totalNum);
			return	parmOut;
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryOperatorListForPage method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001, "查询操作员信息失败 ", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryOperatorListForPage method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001, "查询操作员信息失败 ", ex);
		}
	}

}
