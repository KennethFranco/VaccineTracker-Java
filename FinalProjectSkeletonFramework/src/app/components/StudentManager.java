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



@Component
public class StudentManager {
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private SectionRepository sectionRepo;

	public String createStudent(String name, int year, String vaccineName, Boolean vaccineStatus) {
		Student s = makeStudent(name, year, vaccineName, vaccineStatus);
		
		studentRepo.save(s);
		
		return "Student Created!";
	}
	

	private Student makeStudent(String name, int year, String vaccineName, Boolean vaccineStatus)
	{
		Student s = new Student();
		s.setName(name);
		s.setYear(year);
		s.setVaccineName(vaccineName);
		s.setVaccineStatus(vaccineStatus);
		s.setDeptName("");
		s.setCourse("");
		s.setSections("");
		return s;
	}

	public String deleteStudent(Long id) {
		Long removedStudentID = id;
		List<Section> allSections = sectionRepo.findAll();
		
//		Deleting Student from Sections
		for (Section i: allSections) {
			String studentsOfSection =  i.getStudents();
			System.out.println(studentsOfSection);
		}
		
//		studentRepo.deleteById(id);
		return "Student Deleted!";
	}

	public String viewStudentDetails(Long id) {
		return "Student Details: " + studentRepo.findById(id);
	}

	public List<Student> viewAllStudents() {
		return studentRepo.findAll();
	}

	public String updateStudent(Long id, String name, int year, String vaccineName, Boolean vaccineStatus) {
		
		Student s = studentRepo.getById(id);
		
		s.setName(name);
		s.setVaccineName(vaccineName);
		s.setVaccineStatus(vaccineStatus);
		studentRepo.save(s);
		return "Student Updated!";
	}

}
