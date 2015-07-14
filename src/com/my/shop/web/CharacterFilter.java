package com.my.shop.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterFilter implements Filter
{
	private String encoding;
	
	public void init(FilterConfig cfg) throws ServletException
	{
		this.encoding = cfg.getInitParameter("encoding");
		if(encoding==null || "".equals(encoding.trim()))
		{
			this.encoding = "utf-8";
		}
	}
	
	
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException
	{
		req.setCharacterEncoding(encoding);
		chain.doFilter(req,resp);
	}
	
	public void destroy()
	{
		
	}
	
}
