package bros.provider.parent.bankmanage.bsndef.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.provider.parent.bankmanage.bsndef.service.IBusinessFunctionService;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
/**
 * 
 * @ClassName: BusinessFunctionServiceImpl 
 * @Description: 业务功能管理服务实现类
 * @author huangdazhou 
 * @date 2016年12月23日 上午10:54:46 
 * @version 1.0
 */
@Repository(value = "businessFunctionService")
public class BusinessFunctionServiceImpl implements IBusinessFunctionService {

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	/**
	 * 查询业务功能信息
	 */
	public List<Map<String, Object>> queryBsnFun(String bbfChannel, String bbfId,
			String bbfBsnCode,String bbfType) throws ServiceException {
		Map<String, Object> parmIn=new HashMap<String, Object>();
		parmIn.put("bbfChannel", bbfChannel);
		parmIn.put("bbfId", bbfId);
		parmIn.put("bbfBsnCode", bbfBsnCode);
		parmIn.put("bbfType", bbfType);
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		try {
			returnList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmabsndef.queryBsnFunByAll", parmIn);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询业务功能信息操作失败", e);
		}
		return returnList;
	}
	/**
	 * 查询业务与菜单关联功能
	 */
	public List<Map<String, Object>> queryBsnFunRelMenudef(String bbfChannel,String burlMenueId,String bbfType,String bbfId)
			throws ServiceException {
		Map<String, Object> parmIn=new HashMap<String, Object>();
		parmIn.put("bbfChannel", bbfChannel);
		parmIn.put("burlMenueId", burlMenueId);
		parmIn.put("bbfType", bbfType);
		parmIn.put("bbfId", bbfId);
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		try {
			returnList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmabsndef.queryBsnFunRelMenudef", parmIn);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询业务功能信息操作失败", e);
		}
		return returnList;
	}
	/**
	 * 查询业务菜单关联笔数
	 */
	public int queryBsnFunRelMenuNum(String bbfBsnCode, String burlMenueId)
			throws ServiceException {
		Map<String, Object> parmIn=new HashMap<String, Object>();
		parmIn.put("bbfBsnCode", bbfBsnCode);
		parmIn.put("burlMenueId", burlMenueId);
		int tatolNum=0;
		try {
			tatolNum=myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.bmabsndef.queryBsnRelMenuNum", parmIn);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询业务功能信息操作失败", e);
		}
		return tatolNum;
	}
	/**
	 * 添加业务菜单关联
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int insertBsnFunRelMenu(List<Map<String, Object>> insertList) throws ServiceException {
		int tatolNum=0;
		try {
			tatolNum=myBatisDaoSysDao.insertBatchList("mybatis.mapper.single.table.bmabsndef.insertBsnRelMenu", insertList);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询业务功能信息操作失败", e);
		}
		return tatolNum;
	}
	/**
	 * 修改业务菜单关联
	 */
	/*
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int updateBsnFunRelMenu(List<Map<String, Object>> updateList)
			throws ServiceException {
		int tatolNum=0;
		try {
			tatolNum=myBatisDaoSysDao.updateBatchList("mybatis.mapper.single.table.bmabsndef.updateBsnRelMenu", updateList);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询业务功能信息操作失败", e);
		}
		return tatolNum;
	}
	*/
	/**
	 * 删除业务菜单关联
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int deleteBsnFunRelMenu(List<Map<String, Object>> deleteList)
			throws ServiceException {
		int tatolNum=0;
		try {
			tatolNum=myBatisDaoSysDao.updateBatchList("mybatis.mapper.single.table.bmabsndef.deleteBsnRelMenu", deleteList);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询业务功能信息操作失败", e);
		}
		return tatolNum;
	}
	/**
	 * 添加业务功能信息
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int insertOneBsndef(String bbfBsnCode,String bbfName,String bbfAlias,String bbfDesc,String bbfType,
			String bbfBsnlv,String bbfGroup,String bbfUserlv,String bbfStt,String bbfUrl,String bbfRequireAuth,
			String bbfChannel,String bbfCanbatch,String bbfModel)
			throws ServiceException {
		int tatolNum=0;
		Map<String, Object> parmIn=new HashMap<String, Object>();
		parmIn.put("bbfId", BaseUtil.createUUID());
		parmIn.put("bbfBsnCode", bbfBsnCode);
		parmIn.put("bbfName", bbfName);
		parmIn.put("bbfAlias", bbfAlias);
		parmIn.put("bbfDesc", bbfDesc);
		parmIn.put("bbfType", bbfType);
		parmIn.put("bbfBsnlv", bbfBsnlv);
		parmIn.put("bbfGroup", bbfGroup);
		parmIn.put("bbfUserlv", bbfUserlv);
		parmIn.put("bbfStt", bbfStt);
		parmIn.put("bbfUrl", bbfUrl);
		parmIn.put("bbfRequireAuth", bbfRequireAuth);
		parmIn.put("bbfChannel", bbfChannel);
		parmIn.put("bbfCanbatch", bbfCanbatch);
		parmIn.put("bbfModel", bbfModel);
		try {
			tatolNum=myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.bmabsndef.insertOneBsndef", parmIn);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询业务功能信息操作失败", e);
		}
		return tatolNum;
	}
	/**
	 * 修改业务功能信息
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int updateOneBsndef(String bbfId,String bbfBsnCode,String bbfName,String bbfAlias,String bbfDesc,String bbfType,
			String bbfBsnlv,String bbfGroup,String bbfUserlv,String bbfStt,String bbfUrl,String bbfRequireAuth,
			String bbfChannel,String bbfCanbatch,String bbfModel)
			throws ServiceException {
		int tatolNum=0;
		Map<String, Object> parmIn=new HashMap<String, Object>();
		parmIn.put("bbfId", bbfId);
		parmIn.put("bbfBsnCode", bbfBsnCode);
		parmIn.put("bbfName", bbfName);
		parmIn.put("bbfAlias", bbfAlias);
		parmIn.put("bbfDesc", bbfDesc);
		parmIn.put("bbfType", bbfType);
		parmIn.put("bbfBsnlv", bbfBsnlv);
		parmIn.put("bbfGroup", bbfGroup);
		parmIn.put("bbfUserlv", bbfUserlv);
		parmIn.put("bbfStt", bbfStt);
		parmIn.put("bbfUrl", bbfUrl);
		parmIn.put("bbfRequireAuth", bbfRequireAuth);
		parmIn.put("bbfChannel", bbfChannel);
		parmIn.put("bbfCanbatch", bbfCanbatch);
		parmIn.put("bbfModel", bbfModel);
		try {
			tatolNum=myBatisDaoSysDao.update("mybatis.mapper.single.table.bmabsndef.updateOneBsndef", parmIn);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询业务功能信息操作失败", e);
		}
		return tatolNum;
	}
	/**
	 * 删除业务功能信息
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int deleteOneBsndef(String bbfId)
			throws ServiceException {
		int tatolNum=0;
		Map<String, Object> parmIn=new HashMap<String, Object>();
		parmIn.put("bbfId", bbfId);
		try {
			tatolNum=myBatisDaoSysDao.delete("mybatis.mapper.single.table.bmabsndef.deleteOneBsndef", parmIn);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询业务功能信息操作失败", e);
		}
		return tatolNum;
	}
	

}
