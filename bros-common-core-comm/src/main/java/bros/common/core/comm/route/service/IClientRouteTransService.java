package bros.common.core.comm.route.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: IClientRouteTransService 
 * @Description: 客户端服务路由调用
 * @author 何鹏
 * @date 2016年7月11日 上午11:03:08 
 * @version 1.0
 */
public interface IClientRouteTransService {
	/**
	 * 
	 * @Title: route 
	 * @Description: 路由调用
	 * @param sendMap 发送消息
	 * @param commType	通信类型（暂时只有一种）     1:TCPIP通信        
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> route(Map<String, Object> reqHeadMap,Map<String,Object> reqBodyMap) throws ServiceException;
}
