package com.my.shop.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.shop.dao.IProductDao;
import com.my.shop.dao.IUserDao;
import com.my.shop.model.Product;
import com.my.shop.model.ShopCart;
import com.my.shop.model.ShopDi;
import com.my.shop.model.ShopException;
import com.my.shop.model.User;



public class OrdersServlet extends BaseServlet
{
	private IProductDao productDao;
	private IUserDao userDao;
	
	
	public IUserDao getUserDao()
	{
		return userDao;
	}
	@ShopDi
	public void setUserDao(IUserDao userDao)
	{
		this.userDao = userDao;
	}
	
	public IProductDao getProductDao()
	{
		return productDao;
	}
	@ShopDi
	public void setProductDao(IProductDao productDao)
	{
		this.productDao = productDao;
	}
	
	@Auth
	public String showCart(HttpServletRequest req,HttpServletResponse resp) {
		User u = (User)req.getSession().getAttribute("loginUser");
		req.setAttribute("addresses", userDao.load(u.getId()).getAddresses());
		return "orders/showCart.jsp";
	}
	
	@Auth
	public String addToCart(HttpServletRequest req,HttpServletResponse resp) {
		
		try
		{
			ShopCart shopCart = (ShopCart)req.getSession().getAttribute("shopCart");
			if(shopCart==null)
			{
				shopCart = new ShopCart();
				req.getSession().setAttribute("shopCart" , shopCart);
			}
			
			Product p = productDao.load(Integer.parseInt(req.getParameter("id")));
			shopCart.add(p);
		}
		catch (ShopException e)
		{
			return this.handleException(e , req);
		}
		return redirPath+"ProductServlet?method=list";
	}
	
	@Auth
	public String clearProduct(HttpServletRequest req,HttpServletResponse resp)
	{
		int pid = Integer.parseInt(req.getParameter("pid"));
		ShopCart shopCart = (ShopCart)req.getSession().getAttribute("shopCart");
		if(shopCart!=null)
		{
			shopCart.clearProduct(pid);
		}
		return redirPath+"OrdersServlet?method=showCart";
	}
	
	@Auth
	public String clearShopCart(HttpServletRequest req,HttpServletResponse resp)
	{
		ShopCart shopCart = (ShopCart)req.getSession().getAttribute("shopCart");
		if(shopCart!=null)
		{
			shopCart.clearShopProduct();
		}
		return redirPath+"OrdersServlet?method=showCart";
	}
	
	@Auth
	public String productAddNumberInput(HttpServletRequest req,HttpServletResponse resp)
	{
		int pid = Integer.parseInt(req.getParameter("pid"));
		req.setAttribute("pid" , pid);
		Product p = productDao.load(pid);
		req.setAttribute("p" , p);
		return "orders/productAddNumberInput.jsp";
	}
	
	@Auth
	public String productAddNumber(HttpServletRequest req,HttpServletResponse resp)
	{
		int pid = Integer.parseInt(req.getParameter("pid"));
		try
		{
			int number = Integer.parseInt(req.getParameter("number"));
			ShopCart shopCart = (ShopCart)req.getSession().getAttribute("shopCart");
			if(shopCart!=null)
			{
				shopCart.addProductNumber(pid,number);
			}
		}
		catch (NumberFormatException e)
		{
			this.getErrors().put("number","数量必须为整数");
			Product p = productDao.load(pid);
			req.setAttribute("p" , p);
			req.setAttribute("pid", pid);
			return "orders/productAddNumberInput.jsp";
		} 
		catch (ShopException e)
		{
			return this.handleException(e, req);
		}
		
		return redirPath+"OrdersServlet?method=showCart";
	}

	
	
	
	
	
	
}
