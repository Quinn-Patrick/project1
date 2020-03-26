package com.revature.data;

import java.util.List;

import com.revature.models.Reimb;

public interface ReimbDao {
	public int storeReimb(Reimb reimb);
	
	public Reimb retrieveReimb(int reimbId);
	public List<Reimb> retrieveAllReimbs();
	public List<Reimb> retrieveAllUserReimbs(int userId);
	
	public void deleteReimb(int reimbId);
}
