package com.my.shop.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.my.shop.model.Address;
import com.my.shop.model.Pager;
import com.my.shop.model.ShopException;
import com.my.shop.model.SystemContext;
import com.my.shop.model.User;
import com.my.shop.util.MybatisUtil;

public class AddressDao implements IAddressDao
{

	public void add(Address address, int userId)
	{
		User u = DAOFactory.getUserDao().load(userId);
		if(u==null)
			throw new ShopException("添加地址的用户不存在");
		address.setUser(u);
		
		SqlSession session = null;
		session = MybatisUtil.createSession();
		session.insert(Address.class.getName()+".add",address);
		MybatisUtil.closeSession(session);
		
	}

	public void delete(int id)
	{
		SqlSession session = null;
		session = MybatisUtil.createSession();
		session.delete(Address.class.getName()+".delete" , id);
	

		MybatisUtil.closeSession(session);

	}

	public List<Address> list(int userId)//根据user取多条地址
	{
		
		
		List<Address> list = null;
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		SqlSession session = null;
		try{
			session = MybatisUtil.createSession();
			list = session.selectList(Address.class.getName()+".list",params);
		} finally {
			MybatisUtil.closeSession(session);
		}
		return list;
	}

	public Address load(int id)//取一条地址 并且取出拥有的人
	{

		SqlSession session = null;
		Address address = null;
		try{
			session = MybatisUtil.createSession();
			 address = (Address)session.selectOne(Address.class.getName()+".load" , id);
			
		}finally{
			MybatisUtil.closeSession(session);
		}
		return address;
	}

	public void update(Address address)
	{
		SqlSession session = null;
		
		try{
			session = MybatisUtil.createSession();
			session.update(Address.class.getName()+".update" , address);
			
		}catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		}
		finally{
			MybatisUtil.closeSession(session);
		}

	}
	
	public Address loadByname(String name)
	{
		if(" ".equals(name) || name ==null)
		{
			throw new ShopException("用户名不能为空");
		}
		SqlSession session = null;
		Address address = null;
		session = MybatisUtil.createSession();
		address = session.selectOne(Address.class.getName()+".loadByname" , name);
		
		MybatisUtil.closeSession(session);
		
		
		return address;
	}
	
	
	public Pager<Address> find(String name)
	{
		int pageSize = SystemContext.getPageSize();
		int pageOffset = SystemContext.getPageOffset();
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		
		Pager<Address> pages = new Pager<Address>();
		
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
			//params.put("user_id" , 1);
			List<Address> datas = session.selectList(Address.class.getName()+".find" , params);
			
			pages.setDatas(datas);
			pages.setPageSize(pageSize);
			pages.setPageOffset(pageOffset);
			
			int totalRecord = session.selectOne(Address.class.getName()+".find_count" , params);
			pages.setTotalRecord(totalRecord);
			
			
			
			
		}finally{
			MybatisUtil.closeSession(session);
		}
		
		
		return pages;
	}
	
	
	

}
