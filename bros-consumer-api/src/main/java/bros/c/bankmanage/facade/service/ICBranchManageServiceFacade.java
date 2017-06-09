package bros.c.bankmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: ICBranchManageServiceFacade 
 * @Description: 机构管理对外接口
 * @author liwei 
 * @date 2016年6月28日 上午9:22:16 
 * @version 1.0 
 */

public interface ICBranchManageServiceFacade {
	
	/**
	 * 
	 * @Title: addBranch 
	 * @Description: 添加机构
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException
	 */
	public ResponseEntity addBranch (Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateBranch 
	 * @Description: 修改机构
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException
	 */
	public ResponseEntity updateBranch(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteBranch 
	 * @Description: 删除机构（注销）
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException
	 */
	public ResponseEntity deleteBranch(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryAllBaranch 
	 * @Description: 查询机构列表
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException
	 */
	public ResponseEntity queryAllBaranch(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;

}
