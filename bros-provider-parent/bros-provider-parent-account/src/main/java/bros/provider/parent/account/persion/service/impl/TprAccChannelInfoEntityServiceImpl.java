package bros.provider.parent.account.persion.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.account.constants.AccountErrorCodeConstants;
import bros.provider.parent.account.persion.service.ITprAccChannelInfoEntityService;

/**
 * 
 * @ClassName: TprAccChannelInfoEntityServiceImpl 
 * @Description: 个人账户渠道信息实体服务
 * @author huangcanhui 
 * @date 2016年10月8日 下午9:59:13 
 * @version 1.0
 */
@Repository(value = "tprAccChannelInfoEntityService")
public class TprAccChannelInfoEntityServiceImpl implements ITprAccChannelInfoEntityService {
	
	private static final Logger logger = LoggerFactory.getLogger(TprAccChannelInfoEntityServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	
	/**
	 * 根据法人ID+渠道编号+账号查询个人账户渠道信息
	 */
	@Override
	public Map<String, Object> queryTprAccChannelInfoByAccNo(String legalId, String channel, String accNo) throws ServiceException {
		try{
			Map<String, Object> paramIN = new HashMap<String, Object>();
	        paramIN.put("legalId", legalId);
	        paramIN.put("channel", channel);
			paramIN.put("accNo", accNo);
			
			return myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tpraccchannelinfo.queryTprAccChannelInfoByAccNo", paramIN);
		}catch(ServiceException se){
			logger.error("查询个人账户渠道信息异常", se);
			throw se;
		}catch(Exception e){
			logger.error("查询个人账户渠道信息失败 ", e);
			throw new ServiceException(AccountErrorCodeConstants.PPAT0002, "查询个人账户渠道信息失败", e);
		}
	}
	
}
