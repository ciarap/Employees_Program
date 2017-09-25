/**
* @author Ciara Power
* @version 20/04/16
*/

public abstract class Employee {      // abstract as there will be no just "employee", will be specific - manager/salesWorker/tempWorker
 
	private String firstName;      //fields common to all subclasses
	private String lastName;
	private double hourlyRate;
	final static double NORMAL_WORKWEEK = 37.5;
	
	/**
	 * Accessors for each field in the class
	 * @return each field
	 */

	public String getFirstName() {
		return firstName;
	}

	public String getSecondName() {
		return lastName;
	}

	public double getHourlyRate() {
		return hourlyRate;
	}

	/** 
	 * Following methods are mutators for common subclass fields.
	 * @param firstName/lastName/hourlyRate
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setSecondName(String lastName) {
		this.lastName = lastName;
	}

	public void setHourlyRate(double hourlyRate) {
		if (hourlyRate >= 0) {      // if it is greater than 0, allow the change, if not, keep it at original value
			this.hourlyRate = hourlyRate;
		}

	}

	abstract double calculateSalary(double numHours);   //abstract method, this method is present in all subclasses
	
/**
 * Employee()- constructor for employee, never used on its own as employee is abstract
 * @param firstName
 * @param lastName
 * @param hourlyRate
 */
	public Employee(String firstName, String lastName, double hourlyRate) {    // method called when creating objects in subclasses, sets up the common fields
		this.firstName = firstName;
		this.lastName = lastName;
		if (hourlyRate <= 0) {     //if less than 0, set it to 0
			this.hourlyRate = 0.0;
		} else {
			this.hourlyRate = hourlyRate;
		}

	}

	public Employee() {    //default constructor needed for xml use

	}

	/**
	 * calculateOvertime()- method to calculate how much overtime an employee worked that week
	 * @param numHours 
	 * @return the double value for overtime hours
	 */
	public double calculateOvertime(double numHours) {    // used in all subclasses
		if (numHours > NORMAL_WORKWEEK) {  // overtime only occurs if hours worked are more than the weekly normal 
			double overtime = numHours - NORMAL_WORKWEEK;
			return overtime;
		} else {
			return 0.0;
		}
	}

	/**
	 * toString() method to return a string of the employee fields and their values for the employee in question.
	 * This string is only ever overwritten by subclass toString methods, then called from them.
	 * @return String 
	 */
	public String toString() {
		String employeeDetails = "First Name: " + firstName + "\nSecond Name: " + lastName + "\nHourly Rate: €"
				+ hourlyRate + "\n";
		return employeeDetails;

	}
}
