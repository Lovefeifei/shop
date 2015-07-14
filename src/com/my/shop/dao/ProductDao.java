package com.my.shop.dao;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.my.shop.model.Category;
import com.my.shop.model.Pager;
import com.my.shop.model.Product;
import com.my.shop.model.ShopDi;
import com.my.shop.model.ShopException;
import com.my.shop.model.SystemContext;

public class ProductDao extends BaseDao<Product> implements IProductDao {
	private ICategoryDao categoryDao;
	
	public ICategoryDao getCategoryDao() {
		return categoryDao;
	}
	@ShopDi
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public void delete(int id) {
		// TODO 如果用户购买了该商品就不能删除，该商品存在订单也不能删除，
		//如果可以删除商品的话需要删除商品的图片
		Product p = this.load(id);
		String img = p.getImg();
		super.delete(Product.class, id);
		//删除图片
		String path = SystemContext.getRealpath()+"/img/";
		File f = new File(path+img);
		f.delete();
	}

	public Product load(int id) {
		return super.load(Product.class, id);
	}

	public Pager<Product> find(int cid, String name,int status) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(cid>0) {
			params.put("cid", cid);
		}
		if(name!=null&&!"".equals(name.trim())) {
			params.put("name", "%"+name+"%");
		}
		if(status==1||status==-1) {
			params.put("status", status);
		}
		return super.find(Product.class, params);
	}

	public void addStock(int id, int num) {
		Product p = this.load(id);
		p.setStock(p.getStock()+num);
		this.update(p);
	}

	public void decreaseStock(int id, int num) {
		Product p = this.load(id);
		p.setStock(p.getStock()-num);
		this.update(p);
	}
	public void changeStatus(int id) {
		Product p = this.load(id);
		if(p.getStatus()==-1) {
			p.setStatus(1);
		} else {
			p.setStatus(-1);
		}
		this.update(p);
	}
	public void add(Product product, int cid)
	{
		Category c = categoryDao.load(cid);
		if(c==null) throw new ShopException("要添加产品的类别不存在!");
		product.setCategory(c);
		super.add(product);
		
	}
	public void update(Product product, int cid)
	{
		Category c = categoryDao.load(cid);
		if(c==null) throw new ShopException("要添加产品的类别不存在!");
		product.setCategory(c);
		super.update(product);
		
	}

}
