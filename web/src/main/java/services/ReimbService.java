package services;


import java.util.List;

import javax.servlet.http.Part;

import com.revature.data.ReimbDao;
import com.revature.data.ReimbDaoImp;
import com.revature.models.Reimb;

public class ReimbService {
	private static ReimbDao data = new ReimbDaoImp();
	
	public static void storeReimb(Reimb r) {
		data.storeReimb(r);
		System.out.println("Successfully stored reimb.");
	}
	
	public static Reimb retrieveReimb(int i) {
		return data.retrieveReimb(i);
	}
	
	public static List<Reimb> retrieveAllReimbs(){
		return data.retrieveAllReimbs();
	}
	
	public static void deleteReimb(int id) {
		data.deleteReimb(id);
	}
	
	public static List<Reimb> retrieveAllUserReimbs(int userId){
		return data.retrieveAllUserReimbs(userId);
	}
	
	public static void uploadImage(int reimbId, Part image) {
		data.uploadImage(reimbId, image);
		
	}
	
	public static byte[] downloadImage(int reimbId) {
		return data.downloadImage(reimbId);
	}
}
