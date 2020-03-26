package com.revature.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.Reimb;


public class ReimbDaoImp implements ReimbDao {

	private static Logger logger = Logger.getLogger(ReimbDaoImp.class);
	@Override
	public int storeReimb(Reimb reimb) {
		try(Connection conn = ConnectionUtil.getConnection()){
			if(reimb.getReimbId() == -1) {
				logger.info("Attempting to log new reimbursement.");
				String sql = "CALL insert_into_ers_reimbursement(?,?,?,?,?,?,?,?)";
				
				System.out.println(reimb.getDescription());
				
				
				CallableStatement stmt = conn.prepareCall(sql);
				stmt.setString(1, ((Double)reimb.getAmount()).toString());
				LocalDateTime date = reimb.getSubmitted();
				stmt.setTimestamp(2, new Timestamp(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
				date.getHour(), date.getMinute(), date.getSecond(), 0));
				stmt.setNull(3, Types.TIMESTAMP);
				stmt.setString(4, reimb.getDescription());
				stmt.setInt(5, reimb.getAuthor());
				stmt.setInt(6, reimb.getResolver());
				stmt.setInt(7, reimb.getStatus());
				stmt.setInt(8, reimb.getType());
				
				
				
				
				stmt.executeQuery();
				
				sql = "SELECT reimb_id FROM ers_reimbursement WHERE reimb_author = ? AND reimb_submitted = ? AND reimb_amount = ?";
				
				PreparedStatement stmt2 = conn.prepareStatement(sql);
				
				stmt2.setInt(1, reimb.getAuthor());
				stmt2.setTimestamp(2, new Timestamp(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
				date.getHour(), date.getMinute(), date.getSecond(), 0));
				stmt2.setString(3, ((Double)reimb.getAmount()).toString());
				
				
				ResultSet res = stmt.executeQuery();
				
				reimb.setReimbId(res.getInt("reimb_id"));
				return reimb.getReimbId();
			}else {
				System.out.println("Updating request...");
				String sql = "CALL update_ers_reimbursement(?,?,?,?)";
				CallableStatement stmt = conn.prepareCall(sql);
				stmt.setInt(1, reimb.getReimbId());
				LocalDateTime date = reimb.getResolved();
				stmt.setTimestamp(2, new Timestamp(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
				date.getHour(), date.getMinute(), date.getSecond(), 0));
				stmt.setInt(3, reimb.getResolver());
				stmt.setInt(4, reimb.getStatus());
				
				stmt.executeQuery();
				return reimb.getReimbId();			
		}
		}catch(SQLException e) {
			logger.error("SQL exception:\n" + e.getStackTrace());
			return -1;
		}

	}

	@Override
	public Reimb retrieveReimb(int reimbId) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_id = ?";
			ResultSet res = cleanAndExecute(conn, sql, ((Integer)reimbId).toString());
			if(!res.next()) return null;
			
			Reimb newR = new Reimb(
					res.getInt("reimb_amount"),
					dateFormatReverse(res.getTimestamp("reimb_submitted")),
					dateFormatReverse(res.getTimestamp("reimb_resolved")),
					res.getString("reimb_description"),
					res.getInt("reimb_author"),
					res.getInt("reimb_resolver"),
					res.getInt("reimb_status_id"),
					res.getInt("reimb_type_id"));
			
			newR.setReimbId(res.getInt("reimb_id"));
			
			sql = "SELECT ers_username FROM ers_users WHERE ers_users_id = ?";
			ResultSet innerResults = cleanAndExecute(conn, sql, ""+newR.getAuthor());
			innerResults.next();
			newR.setAuthorName(innerResults.getString("ers_username"));
			
			sql = "SELECT ers_username FROM ers_users WHERE ers_users_id = ?";
			innerResults = cleanAndExecute(conn, sql, ""+newR.getResolver());
			innerResults.next();
			newR.setResolverName(innerResults.getString("ers_username"));
			
			sql = "SELECT reimb_status FROM ers_reimbursement_status WHERE reimb_status_id = ?";
			System.out.println(newR.getStatus());
			innerResults = cleanAndExecute(conn, sql, ""+newR.getStatus());
			innerResults.next();
			newR.setStatusName(innerResults.getString("reimb_status"));
			
			sql = "SELECT reimb_type FROM ers_reimbursement_type WHERE reimb_type_id = ?";
			innerResults = cleanAndExecute(conn, sql, ""+newR.getType());
			innerResults.next();
			newR.setStatusName(innerResults.getString("reimb_type"));
			
			
			
			
			
			
			return newR; 
		}catch(SQLException e){
			logger.error("SQL exception:\n" + e.getStackTrace());
			return null;
		}
	}

	@Override
	public List<Reimb> retrieveAllReimbs() {
		try(Connection conn = ConnectionUtil.getConnection()){
			List<Reimb> allReimb = new ArrayList<>();
			
			String sql = "SELECT * FROM ers_reimbursement";
			ResultSet res = cleanAndExecute(conn, sql);
			
			while(res.next()) {
				Reimb nextR = new Reimb(
						res.getInt("reimb_amount"),
						dateFormatReverse(res.getTimestamp("reimb_submitted")),
						dateFormatReverse(res.getTimestamp("reimb_resolved")),
						res.getString("reimb_description"),
						res.getInt("reimb_author"),
						res.getInt("reimb_resolver"),
						res.getInt("reimb_status_id"),
						res.getInt("reimb_type_id"));
				
				nextR.setReimbId(res.getInt("reimb_id"));
				
				sql = "SELECT ers_username FROM ers_users WHERE ers_users_id = ?";
				ResultSet innerResults = cleanAndExecute(conn, sql, ((Integer)nextR.getAuthor()).toString());
				innerResults.next();
				nextR.setAuthorName(innerResults.getString("ers_username"));
				
				sql = "SELECT ers_username FROM ers_users WHERE ers_users_id = ?";
				innerResults = cleanAndExecute(conn, sql, ""+nextR.getResolver());
				innerResults.next();
				nextR.setResolverName(innerResults.getString("ers_username"));
				
				sql = "SELECT reimb_status FROM ers_reimbursement_status WHERE reimb_status_id = ?";
				innerResults = cleanAndExecute(conn, sql, ""+nextR.getStatus());
				innerResults.next();
				nextR.setStatusName(innerResults.getString("reimb_status"));
				
				sql = "SELECT reimb_type FROM ers_reimbursement_type WHERE reimb_type_id = ?";
				innerResults = cleanAndExecute(conn, sql, ""+nextR.getType());
				innerResults.next();
				nextR.setTypeName(innerResults.getString("reimb_type"));
				
				allReimb.add(nextR);
				
				
			}
			return allReimb;
		}catch(SQLException e) {
			logger.error("SQL exception:\n" + e.getStackTrace());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteReimb(int reimbId) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "DELETE FROM ers_reimbursement WHERE reimb_id = ?";
			cleanAndExecute(conn, sql, ((Integer)reimbId).toString());
		}catch(SQLException e) {
			logger.error("SQL exception:\n" + e.getStackTrace());
		}
	}
	
	private List<Integer> retrieveAllReimbId(){
		try(Connection conn = ConnectionUtil.getConnection()){
			List<Integer> allId = new ArrayList<>();
			String sql = "SELECT reimb_id FROM ers_reimbursement";
			ResultSet res = cleanAndExecute(conn, sql);
			while(res.next()) {
				allId.add(res.getInt("reimb_id"));
			}
			return allId;
		}catch(SQLException e) {
			logger.error("SQL exception:\n" + e.getStackTrace());
			return null;
		}
		
	}
	
	/*private boolean newReimb(int reimbId) {
		if(retrieveAllReimbId().contains(reimbId)) return false;
		else return true;
	}*/
	
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
				
				System.out.println("Made it here.");
				for(int i = 1; i < sql.length; i++) {
					System.out.println(sql[i]);
					stmt.setString(i, sql[i]);
				}
				System.out.println("Made it here. " + sql[0]);
				res = stmt.executeQuery();
			}
			//System.out.println("Made it here.");
			return res;
		}catch(SQLException e) {
			e.printStackTrace();
			logger.error("SQL exception:\n" + e.getStackTrace());
			if(!sql[0].toLowerCase().contains("connection"))
				//Driver.logger.error("Connection to database failed.");
				//e.printStackTrace();
			return null;
		}
		System.out.println("How did I get here?");
		return null;
	}
	
	private String dateFormat(LocalDateTime date) {
		if(date == null) return null;
		else {
			String month = ((Integer)date.getMonthValue()).toString();
			if(date.getMonthValue() < 10) month = "0"+month;
			
			String day = ((Integer)date.getDayOfMonth()).toString();
			if(date.getDayOfMonth() < 10) day = "0"+day;
			
			String dateString  ="TO_TIMESTAMP('"+ date.getYear() + "-"
					+ month + "-"
					+ day + " "
					+ date.getHour() + ":"
					+ date.getMinute() + ":"
					+ date.getSecond()
					+ ".00', 'RRRR-MM-DD HH24:MI:SS.FF')";
			System.out.println(dateString);
			return(dateString);
		}
	}
	
	
	private LocalDateTime dateFormatReverse(Timestamp time) {
		if(time != null)return time.toLocalDateTime();
		else return null;
	}

	@Override
	public List<Reimb> retrieveAllUserReimbs(int userId) {
		try(Connection conn = ConnectionUtil.getConnection()){
			List<Reimb> allReimb = new ArrayList<>();
			
			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ?";
			ResultSet res = cleanAndExecute(conn, sql, ""+userId);
			
			while(res.next()) {
				Reimb nextR = new Reimb(
						res.getInt("reimb_amount"),
						dateFormatReverse(res.getTimestamp("reimb_submitted")),
						dateFormatReverse(res.getTimestamp("reimb_resolved")),
						res.getString("reimb_description"),
						res.getInt("reimb_author"),
						res.getInt("reimb_resolver"),
						res.getInt("reimb_status_id"),
						res.getInt("reimb_type_id"));
				
				nextR.setReimbId(res.getInt("reimb_id"));
				
				sql = "SELECT ers_username FROM ers_users WHERE ers_users_id = ?";
				ResultSet innerResults = cleanAndExecute(conn, sql, ((Integer)nextR.getAuthor()).toString());
				innerResults.next();
				nextR.setAuthorName(innerResults.getString("ers_username"));
				
				sql = "SELECT ers_username FROM ers_users WHERE ers_users_id = ?";
				innerResults = cleanAndExecute(conn, sql, ""+nextR.getResolver());
				innerResults.next();
				nextR.setResolverName(innerResults.getString("ers_username"));
				
				sql = "SELECT reimb_status FROM ers_reimbursement_status WHERE reimb_status_id = ?";
				innerResults = cleanAndExecute(conn, sql, ""+nextR.getStatus());
				innerResults.next();
				nextR.setStatusName(innerResults.getString("reimb_status"));
				
				sql = "SELECT reimb_type FROM ers_reimbursement_type WHERE reimb_type_id = ?";
				innerResults = cleanAndExecute(conn, sql, ""+nextR.getType());
				innerResults.next();
				nextR.setTypeName(innerResults.getString("reimb_type"));
				
				allReimb.add(nextR);
				
				
			}
			return allReimb;
		}catch(SQLException e) {
			logger.error("SQL exception:\n" + e.getStackTrace());
			e.printStackTrace();
			return null;
		}
	}
}
