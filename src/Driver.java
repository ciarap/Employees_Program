/** EXTRA FEATURES
 *  - Delete a chosen employee completely from the system
 *  - Delete a chosen employee from a chosen department
 *  - Edit a chosen employees wage details ( hourly rate / any bonuses)
 *  - Save employees to xml file  
 *  - Load employees from xml file (this load populates the managers / allWorkers arrayLists also)
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
* @author Ciara Power
* @version 20/04/16
*/
public class Driver {

	private Scanner input = new Scanner(System.in);
	private List<Employee> employees;    // all employees
	private List<Manager> managers;		// all managers
	private List<Employee> allWorkers;  // all salesWorkers and tempWorkers
	private List<Double> employeeNumHours;  // to store the number of hours for each employee, when calculating all salaries at once

	public Driver() {
		input = new Scanner(System.in);
		employees = new ArrayList<Employee>();    //lists are instantiated as ArrayLists through use of interfaces
		managers = new ArrayList<Manager>();
		allWorkers = new ArrayList<Employee>();
		employeeNumHours = new ArrayList<Double>();

	}

	public static void main(String[] args) {
		Driver app = new Driver();
		app.run();

	}

	/**
	 * mainMenu() - This method displays the menu for the application, reads the
	 * menu option that the user entered and returns it.
	 * 
	 * @return the users menu choice
	 */

	private int mainMenu() {
		System.out.println("-----------------------------");
		System.out.println("    EMPLOYEE ACTION MENU");
		System.out.println("-----------------------------");
		System.out.println("  1) Add an Employee");
		System.out.println("  2) Delete an Employee");
		System.out.println("  3) List all Employee's details");
		System.out.println("  -------------------------");
		System.out.println("  4) Add an existing Employee to a Manager's department");
		System.out.println("  5) Delete an existing Employee from a Manager's department");
		System.out.println("  -------------------------");
		System.out.println("  6) Edit an Employee's wage details");
		System.out.println("  7) Calculate salaries");
		System.out.println("  -------------------------");
		System.out.println("  8) Save Employees to employee.xml");
		System.out.println("  9) Load Employee List from employee.xml");
		System.out.println("  -------------------------");
		System.out.println("  0) Exit");
		System.out.print("==>>");
		int option;
		try {
			option = input.nextInt();
			while (option < 0 || option > 9) {   // must get option in the menu range
				System.out.println("Invalid choice,please choose again!");
				System.out.print("==>>");
				option = input.nextInt();
			}
		} catch (Exception e) {      // if input other than integer is entered
			System.out.println("---Invalid choice,please choose again!---");
			System.out.println("    ---Press any key to continue---");
			input.nextLine();
			input.nextLine();
			option = mainMenu();
		}

		return option;
	}

	/**
	 * run() - This method displays the menu and processes the user's menu
	 * choice. Option ‘0’ exits the program.
	 */
	private void run() {
		int option = mainMenu();
		while (option != 0) {
			switch (option) {
			case 1:
				addEmployee();
				break;
			case 2:
				if (employees.size() != 0) {    // must be employees on system to delete an employee
					deleteEmployee();
				} else {
					System.out.println("Please add Employees before choosing this option!.");
				}

				break;
			case 3:
				if (employees.size() != 0) {    // must be employees on system to list them
					int index = 1;
					for (Employee employee : employees) {
						System.out.println("------ EMPLOYEE " + index + " -------");
						System.out.println(employee.toString());    // manager/salesWorker/tempWorker toString is used, depending on employee object
						index++;
					}
				} else {
					System.out.println("There are no Employees.");
				}
				break;
			case 4:
				if (managers.size() != 0 && allWorkers.size() != 0) {  // must be at least one manager and one worker
					addEmployeeToDepartment();
				} else {
					System.out.println("There is not enough managers/worker employees currently employed!"
							+ "\nPlease have at least one manager and one worker employee before choosing this option from the menu.");
				}
				break;
			case 5:
				if (managers.size() != 0 && allWorkers.size() != 0 && anyDeptEmployees()) {   //have to have manager and workers, and at least one manager must have a worker in dept
					deleteDeptEmployee();
				} else {
					System.out.println("There is not enough employees currently in departments!"
							+ "\nPlease have at least one worker in one manager's department before choosing this option from the menu.");
				}
				break;
			case 6:
				if (employees.size() != 0) {    // there must be employees 
					editEmployeeDetails();
				} else {
					System.out.println("Please add Employees before choosing this option!.");
				}

				break;
			case 7:
				if (employees.size() != 0) {   // there must be employees 
					calculateSalaries();
				} else {
					System.out.println("Please add Employees before choosing this option!.");
				}
				break;
			case 8:
				try {
					save();
					System.out.println(">>>The current employee list has been saved!<<<");
				} catch (Exception e) {
					System.out.println("Error writing to file: " + e);
				}
				break;
			case 9:
				try {
					load();
					System.out.println(">>>An employee list has been successfully loaded!<<<");
				} catch (Exception e) {
					System.out.println("Error reading from file: " + e);
				}
				break;
			default:
				System.out.println("---Invalid option selected.---");
				break;
			}

			System.out.println("\nPress any key to continue...");
			input.nextLine();
			input.nextLine();
			System.out.println("");
			option = mainMenu();  // display the main menu again and get option
		}
		System.out.println("Exiting... bye");
	}
	
	/**
	 * addEmployee() - This method adds an employee to the system. The type of employee is asked, and details such
	 * as first name, second name, hourly wage rate, and any bonuses are input. The new employee is then
	 * added to the relevant arraylists. 
	 */

	public void addEmployee() {

		int option = addEmployeeMenu();     // get choice for type of employee from the menu
		System.out.print("Employee first name: ");
		String employeeFirstName = input.nextLine();
		employeeFirstName = input.nextLine();
		System.out.print("Employee second name: ");
		String employeeSecondName = input.nextLine();
		System.out.print("Employee hourly rate: ");
		double employeeHourlyRate = 0.0;
		try {
			employeeHourlyRate = input.nextDouble();
			switch (option) {     // different bonuses etc for different types of employee
			case 1:   // manager type
				System.out.print("Manager bonus: ");
				double managerBonus = 0.0;
				managerBonus = input.nextDouble();
				IBonusWorkers managerAdd = new Manager(employeeFirstName, employeeSecondName, employeeHourlyRate,
						managerBonus);      // create the new manager object
				employees.add( (Employee) managerAdd);   // add the same object to employees list
				managers.add((Manager) managerAdd);   // add the same object to managers list
				break;
			case 2:
				TempWorker tempWorkerAdd = new TempWorker(employeeFirstName, employeeSecondName, employeeHourlyRate); // new tempWorker
				employees.add(tempWorkerAdd);    // add same tempWorker to employees list
				allWorkers.add(tempWorkerAdd);    // add same tempWorker to allWorkers list
				break;
			case 3:
				double salesBonusPercentage;      
				salesBonusPercentage = getSalesBonus();   // call method to get bonus 
				IBonusWorkers salesWorkerAdd = new SalesWorker(employeeFirstName, employeeSecondName, employeeHourlyRate,
						salesBonusPercentage);    // new salesWorker
				employees.add((Employee) salesWorkerAdd);   // add same worker to employees list
				allWorkers.add((Employee) salesWorkerAdd);   // add to allWorkers list
				break;
			}
		} catch (Exception e) {    // if non integer is input
			System.out.println(
					"\n---Please enter a numeric value for bonuses and hourly rates!---\n---The employee enrollment process will now restart.---");
			System.out.println("\nPress any key to continue...");
			input.nextLine();
			input.nextLine();
			addEmployee();    // starts adding process again
		}

	}

	/**
	 * addEmployeeMenu() - This method displays the menu for adding an employee and processes the user's menu
	 * choice. This determines the type of employee to be created.
	 * 
	 * @return the users menu choice
	 */
	
	public int addEmployeeMenu() {      
		System.out.println("  -------------------------");
		System.out.println("Please choose the employee's position in the firm: ");
		System.out.println("  1) Manager");
		System.out.println("  2) Temporary Worker");
		System.out.println("  3) Sales Worker");
		System.out.println("  -------------------------");
		System.out.print("==>>");
		int option;
		try {
			option = input.nextInt();
			while (option != 1 && option != 2 && option != 3) {      // must be in menu range
				System.out.println("Invalid choice,please choose again!");
				System.out.print("==>>");
				option = input.nextInt();
			}
		} catch (Exception e) {     // if non integer input
			System.out.println("---Invalid choice,please choose again!---");
			System.out.println("    ---Press any key to continue---");
			input.nextLine();
			input.nextLine();
			option = addEmployeeMenu();   // restart this menu 
		}

		return option;
	}
	
	/**
	 * deleteEmployee() - method to completely remove an employee from the system.
	 */

	private void deleteEmployee() {
		try {
			int index = 1;
			System.out.println("---- LIST OF ALL EMPLOYEES ----");   // lists all workers and managers with an index 
			for (Employee employee : employees) {
				System.out.println(index + ": " + employee.getFirstName() + " " + employee.getSecondName());
				index++;
			}
			System.out.println("Please choose the employee to delete from the system:");
			int employeeOption = input.nextInt() - 1;      // choice-1 , as arraylists index start at 0
			while (employeeOption < 0 || employeeOption >= employees.size()) {    // choice must be in employee list range
				System.out.println("Invalid choice,please choose again!");
				System.out.print("==>>");
				employeeOption = input.nextInt() - 1;
			}
			Employee employee = employees.get(employeeOption);      //let variable equal the employee chosen
			employees.remove(employee);    //remove from employees list 
			if (employee instanceof Manager) {    // if manager object, remove from managers list also
				managers.remove(employee);
			} else {    // otherwise, it is a worker
				allWorkers.remove(employee);          //remove from allWorkers list
				for (Manager manager : managers) {    // for loop to remove employee from any managers departments
					if (manager.numberInDept() != 0) {    //manager has at least one employee in dept
						for (int i = 0; i < manager.numberInDept(); i++) {    // test each dept employee
							Employee employeeInDept = manager.getDept().get(i);    
							if (employeeInDept == employee) {     // if the employee in dept is equal to the emplyee being removed
								manager.deleteDeptEmployee(employeeInDept);   //delete employee from dept list (method called from manager class)
							}
						}
					}
				}
			}

		} catch (Exception e) {   //if non integer input
			System.out.println("---Invalid choice,please enter a numeric value!---");
			System.out.println("    ---Press any key to restart the process---");
			input.nextLine();
			input.nextLine();
			deleteEmployee();
		}

	}

	/**
	 * addEmployeeToDepartment()- method to add an existing employee to a chosen managers department.
	 * 
	 */
	public void addEmployeeToDepartment() {
		int index = 1;
		System.out.println("---- LIST OF AVAILABLE WORKER EMPLOYEES ----");   // worker employees (we dont want to add a manager to another managers department)
		List<Employee> listAvailableWorkers = new ArrayList<Employee>();   
		for (Employee employee : allWorkers) {
			if (!inAnyDept(employee)) {   // calls method which tests if an employee is in any department already
				listAvailableWorkers.add(employee);  // add employee to the availableWorkers list if they are not in any dept
			}

		}
		if (listAvailableWorkers.size() == 0) {    //if there are no available employees
			System.out.println("There are no employees that are not in a department already!");
		} else {
			for (Employee employee : listAvailableWorkers) {    //print each available employee with index
				System.out.println(index + ": " + employee.getFirstName() + " " + employee.getSecondName());
				index++;
			}

			System.out.println("Please choose the worker employee to add to a department:");
			int employeeOption;
			try {
				employeeOption = input.nextInt() - 1;
				while (employeeOption < 0 || employeeOption >= listAvailableWorkers.size()) {   //choice must be within range
					System.out.println("Invalid choice,please choose again!");
					System.out.print("==>>");
					employeeOption = input.nextInt() - 1;
				}
				index = 1;
				System.out.println("---- LIST OF MANAGERS ----");    // list managers (choose which managers dept to add the chosen employee to)
				for (Manager manager : managers) {
					System.out.println(index + ": " + manager.getFirstName() + " " + manager.getSecondName());
					index++;
				}
				System.out.println("Please choose the manager you want to allocate the chosen employee to:");
				int managerOption = input.nextInt() - 1;
				while (managerOption < 0 || managerOption >= managers.size()) {
					System.out.println("Invalid choice,please choose again!");
					System.out.print("==>>");
					managerOption = input.nextInt() - 1;
				}
				Manager manager = managers.get(managerOption);    // get the chosen manager employee object from managers list
				manager.addDeptEmployee(listAvailableWorkers.get(employeeOption));  // add the chosen employee to the chosen manager dept
				System.out.println("\n>> " + manager.getFirstName() + " " + manager.getSecondName() + " now has "
						+ manager.numberInDept() + " employee(s) in their department. <<");  // updated dept numbers

			} catch (Exception e) {    //if non integer input
				System.out.println("     ---Invalid choice,please enter a numeric value!---");
				System.out.println("---Press any key to restart the department addition process---");
				input.nextLine();
				input.nextLine();
				addEmployeeToDepartment();
			}
		}

	}
	
	/**
	 * checkBonusQualified()- method to give the user a choice when creating a sales worker, regarding the bonus for the worker.
	 * 
	 * @return a boolean to indicate if the salesWorker gets a bonus
	 */

	public boolean checkBonusQualified() {
		boolean qualified = true;
		int option = 0;
		System.out.println("Please indicate if this sales worker qualifies for a bonus:");
		System.out.println("  1) Yes");
		System.out.println("  2) No");
		System.out.println("  -------------------------");
		System.out.print("==>>");
		try {
			option = input.nextInt();
			while (option != 1 && option != 2) {
				System.out.println("Invalid choice,please choose again!");
				System.out.print("==>>");
				option = input.nextInt();
			}
			if (option == 1) {
				qualified = true;    // sales worker gets a bonus
			} else if (option == 2) {
				qualified = false;   //sales worker does not get a bonus (bonus will equal 0)
			}
		} catch (Exception e) {    //non integer input
			System.out.println("     ---Invalid choice,please enter a numeric value!---");
			System.out.println("---Press any key to restart the bonus qualification process---");
			input.nextLine();
			input.nextLine();
			qualified = checkBonusQualified();
			;
		}

		return qualified;
	}

	/**
	 * calculateSalaries()- method to choose options for calculating salaries, and showing the results.
	 * 
	 */
	public void calculateSalaries() {
		
		try {
			int option = calculateSalariesMenu();   //gets choice from menu
            
			if (option == 1) {  // if choice is to calculate an individual employees salary
				int index = 1;
				System.out.println("---- LIST OF ALL EMPLOYEES ----");
				for (Employee employee : employees) {
					System.out.println(index + ": " + employee.getFirstName() + " " + employee.getSecondName());
					index++;
				}
				System.out.println("-------------------------------");
				System.out.println("Please choose the employee to calculate salary for:");
				int employeeOption = input.nextInt() - 1; //arraylist index starts at 0
				while (employeeOption < 0 || employeeOption >= employees.size()) {   //in range
					System.out.println("Invalid choice,please choose again!");
					System.out.print("==>>");
					employeeOption = input.nextInt() - 1;
				}
				Employee employee = employees.get(employeeOption);  
				System.out.println("Please enter the hours worked by " + employee.getFirstName() + " "
						+ employee.getSecondName() + " this week:");  // enter the hours for the current week you want to calculate salary for
				double employeeNumHours = input.nextDouble(); 
				System.out.println("---- SALARY ----");
				System.out.println(employee.getFirstName() + " " + employee.getSecondName() + "'s salary: €"
						+ employee.calculateSalary(employeeNumHours));   //calls method to get salary figure (will depend on object instance)
				System.out.println("----------------");
			} else {    //if we want all salaries and a total
				employeeNumHours.clear();   // clear numHours list , creat lost each time we calculate salaries
				int index = 1;
				System.out.println("---- EMPLOYEES HOURS ----");
				for (Employee employee : employees) {
					System.out.println(index + ": " + employee.getFirstName() + " " + employee.getSecondName()
							+ "'s hours for this week: ");
					System.out.print("==>>");    //enter each employees hours

					employeeNumHours.add(input.nextDouble()); //add the hours to the arraylist for hours
					index++;
				}

				System.out.println("-------------------------\n");
				System.out.println("---- EMPLOYEES SALARIES ----");
				index = 1;
				double totalSalary = 0.0;
				for (Employee employee : employees) {
					double salary = employee.calculateSalary(employeeNumHours.get(index - 1));  // gets the hours from the list relating to this employee
					System.out.println(index + ": " + employee.getFirstName() + " " + employee.getSecondName()
							+ "'s salary: €" + salary);
					index++;
					totalSalary += salary;   // add each salary to the total
				}
				System.out.println("----------------------------");
				System.out.println("Total of all employees salaries: €" + totalSalary);
				System.out.println("----------------------------");

			}

		} catch (Exception e) {    // incorrect input type
			System.out.println("     ---Invalid choice,please enter a numeric value!---");
			System.out.println("---Press any key to restart the calculate salaries process---");
			input.nextLine();
			input.nextLine();
			calculateSalaries();
		}
	}
	
	/**
	 * calculateSalariesMenu()- Gives the user options to choose if they want to calculate one salary or calculate all with a total
	 * 
	 * 
	 * @return user choice for salaries menu
	 */

	public int calculateSalariesMenu() {
		System.out.println("Please choose a salary option: ");
		System.out.println("  1) Calculate an individual employee's salary");
		System.out.println("  2) Show each employee's salary and the total of all employees' salaries");
		System.out.println("  -------------------------");
		System.out.print("==>>");
		int option = input.nextInt();
		while (option != 1 && option != 2) {    // must be in menu range
			System.out.println("Invalid choice,please choose again!");
			System.out.print("==>>");
			option = input.nextInt();
		}
		return option;
	}
	
	/**
	 * editEmployeeDetails()- method that allows the user to change the wage/bonus details of a selected employee.
	 * Names cannot be changed, as the feature is used to edit an existing employee only, not replace them with a new one. 
	 */

	public void editEmployeeDetails() {
		int index = 1;
		System.out.println("---- LIST OF ALL EMPLOYEES ----");    // list all employees to choose from
		for (Employee employee : employees) {
			System.out.println(index + ": " + employee.getFirstName() + " " + employee.getSecondName());
			index++;
		}
		System.out.println("-------------------------------");
		System.out.println("Please choose the employee you wish to edit wage details for:");
		int employeeOption;
		try {
			employeeOption = input.nextInt() - 1;
			Employee employee = employees.get(employeeOption);   //get the selected employee from the list and store in variable
			System.out.println("---- EDIT EMPLOYEE DETAILS ----");
			System.out.println(
					">> Editing " + employee.getFirstName() + " " + employee.getSecondName() + "'s Details <<");
			System.out.print("Employee's new hourly rate: ");

			employee.setHourlyRate(input.nextDouble());   // sets new hourly rate
			if (employee instanceof Manager) {   // if the employee is a manager we need to change the manager bonus
				Manager manager = (Manager) employees.get(employeeOption);   // cast the employee to a Manager variable to allow manager methods be used
				System.out.println("Please enter the manager's bonus:");

				manager.setBonus(input.nextDouble());   // set the bonus

			} else if (employee instanceof SalesWorker) {   // if employee is salesworker
				SalesWorker salesWorker = (SalesWorker) employees.get(employeeOption); // cast employee choice to a SalesWorker variable to allow the SalesWorker bonus method be used

				salesWorker.setBonus(getSalesBonus());   // calls method to get bonus percentage (same as when adding salesworker employee)

			}

		} catch (Exception e) {   // non numeric input
			System.out.println("   ---Invalid choice,please enter a numeric value!---");
			System.out.println("---Press any key to restart the edit details process---");
			input.nextLine();
			input.nextLine();
			editEmployeeDetails();
		}
	}
	
	/**
	 * getSalesBonus()-method to get the sales workers bonus percentage rate from the user.
	 * Ensures the rate input is within the given range (0-20%)
	 * @return the double value for bonus percentage
	 */

	public double getSalesBonus() {    
		double salesBonusPercentage;
		if (checkBonusQualified()) {   // if worker qualifies for bonus (choice given in boolean method that is called )
			System.out.print("Sales Worker's Bonus Percentage (Must be between 0% and 20%) : ");
			salesBonusPercentage = input.nextDouble();
			while (salesBonusPercentage < 0 || salesBonusPercentage >= 20) {   //if not within given range
				System.out.println(
						"The bonus percentage must be in the range 0-20%.\nPlease enter a valid bonus percentage:");
				System.out.print("==>>");
				salesBonusPercentage = input.nextDouble();
			}
		} else {
			salesBonusPercentage = 0.0; // if not qualified for bonus, set to 0 
		}
		return salesBonusPercentage;
	}
	
	/**
	 * anyDeptEmployees()- method to check if any manager has employees in dept.
	 * @return boolean value indicating if there is employees in any department
	 */

	public boolean anyDeptEmployees() {
		boolean anyDeptEmployees = false;   //set that none have employees
		for (Manager manager : managers) {
			if (manager.numberInDept() != 0) {    // if manager has employees in dept, set to true, 
				anyDeptEmployees = true;
			
		}
		}
		return anyDeptEmployees;
	}
	
	/**
	 * inAnyDept()- method to check if a chosen employee is in any managers department already
	 * @param employee
	 * @return boolean value to indicate if the employee is in any department
	 */

	public boolean inAnyDept(Employee employee) {    // method to check if the employee passed in as parameter is in any dept already
		boolean inAnyDept = false;    //start off that they are not in dept ,until proven otherwise
		for (Manager manager : managers) {  //check each manager dept
			for (Employee employeeInDept : manager.getDept()) {     // run through each employee in the dept
				if (employeeInDept == employee) {   // if employee in dept is the same as employee passed in to check,chosen employee is in a dept
					inAnyDept = true;
				}
			}
		}
		return inAnyDept;
	}
	/**
	 * deleteDeptEmployee()-method to remove an employee from a chosen managers department
	 */
	public void deleteDeptEmployee() {
		int index = 1;
		System.out.println("---- LIST OF MANAGERS ----");  
		for (Employee employee : managers) {    //only want to list managers
			System.out.println(index + ": " + employee.getFirstName() + " " + employee.getSecondName());
			index++;
		}
		System.out.println("Please choose the manager you want to delete a department employee for:");
		int managerOption;
		try {
			managerOption = input.nextInt() - 1;
			while (managerOption < 0 || managerOption >= managers.size()) {   //manager choice within range
				System.out.println("Invalid choice,please choose again!");
				System.out.print("==>>");
				managerOption = input.nextInt() - 1;
			}
			Manager manager = managers.get(managerOption);   // get the chosen employee and put in manager variable 
			while (manager.numberInDept() == 0) {    // if the choice has an empty dept (while loop will eventually end as this delete method 
				                                               //can only be called if at least one manager has an employee in their dept.
				System.out.println("This manager has no employees in their department,please choose again!");
				System.out.print("==>>");
				managerOption = input.nextInt() - 1;
				manager = managers.get(managerOption);
			}

			index = 1;
			System.out.println("---- LIST OF DEPARTMENT EMPLOYEES ----");
			for (Employee employee : manager.getDept()) {     // list each employee in the chosen manager dept
				System.out.println(index + ": " + employee.getFirstName() + " " + employee.getSecondName());
				index++;
			}
			System.out.println("Please choose the employee to delete from the department:");
			int employeeOption = input.nextInt() - 1;
			while (employeeOption < 0 || employeeOption >= (manager.numberInDept())) {   //must be within range
				System.out.println("Invalid choice,please choose again!");
				System.out.print("==>>");
				employeeOption = input.nextInt() - 1;
			}

			manager.deleteDeptEmployee(manager.getDept().get(employeeOption));  // calls method in manager class to remove chosen employee 
			System.out.println("\n>> " + manager.getFirstName() + " " + manager.getSecondName() + " now has "
					+ manager.numberInDept() + " employee(s) in their department. <<");   //updated numbers

		} catch (Exception e) {
			System.out.println("---Invalid choice,please enter a numeric value!---");
			System.out.println("    ---Press any key to restart the process---");
			input.nextLine();
			input.nextLine();
			deleteDeptEmployee();
		}
	}

	public void load() throws Exception {     // load employees from xml file
		employees = HandleXML.read("employees.xml");    //all employees loaded into employees arraylist
		managers.clear();    // clear previous list for managers
		allWorkers.clear();  // clear previous list for allWorkers
		for (Employee employee : employees) {   //go through each employee loaded
			if (employee instanceof Manager) {   // if its a manager then add to the managers list
				managers.add((Manager) employee);
			} else {
				allWorkers.add(employee);  //otherwise it is a worker for allWorkers list
			}
		}
	}

	public void save() throws Exception {
		HandleXML.write(employees, "employees.xml");    //save employees list to xml file

	}

}
