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

	public String createFaculty(String name, Long vaccineID) {
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
		if (vaccineID==null) {
			
		}
		else {
			for(Vaccine i: allVaccines) {
				System.out.println(vaccineID);
				
				Long checkerID = i.getId();
				System.out.println(checkerID);
				if(vaccineID.toString().equals(checkerID.toString())) {
					vaccineExists = true;
					break;
					
				}
				else {
					vaccineExists = false;
				}
				
				
			}
		}

		Faculty f = new Faculty();
		if (vaccineExists==false) {
			if (vaccineID==null) {
				f.setVaccineStatus(false);
				f.setVaccineName("");
			}
			else {
				return "The vaccine you are trying to set to the faculty does not exist! Please provide a valid vaccine ID.";
			}
			
		}
		
		f.setDeptName("");
		f.setName(name);
		f.setSections("");
		
		if (vaccineID != null) {
			Vaccine v = VacRepo.getById(vaccineID);
			f.setVaccineName(v.getName());
			f.setVaccineStatus(true);
		}

	
		FacRepo.save(f);
		
		return "Faculty Created!\n" + "Details:\n" + f.toString();
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

	public String updateFaculty(Long id, String name, Long vaccineID) {
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
		
		Boolean vaccineExists = false;
		if (vaccineID==null) {
			
		}
		else {
			for(Vaccine i: allVaccines) {
				System.out.println(vaccineID);
				
				Long checkerID = i.getId();
				System.out.println(checkerID);
				if(vaccineID.toString().equals(checkerID.toString())) {
					vaccineExists = true;
					break;
					
				}
				else {
					vaccineExists = false;
				}
				
				
			}
		}

		if (vaccineExists==false) {
			if (vaccineID==null) {
				facToUpdate.setVaccineStatus(false);
				facToUpdate.setVaccineName("");
			}
			else {
				return "The vaccine you are trying to set to the faculty does not exist! Please provide a valid vaccine ID.";
			}
			
		}
		
		facToUpdate.setName(name);

		if (vaccineID != null) {
			Vaccine v = VacRepo.getById(vaccineID);
			facToUpdate.setVaccineName(v.getName());
			facToUpdate.setVaccineStatus(true);
		}

		FacRepo.save(facToUpdate);
		
		return "Student Updated!\n" + "New Details:\n" + facToUpdate.toString();
		
		

	}

	public String viewTotalFacultyVaccinationRate() {
		List<Faculty> allFaculties = FacRepo.findAll();
		
		int vaccinated = 0;
		int notVaccinated = 0;
		int totalFaculties = 0;
		
		for (Faculty i: allFaculties) {
			Boolean checker = i.getVaccineStatus();
			if (checker==true) {
				vaccinated+=1;
			}
			else {
				notVaccinated+=1;
			}
			totalFaculties+=1;
			
		}
		
		double SV = vaccinated+notVaccinated;
		double SSV = vaccinated/SV;
		double SSSV = SSV*100;
		
		String facultyVaccinationRateFinal = Double.toString(SSSV) + "%";
		
		return "Total Number of Faculties: " + totalFaculties + "\nVaccinated Faculties " + vaccinated + "\nUnvaccinated Faculties: " + notVaccinated + "\nTotal Vaccination Rate: " + facultyVaccinationRateFinal;
	}

}