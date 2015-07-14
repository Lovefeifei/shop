package com.my.shop.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.my.shop.model.Pager;
import com.my.shop.model.ShopException;
import com.my.shop.model.SystemContext;
import com.my.shop.model.User;
import com.my.shop.util.MybatisUtil;

public class UserDao implements IUserDao
{
	public void add(User user)
	{
		User tu = this.loadByUsername(user.getUsername());
		if(tu!=null)
			throw new ShopException("要添加的用户已经存在");
		else
		{
			SqlSession session = null;
			
			try{
				session = MybatisUtil.createSession();
				session.insert(User.class.getName()+".add" , user);
				
			}catch (Exception e) {
				e.printStackTrace();
				session.rollback();
			}
			finally{
				MybatisUtil.closeSession(session);
			}
		}
		
	}

	public void delete(int id)
	{
		SqlSession session = null;
		
		try{
			session = MybatisUtil.createSession();
			session.delete(User.class.getName()+".delete" , id);
			
		}catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		}
		finally{
			MybatisUtil.closeSession(session);
		}
		
	}

	public Pager<User> find(String name)
	{
		int pageSize = SystemContext.getPageSize();
		int pageOffset = SystemContext.getPageOffset();
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		
		Pager<User> pages = new Pager<User>();
		
		SqlSession session = null;
		try
		{
			session = MybatisUtil.createSession();
			
			Map<String , Object> params = new HashMap<String,Object>();
			
			if(name!=null && !name.equals(""))
				params.put("name","%"+name+"%");
			
			params.put("pageSize" , pageSize);
			params.put("pageOffset" , pageOffset);
			
			params.put("order" , order);
			params.put("sort" , sort);
			List<User> datas = session.selectList(User.class.getName()+".find" , params);
			
			pages.setDatas(datas);
			pages.setPageSize(pageSize);
			pages.setPageOffset(pageOffset);
			
			int totalRecord = session.selectOne(User.class.getName()+".find_count" , params);
			pages.setTotalRecord(totalRecord);
			
			
			
			
		}finally{
			MybatisUtil.closeSession(session);
		}
		
		
		return pages;
	}

	public User load(int id)
	{
		SqlSession session = null;
		User u = null;
		try{
			session = MybatisUtil.createSession();
			 u = (User)session.selectOne(User.class.getName()+".load" , id);
			
		}finally{
			MybatisUtil.closeSession(session);
		}
		return u;
	}

	public User loadByUsername(String username)
	{
		SqlSession session = null;
		User u = null;
		try{
			session = MybatisUtil.createSession();
			u = (User)session.selectOne(User.class.getName()+".loadByUsername" , username);
			
		}finally{
			MybatisUtil.closeSession(session);
		}
		return u;
	}

	public User login(String username, String password)
	{
		User u = this.loadByUsername(username);
		if(u==null)
			throw new ShopException("用户不存在");
		if(!password.equals(u.getPassword()))
			throw new ShopException("密码不正确");
		return u;
		
	}

	public void update(User user)
	{
		SqlSession session = null;
		
		try{
			session = MybatisUtil.createSession();
			session.update(User.class.getName()+".update" , user);
			
		}catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		}
		finally{
			MybatisUtil.closeSession(session);
		}
		
		
	}
	
}
