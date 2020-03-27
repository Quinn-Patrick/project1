package com.revature.data;


import java.util.List;

import javax.servlet.http.Part;

import com.revature.models.Reimb;

public interface ReimbDao {
	public int storeReimb(Reimb reimb);
	
	public Reimb retrieveReimb(int reimbId);
	public List<Reimb> retrieveAllReimbs();
	public List<Reimb> retrieveAllUserReimbs(int userId);
	
	public void deleteReimb(int reimbId);
	public void uploadImage(int reimbId, Part image);
	
	public byte[] downloadImage(int reimbId);
}
