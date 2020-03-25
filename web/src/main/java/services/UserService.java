package services;

import com.revature.data.UserDao;
import com.revature.data.UserDaoImp;
import com.revature.models.User;

public class UserService {
	private static UserDao data = new UserDaoImp();
	
	public static User retrieveUser(String username) {
		return data.retrieveUser(username);
	}

}
