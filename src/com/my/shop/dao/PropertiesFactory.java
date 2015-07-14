package com.my.shop.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.my.shop.util.PropertiesUtil;


public class PropertiesFactory implements IFactoryDao
{
	//添加单例，并且构造一个可以获取该单例的方法。
	private static PropertiesFactory f = new PropertiesFactory();
	
	private PropertiesFactory(){}
	
	public static IFactoryDao getInstance()
	{
		return f;
	}
	
	//为了反正不停的创建对象，使用map管理
	private static Map<String , Object> daos = new HashMap<String , Object>();
	
	
	
	
	
	public Object getDao(String name)
	{
		if(daos.containsKey(name))
		{
			return daos.get(name);
		}
		Properties daoProp = PropertiesUtil.getDaoProp();
		
		String dao = daoProp.getProperty(name);
		try
		{
			Class<?> clz = Class.forName(dao);
			Object o = clz.newInstance();
			daos.put(name , o);
			return o;
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}catch (InstantiationException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}
		
		
		return null;
	}
	

}
