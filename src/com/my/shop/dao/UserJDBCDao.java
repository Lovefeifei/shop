package com.my.shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.my.shop.model.Address;
import com.my.shop.model.Pager;
import com.my.shop.model.User;
import com.my.shop.util.DBUtil;

public class UserJDBCDao implements IUserDao {
	
	public User loadOnceUser(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Address> as = new ArrayList<Address>();
		Address a = null;
		User u = null;
		try {
			con = DBUtil.getConnection();
			String sql = "select *,t2.id as 'a_id' " +
					"from t_user t1 left join t_address t2 on(t1.id=t2.user_id) where t1.id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				if(u==null) {//第一次进来  为u赋值
					u = new User();
					u.setId(rs.getInt("user_id"));
					u.setNickname(rs.getString("nickname"));
					u.setPassword(rs.getString("password"));
					u.setType(rs.getInt("type"));
					u.setUsername(rs.getString("username"));
				}
				a = new Address();
				a.setId(rs.getInt("a_id"));
				a.setName(rs.getString("name"));
				a.setPhone(rs.getString("phone"));
				a.setPostcode(rs.getString("postcode"));
				as.add(a);
			}
			u.setAddresses(as);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(con);
		}
		return u;
	}
	
	@SuppressWarnings("unused")
	private User loadSecondUser(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Address> as = new ArrayList<Address>();
		Address a = null;
		User u = null;
		try {
			con = DBUtil.getConnection();
			String sql = "select * from t_user where id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				u = new User();
				u.setId(rs.getInt("id"));
				u.setNickname(rs.getString("nickname"));
				u.setPassword(rs.getString("password"));
				u.setType(rs.getInt("type"));
				u.setUsername(rs.getString("username"));
			}
			sql = "select * from t_address where user_id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				a = new Address();
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setPhone(rs.getString("phone"));
				a.setPostcode(rs.getString("postcode"));
				as.add(a);
			}
			u.setAddresses(as);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(con);
		}
		return u;
	}

	public User load(int id) {
		return loadOnceUser(id);
	}

	public void add(User user) {
		
	}

	public void delete(int id) {
		
	}

	public void update(User user) {
		
	}

	public User loadByUsername(String username) {
		return null;
	}

	public Pager<User> find(String name) {
		return null;
	}

	public User login(String username, String password) {
		return null;
	}

}
