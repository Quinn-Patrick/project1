package p1unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.models.Reimb;

import services.ReimbService;
import services.UserService;

public class ReimbursementTests {
	private Reimb r = null;
	private static int id;

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
		ReimbService.deleteReimb(id);
	}

	/*
	 * This method will be invoked before each individual test case.
	 * This is primarily used to set up specific test environments, or perhaps to rest
	 * the test environment.
	 */
	@Before
	public void setUp() throws Exception {
		r = new Reimb(100.00, LocalDateTime.now(), null, "", 0, -1, 0, 0);
		id = r.getReimbId();
	}

	/*
	 * This method will be invoked after each individual test case.
	 * This is primarily used to reset the test environment.
	 */
	@After
	public void tearDown() throws Exception {
		ReimbService.deleteReimb(id);
	}

	@Test
	public void testAmount() {
		r.setAmount(200.00);
		assertEquals(r.getAmount(), 200, 0.01);
	}
	
	@Test
	public void testDates() {
		LocalDateTime d = LocalDateTime.now();
		r.setSubmitted(d);
		assertEquals(r.getSubmitted(), d);
		
		r.setResolved(d);
		assertEquals(r.getResolved(), d);
		
	}
	
	@Test
	public void testDescription() {
		r.setDescription("test");
		assertEquals(r.getDescription(), "test");
	}
	
	@Test
	public void testAuthorAndResolver() {
		r.setAuthor(1);
		r.setResolver(2);
		ReimbService.storeReimb(r);
		r = ReimbService.retrieveReimb(id);
		assertEquals(1, r.getAuthor());
		assertEquals(2, r.getResolver());
		assertEquals("user1", r.getAuthorName());
		assertEquals("admin1", r.getResolverName());
		
	}
	
	@Test
	public void testTypeAndStatus() {
		r.setType(3);
		r.setStatus(1);
		ReimbService.storeReimb(r);
		r = ReimbService.retrieveReimb(id);
		assertEquals(3, r.getType());
		assertEquals(1, r.getStatus());
		assertEquals("Other", r.getTypeName());
		assertEquals("Approved", r.getStatusName());
		
	}
	
	@Test
	public void testMisc() {
		assertTrue(r.equals(r));
		System.out.println(id);
		ReimbService.storeReimb(r);
		r = ReimbService.retrieveReimb(id);
		Reimb r2 = ReimbService.retrieveReimb(id);
		System.out.println(r);
		System.out.println(r2);
		assertTrue(r.equals(r2));
		System.out.println(r.toString());
		System.out.println(r.hashCode());
	}
}
