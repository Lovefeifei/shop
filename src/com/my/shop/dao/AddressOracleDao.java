package com.my.shop.dao;

import java.util.List;

import com.my.shop.model.Address;

public class AddressOracleDao implements IAddressDao {

	
	public void add(Address address, int userId) {

	}

	
	public void update(Address address) {
		

	}

	public void delete(int id) {

	}


	public Address load(int id) {
		System.out.println("oracle");
		return null;
	}


	public List<Address> list(int userId) {
		return null;
	}

}
