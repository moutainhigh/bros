package bros.pre.common.route.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONNull;
import bros.common.core.comm.route.service.impl.ClientTransRouteServiceImpl;
import bros.common.core.constants.BaseParamsConstants;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.exception.ServiceException;
import bros.common.core.generator.flow.FlowGenerator;
import bros.common.core.mvc.constants.CoreMvcParamsConstants;
import bros.common.core.session.manager.SessionManager;
import bros.common.core.util.DateUtil;
import bros.common.core.util.RelaySerialNumberUtil;
import bros.common.core.util.TokenUtil;
import bros.common.core.util.ValidateUtil;
import bros.pre.common.constants.CommonErrorCodeConstants;
import bros.pre.common.constants.CommonParamsConstants;
/**
 * 
 * @ClassName: ClientRouteTransServiceImpl 
 * @Description: 闁告挸绉堕悿鍡樼閵堝棙顫滈柛娆愬灴閿熸垝鑳剁划鐑樼▔閿熻姤寰勯崟顓熷��
 * @author 濞达絾娲熺粻锟�
 * @date 2016妤犵儑鎷�7闁哄牞鎷�13闁哄喛鎷� 濞戞挸锕ゅ畷锟�11:22:11 
 * @version 1.0
 */
public class ClientRouteTransServiceImpl extends ClientTransRouteServiceImpl {
	/**
	 * 闁规亽鍎辨慨蹇撱�掗悩璁冲
	 */
	private String recChannel;
	/**
	 * 闁告瑦鍨奸幑锝囧寲閼姐倗鍩犵紓鍌涚墪瑜帮拷
	 */
	private String sendChannel;
	/**
	 * 闁汇垻鍠愰崹姘跺礂閵娿儳婀版繛缈犵劍閹稿宕ｉ敓锟�6濞达絽绉疯棢闁稿繐鎳忛弳鐔煎箲閿燂拷
	 */
	private String fillRightStr;
	/**
	 * 婵炴挾濞�娴滃墽绮旀担瑙勭畳濞村吋淇洪惁鐣岀不閿涘嫭鍊為柛锝忔嫹
	 */
	private SessionManager sessionManager;
	/**
	 * 闁哄牆绉存慨鐔烘嫬閸愵亝鏆忛柡鍌氭贡闁绱掗悜鐡�
	 */
	private String consumerId;
	/**
	 * 婵炵繝鐒﹂幐澶愭偨閻旂鐏囬柛锝忔嫹
	 */
	private FlowGenerator flowGenerator;
	/**
	 * token缂佺媴绱曢幃濠傤啅閵夈儱寰�
	 */
	private TokenUtil tokenUtil;
	
	public String getRecChannel() {
		return recChannel;
	}
	public void setRecChannel(String recChannel) {
		this.recChannel = recChannel;
	}
	public String getSendChannel() {
		return sendChannel;
	}
	public void setSendChannel(String sendChannel) {
		this.sendChannel = sendChannel;
	}
	public String getFillRightStr() {
		return fillRightStr;
	}
	public void setFillRightStr(String fillRightStr) {
		this.fillRightStr = fillRightStr;
	}
	public FlowGenerator getFlowGenerator() {
		return flowGenerator;
	}
	public void setFlowGenerator(FlowGenerator flowGenerator) {
		this.flowGenerator = flowGenerator;
	}
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public SessionManager getSessionManager() {
		return sessionManager;
	}
	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
	public TokenUtil getTokenUtil() {
		return tokenUtil;
	}
	public void setTokenUtil(TokenUtil tokenUtil) {
		this.tokenUtil = tokenUtil;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> route(Map<String, Object> reqHeadMap,
			Map<String, Object> reqBodyMap) throws ServiceException {
		try{
			//闁兼儳鍢茶ぐ鍥╃矓娴ｈ绠掑ù鍏间亢閻︿粙寮悧鍫濈ウ
			String token = (String) (reqHeadMap.get(HeadParameterDefinitionConstants.PRE_TOKEN)==null?"":reqHeadMap.get(HeadParameterDefinitionConstants.PRE_TOKEN));
			Map<String,Object> privateSessionMap = null;
			if(!ValidateUtil.isEmpty(token)){
				String sessionId = tokenUtil.getRedisSessionId(token);
				privateSessionMap = sessionManager.getSession(sessionId);
			}
			
			
			//闁告艾楠歌ぐ瀛樻交閺傛寧绀�40濞达絽绉电粊锕�顫濋弶鎴濆▏
			String backEndSeqNo = (String) (reqHeadMap.get(HeadParameterDefinitionConstants.SEN_BACKENDSEQNO)==null?"":reqHeadMap.get(HeadParameterDefinitionConstants.SEN_BACKENDSEQNO));
			String taxi = (String) (reqHeadMap.get(HeadParameterDefinitionConstants.PRE_TAXI)==null?"":reqHeadMap.get(HeadParameterDefinitionConstants.PRE_TAXI));//缂佸鍠愰崺娑氱磽閺嶎偆鍨�
			String sceneCode = (String) (reqHeadMap.get(HeadParameterDefinitionConstants.REC_SCENECODE)==null?"":reqHeadMap.get(HeadParameterDefinitionConstants.REC_SCENECODE));//闁革妇鍎ゅ▍娆戠磽閺嵮冨▏
			String stepCode = (String) (reqHeadMap.get(HeadParameterDefinitionConstants.PRE_STEP_CODE)==null?"":reqHeadMap.get(HeadParameterDefinitionConstants.PRE_STEP_CODE));//闁革妇鍎ゅ▍娆忣潰閵夆晩锟藉啰绱撻弽顐ゅ灣
			
			//30闁稿繈鍔岄惇顒�霉娴ｈ瀵滈柛娆欐嫹
			String globaleSeqNo = "";
			if(ValidateUtil.isEmpty(backEndSeqNo)){//濠碘�冲�归悘锟�40濞达絽绉电粊锕�顫濋弶鎴濆▏濞戞捁娅ｉ埞鏍儍閸曨偄顕ч梻鍥锋嫹閻熸洑鑳堕弫鎾诲箣閿燂拷30濞达絽绉撮崣蹇曚沪閿熻棄霉娴ｈ瀵滈柛娆忓殩缁辨繈宕敓锟�40濞达絽绉电粊锕�顫濋弶鎴濆▏
				globaleSeqNo = flowGenerator.getGlobalSeqNo(taxi, sendChannel, sceneCode, stepCode);
				backEndSeqNo = globaleSeqNo+recChannel+fillRightStr;
			}else{
				globaleSeqNo = backEndSeqNo.substring(0,30);//30濞达絽绉撮崣蹇曚沪閿熻棄霉娴ｈ瀵滈柛娆欐嫹
				backEndSeqNo = RelaySerialNumberUtil.getRelaySerialNumber(backEndSeqNo, recChannel);
			}
			//40濞达絽绉冲锕傚及閹惧銈︽慨妯绘綑瑜帮拷
			reqHeadMap.put(HeadParameterDefinitionConstants.REC_GLOBALSEQNO, globaleSeqNo);
			//30濞达絽绉撮崣蹇曚沪閿熻棄霉娴ｈ瀵滈柛娆欐嫹
			reqHeadMap.put(HeadParameterDefinitionConstants.REC_TRANSEQNO, backEndSeqNo);
			
			//缂侇垵宕电划鐑樼閵堝棙顫滈柡鍐﹀劜濠�锟�
			reqHeadMap.put(HeadParameterDefinitionConstants.REC_TRANDATE, DateUtil.getServerTime(DateUtil.DEFAULT_DATE_FORMAT));
			//缂侇垵宕电划鐑樼閵堝棙顫滈柡鍐ㄧ埣濡拷
			reqHeadMap.put(HeadParameterDefinitionConstants.REC_TRANTIME, DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT_HMS));
			//闁哄牆绉存慨鐔烘嫬閸愵亝鏆忛柡鍌氭贡闁绱掗悞娌�
			reqHeadMap.put(HeadParameterDefinitionConstants.REC_CONSUMERID, consumerId);
			//婵炴挾濞�娴滃墽绱撻弽顐ゅ灣
			reqHeadMap.put(HeadParameterDefinitionConstants.REC_CHANNEL, sendChannel);
			
			
			if(privateSessionMap != null && privateSessionMap.size()>0){//閻庢稒锚濠�顏堝礂椤掞拷閸欙繝寮悧鍫濈ウ闁挎稑鐭傚〒鍓佹啺娴ｇ晫绠婚悶娑樿嫰椤︹晠鎮堕敓锟�
				setNotMustInputToHeadMap(privateSessionMap,reqHeadMap);//闁硅翰鍎查弸鍐╁緞閹绢喗濮滈煫鍥ф嚀缁额參寮悧鍫濈ウ閺夆晜绋栭、鎴犵磼閸曨噮妫�
			}else{//sessionId濞戞挸绉撮悺銊╁捶椤帞绀夐悶娑栧妿閵囨艾鈻介埄鍐╃畳闁谎嗩嚙缂嶅秹鏁嶅畝鍕粯閻熸洑娴囬鏇犵磾椤旈箖鍏囩紓浣哄枔妤犲洭寮甸悜妯硷拷顖炲矗瀹勭増瀚茬紒顖濆吹缁櫣鐥閻撴牠宕ㄥΟ鍝勫▏
				reqHeadMap.put(HeadParameterDefinitionConstants.REC_BRANCHID ,CommonParamsConstants.SYSTEM_BRANCH);
				reqHeadMap.put(HeadParameterDefinitionConstants.REC_TRANTELLERNO ,CommonParamsConstants.SYSTEM_TELLERNO);
			}
			
			reqHeadMap.remove(HeadParameterDefinitionConstants.PRE_TOKEN);//閺夆晛娲﹂幎銈咁嚗閿熶粙宕ユ惔鈥抽叡闁告瑦鍨块敓鎴掕兌濞堟唸oken濞ｅ洠鍓濇导锟�
			
			Map<String,Object> result = super.route(reqHeadMap, reqBodyMap);
			
			//闁告瑦鐗犻弫濠勬嫚椤栨粎鍨抽梺鎸庣懆椤曘倖绌遍埄鍐х礀
			Map<String, Object> headMap = (Map<String, Object>) result.get(CoreMvcParamsConstants.RSP_DATA_HEAD_MAP);
//		shaoxu 2017/06/07	String returnCode = (String) headMap.get(HeadParameterDefinitionConstants.SEN_RETURNCODE);
			String returnCode = "00000000";
			if(!BaseParamsConstants.TRADE_SUCCESS_FLAG.equals(returnCode)){//閺夆晜鏌ㄥú鏍儘娓氾拷濞碱亪骞嬮幇顒�顫�
				String returnMsg = (String) headMap.get(HeadParameterDefinitionConstants.SEN_RETURNMSG);
				throw new ServiceException(returnCode,returnMsg,result);
			}
			
			headMap.put("returnCode", returnCode); //shaoxu 2017/06/07
			
			return mapFilterJsonNull(result);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(CommonErrorCodeConstants.PCOO0001,"濞存嚎鍊栧Σ妤冪磼閸曨噮妫呭鎯扮簿鐟欙拷",e);
		}
	}
	
	/**
	 * 
	 * @Title: setNotMustInputToHeadMap 
	 * @Description: 濞寸姴娴风槐锔撅拷娑櫭奸懙鎴︽嚔瀹勬澘绲块柡浣哄瀹撲焦娼诲☉婊庢斀閻忓繋娴囬ˉ濠囧礆閻楀牆袚闁哄倸娲ら妵鏃堟晬閸喎袚闁哄倸娲ら妵鏃堟閻愯尙绠戦弶鍫熸尰閺嗙喖骞戦鍡欑
	 * @param privateSessionMap	session濞戞搩鍘惧▓鎴﹀极閻楀牆绁�
	 * @param reqHeadMap 閻犲洭鏀遍惇浼村箮閵夛附鐎璁规嫹
	 */
	private void setNotMustInputToHeadMap(Map<String,Object> privateSessionMap,Map<String,Object> reqHeadMap){
		//濞存嚎鍊栧Σ妤呭嫉閻戞锟斤拷
		String branchId = (String) (privateSessionMap.get(HeadParameterDefinitionConstants.REC_BRANCHID)==null?"":privateSessionMap.get(HeadParameterDefinitionConstants.REC_BRANCHID));
		//濞存嚎鍊栧Σ妤呭蓟濠婂啯鍠�
		String tellerNo = (String) (privateSessionMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO)==null?"":privateSessionMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO));
		
		//閻庡箍鍨洪崺娑㈠矗閿燂拷
		String cstNo = (String) (privateSessionMap.get(HeadParameterDefinitionConstants.REC_CSTNO)==null?"":privateSessionMap.get(HeadParameterDefinitionConstants.REC_CSTNO));
		//闁瑰灝绉崇紞鏃堝川濡偐妞介柛娆欐嫹
		String operatorNo = (String) (privateSessionMap.get(HeadParameterDefinitionConstants.REC_OPERATORNO)==null?"":privateSessionMap.get(HeadParameterDefinitionConstants.REC_OPERATORNO));
		//婵炲娲戝Ч澶岀磽閺嵮冨▏
		String legalCode = (String) (privateSessionMap.get(HeadParameterDefinitionConstants.REC_LEGALCODE)==null?"":privateSessionMap.get(HeadParameterDefinitionConstants.REC_LEGALCODE));
		//婵炲娲戝Ч濉眃
		String legalId = (String) (privateSessionMap.get(HeadParameterDefinitionConstants.REC_LEGALID)==null?"":privateSessionMap.get(HeadParameterDefinitionConstants.REC_LEGALID));	
		//婵炵繝鑳堕埢鑲╂嫚闁垮婀撮柛娆欐嫹
		String customerNo = (String) (privateSessionMap.get(HeadParameterDefinitionConstants.REC_CUSTOMERNO)==null?"":privateSessionMap.get(HeadParameterDefinitionConstants.REC_CUSTOMERNO));
		//閻庡箍鍨洪崺娑氭嫚闁垮婀撮柛娆欐嫹
		String frontChlCode = (String) (privateSessionMap.get(HeadParameterDefinitionConstants.REC_FRONTCHLCODE)==null?"":privateSessionMap.get(HeadParameterDefinitionConstants.REC_FRONTCHLCODE));
		
		setKeyAndValueJudge(HeadParameterDefinitionConstants.REC_BRANCHID ,branchId,reqHeadMap);
		setKeyAndValueJudge(HeadParameterDefinitionConstants.REC_TRANTELLERNO ,tellerNo,reqHeadMap);
		
		setKeyAndValue(HeadParameterDefinitionConstants.REC_CSTNO,cstNo,reqHeadMap);
		setKeyAndValue(HeadParameterDefinitionConstants.REC_OPERATORNO,operatorNo,reqHeadMap);
		setKeyAndValue(HeadParameterDefinitionConstants.REC_LEGALID,legalId,reqHeadMap);
		setKeyAndValue(HeadParameterDefinitionConstants.REC_LEGALCODE,legalCode,reqHeadMap);
		setKeyAndValue(HeadParameterDefinitionConstants.REC_CUSTOMERNO,customerNo,reqHeadMap);
		setKeyAndValue(HeadParameterDefinitionConstants.REC_FRONTCHLCODE,frontChlCode,reqHeadMap);
		
		
	}
	
	/**
	 * 
	 * @Title: setKeyAndValueJudge 
	 * @Description: 闁稿繐鐗嗛崹浠嬪棘椤撶喎袚闁哄倸娲ら妵鏃�绋夐鐔感﹂柛姘剧畳椤旀洜绱旈鑽ゅ晩闁轰胶澧楀畵渚�鏁嶇仦绛嬫搐闁哄绮忛鏇犵磾椤旇崵鍟婇悘蹇氫含濞插潡骞掗妷銉ョ岛闂侇偂绶ょ槐婵嬪触閿曪拷閸垳浜搁崣妯肩煠缂傚倹鎸搁悺銊︾▔椤撯�崇闁告瑦鐗楅弳鐔煎箲閿燂拷
	 * @param key
	 * @param value
	 * @param reqHeadMap
	 */
	private void setKeyAndValueJudge(String key ,String value,Map<String,Object> reqHeadMap){
		String headValue = (String) (reqHeadMap.get(key)==null?"":reqHeadMap.get(key));
		if(ValidateUtil.isEmpty(headValue)){
			setKeyAndValue(key ,value,reqHeadMap);
		}
	}
	
	/**
	 * 
	 * @Title: setKeyAndValue 
	 * @Description: 閻忓繐妫欓弳鐔煎箲椤旂偓绾柟鎭掑劚閻°劑寮ㄩ幆褍鐓傞柟韬插劜閺嬪啯寰勭紙鐘哄幀
	 * @param key
	 * @param value
	 * @param reqHeadMap
	 */
	private void setKeyAndValue(String key ,String value,Map<String,Object> reqHeadMap){
		if(!ValidateUtil.isEmpty(value)){
			reqHeadMap.put(key, value);
		}
	}
	
	/**
	 * 
	 * @Title: mapFilterJsonNull 
	 * @Description: 闁告绮敮锟絤ap濞戞搩鍘惧▓鎲倁ll闁告粌鐡癝ONNull
	 * @param result
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Object> mapFilterJsonNull(Map<String,Object> result){
		Map<String,Object> mapResult = new HashMap<String, Object>();
		if(result != null && result.size()>0){
			Set<String> set = result.keySet();
			for(Iterator<String> it = set.iterator();it.hasNext();){
				String key = it.next();
				Object obj = result.get(key);
				if(obj == null || obj instanceof JSONNull){
					
				}else if(obj instanceof Map){
					mapResult.put(key, mapFilterJsonNull((Map<String, Object>) obj));
				}else if(obj instanceof List){
					mapResult.put(key, listFilterJsonNull((List<Object>) obj));
				}else{
					mapResult.put(key, obj);
				}
			}
		}
		return mapResult;
	}

	/**
	 * 
	 * @Title: listFilterJsonNull 
	 * @Description: 闁告绮敮锟絣ist濞戞搩鍘惧▓鎲倁ll闁告粌鐡癝ONNull
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Object> listFilterJsonNull(List<Object> list){
		List<Object> resultList = new ArrayList<Object>();
		if(list != null && list.size()>0){
			for(Object obj : list){
				if(obj ==null || obj instanceof JSONNull){
					
				}else if(obj instanceof Map){
					resultList.add(mapFilterJsonNull((Map<String, Object>) obj));
				}else if(obj instanceof List){
					resultList.add(listFilterJsonNull((List<Object>) obj));
				}else{
					resultList.add(obj);
				}
			}
		}
		return resultList;
	}
	
}
