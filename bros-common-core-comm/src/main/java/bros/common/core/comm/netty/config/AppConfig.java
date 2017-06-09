package bros.common.core.comm.netty.config;

import java.util.Map;

/**
 * 
 * @ClassName: AppConfig 
 * @Description: netty通信配置文件
 * @author 何鹏
 * @date 2016年6月30日 上午9:26:27 
 * @version 1.0
 */
public class AppConfig{
	//------------------------------通信配置开始
	/**
	 * 端口  默认为8888
	 */
	private int port=8888;
	/**
	 * tcp是否延迟  默认为true
	 */
	private boolean tcpNoDelay=true;
	/**
	 * 是否保持连接   默认为true
	 */
	private boolean soKeepAlive=true;
	/**
	 * 报文头长度
	 */
	private int headLength = 8;
	/**
	 * 接力系统初始顺序流水号
	 */
	private String fillRightStr="000000";
	/**
	 * 校验位的md5加密主密钥
	 */
	private String mdKey="1234567890";
	/**
	 * 流水生成标志   里面存放的是渠道标识
	 */
	private Map<String,String> flowProductFlag;
	//--------------------------------通信配置结束
	
	public AppConfig() {
		super();
	}
	public final int getPort() {
		return port;
	}
	public final void setPort(int port) {
		this.port = port;
	}
	public final boolean isTcpNoDelay() {
		return tcpNoDelay;
	}
	public final void setTcpNoDelay(boolean tcpNoDelay) {
		this.tcpNoDelay = tcpNoDelay;
	}
	public final boolean isSoKeepAlive() {
		return soKeepAlive;
	}
	public final void setSoKeepAlive(boolean soKeepAlive) {
		this.soKeepAlive = soKeepAlive;
	}
	public final int getHeadLength() {
		return headLength;
	}
	public final void setHeadLength(int headLength) {
		this.headLength = headLength;
	}
	public Map<String, String> getFlowProductFlag() {
		return flowProductFlag;
	}
	public void setFlowProductFlag(Map<String, String> flowProductFlag) {
		this.flowProductFlag = flowProductFlag;
	}
	public String getFillRightStr() {
		return fillRightStr;
	}
	public void setFillRightStr(String fillRightStr) {
		this.fillRightStr = fillRightStr;
	}
	public String getMdKey() {
		return mdKey;
	}
	public void setMdKey(String mdKey) {
		this.mdKey = mdKey;
	}
	
}
