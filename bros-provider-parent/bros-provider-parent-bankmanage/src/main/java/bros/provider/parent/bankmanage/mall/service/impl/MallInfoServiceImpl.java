package bros.provider.parent.bankmanage.mall.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.mall.service.IMallInfoService;
/**
 * 
 * @ClassName: MallInfoServiceImpl 
 * @Description: 商城基本信息维护实现类
 * @author gaoyongjing 
 * @date 2016年6月30日 下午15:35:28 
 * @version 1.0
 */
@Repository(value = "mallInfoService")
public class MallInfoServiceImpl implements IMallInfoService {
	/**
	 * 商城信息Log
	 */
	private static final  Logger logger = LoggerFactory.getLogger(MallInfoServiceImpl.class);
	 /**
	  * 数据库操作
	  */
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	
	/**
	 * 
	 * @Title: addMallInfoMethod
	 * @Description: 新增商城信息
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param mallDesc 商城描述
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @param pageName 登录首页页面名称
	 * @return 
	 * @throws ServiceException
	 */
	@Override
	public void addMallInfoMethod(String mallCode,String mallName,String mallDesc,
			String chlId,String branchNo,String pageName ) throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String mallId = BaseUtil.createUUID();  
			
			param.put("mallId",mallId);// 商城ID
			param.put("mallCode",mallCode);// 商城编码
			param.put("mallName",mallName);// 商城名称
			param.put("mallDesc",mallDesc);// 商城描述
			param.put("branchNo",branchNo);// 机构号
			param.put("pageName",pageName);// 登录首页页面名称
			param.put("chlId",chlId);//渠道系统唯一标识
			
			// 添加商城信息
			myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.prdmallinfo.insertMallInfo", param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s addMallInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s addMallInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加商城信息失败", e);
		}

	}
	/**
	 * 
	 * @Title: updateMallInfoMethod
	 * @Description: 修改商城信息
	 * @param mallId 商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param mallDesc 商城描述
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @param pageName 登录首页页面名称
	 * @return 
	 * @throws ServiceException
	 */
	@Override
	public void updateMallInfoMethod(String mallId,String mallCode,String mallName,String mallDesc,
			String chlId,String branchNo,String pageName) throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("mallId",mallId);// 商城ID
			param.put("mallCode",mallCode);// 商城编码
			param.put("mallName",mallName);// 商城名称
			param.put("mallDesc",mallDesc);// 商城描述
			param.put("branchNo",branchNo);// 机构号
			param.put("pageName",pageName);// 登录首页页面名称
			param.put("chlId",chlId);//渠道系统唯一标识
			
			// 修改商城信息
			myBatisDaoSysDao.update("mybatis.mapper.single.table.prdmallinfo.updateMallInfo", param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s updateMallInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s updateMallInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "修改商城信息失败", e);
		}

	}
	/**
	 * 
	 * @Title: deleteMallInfoMethod
	 * @Description: 删除商城信息
	 * @param mallId 商城ID
	 * @param mallCode 商城编号
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @return 
	 * @throws ServiceException
	 */
	@Override
	public void deleteMallInfoMethod(String mallId,String mallCode,String chlId,String branchNo) throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("mallId",mallId);// 商城ID
			param.put("mallCode",mallCode);// 商城编码
			param.put("branchNo",branchNo);// 机构号
			param.put("chlId",chlId);//渠道系统唯一标识
			
			// 删除商城信息
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.prdmallinfo.deleteMallInfo", param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteMallInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s deleteMallInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除商城信息失败", e);
		}
	}
	/**
	 * 
	 * @Title: queryMallInfoMethod
	 * @Description: 查询商城信息
	 * @param paramMap 商城信息包含以下参数
	 * @param mallId  商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @return List<Map<String,Object>>商城信息列表 
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String,Object>>  queryMallInfoMethod(Map<String, Object> paramMap) throws ServiceException {
		List<Map<String,Object>> mallList = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String mallId = (String) (paramMap.get("mallId")==null?"":paramMap.get("mallId"));
			String mallCode = (String) (paramMap.get("mallCode")==null?"":paramMap.get("mallCode"));  
			String mallName = (String) (paramMap.get("mallName")==null?"":paramMap.get("mallName"));  
			String chlId = (String) (paramMap.get("chlId")==null?"":paramMap.get("chlId"));  
			String branchNo = (String) (paramMap.get("branchNo")==null?"":paramMap.get("branchNo"));  
			
			param.put("mallId",mallId);// 商城ID
			param.put("mallCode",mallCode);// 商城编码
			param.put("mallName",mallName);// 商城名称
			param.put("branchNo",branchNo);// 机构号
			param.put("chlId",chlId);//渠道系统唯一标识
			 
			//查询商城信息
			mallList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdmallinfo.queryMallInfo", param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询商城信息失败", e);
		}
		return mallList;
	}
	/**
	 * 
	 * @Title: queryMallInfoPageMethod
	 * @Description: 分页查询商城信息
	 * @param paramMap 商城信息包含以下参数
	 * @param mallId  商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return List<Map<String,Object>>商城信息列表
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String,Object>> queryMallInfoPageMethod(Map<String, Object> paramMap) throws ServiceException {
		List<Map<String,Object>> mallList = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String mallId = (String) (paramMap.get("mallId")==null?"":paramMap.get("mallId"));
			String mallCode = (String) (paramMap.get("mallCode")==null?"":paramMap.get("mallCode"));  
			String mallName = (String) (paramMap.get("mallName")==null?"":paramMap.get("mallName"));  
			String chlId = (String) (paramMap.get("chlId")==null?"":paramMap.get("chlId"));  
			String branchNo = (String) (paramMap.get("branchNo")==null?"":paramMap.get("branchNo"));  
			
			param.put("mallId",mallId);// 商城ID
			param.put("mallCode",mallCode);// 商城编码
			param.put("mallName",mallName);// 商城名称
			param.put("branchNo",branchNo);// 机构号
			param.put("chlId",chlId);//渠道系统唯一标识
			//分页查询 查询商城信息
			int pageNo = (Integer)paramMap.get("pageNo");  //  页码
			int pageSize = (Integer)paramMap.get("pageSize");  //  每页条数
			mallList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdmallinfo.queryMallInfo", param, pageNo, pageSize);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallInfoPageMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallInfoPageMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "分页查询商城信息失败", e);
		}
		return mallList;
	}
	
	/**
	 * 
	 * @Title: queryMallInfoMethod
	 * @Description: 查询商城信息
	 * @param paramMap 商城信息包含以下参数
	 * @param mallId  商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @return  totalNum 总条数
	 * @throws ServiceException
	 */
	@Override
	public int queryMallTotalNumMethod(Map<String, Object> paramMap) throws ServiceException {
		int totalNum = 0;
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String mallId = (String) (paramMap.get("mallId")==null?"":paramMap.get("mallId"));
			String mallCode = (String) (paramMap.get("mallCode")==null?"":paramMap.get("mallCode"));  
			String mallName = (String) (paramMap.get("mallName")==null?"":paramMap.get("mallName"));  
			String chlId = (String) (paramMap.get("chlId")==null?"":paramMap.get("chlId"));  
			String branchNo = (String) (paramMap.get("branchNo")==null?"":paramMap.get("branchNo"));  
			
			param.put("mallId",mallId);// 商城ID
			param.put("mallCode",mallCode);// 商城编码
			param.put("mallName",mallName);// 商城名称
			param.put("branchNo",branchNo);// 机构号
			param.put("chlId",chlId);//渠道系统唯一标识
			 
			//查询总条数
			totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.prdmallinfo.queryMallInfoTotalNum", param);
			
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallTotalNumMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallTotalNumMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询商城总条数失败", e);
		}
		return totalNum;
	}
	
	/**
	 * 
	 * @Title: queryMallInfoByObjectIdMethod
	 * @Description: 查询商城信息是否存在
	 * @param mallCode 商城编号
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @param mallId 商城唯一id
	 * @return Map<String,Object> 商城信息
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryMallInfoByObjectIdMethod(String mallCode,String branchNo,String chlId,String mallId) throws ServiceException {
		Map<String, Object> paramOut = new HashMap<String,Object>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("mallCode",mallCode);// 商城编码
			param.put("branchNo",branchNo);// 机构号
			param.put("chlId",chlId);//渠道系统唯一标识
			param.put("mallId",mallId);//商城唯一id
			 
			//查询商城信息
			paramOut = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.prdmallinfo.queryMallInfo", param);
			
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallInfoByObjectIdMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallInfoByObjectIdMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询商城信息失败", e);
		}
		return paramOut;
	}
	/**
	 * 
	 * @Title: queryMallInfoByBranchNoMethod
	 * @Description: 根据机构号渠道标识查询商城信息是否存在
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @return Map<String,Object> 商城信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryMallInfoByBranchNoMethod(String branchNo,String chlId) throws ServiceException {
		Map<String, Object> paramOut = new HashMap<String,Object>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("branchNo",branchNo);// 机构号
			param.put("chlId",chlId);//渠道系统唯一标识
			 
			//查询商城信息
			paramOut = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.prdmallinfo.queryMallDetailedInfo", param);
			
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallInfoByBranchNoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallInfoByBranchNoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "根据机构号渠道标识查询商城信息是否存在失败", e);
		}
		return paramOut;
	}
	
	/**
	 * 
	 * @Title: queryMallInfoByChlCodeLegalIdBranchNoMethod
	 * @Description: 根据法人id、渠道编码、机构号查询商城信息
	 * @param headMap  头信息
	 * @param paramMap 商城信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @param chlCode 渠道编码
	 * @param branchNo 机构号
	 * @return ResponseEntity Map<String,Object> mallInfoMap商城信息
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryMallInfoByChlCodeLegalIdBranchNoMethod(Map<String,Object> paramMap) throws ServiceException {
		Map<String, Object> mallInfoMap = new HashMap<String,Object>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));
			String chlCode = (String) (paramMap.get("chlCode")==null?"":paramMap.get("chlCode"));  
			String branchNo = (String) (paramMap.get("branchNo")==null?"":paramMap.get("branchNo"));
			
			param.put("legalPersonId",legalPersonId);// 法人ID
			param.put("chlCode",chlCode);            // 渠道编码
			param.put("branchNo",branchNo);          // 机构号
			
			//查询商城信息
			mallInfoMap = myBatisDaoSysDao.selectOne("mybatis.mapper.relational.table.prdchlsys-prdmallinfo-prdshelfdef.queryMallDetailInfo", param);
			
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallInfoByChlCodeLegalIdBranchNoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallInfoByChlCodeLegalIdBranchNoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "根据法人id、渠道编码、机构号查询商城信息失败", e);
		}
		return mallInfoMap;
	}
}
