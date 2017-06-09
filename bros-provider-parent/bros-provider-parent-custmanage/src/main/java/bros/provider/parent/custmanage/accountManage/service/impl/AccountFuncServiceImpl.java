package bros.provider.parent.custmanage.accountManage.service.impl;

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
import bros.provider.parent.custmanage.accountManage.service.IAccountFuncService;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
/**
 * 
 * @ClassName: AccountFuncServiceImpl 
 * @Description: 账户转账权限功能维护
 * @author 高永静
 * @date 2016年10月08日 上午9:45:17 
 * @version 1.0
 */
@Repository(value = "accountFuncService")
public class AccountFuncServiceImpl implements IAccountFuncService {
	private static final  Logger logger = LoggerFactory.getLogger(AccountFuncServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao; 
	/**
	 * 
	 * @Title: queryAccFuncInfoNoChannelMethod
	 * @Description: 根据客户号查询账户功能信息
	 * @param cstId	客户id 
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String,Object>> queryAccFuncInfoNoChannelMethod(String cstId)
			throws ServiceException {
		List<Map<String,Object>> accFuncList = new ArrayList<Map<String,Object>>();
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("cstId", cstId);
		
			accFuncList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.tpraccfunc.queryAccFuncInfoNoChannel",param);
			
		}catch(ServiceException e){
			logger.error("根据客户号查询账户功能信息失败 " + this.getClass() + ".queryAccFuncInfoNoChannelMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("根据客户号查询账户功能信息失败 " + this.getClass() + ".queryAccFuncInfoNoChannelMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"根据客户号查询账户功能信息失败", ex);
        }
		return accFuncList;
	}
	/**
	 * 
	 * @Title: checkAccRightsByBsnCodeMethod
	 * @Description: 检查账户操作权限-根据svrCode
	 * @param legalId 法人id
	 * @param cstNo	客户号
	 * @param bsnCode 业务编码
	 * @param accNo 账号
	 * @param channel 渠道号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public boolean checkAccRightsByBsnCodeMethod(String legalId, String cstNo,String bsnCode,String accNo,String channel)
			throws ServiceException {
		boolean isRights = false;
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("legalId", legalId);
			param.put("cstNo", cstNo);
			param.put("bsnCode", bsnCode);
			param.put("accNo", accNo);
			param.put("channel", channel);
			
			int num = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.relational.table.tpraccfunc-tprfunclassbsnrel.queryAccFunclassCount",param);
			if(num > 0){
				isRights = true;
			}
		}catch(ServiceException e){
			logger.error("检查账户操作权限-根据svrCode失败" + this.getClass() + ".checkAccRightsByBsnCodeMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("检查账户操作权限-根据svrCode失败" + this.getClass() + ".checkAccRightsByBsnCodeMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"检查账户操作权限-根据svrCode失败", ex);
        }
		return isRights;
	}

	/**
	 * 
	 * @Title: queryFuncClassBsnRelInfoByBsnCodeMethod
	 * @Description: 根据业务代码查询个人网银账户功能业务分类与业务关系
	 * @param legalId 法人id
	 * @param bsnCode 业务编码
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String,Object> queryFuncClassBsnRelInfoByBsnCodeMethod(String legalId,String bsnCode)
			throws ServiceException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("legalId", legalId);
			param.put("bsnCode", bsnCode);
			
			resultMap = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprfunclassbsnrel.queryFuncClassBsnRelInfoByBsnCode",param);
			 
		}catch(ServiceException e){
			logger.error("根据业务代码查询个人网银账户功能业务分类与业务关系失败   " + this.getClass() + ".queryFuncClassBsnRelInfoByBsnCodeMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("根据业务代码查询个人网银账户功能业务分类与业务关系失败   " + this.getClass() + ".queryFuncClassBsnRelInfoByBsnCodeMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"根据业务代码查询个人网银账户功能业务分类与业务关系失败", ex);
        }
		return resultMap;
	}
}
