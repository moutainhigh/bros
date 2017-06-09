package bros.provider.parent.custmanage.recvPersonInfo.service.impl;

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
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.common.core.util.PersonUtil;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
import bros.provider.parent.custmanage.recvPersonInfo.service.IRecvPersonInfoService;
/**
 * 
 * @ClassName: RecvPersonInfoServiceImpl 
 * @Description: 收款人名册维护实现类
 * @author 高永静
 * @date 2016年9月20日 上午9:45:17 
 * @version 1.0
 */
@Repository(value = "recvPersonInfoService")
public class RecvPersonInfoServiceImpl implements IRecvPersonInfoService {
	private static final Logger logger = LoggerFactory.getLogger(RecvPersonInfoServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/**
	 * 
	 * @Title: addRecvPersonInfoMethod
	 * @Description: 新增收款人信息
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public void addRecvPersonInfoMethod(Map<String, Object> payBookMap)
			throws ServiceException {
		try {
			String payBookId = PersonUtil.getDefaultSeq();  // 收款人记录唯一标识
			String comitrNo = (String) (payBookMap.get("comitrNo")==null?"":payBookMap.get("comitrNo"));//联行行号
			String transType = (String) (payBookMap.get("transType")==null?"":payBookMap.get("transType"));//转账类型
			payBookMap.put("payBookId", payBookId);
			myBatisDaoSysDao.insertOne("mybatis.mapper.relational.table.tprpaybook-subpaybook.insertPayBookInfo",payBookMap);
			if(!"".equals(transType)){//转账类型不为空则添加子表信息
				 this.addSubRecvPersonInfoMethod(payBookId, transType, comitrNo);
			}
		} catch (ServiceException se) {
			logger.error("新增收款人信息失败   " + this.getClass() + ".addRecvPersonInfoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("新增收款人信息失败    " + this.getClass() + ".addRecvPersonInfoMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"新增收款人信息失败", e);
		}

	}
	/**
	 * 
	 * @Title: updateRecvPersonInfoMethod
	 * @Description: 修改收款人信息
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void updateRecvPersonInfoMethod(Map<String, Object> payBookMap)
			throws ServiceException {
		try {
			int updateCount = myBatisDaoSysDao.update("mybatis.mapper.relational.table.tprpaybook-subpaybook.updatePayBookInfo",payBookMap);
			if(updateCount!=1){
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"修改收款人信息失败");
			}
		} catch (ServiceException se) {
			logger.error("修改收款人信息失败   " + this.getClass() + ".updateRecvPersonInfoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("修改收款人信息失败    " + this.getClass() + ".updateRecvPersonInfoMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"修改收款人信息失败", e);
		}

	}
	/**
	 * 
	 * @Title: deleteRecvPersonInfoMethod
	 * @Description:     删除收款人信息
	 * @param payBookId  收款人记录唯一标识
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void deleteRecvPersonInfoMethod(String payBookId)
			throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("payBookId",payBookId);//收款人记录唯一标识
			int deleteCount = myBatisDaoSysDao.delete("mybatis.mapper.relational.table.tprpaybook-subpaybook.deletePayBookInfo",param);
			if(deleteCount < 1){
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"删除收款人信息失败");
			}
		} catch (ServiceException se) {
			logger.error("删除收款人信息失败   " + this.getClass() + ".deleteRecvPersonInfoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("删除收款人信息失败    " + this.getClass() + ".deleteRecvPersonInfoMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"删除收款人信息失败", e);
		}

	}
	
	/**
	 * @Title: queryAllRecvPersonInfoMethod
	 * @Description:        查询该渠道客户的所有收款人名册列表
	 * @param cstId   	客户Id
	 * @param payBookId 	收款人唯一id
	 * @param payeeType 	收款人类型
	 * @param accountName	账号名称
	 * @param accountNo		账号
	 * @return recvPersonInfoList 收款人名册列表
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryAllRecvPersonInfoMethod(String cstId,String payBookId,String payeeType,String accountName,String accountNo)throws ServiceException{
		List<Map<String,Object>> recvPersonInfoList = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("cstId",cstId);//客户号
			param.put("payeeType",payeeType);//收款人类型
			param.put("accountName",accountName);//账号名称
			param.put("accountNo",accountNo);//账号
			param.put("payBookId",payBookId);//收款人唯一id
			recvPersonInfoList = myBatisDaoSysDao.selectList("mybatis.mapper.relational.table.tprpaybook-subpaybook.queryAllPayBooksForPageByCstNo",param);
			 
		} catch (ServiceException se) {
			logger.error("查询该渠道客户的所有收款人名册列表失败   " + this.getClass() + ".queryAllRecvPersonInfoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("查询该渠道客户的所有收款人名册列表失败    " + this.getClass() + ".queryAllRecvPersonInfoMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"查询该渠道客户的所有收款人名册列表失败", e);
		}
		return recvPersonInfoList;
	}
	/**
	 * @Title: queryAllRecvPersonInfoMethod
	 * @Description:        分页查询该渠道客户的所有收款人名册列表
	 * @param cstId   	客户Id
	 * @param payBookId 	收款人唯一id
	 * @param payeeType 	收款人类型
	 * @param accountName	账号名称
	 * @param accountNo		账号
	 * @param pageNo		页码
	 * @param pageSize		每页条数
	 * @return recvPersonInfoList 收款人名册列表
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryRecvPersonInfoPageMethod(String cstId,String payBookId,String payeeType,String accountName,String accountNo,int pageNo,int pageSize)throws ServiceException{
		List<Map<String,Object>> recvPersonInfoList = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("cstId",cstId);//法人ID
			param.put("payeeType",payeeType);//收款人类型
			param.put("accountName",accountName);//账号名称
			param.put("accountNo",accountNo);//账号
			param.put("payBookId",payBookId);//收款人唯一id
			recvPersonInfoList = myBatisDaoSysDao.selectListPage("mybatis.mapper.relational.table.tprpaybook-subpaybook.queryAllPayBooksForPageByCstNo",param,pageNo, pageSize);
			 
		} catch (ServiceException se) {
			logger.error("分页查询该渠道客户的所有收款人名册列表失败   " + this.getClass() + ".queryRecvPersonInfoPageMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("分页查询该渠道客户的所有收款人名册列表失败    " + this.getClass() + ".queryRecvPersonInfoPageMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"分页查询该渠道客户的所有收款人名册列表失败", e);
		}
		return recvPersonInfoList;
	}
	/**
	 * 
	 * @Title: queryRecvPersonTotalNumMethod
	 * @Description:        查询该渠道客户的所有收款人名册列表的总记录数
	 * @param legalId   	法人Id
	 * @param payBookId 	收款人唯一id
	 * @param cstNo         客户号
	 * @param payeeType 	收款人类型
	 * @param accountName	账号名称
	 * @param accountNo		账号
	 * @return totalNum 	总条数
	 * @throws ServiceException
	 */
	public int queryRecvPersonTotalNumMethod (String cstId,String payBookId,String payeeType,String accountName,String accountNo)throws ServiceException{
		int totalNum = 0;
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("cstId",cstId);
			param.put("payeeType",payeeType);
			param.put("accountName",accountName);
			param.put("accountNo",accountNo);
			param.put("payBookId",payBookId);//收款人唯一id
			totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.relational.table.tprpaybook-subpaybook.queryAllPayBooksForCount",param);
			 
		} catch (ServiceException se) {
			logger.error("查询收款人名册总条数失败   " + this.getClass() + ".queryRecvPersonTotalNumMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("查询收款人名册总条数失败    " + this.getClass() + ".queryRecvPersonTotalNumMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"查询收款人名册总条数失败", e);
		}
		return totalNum;
	}
	/**
	 * 
	 * @Title: queryRecvPersonInfoByPayBookIdMethod
	 * @Description:        	根据收款人名册ID查询
	 * @param payBookId     	收款人唯一id
	 * @return recvPersonInfoMap收款人信息
	 * @throws ServiceException
	 */
	public Map<String,Object> queryRecvPersonInfoByPayBookIdMethod (String payBookId)throws ServiceException{
		Map<String,Object> recvPersonInfoMap = new HashMap<String,Object>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			
			param.put("payBookId",payBookId);
			
			recvPersonInfoMap = myBatisDaoSysDao.selectOne("mybatis.mapper.relational.table.tprpaybook-subpaybook.queryPayBookByBookId",param);
			 
		} catch (ServiceException se) {
			logger.error("根据收款人名册ID查询收款人名册失败   " + this.getClass() + ".queryRecvPersonInfoByPayBookIdMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("根据收款人名册ID查询收款人名册失败    " + this.getClass() + ".queryRecvPersonInfoByPayBookIdMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"根据收款人名册ID查询收款人名册失败", e);
		}
		return recvPersonInfoMap;
	}
	/**
	 * 
	 * @Title: queryRecvPersonInfoByCstNoAccNoMethod
	 * @Description:    根据渠道客户号、账号、手机号、账号名称,查询收款人信息，如果是手机号收款人，根据手机号，否则根据账号查
	 * @param cstId   客户Id
	 * @param payeeType	转账类型
	 * @param mobileNo	手机号
	 * @param accountNo	账号
	 * @param accountName 账号名称
	 * @return recvPersonInfoMap收款人信息
	 * @throws ServiceException
	 */
	public Map<String,Object> queryRecvPersonInfoByCstNoAccNoMethod(String cstId,String payeeType,String mobileNo,String accountNo,String accountName)throws ServiceException{
		Map<String,Object> recvPersonInfoMap = new HashMap<String,Object>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			
			param.put("cstId",cstId);
			param.put("payeeType",payeeType);
			param.put("mobileNo",mobileNo);
			param.put("accountNo",accountNo);
			param.put("accountName",accountName);
			
			recvPersonInfoMap = myBatisDaoSysDao.selectOne("mybatis.mapper.relational.table.tprpaybook-subpaybook.queryPayBooksByCstNoAccNo",param);
			 
		} catch (ServiceException se) {
			logger.error("查询收款人名册列表失败   " + this.getClass() + ".queryRecvPersonInfoByCstNoAccNoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("查询收款人名册列表失败    " + this.getClass() + ".queryRecvPersonInfoByCstNoAccNoMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"查询收款人名册列表失败", e);
		}
		return recvPersonInfoMap;
	}
	/**
	 * 
	 * @Title: addSubRecvPersonInfoMethod
	 * @Description: 新增收款人子表信息
	 * @param payBookId 收款人记录唯一标识
	 * @param transType 转账类型
	 * @param comitrNo  收款银行联行号或网银互联号（他行使用）
	 * @return
	 * @throws ServiceException
	 */
	public void addSubRecvPersonInfoMethod(String payBookId,String transType,String comitrNo)throws ServiceException{
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String objectId = BaseUtil.createUUID();  //唯一ID
			param.put("objectId",objectId);
			param.put("payBookId",payBookId);
			param.put("transType",transType);
			param.put("comitrNo",comitrNo);
			
			myBatisDaoSysDao.selectOne("mybatis.mapper.relational.table.tprpaybook-subpaybook.insertSubPayBook",param);
			 
		} catch (ServiceException se) {
			logger.error("新增收款人子表信息失败   " + this.getClass() + ".addSubRecvPersonInfoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("新增收款人子表信息失败    " + this.getClass() + ".addSubRecvPersonInfoMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"新增收款人子表信息失败", e);
		}
	}
	/**
	 * 
	 * @Title: updateSubRecvPersonInfoMethod
	 * @Description:    修改收款人子表信息
	 * @param payBookId 收款人记录唯一标识
	 * @param transType 转账类型
	 * @param comitrNo  收款银行联行号或网银互联号（他行使用）
	 * @return
	 * @throws ServiceException
	 */
	public void updateSubRecvPersonInfoMethod(String payBookId,String transType,String comitrNo) throws ServiceException{
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			
			param.put("payBookId",payBookId);
			param.put("transType",transType);
			param.put("comitrNo",comitrNo);
			
			myBatisDaoSysDao.selectOne("mybatis.mapper.relational.table.tprpaybook-subpaybook.updateSubPayBookByPayBookIdAndTranType",param);
			 
		} catch (ServiceException se) {
			logger.error("修改收款人子表信息失败   " + this.getClass() + ".updateSubRecvPersonInfoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("修改收款人子表信息失败    " + this.getClass() + ".updateSubRecvPersonInfoMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"修改收款人子表信息失败", e);
		}
	}
	/**
	 * 
	 * @Title: deleteSubRecvPersonInfoMethod
	 * @Description:     删除收款人子表信息
	 * @param payBookId  收款人记录唯一标识
	 * @return
	 * @throws ServiceException
	 */
	public void deleteSubRecvPersonInfoMethod (String payBookId)throws ServiceException{
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			
			param.put("payBookId",payBookId);
			 
			myBatisDaoSysDao.delete("mybatis.mapper.relational.table.tprpaybook-subpaybook.deleteSubPayBookByObjectId",param);
			 
		} catch (ServiceException se) {
			logger.error("删除收款人子表信息失败   " + this.getClass() + ".deleteSubRecvPersonInfoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("删除收款人子表信息失败    " + this.getClass() + ".deleteSubRecvPersonInfoMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"删除收款人子表信息失败", e);
		}
	}
	/**
	 * @Title: queryAllSubRecvPersonInfoMethod
	 * @Description:        根据payBookId查询收款人名册子表中的所有记录
	 * @param payBookId  	收款人记录唯一标识
	 * @return 
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryAllSubRecvPersonInfoMethod(String payBookId)throws ServiceException{
		List<Map<String,Object>> recvPersonInfoList = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			
			param.put("payBookId",payBookId);
			
			recvPersonInfoList = myBatisDaoSysDao.selectList("mybatis.mapper.relational.table.tprpaybook-subpaybook.querySubPayBookByPayBookId",param);
			 
		} catch (ServiceException se) {
			logger.error("根据payBookId查询收款人名册子表失败   " + this.getClass() + ".queryAllSubRecvPersonInfoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("根据payBookId查询收款人名册子表失败    " + this.getClass() + ".queryAllSubRecvPersonInfoMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"根据payBookId查询收款人名册子表失败", e);
		}
		return recvPersonInfoList;
	}
	/**
	 * @Title: querySubRecvPersonInfoMethod
	 * @Description:        根据payBookId和tranType查询收款人名册子表中的是否存在记录
	 * @param payBookId     收款人记录唯一标识
	 * @param transType 	转账类型
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> querySubRecvPersonInfoMethod(String payBookId,String transType)throws ServiceException{
		Map<String,Object> recvPersonInfoMap = new HashMap<String,Object>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			
			param.put("payBookId",payBookId);
			param.put("transType",transType);
			
			recvPersonInfoMap = myBatisDaoSysDao.selectOne("mybatis.mapper.relational.table.tprpaybook-subpaybook.querySubPayBookByPayBookIdAndTranType",param);
			 
		} catch (ServiceException se) {
			logger.error("收款人名册子表信息失败   " + this.getClass() + ".querySubRecvPersonInfoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("收款人名册子表信息失败    " + this.getClass() + ".querySubRecvPersonInfoMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"收款人名册子表信息失败", e);
		}
		return recvPersonInfoMap;
	}
	
	/**
	 * @Title: queryRecvPersonInfoPageWithSubPayBookMethod
	 * @Description:        分页查询该渠道客户的所有收款人名册列表及子表信息
	 * @param cstId   	客户Id
	 * @param payBookId 	收款人唯一id
	 * @param payeeType 	收款人类型
	 * @param accountName	账号名称
	 * @param accountNo		账号
	 * @return recvPersonInfoList 收款人名册列表
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryRecvPersonInfoPageWithSubPayBookMethod(String cstId,String payBookId,String payeeType,String accountName,String accountNo,int pageNo,int pageSize)throws ServiceException{
		List<Map<String,Object>> recvPersonInfoList = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("cstId",cstId);//法人ID
			param.put("payeeType",payeeType);//收款人类型
			param.put("accountName",accountName);//账号名称
			param.put("accountNo",accountNo);//账号
			param.put("payBookId",payBookId);//收款人唯一id
			
			recvPersonInfoList = myBatisDaoSysDao.selectListPage("mybatis.mapper.relational.table.tprpaybook-subpaybook.queryAllPayBooksForPageByCstNoWithSubPayBook",param,pageNo, pageSize);
			 
		} catch (ServiceException se) {
			logger.error("分页查询该渠道客户的所有收款人名册列表失败   " + this.getClass() + ".queryRecvPersonInfoPageWithSubPayBookMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("分页查询该渠道客户的所有收款人名册列表失败    " + this.getClass() + ".queryRecvPersonInfoPageWithSubPayBookMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"分页查询该渠道客户的所有收款人名册列表失败", e);
		}
		return recvPersonInfoList;
	}
	/**
	 * 
	 * @Title: queryRecvPersonTotalNumWithSubPayBookMethod
	 * @Description:        查询该渠道客户的所有收款人名册列表及子表的总记录数
	 * @param cstId   	客户Id
	 * @param payBookId 	收款人唯一id
	 * @param payeeType 	收款人类型
	 * @param accountName	账号名称
	 * @param accountNo		账号
	 * @return totalNum 	总条数
	 * @throws ServiceException
	 */
	public int queryRecvPersonTotalNumWithSubPayBookMethod (String cstId,String payBookId,String payeeType,String accountName,String accountNo)throws ServiceException{
		int totalNum = 0;
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("cstId",cstId);//法人ID
			param.put("payeeType",payeeType);
			param.put("accountName",accountName);
			param.put("accountNo",accountNo);
			param.put("payBookId",payBookId);
			
			totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.relational.table.tprpaybook-subpaybook.queryAllPayBooksForCountWithSubPayBook",param);
			 
		} catch (ServiceException se) {
			logger.error("查询收款人名册总条数失败   " + this.getClass() + ".queryRecvPersonTotalNumWithSubPayBookMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("查询收款人名册总条数失败    " + this.getClass() + ".queryRecvPersonTotalNumWithSubPayBookMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"查询收款人名册总条数失败", e);
		}
		return totalNum;
	}
	
	/**
	 * @Title: queryDistinctRecvPersonInfoPageWithSubPayBookMethod
	 * @Description:        分页查询该渠道客户的所有收款人名册列表及子表信息(去掉账号重复)
	 * @param cstId   	客户Id
	 * @param payBookId 	收款人唯一id
	 * @param payeeType 	收款人类型
	 * @param accountName	账号名称
	 * @param accountNo		账号
	 * @return recvPersonInfoList 收款人名册列表
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryDistinctRecvPersonInfoPageWithSubPayBookMethod(String cstId,String payBookId,String payeeType,String accountName,String accountNo,int pageNo,int pageSize)throws ServiceException{
		List<Map<String,Object>> recvPersonInfoList = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("cstId",cstId);//法人ID
			param.put("payeeType",payeeType);//收款人类型
			param.put("accountName",accountName);//账号名称
			param.put("accountNo",accountNo);//账号
			param.put("payBookId",payBookId);//收款人唯一id
			
			recvPersonInfoList = myBatisDaoSysDao.selectListPage("mybatis.mapper.relational.table.tprpaybook-subpaybook.queryDistinctPayBooksForPageByCstNoWithSubPayBook",param,pageNo, pageSize);
			 
		} catch (ServiceException se) {
			logger.error("分页查询该渠道客户的所有收款人名册列表失败   " + this.getClass() + ".queryRecvPersonInfoPageWithSubPayBookMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("分页查询该渠道客户的所有收款人名册列表失败    " + this.getClass() + ".queryRecvPersonInfoPageWithSubPayBookMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"分页查询该渠道客户的所有收款人名册列表失败", e);
		}
		return recvPersonInfoList;
	}
	/**
	 * 
	 * @Title: queryDistinctRecvPersonTotalNumWithSubPayBookMethod
	 * @Description:        查询该渠道客户的所有收款人名册列表及子表的总记录数(去掉账号重复)
	 * @param cstId   	客户Id
	 * @param payBookId 	收款人唯一id
	 * @param payeeType 	收款人类型
	 * @param accountName	账号名称
	 * @param accountNo		账号
	 * @return totalNum 	总条数
	 * @throws ServiceException
	 */
	public int queryDistinctRecvPersonTotalNumWithSubPayBookMethod (String cstId,String payBookId,String payeeType,String accountName,String accountNo)throws ServiceException{
		int totalNum = 0;
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("cstId",cstId);//法人ID
			param.put("payeeType",payeeType);
			param.put("accountName",accountName);
			param.put("accountNo",accountNo);
			param.put("payBookId",payBookId);
			
			totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.relational.table.tprpaybook-subpaybook.queryDistinctPayBooksForCountWithSubPayBook",param);
			 
		} catch (ServiceException se) {
			logger.error("查询收款人名册总条数失败   " + this.getClass() + ".queryRecvPersonTotalNumWithSubPayBookMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("查询收款人名册总条数失败    " + this.getClass() + ".queryRecvPersonTotalNumWithSubPayBookMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"查询收款人名册总条数失败", e);
		}
		return totalNum;
	}
}
