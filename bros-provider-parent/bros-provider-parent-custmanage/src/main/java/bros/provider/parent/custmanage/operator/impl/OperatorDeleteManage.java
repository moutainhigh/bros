package bros.provider.parent.custmanage.operator.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.db.impl.MyBatisDaoSysDaoImpl;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.DateUtil;
import bros.provider.parent.custmanage.constants.CustmanageConstants;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;

/**
 * 
 * @ClassName: OperatorDeleteManage 
 * @Description: 删除操作员具体实现类
 * @author mazhilei 
 * @date 2016年7月6日 下午4:23:07 
 * @version 1.0
 */
@Component("operatorDeleteManage")
public class OperatorDeleteManage {
	private static final  Logger logger = LoggerFactory.getLogger(OperatorDeleteManage.class);
	@Autowired
	private MyBatisDaoSysDaoImpl myBatisDaoSysDao;
	@Autowired
	private OperatorUpdateManage operatorUpdateManage;
	/**
	 * 
	 * @Title: deleteOperatorManageMethod 
	 * @Description: 修改操作员状态。1:冻结；2：解冻；3：注销
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException
	 */
	public void updateOperatorSttManageMethod(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
		String nowTime=DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT_DB);
		String operatorStt=(String)bodyMap.get("operatorStt");
		/**
		 * 检查操作员是否存在
		 */
		operatorUpdateManage.checkOperatorManageIsExist(headMap, bodyMap);
		
		Map<String, Object> param=new HashMap<String,Object>();
		param.put("operatorId", (String)bodyMap.get("operatorId"));
		param.put("operatorLasedate", nowTime);
		if(operatorStt.equals("1")){
			param.put("operatorStt", CustmanageConstants.OPERATORSTT_1);
		}else if(operatorStt.equals("2")){
			param.put("operatorStt", CustmanageConstants.OPERATORSTT_0);
		}else if(operatorStt.equals("3")){
			param.put("operatorStt", CustmanageConstants.OPERATORSTT_2);
		}else{
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0013,"操作员状态标识不正确");		
		}
		/**
		 * 更新操作员状态
		 */
		int updateOperatorFlag = myBatisDaoSysDao.update("updateTtpUserInfChannelByOperatorId", param);
		if(updateOperatorFlag < 0){
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0012,"修改操作员状态信息失败");		//修改操作员信息失败
		}
			
		}catch(ServiceException be){
			logger.error("修改操作员状态信息失败Exception from " + this.getClass().getName() + "'s checkOperatorManageIsRepetition method.", be);
			throw be;
		}catch(Exception e) {
			logger.error("修改操作员状态信息失败 Exception from " + this.getClass().getName() + "'s checkOperatorManageIsRepetition method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0001,"默认错误码",e);
		}
		
		
		
		
		
		
	}

}
