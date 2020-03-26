package com.revature.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.User;

public class UserDaoImp implements UserDao {

	Logger log = Logger.getLogger(UserDaoImp.class);
	
	public void storeUser(User user) {
		try(Connection conn = ConnectionUtil.getConnection()){
			if(newUser(user)) {
				String sql = "CALL insert_into_ers_users(?,?,?,?,?,?,?)";
				cleanAndExecute(conn, sql, ((Integer)user.getUserId()).toString(), user.getUsername(), user.getPassword(), user.getFirstName(),
						user.getLastName(), user.getEmail(), ((Integer) user.getRole()).toString());
			}else {
				String sql = "CALL update_ers_users(?,?,?,?,?,?)";
				cleanAndExecute(conn, sql, user.getUsername(), user.getPassword(), user.getFirstName(),
						user.getLastName(), user.getEmail(), ((Integer) user.getRole()).toString());
			}
		}catch(SQLException e) {
			log.error("sql exception:\n" + e.getStackTrace());
			e.printStackTrace();
		}
	}

	public User retrieveUser(String username) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "SELECT * FROM ers_users WHERE ers_username = ?";
			ResultSet res = cleanAndExecute(conn, sql, username);
			
			if(!res.next()) return null;
			else {
				return new User(res.getInt("ers_users_id"),
						res.getString("ers_username"),
						res.getString("ers_password"),
						res.getString("ers_first_name"),
						res.getString("ers_last_name"),
						res.getString("user_email"),
						res.getInt("user_role_id"));
			}
		}catch(SQLException e) {
			log.error("sql exception:\n" + e.getStackTrace());
			e.printStackTrace();
			return null;
		}
		
	}
	
	/*
	 * private User retrieveUser(String username, Connection conn) { try{ String sql
	 * = "SELECT * FROM ers_users WHERE ers_username = ?"; ResultSet res =
	 * cleanAndExecute(conn, sql, username);
	 * 
	 * if(!res.next()) return null; else { return new
	 * User(res.getInt("ers_users_id"), res.getString("ers_username"),
	 * res.getString("ers_password"), res.getString("ers_first_name"),
	 * res.getString("ers_last_name"), res.getString("user_email"),
	 * res.getInt("user_role")); } }catch(SQLException e) {
	 * log.error("sql exception:\n" + e.getStackTrace()); e.printStackTrace();
	 * return null; }
	 * 
	 * }
	 */

	public List<User> retrieveAllUsers() {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			List<User> allUsers = new ArrayList<>();
			String sql = "SELECT * FROM ers_users";
			ResultSet res = cleanAndExecute(conn, sql);
			
			while(res.next()) {
				allUsers.add(new User(res.getInt("ers_users_id"),
						res.getString("ers_username"),
						res.getString("ers_password"),
						res.getString("ers_first_name"),
						res.getString("ers_last_name"),
						res.getString("user_email"),
						res.getInt("user_role")));
			}
			return allUsers;
			
		}catch(SQLException e) {
			log.error("sql exception:\n" + e.getStackTrace());
			e.printStackTrace();
			return null;
		}
	}

	public List<String> retrieveAllUsernames() {
		try(Connection conn = ConnectionUtil.getConnection()){
			List<String> allUsernames = new ArrayList<>();
			String sql = "SELECT ers_username FROM ers_users";
			ResultSet res = cleanAndExecute(conn, sql);
			while(res.next()) {
				allUsernames.add(res.getString("ers_username"));
			}
			return allUsernames;
		}catch(SQLException e) {
			log.error("sql exception:\n" + e.getStackTrace());
			e.printStackTrace();
			return null;
		}
	}

	public void deleteUser(String username) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "DELETE FROM ers_users WHERE ers_username = ?";
			cleanAndExecute(conn, sql, username);
		}catch(SQLException e) {
			log.error("sql exception:\n" + e.getStackTrace());
			e.printStackTrace();
		}

	}
	
	private boolean newUser(User user) {
		if(this.retrieveAllUsernames().contains(user.getUsername())) return false;
		else return true;
	}
	
	private ResultSet cleanAndExecute( Connection conn, String ... sql) {
		try{
			//System.out.println("Made it here.");
			ResultSet res = null;
			if(sql[0].contains("?") && !sql[0].contains("CALL")){
				PreparedStatement stmt = conn.prepareStatement(sql[0]);
			
				//System.out.println("Made it here.");
				for(int i = 1; i < sql.length; i++) {
					//System.out.println(sql[i]);
					stmt.setString(i, sql[i]);
				}
				//System.out.println("Made it here. " + sql[1]);
				res = stmt.executeQuery();
			}
			else if(!sql[0].contains("CALL")) {
				Statement stmt = conn.createStatement();
				res = stmt.executeQuery(sql[0]);
			}
			else {
				CallableStatement stmt = conn.prepareCall(sql[0]);
				
				//System.out.println("Made it here.");
				for(int i = 1; i < sql.length; i++) {
					//System.out.println(sql[i]);
					stmt.setString(i, sql[i]);
				}
				//System.out.println("Made it here. " + sql[0]);
				res = stmt.executeQuery();
			}
			//System.out.println("Made it here.");
			return res;
		}catch(SQLException e) {
			log.error("sql exception:\n" + e.getStackTrace());
			return null;
		}
	}


}
