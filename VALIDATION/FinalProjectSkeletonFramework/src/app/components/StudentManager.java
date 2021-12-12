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
		
		for(Vaccine i: allVaccines) {
			Long checkerID = i.getId();
			if(vaccineID.toString().equals(checkerID.toString())) {
				break;
			}
			else {
				return "The vaccine you are trying to set to the student does not exist! Please provide a valid vaccine ID.";
			}
			
			
		}
		
		Student s = makeStudent(name, year, vaccineID);
		
		studentRepo.save(s);
		
		return "Student Created!";
	}
	

	private Student makeStudent(String name, int year, Long vaccineID)
	{
		List<Vaccine> allVaccines = vaccineRepo.findAll();
		Student s = new Student();
		s.setName(name);
		s.setYear(year);
		String vaccineName = "N/A";
		
		for(Vaccine i: allVaccines) {
			Long id = i.getId();
			if(id.toString().equals(vaccineID.toString())) {
				vaccineName = i.getName();
				break;
			}
		}
		s.setVaccineName(vaccineName);
		
		if (vaccineName.equals("N/A")) {
			s.setVaccineStatus(false);
		}
		else {
			s.setVaccineStatus(true);
		}
		s.setDeptName("");
		s.setCourse("");
		s.setSections("");
		return s;
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
					return "Student Deleted!";
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

	public String viewStudentDetails(Long id) {
		return "Student Details: " + studentRepo.findById(id);
	}

	public List<Student> viewAllStudents() {
		return studentRepo.findAll();
	}

	public String updateStudent(Long id, String name, int year, Long vaccineID) {
		List<Student> allStudents = studentRepo.findAll();
		List<Vaccine> allVaccines = vaccineRepo.findAll();
		Student s = studentRepo.getById(id);
		
		for (Student i: allStudents) {
			String checkerName = i.getName();
			Long checkerID = i.getId();
			
			if (checkerName.equals(name)) {
//				Trying to update to same name, let it pass
				if(id.toString().equals(checkerID.toString())) {
					break;
				}
//				Trying to update to another name that already exists in the database
				else {
					return "The name of the student you are trying to update to already has a record that exists in the database! Please provide a unique name.";
				}
			}
		}
		s.setName(name);
		
		String vaccineName = "";
		for(Vaccine i: allVaccines) {
			Long checkerID = i.getId();
			if(vaccineID.toString().equals(checkerID.toString())) {
				vaccineName = i.getName();
				break;
			}
			else {
				return "The vaccine ID you are trying to update to does not exist! Please provide a valid vaccine ID.";
			}
			
		}
		
		if (vaccineID==null) {
			s.setVaccineName(vaccineName);
			s.setVaccineStatus(false);
		}
		else {
			s.setVaccineName(vaccineName);
			s.setVaccineStatus(true);
		}
		
		studentRepo.save(s);
		return "Student Updated!";
	}

}
