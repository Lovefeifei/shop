package com.my.shop.web;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.my.shop.model.User;
import com.my.shop.util.DaoUtil;

public class BaseServlet extends HttpServlet
{
	public final static String redirPath = "redirect:";
	private Map<String,String> errors = new HashMap<String,String>();
	
	protected Map<String,String> getErrors() {
		return errors;
	}
	
	protected boolean hasErrors() {
		if(errors!=null&&errors.size()>0) return true;
		return false;
	}
	
	protected String handleException(Exception e,HttpServletRequest req) {
		req.setAttribute("errorMsg",e.getMessage());
		return "inc/error.jsp";
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		try
		{
			errors.clear();
			req.setAttribute("errors", errors);
			if(ServletFileUpload.isMultipartContent(req)) {
				req = new MultipartWrapper(req);
			}
			String method = req.getParameter("method");
		
			DaoUtil.diDao(this);
			Method m = this.getClass().getMethod(method,HttpServletRequest.class,HttpServletResponse.class);
			/*
			 * 在这里进行权限控制
			 */
			int flag = checkAuth(req,m,resp);
			if(flag==1) {
				resp.sendRedirect("UserServlet?method=loginInput");
				return;
			} else if(flag==2) {
				req.setAttribute("errorMsg", "你没有权限访问该功能");
				req.getRequestDispatcher("/WEB-INF/inc/error.jsp").forward(req, resp);
				return;
			}
			String rel = (String)m.invoke(this,req,resp);
			if(rel.startsWith(redirPath))
			{
				resp.sendRedirect(rel.substring(redirPath.length()));
			}
			else
			{
				req.getRequestDispatcher("/WEB-INF/"+rel).forward(req,resp);
			}
			
		}catch (SecurityException e){
			e.printStackTrace();
		}catch (NoSuchMethodException e){
			e.printStackTrace();
		}catch (IllegalArgumentException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}catch (InvocationTargetException e){	
			e.printStackTrace();
		}
	
	}
	
	/*
	 * 返回一个int类型的值，如果是0表示可以成功访问，如果是1表示到登录页面，如果是2表示显示没有权限
	 */
	private int checkAuth(HttpServletRequest req,Method m ,HttpServletResponse resp)
	{
		User u = (User)req.getSession().getAttribute("loginUser");
		if(u!=null && u.getType()==1 )
		{
			/*
			 * 如果是管理员，则所有的功能都可以访问
			 */
			return 0;
		}
		if(!m.isAnnotationPresent(Auth.class))//没有Annotation说明由管理员访问
		{
			//如果u==null,则说明用户没有登录
			/*
			 * 怎样理解u为空值，即用户没有登录，毕竟用户登录那边会有报错。？
			 * 理解:假设用户没有登录，但是他并不知道他有没有登录，就直接访问了其他的功能。
			 * 因为没有登录，所以没有执行login方法，所以不会显示用户登录报错，所以就
			 * 有可能u的值为空，并且程序正常运行。
			 * 这也是为什么把用户存入session当中。
			 */
			if(u==null){
				return 1;//需要用户登录了后才能访问
			}else if(u.getType()!=1)
			{
				return 2;
			}
			
		}
		else
		{
			Auth a = m.getAnnotation(Auth.class);
			String v = a.value();
			if(v.equals("any")) {
				return 0;
			} else if(v.equals("user")){
				if(u==null)
					return 1;
				else return 0;
			}
		}
		
		return 0;
	}
}
