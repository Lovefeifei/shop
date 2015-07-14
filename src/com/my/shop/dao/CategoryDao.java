package com.my.shop.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.my.shop.model.Category;
import com.my.shop.model.ShopException;

public class CategoryDao extends BaseDao<Category> implements ICategoryDao
{
	

	public void add(Category category)
	{
		Category cg = this.load(category.getId());
		if(cg!=null)
		{
			throw new ShopException("要添加的用户已经存在");
		}
		else
		{
			super.add(category);
		}
		
	}

	public void delete(int id) {
		//TODO 需要判断该类别是否还有商品存在，如果有不能删除
		super.delete(Category.class, id);
	}

	public void update(Category category) {
		super.update(category);
	}

	public List<Category> list(String name) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(name!=null&&!"".equals(name.trim()))
			params.put("name", "%"+name+"%");
		return super.list(Category.class, params);
	}

	public Category load(int id) {
		return super.load(Category.class, id);
	}

	public List<Category> list() {
		return list(null);
	}
	
}
