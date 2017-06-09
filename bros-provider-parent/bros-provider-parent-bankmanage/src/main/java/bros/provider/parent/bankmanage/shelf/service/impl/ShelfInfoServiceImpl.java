package bros.provider.parent.bankmanage.shelf.service.impl;

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
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.shelf.service.IShelfInfoService;
/**
 * 
 * @ClassName: ShelfInfoServiceImpl 
 * @Description: 货架基本信息实现类
 * @author gaoyongjing 
 * @date 2016年7月1日 下午15:35:28 
 * @version 1.0
 */
@Repository(value="shelfInfoService")
public class ShelfInfoServiceImpl implements IShelfInfoService {
	/**
	 * 货架基本信息Log
	 */
	private static final  Logger logger = LoggerFactory.getLogger(ShelfInfoServiceImpl.class);
	
    /**
	  * 数据库操作
	  */
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/**
	 * 
	 * @Title: addShelfInfoMethod
	 * @Description: 新增货架信息
	 * @param shelfCode 货架编码
	 * @param shelfName 货架名称
	 * @param shelfDesc 货架描述
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @return 
	 * @throws ServiceException
	 */
	@Override
	public void addShelfInfoMethod(String shelfCode,String shelfName,String shelfDesc,
			String parentShelf,String mallId) throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String shelfFlag = "00";//货架上下架标志 00-上架 01-下架，新增时默认00-上架
			
			param.put("shelfCode",shelfCode);//货架编码
			param.put("shelfName",shelfName);//货架名称
			param.put("shelfDesc",shelfDesc);//货架描述
			param.put("parentShelf",parentShelf);//上级货架编码
			param.put("mallId",mallId);//商城ID
			param.put("shelfFlag",shelfFlag);//货架上下架标志
			
			// 添加货架信息
			myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.prdshelfinfo.insertShelfInfo", param);
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s addShelfInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s addShelfInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加货架信息失败", e);
		}

	}

	/**
	 * 
	 * @Title: updateShelfInfoMethod
	 * @Description: 修改货架信息
	 * @param shelfCode 货架编码
	 * @param shelfName 货架名称
	 * @param shelfDesc 货架描述
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @param shelfFlag 货架上下架标志
	 * @return 
	 * @throws ServiceException
	 */
	@Override
	public void updateShelfInfoMethod(String shelfCode,String shelfName,String shelfDesc,
			String parentShelf,String mallId,String shelfFlag) throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			
			param.put("shelfCode",shelfCode);//货架编码
			param.put("shelfName",shelfName);//货架名称
			param.put("shelfDesc",shelfDesc);//货架描述
			param.put("parentShelf",parentShelf);//上级货架编码
			param.put("mallId",mallId);//商城ID
			param.put("shelfFlag",shelfFlag);//货架上下架标志
			
			// 修改货架信息
			myBatisDaoSysDao.update("mybatis.mapper.single.table.prdshelfinfo.updateShelfInfo", param);
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s updateShelfInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s updateShelfInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "修改货架信息失败", e);
		}

	}
	/**
	 * 
	 * @Title: deleteShelfInfoMethod
	 * @Description: 删除货架信息
	 * @param headMap  头信息
	 * @param shelfCode 货架编码
	 * @param mallId 商城ID
	 * @return 
	 * @throws ServiceException
	 */
	@Override
	public void deleteShelfInfoMethod(String shelfCode,String mallId) throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("shelfCode",shelfCode);//货架编码
			param.put("mallId",mallId);//商城ID
			// 删除货架
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.prdshelfinfo.deleteShelfInfo", param);
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteShelfInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s deleteShelfInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除货架信息失败", e);
		}

	}

	/**
	 * 
	 * @Title: queryShelfInfoMethod
	 * @Description: 查询货架信息
	 * @param paramMap 货架信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @param shelfFlag 上下架标志 00-上架 01-下架
	 * @return List<Map<String,Object>>货架信息列表 
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String,Object>> queryShelfInfoMethod(Map<String, Object> paramMap)throws ServiceException {
		List<Map<String,Object>> shelfInfoList = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode")); 
			String parentShelf = (String) (paramMap.get("parentShelf")==null?"":paramMap.get("parentShelf"));
			String mallId = (String) (paramMap.get("mallId")==null?"":paramMap.get("mallId"));  
			String shelfFlag = (String) (paramMap.get("shelfFlag")==null?"":paramMap.get("shelfFlag"));
			
			param.put("shelfCode",shelfCode);//货架编码
			param.put("parentShelf",parentShelf);//上级货架编码
			param.put("mallId",mallId);//商城ID
			param.put("shelfFlag",shelfFlag);//上下架标志 00-上架 01-下架
			 
			//根据条件查询全部
			shelfInfoList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdshelfinfo.queryShelfInfo", param);
			 
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询货架信息失败", e);
		}
		return shelfInfoList;
	}
	/**
	 * 
	 * @Title: queryShelfInfoPageMethod
	 * @Description: 分页查询货架信息
	 * @param paramMap 货架信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @param shelfFlag 上下架标志 00-上架 01-下架
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return List<Map<String,Object>> 货架信息列表  
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String,Object>> queryShelfInfoPageMethod(Map<String, Object> paramMap)throws ServiceException {
		List<Map<String,Object>> shelfInfoList = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode")); 
			String parentShelf = (String) (paramMap.get("parentShelf")==null?"":paramMap.get("parentShelf"));
			String mallId = (String) (paramMap.get("mallId")==null?"":paramMap.get("mallId"));  
			String shelfFlag = (String) (paramMap.get("shelfFlag")==null?"":paramMap.get("shelfFlag"));
			
			param.put("shelfCode",shelfCode);//货架编码
			param.put("parentShelf",parentShelf);//上级货架编码
			param.put("mallId",mallId);//商城ID
			param.put("shelfFlag",shelfFlag);//上下架标志 00-上架 01-下架
			
			// 分页查询
			int pageNo = (Integer)paramMap.get("pageNo");  //  页码
			int pageSize = (Integer)paramMap.get("pageSize");  //  每页条数
			shelfInfoList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdshelfinfo.queryShelfInfo", param, pageNo, pageSize);
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfInfoPageMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfInfoPageMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "分页查询货架信息失败", e);
		}
		return shelfInfoList;
	}
	/**
	 * 
	 * @Title: queryShelfTotalNumMethod
	 * @Description: 查询货架总条数
	 * @param paramMap 货架信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @param shelfFlag 上下架标志 00-上架 01-下架
	 * @return int 总条数
	 * @throws ServiceException
	 */
	@Override
	public int queryShelfTotalNumMethod(Map<String, Object> paramMap)throws ServiceException {
		int totalNum = 0;
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode")); 
			String parentShelf = (String) (paramMap.get("parentShelf")==null?"":paramMap.get("parentShelf"));
			String mallId = (String) (paramMap.get("mallId")==null?"":paramMap.get("mallId"));  
			String shelfFlag = (String) (paramMap.get("shelfFlag")==null?"":paramMap.get("shelfFlag"));
			
			param.put("shelfCode",shelfCode);//货架编码
			param.put("parentShelf",parentShelf);//上级货架编码
			param.put("mallId",mallId);//商城ID
			param.put("shelfFlag",shelfFlag);//上下架标志 00-上架 01-下架
			
			totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.prdshelfinfo.queryShelfInfoTotalNum", param);
			
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfTotalNumMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfTotalNumMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询货架总条数失败", e);
		}
		return totalNum;
	}
	/**
	 * 
	 * @Title: queryShelfInfoByObjectIdMethod
	 * @Description: 校验货架信息是否存在
	 * @param shelfCode 货架编码
	 * @param mallId 商城ID
	 * @return Map<String,Object> 货架信息
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryShelfInfoByObjectIdMethod(String shelfCode, String mallId)
			throws ServiceException {
		Map<String,Object> shelfInfoMap = new HashMap<String,Object>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("shelfCode",shelfCode);//货架编码
			param.put("mallId",mallId);//商城ID
			//查询货架信息
			shelfInfoMap = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.prdshelfinfo.queryShelfInfoByShelfCode", param);
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfInfoByObjectIdMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfInfoByObjectIdMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询货架信息失败", e);
		}
		return shelfInfoMap;
	}
	
	/**
	 * 
	 * @Title: queryShowShelfInfoMethod
	 * @Description: 根据渠道编码、法人id、机构号查询货架信息
	 * @param headMap  头信息
	 * @param paramMap 信息,包含以下参数
	 * @param chlCode 渠道编码
	 * @param legalPersonId 法人id
	 * @param branchNo 机构号
	 * @return ResponseEntity Map<String,Object> returnList货架信息列表
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryShowShelfInfoMethod(Map<String, Object> paramMap)throws ServiceException {
		Map<String, Object> paramOut = new HashMap<String,Object>();
		try {
			List<Map<String,Object>> shelfInfoList = new ArrayList<Map<String,Object>>();
			Map<String,Object> param=new HashMap<String, Object>();
			String chlCode = (String) (paramMap.get("chlCode")==null?"":paramMap.get("chlCode")); 
			String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));
			String branchNo = (String) (paramMap.get("branchNo")==null?"":paramMap.get("branchNo"));  
			
			param.put("chlCode",chlCode);//渠道编码
			param.put("legalPersonId",legalPersonId);//法人id
			param.put("branchNo",branchNo);//机构号
			//根据条件查询货架信息
			shelfInfoList = myBatisDaoSysDao.selectList("mybatis.mapper.relational.table.prdchlsys-prdmallinfo-prdshelfdef.queryShowShelfInfo", param);
			paramOut.put("returnList", shelfInfoList);
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryShowShelfInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryShowShelfInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "根据渠道编码、法人id、机构号查询货架信息失败", e);
		}
		return paramOut;
	}
}
