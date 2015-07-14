package com.my.shop.test;

import com.my.shop.util.DaoUtil;


public class BaseTest
{
	public BaseTest() {
		
		try
		{
			DaoUtil.diDao(this);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
