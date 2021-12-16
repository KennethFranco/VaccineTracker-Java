package app.components;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Course;
import app.entity.Department;
import app.entity.Student;
import app.repository.CourseRepository;
import app.repository.DepartmentRepository;
import app.repository.StudentRepository;
import app.repository.VaccineRepository;

@Component
public class CourseManager {
	
	@Autowired
	StudentRepository StuRepo;
	
	@Autowired
	DepartmentRepository DepRepo;
	
	
	@Autowired
	VaccineRepository VacRepo;
	
	@Autowired
	CourseRepository CourRepo;

	public String createCourse(String name, String deptName) {
		// TODO Auto-generated method stub
		List<Course> allCourse = CourRepo.findAll();
		List<Department> allDept = DepRepo.findAll();
		
		for(Course i: allCourse) {
			String checkerName = i.getName();
			if (name.equals(checkerName)) {
				return "The name of the Course you are trying to add already has a record that exists in the database! Please provide a unique name.";
			}
		}
		
		Boolean departmentExists = false;
		for(Department i: allDept) {
			String checkerVaccineName = i.getName();
			if(deptName.equals(checkerVaccineName)) {
				departmentExists = true;
				break;
			}
			else {
				departmentExists = false;
			}
			
		}
		
		
		String returnMessage = "";
		if (departmentExists == true) {
			Course newCourse = new Course();
			newCourse.setName(name);
			newCourse.setDeptName(deptName);
			newCourse.setStudents("");
			CourRepo.save(newCourse);
			
			returnMessage = "Course Created!";
		}
		else if (departmentExists == false) {
			returnMessage = "The Department that you are trying to set to the Course does not exist! Please provide a valid Department name.";
		}
		
		return returnMessage;
	}

	public String deleteCourse(Long id) {
		// TODO Auto-generated method stub
		Course courseToDelete = CourRepo.getById(id);
		
		if (courseToDelete==null) {
			return "The Course you are trying to delete does not exist! Please provide a valid ID.";
		}
		String currentStudentsOfFaculty = courseToDelete.getStudents();
		
		if (currentStudentsOfFaculty.equals("")) {
			CourRepo.deleteById(id);
			return "Course Deleted!";

		}
		else {
			return "Students are currenty present inside this Course. Please remove them from the Course first before attempting to delete this record.";
		}
	}

	public Optional<Course> viewCourseDetails(Long id) {
		// TODO Auto-generated method stub
		return CourRepo.findById(id);
	}

	public String updateCourse(Long id, String name, String deptName) {
		// TODO Auto-generated method stub
		
		List<Course> allCourse = CourRepo.findAll();
		List<Department> allDept = DepRepo.findAll();
		
		Course courseToUpdate = CourRepo.getById(id);
		
		for (Course i: allCourse) {
			String checkerName = i.getName();
			Long checkerID = i.getId();
			
			if (checkerName.equals(name)) {
//				Trying to update to same name, let it pass
				if(id.toString().equals(checkerID.toString())) {
					break;
				}
//				Trying to update to another name that already exists in the database
				else {
					return "The name of the Course you are trying to update to already has a record that exists in the database! Please provide a unique name.";
				}
			}
		}
		
		courseToUpdate.setName(name);
		
		
		Boolean departmentExists = false;
		String newDeptName = "";
		for(Department i: allDept) {
			String checkerID = i.getName();
			if(deptName.equals(checkerID)) {
				newDeptName = i.getName();
				departmentExists = true;
				break;
			}
			else {
				departmentExists = false;
			}
			
		}
		
		String returnMessage = "";
		if (departmentExists == true) {
			courseToUpdate.setDeptName(newDeptName);
			returnMessage = "Course Updated!";		
		}
		else if (departmentExists == false) {
			returnMessage = "The Department Name you are trying to update to does not exist! Please provide a valid Department name.";
		}
		
		
		CourRepo.save(courseToUpdate);
		return returnMessage;
	}

	public String addStudentToCourse(Long studentID, Long courseID) {
		// TODO Auto-generated method stub
		Boolean exists = false;
		
		Course course = CourRepo.getById(courseID);
		
		if (course==null) {
			return "Given ID for Course does not exist!";
		}
		
		String deptName = course.getDeptName();
		Department dep = DepRepo.findByName(deptName);
		Student stu = StuRepo.getById(studentID);
		
		
		if (stu==null) {
			return "Given ID for Student does not exist!";
		}
		
		String getCurrentStudentsOfCourse = course.getStudents();
		String getCurrentCourseOfStudent = stu.getCourse();
		String getCurrentStudentsOfDept = dep.getStudents();
		
		
		
		Long addedStudentID = studentID;
		Long addedCourseID = courseID;
		

		if (getCurrentStudentsOfDept==null) {
			getCurrentStudentsOfDept = "";
		}
		if (getCurrentStudentsOfCourse == null) {
			getCurrentStudentsOfCourse = "";
		}
		
//		Checking if Student is already in Section
		String checkerForStudents = course.getStudents();
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
			return "This student is already present inside that Course!";
		}
		else {
//			Adding Student to Section
			String addedStudent = "ID#" + addedStudentID;
			System.out.println("Added Student:" + addedStudent);
			
//			Check if students of section currently empty
			if (getCurrentStudentsOfCourse.equals("")) {
				getCurrentStudentsOfCourse = addedStudent;
			}
			else {
				getCurrentStudentsOfCourse = getCurrentStudentsOfCourse + " " +  addedStudent;
			}
			
			System.out.println("Updated Students of Section: " + getCurrentStudentsOfCourse);
			
			course.setStudents(getCurrentStudentsOfCourse);
			CourRepo.save(course);
			
//			Adding Section to Student
			String addedCourse = "CourseID#" + addedCourseID;
			System.out.println("Added Course: " + addedCourse);
			
//			Check if sections of student currently empty
			if (getCurrentCourseOfStudent == null) {
				getCurrentCourseOfStudent = "";
			}
			
			if (getCurrentCourseOfStudent.equals("")) {
				getCurrentCourseOfStudent = addedCourse;
			}
			else {
				getCurrentCourseOfStudent = getCurrentCourseOfStudent + " " + addedCourse;
			}
			
			System.out.println("Updated Sections of Student: " + getCurrentCourseOfStudent);
			
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
			
			stu.setCourse(getCurrentCourseOfStudent);
			stu.setDeptName(CourRepo.getById(courseID).getDeptName());
			StuRepo.save(stu);
			
//			---------------------------------------------------------
//			---------------------------------------------------------
//			VACCINATION RATE LOGIC
			List<Student> allStudents = StuRepo.findAll();
			String test[] = getCurrentStudentsOfCourse.split("\\s+");
			System.out.println(test[0]);
			System.out.println(test);
			
			int vaccinated = 0;
			int notVaccinated = 0;
			int totalStudents = 0;
			
			List<String> currentStudentsInCourse = new ArrayList<>();
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
				currentStudentsInCourse.add(numberString);
			}
			
			System.out.println(currentStudentsInCourse);
			
			
			for (Student i: allStudents) {
				System.out.println("current id: " + i.getId());
				for(String code: currentStudentsInCourse) {
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
			
			String test2[] = getCurrentStudentsOfDept.split("\\s+");
			System.out.println(test2[0]);
			System.out.println(test2);
			
			int vaccinated2 = 0;
			int notVaccinated2 = 0;
			int totalStudents2 = 0;
			
			List<String> currentStudentsInDept = new ArrayList<>();
//			Removing ID# from each instance in the array
			for (String i : test2) {
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
							vaccinated2+=1;
						}
						else {
							notVaccinated2+=1;
						}
						totalStudents2+=1;
					}
				}
			}
			
			System.out.println("V:" + vaccinated);
			System.out.println("NV:" + notVaccinated);
			System.out.println("TOTAL:" + totalStudents);
			
			double SV = vaccinated+notVaccinated;
			double SSV = vaccinated/SV;
			double SSSV = SSV*100;
			
			double SV2 = vaccinated2+notVaccinated2;
			double SSV2 = vaccinated2/SV2;
			double SSSV2 = SSV2*100;
			
			System.out.println(SSSV);
			
			course.setNumberOfStudents(totalStudents);
			dep.setNumberOfStudents(totalStudents2);
			
			String studentVaccinationRateFinal = Double.toString(SSSV) + "%";
			String studentVaccinationRateFinal2 = Double.toString(SSSV2) + "%";
			System.out.println(studentVaccinationRateFinal);
			
			course.setStudentVaccinationRate(studentVaccinationRateFinal);
			dep.setStudentVaccinationRate(studentVaccinationRateFinal2);
			
			DepRepo.save(dep);
			CourRepo.save(course);
			
			return "Student added to Course!\n Current Students:\n" + getCurrentStudentsOfCourse;
		}
	}

	public String removeStudentFromCourse(Long studentID, Long courseID) {
		// TODO Auto-generated method stub
		Boolean exists = true;
		Course cour = CourRepo.getById(courseID);
		Student stu = StuRepo.getById(studentID);
		
		if (cour==null) {
			return "Given ID for Course does not exist!";
		}
		
		String deptName = cour.getDeptName();
		Department dep = DepRepo.findByName(deptName);
		
		
		if (stu==null) {
			return "Given ID for Student does not exist!";
		}
		
		String getCurrentStudentsOfDept = dep.getStudents();
		String currentStudents = cour.getStudents();
		Long removedStudentID = stu.getId();
		Long removedCourID = cour.getId();
		
		if (currentStudents == null) {
			return "No students in current Course!";
		}
		
//		CHECKING IF IT EXISTS
		String checkerForStudents = cour.getStudents();
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
			return "This student exists, but is not present inside this Course!";
		}
		
		String students[] = currentStudents.split("\\s+");
		List<String> currentStudentsInCourse = new ArrayList<>();
		
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
			currentStudentsInCourse.add(numberString);
		}
		
//		Removes it
		for (String i : currentStudentsInCourse) {
			  if (i.equals(removedStudentID.toString())) {
				  currentStudentsInCourse.remove(i);
				  break;

			  }
		}

		System.out.println(currentStudentsInCourse);
		

		String finalCourseStudents = "";
		for (String i : currentStudentsInCourse) {
			if (i.equals("")){
				
			}
			else {
				if (finalCourseStudents.equals("")) {
					finalCourseStudents = "ID#" + i;
				}
				else {
					finalCourseStudents = finalCourseStudents + " " +  "ID#" + i;
				}
			}

		}
		
		System.out.println(finalCourseStudents);
		
		cour.setStudents(finalCourseStudents);
		CourRepo.save(cour);
		
		String students2[] = getCurrentStudentsOfDept.split("\\s+");
		List<String> currentStudentsInDept = new ArrayList<>();
		
//		Removing ID# from each instance in the array
		for (String i : students2) {
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
		String currentCourseOfStudent = stu.getCourse();
		String Courses[] = currentCourseOfStudent.split("\\s+");
		List<String> currentCourseOfStudentRaw = new ArrayList<>();
		
		for (String i : Courses) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("C") || characterString.equals("o") || characterString.equals("u") || characterString.equals("r") ||characterString.equals("s") || characterString.equals("e") || characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
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
			currentCourseOfStudentRaw.add(numberString);
		}
		
		for (String i : currentCourseOfStudentRaw) {
			  if (i.equals(removedCourID.toString())) {
				  currentCourseOfStudentRaw.remove(i);
				  break;

			  }
		}
		
		String finalCourseOfStudent = "";
		for (String i: currentCourseOfStudentRaw) {
			if (i.equals("")) {
				
			}
			else {
				if (finalCourseOfStudent.equals("")) {
					finalCourseOfStudent =  "CourseID#" + i;
				}
				else {
					finalCourseOfStudent = finalCourseOfStudent + " " +  "CourseID#" +  i;
				}
			}
		}
		stu.setCourse(finalCourseOfStudent);
		stu.setDeptName("");
		StuRepo.save(stu);
		
//		//		---------------------------------
//		VACCINATION RATE LOGIC
		String getCurrentStudentsOfCourse = cour.getStudents();
		String getCurrentStudentsOfDept2 = dep.getStudents();
		
		List<Student> allStudents = StuRepo.findAll();
		String test[] = getCurrentStudentsOfCourse.split("\\s+");
		System.out.println(test[0]);
		System.out.println(test);
		
		int vaccinated = 0;
		int notVaccinated = 0;
		int totalStudents = 0;
		
		List<String> currentStudentsInCourse2 = new ArrayList<>();
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
			currentStudentsInCourse2.add(numberString);
		}
		
		System.out.println(currentStudentsInCourse2);
		
		
		for (Student i: allStudents) {
			System.out.println("current id: " + i.getId());
			for(String code: currentStudentsInCourse) {
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
		
		String test2[] = getCurrentStudentsOfDept2.split("\\s+");
		System.out.println(test2[0]);
		System.out.println(test2);
		
		int vaccinated2 = 0;
		int notVaccinated2 = 0;
		int totalStudents2 = 0;
		
		List<String> currentStudentsInDept2 = new ArrayList<>();
//		Removing ID# from each instance in the array
		for (String i : test2) {
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
			for(String code: currentStudentsInDept2) {
				System.out.println(code);
				if(code.equals(i.getId().toString())) {
					Boolean checker = i.getVaccineStatus();
					if (checker==true) {
						vaccinated2+=1;
					}
					else {
						notVaccinated2+=1;
					}
					totalStudents2+=1;
				}
			}
		}
		
		
		System.out.println("V:" + vaccinated);
		System.out.println("NV:" + notVaccinated);
		System.out.println("TOTAL:" + totalStudents);
		
		double SV = vaccinated+notVaccinated;
		double SSV = vaccinated/SV;
		double SSSV = SSV*100;
		
		System.out.println("V2:" + vaccinated2);
		System.out.println("NV2:" + notVaccinated2);
		System.out.println("TOTAL2:" + totalStudents2);
		
		double SV2 = vaccinated2+notVaccinated2;
		double SSV2 = vaccinated2/SV2;
		double SSSV2 = SSV2*100;
		
		cour.setNumberOfStudents(totalStudents);
		dep.setNumberOfStudents(totalStudents2);
		
		String studentVaccinationRateFinal = Double.toString(SSSV) + "%";
		String studentVaccinationRateFinal2 = Double.toString(SSSV2) + "%";
		System.out.println(studentVaccinationRateFinal);
		System.out.println(studentVaccinationRateFinal2);
		
		cour.setStudentVaccinationRate(studentVaccinationRateFinal);
		dep.setStudentVaccinationRate(studentVaccinationRateFinal2);

		DepRepo.save(dep);
		CourRepo.save(cour);
		
		return "Student removed from Course!";
	}

}
