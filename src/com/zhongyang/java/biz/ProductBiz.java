package com.zhongyang.java.biz;

import java.util.List;

import com.zhongyang.java.pojo.Product;
import com.zhongyang.java.vo.ProductVo;

/**
 * 
* @Title: ProductBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:产品业务处理接口 
* @author 苏忠贺   
* @date 2015年12月17日 下午3:57:13 
* @version V1.0
 */
public interface ProductBiz {
	/**
	 * 
	* @Title: queryAllProducts 
	* @Description:查询所有产品 
	* @return List<Product>    返回类型 
	* @throws
	 */
	public List<ProductVo> queryAllProducts();
	/**
	 * 
	* @Title: queryProductById 
	* @Description：根据产品ID查询产品详情 
	* @return Product    返回类型 
	* @throws
	 */
	public Product queryProductById(String id);
}
