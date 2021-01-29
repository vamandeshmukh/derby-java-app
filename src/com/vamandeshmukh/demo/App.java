package com.vamandeshmukh.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Vaman Deshmukh
 *
 */

public class App {

	private static String url = "jdbc:derby:E:\\ness\\hr";
	private static Connection con = null;
	private static Statement stmt = null;

	public static void main(String[] args) throws Exception {
		System.out.println("Start");
		App.createConnection();
		Thread.sleep(1000);
//		App.createTable();
//		Thread.sleep(2000);
		App.insertRecords(101, "Sonu", 10.50);
		Thread.sleep(1000);
		App.selectRecords();
		Thread.sleep(1000);
		App.updateRecords();
		Thread.sleep(1000);
		App.selectRecords();
		Thread.sleep(1000);
		App.deleteRecords();
		Thread.sleep(1000);
		App.selectRecords();
		Thread.sleep(1000);
		App.shutdown();
		System.out.println("end");
	}

	private static void createConnection() {
		System.out.println("Creating connection...");

		try {
			con = DriverManager.getConnection(App.url);
			System.out.println(con);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void createTable() {
		System.out.println("Creating table...");
		try {
			con = DriverManager.getConnection(App.url);
			stmt = con.createStatement();
			stmt.execute("create table emp (eid integer, name varchar(10), salary double)");
		} catch (SQLException sqlExcept) {
			System.out.println("Table already exists.");
//			sqlExcept.printStackTrace();
		} catch (Exception ex) {
			System.out.println("Something is wrong!");
		}
	}

	private static void insertRecords(int eid, String name, double salary) {
		System.out.println("Inserting records...");
		try {
			con = DriverManager.getConnection(App.url);
			stmt = con.createStatement();
			stmt.execute("insert into emp values (101, 'Sonu', 10.50)");
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void updateRecords() {
		System.out.println("Updating records...");
		try {
			con = DriverManager.getConnection(App.url);
			stmt = con.createStatement();
			stmt.execute("update emp set salary = salary * 2 where eid = 101");
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void deleteRecords() {
		System.out.println("Deleting records...");
		try {
			con = DriverManager.getConnection(App.url);
			stmt = con.createStatement();
			stmt.execute("delete from emp where salary > 500.0");
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void selectRecords() {
		System.out.println("Selecting records...");
		try {
			con = DriverManager.getConnection(App.url);
			ResultSet results = stmt.executeQuery("select eid, name, salary from emp");
			ResultSetMetaData rsmd = results.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numberOfColumns; i++) {
				System.out.print(rsmd.getColumnLabel(i) + "\t\t");
			}

			System.out.println("\n-------------------------------------------");

			while (results.next()) {
				int id = results.getInt(1);
				String restName = results.getString(2);
				String cityName = results.getString(3);
				System.out.println(id + "\t\t" + restName + "\t\t" + cityName);
			}
			results.close();
			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void shutdown() {
		System.out.println("Shutting down...");
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException sqlExcept) {
			System.out.println("...done.");
		}
	}
}
