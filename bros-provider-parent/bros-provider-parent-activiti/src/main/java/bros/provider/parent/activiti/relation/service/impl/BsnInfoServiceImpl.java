package bros.provider.parent.activiti.relation.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.activiti.constants.ActivitiErrorCodeConstants;
import bros.provider.parent.activiti.relation.service.IBsnInfoService;

/** 
 * @ClassName: BsnInfoServiceImpl 
 * @Description: 业务信息服务实现
 * @author weiyancheng
 * @date 2016年8月3日 上午9:25:17 
 * @version 1.0 
 */
@Component("bsnInfoService")
public class BsnInfoServiceImpl implements IBsnInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(BsnInfoServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	
	/** 
	 * 根据渠道+业务编码查询业务配置信息
	 */
	@Override
	public Map<String, Object> qryBsnInfo(String channel, String bsnCode)
			throws ServiceException {
		try{
			Map<String, Object> paramIn = new HashMap<String, Object>();
			paramIn.put("channel", channel);
			paramIn.put("bsnCode", bsnCode);
			Map<String, Object> resultMap = myBatisDaoSysDao.selectOne("mybatis.mapper.activiti.inside.table.workflowbsndef.queryBsnInfo",paramIn);
			return resultMap;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryBsnInfo");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".qryBsnInfo");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0031,"查询企业功能信息失败", e);
		}
	}

}
