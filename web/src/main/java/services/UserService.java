package services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.revature.data.UserDao;
import com.revature.data.UserDaoImp;
import com.revature.models.User;

public class UserService {
	private static UserDao data = new UserDaoImp();
	
	public static User retrieveUser(String username) {
		return data.retrieveUser(username);
	}
	
	public static void storeUser(User u) throws NoSuchAlgorithmException, InvalidKeySpecException {
		data.storeUser(u);
	}
	
	public static void deleteUser(String un) {
		data.deleteUser(un);
	}

}
