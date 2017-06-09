package bros.common.core.comm.netty.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import bros.common.core.comm.constants.CommErrorCodeConstants;
import bros.common.core.comm.util.Crc32Util;
import bros.common.core.crypto.service.impl.EncryptDecryptUtilImpl;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.JsonUtil;
/**
 * 
 * @ClassName: SocketClient 
 * @Description: socket客户端调用
 * @author 何鹏
 * @date 2016年6月30日 下午3:17:28 
 * @version 1.0
 */
public class SocketClient {

	private static String mdKey = "1234567890";
			
	public static void main(String[] args) throws Exception {
		Map<String,Object> headMap = new HashMap<String,Object>();
		Map<String,Object> bodyMap = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		
		headMap.put("serviceName", "queryAllBranchManageAction");
		headMap.put("globalSeqNo", "301001111122222222223333333333");
		headMap.put("tranSeqNo", "3010011111222222222233333333334444444444");
		headMap.put("branchId", "4444");
		headMap.put("tranTellerNo", "55555");
		headMap.put("consumerId", "6666");
		headMap.put("tranDate", "20150910");
		headMap.put("tranTime", "8888888");
		headMap.put("channelDate", "99999999");
		headMap.put("channel", "2222");
		headMap.put("sceneCode", "1111");
		headMap.put("legalCode", "branchLegal-01");
		headMap.put("legalId", "123456789012345678901234567890123456");
		headMap.put("flag", "1");
		headMap.put("tradeType", "2");
		headMap.put("version", "2.0.0");
		
		
		//headMap.put("taxi", "8888");
		//headMap.put("stepCode", "01");
		
		
//		bodyMap.put("branchCode", "9999");
//		bodyMap.put("accountName", "测试姓名");
		
		map.put("reqHead", headMap);
		map.put("reqBody", bodyMap);
		String result = "aaaaaa";
		System.out.println(EncryptDecryptUtilImpl.getInstance().MD5(result,mdKey)+result);
		tcpclientMethodByte(JsonUtil.mapToJsonStr(map));
	}

	@SuppressWarnings("resource")
	public static void tcpclientMethodByte(String result) {
		try {
			result = EncryptDecryptUtilImpl.getInstance().MD5(result,mdKey)+result;
			
			System.out.println(result);
			byte[] msg = result.getBytes();
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress("127.0.0.1", 18888));
			socket.setKeepAlive(true);
			OutputStream out = socket.getOutputStream();
			ByteBuffer header = ByteBuffer.allocate(8);
			System.out.println(msg.length);
			header.putInt(msg.length);
			out.write(header.array());
			out.write(msg);
			out.flush();
			InputStream in = socket.getInputStream();
			byte[] buff = new byte[1024*1024];
			int readed = in.read(buff);
			if (readed > 0) {
				String str = new String(buff);
				System.out.println("client received msg from server:" + str);
			}
			out.close();
			
			
			String recieveMessage = new String(buff);
			String recieveValidatorCode = recieveMessage.substring(8,32);//报文传送过来的校验码
			String recieveBody = recieveMessage.substring(32).trim();
			
			String countValidatorCode = EncryptDecryptUtilImpl.getInstance().MD5(recieveBody,mdKey);;//计算收到的报文体的校验码
			if(!countValidatorCode.equals(recieveValidatorCode)){
				System.out.println(CommErrorCodeConstants.CCCM0013+"===数据非法，被篡改");
			}
			
			System.out.println("=========over");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
