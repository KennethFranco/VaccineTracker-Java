package app.components;
import java.util.List;


import org.springframework.stereotype.Component;

import app.entity.Faculty;
import app.entity.Student;
import app.entity.Vaccine;

@Component
public class FacultyManager {

	public String createFaculty(String name, String vaccineName, Boolean vaccineStatus) {
		// TODO Auto-generated method stub
		return "Faculty Created!";
	}

	public String deleteFaculty(Long id) {
		// TODO Auto-generated method stub
		return "Faculty Deleted!";
	}

	public String viewFacultyDetails(Long id) {
		// TODO Auto-generated method stub
		return "Faculty Details:";
	}

	public List<Faculty> viewAllFaculty() {
		// TODO Auto-generated method stub
		return null;
	}

	public String updateFaculty(Long id, String name, String vaccineName, Boolean vaccineStatus) {
		// TODO Auto-generated method stub
		return "Faculty Updated!";
	}

}
