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
import javassist.expr.Instanceof;

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
			System.out.println(name);
			System.out.println(i.getVaccineName());
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

	public String viewTotalVaccinationStats() {
		List<Student> allStudents = studentRepo.findAll();
		
		int vaccinated = 0;
		int notVaccinated = 0;
		int totalStudents = 0;
		
		for (Student i: allStudents) {
			Boolean checker = i.getVaccineStatus();
			if (checker==true) {
				vaccinated+=1;
			}
			else {
				notVaccinated+=1;
			}
			totalStudents+=1;
			
		}
		
		double SV = vaccinated+notVaccinated;
		double SSV = vaccinated/SV;
		double SSSV = SSV*100;
		
		String studentVaccinationRateFinal = Double.toString(SSSV) + "%" + " (" + Integer.toString(vaccinated) + "/" + Integer.toString(totalStudents) + ")" ;
		
		
		List<Faculty> allFaculties = facultyRepo.findAll();
		
		int vaccinated2 = 0;
		int notVaccinated2 = 0;
		int totalFaculty = 0;
		
		for (Faculty i: allFaculties) {
			Boolean checker = i.getVaccineStatus();
			if (checker==true) {
				vaccinated2+=1;
			}
			else {
				notVaccinated2+=1;
			}
			totalFaculty+=1;
			
		}
		
		double calc1 = vaccinated2+notVaccinated2;
		double calc2 = vaccinated2/calc1;
		double calc3 = calc2*100;
		
		String facultyVaccinationRateFinal = Double.toString(calc3) + "%" + " (" + Integer.toString(vaccinated2) + "/" + Integer.toString(totalFaculty) + ")" ;
		
		int finalVaccinated = vaccinated+vaccinated2;
		int finalTotal = (int) (SV+calc1);
		double finalVaccinationRate = (vaccinated+vaccinated2)/(SV+calc1);
		double finalPercentage =  finalVaccinationRate*100;
		
		String totalPopulationVaccinationRate = Double.toString(finalPercentage) + "%" + " (" + Integer.toString(finalVaccinated) + "/" + Integer.toString(finalTotal) + ")" ;
		
		return "Total Student Population Stats: " + studentVaccinationRateFinal + "\nTotal Faculty Population Stats: " + facultyVaccinationRateFinal + "\nTotal Ateneo Population Stats: " + totalPopulationVaccinationRate;
	}


}