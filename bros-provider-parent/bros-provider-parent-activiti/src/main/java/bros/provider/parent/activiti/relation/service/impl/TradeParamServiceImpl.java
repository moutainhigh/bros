package bros.provider.parent.activiti.relation.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.activiti.constants.ActivitiErrorCodeConstants;
import bros.provider.parent.activiti.relation.service.ITradeParamService;

/** 
 * @ClassName: TradeParamServiceImpl 
 * @Description: 工作流回调服务所需数据处理服务实现
 * @author weiyancheng
 * @date 2016年7月18日 下午1:07:39 
 * @version 1.0 
 */
@Component("tradeParamService")
public class TradeParamServiceImpl implements ITradeParamService {
	
	private static final Logger logger = LoggerFactory.getLogger(TradeParamServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/** 
	 * @Title: qryTradeParam 
	 * @Description: 查询回调服务所需数据
	 * @param businessKey
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> qryTradeParam(String businessKey)
			throws ServiceException {
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("businessKey", businessKey);
			Map<String, Object> resultMap = myBatisDaoSysDao
					.selectOne("mybatis.mapper.activiti.inside.table.workflowtradeparam.queryActBizData",param);
			return resultMap;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryTradeParam");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".qryTradeParam");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0000,"查询工作流回调服务数据信息失败", e);
		}
	}

	/** 
	 * @Title: deleteTradeParam 
	 * @Description: 删除回调服务所需数据
	 * @param businessKey
	 * @throws ServiceException
	 */
	@Override
	public void deleteTradeParam(String businessKey) throws ServiceException {
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("businessKey", businessKey);
			myBatisDaoSysDao.delete("mybatis.mapper.activiti.inside.table.workflowtradeparam.deleteActBizData",param);
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".deleteTradeParam");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".deleteTradeParam");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0000,"删除工作流回调服务数据信息失败", e);
		}

	}

	/** 
	 * @Title: insertTradeParam 
	 * @Description: 插入回调服务所需数据
	 * @param param
	 * @throws ServiceException
	 */
	@Override
	public void insertTradeParam(Map<String, Object> param)
			throws ServiceException {
		try{
			int num = myBatisDaoSysDao
					.insertOne("mybatis.mapper.activiti.inside.table.workflowtradeparam.insertActBizData",param);
			if(num!=1){
				throw new ServiceException(ActivitiErrorCodeConstants.PPAI0013,"插入流程回调服务数据失败");
			}
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".insertTradeParam");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".insertTradeParam");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0000,"插入工作流回调服务数据信息失败", e);
		}

	}

	/** 
	 * @Title: insertCallBackServiceData 
	 * @Description: 这里用一句话描述这个方法的作用
	 * @param invocation
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insertCallBackServiceData(MethodInvocation invocation)
			throws ServiceException {
		try{
			//获取要执行Service的bean ID
			Component cp = invocation.getThis().getClass().getAnnotation(Component.class);
			if(null == cp){
				throw new ServiceException(ActivitiErrorCodeConstants.PPAI0014,"后台服务未使用@Component注解");
			}
			String beanId = cp.value();
			//获取要执行Service的方法名
			String methodName = invocation.getMethod().getName();
			//获取要执行Service的方法名的参数
			Class<?>[] parameterTypes = invocation.getMethod().getParameterTypes();
			JSONArray parameterTypesJson = JSONArray.fromObject(parameterTypes);
			String parameterTypesStr = parameterTypesJson.toString();
			//获取要执行Service的方法的参数对的应值
			Object[] parameterValues = invocation.getArguments();
			JSONArray parameterValuesJson = JSONArray.fromObject(parameterValues);
			String parameterValuesStr = parameterValuesJson.toString();
			//获取报文头数据域
			//获取报文头数据域
			Map<String, Object> headMap = (Map<String, Object>) parameterValues[0];
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("businessKey", headMap.get(HeadParameterDefinitionConstants.REC_GLOBALSEQNO));
			param.put("bizData", parameterValuesStr);
			param.put("beanId", beanId);
			param.put("method", methodName);
			param.put("paramTypes", parameterTypesStr);
			this.insertTradeParam(param);
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".insertCallBackServiceData");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".insertCallBackServiceData");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0000,"插入工作流回调服务数据信息失败", e);
		}
		
	}

}
