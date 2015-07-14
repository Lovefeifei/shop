package com.my.shop.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.shop.dao.IUserDao;
import com.my.shop.model.Pager;
import com.my.shop.model.ShopDi;
import com.my.shop.model.ShopException;
import com.my.shop.model.User;
import com.my.shop.util.RequestUtil;

public class UserServlet extends BaseServlet
{
	private IUserDao userDao;

	@ShopDi
	public void setUserDao(IUserDao userDao)
	{
		this.userDao = userDao;
	}
	
	public String list(HttpServletRequest req, HttpServletResponse resp)
	{
		Pager<User> users = userDao.find("");
		req.setAttribute("users", users);
		return "user/list.jsp";
	}
	
	public String changeType(HttpServletRequest req,HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		User u = userDao.load(id);
		if(u.getType()==0){
			u.setType(1);
		} else{
			u.setType(0);
		}
		userDao.update(u);
	
		return redirPath+"UserServlet?method=list";
	}
	
	@Auth("any")
	public String addInput(HttpServletRequest req,HttpServletResponse resp) {
		return "user/addInput.jsp";
	}
	
	public String delete(HttpServletRequest req,HttpServletResponse resp) {
		String id = req.getParameter("id");
		userDao.delete(Integer.parseInt(id));
		
		return redirPath+"UserServlet?method=list";
	}
	
	public String updateInput(HttpServletRequest req,HttpServletResponse resp) {
		String id = req.getParameter("id");
		User u = userDao.load(Integer.parseInt(id));
		req.setAttribute("user" , u);
		return "user/updateInput.jsp";
	}
	
	public String update(HttpServletRequest req,HttpServletResponse resp) {
		User u = (User)RequestUtil.setParam(User.class,req);
		int id = Integer.parseInt(req.getParameter("id"));
		User user = userDao.load(id);
		boolean isValidate = RequestUtil.Validate(User.class,req);
		user.setNickname(u.getNickname());
		if(!isValidate) {
			req.setAttribute("user", user);
			return "user/updateInput.jsp";
		}
		
		userDao.update(u);
		
		return redirPath+"UserServlet?method=list";
	}
	
	@Auth("any")
	public String add(HttpServletRequest req,HttpServletResponse resp) {
		
//		String username = req.getParameter("username");
//		String password = req.getParameter("password");
//		String nickname = req.getParameter("nickname");
//		
//		User u = new User();
//		u.setUsername(username);
//		u.setPassword(password);
//		u.setNickname(nickname);
		User u = (User)RequestUtil.setParam(User.class,req);
		boolean isValidate = RequestUtil.Validate(User.class,req);
		if(!isValidate)
		{
			return "user/addInput.jsp";
		}
		
		try
		{
			userDao.add(u);
		}catch(ShopException e)
		{
			req.setAttribute("errorMsg",e.getMessage());
			return "inc/error.jsp";
		}
		return redirPath+"UserServlet?method=list";
	}
	
	@Auth("any")
	public String loginInput(HttpServletRequest req,HttpServletResponse resp) {
		return "user/loginInput.jsp";
	}
	
	@Auth("any")
	public String login(HttpServletRequest req,HttpServletResponse resp) {
		
		try
		{
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			User u = userDao.login(username,password);
			req.getSession().setAttribute("loginUser" , u);
		}
		catch (ShopException e)
		{
			req.setAttribute("errorMsg",e.getMessage());
			return "inc/error.jsp";
		}
		return redirPath+"ProductServlet?method=list";
	}
	
	@Auth("any")
	public String logout(HttpServletRequest req,HttpServletResponse resp) {
		req.getSession().invalidate();
		return redirPath+"ProductServlet?method=list";
	}
	
	@Auth
	public String updateSelfInput(HttpServletRequest req,HttpServletResponse resp) {
		
		req.setAttribute("user" , (User)req.getSession().getAttribute("loginUser"));
		return "user/updateSelfInput.jsp";
	}
	
	@Auth
	public String updateSelf(HttpServletRequest req,HttpServletResponse resp) {
		User u = (User)RequestUtil.setParam(User.class,req);
		boolean isValidate = RequestUtil.Validate(User.class,req);
		User user = (User)req.getSession().getAttribute("loginUser");
		user.setUsername(u.getPassword());
		user.setNickname(u.getNickname());
		if(!isValidate) {
			req.setAttribute("user", user);
			return "user/updateSelfInput.jsp";
		}
		userDao.update(user);
		
		return redirPath+"ProductServlet?method=list";
	}
	
	@Auth
	public String show(HttpServletRequest req,HttpServletResponse resp) {
		User user = userDao.load(Integer.parseInt(req.getParameter("id")));
		req.setAttribute("user" , user);
		return "user/show.jsp";
	}
	
	
	
}
