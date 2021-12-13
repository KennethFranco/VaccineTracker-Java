package app.components;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Faculty;
import app.entity.Student;
import app.entity.Vaccine;
import app.repository.FacultyRepository;
import app.repository.VaccineRepository;

@Component
public class FacultyManager {
	
	@Autowired
	private FacultyRepository FacRepo;
	
	@Autowired
	private VaccineRepository VacRepo;

	public String createFaculty(String name, String vaccineName, Boolean vaccineStatus) {
		// TODO Auto-generated method stub
		
		List<Faculty> allFaculty = FacRepo.findAll();
		List<Vaccine> allVaccines = VacRepo.findAll();
		
		for(Faculty i: allFaculty) {
			String checkerName = i.getName();
			if (name.equals(checkerName)) {
				return "The name of the Faculty you are trying to add already has a record that exists in the database! Please provide a unique name.";
			}
		}
		
		Boolean vaccineExists = false;
		for(Vaccine i: allVaccines) {
			String checkerVaccineName = i.getName();
			if(vaccineName.equals(checkerVaccineName)) {
				vaccineExists = true;
				break;
			}
			else {
				vaccineExists = false;
			}
			
		}
		
		
		String returnMessage = "";
		if (vaccineExists == true) {
			Faculty newFaculty = new Faculty();
			newFaculty.setName(name);
			newFaculty.setVaccineName(vaccineName);
			newFaculty.setVaccineStatus(vaccineStatus);
			newFaculty.setSections("");
			newFaculty.setDeptName("");
			FacRepo.save(newFaculty);
			
			returnMessage = "Faculty Created!";
		}
		else if (vaccineExists == false) {
			returnMessage = "The vaccine you are trying to set to the student does not exist! Please provide a valid vaccine name.";
		}
		
		return returnMessage;
		
		
	}

	public String deleteFaculty(Long id) {
		// TODO Auto-generated method stub
		
		Faculty facToDelete = FacRepo.getById(id);
		
		if (facToDelete==null) {
			return "The Faculty you are trying to delete does not exist! Please provide a valid ID.";
		}
		String currentSectionsOfFaculty = facToDelete.getSections();
		String currentDeptOfFaculty = facToDelete.getDeptName();
		
		if (currentSectionsOfFaculty.equals("")) {
			if (currentDeptOfFaculty.equals("")) {
				FacRepo.deleteById(id);
				return "Faculty Deleted!";
			}
			else {
				return "Faculty is currently present inside a department. Please remove him/her from this department first before attempting to delete this record.";
			}

		}
		else {
			return "Faculty is currenty present inside section/s. Please remove him/her from the section/s first before attempting to delete this record.";
		}
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
		
		List<Faculty> allFaculty = FacRepo.findAll();
		List<Vaccine> allVaccines = VacRepo.findAll();
		
		Faculty facToUpdate = FacRepo.getById(id);
		
		for (Faculty i: allFaculty) {
			String checkerName = i.getName();
			Long checkerID = i.getId();
			
			if (checkerName.equals(name)) {
//				Trying to update to same name, let it pass
				if(id.toString().equals(checkerID.toString())) {
					break;
				}
//				Trying to update to another name that already exists in the database
				else {
					return "The name of the Faculty you are trying to update to already has a record that exists in the database! Please provide a unique name.";
				}
			}
		}
		
		facToUpdate.setName(name);
		
		
		Boolean vaccineExists = false;
		String newVaccineName = "";
		for(Vaccine i: allVaccines) {
			String checkerID = i.getName();
			if(vaccineName.equals(checkerID)) {
				newVaccineName = i.getName();
				vaccineExists = true;
				break;
			}
			else if (vaccineName.equals("")) {
				vaccineExists = true;
				break;
			}
			else {
				vaccineExists = false;
			}
			
		}
		
		String returnMessage = "";
		if (vaccineExists == true) {
			returnMessage = "Faculty Updated!";
			
			if (vaccineName==null) {
				facToUpdate.setVaccineName(newVaccineName);
				facToUpdate.setVaccineStatus(false);
			}
			else {
				facToUpdate.setVaccineName(newVaccineName);
				facToUpdate.setVaccineStatus(vaccineStatus);
			}
			
		}
		else if (vaccineExists == false) {
			returnMessage = "The vaccine name you are trying to update to does not exist! Please provide a valid vaccine name.";
		}
		
		
		FacRepo.save(facToUpdate);
		return returnMessage;
	}

}
