package app.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Section;
import app.entity.Student;
import app.entity.Vaccine;
import app.repository.SectionRepository;
import app.repository.StudentRepository;
import app.repository.VaccineRepository;



@Component
public class StudentManager {
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private SectionRepository sectionRepo;
	
	@Autowired
	private VaccineRepository vaccineRepo;

	public String createStudent(String name, int year, Long vaccineID) {
		List<Student> allStudents = studentRepo.findAll();
		List<Vaccine> allVaccines = vaccineRepo.findAll();
		
		for(Student i: allStudents) {
			String checkerName = i.getName();
			if (name.equals(checkerName)) {
				return "The name of the student you are trying to add already has a record that exists in the database! Please provide a unique name.";
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

		Student s = new Student();
		if (vaccineExists==false) {
			if (vaccineID==null) {
				s.setVaccineStatus(false);
				s.setVaccineName("");
			}
			else {
				return "The vaccine you are trying to set to the student does not exist! Please provide a valid vaccine ID.";
			}
			
		}
		
		s.setCourse("");
		s.setDeptName("");
		s.setName(name);
		s.setSections("");
		
		if (vaccineID != null) {
			Vaccine v = vaccineRepo.getById(vaccineID);
			s.setVaccineName(v.getName());
			s.setVaccineStatus(true);
		}

		s.setYear(year);
		
		studentRepo.save(s);
		return "Student Created!\n" + "Details:\n" + s.toString();
	}
	

	public String deleteStudent(Long id) {
		Student s = studentRepo.getById(id);
		
		if (s==null) {
			return "The student you are trying to delete does not exist! Please provide a valid ID.";
		}
		String currentSectionsOfStudent = s.getSections();
		String currentDeptOfStudent = s.getDeptName();
		String currentCourseOfStudent = s.getCourse();
		
		if (currentSectionsOfStudent.equals("")) {
			if (currentDeptOfStudent.equals("")) {
				if(currentCourseOfStudent.equals("")) {
					studentRepo.deleteById(id);
					return "Student #" + id + " deleted!";
				}
				else {
					return "Student is currently present inside a course. Please remove him/her from this course first before attempting to delete this record.";
				}
			}
			else {
				return "Student is currently present inside a department. Please remove him/her from this department first before attempting to delete this record.";
			}

		}
		else {
			return "Student is currenty present inside section/s. Please remove him/her from the section/s first before attempting to delete this record.";
		}
	}

	public java.util.Optional<Student> viewStudentDetails(Long id){
		return studentRepo.findById(id);
	}

	public List<Student> viewAllStudents() {
		return studentRepo.findAll();
	}

	public String updateStudent(Long id, String name, int year, Long vaccineID) {
		List<Student> allStudents = studentRepo.findAll();
		List<Vaccine> allVaccines = vaccineRepo.findAll();
		Student s = studentRepo.getById(id);
		
		
		if (s==null) {
			return "The student you are trying to update does not exist! Please provide a valid student ID.";
		}
		
		Boolean studentExist = false;
		// Checks if ID Exists
		for (Student i: allStudents) {
			Long checkerID = i.getId();
			System.out.println("current checker id:" + checkerID);
			if(id.toString().equals(checkerID.toString())) {
				System.out.println("current checker id:" + checkerID);
				System.out.println(id);
				studentExist = true;
				System.out.println(studentExist);
				break;
			}
			else {
				studentExist = false;
				System.out.println(studentExist);
			}
		}

		System.out.println(studentExist);
		for (Student i: allStudents) {
			String checkerName = i.getName();
			Long checkerID = i.getId();

			if (checkerName.equals(name)) {
//				Trying to update to same name, let it pass
				if(id.toString().equals(checkerID.toString())) {
					break;
				}
				else if (studentExist == false){
					return "The ID of the Student you are trying to update does not exist in the database! Please provide a valid ID.";
				}
//				Trying to update to another name that already exists in the database
				else {
					return "The name of the Student you are trying to update to already has a record that exists in the database! Please provide a unique name.";
				}
			}
		}
		
		s.setName(name);
		s.setYear(year);
		
		Boolean vaccineProvided = true;
		
		if(vaccineID==null) {
			s.setVaccineName("");
			vaccineProvided = false;
			s.setVaccineStatus(false);
		}
		else {
//			Checks if vaccineID is valid
			Vaccine v = vaccineRepo.getById(vaccineID);
			
			
//			If not valid return this
			if (v==null) {
				return "The vaccine you are trying to update to does not exist! Please provide a valid vaccine ID.";
			} 
			
//			If valid change vaccineProvided to true
			else {
				vaccineProvided = true;
			}
		}
		
		
		if (vaccineProvided==true) {
			Vaccine v = vaccineRepo.getById(vaccineID);
			String vaccineName = v.getName();
			s.setVaccineName(vaccineName);
			s.setVaccineStatus(true);
		}
		
		studentRepo.save(s);
		return "Student Updated!\n" + "New Details:\n" + s.toString();
	}


	public String viewTotalStudentVaccinationRate() {
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
		
		String studentVaccinationRateFinal = Double.toString(SSSV) + "%";
		
		return "Total Number of Students: " + totalStudents + "\nVaccinated Students: " + vaccinated + "\nUnvaccinated Students: " + notVaccinated + "\nTotal Vaccination Rate: " + studentVaccinationRateFinal;
	}

}