/**   
 * @Title: BranchManageServiceImpl.java 
 * @Package bros.provider.parent.branchmanage.service.impl 
 * @Description: 用一句话描述该文件做什么 
 * @author MacPro   
 * @date 2016年6月27日 下午4:22:47 
 * @version 1.0   
 */

package bros.provider.parent.branchmanage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.branchmanage.service.IBranchManageService;

/** 
 * @ClassName: BranchManageServiceImpl 
 * @Description: 机构管理服务实现
 * @author liwei 
 * @date 2016年6月27日 下午4:22:47 
 * @version 1.0 
 */
@Repository(value="branchManageService")
public class BranchManageServiceImpl implements IBranchManageService{
	
	/**
	 * 机构管理Log
	 */
	private static final  Logger logger = LoggerFactory.getLogger(BranchManageServiceImpl.class);
	
	/**
	 * 机构管理SQL语句命名空间
	 */
    private static final String BRANCH_SQL_NAMESPACE = "mybatis.mapper.single.table.branchmanage.";
    
    /**
	  * 数据库操作
	  */
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	

	/** 
	 * @Title: addBranch 
	 * @Description: 添加机构
	 * @param headMap
	 * @param contextMap
	 * @throws ServiceException	异常信息
	 */
	
	@Override
	public void addBranch(Map<String, Object> headMap, Map<String, Object> contextMap) throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String branchId = BaseUtil.createUUID();  //  机构主键UUID
			String branchLegal = (String) contextMap.get("legalPersonId");//法人id
			String branchCode = (String) (contextMap.get("branchCode")==null?"":contextMap.get("branchCode"));  //  机构编号
			String branchName = (String) (contextMap.get("branchName")==null?"":contextMap.get("branchName"));  //  机构全称
			String branchShortname = (String) (contextMap.get("branchShortname")==null?"":contextMap.get("branchShortname"));  //  机构简称
			String branchStt = (String) (contextMap.get("branchStt")==null?"":contextMap.get("branchStt"));  //  机构状态（0-未激活 1-激活 2-日常 3-完工 4-等待开工 5-注销 6-冻结）
			String branchParentid = (String) (contextMap.get("branchParentid")==null?"":contextMap.get("branchParentid"));  //  父级机构主键（对应本表中的BBH_ID字段值）
			String branchDesc = (String) (contextMap.get("branchDesc")==null?"":contextMap.get("branchDesc"));  //  机构描述
			String branchAddress = (String) (contextMap.get("branchAddress")==null?"":contextMap.get("branchAddress"));  //  机构地址
			String branchContact = (String) (contextMap.get("branchContact")==null?"":contextMap.get("branchContact"));  //  机构联系人名称
			String branchPhone = (String) (contextMap.get("branchPhone")==null?"":contextMap.get("branchPhone"));  //  机构联系人电话
			String branchTeller = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO));  //  修改柜员  用于记录该机构最后的修改人
			String branchLevel = (String) (contextMap.get("branchLevel")==null?"":contextMap.get("branchLevel"));  //  机构级别（0-总行 1-分行 2-一级支行 3-二级支行）
			String branchSign_in_time = (String) (contextMap.get("branchSign_in_time")==null?"":contextMap.get("branchSign_in_time"));  //  签到时间
			String branchSign_out_time = (String) (contextMap.get("branchSign_out_time")==null?"":contextMap.get("branchSign_out_time"));  //  签退时间
			
			
			param.put("branchId",branchId);
			param.put("branchLegal",branchLegal);
			param.put("branchCode",branchCode);
			param.put("branchName",branchName);
			param.put("branchShortname",branchShortname);
			param.put("branchStt",branchStt);
			param.put("branchParentid",branchParentid);
			param.put("branchDesc",branchDesc);
			param.put("branchAddress",branchAddress);
			param.put("branchContact",branchContact);
			param.put("branchPhone",branchPhone);
			param.put("branchTeller",branchTeller);
			param.put("branchLevel",branchLevel);
			param.put("branchSign_in_time",branchSign_in_time);
			param.put("branchSign_out_time",branchSign_out_time);
			
			// 添加机构
			myBatisDaoSysDao.insertOne(BRANCH_SQL_NAMESPACE +"insertbranch", param);
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s addBranch method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s addBranch method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加机构", e);
		}
		
	}

	/** 
	 * @Title: updateBranch 
	 * @Description: 修改机构
	 * @param headMap
	 * @param contextMap
	 * @throws ServiceException  异常信息
	 */
	
	@Override
	public void updateBranch(Map<String, Object> headMap, Map<String, Object> contextMap) throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String branchLegal = (String) contextMap.get("legalPersonId");//法人id
			String branchCode = (String) (contextMap.get("branchCode")==null?"":contextMap.get("branchCode"));  //  机构编号
			String branchName = (String) (contextMap.get("branchName")==null?"":contextMap.get("branchName"));  //  机构全称
			String branchShortname = (String) (contextMap.get("branchShortname")==null?"":contextMap.get("branchShortname"));  //  机构简称
			String branchDesc = (String) (contextMap.get("branchDesc")==null?"":contextMap.get("branchDesc"));  //  机构描述
			String branchAddress = (String) (contextMap.get("branchAddress")==null?"":contextMap.get("branchAddress"));  //  机构地址
			String branchContact = (String) (contextMap.get("branchContact")==null?"":contextMap.get("branchContact"));  //  机构联系人名称
			String branchPhone = (String) (contextMap.get("branchPhone")==null?"":contextMap.get("branchPhone"));  //  机构联系人电话
			String branchTeller = (String) (contextMap.get("branchTeller")==null?"":contextMap.get("branchTeller"));  //  修改柜员  用于记录该机构最后的修改人
			String branchStt = (String) (contextMap.get("branchStt")==null?"":contextMap.get("branchStt"));  //  机构状态（0-未激活 1-激活 2-日常 3-完工 4-等待开工 5-注销 6-冻结）

			
			param.put("branchLegal",branchLegal);
			param.put("branchCode",branchCode);
			param.put("branchName",branchName);
			param.put("branchShortname",branchShortname);
			param.put("branchDesc",branchDesc);
			param.put("branchAddress",branchAddress);
			param.put("branchContact",branchContact);
			param.put("branchPhone",branchPhone);
			param.put("branchTeller",branchTeller);
			param.put("branchStt",branchStt);
			
			// 修改机构
			myBatisDaoSysDao.update(BRANCH_SQL_NAMESPACE + "updatebranch",param);
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s updateBranch method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s updateBranch method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "修改机构", e);
		}
		
	}

	/** 
	 * @Title: deleteBranch 
	 * @Description: 删除机构
	 * @param headMap
	 * @param contextMap
	 * @throws ServiceException 异常信息
	 */
	
	@Override
	public void deleteBranch(Map<String, Object> headMap, Map<String, Object> contextMap) throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String branchLegal = (String) contextMap.get("legalPersonId");//法人id
			String branchCode = (String) (contextMap.get("branchCode")==null?"":contextMap.get("branchCode"));  //  机构编号
			String branchStt = "5";  //  机构状态
			
			param.put("branchLegal",branchLegal);
			param.put("branchCode",branchCode);
			param.put("branchStt",branchStt);
			
			// 删除机构
			myBatisDaoSysDao.update(BRANCH_SQL_NAMESPACE + "deleteBranchByBranchId",param);
			
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteBranch method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s updateBranch method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除机构", e);
		}
	}

	/** 
	 * @Title: queryAllBaranch 
	 * @Description: 这里用一句话描述这个方法的作用
	 * @param headMap
	 * @param contextMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	
	@Override
	public Map<String, Object> queryAllBaranch(Map<String, Object> headMap, Map<String, Object> contextMap)
			throws ServiceException {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			String branchLegal = (String) contextMap.get("legalPersonId");//法人id
			String branchCode = (String) (contextMap.get("branchCode")==null?"":contextMap.get("branchCode"));//机构编号
			String branchStt = (String) (contextMap.get("branchStt")==null?"":contextMap.get("branchStt"));  //  机构状态（0-未激活 1-激活 2-日常 3-完工 4-等待开工 5-注销 6-冻结）
			
						
			parmIn.put("branchLegal", branchLegal);
			parmIn.put("branchCode", branchCode);
			parmIn.put("branchStt", branchStt);
			
			List<Map<String, Object>> branchList  =  new ArrayList<Map<String, Object>>();		
			//查询机构信息列表
			branchList = myBatisDaoSysDao.selectList(BRANCH_SQL_NAMESPACE +"queryAllBranchByObjectId",parmIn);	
			
			returnMap.put("returnList", branchList);
			//查询总条数
//			int totalNum = myBatisDaoSysDao.selectTotalNum(BRANCH_SQL_NAMESPACE +"queryAllBranchByObjectIdnum",parmIn);
//			returnMap.put("totalNum", totalNum);
		} catch (ServiceException se) {
			logger.error("查询机构信息失败   " + this.getClass() + ".queryAllBaranch");
			throw se;
		} catch (Exception e) {
			logger.error("查询机构信息失败   " + this.getClass() + ".queryAllBaranch");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询机构信息操作失败", e);
		}
		return returnMap;
	}

	/** 
	 * @Title: queryAllBranchByObjectId 
	 * @Description: 判断机构是否存在
	 * @param headMap
	 * @param contextMap
	 * @return
	 * @throws ServiceException 异常信息
	 */
	
	@Override
	public Map<String, Object> queryAllBranchByObjectId(Map<String, Object> contextMap) throws ServiceException {
		
		Map<String, Object> parmIn = new HashMap<String,Object>();
		String branchLegal = (String) contextMap.get("legalPersonId");//法人id
		String branchCode = (String) (contextMap.get("branchCode")==null?"":contextMap.get("branchCode"));//机构编号
		parmIn.put("branchCode", branchCode);
		parmIn.put("branchLegal", branchLegal);
		Map<String, Object> result = null;
		try {
			result = myBatisDaoSysDao.selectOne(BRANCH_SQL_NAMESPACE +"queryAllBranchByObjectId",parmIn);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryAllBranchByObjectId method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryAllBranchByObjectId method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "判断机构是否存在", e);
		}
		return result;
	}

}
