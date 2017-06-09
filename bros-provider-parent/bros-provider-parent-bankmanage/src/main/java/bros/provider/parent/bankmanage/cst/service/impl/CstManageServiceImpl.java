package bros.provider.parent.bankmanage.cst.service.impl;

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
import bros.common.core.util.ValidateUtil;
import bros.provider.parent.bankmanage.constants.AppConstants;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.cst.service.ICstManageService;

/**
 * 
 * @ClassName: CstManageServiceImpl 
 * @Description: 渠道客户信息接口实现
 * @author liwei 
 * @date 2016年9月19日 下午5:44:34 
 * @version 1.0
 */
@Repository(value = "cstManageService")
public class CstManageServiceImpl implements ICstManageService{
	
	private static final Logger logger = LoggerFactory.getLogger(CstManageServiceImpl.class);
	
	
	/**
	 * 渠道客户管理SQL语句命名空间
	 */
    private static final String CST_SQL_NAMESPACE = "mybatis.mapper.single.table.tprcstcstinf.";
    /**
	 * 渠道客户管理SQL语句命名空间(多表查询)
	 */
    private static final String CSTCHANNEL_SQL_NAMESPACE = "mybatis.mapper.relational.table.tprcstcstinftprcstchannelnamespace.";
	
	/**
	 * 数据库Dao
	 */
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/**
	 * 
	 * @Title: queryCstInfoByCstNo 
	 * @Description: 客户信息查询
	 * @param legalId	法人Id
	 * @param certType	证件类型
	 * @param certNo	证件号码
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public Map<String, Object> queryCstInfoByCertNo(String legalId,String certType, String certNo) throws ServiceException {
		Map<String, Object> result = null;
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("legalId", legalId);		//法人Id
			parmIn.put("certType", certType);	//证件类型
			parmIn.put("certNo", certNo);		//证件号码
			result = myBatisDaoSysDao.selectOne("mybatis.mapper.relational.table.tprcstinfo-tprcstchannelinf.queryTprCstInfoByCtfNo",parmIn);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0006,"客户信息查询失败", e);
		}
		return result;
	}
	/**
	 * 
	 * @Title: queryCstInfoByCstNo 
	 * @Description: 客户信息查询
	 * @param legalId	法人Id
	 * @param certType	证件类型
	 * @param certNo	证件号码
	 * @param nameCn	客户姓名
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public Map<String, Object> queryCstInfoByCstNo(String legalId,String certType, String certNo, String nameCn) throws ServiceException {
		Map<String, Object> result = null;
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("legalId", legalId);		//法人Id
			parmIn.put("certType", certType);	//证件类型
			parmIn.put("certNo", certNo);		//证件号码
			parmIn.put("nameCn", nameCn);		//客户姓名
			result = myBatisDaoSysDao.selectOne(CST_SQL_NAMESPACE + "queryCstInfoByCstNo",parmIn);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0006,"客户信息查询失败", e);
		}
		return result;
	}
	
	/**
	 * 
	 * @Title: queryCstInfoChannelByPhoneNo 
	 * @Description: 渠道客户签约信息查询
	 * @param cstId		客户号Id
	 * @param payPhoneNo手机号
	 * @param channel	渠道标识
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public List<Map<String, Object>> queryCstInfoChannelInfo(String cstId, String payPhoneNo, String channel) throws ServiceException {
		List <Map<String, Object>> resultList = null;
		
 		try{
			Map<String, Object> pramIn = new HashMap<String, Object>();
			
			
			if(!ValidateUtil.isEmpty(cstId)){
				pramIn.put("cstId", cstId);					// 客户号
			}
			
			if(!ValidateUtil.isEmpty(payPhoneNo)){
				pramIn.put("payPhoneNo", payPhoneNo);		// 手机号
			}
			
			if(!ValidateUtil.isEmpty(channel)){
				pramIn.put("channel", channel);				// 渠道标识
			}
			
			resultList = myBatisDaoSysDao.selectList(CST_SQL_NAMESPACE + "queryCstInfoChannelInfo", pramIn);
			
		}catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0007,"渠道客户签约信息查询失败", e);
		}
		return resultList;
	}
	
	/**
	 * 
	 * @Title: saveCstInfoChannel 
	 * @Description: 保存渠道客户签约信息
	 * @param cstChannelMap
	 * @throws ServiceException    设定文件
	 */
	@Override
	public void saveCstInfoChannel(Map<String, Object> cstChannelMap) throws ServiceException {
		try{
			myBatisDaoSysDao.insertOne(CST_SQL_NAMESPACE + "insertCstInfChannel", cstChannelMap);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0008,"渠道客户签约失败", e);
		}
	}
	
	/***
	 * 
	 * @Title: checkAlias 
	 * @Description: 验证昵称是否可用
	 * @param cstId		客户Id
	 * @param alias			别名
	 * @param channel		渠道标识
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public String checkAlias(String cstId, String alias, String channel) throws ServiceException {
		String existAlias = "";
		try{
			Map<String, Object> pramIn = new HashMap<String, Object>();
			pramIn.put("cstId", cstId);
			pramIn.put("alias", alias);
			pramIn.put("channel", channel);
			existAlias = AppConstants.SUCCESS;
			
			
			//验证在多渠道别名是否重复
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			result = myBatisDaoSysDao.selectList(CST_SQL_NAMESPACE + "checkCustomerAliasByAlias",pramIn);
			
			if(result != null && result.size() > 0){
				return AppConstants.FAIL;
			}
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0008,"验证昵称失败", e);
		}
		
		return existAlias;
	}
	
	
	/**
	 * 
	 * @Title: checkAccountRegistered 
	 * @Description: 检查手机号是否可用
	 * @param legalId	法人Id
	 * @param accNo		账号
	 * @param channel	渠道标识
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public List<Map<String,Object>> queryCstInfByMachine(String machineCode, String channel) throws ServiceException {
		
		List<Map<String,Object>> mobilelist = null;
		try{
			Map<String, Object> pramIn = new HashMap<String, Object>();
			
			pramIn.put("machineCode", machineCode);
			pramIn.put("channel", channel);
			
			mobilelist = myBatisDaoSysDao.selectList(CSTCHANNEL_SQL_NAMESPACE + "queryCstInfByMachine",pramIn);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0012,"检查手机号是否可用失败", e);
		}
		
		return mobilelist;
	}
	
	/**
	 * 
	 * @Title: queryCstInfByCstNoAndAccNo 
	 * @Description: 通过客户号和账号查询客户以及账户信息，用于转账过程中校验
	 * @param legalId	法人Id
	 * @param cstId	客户Id
	 * @param accNo		账号
	 * @param channel	渠道标识
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public List<Map<String,Object>> queryCstInfByCstNoAndAccNo(String legalId,String cstId,String accNo, String channel) throws ServiceException {
		
		try{
			Map<String, Object> paramIn = new HashMap<String, Object>();
			paramIn.put("cstId", cstId);
			paramIn.put("accNo", accNo);
			paramIn.put("channel", channel);
			paramIn.put("legalId", legalId);
			return myBatisDaoSysDao.selectList(CSTCHANNEL_SQL_NAMESPACE + "queryCstInfByCstNoAndAccNo",paramIn);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0014,"通过客户号和账号查询客户以及账户信息失败", e);
		}
	}
}
