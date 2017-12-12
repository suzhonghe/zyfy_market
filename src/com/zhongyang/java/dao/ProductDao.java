package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.Product;

/**
 * 
* @Title: ProductDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 产品DAO 
* @author 苏忠贺   
* @date 2015年12月16日 下午3:53:17 
* @version V1.0
 */
public interface ProductDao {
	
	/**
	 * 
	* @Title: selectAllProduct 
	* @Description:查询所有产品 
	* @return void    返回类型 
	* @throws
	 */
	public List<Product> selectAllProduct()throws Exception;
	
	/**
	 * 
	* @Title: selectProductById 
	* @Description:根据产品ID查询详情
	* @return Product    返回类型 
	* @throws
	 */
	public Product selectProductById(String id)throws Exception;
}
