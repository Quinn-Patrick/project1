package services;

import java.util.List;

import com.revature.data.ReimbDao;
import com.revature.data.ReimbDaoImp;
import com.revature.models.Reimb;

public class ReimbService {
	private static ReimbDao data = new ReimbDaoImp();
	
	public static void storeReimb(Reimb r) {
		data.storeReimb(r);
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
}
