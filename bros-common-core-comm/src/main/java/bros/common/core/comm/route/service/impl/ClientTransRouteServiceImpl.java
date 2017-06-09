package bros.common.core.comm.route.service.impl;

import java.util.HashMap;
import java.util.Map;

import bros.common.core.comm.constants.CommErrorCodeConstants;
import bros.common.core.comm.route.service.IClientRouteTransService;
import bros.common.core.comm.route.service.ICommunicationService;
import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: ClientTransRouteServiceImpl 
 * @Description: 客户端服务路由实现
 * @author 何鹏
 * @date 2016年7月11日 上午11:03:08 
 * @version 1.0
 */
public class ClientTransRouteServiceImpl implements IClientRouteTransService {
	/**
	 * 通信方式
	 */
	private String commType;
	/**
	 * 通信组件
	 */
	private ICommunicationService communicationService;
	
	public final String getCommType() {
		return commType;
	}
	public final void setCommType(String commType) {
		this.commType = commType;
	}
	public ICommunicationService getCommunicationService() {
		return communicationService;
	}
	public void setCommunicationService(
			ICommunicationService communicationService) {
		this.communicationService = communicationService;
	}


	/**
	 * 
	 * @Title: route 
	 * @Description: 路由调用
	 * @param sendMap 发送消息
	 * @param commType	通信类型（暂时只有一种）     1:TCPIP通信        
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> route(Map<String, Object> reqHeadMap,Map<String,Object> reqBodyMap) throws ServiceException {
		Map<String,Object> sendMap = new HashMap<String, Object>();
		//Map<String,Object> returnMap = new HashMap<String,Object>();
		try{
				if("1".equals(commType)){//TCPIP通信方式
					sendMap.put("reqHead", reqHeadMap);
					sendMap.put("reqBody", reqBodyMap);
					Map<String,Object> result = communicationService.client(sendMap);
					/*
					if(result == null || result.size() != 2){
						throw new ServiceException(CommErrorCodeConstants.CCCM0009,"返回报文格式不合法");
					}
					try{
						Map<String,Object> rspHeadMap = (Map<String, Object>) result.get("rspHead");
						returnMap.putAll(rspHeadMap);
					}catch(Exception e){
						throw new ServiceException(CommErrorCodeConstants.CCCM0010,"返回报文头转换失败",e);
					}
					try{
						Map<String,Object> rspBodyMap = (Map<String, Object>) result.get("rspBody");
						returnMap.putAll(rspBodyMap);
					}catch(Exception e){
						throw new ServiceException(CommErrorCodeConstants.CCCM0011,"返回报文体转换失败",e);
					}
					return returnMap;
					*/
					return result;
				}else{
					throw new ServiceException(CommErrorCodeConstants.CCCM0008,"未知的通信方式");
				}
		}catch(ServiceException se){
			throw se;
		}catch(Exception ex){
			throw new ServiceException(CommErrorCodeConstants.CCCM0012,"通信异常",ex);
		}
		
	}

}
