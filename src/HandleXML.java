import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HandleXML {

	public static void write(List<Employee> employees, String filename) throws Exception {
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		encoder.writeObject(employees);
		encoder.close();
	}

	public static List<Employee> read(String filename) throws Exception {
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
		List<Employee> employees = (List<Employee>) decoder.readObject();
		decoder.close();
		return employees;
	}

}