/**
* @author Ciara Power
* @version 20/04/16
*/

public class TempWorker extends Employee {   //superclass is Employee

	/**
	 * calculateSalary(double) to calculate the salary specifically for a tempWorker 
	 * @return double value for salary
	 */
	public double calculateSalary(double numHours) {
		double overtimeHours = super.calculateOvertime(numHours);  //get overtime hours worked
		double salary = 0.0;
		if (numHours >= 0) {
			salary = (numHours - overtimeHours) * super.getHourlyRate() + (overtimeHours * super.getHourlyRate()) * 2; //no bonuses for temp
		}

		return salary;
	}

	/**
	 * TempWorker() constructor - creates the tempWorker object and sets up field values
	 * @param firstName
	 * @param lastName
	 * @param hourlyRate
	 */
	
	public TempWorker(String firstName, String lastName, double hourlyRate) {
		super(firstName, lastName, hourlyRate);
	}

	public TempWorker() {  //default constructor for xml use

	}

	
	/**
	 * toString() overrides the superclass method , but calls it and adds on to it , to be specific for a tempWorker object
	 * @return String
	 */
	
	public String toString() {
		String employeeDetails = super.toString() + "Position: Temporary Employee\n-------------------------";
		return employeeDetails;
	}

}
