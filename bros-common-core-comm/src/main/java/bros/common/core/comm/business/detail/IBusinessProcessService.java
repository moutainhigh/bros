package bros.common.core.comm.business.detail;

import java.util.Map;

import org.springframework.context.ApplicationContext;

import bros.common.core.comm.netty.config.AppConfig;
import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: IBusinessProcessService 
 * @Description: 业务处理实现接口
 * @author 何鹏
 * @date 2016年7月2日 上午10:12:17 
 * @version 1.0
 */
public interface IBusinessProcessService {
	/**
	 * 
	 * @Title: businessDetail 
	 * @Description: 业务处理器
	 * @param context
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> businessDetail(final ApplicationContext context,Map<String,Object> params,AppConfig config) throws ServiceException;
}
