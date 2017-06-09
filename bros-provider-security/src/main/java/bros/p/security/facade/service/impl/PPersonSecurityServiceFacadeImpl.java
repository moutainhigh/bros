package bros.p.security.facade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.constants.BaseParamsConstants;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.crypto.service.IEncryptAndDecryptService;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.common.core.util.DateUtil;
import bros.common.core.util.ValidateUtil;
import bros.p.security.facade.service.IPPersonSecurityServiceFacade;
import bros.provider.parent.customer.person.service.ITprCstChannelInfEntityService;
import bros.provider.parent.customer.person.service.ITprCstInfoEntityService;
import bros.provider.parent.security.person.question.service.IPersonPrivateQuestionAssembleService;
import bros.provider.parent.security.person.question.service.ITprCstPrivateQuestionEntityService;
import bros.provider.security.constants.SecurityErrorCodeConstants;

/**
 * 
 * @ClassName: PPersonSecurityServiceFacadeImpl 
 * @Description: 个人安全设置服务实现类
 * @author huangcanhui 
 * @date 2016年10月10日 下午3:42:57 
 * @version 1.0
 */
@Component("pPersonSecurityServiceFacade")
public class PPersonSecurityServiceFacadeImpl implements IPPersonSecurityServiceFacade {
	
	/**
	 * 个人客户基本信息实体服务
	 */
	@Autowired
	private ITprCstInfoEntityService tprCstInfoEntityService;
	
	/**
	 * 个人客户渠道信息实体服务
	 */
	@Autowired
	private ITprCstChannelInfEntityService tprCstChannelInfEntityService;

	/**
	 * 个人客户与私密问题关系实体服务
	 */
	@Autowired
	private ITprCstPrivateQuestionEntityService tprCstPrivateQuestionEntityService;
	
	/**
	 * 个人私密问题组合服务
	 */
	@Autowired
	private IPersonPrivateQuestionAssembleService personPrivateQuestionAssembleService;
	
	/**
	 * 数据加密解密服务
	 */
	@Autowired
	private IEncryptAndDecryptService encryptAndDecryptService;
	
	/**
	 * 修改登录密码
	 */
	@Validation(value="p0000213")
	@Override
	public ResponseEntity modifyPassword(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			//法人id
			String legalId = (String) (headMap.get(HeadParameterDefinitionConstants.REC_LEGALID)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
			//客户编号
			String cstNo = (String) (headMap.get(HeadParameterDefinitionConstants.REC_CSTNO)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));
			//渠道编号
			String channel = (String) (headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL));
			
			//原登录密码
			String oldPassword = (String) (bodyMap.get("oldPassword")==null?"":bodyMap.get("oldPassword"));
			//新登录密码
			String newPassword = (String) (bodyMap.get("newPassword")==null?"":bodyMap.get("newPassword"));
			//确认登录密码
			String cfmPassword = (String) (bodyMap.get("cfmPassword")==null?"":bodyMap.get("cfmPassword"));
			
			//比较两次输入密码
			if( (ValidateUtil.isEmpty(newPassword) 
					|| ValidateUtil.isEmpty(cfmPassword))
					&& !newPassword.equals(cfmPassword) )
			{
				throw new ServiceException(SecurityErrorCodeConstants.PSEY0001, "两次输入密码不一致");
			}
			
			//加密登录密码
			oldPassword = encryptAndDecryptService.encryptData(oldPassword);
			newPassword = encryptAndDecryptService.encryptData(newPassword);

			//根据法人ID+客户编号+渠道编号查询个人客户基本信息
			Map<String, Object> cstInfoMap = tprCstInfoEntityService.queryTprCstInfoByCstNo(legalId, cstNo, channel);
			if(null==cstInfoMap || cstInfoMap.size()<=0){
				throw new ServiceException(SecurityErrorCodeConstants.PSEY0002, "客户信息不存在");
			}
			
			//客户唯一标识
			String cstId = (String)cstInfoMap.get("cstId");
			//密码修改时间
			String pwdTime = DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT_DB);
			
			//修改登录密码
			int resultNum = tprCstChannelInfEntityService.updateTprCstChannelPwdByKey(cstId, channel, newPassword, oldPassword, pwdTime);
			if(resultNum!=1){
				throw new ServiceException(SecurityErrorCodeConstants.PSEY0003, "更新个人客户渠道信息异常");
			}
			
			return new ResponseEntity();
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(SecurityErrorCodeConstants.PSEY0004, "修改登录密码异常", e);
		}
	}
	
	/**
	 * 重置私密问题
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000214")
	@Override
	public ResponseEntity resetPrivateQuestion(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			//法人id
			String legalId = (String) (headMap.get(HeadParameterDefinitionConstants.REC_LEGALID)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
			//客户编号
			String cstNo = (String) (headMap.get(HeadParameterDefinitionConstants.REC_CSTNO)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));
			//渠道编号
			String channel = (String) (headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL));
			
			//私密问题列表
			List<Map<String, Object>> customerprivateQuestionList = (List<Map<String, Object>>) bodyMap.get("privateQuestionList");
			if(null==customerprivateQuestionList || customerprivateQuestionList.size()<=0){
				throw new ServiceException(SecurityErrorCodeConstants.PSEY0006, "私密问题列表不合法");
			}
			
			//根据法人ID+客户编号+渠道编号查询个人客户基本信息
			Map<String, Object> cstInfoMap = tprCstInfoEntityService.queryTprCstInfoByCstNo(legalId, cstNo, channel);
			if(null==cstInfoMap || cstInfoMap.size()<=0){
				throw new ServiceException(SecurityErrorCodeConstants.PSEY0002, "客户信息不存在");
			}
			
			//客户唯一标识
			String cstId = (String)cstInfoMap.get("cstId");
			
			List<Map<String, Object>> privateQuestionList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> cstPrivateQuestionList = new ArrayList<Map<String, Object>>();
			
			int seqNo = 0; //问题序号
			for(Map<String, Object> map : customerprivateQuestionList){
				String pqGroupId = (String) (map.get("pqGroupId")==null?"":map.get("pqGroupId")); //问题分组
				String pqId = (String) (map.get("questionNo")==null?"":map.get("questionNo")); //问题编号
				String question = (String) (map.get("question")==null?"":map.get("question")); //问题描述
				String answer = (String) (map.get("answer")==null?"":map.get("answer")); //问题答案
				
				//客户自定义私密问题列表
				if(ValidateUtil.isEmpty(pqId)){
					pqId = BaseUtil.createUUID();
					seqNo = seqNo + 1;
					Map<String, Object> privateQuestionMap = new HashMap<String, Object>();
					privateQuestionMap.put("pqId", pqId);
					privateQuestionMap.put("seqNo", seqNo);
					privateQuestionMap.put("question", question);
					privateQuestionMap.put("pqGroupId", pqGroupId);
					privateQuestionMap.put("languag", BaseParamsConstants.LANG_ZH_CN);
					privateQuestionList.add(privateQuestionMap);
				}

				//个人客户与私密问题关联列表
				Map<String, Object> cstPrivateQuestionMap = new HashMap<String, Object>();
				cstPrivateQuestionMap.put("pqId", pqId);
				cstPrivateQuestionMap.put("cstId", cstId);
				cstPrivateQuestionMap.put("answer", answer);
				cstPrivateQuestionList.add(cstPrivateQuestionMap);
			}
			
			//保存私密问题列表和关联列表（重置私密问题或注销客户时，需清除客户自定义私密问题和关联列表）
			personPrivateQuestionAssembleService.savePrivateQuestion(privateQuestionList, cstPrivateQuestionList);
			
			return new ResponseEntity();
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(SecurityErrorCodeConstants.PSEY0005, "重置私密问题异常", e);
		}
	}

}
