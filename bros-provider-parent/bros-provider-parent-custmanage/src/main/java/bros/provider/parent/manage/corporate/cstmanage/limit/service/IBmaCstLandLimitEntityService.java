package bros.provider.parent.manage.corporate.cstmanage.limit.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IBmaCstLandLimitEntityService 
 * @Description: 企业落地限额服务
 * @author zsg 
 * @date 2017年1月12日 下午3:04:47 
 * @version 1.0
 */
public interface IBmaCstLandLimitEntityService {
	/**
	 * 
	 * @Title: addBmaCstLandLimit 
	 * @Description: 添加企业客户落地限额
	 * @param cstNo
	 * @param channel
	 * @param legalId
	 * @param singleMax
	 * @return
	 * @throws ServiceException
	 */
	public int addBmaCstLandLimit(String cstNo,String channel,String legalId,String singleMax)throws ServiceException;
	/**
	 * 
	 * @Title: queryBmaCstLandCstLimitList 
	 * @Description: 根据客户号+法人id+渠道查询企业客户落地限额信息
	 * @param cstNo
	 * @param legalId
	 * @param channel
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryBmaCstLandLimitList(String cstNo,String legalId,String channel)throws ServiceException;
	/**
	 * 
	 * @Title: updateBmaCstLandCstLimit 
	 * @Description: 更新企业客户落地限额
	 * @param cstNo
	 * @param channel
	 * @param legalId
	 * @param singleMax
	 * @return
	 * @throws ServiceException
	 */
	public int updateBmaCstLandLimit(String cstNo,String channel,String legalId,String singleMax)throws ServiceException;
	/**
	 * 
	 * @Title: deleteBmaCstLandCstLimit 
	 * @Description: 根据客户号+渠道+法人id删除客户限额
	 * @param cstNo
	 * @param channel
	 * @param legalId
	 * @return
	 * @throws ServiceException
	 */
	public int deleteBmaCstLandLimit(String cstNo,String channel,String legalId)throws ServiceException;
	
}
