package bros.c.custmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICTransServiceFacade 
 * @Description: 转账接口
 * @author gaoyongjing 
 * @date 2016年10月10日 下午2:44:40 
 * @version 1.0
 */
public interface ICTransServiceFacade {
	/**
	 * 
	 * @Title: transferPreCheck 
	 * @Description: 汇款预校验
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public ResponseEntity transferPreCheck(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: tranProcess 
	 * @Description: 发起单笔转账
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public ResponseEntity tranProcess(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryAccTransResultInf 
	 * @Description: 根据条件查询转账结果流水记录信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public ResponseEntity queryAccTransResultInf(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryCnapsTransResult 
	 * @Description: 大小额转账结果查证
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public ResponseEntity queryCnapsTransResult(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryTransResult 
	 * @Description: 业务状态查询
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public ResponseEntity queryTransResult(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryApproveByStatMethod
	 * @Description: 条件查询落地审批信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryApproveByStatMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: cancelTransMethod
	 * @Description: 取消转账
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity cancelTransMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: addEletoSingleMethod
	 * @Description: 添加电子回单
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity addEletoSingleMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryEleToSingleinfoMethod
	 * @Description: 查询电子回单信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryEleToSingleinfoMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateEletoSinglePrintNumByObjectId
	 * @Description: 更新打印次数
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity updateEletoSinglePrintNumByObjectId(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
}
