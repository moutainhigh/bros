package bros.provider.parent.bankmanage.mall.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.mall.service.IMallStyleService;

/**
 * 
 * @ClassName: MallStyleServiceImpl 
 * @Description: 商城样式实现类
 * @author gaoyongjing 
 * @date 2016年6月30日 下午15:35:28 
 * @version 1.0
 */
@Repository(value = "mallStyleService")
public class MallStyleServiceImpl implements IMallStyleService {
	/**
	 * 商城样式Log
	 */
	private static final  Logger logger = LoggerFactory.getLogger(MallStyleServiceImpl.class);
	 /**
	  * 数据库操作
	  */
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	
	/**
	 * 
	 * @Title: addMallStyleMethod
	 * @Description: 新增商城样式
	 * @param mallId 商城ID
	 * @param styleId 样式ID
	 * @return 
	 * @throws ServiceException
	 */
	@Override
	public void addMallStyleMethod(String mallId,String styleId) throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("mallId",mallId);// 商城ID
			param.put("styleId",styleId);//样式ID
			// 添加商城样式
			myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.prdmallstyle.insertMallStyle", param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s addMallStyleMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s addMallStyleMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加商城样式失败", e);
		}

	}

	/**
	 * 
	 * @Title: deleteMallStyleMethod
	 * @Description: 删除商城样式
	 * @param mallId 商城ID
	 * @param styleId 样式ID
	 * @return 
	 * @throws ServiceException
	 */
	@Override
	public void deleteMallStyleMethod(String mallId, String styleId) throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("mallId",mallId);// 商城ID
			param.put("styleId",styleId);//样式ID
			// 删除商城样式
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.prdmallstyle.deleteMallStyle", param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteMallStyleMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s deleteMallStyleMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除商城样式失败", e);
		}

	}

	/**
	 * 
	 * @Title: queryMallStyleMethod
	 * @Description: 查询商城样式
	 * @param mallId 商城ID
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return Map<String,Object> returnList商城样式列表 totalNum 总条数
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryMallStyleMethod(String mallId, int pageNo,
			int pageSize) throws ServiceException {
		Map<String, Object> paramOut = new HashMap<String,Object>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			List<Map<String,Object>> mallStyleList = new ArrayList<Map<String,Object>>();
			param.put("mallId",mallId);// 商城ID
			if(pageNo != 0){
				//分页查询 查询商城信息
				mallStyleList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdmallstyle.queryMallStyleByMallId", param, pageNo, pageSize);
			}else{
				//查询全部
				mallStyleList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdmallstyle.queryMallStyleByMallId", param);
			}
			//查询总条数
			int totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.prdmallstyle.queryMallStyleTotalNumByMallId", param);
			
			paramOut.put("returnList", mallStyleList);
			paramOut.put("totalNum", totalNum);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallStyleMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallStyleMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询商城样式失败", e);
		}
		return paramOut;
	}

	/**
	 * 
	 * @Title: queryMallStyleByStyleIdAndMallIdMethod
	 * @Description: 判断商城样式是否存在
	 * @param mallId 商城ID
	 * @param styleId 样式ID
	 * @return Map<String,Object> returnList商城样式列表
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryMallStyleByStyleIdAndMallIdMethod(String mallId,String styleId) throws ServiceException {
		Map<String,Object> mallStyleMap = new HashMap<String, Object>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("mallId",mallId);// 商城ID
			param.put("styleId",styleId);// 样式ID
			//查询商城样式
			mallStyleMap = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.prdmallstyle.queryMallStyleByStyleIdAndMallId", param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallStyleByStyleIdAndMallIdMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryMallStyleByStyleIdAndMallIdMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "判断商城样式是否存在失败", e);
		}
		return mallStyleMap;
	}
}
