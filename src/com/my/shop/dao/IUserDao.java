package com.my.shop.dao;

import com.my.shop.model.Pager;
import com.my.shop.model.User;

public interface IUserDao {
	public void add(User user);
	public void delete(int id);
	public void update(User user);
	public User loadByUsername(String username);
	public User load(int id);
	public Pager<User> find(String name);//分页
	public User login(String username,String password);
}
