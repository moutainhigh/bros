package bros.common.core.comm.netty.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import bros.common.core.comm.constants.CommErrorCodeConstants;
import bros.common.core.comm.util.Crc32Util;
import bros.common.core.crypto.service.impl.EncryptDecryptUtilImpl;
import bros.common.core.util.JsonUtil;
import bros.common.core.util.ValidateUtil;

/**
 * 
 * @ClassName: HttpClient 
 * @Description: HttpClient
 * @author 何鹏
 * @date 2016年5月14日 下午3:25:25 
 * @version 1.0
 */
public class HttpClient{
	/**
	 * 报文编码
	 * */
	private String charset = "UTF-8";
	/**
	 * 通信参数配置
	 * */
	private String backSysDesc="requestMethod:POST|Accept:*/*|User-Agent:Apache-HttpClient/4.1.1|Accept-Encoding:gzip,deflate|Connection:Keep-Alive|Content-Type:text/xml;charset=|namespace;";
	/**
	 * 通信超时时间   单位秒
	 * */
	private String timeout="360";//超时时间   秒
	
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
		headMap.put("version", "1.0.0");
		
		
		bodyMap.put("branchCode", "9999");
		bodyMap.put("accountName", "测试姓名");
		
		map.put("reqHead", headMap);
		map.put("reqBody", bodyMap);
		new HttpClient().sendMsg(JsonUtil.mapToJsonStr(map));
	}
	
	public void sendMsg(String sendMsg) throws Exception{
		
		sendMsg = EncryptDecryptUtilImpl.getInstance().MD5(sendMsg,mdKey)+sendMsg;
		
		System.out.println("上送报文："+sendMsg);
		URL url = new URL("http://127.0.0.1:18844/aaaaaa");
		HttpURLConnection connection = null;
		String returnMsg = "";
		StringBuffer buffer = new StringBuffer();
			
				
		String[] paramArray = backSysDesc.split("\\|");
		Map<String,String> props = new HashMap<String, String>(); 
		for (String string : paramArray) {
			if(!ValidateUtil.isEmpty(string) && string.indexOf(":") != -1){
				String key = string.split(":")[0];
				String value = string.split(":")[1];
				if(null!= key){
					props.put(key, value);
				}
			}
		}
				
		connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setConnectTimeout(Integer.parseInt(timeout)*1000);//连接超时时间 6分钟超时
		//新二代
		if(props.get("requestMethod")!= null){
			connection.setRequestMethod(props.get("requestMethod"));
		}
		if(props.get("Accept")!= null){
			connection.setRequestProperty("Accept", props.get("Accept"));
		}
		if(props.get("User-Agent")!= null){
			connection.setRequestProperty("User-Agent", props.get("User-Agent"));
		}
		if(props.get("Accept-Language")!= null){
			connection.setRequestProperty("Accept-Language", props.get("Accept-Language"));
		}
		if(props.get("Accept-Encoding")!= null){
			connection.setRequestProperty("Accept-Encoding", props.get("Accept-Encoding"));
		}
		if(props.get("Cache-Control")!= null){
			connection.setRequestProperty("Cache-Control", props.get("Cache-Control"));
		}
		if(props.get("Content-Type")!= null){
			connection.addRequestProperty("Content-Type", props.get("Content-Type")+charset);
		}

		connection.setUseCaches(false);
		connection.connect();

		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.write(sendMsg.getBytes(charset));
		out.flush();
		out.close();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), charset));
		
		String line = "";
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		reader.close();
		returnMsg = buffer.toString();
		System.out.println("收到报文=="+returnMsg);
		
		String recieveValidatorCode = returnMsg.substring(8,32);//报文传送过来的校验码
		String recieveBody = returnMsg.substring(32).trim();
		
		String countValidatorCode = EncryptDecryptUtilImpl.getInstance().MD5(recieveBody,mdKey);;//计算收到的报文体的校验码
		if(!countValidatorCode.equals(recieveValidatorCode)){
			System.out.println(CommErrorCodeConstants.CCCM0013+"===数据非法，被篡改");
		}
		
		System.out.println("=========over");
	}
}
