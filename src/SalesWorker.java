/**
* @author Ciara Power
* @version 20/04/16
*/

public class SalesWorker extends Employee implements IBonusWorkers {   // superclass is Employee
	private double salesBonusPercentage = 0.0;

	
	/**
	 * getBonus() method returns the bonus percentage rate for the worker
	 * @return salesBonusPercentage
	 */
	public double getBonus() {
		return salesBonusPercentage;
	}
	
	/**
	 * setBonus(double) method to change the field for the sales workers bonus
	 * @param bonus
	 */

	public void setBonus(double bonus) {
		if (bonus >= 0 && bonus <= 20) {
			this.salesBonusPercentage = bonus;
		}
	}
	
	/**
	 * calculateSalary(double) to calculate the salary specifically for a salesWorker 
	 * @return double salary
	 */
	public double calculateSalary(double numHours) {
		double overtimeHours = super.calculateOvertime(numHours);
		double salary = 0.0;
		if (numHours >= 0) {
			salary = (numHours - overtimeHours) * super.getHourlyRate() + (overtimeHours * super.getHourlyRate()) * 2;
			salary += (salary * salesBonusPercentage / 100.0);
		}
		return salary;
	}

	/**
	 * SalesWorker() constructor - creates the salesWorker object and sets up field values
	 * @param firstName
	 * @param lastName
	 * @param hourlyRate
	 * @param salesBonusPercentage
	 */
	
	public SalesWorker(String firstName, String lastName, double hourlyRate, double salesBonusPercentage) {
		super(firstName, lastName, hourlyRate);
		if (salesBonusPercentage <= 0.0 || salesBonusPercentage >= 20) {   //if not within this range
			this.salesBonusPercentage = 0.0;
		} else {
			this.salesBonusPercentage = salesBonusPercentage;
		}
	}

	public SalesWorker() {  //default for xml use

	}

	/**
	 * toString() overrides the superclass method , but calls it and adds on to it , to be specific for a salesWorker object
	 * @return String
	 */
	
	public String toString() {
		String employeeDetails = super.toString() + "Sales Bonus Percentage: " + salesBonusPercentage
				+ "%\nPosition: Sales Employee\n-------------------------";
		return employeeDetails;
	}



}
