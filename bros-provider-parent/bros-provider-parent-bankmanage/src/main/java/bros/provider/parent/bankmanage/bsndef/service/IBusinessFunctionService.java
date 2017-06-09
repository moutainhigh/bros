package bros.provider.parent.bankmanage.bsndef.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IBusinessFunctionService 
 * @Description: 业务功能管理服务实现接口
 * @author huangdazhou 
 * @date 2016年12月23日 上午10:46:35 
 * @version 1.0
 */
public interface IBusinessFunctionService {
	/**
	 * 
	 * @Title: queryBsnFun 
	 * @Description: 查询业务功能信息
	 * @param bbfChannel 渠道编码
	 * @param bbfId 业务功能id
	 * @param bbfBsnCode 业务编码
	 * @param bbfType 业务编码
	 * @return bbfType 业务类型
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryBsnFun(String bbfChannel,String bbfId,String bbfBsnCode,String bbfType)throws ServiceException;
	/**
	 * 
	 * @Title: queryBsnFunRelMenudef 
	 * @Description: 查询业务与菜单关联功能
	 * @param bbfChannel 渠道编码
	 * @param burlMenueId 菜单编码
	 * @param bbfType 业务类型
	 * @param bbfId 业务id
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryBsnFunRelMenudef(String bbfChannel,String burlMenueId,String bbfType,String bbfId)throws ServiceException;
	/**
	 * 
	 * @Title: queryBsnFunRelMenuNum 
	 * @Description: 查询业务菜单关联笔数
	 * @param bbfBsnCode
	 * @param burlMenueId
	 * @return
	 * @throws ServiceException
	 */
	public int queryBsnFunRelMenuNum(String bbfBsnCode,String burlMenueId)throws ServiceException;
	/**
	 * 
	 * @Title: insertBsnFunRelMenu 
	 * @Description: 添加业务菜单关联
	 * @param insertList
	 * @return
	 * @throws ServiceException
	 */
	public int insertBsnFunRelMenu(List<Map<String, Object>> insertList)throws ServiceException;
	/**
	 * 
	 * @Title: updateBsnFunRelMenu 
	 * @Description: 修改业务菜单关联
	 * @param updateList
	 * @return
	 * @throws ServiceException
	 */
	//public int updateBsnFunRelMenu(List<Map<String, Object>> updateList)throws ServiceException;
	/**
	 * 
	 * @Title: deleteBsnFunRelMenu 
	 * @Description: 删除业务菜单关联
	 * @param deleteList
	 * @return
	 * @throws ServiceException
	 */
	public int deleteBsnFunRelMenu(List<Map<String, Object>> deleteList)throws ServiceException;
	/**
	 * @Title: insertBsndef 
	 * @Description:添加业务功能信息
	 * @param bbfBsnCode 业务编码 
	 * @param bbfName 业务名称
	 * @param bbfAlias 业务别名
	 * @param bbfDesc 业务描述
	 * @param bbfType 业务类型（0：普通交易；1：网银查询交易；2：付款录入交易；3：授权交易；4：代理业务录入交易；5：维护类交易；6：申请录入类交易；9：管理台交易；10：交易发送 ；S为系统交易）
	 * @param bbfBsnlv 业务级别（0：默认开通交易；1：需申请交易）
	 * @param bbfGroup 是否为集团客户专有交易（0：普通交易；1：集团交易）
	 * @param bbfUserlv 用户级别（0：密码用户专有；1：证书用户专有；2：公有）
	 * @param bbfStt 交易状态（0：正常；1：冻结）
	 * @param bbfUrl 指向明细查询URL
	 * @param bbfRequireAuth 是否需要授权（0:无需授权、1:金额相关、2:金额无关、3:管理类授权）
	 * @param bbfChannel 渠道唯一标识
	 * @param bbfCanbatch 是否可以批量授权（0：否 ,1：是） 
	 * @param bbfModel 授权详情页面模板名称
	 * @return
	 * @throws ServiceException
	 */
	public int insertOneBsndef(String bbfBsnCode,String bbfName,String bbfAlias,String bbfDesc,String bbfType,
			String bbfBsnlv,String bbfGroup,String bbfUserlv,String bbfStt,String bbfUrl,String bbfRequireAuth,
			String bbfChannel,String bbfCanbatch,String bbfModel)throws ServiceException;
	/**
	 * 
	 * @Title: updateBsndef 
	 * @Description: 修改业务功能信息
	 * @param bbfId bbfId
	 * @param bbfBsnCode 业务编码 
	 * @param bbfName 业务名称
	 * @param bbfAlias 业务别名
	 * @param bbfDesc 业务描述
	 * @param bbfType 业务类型（0：普通交易；1：网银查询交易；2：付款录入交易；3：授权交易；4：代理业务录入交易；5：维护类交易；6：申请录入类交易；9：管理台交易；10：交易发送 ；S为系统交易）
	 * @param bbfBsnlv 业务级别（0：默认开通交易；1：需申请交易）
	 * @param bbfGroup 是否为集团客户专有交易（0：普通交易；1：集团交易）
	 * @param bbfUserlv 用户级别（0：密码用户专有；1：证书用户专有；2：公有）
	 * @param bbfStt 交易状态（0：正常；1：冻结）
	 * @param bbfUrl 指向明细查询URL
	 * @param bbfRequireAuth 是否需要授权（0:无需授权、1:金额相关、2:金额无关、3:管理类授权）
	 * @param bbfChannel 渠道唯一标识
	 * @param bbfCanbatch 是否可以批量授权（0：否 ,1：是） 
	 * @param bbfModel 授权详情页面模板名称
	 * @return
	 * @throws ServiceException
	 */
	public int updateOneBsndef(String bbfId,String bbfBsnCode,String bbfName,String bbfAlias,String bbfDesc,String bbfType,
			String bbfBsnlv,String bbfGroup,String bbfUserlv,String bbfStt,String bbfUrl,String bbfRequireAuth,
			String bbfChannel,String bbfCanbatch,String bbfModel)throws ServiceException;
	/**
	 * 
	 * @Title: deleteBsndef 
	 * @Description: 删除业务功能信息
	 * @param bbfId
	 * @return
	 * @throws ServiceException
	 */
	public int deleteOneBsndef(String bbfId)throws ServiceException;
	
}
