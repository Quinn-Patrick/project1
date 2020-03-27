package com.revature.data;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import com.revature.models.User;
public interface UserDao {
	public void storeUser(User user)  throws NoSuchAlgorithmException, InvalidKeySpecException ;
	
	public User retrieveUser(String username);
	public List<User> retrieveAllUsers();
	public List<String> retrieveAllUsernames();
	
	public void deleteUser(String username);

}
