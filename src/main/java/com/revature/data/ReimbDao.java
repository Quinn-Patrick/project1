package com.revature.data;

import java.util.List;

import com.revature.models.Reimb;

public interface ReimbDao {
	public void storeReimb(Reimb reimb);
	
	public Reimb retrieveReimb(int reimbId);
	public List<Reimb> retrieveAllReimbs();
	
	public void deleteReimb(int reimbId);
}
