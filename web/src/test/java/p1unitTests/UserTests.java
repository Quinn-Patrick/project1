package p1unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.models.User;

import services.Hashing;
import services.UserService;



public class UserTests {

	private User u = null;

	/*
	 * This method will be invoked before the test class is instantiated.
	 * This is primarily used in order to set up the global test environment.
	 */
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	/*
	 * This method will be invoked after the entire test class has finished
	 * running its tests.
	 * This is primarily used to clean up the global test environment.
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	/*
	 * This method will be invoked before each individual test case.
	 * This is primarily used to set up specific test environments, or perhaps to rest
	 * the test environment.
	 */
	@Before
	public void setUp() throws Exception {
		u = new User(100000000, "a", "a", "a", "a", "a", 0);
	}

	/*
	 * This method will be invoked after each individual test case.
	 * This is primarily used to reset the test environment.
	 */
	@After
	public void tearDown() throws Exception {
		UserService.deleteUser("a");
	}

	@Test
	public void testusername() {
		u.setUsername("Test");
		assertEquals("Test", u.getUsername());
		u.setUsername("a");
	}
	@Test
	public void testpassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
		u.setPassword("Test");
		UserService.storeUser(u);
		assertEquals("Test", u.getPassword());
		User u2 = UserService.retrieveUser("a");
		assertEquals(u2.getPassword(), Hashing.hash(u.getPassword()));
	}
	
	@Test
	public void testname() {
		u.setFirstName("Test");
		assertEquals("Test", u.getFirstName());
		
		u.setLastName("Test");
		assertEquals("Test", u.getLastName());
	}
	@Test
	public void testrole() {
		u.setRole(1);
		assertEquals(1, u.getRole());
	}
	
	@Test
	public void testemail() {
		u.setEmail("test");
		assertEquals("test", u.getEmail());
	}
	
	@Test
	public void testmisc() throws NoSuchAlgorithmException, InvalidKeySpecException {
		System.out.println(u.toString());
		System.out.println(u.hashCode());
		assertTrue(u.equals(u));
		UserService.storeUser(u);
		u.setPassword(Hashing.hash(u.getPassword()));
		User u2 = UserService.retrieveUser("a");
		System.out.println(u2.toString());
		assertTrue(u.equals(u2));
	}
	
	/*
	 * This test will be a success if it sees MyException.
	 * It will fail if it sees no exception.
	 */
	/*
	 * @Test(expected = MyException.class) public void expectException() {
	 * 
	 * }
	 */
	
	/*
	 * In test driven development, we come up with crazier and crazier edge cases
	 * and alter the logic of our methods to pass them.
	 * 
	 * Or we can just ignore them.
	 */
	/*
	 * @Ignore
	 * 
	 * @Test public void testAdd2() { }
	 * 
	 * @Test(timeout = 3000) //This test will fail if it does not complete within 3
	 * seconds. public void testTimeOut() { while(true) {
	 * 
	 * } }
	 */
	
}
