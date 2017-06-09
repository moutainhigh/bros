package bros.provider.parent.activiti.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.exception.ServiceException;
import bros.common.core.flow.db.service.IBsnFlowCfgDaoService;
import bros.common.core.flow.jdbc.updater.service.IOrderUpdaterService;
import bros.common.core.flow.service.IFlowProcessorService;
import bros.provider.parent.activiti.constants.ActivitiErrorCodeConstants;
import bros.provider.parent.activiti.service.IAuthBusinessFlowService;

/** 
 * @ClassName: AuthBusinessFlowServiceImpl 
 * @Description: 授权业务指令流水服务实现
 * @author weiyancheng
 * @date 2016年7月29日 下午2:00:36 
 * @version 1.0 
 */
@Component("authBusinessFlowService")
public class AuthBusinessFlowServiceImpl implements IAuthBusinessFlowService {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthBusinessFlowServiceImpl.class);

	@Autowired
	private IFlowProcessorService flowProcessorService;
	@Autowired
	private IBsnFlowCfgDaoService bsnFlowCfgDaoService;
	
	/** 
	 * 根据指令流水号更新流水批次号
	 */
	@Override
	public void updateFlowBatchNoAndStt(String bsnCode,String flowSeq, String batchNo, String stt,String channel)
			throws ServiceException {
		try{
			Map<String, Object> bsnMap = bsnFlowCfgDaoService.queryBsnFlowCfg(bsnCode,channel);
			IOrderUpdaterService service = flowProcessorService.createProcessor((String)bsnMap.get("updaterBeanId"), (String)bsnMap.get("providerBeanId"));
			service.updateFLowBathNoAndOrderState(flowSeq, batchNo, stt);
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass() + ".updateFlowBatchNo");
			throw se;
		}catch(Exception e){
			logger.error("Exception from " + this.getClass() + ".updateFlowBatchNo");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0043,"更新指令批次号和状态失败");
		}

	}

}
