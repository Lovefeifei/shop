package com.my.shop.model;

import java.util.ArrayList;
import java.util.List;


public class ShopCart
{
	private List<CartProduct> products;
	private boolean isEmpty;
	
	public ShopCart()
	{
		isEmpty = true;
		products = new ArrayList<CartProduct>();
	}
	
	
	public boolean getIsEmpty()
	{
		return isEmpty;
	}

	public void setIsEmpty(boolean isEmpty)
	{
		this.isEmpty = isEmpty;
	}


	public List<CartProduct> getProducts()
	{
		return products;
	}
	
	public void setProducts(List<CartProduct> products)
	{
		this.products = products;
	}
	
	public void clearProduct(int pid)
	{
		//不能用迭代器删除。会有问题
//		for(CartProduct cp : products)
//		{
//			if(cp.getPid() == pid)
//			{
//				products.remove(cp);
//			}
//		}
		for(int i=0;i<products.size();i++) {
			CartProduct cp = products.get(i);
			if(cp.getPid()==pid) {
				products.remove(cp);
			}
		}
		if(products.size() <=0)
		{
			isEmpty = true;
		}
		
		
	}
	
	public void clearShopProduct()
	{
		products.clear();
		isEmpty = true;
	}
	
	
	public void add(Product product)
	{
		CartProduct cp = findCartProduct(product.getId());
		if(cp == null)
		{
			if(product.getStock() < 1)
			{
				throw new ShopException("该商品库存不够");
			}
			cp = new CartProduct();
			cp.setNumber(1);
			cp.setPrice(product.getPrice());
			cp.setProduct(product);
			cp.setPid(product.getId());
			products.add(cp);
		}
		else
		{
			if(product.getStock() < cp.getNumber()+1)
			{
				throw new ShopException("该商品库存不够");
			}
			cp.setNumber(cp.getNumber()+1);
			cp.setPrice(cp.getPrice()+product.getPrice());
		}
		isEmpty = false;
	}
	
	private CartProduct findCartProduct(int pid)
	{
		for(CartProduct cp : products)
		{
			if(cp.getPid() == pid)
			{
				return cp;
			}
		}
		return null;
	}


	public void addProductNumber(int pid, int number)
	{
		CartProduct cp = findCartProduct(pid);
		if(cp.getProduct().getStock() < (cp.getNumber() + number))
		{
			throw new ShopException("该商品库存不够");
		}
		cp.setNumber(cp.getNumber() + number);
		cp.setPrice(cp.getPrice()+(number*cp.getProduct().getPrice()));
		
	}
	
	
	
	
	
	
	
	
	

	
}
