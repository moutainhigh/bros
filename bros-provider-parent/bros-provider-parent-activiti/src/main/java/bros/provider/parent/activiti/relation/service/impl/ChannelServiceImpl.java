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
import bros.provider.parent.activiti.relation.service.IChannelService;

/** 
 * @ClassName: ChannelServiceImpl 
 * @Description: 查询渠道信息服务实现
 * @author weiyancheng
 * @date 2016年7月22日 上午11:37:52 
 * @version 1.0 
 */
@Component("channelService")
public class ChannelServiceImpl implements IChannelService {
	
	private static final Logger logger = LoggerFactory.getLogger(ChannelServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/** 
	 * @Title: qryChannelFlag 
	 * @Description: 根据渠道编号查询渠道标记
	 * @param channel 渠道编号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> qryChannelFlag(String channel) throws ServiceException {
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("channel", channel);
			Map<String, Object> result = myBatisDaoSysDao
					.selectOne("mybatis.mapper.activiti.inside.table.workflowtradeparam.queryChannelFlag",param);
			return result;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryChannelFlag");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".qryChannelFlag");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0000,"查询渠道标记失败", e);
		}
	}

}
