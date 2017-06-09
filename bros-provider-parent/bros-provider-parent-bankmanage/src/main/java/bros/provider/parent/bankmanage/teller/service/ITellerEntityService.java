package bros.provider.parent.bankmanage.teller.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ITellerEntityService 
 * @Description: 柜员实体服务接口
 * @author 何鹏
 * @date 2016年7月19日 下午2:07:56 
 * @version 1.0
 */
public interface ITellerEntityService {
	/**
	 * 
	 * @Title: queryTellerInfo 
	 * @Description: 查询柜员信息
	 * @param legalId	法人id
	 * @param branchCode	机构编号
	 * @param tellerCode	柜员编号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTellerInfo(String legalId, String branchCode,String tellerCode) throws ServiceException;
	/**
	 * 
	 * @Title: updateTellerStt 
	 * @Description: 根据法人编号、机构编码、柜员编号更新柜员状态
	 * @param legalId	法人id
	 * @param branchCode	机构编号
	 * @param tellerCode	柜员编号
	 * @param stt	柜员状态
	 * @throws ServiceException
	 */
	public void updateTellerStt(String legalId,String branchCode,String tellerCode,String stt) throws ServiceException;
}
