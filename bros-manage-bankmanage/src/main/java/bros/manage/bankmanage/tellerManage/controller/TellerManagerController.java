package bros.manage.bankmanage.tellerManage.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bros.common.core.annotation.MethodAttribute;
import bros.common.core.annotation.NeedInSessionType;
import bros.common.core.comm.route.service.IClientRouteTransService;
import bros.common.core.exception.ServiceException;
import bros.pre.common.web.controller.CommonSessionController;

/**
 * 
 * @ClassName: TellerManagerController
 * @Description: 柜员控制器
 * @author 马志磊
 * @date 2016年5月12日 上午9:29:16
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/bankmanage")
public class TellerManagerController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(TellerManagerController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	

	@RequestMapping(value = "/tellerManage" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> TellerManage(@RequestBody Map<String, Object> loginMap) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			logger.info("前置与后台通讯结束");
		}catch(ServiceException ex){
			logger.error("");
			this.handleError(ex,resultMap);
		}catch(Exception e){
			logger.error("");
			this.handleError(e,resultMap);
		}
		
		return resultMap;
	}
	
	
}
