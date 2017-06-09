package bros.unified.receive.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bros.common.core.comm.constants.CommErrorCodeConstants;
import bros.common.core.crypto.service.impl.EncryptDecryptUtilImpl;
import bros.common.core.util.DateUtil;
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
		queryDiscoverHot(headMap, bodyMap);
		//更新购物车
		//updateShopCartMethod(headMap, bodyMap);
		//查询购物车
		//queryShopCartMethod(headMap,bodyMap);
		//删除购物车中的某些记录
		//deleteShopCartRecordMethod(headMap,bodyMap);
		//清除购物车
		//deleteClearShopCartMethod(headMap,bodyMap);
		map.put("reqHead", headMap);
		map.put("reqBody", bodyMap);
		TaskCenterViewMethod(headMap, bodyMap);
//		customerAuthMethod(headMap, bodyMap);
		tcpclientMethodByte(JsonUtil.mapToJsonStr(map));
	}
	private static void checkShowShelfGoodsInfoMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) {
		headMap.put("serviceName", "checkShowShelfGoodsInfoMethodAction");
		headMap.put("globalSeqNo", "301001111122222222223333333333");
		headMap.put("tranSeqNo", "3010011111222222222233333333334444444444");
		headMap.put("branchId", "4444");
		headMap.put("tranTellerNo", "55555");
		headMap.put("consumerId", "6666");
		headMap.put("tranDate", "20150910");
		headMap.put("tranTime", "8888888");
		headMap.put("channelDate", "99999999");
		headMap.put("channel", "1111");
		headMap.put("sceneCode", "1111");
		headMap.put("legalCode", "branchLegal-01");
		headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
		headMap.put("flag", "1");
		headMap.put("tradeType", "2");
		headMap.put("version", "1.0.0");
			
			
		bodyMap.put("productStoreId","MODEL-FP-OL-STORE");
		bodyMap.put("productId","CARD_0615_10000-01");
		bodyMap.put("goodStatus","00");
		bodyMap.put("goodsType","02");
		bodyMap.put("goodsFlag","00");
	}
	private static void queryDiscoverPromotions(Map<String, Object> headMap,
			Map<String, Object> bodyMap) {
		headMap.put("serviceName", "queryDiscoverPromotionsAction");
		headMap.put("globalSeqNo", "301001111122222222223333333333");
		headMap.put("tranSeqNo", "3010011111222222222233333333334444444444");
		headMap.put("branchId", "4444");
		headMap.put("tranTellerNo", "55555");
		headMap.put("consumerId", "6666");
		headMap.put("tranDate", "20150910");
		headMap.put("tranTime", "8888888");
		headMap.put("channelDate", "99999999");
		headMap.put("channel", "1111");
		headMap.put("sceneCode", "1111");
		headMap.put("legalCode", "branchLegal-01");
		headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
		headMap.put("flag", "1");
		headMap.put("tradeType", "2");
		headMap.put("version", "1.0.0");
			
			
		bodyMap.put("productStoreId","MODEL-FP-OL-STORE");
	}
	private static void queryDiscoverHot(Map<String, Object> headMap,
			Map<String, Object> bodyMap) {
		headMap.put("serviceName", "queryDiscoverHotAction");
		headMap.put("globalSeqNo", "301001111122222222223333333333");
		headMap.put("tranSeqNo", "3010011111222222222233333333334444444444");
		headMap.put("branchId", "4444");
		headMap.put("tranTellerNo", "55555");
		headMap.put("consumerId", "6666");
		headMap.put("tranDate", "20150910");
		headMap.put("tranTime", "8888888");
		headMap.put("channelDate", "99999999");
		headMap.put("channel", "1111");
		headMap.put("sceneCode", "1111");
		headMap.put("legalCode", "branchLegal-01");
		headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
		headMap.put("flag", "1");
		headMap.put("tradeType", "2");
		headMap.put("version", "1.0.0");
			
			
		bodyMap.put("productStoreId","MODEL-FP-OL-STORE");
	}
	private static void customerAuthMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) {
		Date date=new Date();
		SimpleDateFormat df = new SimpleDateFormat(DateUtil.DEFAULT_TIME_FORMAT_DB);
		String dateFormat=df.format(date);
		headMap.put("serviceName", "queryOutSideQueryTaskCenterViewAction");
		headMap.put("globalSeqNo", "3010011111222222"+dateFormat);
		headMap.put("tranSeqNo", "30100111112222222222333333"+dateFormat);
		headMap.put("branchId", "4444");
		headMap.put("bsnCode", "CB88888888");
		headMap.put("tranTellerNo", "462");
		headMap.put("operatorNo", "00007");
		headMap.put("consumerId", "6666");
		headMap.put("tranDate", "20150910");
		headMap.put("tranTime", "8888888");
		headMap.put("channelDate", "99999999");
		headMap.put("channel", "1003");
		headMap.put("sceneCode", "1111");
		headMap.put("legalCode", "branchLegal-01");
		headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
		headMap.put("flag", "1");
		headMap.put("tradeType", "2");
		headMap.put("version", "1.0.0");
		
		bodyMap.put("cstNo", "C888889137");
		bodyMap.put("nowTurnFlag", "0");
		bodyMap.put("payAccNo", "19910617180");
		bodyMap.put("payAccName", "张飞");
		bodyMap.put("voucheType", "100");
		bodyMap.put("voucheNo", "00000001");
		bodyMap.put("payType", "1");
		bodyMap.put("payPwdNo", "1111111111111111");
		bodyMap.put("voucheDate", "20160920");
		bodyMap.put("cansiCode", "10100");
		bodyMap.put("accounType", "2");
		bodyMap.put("payBankNo", "1000000000011");
		bodyMap.put("payBankName", "中国人民银行");
		bodyMap.put("resiveAccNo", "622622011551339");
		bodyMap.put("resiveAccName", "嘻嘻股份有限公司");
		bodyMap.put("resiveBankNo", "1000000000011");
		bodyMap.put("resiveBankName", "中国人民银行");
		bodyMap.put("currency", "CNY");
		bodyMap.put("transAmt", "20000.00");
		bodyMap.put("feeAmt", "10.00");
		bodyMap.put("purpose", "本行转账");
		
	}
	private static void TaskCenterViewMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) {
		Date date=new Date();
		SimpleDateFormat df = new SimpleDateFormat(DateUtil.DEFAULT_TIME_FORMAT_DB);
		String dateFormat=df.format(date);
		headMap.put("serviceName", "queryOutSideQueryTaskCenterViewAction");
		headMap.put("globalSeqNo", "3010011111222222"+dateFormat);
		headMap.put("tranSeqNo", "30100111112222222222333333"+dateFormat);
		headMap.put("branchId", "4444");
		headMap.put("bsnCode", "");
		headMap.put("tranTellerNo", "462");
		headMap.put("operatorNo", "00001");
		headMap.put("consumerId", "6666");
		headMap.put("tranDate", "20150910");
		headMap.put("tranTime", "8888888");
		headMap.put("channelDate", "99999999");
		headMap.put("channel", "1003");
		headMap.put("sceneCode", "1111");
		headMap.put("legalCode", "branchLegal-01");
		headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
		headMap.put("flag", "1");
		headMap.put("tradeType", "2");
		headMap.put("version", "1.0.0");
		headMap.put("cstNo", "C888889137");
		
		
		
	}
	private static void getSurveyQuestion(Map<String, Object> headMap,
			Map<String, Object> bodyMap) {
		headMap.put("serviceName", "getSurveyQuestionAction");
		headMap.put("globalSeqNo", "301001111122222222223333333333");
		headMap.put("tranSeqNo", "3010011111222222222233333333334444444444");
		headMap.put("branchId", "4444");
		headMap.put("tranTellerNo", "55555");
		headMap.put("consumerId", "6666");
		headMap.put("tranDate", "20150910");
		headMap.put("tranTime", "8888888");
		headMap.put("channelDate", "99999999");
		headMap.put("channel", "1111");
		headMap.put("sceneCode", "1111");
		headMap.put("legalCode", "branchLegal-01");
		headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
		headMap.put("flag", "1");
		headMap.put("tradeType", "2");
		headMap.put("version", "1.0.0");
			
			
		bodyMap.put("configOptionId","CARD_0521_10010");
		bodyMap.put("productId","CARD_0521_10000-01");
	}
	
	private static void deleteClearShopCartMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) {
		headMap.put("serviceName", "deleteClearShopCartAction");
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
		headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
		headMap.put("flag", "1");
		headMap.put("tradeType", "2");
		headMap.put("version", "1.0.0");
		
		bodyMap.put("uniqueCstIdentity", "19910617180");
	}
	
	private static void queryBankingLimitAction(Map<String, Object> headMap,
			Map<String, Object> bodyMap) {
		headMap.put("serviceName", "queryBankingLimitAction");
		headMap.put("globalSeqNo", "301001111122222222223333333333");
		headMap.put("tranSeqNo", "3010011111222222222233333333334444444444");
		headMap.put("branchId", "4444");
		headMap.put("tranTellerNo", "55555");
		headMap.put("consumerId", "6666");
		headMap.put("tranDate", "20150910");
		headMap.put("tranTime", "8888888");
		headMap.put("channelDate", "99999999");
		headMap.put("channel", "1111");
		headMap.put("sceneCode", "1111");
		headMap.put("legalCode", "branchLegal-01");
		headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
		headMap.put("flag", "1");
		headMap.put("tradeType", "2");
		headMap.put("version", "1.0.0");
			
			
		bodyMap.put("channelNo","1003");
		bodyMap.put("legalNo","154eb799aabc");
		bodyMap.put("pageNo","1");
		bodyMap.put("pageSize","10");
		
	}
	
	
	
	
	
	
	private static void updateFunctionAuthorizationModel(Map<String, Object> headMap,
			Map<String, Object> bodyMap) {
		headMap.put("serviceName", "updateBmaFunctionAuthorizationModelAction");
		headMap.put("globalSeqNo", "301001111122222222223333333333");
		headMap.put("tranSeqNo", "3010011111222222222233333333334444444444");
		headMap.put("branchId", "4444");
		headMap.put("tranTellerNo", "55555");
		headMap.put("consumerId", "6666");
		headMap.put("tranDate", "20150910");
		headMap.put("tranTime", "8888888");
		headMap.put("channelDate", "99999999");
		headMap.put("channel", "1111");
		headMap.put("sceneCode", "1111");
		headMap.put("legalCode", "branchLegal-01");
		headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
		headMap.put("flag", "1");
		headMap.put("tradeType", "2");
		headMap.put("version", "1.0.0");
		
		List<Map<String,Object>> funcAuthList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map1 = new HashMap<String, Object>();
		map1.put("bsnCode", "CB05050001");
		map1.put("modelId", "4918c853-ce7a-401b-937c-1833dddcb2a3");
		funcAuthList.add(map1);
		
		
		Map<String,Object> map2 = new HashMap<String, Object>();
		map2.put("bsnCode", "CB05050003");
		map2.put("modelId", "");
		funcAuthList.add(map2);
		
		
		bodyMap.put("funcAuthList",funcAuthList);
		
	}
	
	private static void deleteShopCartRecordMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) {
		headMap.put("serviceName", "deleteShopCartRecordAction");
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
		headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
		headMap.put("flag", "1");
		headMap.put("tradeType", "2");
		headMap.put("version", "1.0.0");
		
		bodyMap.put("uniqueCstIdentity", "19910617180");
		List<String> cartList = new ArrayList<String>();
		cartList.add("01");
		bodyMap.put("cartList", cartList);
	}
	private static void queryShopCartMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) {
		headMap.put("serviceName", "queryShopCartAction");
		headMap.put("globalSeqNo", "301001111122222222223333333333");
		headMap.put("tranSeqNo", "3010011111222222222233333333334444444444");
		headMap.put("branchId", "4444");
		headMap.put("tranTellerNo", "55555");
		headMap.put("consumerId", "2222");
		headMap.put("tranDate", "20150910");
		headMap.put("tranTime", "8888888");
		headMap.put("channelDate", "99999999");
		headMap.put("channel", "2222");
		headMap.put("sceneCode", "1111");
		headMap.put("legalCode", "branchLegal-01");
		headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
		headMap.put("flag", "1");
		headMap.put("tradeType", "2");
		headMap.put("version", "1.0.0");
		
		
		bodyMap.put("uniqueCstIdentity", "100010");
	}
	

	private static void updateShopCartMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) {
		headMap.put("serviceName", "updateShopCartAction");
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
		headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
		headMap.put("flag", "1");
		headMap.put("tradeType", "2");
		headMap.put("version", "1.0.0");
		
		bodyMap.put("cartStatus", "1");
		bodyMap.put("uniqueCstIdentity", "19910617180");
		List<Map<String,Object>> cartList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map1 = new HashMap<String, Object>();
		map1.put("productId", "01");
		map1.put("productName", "测试");
		map1.put("productDes", "测试1描述");
		map1.put("productPrice", "2.03");
		map1.put("productAmount", "1");
		cartList.add(map1);
		
		
		Map<String,Object> map2 = new HashMap<String, Object>();
		map2.put("productId", "02");
		map2.put("productName", "测试2");
		map2.put("productDes", "测试2描述");
		map2.put("productPrice", "4.03");
		map2.put("productAmount", "4");
		cartList.add(map2);
		
		
		bodyMap.put("cartList",cartList);
	}

	@SuppressWarnings("resource")
	public static void tcpclientMethodByte(String result) {
		try {
			result = EncryptDecryptUtilImpl.getInstance().md5Encode(result,mdKey)+result;
			
			System.out.println(result);
			byte[] msg = result.getBytes();
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress("10.10.10.131", 18888));
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
			String recieveValidatorCode = recieveMessage.substring(8,40);//报文传送过来的校验码
			String recieveBody = recieveMessage.substring(40).trim();
			
			if(!EncryptDecryptUtilImpl.getInstance().md5DecodeValid(recieveValidatorCode, recieveBody, mdKey)){
				System.out.println(CommErrorCodeConstants.CCCM0013+"===数据非法，被篡改");
			}
			
			System.out.println("=========over");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
