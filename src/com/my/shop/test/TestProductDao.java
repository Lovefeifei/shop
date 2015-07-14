package com.my.shop.test;

import org.junit.Test;

import com.my.shop.dao.IProductDao;
import com.my.shop.model.Product;
import com.my.shop.model.ShopDi;


public class TestProductDao extends BaseTest
{
	private IProductDao productDao;

	public IProductDao getProductDao()
	{
		return productDao;
	}
	@ShopDi("productDao")
	public void setProductDao(IProductDao productDao)
	{
		this.productDao = productDao;
	}
	
	@Test
	public void testAdd() 
	{
		Product p = new Product();
		p.setName("狒狒2");
		p.setPrice(200000);
		p.setIntro("这是个无价之物2");
		p.setImg("这是个空图片2");
		p.setStatus(1);
		p.setStock(1);
		productDao.add(p , 1);
		
	}
	
}
