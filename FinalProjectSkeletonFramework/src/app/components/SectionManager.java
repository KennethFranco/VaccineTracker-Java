package app.components;
import java.util.List;


import org.springframework.stereotype.Component;

import app.entity.Section;
import app.entity.Student;
import app.entity.Vaccine;

@Component
public class SectionManager {

	public String createSection(String name, String deptName) {
		// TODO Auto-generated method stub
		return "Section Created!";
	}

	public String deleteSection(Long id) {
		// TODO Auto-generated method stub
		return "Section Deleted!";
	}

	public String viewSectionDetails(Long id) {
		// TODO Auto-generated method stub
		return "Section Details:";
	}

	public String updateSection(String name, String deptName) {
		// TODO Auto-generated method stub
		return "Section Updated!";
	}

	public String addStudentToSection(Long studentID, Long sectionID) {
		// TODO Auto-generated method stub
		return "Student added to Section!";
	}

	public String removeStudentFromSection(Long studentID, Long sectionID) {
		// TODO Auto-generated method stub
		return "Student removed from Section!";
	}

	public String addFacultyToSection(Long facultyID, Long sectionID) {
		// TODO Auto-generated method stub
		return "Faculty added to Section!";
	}

	public String removeFacultyFromSection(Long facultyID, Long sectionID) {
		// TODO Auto-generated method stub
		return "Faculty removed from Section!";
	}

}
