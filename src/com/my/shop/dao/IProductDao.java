package com.my.shop.dao;

import com.my.shop.model.Pager;
import com.my.shop.model.Product;

public interface IProductDao
{
	public void add(Product product, int cid);
	public void delete(int id);
	public void update(Product product,int cid);
	public Product load(int id);
	/**
	 * 可以通过商品类别和名称进行搜索
	 * 此时可以进行灵活的排序
	 * @param cid
	 * @param name
	 * @return
	 */
	public Pager<Product> find(int cid,String name,int status);
	
	public void addStock(int id,int num);
	/**
	 * 减少库存
	 * @param id
	 * @param num
	 */
	public void decreaseStock(int id,int num);
	/**
	 * 变更状态
	 * @param id
	 */
	public void changeStatus(int id);
}
