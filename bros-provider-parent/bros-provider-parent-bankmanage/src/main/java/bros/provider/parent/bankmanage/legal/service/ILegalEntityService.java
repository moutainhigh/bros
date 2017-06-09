package bros.provider.parent.bankmanage.legal.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ILegalEntityService 
 * @Description: 法人实体服务接口
 * @author 何鹏
 * @date 2016年7月19日 下午2:01:45 
 * @version 1.0
 */
public interface ILegalEntityService {
	/**
	 * 
	 * @Title: queryLegalInfo 
	 * @Description: 根据法人编号和法人状态查询法人信息
	 * @param legalCode	法人编号
	 * @param stt	法人状态 	0：正常   1：清算	2：撤销
	 * @return
	 */
	public Map<String,Object> queryLegalInfo(String legalCode,String stt) throws ServiceException;
	/**
	 * 
	 * @Title: queryLegalInfoById 
	 * @Description: 根据法人id和法人状态查询法人信息
	 * @param legalId	法人id
	 * @param stt	法人状态 	0：正常   1：清算	2：撤销
	 * @return
	 */
	public Map<String,Object> queryLegalInfoById(String legalId,String stt) throws ServiceException;

}
