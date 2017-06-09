package bros.provider.parent.bankmanage.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.product.service.IProductServiceService;
/**
 * 
 * @ClassName: ProductServiceServiceImpl 
 * @Description:产品服务实现类 
 * @author huangdazhou
 * @date 2016年6月30日 上午10:39:39 
 * @version 1.0
 */
@Repository(value="productServiceService")
public class ProductServiceServiceImpl implements IProductServiceService {
	private static final  Logger logger = LoggerFactory.getLogger(ProductTypeServiceImpl.class);
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/**
	 * 
	 * @Title: addProductServiceMethod 
	 * @Description: 新增产品服务方法
	 * @param bodyMap 报文体map
	 * @return int 返回结果信息
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int addProductServiceMethod(Map<String, Object> bodyMap) throws ServiceException {
		int resultInt=0;
		try {
			resultInt=myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.prdptservice.insertPrdPtService", bodyMap);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "addProductServiceMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加产品服务信息失败", e);
		}
		return resultInt;
	}

	/**
	 * 
	 * @Title: updateProductServiceMethod 
	 * @Description: 修改产品服务方法
	 * @param bodyMap 报文体map
	 * @return int 返回结果信息
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int updateProductServiceMethod(Map<String, Object> bodyMap) throws ServiceException {
		int resultInt=0;
		try {
			resultInt=myBatisDaoSysDao.update("mybatis.mapper.single.table.prdptservice.updatePrdPtService", bodyMap);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "updateProductServiceMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "修改产品服务信息失败", e);
		}
		return resultInt;
	}

	/**
	 * 
	 * @Title: deleteProductServiceMethod 
	 * @Description: 删除产品服务方法
	 * @param prdSvrCode 产品服务编码
	 * @return int 返回结果信息
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int deleteProductServiceMethod(String prdSvrCode) throws ServiceException {
		int resultInt=0;
		try {
			Map<String, Object> tmpPrdServiceMap=new HashMap<String, Object>();
			tmpPrdServiceMap.put("prdSvrCode",prdSvrCode);
			resultInt=myBatisDaoSysDao.delete("mybatis.mapper.single.table.prdptservice.deletePrdPtServiceByprdSvrCode", tmpPrdServiceMap);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "deleteProductServiceMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除产品服务信息失败", e);
		}
		return resultInt;
	}

	/**
	 * 
	 * @Title: queryProductServiceMethod 
	 * @Description: 查询产品服务方法
	 * @param bodyMap 报文体map
	 * @return Map<String,Object> 返回结果信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryProductServiceMethod(String prdSvrCode,
			String prdTypeCode,String prdSvrName) throws ServiceException {
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();//实例属性
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("prdSvrCode", prdSvrCode);
		parmIN.put("prdTypeCode", prdTypeCode);
		parmIN.put("prdSvrName", prdSvrName);
		try {
			returnList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdptservice.queryPrdPtServiceByParameter", parmIN);
		} catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品服务信息失败", e);
		}
		return returnList;
	}

	/**
	 * 查询产品服务方法
	 */
	public Map<String, Object> queryProductServicePageMethod(
			String prdSvrCode, String prdTypeCode, String prdSvrName,
			int pageNo, int pageSize)
			throws ServiceException {
		int totalNum=0;
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("prdSvrCode", prdSvrCode);
		parmIN.put("prdTypeCode", prdTypeCode);
		parmIN.put("prdSvrName", prdSvrName);
		try {
			returnList=myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdptservice.queryPrdPtServiceByParameter", parmIN, pageNo, pageSize);
			totalNum=queryProductServiceNumMethod(prdSvrCode, prdTypeCode, prdSvrName);
			resultMap.put("returnList", returnList);
			resultMap.put("totalNum", totalNum);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品服务信息失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品服务信息失败", e);
		}
		return resultMap;
	}

	/**
	 * 查询产品服务方法
	 */
	public Map<String, Object> queryProductServiceOneMethod(String prdSvrCode) throws ServiceException {
		int totalNum=0;
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("prdSvrCode", prdSvrCode);
		try {
			resultMap=myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.prdptservice.queryPrdPtServiceByParameter", parmIN);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品服务信息失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品服务信息失败", e);
		}
		return resultMap;
	}

	/**
	 * 查询产品服务方法
	 */
	public int queryProductServiceNumMethod(
			String prdSvrCode, String prdTypeCode, String prdSvrName)
			throws ServiceException {
		int totalNum=0;
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("prdSvrCode", prdSvrCode);
		parmIN.put("prdTypeCode", prdTypeCode);
		parmIN.put("prdSvrName", prdSvrName);
		try {
			totalNum=myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.prdptservice.queryPrdPtServiceNum", parmIN);
		} catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品服务信息失败", e);
		}
		return totalNum;
	}

}
