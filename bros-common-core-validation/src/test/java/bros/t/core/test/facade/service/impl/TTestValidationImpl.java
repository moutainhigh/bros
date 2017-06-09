package bros.t.core.test.facade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.t.core.test.facade.service.ITTestValidation;
/**
 * 
 * @ClassName: TTestValidationImpl 
 * @Description: 校验模板单元测试接口实现类
 * @author 何鹏
 * @date 2016年8月15日 下午2:15:11 
 * @version 1.0
 */
@Component(value="ttestValidation")
public class TTestValidationImpl implements ITTestValidation {

	@Override
	public ResponseEntity getAllBranchNotValidation(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> result1 = new HashMap<String, Object>();
		result1.put("branchCode", "1001");
		result1.put("branchName", "立水桥支行");
		list.add(result1);

		Map<String, Object> result2 = new HashMap<String, Object>();
		result2.put("branchCode", "1002");
		result2.put("branchName", "安德门支行");
		list.add(result2);

		ResponseEntity entity = new ResponseEntity();
		entity.setObject("list", list);
		return entity;
	}

	@Validation(value="p0100001")
	@Override
	public ResponseEntity getAllBranchValidation(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> result1 = new HashMap<String, Object>();
		result1.put("branchCode", "1001");
		result1.put("branchName", "立水桥支行");
		list.add(result1);

		Map<String, Object> result2 = new HashMap<String, Object>();
		result2.put("branchCode", "1002");
		result2.put("branchName", "安德门支行");
		list.add(result2);

		ResponseEntity entity = new ResponseEntity();
		entity.setObject("list", list);
		return entity;
	}

	@Validation(value="sssssssss")
	@Override
	public ResponseEntity getAllBranchValidationNotModel(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> result1 = new HashMap<String, Object>();
		result1.put("branchCode", "1001");
		result1.put("branchName", "立水桥支行");
		list.add(result1);

		Map<String, Object> result2 = new HashMap<String, Object>();
		result2.put("branchCode", "1002");
		result2.put("branchName", "安德门支行");
		list.add(result2);

		ResponseEntity entity = new ResponseEntity();
		entity.setObject("list", list);
		return entity;
	}

	@Validation(value="p0100001")
	@Override
	public ResponseEntity getAllBranchValidationManyParams(
			Map<String, Object> headMap, Map<String, Object> bodyMap,
			Map<String, Object> result) throws ServiceException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> result1 = new HashMap<String, Object>();
		result1.put("branchCode", "1001");
		result1.put("branchName", "立水桥支行");
		list.add(result1);

		Map<String, Object> result2 = new HashMap<String, Object>();
		result2.put("branchCode", "1002");
		result2.put("branchName", "安德门支行");
		list.add(result2);

		ResponseEntity entity = new ResponseEntity();
		entity.setObject("list", list);
		return entity;
	}

	@Validation(value="p0100001",paramName="headMap,bodyMap,aaaa")
	@Override
	public ResponseEntity getAllBranchValidationManyParams1(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> result1 = new HashMap<String, Object>();
		result1.put("branchCode", "1001");
		result1.put("branchName", "立水桥支行");
		list.add(result1);

		Map<String, Object> result2 = new HashMap<String, Object>();
		result2.put("branchCode", "1002");
		result2.put("branchName", "安德门支行");
		list.add(result2);

		ResponseEntity entity = new ResponseEntity();
		entity.setObject("list", list);
		return entity;
	}

}
