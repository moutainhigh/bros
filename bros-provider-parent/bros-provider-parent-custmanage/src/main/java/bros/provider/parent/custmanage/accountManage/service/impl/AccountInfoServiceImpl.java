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
import bros.provider.parent.custmanage.accountManage.service.IAccountInfoService;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
/**
 * 
 * @ClassName: IAccountInfoService 
 * @Description: 账户信息
 * @author 高永静
 * @date 2016年10月08日 上午9:45:17 
 * @version 1.0
 */
@Repository(value = "accountInfoService")
public class AccountInfoServiceImpl implements IAccountInfoService {
	private static final  Logger logger = LoggerFactory.getLogger(AccountInfoServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao; 
	/**
	 * 
	 * @Title: queryAccInfChannelBySttAccNoMethod
	 * @Description: 根据查询条件查询账号信息
	 * @param cstId	客户id 
	 * @param accNo 账号
	 * @param stt 账号状态
	 * @param accountType 账户类型
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String,Object>> queryAccInfChannelBySttAccNoMethod(
			String cstId, String accNo, String stt,
			String accountType) throws ServiceException {
		List<Map<String,Object>> accInfoList = new ArrayList<Map<String,Object>>();
		try{
			Map<String,Object> param=new HashMap<String,Object>();
//			param.put("legalId", legalId);
			param.put("cstId", cstId);
			param.put("accNo", accNo);
			param.put("stt", stt);
			param.put("accountType", accountType);
			
			accInfoList = myBatisDaoSysDao.selectList("mybatis.mapper.relational.table.tpraccinf-tpraccinfchannel.queryAccInfChannelBySttAccNo",param);
			
		}catch(ServiceException e){
			logger.error("查询账户信息失败 " + this.getClass() + ".queryAccInfChannelBySttAccNoMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("查询账户信息失败 " + this.getClass() + ".queryAccInfChannelBySttAccNoMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"查询账户信息失败", ex);
        }
		return accInfoList;
	}
	
	/**
	 * 
	 * @Title: queryAccInfByAccNoMethod
	 * @Description: 根据账号查询账号信息
	 * @param accNo 账号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String,Object> queryAccInfByAccNoMethod(String accNo) throws ServiceException {
		Map<String,Object> accInfoMap = new HashMap<String,Object>();
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("accNo", accNo);
			
			accInfoMap = myBatisDaoSysDao.selectOne("mybatis.mapper.relational.table.tpraccinf-tpraccinfchannel.queryAccInfByAccNo",param);
			
		}catch(ServiceException e){
			logger.error("查询账户信息失败 " + this.getClass() + ".queryAccInfByAccNoMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("查询账户信息失败 " + this.getClass() + ".queryAccInfByAccNoMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"查询账户信息失败", ex);
        }
		return accInfoMap;
	}
	/**
	 * 
	 * @Title: updateAccInfChannelSttMethod
	 * @Description: 更新账户状态
	 * @param cstId	客户id 
	 * @param accNo 账号
	 * @param stt 账号状态
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void updateAccInfChannelSttMethod(String cstId, String accNo, String stt
			 ) throws ServiceException {
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("cstId", cstId);
			param.put("accNo", accNo);
			param.put("stt", stt);
			 
			myBatisDaoSysDao.update("mybatis.mapper.relational.table.tpraccinf-tpraccinfchannel.updateAccChannelInfStt",param);
			
		}catch(ServiceException e){
			logger.error("更新账户状态失败 " + this.getClass() + ".updateAccInfChannelSttMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("更新账户状态失败 " + this.getClass() + ".updateAccInfChannelSttMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"更新账户状态失败", ex);
        }
	}
	/**
	 * 
	 * @Title: updateAccountAliasMethod
	 * @Description: 更改账户别名
	 * @param cstId 客户标识ID
	 * @param accNo 账号
	 * @param accAlias 账号别名
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> updateAccountAliasMethod(String cstId, String accNo, String accAlias)
			throws ServiceException {
		
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("cstId", cstId);
			param.put("accNo", accNo);
			param.put("accountAlias", accAlias);
			 
			myBatisDaoSysDao.update("mybatis.mapper.single.table.tpraccinfo.updateAccAliasByCstIdAndAccNo",param);
			
		}catch(ServiceException e){
			logger.error("更改账户别名失败 " + this.getClass() + ".updateAccountAliasMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("更改账户别名失败 " + this.getClass() + ".updateAccountAliasMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"更改账户别名失败", ex);
        }
		
		return null;
	}
	/**
	 * 
	 * @Title: deleteTprAccChannelInfoByCstIdAndAccNoMethod
	 * @Description: 根据客户标识及账号删除表tpr_acc_channel_info
	 * @param cstId 客户标识ID
	 * @param accNo 账号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void deleteTprAccChannelInfoByCstIdAndAccNoMethod(String cstId, String accNo) throws ServiceException {
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("cstId", cstId);
			param.put("accNo", accNo);
			 
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.tpraccchannelinfo.deleteTprAccChannelInfoByCstIdAndAccNo",param);
			
		}catch(ServiceException e){
			logger.error("删除账号信息表tpr_acc_channel_info失败 " + this.getClass() + ".deleteTprAccChannelInfoByCstIdAndAccNoMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("删除账号信息表tpr_acc_channel_info失败 " + this.getClass() + ".deleteTprAccChannelInfoByCstIdAndAccNoMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"删除账号失败", ex);
        }
		
	}
	/**
	 * 
	 * @Title: deleteTprAccInfoByCstIdAndAccNoMethod
	 * @Description: 根据客户标识And账号删除tpr_acc_info
	 * @param cstId 客户标识ID
	 * @param accNo 账号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void deleteTprAccInfoByCstIdAndAccNoMethod(String cstId, String accNo) throws ServiceException {
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("cstId", cstId);
			param.put("accNo", accNo);
			 
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.tpraccinfo.deleteTprAccInfoByCstIdAndAccNo",param);
			
		}catch(ServiceException e){
			logger.error("删除账号表 tpr_acc_info失败 " + this.getClass() + ".deleteTprAccInfoByCstIdAndAccNoMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("删除账号表 tpr_acc_info 失败 " + this.getClass() + ".deleteTprAccInfoByCstIdAndAccNoMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"删除账号表 TPR_ACC_INFO失败", ex);
        }
		
	}
	/**
	 * 
	 * @Title: deleteTprAccFuncByCstIdAndAccNoMethod
	 * @Description: 根据客户标识And账号删除TPR_ACC_FUNC
	 * @param cstId 客户标识ID
	 * @param accNo 账号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void deleteTprAccFuncByCstIdAndAccNoMethod(String cstId, String accNo) throws ServiceException {
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("cstId", cstId);
			param.put("accNo", accNo);
			
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.tpraccfunc.deleteTprAccFuncByCstIdAndAccNo",param);
			
		}catch(ServiceException e){
			logger.error("删除账号表 TPR_ACC_FUNC失败 " + this.getClass() + ".deleteTprAccFuncByCstIdAndAccNoMethod");
			throw e;
		} catch(Exception ex){
			logger.error("删除账号表 TPR_ACC_FUNC 失败 " + this.getClass() + ".deleteTprAccFuncByCstIdAndAccNoMethod");
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"删除账号表 TPR_ACC_INFO失败", ex);
		}
	}
	/**
	 * 
	 * @Title: queryBranchByAccNoMethod
	 * @Description: 根据账号查询开户行行名
	 * @param accNo 账号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String,Object> queryBranchByAccNoMethod(String accNo) throws ServiceException {
		Map<String,Object> subBranchMap = new HashMap<String,Object>();
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("accNo", accNo);
			subBranchMap = myBatisDaoSysDao.selectOne("mybatis.mapper.relational.table.tpraccinf-tpraccinfchannel.queryBranchByAccNo",param);
		}catch(ServiceException e){
			logger.error("根据账号查询开户行行名失败 " + this.getClass() + ".queryBranchByAccNoMethod");
			throw e;
		} catch(Exception ex){
			logger.error("根据账号查询开户行行名失败 " + this.getClass() + ".queryBranchByAccNoMethod");
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"根据账号查询开户行行名失败", ex);
		}
		return subBranchMap;
	}
}
