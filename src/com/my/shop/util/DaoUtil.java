package com.my.shop.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import com.my.shop.dao.IFactoryDao;
import com.my.shop.model.ShopDi;
/**
 * 将反射应用进来
 * @author justin
 *
 */
public class DaoUtil {
	public static void main(String[] args) {
		System.out.println(createDaoFactory());
	}
	
	public static void diDao(Object obj)
	{
		try{
			
			Method[] md = obj.getClass().getMethods();
			
			for(Method m : md)
			{
				if(m.isAnnotationPresent(ShopDi.class))
				{
					ShopDi sd = m.getAnnotation(ShopDi.class);
					String value = sd.value();
					if(value==null || "".equals(value.trim()))
					{
						value = m.getName().substring(3);
						value = value.substring(0,1).toLowerCase()+value.substring(1);
					}
					
					Object o = DaoUtil.createDaoFactory().getDao(value);
					//调用set方法，注入对象
					m.invoke(obj , o);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * 
	 
	public static void diDao(Object obj) 
	{
		try
		{
			
			//获取自己的所有的方法
			Method[] ms = obj.getClass().getDeclaredMethods();
			for(Method m : ms)
			{
				//获取方法的名称
				String name = m.getName();
				//判断这个方法是否以set开头
				if(name.startsWith("set"))
				{
					//如果是以set开头，则获取后面的具体dao
					name = name.substring(3);
					name = name.substring(0,1).toLowerCase()+name.substring(1);
					
					
					//调用工厂来获取这个dao,利用反射来获取
					Object o = DaoUtil.createDaoFactory().getDao(name);
					
					//通过method来完成注入，m是一个setXXX的方法，调用者是this,参数是
					//上一段代码从工厂中取出的具体的XXX对象
					//this.setXX(o)--m.invoke(this,o)
					m.invoke(obj,o);
						
				}	
			}
		}catch (IllegalArgumentException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}catch (InvocationTargetException e){
			e.printStackTrace();
		}
		
	}
	
	* @param obj
	 */
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public static IFactoryDao createDaoFactory() {
		IFactoryDao f = null;
		try {
			Properties prop = PropertiesUtil.getDaoProp();
			String fs = prop.getProperty("factory");
			Class clz = Class.forName(fs);
			String mn = "getInstance";
			Method m = clz.getMethod(mn);
			f = (IFactoryDao)m.invoke(clz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return f;
	}

	
	
	
	
	
	
	
	
	
}
