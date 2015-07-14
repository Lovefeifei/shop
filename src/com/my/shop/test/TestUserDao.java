package com.my.shop.test;

import org.junit.Test;

import com.my.shop.dao.IUserDao;
import com.my.shop.model.Address;
import com.my.shop.model.Pager;
import com.my.shop.model.ShopDi;
import com.my.shop.model.SystemContext;
import com.my.shop.model.User;


public class TestUserDao extends BaseTest
{
	private IUserDao ud;
	
	public IUserDao getUd()
	{
		return ud;
	}
	
	@ShopDi("userDao")
	public void setUd(IUserDao ud)
	{
		this.ud = ud;
	}

//	@Before
//	public void init() {
//		ud = DAOFactory.getUserDao();
//	}
	
	@Test
	public void testAdd(){
		User u = new User();
		u.setNickname("I love you");
		u.setPassword("520");
		u.setType(1);
		u.setUsername("费玉琴");
		ud.add(u);
	}
	
	@Test
	public void testUpdate() {
		User u = ud.loadByUsername("cc");
		u.setPassword("2222");
		ud.update(u);
	}
	
	@Test
	public void testDelete() {
		ud.delete(17);
	}
	
	@Test
	public void testLogin() {
		User u = ud.login("wukong", "123");
		System.out.println(u.getClass().getName());
		System.out.println(u.getNickname());
	}
	
	@Test
	public void testFind() {
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(10);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		Pager<User> ps = ud.find("U");
		System.out.println(ps.getTotalRecord());
		for(User u:ps.getDatas()) {
			System.out.println(u);
		}
		
	}
	
	@Test
	public void testLoad() {
		User u = ud.load(1);
		for(Address a:u.getAddresses()) {
			System.out.println(a);
		}
	}

}
