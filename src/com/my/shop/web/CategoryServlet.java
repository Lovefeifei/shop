package com.my.shop.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.shop.dao.ICategoryDao;
import com.my.shop.model.Category;
import com.my.shop.model.ShopDi;
import com.my.shop.model.ShopException;
import com.my.shop.util.RequestUtil;



public class CategoryServlet extends BaseServlet
{
	private ICategoryDao categoryDao;

	public ICategoryDao getCategoryDao()
	{
		return categoryDao;
	}
	@ShopDi
	public void setCategoryDao(ICategoryDao categoryDao)
	{
		this.categoryDao = categoryDao;
	}
	
	public String list(HttpServletRequest req,HttpServletResponse resp) {
		String name = req.getParameter("name");
		List<Category> list = categoryDao.list(name);
		req.setAttribute("cs" , list);
		return "category/list.jsp";
	}

	public String addInput(HttpServletRequest req,HttpServletResponse resp) {
		return "category/addInput.jsp";
	}
	
	public String add(HttpServletRequest req,HttpServletResponse resp) {
		Category category = (Category)RequestUtil.setParam(Category.class,req);
		boolean isValidate = RequestUtil.Validate(Category.class,req);
		if(!isValidate)
		{
			return "category/addInput.jsp";
		}
		categoryDao.add(category);
		return redirPath+"CategoryServlet?method=list";
	}
	
	public String show(HttpServletRequest req,HttpServletResponse resp) {
		Category category = categoryDao.load(Integer.parseInt(req.getParameter("id")));
		req.setAttribute("category" , category);
		return "category/show.jsp";
	}
	
	public String delete(HttpServletRequest req,HttpServletResponse resp) {
		try {
			categoryDao.delete(Integer.parseInt(req.getParameter("id")));
		} catch (ShopException e) {
			req.setAttribute("errorMsg", e.getMessage());
			return "inc/error.jsp";
		}
		return redirPath+"CategoryServlet?method=list";
	}
	
	public String updateInput(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("category",
				categoryDao.load(Integer.parseInt(req.getParameter("id"))));
		return "category/updateInput.jsp";
	}
	
	public String update(HttpServletRequest req,HttpServletResponse resp) {
		Category cg = (Category)RequestUtil.setParam(Category.class,req);
		if(!RequestUtil.Validate(Category.class, req)) {
			return "category/updateInput.jsp";
		}
		Category category = categoryDao.load(Integer.parseInt(req.getParameter("id")));
		category.setName(cg.getName());
		categoryDao.update(category);
		return redirPath+"CategoryServlet?method=show&id="+category.getId();
	}
	
	
	
}
