package bros.p.bankmanage.facade.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPBannerinfoServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.bannerinfo.service.IBannerinfoService;

/**
 * @ClassName:IPBannerinfoServiceFacade
 * @Description:广告栏服务对外接口实现类
 * @author wuchenglong
 * @date 2016年9月6日 上午14:50:28
 * @version V1.0
 */
@Component("pbannerinfoServiceFacade")
public class PBannerinfoServiceFacadeImpl implements IPBannerinfoServiceFacade {

	/**
	 * 广告栏实现类
	 */
	@Autowired
	private IBannerinfoService bannerinfoService;

	/**
	 * 
	 * @Title: addBannerinfo
	 * @Description: 增加广告栏
	 * @param headMap
	 *            头信息
	 * @param bodyMap
	 *            上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000400")
	public ResponseEntity addBannerinfo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = bannerinfoService.queryBannerinfo(headMap, bodyMap);
		
		if ((Integer) tellerMap.get("totalNum") != 0) {
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0137);
		} else {
			bannerinfoService.addBannerinfo(headMap, bodyMap);
		}
		return entity;
	}

	/**
	 * 
	 * @Title: queryBannerinfo
	 * @Description: 广告栏查询
	 * @param headMap
	 *            头信息
	 * @param bodyMap
	 *            上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000402")
	public ResponseEntity queryBannerinfo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = bannerinfoService.queryBannerinfo(headMap, bodyMap);
		
		if (tellerMap != null && tellerMap.size() > 0) {
			entity = new ResponseEntity(tellerMap);
		} else {
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0138);
		}
		return entity;
	}

	/**
	 * 
	 * @Title: updateBannerinfo
	 * @Description: 修改广告栏
	 * @param headMap
	 *            头信息
	 * @param bodyMap
	 *            上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000403")
	public ResponseEntity updateBannerinfo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = bannerinfoService.queryBannerinfo(headMap, bodyMap);
		
		if((Integer)tellerMap.get("totalNum") != 0){
			bannerinfoService.updateBannerinfo(headMap, bodyMap);
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0138);		
		}
		return entity;
	}

	/**
	 * 
	 * @Title: deleteBannerinfo
	 * @Description: 删除广告栏
	 * @param headMap
	 *            头信息
	 * @param bodyMap
	 *            上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000404")
	public ResponseEntity deleteBannerinfo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = bannerinfoService.queryBannerinfo(headMap, bodyMap);
		
		if((Integer)tellerMap.get("totalNum") != 0){
			bannerinfoService.deleteBannerinfo(headMap,bodyMap);
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0138);		
		}
		return entity;
	}

}
