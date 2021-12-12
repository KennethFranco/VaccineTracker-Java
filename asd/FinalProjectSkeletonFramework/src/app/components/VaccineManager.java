package app.components;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Faculty;
import app.entity.Student;
import app.entity.Vaccine;
import app.repository.FacultyRepository;
import app.repository.StudentRepository;
import app.repository.VaccineRepository;

@Component
public class VaccineManager {

	@Autowired
	private VaccineRepository vaccineRepo;
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private FacultyRepository facultyRepo;
	
	public String createVaccine(String name, String manufacturer) {
		List<Vaccine> allVaccines = vaccineRepo.findAll();
		Vaccine v = makeVaccine(name, manufacturer);
		vaccineRepo.save(v);
		for (Vaccine i: allVaccines) {
			String checkerName = i.getName();
			if (checkerName.equals(name)){
				return "The name of the vaccine you are trying to add already has a record that exists in the database! Please provide a unique name.";
			}
		}
		
		return "Vaccine Created!";
	}
	
	private Vaccine makeVaccine(String name, String manufacturer) {
		Vaccine v = new Vaccine();
		v.setName(name);
		v.setManufacturer(manufacturer);
		return v;
	}

	public String deleteVaccine(Long id) {
		Vaccine v = vaccineRepo.getById(id);
		if (v==null) {
			return "The vaccine you are trying to delete does not exist! Please provide a valid ID.";
		}
		
		String checkerName = v.getName();
		List<Student> allStudents = studentRepo.findAll();
		List<Faculty> allFaculties = facultyRepo.findAll();
		
		for (Student i: allStudents) {
			if (checkerName.equals(i.getVaccineName())){
				return "Vaccine is currently present inside a student. Please remove this vaccine from this student first before attempting to delete this record."; 
			}
		}
		
		for (Faculty i: allFaculties) {
			if (checkerName.equals(i.getVaccineName())){
				return "Vaccine is currently present inside a faculty. Please remove this vaccine from this student first before attempting to delete this record."; 
			}
		}
		
		vaccineRepo.deleteById(id);
		return "Vaccine Deleted!";
	}

	public Optional<Vaccine> viewVaccineDetails(Long id)
	{
		Optional<Vaccine> vaccineDetails = vaccineRepo.findById(id);
		return vaccineDetails;
	}

	public List<Vaccine> viewAllVaccines() {
		return vaccineRepo.findAll();
	}

	public String updateVaccine(Long id, String name, String manufacturer) {
		List<Vaccine> allVaccines = vaccineRepo.findAll();
		List<Student> allStudents = studentRepo.findAll();
		List<Faculty > allFaculties = facultyRepo.findAll();
		Vaccine updateVaccine = vaccineRepo.getById(id);
		
		if (updateVaccine==null) {
			return "The vaccine you are trying to update does not exist! Please provide a valid vaccine ID.";
		}
		
		for (Student i: allStudents) {
			String checkerName = i.getVaccineName();
			
			if (checkerName.equals(name)) {
				return "The vaccine you are trying to update is currently present inside a student. Please change/remove this vaccine from this student before trying to update this.";
			}
		}
		
		for (Faculty i: allFaculties) {
			String checkerName = i.getVaccineName();
			if (checkerName.equals(name)) {
				return "The vaccine you are trying to update is currently present inside a faculty. Please change/remove this vaccine from this student before trying to update this.";
			}
		}
		
		for (Vaccine i: allVaccines) {
			String checkerName = i.getName();
			Long checkerID = i.getId();
			
			if (checkerName.equals(name)) {
				if(id.toString().equals(checkerID.toString())) {
					break;
				}
				else {
					return "The name of the vaccine you are trying to update to already has a record that exists in the database! Please provide a unique name.";
				}
				
			}
		}
		
		updateVaccine.setName(name);
		updateVaccine.setManufacturer(manufacturer);
		vaccineRepo.save(updateVaccine);
		return "Vaccine Updated!";
	}


}