
package bros.provider.parent.activiti.authmode.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.activiti.authmode.service.IQryInnerAuthModelService;
import bros.provider.parent.activiti.constants.ActivitiErrorCodeConstants;

/** 
 * @ClassName: QryInnerAuthModelServiceImpl 
 * @Description: 内部授权模型、授权规则查询服务
 * @author weiyancheng
 * @date 2016年7月11日 下午4:39:34 
 * @version 1.0 
 */
@Component("qryInnerAuthModelService")
public class QryInnerAuthModelServiceImpl implements IQryInnerAuthModelService {
	
	private static final Logger logger = LoggerFactory.getLogger(QryInnerAuthModelServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	/** 
	 * @Title: qryAuthModel 
	 * @Description: 查询内部授权模型
	 * @param param
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> qryAuthModel(String authModelFk)
			throws ServiceException {
		try{
			Map<String, Object> paramIn = new HashMap<String, Object>();
			paramIn.put("authModelFk", authModelFk);//授权模型编号
			Map<String, Object> resultMap = myBatisDaoSysDao.selectOne("mybatis.mapper.activiti.inside.table.workflowinnerauth.queryBmaAuthModelByByModelId",paramIn);
			return resultMap;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryAuthInfo");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".qryAuthInfo");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0006,"查询业务授权配置信息失败", e);
		}
	}
	/** 
	 * @Title: qryAuthRule 
	 * @Description: 根据授权模型id或者金额查询授权规则
	 * @param authModelFk
	 * @param tranAmt
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> qryAuthRule(String authModelFk, String tranAmt)
			throws ServiceException {
		try{
			Map<String, Object> paramIn = new HashMap<String, Object>();
			paramIn.put("authModelFk", authModelFk);
			paramIn.put("tranAmt", tranAmt);
			Map<String, Object> resultMap = myBatisDaoSysDao.selectOne("mybatis.mapper.activiti.inside.table.workflowinnerauth.queryAuthModelRuleByAuthModelIdAndAmount",paramIn);
			return resultMap;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryAuthRule");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".qryAuthRule");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0009,"查询授权规则失败", e);
		}
	}

}
