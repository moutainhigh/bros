package bros.p.security.facade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.constants.BaseParamsConstants;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.random.RandomGenerator;
import bros.p.security.facade.service.IPPersonPrivateQuestionServiceFacade;
import bros.provider.parent.customer.person.service.ITprCstInfoEntityService;
import bros.provider.parent.security.bank.question.service.IBmaPrivateQuestionGroupEntityService;
import bros.provider.parent.security.person.question.service.ITprCstPrivateQuestionEntityService;
import bros.provider.parent.security.person.question.service.ITprPrivateQuestionEntityService;
import bros.provider.security.constants.SecurityErrorCodeConstants;

/**
 * 
 * @ClassName: PPersonPrivateQuestionServiceFacadeImpl 
 * @Description: 个人私密问题服务实现类
 * @author huangcanhui 
 * @date 2016年10月10日 下午9:52:57 
 * @version 1.0
 */
@Component("pPersonPrivateQuestionServiceFacade")
public class PPersonPrivateQuestionServiceFacadeImpl implements IPPersonPrivateQuestionServiceFacade {
	
	/**
	 * 私密问题分组实体服务
	 */
	@Autowired
	private IBmaPrivateQuestionGroupEntityService bmaPrivateQuestionGroupEntityService;
	
	/**
	 * 个人私密问题实体服务
	 */
	@Autowired
	private ITprPrivateQuestionEntityService tprPrivateQuestionEntityService;
	
	/**
	 * 个人客户与私密问题关系实体服务
	 */
	@Autowired
	private ITprCstPrivateQuestionEntityService tprCstPrivateQuestionEntityService;
	
	/**
	 * 个人客户基本信息实体服务
	 */
	@Autowired
	private ITprCstInfoEntityService tprCstInfoEntityService;
	
	/**
	 * 查询个人私密问题分组列表
	 */
	@Override
	public ResponseEntity queryPrivateQuestionGroupList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			List<Map<String, Object>> privateQuestionGroupList = bmaPrivateQuestionGroupEntityService.queryBmaPrivateQuestionGroupList();
			Map<String,Object> returnMap = new HashMap<String, Object>();
			//组装返回数据
			returnMap.put("privateQuestionGroupList", privateQuestionGroupList);
			return new ResponseEntity(returnMap);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(SecurityErrorCodeConstants.PSEY0007, "查询私密问题分组列表异常", e);
		}
	}
	
	/**
     * 筛选个人私密问题
     */
	@Override
    public ResponseEntity filterPrivateQuestion(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
    	try{
    		//法人id
			String legalId = (String) (headMap.get(HeadParameterDefinitionConstants.REC_LEGALID)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
			//客户编号
			String cstNo = (String) (headMap.get(HeadParameterDefinitionConstants.REC_CSTNO)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));
			//渠道编号
			String channel = (String) (headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL));
			
			//根据法人ID+客户编号+渠道编号查询个人客户基本信息
			Map<String, Object> cstInfoMap = tprCstInfoEntityService.queryTprCstInfoByCstNo(legalId, cstNo, channel);
			if(null==cstInfoMap || cstInfoMap.size()<=0){
				throw new ServiceException(SecurityErrorCodeConstants.PSEY0002, "客户信息不存在");
			}
			
			//客户唯一标识
			String cstId = (String)cstInfoMap.get("cstId");
			
			//根据客户标识查询私密问题关联列表
			List<Map<String, Object>> cstPrivateQuestionList = tprCstPrivateQuestionEntityService.queryTprCstPrivateQuestionListByCstId(cstId);
			if(null==cstPrivateQuestionList || cstPrivateQuestionList.size()<=0){
				throw new ServiceException(SecurityErrorCodeConstants.PSEY0008, "客户未设置私密问题");
			}
			
			//组装查询条件
			List<String> pqIdList = new ArrayList<String>();
			for(Map<String, Object> map : cstPrivateQuestionList){
				String pqId = (String)map.get("pqId");
				pqIdList.add(pqId);
			}
    		
			//根据问题编号查询私密问题列表
			List<Map<String, Object>> privateQuestionList = tprPrivateQuestionEntityService.queryTprPrivateQuestionListByPqId(pqIdList);
			if(null==privateQuestionList || privateQuestionList.size()<=0){
				throw new ServiceException(SecurityErrorCodeConstants.PSEY0009, "私密问题不存在");
			}
			//筛选私密问题
			int index = RandomGenerator.getSecureRendomNum(privateQuestionList.size());
			Map<String, Object> privateQuestionMap = privateQuestionList.get(index);
			String questionNo = (String)privateQuestionMap.get("pqId");
			String question = (String)privateQuestionMap.get("question");
			
			Map<String,Object> returnMap = new HashMap<String, Object>();
			//组装返回数据
			returnMap.put("questionNo", questionNo);
			returnMap.put("question", question);
			return new ResponseEntity(returnMap);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(SecurityErrorCodeConstants.PSEY0010, "查询私密问题异常", e);
		}
    }
    
    /**
     * 根据分组编号查询个人私密问题列表
     */
	@Override
    public ResponseEntity queryPrivateQuestionList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
    	try{
    		//分组编号
			String pqGroupId = (String) (bodyMap.get("pqGroupId")==null?"":bodyMap.get("pqGroupId"));
			//语种
			String languag = (String) (bodyMap.get("languag")==null ? BaseParamsConstants.LANG_ZH_CN:bodyMap.get("languag"));
			
			//根据分组编号+语种查询私密问题列表
			List<Map<String, Object>> pQuestionList = tprPrivateQuestionEntityService.queryTprPrivateQuestionListByPqGroupId(pqGroupId, languag);
			
			List<Map<String, Object>> privateQuestionList = new ArrayList<Map<String, Object>>();
			if(null!=pQuestionList && pQuestionList.size()>0){
				for(Map<String, Object> pQuestionMap : pQuestionList){
					String questionNo = (String)pQuestionMap.get("pqId");
					int seqNo = ((Integer)pQuestionMap.get("seqNo")).intValue();
					String question = (String)pQuestionMap.get("question");
					
					Map<String, Object> privateQuestionMap = new HashMap<String, Object>();
					privateQuestionMap.put("questionNo", questionNo);
					privateQuestionMap.put("seqNo", seqNo);
					privateQuestionMap.put("question", question);
					privateQuestionList.add(privateQuestionMap);
				}
			}
			
			Map<String,Object> returnMap = new HashMap<String, Object>();
			returnMap.put("privateQuestionList", privateQuestionList);
			return new ResponseEntity(returnMap);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(SecurityErrorCodeConstants.PSEY0010, "查询私密问题异常", e);
		}
    }
	
}
