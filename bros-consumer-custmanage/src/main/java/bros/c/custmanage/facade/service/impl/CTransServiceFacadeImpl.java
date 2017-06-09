package bros.c.custmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.custmanage.facade.service.ICTransServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.custmanage.facade.service.IPTransServiceFacade;
/**
 * 
 * @ClassName: CTransServiceFacadeImpl 
 * @Description: 转账接口
 * @author gaoyongjing 
 * @date 2016年10月10日 下午2:44:40 
 * @version 1.0
 */
@Component("ctransServiceFacade")
public class CTransServiceFacadeImpl implements ICTransServiceFacade {
	@Autowired
	private IPTransServiceFacade ptransServiceFacade;
	/**
	 * 
	 * @Title: transferPreCheck 
	 * @Description: 汇款预校验
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity transferPreCheck(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return ptransServiceFacade.transferPreCheck(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: tranProcess 
	 * @Description: 发起单笔转账
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity tranProcess(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException{
		return ptransServiceFacade.tranProcess(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryAccTransResultInf 
	 * @Description: 根据条件查询转账结果流水记录信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryAccTransResultInf(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException{
		return ptransServiceFacade.queryAccTransResultInf(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryCnapsTransResult 
	 * @Description: 大小额转账结果查证
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryCnapsTransResult(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return ptransServiceFacade.queryCnapsTransResult(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryTransResult 
	 * @Description: 业务状态查询
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryTransResult(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return ptransServiceFacade.queryTransResult(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryApproveByStatMethod
	 * @Description: 条件查询落地审批信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryApproveByStatMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException{
		return ptransServiceFacade.queryApproveByStatMethod(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: cancelTransMethod
	 * @Description: 取消转账
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity cancelTransMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException{
		return ptransServiceFacade.cancelTransMethod(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: addEletoSingleMethod
	 * @Description: 添加电子回单
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity addEletoSingleMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException{
		return ptransServiceFacade.addEletoSingleMethod(headMap, bodyMap);
	}
	
	/**
	 * 
	 * @Title: queryEleToSingleinfoMethod
	 * @Description: 查询电子回单信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryEleToSingleinfoMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException{
		return ptransServiceFacade.queryEleToSingleinfoMethod(headMap, bodyMap);
	}
	
	/**
	 * 
	 * @Title: updateEletoSinglePrintNumByObjectId
	 * @Description: 更新打印次数
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity updateEletoSinglePrintNumByObjectId(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException{
		return ptransServiceFacade.updateEletoSinglePrintNumByObjectId(headMap, bodyMap);
	}
}
