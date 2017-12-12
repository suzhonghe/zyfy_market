package com.zhongyang.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.ProductBiz;
import com.zhongyang.java.pojo.Product;
import com.zhongyang.java.vo.ProductVo;
/**
 * 
* @Title: ProductController.java 
* @Package com.zhongyang.java.controller 
* @Description: 产品控制器 
* @author 苏忠贺   
* @date 2015年12月21日 上午10:48:32 
* @version V1.0
 */
@Controller
public class ProductController extends BaseController{
	
	@Autowired
	private ProductBiz productBiz;
	
	/**
	 * 
	* @Title: queryAllProducts 
	* @Description:查询所有产品 
	* @return List<Product>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/queryAllProducts")
	public @ResponseBody List<ProductVo> queryAllProducts(){
		return productBiz.queryAllProducts();
	}; 

	/**
	 * 
	* @Title: queryProductById 
	* @Description:根据产品ID查询详情
	* @return Product    返回类型 
	* @throws
	 */
	@RequestMapping(value="/queryProductById")
	public @ResponseBody Product queryProductById(String id){
		return productBiz.queryProductById(id);
	}
}
