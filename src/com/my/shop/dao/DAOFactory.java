package com.my.shop.dao;

public class DAOFactory
{

	public static IAddressDao getAddressDao()
	{
		
		return new AddressDao();
	}

	public static IUserDao getUserDao()
	{
		
		return new UserDao();
	}
	
}
