package app.components;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Faculty;
import app.entity.Student;
import app.entity.Vaccine;
import app.repository.DepartmentRepository;
import app.repository.FacultyRepository;
import app.repository.StudentRepository;
import app.entity.Department;

@Component
public class DepartmentManager {
	
	@Autowired
	private DepartmentRepository DepRepo;
	
	@Autowired
	private StudentRepository StudRepo;
	
	@Autowired
	private FacultyRepository FacRepo;

	public String createDepartment(String name) {
		// TODO Auto-generated method stub
		
		List<Department> allDepts = DepRepo.findAll();
		
		for(Department i: allDepts) {
			String checkerName = i.getName();
			if (name.equals(checkerName)) {
				return "The name of the Department you are trying to add already has a record that exists in the database! Please provide a unique name.";
			}
		}
		
		Department newDepartment = new Department();
		newDepartment.setName(name);
		newDepartment.setStudents("");
		newDepartment.setFaculties("");
		DepRepo.save(newDepartment);	
		return "Department Created!";
	}

	public String deleteDepartment(Long id) {
		// TODO Auto-generated method stub
		
		Department deptToDelete = DepRepo.getById(id);
		
		if (deptToDelete==null) {
			return "The Faculty you are trying to delete does not exist! Please provide a valid ID.";
		}
		String currentStudentsOfDept = deptToDelete.getStudents();
		String currentFacultyOfDept = deptToDelete.getFaculties();
		
		if (currentStudentsOfDept.equals("")) {
			if (currentFacultyOfDept.equals("")) {
				DepRepo.deleteById(id);
				return "Department Deleted!";
			}
			else {
				return "Department currently has Faculty inside of it. Please remove them from this department first before attempting to delete this record.";
			}

		}
		else {
			return "Department currently has Students inside of it. Please remove them from this department first before attempting to delete this record.";
		}
	}

	public java.util.Optional<Department> viewDepartmentDetails(Long id) {
		// TODO Auto-generated method stub
		return DepRepo.findById(id);
	}

	public String updateDepartment(Long id, String name) {
		// TODO Auto-generated method stub
//		Department deptToUpdate = DepRepo.getById(id);
//		deptToUpdate.setName(name);
//		DepRepo.save(deptToUpdate);
//		
//		return "Department Updated!";
		
		List<Department> allDepts = DepRepo.findAll();
		
		Department deptToUpdate = DepRepo.getById(id);
		
		if (deptToUpdate==null) {
			return "The ID of the Department you are trying to update does not exist in the database! Please provide a valid ID.";
		}
		
		Boolean deptExist = false;
		// Checks if ID Exists
		for (Department i: allDepts) {
			Long checkerID = i.getId();
			
			if(id.toString().equals(checkerID.toString())) {
				deptExist = true;
			}
			else {
				deptExist = false;
			}
		}
		
		for (Department i: allDepts) {
			String checkerName = i.getName();
			Long checkerID = i.getId();
			
			if (checkerName.equals(name)) {
//				Trying to update to same name, let it pass
				if(id.toString().equals(checkerID.toString())) {
					break;
				}
				else if (deptExist == false){
					return "The ID of the Department you are trying to update does not exist in the database! Please provide a valid ID.";
				}
//				Trying to update to another name that already exists in the database
				else {
					return "The name of the Department you are trying to update to already has a record that exists in the database! Please provide a unique name.";
				}
			}
		}
		
		
		deptToUpdate.setName(name);
		
		
		DepRepo.save(deptToUpdate);
		return "Department Updated!";
	}

	public String addStudentToDepartment(Long studentID, Long departmentID) {
		// TODO Auto-generated method stub
		Boolean exists = false;
		Department dep = DepRepo.getById(departmentID);
		Student stu = StudRepo.getById(studentID);
		
		if (dep==null) {
			return "Given ID for Department does not exist!";
		}
		
		if (stu==null) {
			return "Given ID for Student does not exist!";
		}
		
		String getCurrentStudentsOfDept = dep.getStudents();
		String getCurrentDeptOfStudent = stu.getDeptName();
		
		Long addedStudentID = studentID;
		Long addedDeptID = departmentID;
		

		
		if (getCurrentStudentsOfDept == null) {
			getCurrentStudentsOfDept = "";
		}
		
//		Checking if Student is already in Department
		String checkerForStudents = dep.getStudents();
		String checkerList[] = checkerForStudents.split("\\s+");
		List<String> finalChecker = new ArrayList<>();
		for (String i : checkerList) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
	            }
	            else {
	            	chars.add(ch);
	            }
	        }
	        String numberString = "";
			for (char ch : chars) {
				String characterString = Character.toString(ch);
				numberString = numberString + characterString;
			}
			finalChecker.add(numberString);
		}
		
		System.out.println("FINAL CHECKER: " + finalChecker);
		
		for (String i: finalChecker) {
			if (i.equals(studentID.toString())) {
				exists = true;
				break;
			}
		}
		
		if (exists == true) {
			return "This student is already present inside that department!";
		}
		else {
//			Adding Student to Department
			String addedStudent = "ID#" + addedStudentID;
			System.out.println("Added Student:" + addedStudent);
			
//			Check if students of Department currently empty
			if (getCurrentStudentsOfDept.equals("")) {
				getCurrentStudentsOfDept = addedStudent;
			}
			else {
				getCurrentStudentsOfDept = getCurrentStudentsOfDept + " " +  addedStudent;
			}
			
			System.out.println("Updated Students of Department: " + getCurrentStudentsOfDept);
			
			dep.setStudents(getCurrentStudentsOfDept);
			DepRepo.save(dep);
			
//			Adding Department to Student
			String addedDept = "DepartmentID#" + addedDeptID;
			System.out.println("Added Department: " + addedDept);
			
//			Check if Departments of student currently empty
			if (getCurrentDeptOfStudent == null) {
				getCurrentDeptOfStudent = "";
			}
			
			if (getCurrentDeptOfStudent.equals("")) {
				getCurrentDeptOfStudent = addedDept;
			}
			else {
				getCurrentDeptOfStudent = getCurrentDeptOfStudent + " " + addedDept;
			}
			
			System.out.println("Updated Departments of Student: " + getCurrentDeptOfStudent);
			
			stu.setDeptName(getCurrentDeptOfStudent);
			StudRepo.save(stu);
			
//			---------------------------------------------------------
//			---------------------------------------------------------
//			VACCINATION RATE LOGIC
			List<Student> allStudents = StudRepo.findAll();
			String test[] = getCurrentStudentsOfDept.split("\\s+");
			System.out.println(test[0]);
			System.out.println(test);
			
			int vaccinated = 0;
			int notVaccinated = 0;
			int totalStudents = 0;
			
			List<String> currentStudentsInDept = new ArrayList<>();
//			Removing ID# from each instance in the array
			for (String i : test) {
				List<Character> chars = new ArrayList<>();
		        for (char ch : i.toCharArray()) {
		            String characterString = Character.toString(ch);

		            if (characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
		            }
		            else {
		            	chars.add(ch);
		            }
		        }
		        String numberString = "";
				for (char ch : chars) {
					String characterString = Character.toString(ch);
					numberString = numberString + characterString;
				}
				currentStudentsInDept.add(numberString);
			}
			
			System.out.println(currentStudentsInDept);
			
			
			for (Student i: allStudents) {
				System.out.println("current id: " + i.getId());
				for(String code: currentStudentsInDept) {
					System.out.println(code);
					if(code.equals(i.getId().toString())) {
						Boolean checker = i.getVaccineStatus();
						if (checker==true) {
							vaccinated+=1;
						}
						else {
							notVaccinated+=1;
						}
						totalStudents+=1;
					}
				}
			}
			
			System.out.println("V:" + vaccinated);
			System.out.println("NV:" + notVaccinated);
			System.out.println("TOTAL:" + totalStudents);
			
			double SV = vaccinated+notVaccinated;
			double SSV = vaccinated/SV;
			double SSSV = SSV*100;
			System.out.println(SSSV);
			
			dep.setNumberOfStudents(totalStudents);
			
			String studentVaccinationRateFinal = Double.toString(SSSV) + "%";
			System.out.println(studentVaccinationRateFinal);
			
			dep.setStudentVaccinationRate(studentVaccinationRateFinal);


			DepRepo.save(dep);
			
			return "Student added to Department!\n Current Students:\n" + getCurrentStudentsOfDept;
		}
	}

	public String removeStudentFromDepartment(Long studentID, Long departmentID) {
		// TODO Auto-generated method stub
		Boolean exists = true;
		Department dep = DepRepo.getById(departmentID);
		Student stu = StudRepo.getById(studentID);
		
		if (dep==null) {
			return "Given ID for Department does not exist!";
		}
		
		if (stu==null) {
			return "Given ID for Student does not exist!";
		}
		
		String currentStudents = dep.getStudents();
		Long removedStudentID = stu.getId();
		Long removedDeptID = dep.getId();
		
		if (currentStudents == null) {
			return "No students in current Department!";
		}
		
//		CHECKING IF IT EXISTS
		String checkerForStudents = dep.getStudents();
		String checkerList[] = checkerForStudents.split("\\s+");
		List<String> finalChecker = new ArrayList<>();
		for (String i : checkerList) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
	            }
	            else {
	            	chars.add(ch);
	            }
	        }
	        String numberString = "";
			for (char ch : chars) {
				String characterString = Character.toString(ch);
				numberString = numberString + characterString;
			}
			finalChecker.add(numberString);
		}
		
		System.out.println("FINAL CHECKER: " + finalChecker);
		
		for (String i: finalChecker) {
			if (i.equals(studentID.toString())) {
				exists = false;
				break;
			}
		}
		
		if (exists == true) {
			return "This student exists, but is not present inside this Department!";
		}

		
//		Removing Student from Section

		String students[] = currentStudents.split("\\s+");
		List<String> currentStudentsInDept = new ArrayList<>();
		
//		Removing ID# from each instance in the array
		for (String i : students) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
	            }
	            else {
	            	chars.add(ch);
	            	System.out.println(ch);
	            }
	        }
	        String numberString = "";
			for (char ch : chars) {
				String characterString = Character.toString(ch);
				numberString = numberString + characterString;
			}
			currentStudentsInDept.add(numberString);
		}
		
//		Removes it
		for (String i : currentStudentsInDept) {
			  if (i.equals(removedStudentID.toString())) {
				  currentStudentsInDept.remove(i);
				  break;

			  }
		}

		System.out.println(currentStudentsInDept);
		

		String finalDeptStudents = "";
		for (String i : currentStudentsInDept) {
			if (i.equals("")){
				
			}
			else {
				if (finalDeptStudents.equals("")) {
					finalDeptStudents = "ID#" + i;
				}
				else {
					finalDeptStudents = finalDeptStudents + " " +  "ID#" + i;
				}
			}

		}
		
		System.out.println(finalDeptStudents);
		
		dep.setStudents(finalDeptStudents);
		DepRepo.save(dep);
		
//		Removing Section from Student
		String currentDeptsOfStudent = stu.getDeptName();
		String depts[] = currentDeptsOfStudent.split("\\s+");
		List<String> currentDeptsOfStudentRaw = new ArrayList<>();
		
		for (String i : depts) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("D") || characterString.equals("e") || characterString.equals("p") || characterString.equals("a") ||characterString.equals("r") || characterString.equals("t") || characterString.equals("m") || characterString.equals("e") || characterString.equals("n") || characterString.equals("t") || characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
	            }
	            else {
	            	chars.add(ch);
	            	System.out.println(ch);
	            }
	        }
	        String numberString = "";
			for (char ch : chars) {
				String characterString = Character.toString(ch);
				numberString = numberString + characterString;
			}
			currentDeptsOfStudentRaw.add(numberString);
		}
		
		for (String i : currentDeptsOfStudentRaw) {
			  if (i.equals(removedDeptID.toString())) {
				  currentDeptsOfStudentRaw.remove(i);
				  break;

			  }
		}
		
		String finalDeptsOfStudent = "";
		for (String i: currentDeptsOfStudentRaw) {
			if (i.equals("")) {
				
			}
			else {
				if (finalDeptsOfStudent.equals("")) {
					finalDeptsOfStudent =  "DeptID#" + i;
				}
				else {
					finalDeptsOfStudent = finalDeptsOfStudent + " " +  "DeptID#" +  i;
				}
			}
		}
		stu.setDeptName(finalDeptsOfStudent);
		StudRepo.save(stu);
		
		
//		---------------------------------
//		---------------------------------
//		VACCINATION RATE LOGIC
		String getCurrentStudentsOfDept = dep.getStudents();
		
		List<Student> allStudents = StudRepo.findAll();
		String test[] = getCurrentStudentsOfDept.split("\\s+");
		System.out.println(test[0]);
		System.out.println(test);
		
		int vaccinated = 0;
		int notVaccinated = 0;
		int totalStudents = 0;
		
		List<String> currentStudentsInDept2 = new ArrayList<>();
//		Removing ID# from each instance in the array
		for (String i : test) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
	            }
	            else {
	            	chars.add(ch);
	            }
	        }
	        String numberString = "";
			for (char ch : chars) {
				String characterString = Character.toString(ch);
				numberString = numberString + characterString;
			}
			currentStudentsInDept2.add(numberString);
		}
		
		System.out.println(currentStudentsInDept2);
		
		
		for (Student i: allStudents) {
			System.out.println("current id: " + i.getId());
			for(String code: currentStudentsInDept) {
				System.out.println(code);
				if(code.equals(i.getId().toString())) {
					Boolean checker = i.getVaccineStatus();
					if (checker==true) {
						vaccinated+=1;
					}
					else {
						notVaccinated+=1;
					}
					totalStudents+=1;
				}
			}
		}
		
		System.out.println("V:" + vaccinated);
		System.out.println("NV:" + notVaccinated);
		System.out.println("TOTAL:" + totalStudents);
		
		double SV = vaccinated+notVaccinated;
		double SSV = vaccinated/SV;
		double SSSV = SSV*100;
		System.out.println(SSSV);
		
		dep.setNumberOfStudents(totalStudents);
		
		String studentVaccinationRateFinal = Double.toString(SSSV) + "%";
		System.out.println(studentVaccinationRateFinal);
		
		dep.setStudentVaccinationRate(studentVaccinationRateFinal);


		DepRepo.save(dep);
		
		return "Student removed from Department!";
	}

	public String removeFacultyFromDepartment(Long facultyID, Long departmentID) {
		// TODO Auto-generated method stub
		Boolean exists = true;
		Department dep = DepRepo.getById(departmentID);
		Faculty fac = FacRepo.getById(facultyID);
		
		if (dep==null) {
			return "Given ID for Department does not exist!";
		}
		
		if (fac==null) {
			return "Given ID for Faculty does not exist!";
		}
		
		String currentFaculty = dep.getFaculties();
		Long removedFacultyID = fac.getId();
		Long removedDeptID = dep.getId();
		
		if (currentFaculty == null) {
			return "No faculties in current Department!";
		}
		
//		CHECKING IF IT EXISTS
		String checkerForFaculties = dep.getFaculties();
		String checkerList[] = checkerForFaculties.split("\\s+");
		List<String> finalChecker = new ArrayList<>();
		for (String i : checkerList) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
	            }
	            else {
	            	chars.add(ch);
	            }
	        }
	        String numberString = "";
			for (char ch : chars) {
				String characterString = Character.toString(ch);
				numberString = numberString + characterString;
			}
			finalChecker.add(numberString);
		}
		
		System.out.println("FINAL CHECKER: " + finalChecker);
		
		for (String i: finalChecker) {
			if (i.equals(facultyID.toString())) {
				exists = false;
				break;
			}
		}
		
		if (exists == true) {
			return "This faculty exists, but is not present inside this Department!";
		}

		
//		Removing Faculty from Section

		String faculty[] = currentFaculty.split("\\s+");
		List<String> currentFacultyInDept = new ArrayList<>();
		
//		Removing ID# from each instance in the array
		for (String i : faculty) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
	            }
	            else {
	            	chars.add(ch);
	            	System.out.println(ch);
	            }
	        }
	        String numberString = "";
			for (char ch : chars) {
				String characterString = Character.toString(ch);
				numberString = numberString + characterString;
			}
			currentFacultyInDept.add(numberString);
		}
		
//		Removes it
		for (String i : currentFacultyInDept) {
			  if (i.equals(removedFacultyID.toString())) {
				  currentFacultyInDept.remove(i);
				  break;

			  }
		}

		System.out.println(currentFacultyInDept);
		

		String finalDeptFaculty = "";
		for (String i : currentFacultyInDept) {
			if (i.equals("")){
				
			}
			else {
				if (finalDeptFaculty.equals("")) {
					finalDeptFaculty = "ID#" + i;
				}
				else {
					finalDeptFaculty = finalDeptFaculty + " " +  "ID#" + i;
				}
			}

		}
		
		System.out.println(finalDeptFaculty);
		
		dep.setFaculties(finalDeptFaculty);
		DepRepo.save(dep);
		
//		Removing Section from Student
		String currentDeptsOFaculty = fac.getDeptName();
		String depts[] = currentDeptsOFaculty.split("\\s+");
		List<String> currentDeptsOfFacultyRaw = new ArrayList<>();
		
		for (String i : depts) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("D") || characterString.equals("e") || characterString.equals("p") || characterString.equals("a") ||characterString.equals("r") || characterString.equals("t") || characterString.equals("m") || characterString.equals("e") || characterString.equals("n") || characterString.equals("t") || characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
	            }
	            else {
	            	chars.add(ch);
	            	System.out.println(ch);
	            }
	        }
	        String numberString = "";
			for (char ch : chars) {
				String characterString = Character.toString(ch);
				numberString = numberString + characterString;
			}
			currentDeptsOfFacultyRaw.add(numberString);
		}
		
		for (String i : currentDeptsOfFacultyRaw) {
			  if (i.equals(removedDeptID.toString())) {
				  currentDeptsOfFacultyRaw.remove(i);
				  break;

			  }
		}
		
		String finalDeptsOfFaculty = "";
		for (String i: currentDeptsOfFacultyRaw) {
			if (i.equals("")) {
				
			}
			else {
				if (finalDeptsOfFaculty.equals("")) {
					finalDeptsOfFaculty =  "DeptID#" + i;
				}
				else {
					finalDeptsOfFaculty = finalDeptsOfFaculty + " " +  "DeptID#" +  i;
				}
			}
		}
		fac.setDeptName(finalDeptsOfFaculty);
		FacRepo.save(fac);
		
		
//		---------------------------------
//		---------------------------------
//		VACCINATION RATE LOGIC
		String getCurrentFacultyOfDept = dep.getFaculties();
		
		List<Faculty> allFaculty = FacRepo.findAll();
		String test[] = getCurrentFacultyOfDept.split("\\s+");
		System.out.println(test[0]);
		System.out.println(test);
		
		int vaccinated = 0;
		int notVaccinated = 0;
		int totalFaculty = 0;
		
		List<String> currentFacultyInDept2 = new ArrayList<>();
//		Removing ID# from each instance in the array
		for (String i : test) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
	            }
	            else {
	            	chars.add(ch);
	            }
	        }
	        String numberString = "";
			for (char ch : chars) {
				String characterString = Character.toString(ch);
				numberString = numberString + characterString;
			}
			currentFacultyInDept2.add(numberString);
		}
		
		System.out.println(currentFacultyInDept2);
		
		
		for (Faculty i: allFaculty) {
			System.out.println("current id: " + i.getId());
			for(String code: currentFacultyInDept) {
				System.out.println(code);
				if(code.equals(i.getId().toString())) {
					Boolean checker = i.getVaccineStatus();
					if (checker==true) {
						vaccinated+=1;
					}
					else {
						notVaccinated+=1;
					}
					totalFaculty+=1;
				}
			}
		}
		
		System.out.println("V:" + vaccinated);
		System.out.println("NV:" + notVaccinated);
		System.out.println("TOTAL:" + totalFaculty);
		
		double SV = vaccinated+notVaccinated;
		double SSV = vaccinated/SV;
		double SSSV = SSV*100;
		System.out.println(SSSV);
		
		dep.setNumberOfFaculty(totalFaculty);
		
		String facultyVaccinationRateFinal = Double.toString(SSSV) + "%";
		System.out.println(facultyVaccinationRateFinal);
		
		dep.setFacultyVaccinationRate(facultyVaccinationRateFinal);


		DepRepo.save(dep);
		
		return "Faculty removed from Department!";
	}

	public String addFacultyToDepartment(Long facultyID, Long departmentID) {
		Boolean exists = false;
		Department dep = DepRepo.getById(departmentID);
		Faculty fac = FacRepo.getById(facultyID);
		
		if (dep==null) {
			return "Given ID for Department does not exist!";
		}
		
		if (fac==null) {
			return "Given ID for Faculty does not exist!";
		}
		
		String getCurrentFacultyOfDept = dep.getFaculties();
		String getCurrentDeptOfFaculty = fac.getDeptName();
		
		Long addedFacultyID = facultyID;
		Long addedDeptID = departmentID;
		

		
		if (getCurrentFacultyOfDept == null) {
			getCurrentFacultyOfDept = "";
		}
		
//		Checking if Student is already in Department
		String checkerForFaculty = dep.getFaculties();
		String checkerList[] = checkerForFaculty.split("\\s+");
		List<String> finalChecker = new ArrayList<>();
		for (String i : checkerList) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
	            }
	            else {
	            	chars.add(ch);
	            }
	        }
	        String numberString = "";
			for (char ch : chars) {
				String characterString = Character.toString(ch);
				numberString = numberString + characterString;
			}
			finalChecker.add(numberString);
		}
		
		System.out.println("FINAL CHECKER: " + finalChecker);
		
		for (String i: finalChecker) {
			if (i.equals(facultyID.toString())) {
				exists = true;
				break;
			}
		}
		
		if (exists == true) {
			return "This Faculty is already present inside that department!";
		}
		else {
//			Adding Student to Department
			String addedFaculty = "ID#" + addedFacultyID;
			System.out.println("Added Faculty:" + addedFaculty);
			
//			Check if students of Department currently empty
			if (getCurrentFacultyOfDept.equals("")) {
				getCurrentFacultyOfDept = addedFaculty;
			}
			else {
				getCurrentFacultyOfDept = getCurrentFacultyOfDept + " " +  addedFaculty;
			}
			
			System.out.println("Updated Faculty of Department: " + getCurrentFacultyOfDept);
			
			dep.setFaculties(getCurrentFacultyOfDept);
			DepRepo.save(dep);
			
//			Adding Department to Student
			String addedDept = "DepartmentID#" + addedDeptID;
			System.out.println("Added Department: " + addedDept);
			
//			Check if Departments of student currently empty
			if (getCurrentDeptOfFaculty == null) {
				getCurrentDeptOfFaculty = "";
			}
			
			if (getCurrentDeptOfFaculty.equals("")) {
				getCurrentDeptOfFaculty = addedDept;
			}
			else {
				getCurrentDeptOfFaculty = getCurrentDeptOfFaculty + " " + addedDept;
			}
			
			System.out.println("Updated Departments of Faculty: " + getCurrentDeptOfFaculty);
			
			fac.setDeptName(getCurrentDeptOfFaculty);
			FacRepo.save(fac);
			
//			---------------------------------------------------------
//			---------------------------------------------------------
//			VACCINATION RATE LOGIC
			List<Faculty> allFaculty = FacRepo.findAll();
			String test[] = getCurrentFacultyOfDept.split("\\s+");
			System.out.println(test[0]);
			System.out.println(test);
			
			int vaccinated = 0;
			int notVaccinated = 0;
			int totalFaculty = 0;
			
			List<String> currentFacultyInDept = new ArrayList<>();
//			Removing ID# from each instance in the array
			for (String i : test) {
				List<Character> chars = new ArrayList<>();
		        for (char ch : i.toCharArray()) {
		            String characterString = Character.toString(ch);

		            if (characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
		            }
		            else {
		            	chars.add(ch);
		            }
		        }
		        String numberString = "";
				for (char ch : chars) {
					String characterString = Character.toString(ch);
					numberString = numberString + characterString;
				}
				currentFacultyInDept.add(numberString);
			}
			
			System.out.println(currentFacultyInDept);
			
			
			for (Faculty i: allFaculty) {
				System.out.println("current id: " + i.getId());
				for(String code: currentFacultyInDept) {
					System.out.println(code);
					if(code.equals(i.getId().toString())) {
						Boolean checker = i.getVaccineStatus();
						if (checker==true) {
							vaccinated+=1;
						}
						else {
							notVaccinated+=1;
						}
						totalFaculty+=1;
					}
				}
			}
			
			System.out.println("V:" + vaccinated);
			System.out.println("NV:" + notVaccinated);
			System.out.println("TOTAL:" + totalFaculty);
			
			double SV = vaccinated+notVaccinated;
			double SSV = vaccinated/SV;
			double SSSV = SSV*100;
			System.out.println(SSSV);
			
			dep.setNumberOfFaculty(totalFaculty);
			
			String facultyVaccinationRateFinal = Double.toString(SSSV) + "%";
			System.out.println(facultyVaccinationRateFinal);
			
			dep.setFacultyVaccinationRate(facultyVaccinationRateFinal);


			DepRepo.save(dep);
			
			return "Faculty added to Department!\n Current Faculty:\n" + getCurrentFacultyOfDept;
		}
	}
	
	
}