package app.components;
import java.util.List;

import org.springframework.stereotype.Component;

import app.entity.Faculty;
import app.entity.Student;
import app.entity.Vaccine;
import app.entity.Department;

@Component
public class DepartmentManager {

	public String createDepartment(String name) {
		// TODO Auto-generated method stub
		return "Department Created!";
	}

	public String deleteDepartment(Long id) {
		// TODO Auto-generated method stub
		return "Department Deleted!";
	}

	public String viewDepartmentDetails(Long id) {
		// TODO Auto-generated method stub
		return "Department Details:";
	}

	public String updateDepartment(Long id, String name) {
		// TODO Auto-generated method stub
		return "Department Updated!";
	}

	public String addStudentToDepartment(Long studentID, Long departmentID) {
		// TODO Auto-generated method stub
		return "Student added to Department!";
	}

	public String removeStudentFromDepartment(Long studentID, Long departmentID) {
		// TODO Auto-generated method stub
		return "Student removed from Department!";
	}

	public String removeFacultyFromDepartment(Long facultyID, Long departmentID) {
		// TODO Auto-generated method stub
		return "Faculty removed from Department!";
	}

	public String addFacultyToDepartment(Long facultyID, Long departmentID) {
		// TODO Auto-generated method stub
		return "Faculty added to Department!";
	}

}
