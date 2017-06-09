package bros.provider.parent.custmanage.transferManage.service.impl;

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
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
import bros.provider.parent.custmanage.transferManage.service.IApproveService;
/**
 * 
 * @ClassName: ApproveServiceImpl 
 * @Description: 大额落地审批
 * @author 高永静
 * @date 2016年10月31日 上午9:45:17 
 * @version 1.0
 */
@Repository(value = "approveService")
public class ApproveServiceImpl implements IApproveService {
	
	private static final  Logger logger = LoggerFactory.getLogger(ApproveServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao; 
	/**
	 * 
	 * @Title: queryApproveByStatMethod
	 * @Description: 条件查询落地审批信息
	 * @param payBranchNo 付款行行号
	 * @param approveStat 审批状态 
	 * @param cstNo 客户号
	 * @param tellerNo 操作员
	 * @param cstType 客户类型
	 * @param channel 渠道号
	 * @param legalId 法人id
	 * @param payAccNo 付款人账号
	 * @param resiveAccNo 收款人账号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String, Object>> queryApproveByStatMethod(
			String payBranchNo, String approveStat, String cstNo,
			String tellerNo, String cstType, String channel, String legalId,
			String payAccNo, String resiveAccNo) throws ServiceException {

		List<Map<String,Object>> approveInfoList = new ArrayList<Map<String,Object>>();
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("payBranchNo", payBranchNo);
			param.put("approveStat", approveStat);
			param.put("cstNo", cstNo);
			param.put("tellerNo", tellerNo);
			param.put("cstType", cstType);
			param.put("channel", channel);
			param.put("legalId", legalId);
			param.put("payAccNo", payAccNo);
			param.put("resiveAccNo", resiveAccNo);
			approveInfoList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.tprapprove.queryApproveByStat",param);
			
		}catch(ServiceException e){
			logger.error("查询落地审批信息失败 " + this.getClass() + ".queryApproveByStatMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("查询落地审批信息失败 " + this.getClass() + ".queryApproveByStatMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"查询落地审批信息失败", ex);
        }
		return approveInfoList;
	}
	/**
	 * 
	 * @Title: queryApproveByStatMethod
	 * @Description: 条件查询落地审批信息分页
	 * @param payBranchNo 付款行行号
	 * @param approveStat 审批状态 
	 * @param cstNo 客户号
	 * @param tellerNo 操作员
	 * @param cstType 客户类型
	 * @param channel 渠道号
	 * @param legalId 法人id
	 * @param payAccNo 付款人账号
	 * @param resiveAccNo 收款人账号
	 * @param pageNo 页码
	 * @param pageSize 每页条数
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String, Object>> queryApproveByStatMethod(
			String payBranchNo, String approveStat, String cstNo,
			String tellerNo, String cstType, String channel, String legalId,
			String payAccNo, String resiveAccNo, int pageNo, int pageSize)
			throws ServiceException {

		List<Map<String,Object>> approveInfoList = new ArrayList<Map<String,Object>>();
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("payBranchNo", payBranchNo);
			param.put("approveStat", approveStat);
			param.put("cstNo", cstNo);
			param.put("tellerNo", tellerNo);
			param.put("cstType", cstType);
			param.put("channel", channel);
			param.put("legalId", legalId);
			param.put("payAccNo", payAccNo);
			param.put("resiveAccNo", resiveAccNo);
			approveInfoList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.tprapprove.queryApproveByStat",param,pageNo,pageSize);
			
		}catch(ServiceException e){
			logger.error("查询落地审批信息失败 " + this.getClass() + ".queryApproveByStatMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("查询落地审批信息失败 " + this.getClass() + ".queryApproveByStatMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"查询落地审批信息失败", ex);
        }
		return approveInfoList;
	}
	/**
	 * 
	 * @Title: queryApproveByStatTotalNumMethod
	 * @Description: 条件查询落地审批信息总条数
	 * @param payBranchNo 付款行行号
	 * @param approveStat 审批状态 
	 * @param cstNo 客户号
	 * @param tellerNo 操作员
	 * @param cstType 客户类型
	 * @param channel 渠道号
	 * @param legalId 法人id
	 * @param payAccNo 付款人账号
	 * @param resiveAccNo 收款人账号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public int queryApproveByStatTotalNumMethod(String payBranchNo,
			String approveStat, String cstNo, String tellerNo, String cstType,
			String channel, String legalId, String payAccNo, String resiveAccNo)
			throws ServiceException {

		int totalNum = 0;
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("payBranchNo", payBranchNo);
			param.put("approveStat", approveStat);
			param.put("cstNo", cstNo);
			param.put("tellerNo", tellerNo);
			param.put("cstType", cstType);
			param.put("channel", channel);
			param.put("legalId", legalId);
			param.put("payAccNo", payAccNo);
			param.put("resiveAccNo", resiveAccNo);
			totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.tprapprove.queryApproveByStatTotalNum",param);
			
		}catch(ServiceException e){
			logger.error("查询落地审批信息总条数失败 " + this.getClass() + ".queryApproveByStatTotalNumMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("查询落地审批信息总条数失败 " + this.getClass() + ".queryApproveByStatTotalNumMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"查询落地审批信息总条数失败", ex);
        }
		return totalNum;
	}
	/**
	 * 
	 * @Title: updateApproveInfoMethod
	 * @Description: 更新落地审批状态
	 * @param objectId   审批记录唯一id
	 * @param approveStat 审批状态 
	 * @param approveOpinion 审批意见
	 * @param approveTime 审批时间
	 * @param postscript 审批附言
	 * @return
	 * @throws ServiceException
	 */
	public void updateApproveInfoMethod(String objectId,String approveStat,String approveOpinion ,String approveTime ,String postscript)throws ServiceException{
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("objectId", objectId);
			param.put("approveStat", approveStat);
			param.put("approveOpinion", approveOpinion);
			param.put("approveTime", approveTime);
			param.put("postscript", postscript);
			int i = myBatisDaoSysDao.update("mybatis.mapper.single.table.tprapprove.updateApproveInfo",param);
			if(i <= 0){
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"更新落地审批状态失败");
			}
		}catch(ServiceException e){
			logger.error("更新落地审批状态失败 " + this.getClass() + ".updateApproveInfoMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("更新落地审批状态失败 " + this.getClass() + ".updateApproveInfoMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"更新落地审批状态失败", ex);
        }
	}
	/**
	 * 
	 * @Title: updateApproveInfoMethod
	 * @Description: 根据唯一id查询落地审批信息
	 * @param objectId   审批记录唯一id
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryApproveByObjectIdMethod(String objectId)throws ServiceException{
		Map<String,Object> approveInfo = new HashMap<String,Object>();
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("objectId", objectId);
			approveInfo = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprapprove.queryApproveByObjectId",param);
			 
		}catch(ServiceException e){
			logger.error("根据唯一id查询落地审批信息失败 " + this.getClass() + ".queryApproveByObjectIdMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("根据唯一id查询落地审批信息失败 " + this.getClass() + ".queryApproveByObjectIdMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"根据唯一id查询落地审批信息失败", ex);
        }
		return approveInfo;
	}
}
