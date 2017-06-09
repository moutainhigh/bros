package bros.c.bankmanage.facade.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICBannerinfoServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPBannerinfoServiceFacade;

/**
 * @ClassName:IPBannerinfoServiceFacade
 * @Description:广告栏服务对外接口实现类
 * @author wuchenglong
 * @date 2016年9月6日 上午14:50:28
 * @version V1.0
 */
@Component("cbannerinfoServiceFacade")
public class CBannerinfoServiceFacadeImpl implements ICBannerinfoServiceFacade {

	/**
	 * 广告栏实现类
	 */
	@Autowired
	private IPBannerinfoServiceFacade pbannerinfoServiceFacade;

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
	@Validation(value="c0000400")
	public ResponseEntity addBannerinfo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pbannerinfoServiceFacade.addBannerinfo(headMap, bodyMap);
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
	@Validation(value="c0000402")
	public ResponseEntity queryBannerinfo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pbannerinfoServiceFacade.queryBannerinfo(headMap, bodyMap);
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
	@Validation(value="c0000403")
	public ResponseEntity updateBannerinfo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pbannerinfoServiceFacade.updateBannerinfo(headMap, bodyMap);
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
	@Validation(value="c0000404")
	public ResponseEntity deleteBannerinfo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pbannerinfoServiceFacade.deleteBannerinfo(headMap, bodyMap);
	}

}
