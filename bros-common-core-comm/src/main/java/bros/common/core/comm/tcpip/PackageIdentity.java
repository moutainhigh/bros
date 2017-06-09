package bros.common.core.comm.tcpip;

/**
 * 
 * @ClassName: PackageIdentity 
 * @Description: 响应报文识别接口，在单工通讯（一收一发）协议的情况下，当调用通信服务程序的接收
 *  响应报文时，通讯程序将调用此接口来判断接收到的数据包是否为所需要的数据包。
 * @author huangcanhui
 * @date 2015年6月14日 下午3:29:48 
 * @version 3.0
 */
public interface PackageIdentity {
	/**
	 * 
	 * @Title: isTargetPackage 
	 * @Description: 判断数据包msg是否为所需要的数据包
	 * @param identity
	 * @param pkg
	 * @return
	 */
	public boolean isTargetPackage(Object identity, byte[] pkg );
}
