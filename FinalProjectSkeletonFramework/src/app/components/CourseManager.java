package app.components;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Course;
import app.entity.Student;
import app.repository.CourseRepository;
import app.repository.StudentRepository;
import app.repository.VaccineRepository;

@Component
public class CourseManager {
	
	@Autowired
	StudentRepository StuRepo;
	
	@Autowired
	VaccineRepository VacRepo;
	
	@Autowired
	CourseRepository CourRepo;

	public String createCourse(String name, String deptName) {
		// TODO Auto-generated method stub
		Course newCourse = new Course();
		newCourse.setName(name);
		newCourse.setDeptName(deptName);
		newCourse.setStudents("");
		CourRepo.save(newCourse);
		return "Course Created!";
	}

	public String deleteCourse(Long id) {
		// TODO Auto-generated method stub
		CourRepo.deleteById(id);
		return "Course Deleted!";
	}

	public Optional<Course> viewCourseDetails(Long id) {
		// TODO Auto-generated method stub
		return CourRepo.findById(id);
	}

	public String updateCourse(Long id, String name, String deptName) {
		// TODO Auto-generated method stub
		Course courseToUpdate = CourRepo.getById(id);
		courseToUpdate.setName(name);
		courseToUpdate.setDeptName(deptName);
		CourRepo.save(courseToUpdate);
		return "Course Updated!";
	}

	public String addStudentToCourse(Long studentID, Long courseID) {
		// TODO Auto-generated method stub
		Boolean exists = false;
		Course cour = CourRepo.getById(courseID);
		Student stu = StuRepo.getById(studentID);
		
		if (cour == null) {
			return "Given ID for Course does not exist";
		}
		
		if (stu == null) {
			return "Given ID for student does not exist";
		}
		String getCurrentStudentsOfCourse = cour.getStudents();
		String getCurrentCourseOfStudents = stu.getCourse();
		
		Long addedStudentID = studentID;
		Long addedCourID = courseID;
		
		if (getCurrentStudentsOfCourse == null) {
			getCurrentStudentsOfCourse = "";
		}

//	checking if the student is already in the department
		String checkerForStudents = cour.getStudents();
		String checkerList[] = checkerForStudents.split("\\s+");
		List<String> finalChecker = new ArrayList<>();
		for (String i : checkerList) {
			List<Character> chars = new ArrayList<>();
		for (char ch : i.toCharArray()){
			String characterString = Character.toString(ch);

			if (characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) 
			{
			}
			else 
			{
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
			if (getCurrentStudentsOfCourse.equals("")) {
				getCurrentStudentsOfCourse = addedStudent;
			}
			else {
				getCurrentStudentsOfCourse = getCurrentStudentsOfCourse + " " +  addedStudent;
			}
			
			System.out.println("Updated Students of Department: " + getCurrentStudentsOfCourse);
			
			cour.setStudents(getCurrentStudentsOfCourse);
			CourRepo.save(cour);
			
//			Adding Department to Student
			String addedCour = "DepartmentID#" + addedCourID;
			System.out.println("Added Department: " + addedCour);
			
//			Check if Departments of student currently empty
			if (getCurrentCourseOfStudents == null) {
				getCurrentCourseOfStudents = "";
			}
			
			if (getCurrentCourseOfStudents.equals("")) {
				getCurrentCourseOfStudents = addedCour;
			}
			else {
				getCurrentCourseOfStudents = getCurrentCourseOfStudents + " " + addedCour;
			}
			
			System.out.println("Updated Departments of Student: " + getCurrentCourseOfStudents);
			
			stu.setCourse(getCurrentCourseOfStudents);
			StuRepo.save(stu);
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
			
			System.out.println("V:" + vaccinated);
			System.out.println("NV:" + notVaccinated);
			System.out.println("TOTAL:" + totalStudents);
			
			double SV = vaccinated+notVaccinated;
			double SSV = vaccinated/SV;
			double SSSV = SSV*100;
			System.out.println(SSSV);
			
			cour.setNumberOfStudents(totalStudents);
			
			String studentVaccinationRateFinal = Double.toString(SSSV) + "%";
			System.out.println(studentVaccinationRateFinal);
			
			cour.setStudentVaccinationRate(studentVaccinationRateFinal);


			CourRepo.save(cour);
			
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
		
		if (stu==null) {
			return "Given ID for Student does not exist!";
		}
		
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
		
//		Removing Section from Student
		String currentCourseOfStudent = stu.getCourse();
		String Courses[] = currentCourseOfStudent.split("\\s+");
		List<String> currentCourseOfStudentRaw = new ArrayList<>();
		
		for (String i : Courses) {
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
					finalCourseOfStudent = finalCourseOfStudent + " " +  "DeptID#" +  i;
				}
			}
		}
		stu.setName(finalCourseOfStudent);
		StuRepo.save(stu);
		
//		//		---------------------------------
//		VACCINATION RATE LOGIC
		String getCurrentStudentsOfCourse = cour.getStudents();
		
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
		
		System.out.println("V:" + vaccinated);
		System.out.println("NV:" + notVaccinated);
		System.out.println("TOTAL:" + totalStudents);
		
		double SV = vaccinated+notVaccinated;
		double SSV = vaccinated/SV;
		double SSSV = SSV*100;
		System.out.println(SSSV);
		
		cour.setNumberOfStudents(totalStudents);
		
		String studentVaccinationRateFinal = Double.toString(SSSV) + "%";
		System.out.println(studentVaccinationRateFinal);
		
		cour.setStudentVaccinationRate(studentVaccinationRateFinal);


		CourRepo.save(cour);
		
		return "Student removed from Course!";
	}

}
