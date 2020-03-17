package com.revature.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimb;

public class ReimbDaoImp implements ReimbDao {

	@Override
	public void storeReimb(Reimb reimb) {
		try(Connection conn = ConnectionUtil.getConnection()){
			if(newReimb(reimb.getReimbId())) {
				String sql = "CALL insert_into_ers_reimbursement(?,?,?,?,?,?,?,?,?)";
				cleanAndExecute(conn, sql, 
						((Integer)reimb.getReimbId()).toString(),
						((Double)reimb.getAmount()).toString(),
						dateFormat(reimb.getSubmitted()),
						dateFormat(reimb.getResolved()),
						reimb.getDescription(),
						((Integer)reimb.getAuthor()).toString(),
						((Integer)reimb.getResolver()).toString(),
						((Integer)reimb.getType()).toString(),
						((Integer)reimb.getStatus()).toString()
						);
			}else {
				String sql = "CALL update_ers_reimbursement(?,?,?,?)";
				cleanAndExecute(conn, sql,
						((Integer)reimb.getReimbId()).toString(),
						dateFormat(reimb.getResolved()),
						((Integer)reimb.getResolver()).toString(),
						((Integer)reimb.getStatus()).toString());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Reimb retrieveReimb(int reimbId) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_id = ?";
			ResultSet res = cleanAndExecute(conn, sql, ((Integer)reimbId).toString());
			if(!res.next()) return null;
			return new Reimb(
					res.getInt("reimb_id"),
					res.getInt("reimb_amount"),
					dateFormatReverse(res.getTimestamp("reimb_submitted")),
					dateFormatReverse(res.getTimestamp("reimb_resolved")),
					res.getString("reimb_description"),
					res.getInt("reimb_author"),
					res.getInt("reimb_resolver"),
					res.getInt("reimb_type_id"),
					res.getInt("reimb_status_id"));
		}catch(SQLException e){
			e.printStackTrace();
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
				allReimb.add(new Reimb(
				res.getInt("reimb_id"),
				res.getInt("reimb_amount"),
				dateFormatReverse(res.getTimestamp("reimb_submitted")),
				dateFormatReverse(res.getTimestamp("reimb_resolved")),
				res.getString("reimb_description"),
				res.getInt("reimb_author"),
				res.getInt("reimb_resolver"),
				res.getInt("reimb_type_id"),
				res.getInt("reimb_status_id")));
			}
			return allReimb;
		}catch(SQLException e) {
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
			e.printStackTrace();
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
			e.printStackTrace();
			return null;
		}
		
	}
	
	private boolean newReimb(int reimbId) {
		if(retrieveAllReimbId().contains(reimbId)) return false;
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
			if(!sql[0].toLowerCase().contains("connection"))
				//Driver.logger.error("Connection to database failed.");
				//e.printStackTrace();
			return null;
		}
		return null;
	}
	
	private String dateFormat(LocalDateTime date) {
		return(
				date.getYear() + "-"
				+ date.getMonthValue() + "-"
				+ date.getDayOfMonth() + " "
				+ date.getHour() + ":"
				+ date.getMinute() + ":"
				+ date.getSecond() + "."
				+ ".00");
	}
	
	private LocalDateTime dateFormatReverse(Timestamp time) {
		return time.toLocalDateTime();		
	}
}
