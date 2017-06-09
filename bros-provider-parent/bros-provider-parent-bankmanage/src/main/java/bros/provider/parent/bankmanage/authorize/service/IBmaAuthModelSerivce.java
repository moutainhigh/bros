package bros.provider.parent.bankmanage.authorize.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IBmaAuthModelSerivce 
 * @Description: 内部授权模型管理服务接口
 * @author pengxiangnan 
 * @date 2016年7月18日 下午1:17:33 
 * @version 1.0
 */
public interface IBmaAuthModelSerivce {
	/**
	 * 
	 * @Title: checkAuthorizeModelRuleAndRoleList 
	 * @Description:检查规则数据及组装数据
	 * @param authRuleList 授权规则定义列表
	 * @param authMode 授权方式（0：额度; 1：强制(无金额); 2：条件）
	 *  @param authLevel 授权级别（0：单人; 1：多人）
	 * @return Map<String, Object> 返回结果集
	 * @throws ServiceException
	 */
	public Map<String, Object> checkAuthorizeModelRuleAndRoleList(List<Map<String,Object>> authRuleList, String authMode, 
   		 String authorizeId, List<Map<String,Object>> roleCheckedList,String authLevel)  throws ServiceException;

	/**
	 * 
	 * @Title: checkAuthorizeModelRuleNotCheckPersonNum 
	 * @Description:检查规则数据及组装数据
	 * @param authRuleList 授权规则定义列表
	 * @param authMode 授权方式（0：额度; 1：强制(无金额); 2：条件）
	 * @param authLevel 授权级别（0：单人; 1：多人）
	 * @return Map<String, Object> 返回结果集
	 * @throws ServiceException
	 */
	public Map<String, Object> checkAuthorizeModelRuleNotCheckPersonNum(List<Map<String,Object>> authRuleList, String authMode, 
      		 String authorizeId,String authLevel)  throws ServiceException;
	/**
	 * 
	 * @Title: saveAuthorizationModel 
	 * @Description: 新增授权模型
	 * @param authModelMap 授权模型集合
	 * @param authRuleList 授权规则定义列表
	 * @param roleList 角色列表
	 * @throws ServiceException
	 */
	public void saveAuthorizationModel(Map<String, Object> authModelMap, List<Map<String,Object>> authRuleList, List<Map<String,Object>> roleList) throws ServiceException;
	
	/**
	 * 
	 * @Title: saveAuthorizationModel 
	 * @Description: 新增授权模型
	 * @param authModelMap 授权模型集合
	 * @throws ServiceException
	 */
	public void saveAuthorizationModel(Map<String, Object> authModelMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: saveAuthModelRuleGp 
	 * @Description: 新增授权规则
	 * @param authRuleList 授权规则定义列表
	 * @throws ServiceException
	 */
	public void saveAuthModelRuleGp(List<Map<String,Object>> authRuleList) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteAuthorizationModel 
	 * @Description: 删除授权模型
	 * @param channel 渠道标识
	 * @param legalId 法人标识
	 * @param authorizeId 模型Id
	 * @param authRole 关联角色表ID
	 * @throws ServiceException
	 */
	public void deleteAuthorizationModel(String channel, String legalId ,String authorizeId, String authRole) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateAuthorizationModel 
	 * @Description: 修改授权模型
	 * @param authModelMap 授权模型集合
	 * @param authRuleList 授权规则定义列表
	 * @throws ServiceException
	 */
	public void updateAuthorizationModel(Map<String, Object> authModelMap, List<Map<String,Object>> authRuleList) throws ServiceException;
	
	/**
	 * 
	 * @Title: checkAuthorizeModelRule 
	 * @Description:检查规则数据及组装数据
	 * @param authRuleList 授权规则定义列表
	 * @param authMode 授权方式（0：额度; 1：强制(无金额); 2：条件）
	 * @param authLevel 授权级别（0：单人; 1：多人）
	 * @return Map<String, Object> 返回结果集
	 * @throws ServiceException
	 */
	public Map<String, Object>  checkAuthorizeModelRule(List<Map<String,Object>> authRuleList, String authMode, 
    		 String authorizeId,String authLevel)  throws ServiceException;
	
	/**
	 * 
	 * @Title: checkAmountRange 
	 * @Description: 校验金额区间的完整性 
	 * @param authMode 授权方式（0：额度; 1：强制(无金额); 2：条件）
	 * @param amountMap 金额区间集合
	 * @throws ServiceException
	 */
	public void checkAmountRange(String authMode, Map<Integer, Map<String, BigDecimal>> amountMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: checkAuthModelIsExist 
	 * @Description: 检查授权模型是否存在
	 * @param channel 渠道编号
	 * @param legalId 法人唯一标识
	 * @param authModelName 授权模型名称
	 * @throws ServiceException 
	 */
	public void checkAuthModelIsExist(String channel, String legalId, String authModelName) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryBmaRoleByRole 
	 * @Description: 检查授权模型是否存在
	 * @param authRole 渠道编号
	 * @return List<Map<String, Object>>
	 * @throws ServiceException 
	 */
	public List<Map<String, Object>> queryBmaRoleByRole(String authRole) throws ServiceException;
}
