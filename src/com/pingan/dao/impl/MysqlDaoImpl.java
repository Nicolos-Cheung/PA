package com.pingan.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.pingan.dao.CustomerDao;
import com.pingan.domain.Ivector;
import com.pingan.utils.C3P0Util;
import com.pingan.utils.DBCPUtil;
import com.pingan.utils.JdbcUtil;

/*
 CREATE TABLE customers(
 telnum varchar(100) UNIQUE  NOT NULL,
 url varchar(100) NOT NULL
 );
 */
public class MysqlDaoImpl implements CustomerDao {

	public void register(Ivector c) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBCPUtil.getConnection();
			System.out.println(conn);
			stmt = conn
					.prepareStatement("insert into customers(telnum,url)values(?,?)");
			stmt.setString(1, c.getTelnum());
			stmt.setString(2, c.getUrl());
			int executeUpdate = stmt.executeUpdate();
			if (executeUpdate > 0) {
				System.out.println("添加成功");
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.release(rs, stmt, conn);
		}
	}

	public Ivector find(String telnum) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBCPUtil.getConnection();
			System.out.println(conn);
			stmt = conn
					.prepareStatement("select * from customers where telnum = ?");
			stmt.setString(1, telnum);

			rs = stmt.executeQuery();

			if (rs.next()) {
				Ivector c = new Ivector();
				c.setTelnum(rs.getString("telnum"));
				c.setUrl(rs.getString("url"));
				return c;
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.release(rs, stmt, conn);
		}

	}

	public void update(Ivector c) {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBCPUtil.getConnection();
			System.out.println(conn);
			stmt = conn
					.prepareStatement("update customers set url = ? where telnum = ?");
			stmt.setString(1, c.getUrl());
			stmt.setString(2, c.getTelnum());
			stmt.executeUpdate();

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.release(rs, stmt, conn);
		}

	}

	public void remove(String telnum) {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBCPUtil.getConnection();
			System.out.println(conn);
			stmt = conn
					.prepareStatement("delete from customers where telnum =  ?");
			stmt.setString(1, telnum);
			stmt.executeUpdate();

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.release(rs, stmt, conn);
		}

	}

}
