package bros.provider.parent.activiti.authmode.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.ValidateUtil;
import bros.provider.parent.activiti.authmode.service.IQryOutSideAuthInfoService;
import bros.provider.parent.activiti.constants.ActivitiErrorCodeConstants;

/** 
 * @ClassName: QryOutSideAuthInfoServiceImpl 
 * @Description: 外部授权用：查询授权配置信息服务接口
 * @author weiyancheng
 * @date 2016年7月27日 下午2:21:02 
 * @version 1.0 
 */
@Component("qryOutSideAuthInfoService")
public class QryOutSideAuthInfoServiceImpl implements IQryOutSideAuthInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(QryOutSideAuthInfoServiceImpl.class);
	/**
	 * 数据库操作服务
	 */
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/** 
	 * 根据渠道+法人+客户号+业务编码查询业务授权配置
	 */
	@Override
	public Map<String, Object> qryAuthInfo(String legalId,
			String cstNo, String bsnCode) throws ServiceException {
		try{
			Map<String, Object> paramIn = new HashMap<String, Object>();
			paramIn.put("legalId", legalId);
			paramIn.put("bsnCode", bsnCode);
			paramIn.put("cstNo", cstNo);
			Map<String, Object> resultMap = myBatisDaoSysDao.selectOne("mybatis.mapper.activiti.outside.table.workflowoutsideauth.queryBsnAuthInfo",paramIn);
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
	 * 根据授权模型ID查询业授权模型
	 */
	@Override
	public Map<String, Object> qryAuthModel(String authModelFk)
			throws ServiceException {
		try{
			Map<String, Object> paramIn = new HashMap<String, Object>();
			paramIn.put("authModelFk", authModelFk);
			Map<String, Object> resultMap = myBatisDaoSysDao.selectOne("mybatis.mapper.activiti.outside.table.workflowoutsideauth.queryTtpAuthModelByByModelId",paramIn);
			return resultMap;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryBsnInfo");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".qryBsnInfo");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0032,"查询企业授权模型信息失败", e);
		}
	}

	/** 
	 * 根据授权模型ID或者+交易金额查询授权规则
	 */
	@Override
	public Map<String, Object> qryAuthRule(String authModelFk, String tranAmt)
			throws ServiceException {
		try{
			Map<String, Object> paramIn = new HashMap<String, Object>();
			paramIn.put("authModelFk", authModelFk);
			if(!ValidateUtil.isEmpty(tranAmt)){
				paramIn.put("tranAmt", tranAmt);
			}
			Map<String, Object> resultMap = myBatisDaoSysDao.selectOne("mybatis.mapper.activiti.outside.table.workflowoutsideauth.queryTtpAuthModelByByModelId",paramIn);
			return resultMap;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryBsnInfo");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".qryBsnInfo");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0033,"查询企业授权规则信息失败", e);
		}
	}

}
