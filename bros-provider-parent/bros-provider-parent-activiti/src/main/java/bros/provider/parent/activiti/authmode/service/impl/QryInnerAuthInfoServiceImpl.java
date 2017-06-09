package bros.provider.parent.activiti.authmode.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.activiti.authmode.service.IQryInnerAuthInfoService;
import bros.provider.parent.activiti.constants.ActivitiErrorCodeConstants;

/** 
 * @ClassName: QryInnerAuthInfoServiceImpl 
 * @Description: 查询内部授权业务授权配置信息
 * @author weiyancheng
 * @date 2016年7月11日 下午5:29:32 
 * @version 1.0 
 */
@Component("qryInnerAuthInfoService")
public class QryInnerAuthInfoServiceImpl implements IQryInnerAuthInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(QryInnerAuthInfoServiceImpl.class);
	/**
	 * 数据库操作服务
	 */
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	
	/** 
	 * 查询内部授权业务授权配置信息
	 */
	@Override
	public Map<String, Object> qryAuthInfo(String legalId,String channel,String bsnCode)
			throws ServiceException {
		try{
			Map<String, Object> paramIn = new HashMap<String, Object>();
			paramIn.put("legalId", legalId);
			paramIn.put("channel", channel);
			paramIn.put("bsnCode", bsnCode);
			Map<String, Object> resultMap = myBatisDaoSysDao.selectOne("mybatis.mapper.activiti.inside.table.workflowinnerauth.queryBsnAuthInfo",paramIn);
			return resultMap;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryAuthInfo");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".qryAuthInfo");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0006,"查询业务授权配置信息失败", e);
		}
	}

}
