package com.zhongyang.java.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.ProductBiz;
import com.zhongyang.java.pojo.Product;
import com.zhongyang.java.service.ProductService;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.vo.ProductVo;

/**
 * 
* @Title: ProductBizImpl.java 
* @Package com.zhongyang.java.biz.impl 
* @Description: 产品业务处理接口实现
* @author 苏忠贺   
* @date 2015年12月17日 下午3:59:00 
* @version V1.0
 */
@Service
public class ProductBizImpl implements ProductBiz{
	
	private static Logger logger=Logger.getLogger(ProductBizImpl.class);
	
	@Autowired
	private ProductService productService;
	
	@Override
	public List<ProductVo> queryAllProducts() {
		try {
			List<ProductVo>productVos=new ArrayList<ProductVo>();
			List<Product> products = productService.queryAllProduct();
			ProductVo prov=new ProductVo();
			prov.setId("");
			prov.setName("全部");
			productVos.add(prov);
			if(products==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询数据出现问题");
			}
			for (Product product : products) {
				ProductVo pv=new ProductVo();
				pv.setId(product.getId());
				pv.setName(product.getName());
				productVos.add(pv);
			}
			return productVos;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public Product queryProductById(String id) {
		try {
			if(id==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未接收到参数");
			}
			return productService.queryProductById(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询数据出现问题");
		}
	}

}
