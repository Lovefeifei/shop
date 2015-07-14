package com.my.shop.test;

import java.util.List;

import com.my.shop.dao.DAOFactory;
import com.my.shop.dao.IAddressDao;
import com.my.shop.dao.IUserDao;
import com.my.shop.dao.PropertiesFactory;
import com.my.shop.model.Address;
import com.my.shop.model.User;

public class TestAddressDao
{
	private static IAddressDao addressDao = (IAddressDao)PropertiesFactory.getInstance().getDao("addressDao");
	//private static IAddressDao addressDao = DAOFactory.getAddressDao();
	private static IUserDao userDao = DAOFactory.getUserDao();
	
	public static void main(String[] args) throws Exception
	{
		//testAdd();
		//testList();
		
		testLoadUser();
	}
	
	public static void testLoadUser()
	{
		User u = userDao.load(1);
		System.out.println(u.getNickname());
		for(Address a : u.getAddresses())
		{
			System.out.println(a.getName()+" , " + a.getPhone() + " , " + a.getPostcode());
		} 
	}
	
	public static void testAdd() throws Exception
	{
		Address address = new Address();
		address.setName("重庆邮电大学");
		address.setPhone("12");
		address.setPostcode("4000045");
		
		addressDao.add(address,1);
	}
	
	public static void testList() {
		List<Address> list = addressDao.list(1);
		for(Address a:list) {
			System.out.println(a.getName()+","+a.getUser().getNickname());
		}
	}
}
