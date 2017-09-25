import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Class for the SalesWorker class
 * 
 * @author Ciara Power
 * @version 20/04/16
 */

public class SalesWorkerTest {

	private SalesWorker sales1, sales2, invalidSales1, invalidSales2;

	/**
	 * Method to set up data for testing.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		sales1 = new SalesWorker("John", "Mooney", 8.5, 15);
		sales2 = new SalesWorker("Francis", "Murphy", 10, 0);
		invalidSales1 = new SalesWorker("Ron", "Burgundy", -1, 21);
		invalidSales2 = new SalesWorker("Chandler", "Bing", -4567, -1);
	}

	/**
	 * Test method for AdminWorker constructor
	 */
	@Test
	public void testConstructor() {
		// test on valid data
		assertEquals("John", sales1.getFirstName());
		assertEquals("Mooney", sales1.getSecondName());
		assertEquals(8.5, sales1.getHourlyRate(), 0.01);
		assertEquals(15, sales1.getBonus(), 0.01);
		// test on invalid data
		assertEquals(0, invalidSales1.getHourlyRate(), 0.01);
		assertEquals(0, invalidSales2.getHourlyRate(), 0.01);
		assertEquals(0, invalidSales1.getBonus(), 0.01);
		assertEquals(0, invalidSales2.getBonus(), 0.01);

	}

	/**
	 * Test method for calculateSalary(), testing for employees with and without
	 * overtime, and with or without bonus percentage.
	 */
	@Test
	public void testCalculateSalary() {
		// sales2 has no bonus
		assertEquals(375, sales2.calculateSalary(37.5), 0.01);
		assertEquals(425, sales2.calculateSalary(40), 0.01);

		// sales1 has bonus
		assertEquals(293.25, sales1.calculateSalary(30), 0.01);
		assertEquals(298.8002, sales1.calculateSalary(30.5678), 0.01);
	}

	/**
	 * Test method for getters and setters.
	 */
	@Test
	public void testSettersGetters() {
		assertEquals("John", sales1.getFirstName());
		sales1.setFirstName("Freddy Mercury");
		assertEquals("Freddy Mercury", sales1.getFirstName());

		assertEquals("Mooney", sales1.getSecondName());
		sales1.setSecondName("Rooney");
		assertEquals("Rooney", sales1.getSecondName());

		sales1.setHourlyRate(40);
		assertEquals(40, sales1.getHourlyRate(), 0.01);

		sales1.setBonus(13);
		assertEquals(13, sales1.getBonus(), 0.01);

		// ensure no change when invalid data used
		sales1.setHourlyRate(-40);
		assertEquals(40, sales1.getHourlyRate(), 0.01);

		sales1.setBonus(-1);
		assertEquals(13, sales1.getBonus(), 0.01);
		sales1.setBonus(21);
		assertEquals(13, sales1.getBonus(), 0.01);
	}

	/**
	 * Test for toString() method for both test salesWorkers
	 */
	@Test
	public void testToString() {
		assertEquals(
				"First Name: John\nSecond Name: Mooney\nHourly Rate: €8.5"
						+ "\nSales Bonus Percentage: 15.0%\nPosition: Sales Employee\n-------------------------",
				sales1.toString());
		assertEquals(
				"First Name: Francis\nSecond Name: Murphy\nHourly Rate: €10.0"
						+ "\nSales Bonus Percentage: 0.0%\nPosition: Sales Employee\n-------------------------",
				sales2.toString());
	}

}