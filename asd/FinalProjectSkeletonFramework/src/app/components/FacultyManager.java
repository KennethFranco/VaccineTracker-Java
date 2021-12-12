package app.components;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Faculty;
import app.entity.Student;
import app.entity.Vaccine;
import app.repository.FacultyRepository;

@Component
public class FacultyManager {
	
	@Autowired
	private FacultyRepository FacRepo;

	public String createFaculty(String name, String vaccineName, Boolean vaccineStatus) {
		// TODO Auto-generated method stub
		Faculty newFaculty = new Faculty();
		newFaculty.setName(name);
		newFaculty.setVaccineName(vaccineName);
		newFaculty.setVaccineStatus(vaccineStatus);
		FacRepo.save(newFaculty);
		
		return "Faculty Created!";
	}

	public String deleteFaculty(Long id) {
		// TODO Auto-generated method stub
		FacRepo.deleteById(id);
		return "Faculty Deleted!";
	}

	public java.util.Optional<Faculty> viewFacultyDetails(Long id) {
		// TODO Auto-generated method stub
		
		return FacRepo.findById(id);
	}

	public List<Faculty> viewAllFaculty() {
		// TODO Auto-generated method stub
		return FacRepo.findAll();
	}

	public String updateFaculty(Long id, String name, String vaccineName, Boolean vaccineStatus) {
		// TODO Auto-generated method stub
		Faculty facultyToUpdate = FacRepo.getById(id);
		facultyToUpdate.setName(name);
		facultyToUpdate.setVaccineName(vaccineName);
		facultyToUpdate.setVaccineStatus(vaccineStatus);
		FacRepo.save(facultyToUpdate);
		
		
		return "Faculty Updated!";
	}

}