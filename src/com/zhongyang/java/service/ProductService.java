package com.zhongyang.java.service;
import java.util.List;

import com.zhongyang.java.pojo.Product;


/**
 * 
* @Title: LoanRequestDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 产品管理dao接口
* @author 苏忠贺   
* @date 2015年12月1日 下午3:27:25 
* @version V1.0
 */
public interface ProductService {
	/**
	 * 
	* @Title: selectAllProduct 
	* @Description:查询所有产品 
	* @return void    返回类型 
	* @throws
	 */
	public List<Product> queryAllProduct()throws Exception;
	/**
	 * 
	* @Title: selectProductById 
	* @Description:根据产品ID查询详情 
	* @return Product    返回类型 
	* @throws
	 */
	public Product queryProductById(String id)throws Exception;
}
