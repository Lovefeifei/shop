package com.my.shop.test;

import java.lang.reflect.Method;

import org.junit.Test;

import com.my.shop.model.ShopDi;


public class TestAnnotation
{
	@ShopDi("addressDao")
	public void aa() {
		
	}
	
	@ShopDi
	public void setUserDao() {
		
	}
	
	@Test
	public void test01() {
		Method [] ms = this.getClass().getDeclaredMethods();
		for(Method m:ms) {
			System.out.println(m.getName()+":"+m.isAnnotationPresent(ShopDi.class));
			if(m.isAnnotationPresent(ShopDi.class)) {
				ShopDi sd = m.getAnnotation(ShopDi.class);
				System.out.println(sd);
				//获得注释的值
				String v = sd.value();
				System.out.println(v);
				if(v==null||"".equals(v.trim())) {
					v = m.getName().substring(3);
				}
				System.out.println(v);
			}
		}
	}
}
