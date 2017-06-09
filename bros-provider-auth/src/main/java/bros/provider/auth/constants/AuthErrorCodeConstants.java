package bros.provider.auth.constants;

/**
 * 
 * @ClassName: AuthErrorCodeConstants 
 * @Description: 授权模块错误码常量类
 * @author 何鹏
 * @date 2016年5月14日 下午1:09:56 
 * @version 1.0
 */
public class AuthErrorCodeConstants {
	/**
	 * 授权模块：系统错误
	 */
	public static final String PAUH0000 = "PAUH0000";
	
	/**
	 * 授权模块：业务未定义
	 */
	//public static final String PAUH0001 = "PAUH0001";
	/**
	 * 授权模块：查询待授权指令概览失败
	 */
	public static final String PAUH0002 = "PAUH0002";
	/**
	 * 授权模块：根据业务编号查询待授权交易列表失败
	 */
	public static final String PAUH0003 = "PAUH0003";
	/**
	 * 授权模块：根据业务编号查询待授权交易列表失败
	 */
	public static final String PAUH0004 = "PAUH0004";
	/**
	 * 授权模块：退回任务失败
	 */
	public static final String PAUH0005 = "PAUH0005";
	/**
	 * 授权模块：查询指令详情失败
	 */
	public static final String PAUH0006 = "PAUH0006";
	/**
	 * 授权模块：待授权指令不存在
	 */
	public static final String PAUH0007 = "PAUH0007";
	/**
	 * 授权模块：授权密码或指纹验证错误
	 */
	public static final String PAUH0008 = "PAUH0008";
	/**
	 * 授权模块：对指令进行授权操作失败
	 */
	public static final String PAUH0009 = "PAUH0009";
	/**
	 * 授权模块：授权柜员不能与指令提交柜员是同一人
	 */
	public static final String PAUH0010 = "PAUH0010";
	/**
	 * 授权模块：您无权对该指令进行授权处理
	 */
	public static final String PAUH0011 = "PAUH0011";
	/**
	 * 授权模块：查询指令批量详情列表失败
	 */
	public static final String PAUH0012 = "PAUH0012";
	/**
	 * 授权模块：查询待授权指令列表失败
	 */
	public static final String PAUH0013 = "PAUH0013";
	/**
	 * 授权模块：查询审批意见历史信息失败
	 */
	public static final String PAUH0014 = "PAUH0014";
	/**
	 * 授权模块：查询审批意见历史信息失败
	 */
	public static final String PAUH0015 = "PAUH0015";
	
	/**
	 * 授权模块：审核意见为空
	 */
	public static final String PAUH0016 = "PAUH0016";
	
	/**
	 * 授权模块：操作员无授权权限
	 */
	public static final String PAUH0017 = "PAUH0017";
	
	/**
	 * 授权模块：操作员已参与过授权处理，不能重复授权
	 */
	public static final String PAUH0018 = "PAUH0018";
	
	/**
	 * 授权模块：操作员未成功签收任务
	 */
	public static final String PAUH0019 = "PAUH0019";
	
	/**
	 * 授权模块：操作员无处理权限
	 */
	public static final String PAUH0020 = "PAUH0020";
	
	/**
	 * 授权模块：查询可撤销流程列表失败
	 */
	public static final String PAUH0021 = "PAUH0021";
	
	/**
	 * 授权模块：关联的流程实例不存在或状态不正常
	 */
	public static final String PAUH0022 = "PAUH0022";
	
	/**
	 * 授权模块：关联的流程实例不存在或尚未启动
	 */
	public static final String PAUH0023 = "PAUH0023";
	
	/**
	 * 授权模块：关联的流程实例正在处理中，不允许撤销
	 */
	public static final String PAUH0024 = "PAUH0024";
	
	/**
	 * 授权模块：删除流程实例失败
	 */
	public static final String PAUH0025 = "PAUH0025";
	
	/**
	 * 授权模块：撤销授权申请失败
	 */
	public static final String PAUH0026 = "PAUH0026";
	
	/**
	 * 授权模块：查询已办事宜列表失败
	 */
	public static final String PAUH0027 = "PAUH0027";
	
	/**
	 * 授权模块：查询概览统计失败
	 */
	public static final String PAUH0028 = "PAUH0028";
	/**
	 * 授权模块：获取授权详情模板数据失败
	 */
	public static final String PAUH0029 = "PAUH0029";
	/**
	 * 授权模块：流水记录表pub_bsnflow_cfg中该场景记录不存在
	 */
	public static final String PAUH0030 = "PAUH0030";
	/**
	 * 授权模块：授权详情数据模板不存在
	 */
	public static final String PAUH0031 = "PAUH0031";
	/**
	 * 授权模块：授权详情数据模板格式不正确
	 */
	public static final String PAUH0032 = "PAUH0032";
	/**
	 * 授权模块：cstNo不能为空
	 */
	public static final String PAUH0033 = "PAUH0033";
	/**
	 * 授权模块：operatorNo不能为空
	 */
	public static final String PAUH0034 = "PAUH0034";
	/**
	 * 授权模块：角色权限不能为空
	 */
	public static final String PAUH0035 = "PAUH0035";
	/**
	 * 授权模块：角色权限长度不为4位
	 */
	public static final String PAUH0036 = "PAUH0036";
	/**
	 * 授权模块：操作员信息不存在
	 */
	public static final String PAUH0037 = "PAUH0037";
	/**
	 * 授权模块：revokeReason不能为空
	 */
	public static final String PAUH0038 = "PAUH0038";
	/**
	 * 授权模块：tranTellerNo不能为空
	 */
	public static final String PAUH0039 = "PAUH0039";
	/**
	 * 授权模块：柜员信息不存在
	 */
	public static final String PAUH0040 = "PAUH0040";
	/**
	 * 授权模块：授权操作员不能与指令提交操作员是同一人
	 */
	public static final String PAUH0041 = "PAUH0041";

}
