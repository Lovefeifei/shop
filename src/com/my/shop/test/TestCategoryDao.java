package com.my.shop.test;

import com.my.shop.dao.ICategoryDao;
import com.my.shop.model.Category;
import com.my.shop.model.ShopDi;

import java.util.List;

import org.junit.Test;

public class TestCategoryDao extends BaseTest{
	private ICategoryDao categoryDao;

	@ShopDi
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	@Test
	public void testAdd() {
		Category category = new Category();
		category.setName("夏季服装");
		categoryDao.add(category);
		category = new Category();
		category.setName("冬季服装");
		categoryDao.add(category);
		category = new Category();
		category.setName("家电");
		categoryDao.add(category);
		category = new Category();
		category.setName("武器");
		categoryDao.add(category);
		category = new Category();
		category.setName("大家电");
		categoryDao.add(category);
	}
	
	@Test
	public void testLoad() {
		Category category = categoryDao.load(1);
		System.out.println(category.getName());
	}
	
	@Test
	public void testDelete() {
		categoryDao.delete(2);
	}
	
	@Test
	public void testUpdate() {
		Category c = categoryDao.load(2);
		c.setName("春季服装");
		categoryDao.update(c);
	}
	
	@Test
	public void testList() {
		List<Category> list = categoryDao.list();
		for(Category c:list) {
			System.out.println(c.getId()+","+c.getName());
		}
	}
	
	@Test
	public void testListCon() {
		List<Category> list = categoryDao.list("家");
		for(Category c:list) {
			System.out.println(c.getId()+","+c.getName());
		}
	}
}
