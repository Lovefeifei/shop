package com.my.shop.web;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.my.shop.dao.ICategoryDao;
import com.my.shop.dao.IProductDao;
import com.my.shop.model.Product;
import com.my.shop.model.ShopDi;
import com.my.shop.model.ShopException;
import com.my.shop.model.SystemContext;
import com.my.shop.util.RequestUtil;

public class ProductServlet extends BaseServlet
{
	private IProductDao productDao;
	private ICategoryDao categoryDao;
	
	public IProductDao getProductDao()
	{
		return productDao;
	}

	@ShopDi
	public void setProductDao(IProductDao productDao)
	{
		this.productDao = productDao;
	}

	public ICategoryDao getCategoryDao()
	{
		return categoryDao;
	}

	@ShopDi
	public void setCategoryDao(ICategoryDao categoryDao)
	{
		this.categoryDao = categoryDao;
	}


	@Auth("any")
	public String list(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("products", productDao.find(0, null,0));
		return "product/list.jsp";
	}
	
	public String addInput(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("cs",categoryDao.list());
		return "product/addInput.jsp";
	}
	
	public String add(HttpServletRequest req,HttpServletResponse resp) throws FileNotFoundException, IOException {
		Product p = (Product)RequestUtil.setParam(Product.class, req);
		p.setStatus(1);
		RequestUtil.Validate(Product.class, req);
		int cid = 0;
		try {
			cid = Integer.parseInt(req.getParameter("cid"));
		} catch (NumberFormatException e) {}
		if(cid==0) {
			this.getErrors().put("cid", "商品类别必须选择");
		}
		if(!this.hasErrors()) {
			//文件上传
			byte[] fs = (byte[])req.getAttribute("fs");
			String fname = req.getParameter("img");
			fname = FilenameUtils.getName(fname);
			RequestUtil.uploadFile(fname, "img", fs, req);

		}
		if(this.hasErrors()) {
			addInput(req,resp);
			return "product/addInput.jsp";
		}
		productDao.add(p, cid);
		return redirPath+"ProductServlet?method=list";
	}

	public String delete(HttpServletRequest req,HttpServletResponse resp) {
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			productDao.delete(id);
		} catch (ShopException e) {
			return this.handleException(e, req);
		}
		return redirPath+"ProductServlet?method=list";
	}
	
	
	@Auth("any")
	public String show(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("product", productDao.load(Integer.parseInt(req.getParameter("id"))));
		return "product/show.jsp";
	}
	
	public String changeStatus(HttpServletRequest req,HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		productDao.changeStatus(id);
		return redirPath+"ProductServlet?method=list";
	}
	
	public String addStockInput(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("c",productDao.load(Integer.parseInt(req.getParameter("id"))));
		return "product/addStockInput.jsp";
	}
	
	public String addStock(HttpServletRequest req,HttpServletResponse resp) {
		try {
			int number = Integer.parseInt(req.getParameter("number"));
			int id = Integer.parseInt(req.getParameter("id"));
			productDao.addStock(id, number);
		} catch (NumberFormatException e) {
			this.getErrors().put("number", "库存的类型必须为整数");
			req.setAttribute("c",productDao.load(Integer.parseInt(req.getParameter("id"))));
			return "product/addStockInput.jsp";
		}
		return redirPath+"ProductServlet?method=list";
	}
	
	public String update(HttpServletRequest req,HttpServletResponse resp) throws FileNotFoundException, IOException {
		Product p = (Product)RequestUtil.setParam(Product.class, req);
		RequestUtil.Validate(Product.class, req);
		String img = req.getParameter("img");
		System.out.println(img+"----------------------");
		int cid = 0;
		try {
			cid = Integer.parseInt(req.getParameter("cid"));
		} catch (NumberFormatException e) {}
		if(cid==0) {
			this.getErrors().put("cid", "商品类别必须选择");
		}
		Product tp = productDao.load(Integer.parseInt(req.getParameter("id")));
		tp.setIntro(p.getIntro());
		tp.setName(p.getName());
		tp.setPrice(p.getPrice());
		tp.setStock(p.getStock());
		boolean updateImg = false;
		if(img==null||img.trim().equals("")) {
			//此时说明不修改图片
		} else {
			//此时说明需要修改图片
			if(!this.hasErrors()) {
				//是否要修改文件
				byte[] fs = (byte[])req.getAttribute("fs");
				String fname = req.getParameter("img");
				fname = FilenameUtils.getName(fname);
				RequestUtil.uploadFile(fname, "img", fs, req);
			}
			updateImg = true;
		}
		if(this.hasErrors()) {
			req.setAttribute("p", tp);
			req.setAttribute("cs",categoryDao.list());
			return "product/updateInput.jsp";
		}
		if(updateImg) {
			//先删除原有的图片
			String oimg = tp.getImg();
			File f = new File(SystemContext.getRealpath()+"/img/"+oimg);
			f.delete();
			tp.setImg(p.getImg());
		}
		productDao.update(tp, cid);
		return redirPath+"ProductServlet?method=list";
	}
	
	public String updateInput(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("cs",categoryDao.list());
		req.setAttribute("p", productDao.load(Integer.parseInt(req.getParameter("id"))));
		return "product/updateInput.jsp";
	}
	
	
	
	
	
	
}
