package com.my.shop.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.shop.dao.IAddressDao;
import com.my.shop.dao.IUserDao;
import com.my.shop.model.Address;
import com.my.shop.model.ShopDi;
import com.my.shop.model.User;
import com.my.shop.util.RequestUtil;

public class AddressServlet extends BaseServlet
{
	private IAddressDao addressDao;
	private IUserDao userDao;

	@ShopDi
	public void setAddressDao(IAddressDao addressDao)
	{
		this.addressDao = addressDao;
	}
	
	@ShopDi
	public void setUserDao(IUserDao userDao)
	{
		this.userDao = userDao;
	}
 
	@Auth
	public String addInput(HttpServletRequest req,HttpServletResponse resp) {
		User u = userDao.load(Integer.parseInt(req.getParameter("userId")));
		req.setAttribute("user" , u);
		return "address/addInput.jsp";
	}

	
	@Auth
	public String add(HttpServletRequest req,HttpServletResponse resp) {
		User u = userDao.load(Integer.parseInt(req.getParameter("userId")));
		Address a = (Address)RequestUtil.setParam(Address.class,req);
		boolean isValidate = RequestUtil.Validate(Address.class,req);
		if(!isValidate)
		{
			req.setAttribute("user" , u);
			return "address/addInput.jsp";
		}
		addressDao.add(a,u.getId());
		return redirPath+req.getContextPath()+"/UserServlet?method=show&id="+u.getId();
	}
	
	@Auth
	public String delete(HttpServletRequest req,HttpServletResponse resp) {
		String id = req.getParameter("id");
		String userId = req.getParameter("userId");
		addressDao.delete(Integer.parseInt(id));
		
		return redirPath+req.getContextPath()+"/UserServlet?method=show&id="+userId;
	}
	
	@Auth
	public String updateInput(HttpServletRequest req,HttpServletResponse resp) {
		String id = req.getParameter("id");
		Address a = addressDao.load(Integer.parseInt(id));
		req.setAttribute("address" , a);
		return "address/updateInput.jsp";
	}
	
	@Auth
	public String update(HttpServletRequest req,HttpServletResponse resp) {
		//新获得的地址
		Address a = (Address)RequestUtil.setParam(Address.class,req);
		int id = Integer.parseInt(req.getParameter("id"));
		//旧地址
		Address address = addressDao.load(id);
		address.setName(a.getName());
		address.setPhone(a.getPhone());
		address.setPostcode(a.getPostcode());
		boolean isValidate = RequestUtil.Validate(Address.class,req);
		if(!isValidate) {
			req.setAttribute("address", address);
			return "address/updateInput.jsp";
		}
		
		addressDao.update(address);
		
		return  redirPath+req.getContextPath()+"/UserServlet?method=show&id="+address.getUser().getId();
	}
	
}
