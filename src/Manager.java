import java.util.ArrayList;
import java.util.List;

/**
* @author Ciara Power
* @version 20/04/16
*/

public class Manager extends Employee implements IBonusWorkers {   //subclass of employee
	private double managerBonus;
	private List<Employee> departmentEmployees;   //employees in the department

	/**
	 * getBonus() and getDept() methods to access fields specific to manager objects
	 * @return  managerBonus / departmentEmployees
	 */
	public double getBonus() {
		return managerBonus;
	}

	public List<Employee> getDept() {
		return departmentEmployees;
	}

	/**
	 * setBonus(double) and setDept(List<Employee> to mutate the fields for managers
	 * @param managerBonus / departmentEmployees
	 */
	public void setBonus(double bonus) {
		if (bonus >= 0) {
			this.managerBonus = bonus;
		}
	}

	public void setDept(List<Employee> departmentEmployees) {
		this.departmentEmployees = departmentEmployees;
	}

	/**
	 * calculateSalary(double) to calculate the salary specifically for a manager
	 * @return double value for salary 
	 */
	public double calculateSalary(double numHours) {
		double overtimeHours = super.calculateOvertime(numHours);
		double salary = 0.0;
		if (numHours >= 0) {   //must work hours to receive salary
			salary = (numHours - overtimeHours) * super.getHourlyRate() + (overtimeHours * super.getHourlyRate()) * 2
					+ managerBonus;

		}

		return salary;

	}

	/**
	 * Manager() constructor - creates the manager object and sets up field values
	 * @param firstName
	 * @param lastName
	 * @param hourlyRate
	 * @param managerBonus
	 */
	public Manager(String firstName, String lastName, double hourlyRate, double managerBonus) {
		super(firstName, lastName, hourlyRate);
		if (managerBonus <= 0) {    // bonus must be positive value
			this.managerBonus = 0.0;
		} else {
			this.managerBonus = managerBonus;
		}
		departmentEmployees = new ArrayList<Employee>();   //new empty department list for the manager

	}

	public Manager() {   //in use for xml file

	}

	/**
	 * toString() overrides the superclass method , but calls it and adds on to it , to be specific for a manager object
	 * @return String
	 */
	public String toString() {
		String employeeDetails = super.toString() + "Manager Bonus: €" + managerBonus
				+ "\nPosition: Manager\nNumber of Employees in Department:" + numberInDept()
				+ "\n-------------------------";
		return employeeDetails;

	}
	/**
	 * addDeptEmployee() method simply adds an employee to the department
	 * @param employee
	 */
	public void addDeptEmployee(Employee employee) {
		departmentEmployees.add(employee);
	}

	/**
	 * numberInDept() method returns how many are in the manager's department
	 * @return integer value 
	 */
	public int numberInDept() {
		return departmentEmployees.size();
	}

	/**
	 * deleteDeptEmployee(Employee) removes an employee from the manager's department
	 * @param employee
	 */
	public void deleteDeptEmployee(Employee employee) {
		departmentEmployees.remove(employee);
	}

}
