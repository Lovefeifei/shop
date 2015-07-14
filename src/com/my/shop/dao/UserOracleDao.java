package com.my.shop.dao;

import com.my.shop.model.Pager;
import com.my.shop.model.User;

public class UserOracleDao implements IUserDao {

	public void add(User user) {

	}

	public void delete(int id) {

	}

	public void update(User user) {
		// TODO Auto-generated method stub

	}

	public User loadByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public User load(int id) {
		System.out.println("oracle");
		return null;
	}

	public Pager<User> find(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public User login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
