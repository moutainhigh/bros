package bros.provider.parent.custmanage.transferManage.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IApproveService 
 * @Description: 大额落地审批
 * @author 高永静
 * @date 2016年10月31日 上午9:45:17 
 * @version 1.0
 */
public interface IApproveService {
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
	public List<Map<String,Object>> queryApproveByStatMethod(String payBranchNo, String approveStat , String cstNo ,String tellerNo ,String cstType,String channel ,String legalId ,String payAccNo ,String resiveAccNo )throws ServiceException;
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
	public List<Map<String,Object>> queryApproveByStatMethod(String payBranchNo, String approveStat , String cstNo ,String tellerNo ,String cstType,String channel ,String legalId ,String payAccNo ,String resiveAccNo,int pageNo,int pageSize )throws ServiceException;
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
	public int queryApproveByStatTotalNumMethod(String payBranchNo, String approveStat , String cstNo ,String tellerNo ,String cstType,String channel ,String legalId ,String payAccNo ,String resiveAccNo )throws ServiceException;
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
	public void updateApproveInfoMethod(String objectId,String approveStat,String approveOpinion ,String approveTime ,String postscript)throws ServiceException;
	/**
	 * 
	 * @Title: updateApproveInfoMethod
	 * @Description: 根据唯一id查询落地审批信息
	 * @param objectId   审批记录唯一id
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryApproveByObjectIdMethod(String objectId)throws ServiceException;
}
