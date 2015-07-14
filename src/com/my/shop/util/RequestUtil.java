package com.my.shop.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FilenameUtils;

import com.my.shop.model.ValidateForm;
import com.my.shop.model.ValidateType;

public class RequestUtil
{
	public final static String[] allowFile = {"jpg","bmp","gif","png"};
	public final static String PATH = "E:\\MyEclipse\\chongyou\\shop3\\WebRoot";
	
	@SuppressWarnings("unchecked")
	public static void uploadFile(String fname, String fieldName,byte[] fs,HttpServletRequest req) throws FileNotFoundException, IOException {
		FileOutputStream fos = null;
		try {
			if(fs.length>0) {
				String fn = FilenameUtils.getName(fname);
				String ext = FilenameUtils.getExtension(fname);
				boolean b =  checkFile(ext);
				if(b)
				{
					fos = new FileOutputStream(PATH+"/img/"+fn);
					fos.write(fs, 0, fs.length);
				} else {
					Map<String,String> errors = (Map<String,String>)req.getAttribute("errors");
					errors.put(fieldName, "图片类型必须是jpg,bmp,png,gif");
				}
			}
		} finally {
			if(fos!=null) fos.close();
		}
	}

	private static boolean checkFile(String ext) {
		for(String s:allowFile) {
			if(ext.equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean Validate(Class clz,HttpServletRequest req)
	{
		//获得传过来class的所以属性
		Field[] field = clz.getDeclaredFields();
		boolean isValidate = true;
		Map<String,String> errors = (Map<String,String>)req.getAttribute("errors");
		for(Field f : field)
		{	//判断属性是不是有Annotation注解
			if(f.isAnnotationPresent(ValidateForm.class))
			{
				ValidateForm form = f.getAnnotation(ValidateForm.class);
				ValidateType type = form.type();
				if(type==ValidateType.NotNull)
				{
					String str = req.getParameter(f.getName());
					if(str==null || "".equals(str)){
						errors.put(f.getName(),form.errorMsg());
						isValidate = false;
					}
				}
				else if(type==ValidateType.Number)
				{
					boolean b = validateNumber(f.getName(), req);
					if(!b) {
						isValidate = false;
						errors.put(f.getName(), form.errorMsg());
					}
				}
			}
			 
		}
		
		return isValidate;
	}
	
	private static boolean validateNumber(String name, HttpServletRequest req) {
		try {
			Double.parseDouble(req.getParameter(name));
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	
	@SuppressWarnings("unchecked")
	public static Object setParam(Class clz,HttpServletRequest req)
	{
		Map<String,String[]> params = req.getParameterMap();
		Set<String> keys = params.keySet();
		Object o = null;
		try {
			o = clz.newInstance();
			for(String key:keys) {
				
				String[] vv = params.get(key);
				//如果元素的值的长度大于1，表示获取的应该是一个数组
				if(vv.length>1) {
					BeanUtils.copyProperty(o, key, vv);
				} else {
					BeanUtils.copyProperty(o, key, vv[0]);
				}
				
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return o;
	}
}
