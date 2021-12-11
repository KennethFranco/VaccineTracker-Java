package app.components;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Faculty;
import app.entity.Student;
import app.entity.Vaccine;
import app.repository.DepartmentRepository;
import app.repository.StudentRepository;
import app.entity.Department;

@Component
public class DepartmentManager {
	
	@Autowired
	private DepartmentRepository DepRepo;
	
	@Autowired
	private StudentRepository StudRepo;

	public String createDepartment(String name) {
		// TODO Auto-generated method stub
		Department newDepartment = new Department();
		newDepartment.setName(name);
		DepRepo.save(newDepartment);	
		return "Department Created!";
	}

	public String deleteDepartment(Long id) {
		// TODO Auto-generated method stub
		DepRepo.deleteById(id);
		return "Department Deleted!";
	}

	public java.util.Optional<Department> viewDepartmentDetails(Long id) {
		// TODO Auto-generated method stub
		return DepRepo.findById(id);
	}

	public String updateDepartment(Long id, String name) {
		// TODO Auto-generated method stub
		Department deptToUpdate = DepRepo.getById(id);
		deptToUpdate.setName(name);
		DepRepo.save(deptToUpdate);
		
		return "Department Updated!";
	}

	public String addStudentToDepartment(Long studentID, Long departmentID) {
		// TODO Auto-generated method stub
		
//		List<Department> allDepartments = DepRepo.findAll();
//        Department dep = DepRepo.getById(departmentID);
//        Student stu = StudRepo.getById(studentID);
//        
//        String currentStudents = dep.getStudents();
//        Long addedStudent = stu.getId();
//        
//        if (currentStudents == null) {
//            currentStudents = "";
//        }
//        
//        String idNumberStudent = "ID#" + addedStudent.toString();
//        currentStudents = currentStudents + " " + idNumberStudent;
//        
//        String students[] = currentStudents.split("\\s+");
//        List<String> currentStudentsInDepartment = new ArrayList<>();
//        
//        System.out.println(currentStudents);
//        
//        for (String i : students) {
//            List<Character> chars = new ArrayList<>();
//            for (char ch : i.toCharArray()) {
//                String characterString = Character.toString(ch);
//
//                if (characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
//                }
//                else {
//                    chars.add(ch);
//                }
//            }
//            String numberString = "";
//            for (char ch : chars) {
//                String characterString = Character.toString(ch);
//                numberString = numberString + characterString;
//            }
//            currentStudentsInDepartment.add(numberString);
//        }
//        
//        String finalSectionStudents = "";
//        for (String i : currentStudentsInDepartment) {
//            if (i.equals("")){
//                
//            }
//            else {
//                if (finalSectionStudents.equals("")) {
//                    finalSectionStudents = "" + i;
//                }
//                else {
//                    finalSectionStudents = finalSectionStudents + " " +  "" + i;
//                }
//            }
//
//        }
//        
//        System.out.println(finalSectionStudents);
//        
//        for (Department i: allDepartments) {
//            i.getId();
//        }
//
//        
//        int currentNumberOfStudents = dep.getNumberOfStudents();
//        currentNumberOfStudents += 1;
//        
//        dep.setNumberOfStudents(currentNumberOfStudents);
//        dep.setStudents(currentStudents);
//        DepRepo.save(dep);
//        
//        String studentCurrentDept = stu.getDeptName();
//        Long addedDept = dep.getId();
//        
//        if (studentCurrentDept == null) {
//        	studentCurrentDept = "";
//        }
//        
//        String idNumberSection = "DepartmentID#" + addedDept.toString();
//        studentCurrentDept = studentCurrentDept + " " + idNumberSection;
//        
//        stu.setDeptName(studentCurrentDept);
//        
//        
//        StudRepo.save(stu);
        
        
//        return "Student added to Department!\n Current Students:\n" + currentStudents;
		
		return "nice";
	}

	public String removeStudentFromDepartment(Long studentID, Long departmentID) {
		// TODO Auto-generated method stub
		return "Student removed from Department!";
	}

	public String removeFacultyFromDepartment(Long facultyID, Long departmentID) {
		// TODO Auto-generated method stub
		return "Faculty removed from Department!";
	}

	public String addFacultyToDepartment(Long facultyID, Long departmentID) {
		// TODO Auto-generated method stub
		return "Faculty added to Department!";
	}

}
