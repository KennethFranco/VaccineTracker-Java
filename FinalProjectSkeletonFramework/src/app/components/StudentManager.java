package app.components;

import java.util.List;

import org.springframework.stereotype.Component;

import app.entity.Student;
import app.entity.Vaccine;

@Component
public class StudentManager {

	public String createStudent(String name, int year, String vaccineName, Boolean vaccineStatus) {
		// TODO Auto-generated method stub
		return "Student Created!";
	}

	public String deleteStudent(Long id) {
		// TODO Auto-generated method stub
		return "Student Deleted!";
	}

	public String viewStudentDetails(Long id) {
		// TODO Auto-generated method stub
		return "Student Details:";
	}

	public List<Student> viewAllStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	public String updateStudent(Long id, String name, int year, String vaccineName, Boolean vaccineStatus) {
		// TODO Auto-generated method stub
		return "Student Updated!";
	}

}
