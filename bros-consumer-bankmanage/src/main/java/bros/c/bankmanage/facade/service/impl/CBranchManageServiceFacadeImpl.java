package bros.c.bankmanage.facade.service.impl;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICBranchManageServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPBranchManageServiceFacade;

/** 
 * @ClassName: CBranchManageServiceFacadeImpl 
 * @Description: 机构管理对外接口实现类
 * @author MacPro 
 * @date 2016年6月28日 上午9:24:49 
 * @version 1.0 
 */
@Component("cbranchManageServiceFacade")
public class CBranchManageServiceFacadeImpl implements ICBranchManageServiceFacade{

	@Autowired
	private IPBranchManageServiceFacade pbranchManageServiceFacade;
	/** 
	 * @Title: addBranch 
	 * @Description: 添加机构
	 * @param headMap
	 * @param contextMap
	 * @throws ServiceException 异常信息
	 */
	
	@Validation(value="c0000048")
	public ResponseEntity addBranch(Map<String, Object> headMap, Map<String, Object> contextMap) throws ServiceException {
		return pbranchManageServiceFacade.addBranch(headMap, contextMap);
	}

	/** 
	 * @Title: updateBranch 
	 * @Description: 修改机构
	 * @param headMap
	 * @param contextMap
	 * @throws ServiceException 异常信息
	 */
	
	@Validation(value="c0000049")
	public ResponseEntity updateBranch(Map<String, Object> headMap, Map<String, Object> contextMap) throws ServiceException {
		return pbranchManageServiceFacade.updateBranch(headMap, contextMap);
	}

	/** 
	 * @Title: deleteBranch 
	 * @Description: 删除机构
	 * @param headMap
	 * @param contextMap
	 * @throws ServiceException  异常信息
	 */
	
	@Validation(value="c0000050")
	public ResponseEntity deleteBranch(Map<String, Object> headMap, Map<String, Object> contextMap) throws ServiceException {
		return pbranchManageServiceFacade.deleteBranch(headMap, contextMap);
	}

	/** 
	 * @Title: queryAllBaranch 
	 * @Description: 机构列表查询
	 * @param headMap
	 * @param contextMap
	 * @return
	 * @throws ServiceException 异常信息
	 */
	public ResponseEntity queryAllBaranch(Map<String, Object> headMap, Map<String, Object> contextMap) throws ServiceException {
		return pbranchManageServiceFacade.queryAllBaranch(headMap, contextMap);
	}

}
