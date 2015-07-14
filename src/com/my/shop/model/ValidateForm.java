package com.my.shop.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateForm
{
	/*
	 * type表示要验证的类型
	 */
	public ValidateType type(); 
	/*
	 * 
	 */
	public String errorMsg();
	
}
