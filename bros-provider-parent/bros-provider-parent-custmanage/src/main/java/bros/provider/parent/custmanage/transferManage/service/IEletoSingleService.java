package bros.provider.parent.custmanage.transferManage.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IEletoSingleService 
 * @Description: 电子回单
 * @author 高永静
 * @date 2016年11月3日 上午9:45:17 
 * @version 1.0
 */
public interface IEletoSingleService {
	/**
	 * 
	 * @Title: addEletoSingleMethod
	 * @Description: 添加电子回单
	 * @param eletoReceiptInfo 电子回单信息
	 * @return
	 * @throws ServiceException
	 */
	public void addEletoSingleMethod(Map<String,Object> eletoReceiptInfo)throws ServiceException;
	/**
	 * 
	 * @Title: queryEleToSingleinfoMethod
	 * @Description: 查询电子回单信息
	 * @param legalId 法人id
	 * @param cstNo 客户号
	 * @param eleReceiptNo 回单号
	 * @param authCode 验证码
	 * @param transTimestamp 交易时间
	 * @param payAccNo 付款人账号
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryEleToSingleinfoMethod(String legalId,String cstNo,String eleReceiptNo,String authCode,String transTimestamp,String payAccNo)throws ServiceException;
	 
	/**
	 * 
	 * @Title: updateEletoSinglePrintNumByObjectId
	 * @Description: 更新打印次数
	 * @param objectId  记录唯一id
	 * @return
	 * @throws ServiceException
	 */
	public void updateEletoSinglePrintNumByObjectId(String objectId)throws ServiceException;
}
