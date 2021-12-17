package app.components;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Faculty;
import app.entity.Section;
import app.entity.Student;
import app.entity.Vaccine;
import app.repository.FacultyRepository;
import app.repository.SectionRepository;
import app.repository.StudentRepository;

@Component
public class SectionManager {

	@Autowired
	private SectionRepository sectionRepo;
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private FacultyRepository facultyRepo;
	
	public String createSection(String name) {
		Section s = makeSection(name);
		sectionRepo.save(s);
		// TODO Auto-generated method stub
		return "Section Created!";
	}
	
	private Section makeSection(String name)
	{
		Section s = new Section();
		s.setName(name);
		s.setStudents("");
		s.setFaculties("");
		return s;
	}

	public String deleteSection(Long id) {
		Section s = sectionRepo.getById(id);
		
		if (s==null) {
			return "The section you are trying to delete does not exist! Please provide a valid ID.";
		}
		
		String currentFacultiesOfSection = s.getFaculties();
		String currentStudentsOfSection = s.getStudents();
		
		if (currentFacultiesOfSection.equals("")) {
			if (currentStudentsOfSection.equals("")){
				sectionRepo.deleteById(id);
				return "Section Deleted!";
			}
			else {
				return "Section currently has students inside of its record. Please remove these students from this section first before attempting to delete this record.";
			}
		}
		else {
			return "Section currently has faculties inside of its record. Please remove these faculties from this section first before attempting to delete this record.";
		}
	}

	public String viewSectionDetails(Long id) {
		// TODO Auto-generated method stub
		return "Section Details:" + sectionRepo.findById(id);
	}

	public String updateSection(Long id, String name) {
		Section s = sectionRepo.getById(id);
		
		s.setName(name);
		sectionRepo.save(s);
		return "Section Updated!";
	}

	public String addStudentToSection(Long studentID, Long sectionID) {
		Boolean exists = false;
		Section sec = sectionRepo.getById(sectionID);
		Student stu = studentRepo.getById(studentID);
		
		if (sec==null) {
			return "Given ID for Section does not exist!";
		}
		
		if (stu==null) {
			return "Given ID for Student does not exist!";
		}
		
		String getCurrentStudentsOfSection = sec.getStudents();
		String getCurrentSectionsOfStudent = stu.getSections();
		
		Long addedStudentID = studentID;
		Long addedSectionID = sectionID;
		

		
		if (getCurrentStudentsOfSection == null) {
			getCurrentStudentsOfSection = "";
		}
		
//		Checking if Student is already in Section
		String checkerForStudents = sec.getStudents();
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
			return "This student is already present inside that section!";
		}
		else {
//			Adding Student to Section
			String addedStudent = "ID#" + addedStudentID;
			System.out.println("Added Student:" + addedStudent);
			
//			Check if students of section currently empty
			if (getCurrentStudentsOfSection.equals("")) {
				getCurrentStudentsOfSection = addedStudent;
			}
			else {
				getCurrentStudentsOfSection = getCurrentStudentsOfSection + " " +  addedStudent;
			}
			
			System.out.println("Updated Students of Section: " + getCurrentStudentsOfSection);
			
			sec.setStudents(getCurrentStudentsOfSection);
			sectionRepo.save(sec);
			
//			Adding Section to Student
			String addedSection = "SectionID#" + addedSectionID;
			System.out.println("Added Section: " + addedSection);
			
//			Check if sections of student currently empty
			if (getCurrentSectionsOfStudent == null) {
				getCurrentSectionsOfStudent = "";
			}
			
			if (getCurrentSectionsOfStudent.equals("")) {
				getCurrentSectionsOfStudent = addedSection;
			}
			else {
				getCurrentSectionsOfStudent = getCurrentSectionsOfStudent + " " + addedSection;
			}
			
			System.out.println("Updated Sections of Student: " + getCurrentSectionsOfStudent);
			
			stu.setSections(getCurrentSectionsOfStudent);
			studentRepo.save(stu);
			
//			---------------------------------------------------------
//			---------------------------------------------------------
//			VACCINATION RATE LOGIC
			List<Student> allStudents = studentRepo.findAll();
			String test[] = getCurrentStudentsOfSection.split("\\s+");
			System.out.println(test[0]);
			System.out.println(test);
			
			int vaccinated = 0;
			int notVaccinated = 0;
			int totalStudents = 0;
			
			List<String> currentStudentsInSection = new ArrayList<>();
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
				currentStudentsInSection.add(numberString);
			}
			
			System.out.println(currentStudentsInSection);
			
			
			for (Student i: allStudents) {
				System.out.println("current id: " + i.getId());
				for(String code: currentStudentsInSection) {
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
			
			sec.setNumberOfStudents(totalStudents);
			
			String studentVaccinationRateFinal = Double.toString(SSSV) + "%";
			System.out.println(studentVaccinationRateFinal);
			
			sec.setStudentVaccinationRate(studentVaccinationRateFinal);


			sectionRepo.save(sec);
			
			return "Student added to Section!\n Current Students:\n" + getCurrentStudentsOfSection;
		}
		
		

	}

	public String removeStudentFromSection(Long studentID, Long sectionID) {
		Boolean exists = true;
		Section sec = sectionRepo.getById(sectionID);
		Student stu = studentRepo.getById(studentID);
		
		if (sec==null) {
			return "Given ID for Section does not exist!";
		}
		
		if (stu==null) {
			return "Given ID for Student does not exist!";
		}
		
		String currentStudents = sec.getStudents();
		Long removedStudentID = stu.getId();
		Long removedSectionID = sec.getId();
		
		if (currentStudents == null) {
			return "No students in current section!";
		}
		
//		CHECKING IF IT EXISTS
		String checkerForStudents = sec.getStudents();
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
			return "This student exists, but is not present inside this section!";
		}

		
//		Removing Student from Section

		String students[] = currentStudents.split("\\s+");
		List<String> currentStudentsInSection = new ArrayList<>();
		
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
			currentStudentsInSection.add(numberString);
		}
		
//		Removes it
		for (String i : currentStudentsInSection) {
			  if (i.equals(removedStudentID.toString())) {
				  currentStudentsInSection.remove(i);
				  break;

			  }
		}

		System.out.println(currentStudentsInSection);
		

		String finalSectionStudents = "";
		for (String i : currentStudentsInSection) {
			if (i.equals("")){
				
			}
			else {
				if (finalSectionStudents.equals("")) {
					finalSectionStudents = "ID#" + i;
				}
				else {
					finalSectionStudents = finalSectionStudents + " " +  "ID#" + i;
				}
			}

		}
		
		System.out.println(finalSectionStudents);
		
		sec.setStudents(finalSectionStudents);
		sectionRepo.save(sec);
		
//		Removing Section from Student
		String currentSectionsOfStudent = stu.getSections();
		String sections[] = currentSectionsOfStudent.split("\\s+");
		List<String> currentSectionsOfStudentRaw = new ArrayList<>();
		
		for (String i : sections) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("S") || characterString.equals("e") || characterString.equals("c") || characterString.equals("t") ||  characterString.equals("i") || characterString.equals("o") ||  characterString.equals("n") ||characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
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
			currentSectionsOfStudentRaw.add(numberString);
		}
		
		for (String i : currentSectionsOfStudentRaw) {
			  if (i.equals(removedSectionID.toString())) {
				  currentSectionsOfStudentRaw.remove(i);
				  break;

			  }
		}
		
		String finalSectionsOfStudent = "";
		for (String i: currentSectionsOfStudentRaw) {
			if (i.equals("")) {
				
			}
			else {
				if (finalSectionsOfStudent.equals("")) {
					finalSectionsOfStudent =  "SectionID#" + i;
				}
				else {
					finalSectionsOfStudent = finalSectionsOfStudent + " " +  "SectionID#" +  i;
				}
			}
		}
		stu.setSections(finalSectionsOfStudent);
		studentRepo.save(stu);
		
		
//		---------------------------------
//		---------------------------------
//		VACCINATION RATE LOGIC
		String getCurrentStudentsOfSection = sec.getStudents();
		
		List<Student> allStudents = studentRepo.findAll();
		String test[] = getCurrentStudentsOfSection.split("\\s+");
		System.out.println(test[0]);
		System.out.println(test);
		
		int vaccinated = 0;
		int notVaccinated = 0;
		int totalStudents = 0;
		
		List<String> currentStudentsInSection2 = new ArrayList<>();
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
			currentStudentsInSection2.add(numberString);
		}
		
		System.out.println(currentStudentsInSection2);
		
		
		for (Student i: allStudents) {
			System.out.println("current id: " + i.getId());
			for(String code: currentStudentsInSection) {
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
		
		sec.setNumberOfStudents(totalStudents);
		
		String studentVaccinationRateFinal = Double.toString(SSSV) + "%";
		System.out.println(studentVaccinationRateFinal);
		
		sec.setStudentVaccinationRate(studentVaccinationRateFinal);


		sectionRepo.save(sec);
		
		return "Student removed from Section!";
	}

	
//	FACULTY
//	
//	
//	
	public String addFacultyToSection(Long facultyID, Long sectionID) {
		Boolean exists = false;
		Section sec = sectionRepo.getById(sectionID);
		Faculty fac = facultyRepo.getById(facultyID);
		
		if (sec==null) {
			return "Given ID for Section does not exist!";
		}
		
		if (fac==null) {
			return "Given ID for Faculty does not exist!";
		}
		
		String getCurrentFacultiesOfSection = sec.getFaculties();
		String getCurrentSectionsOfFaculty = fac.getSections();
		
		Long addedFacultyID = facultyID;
		Long addedSectionID = sectionID;
		
//		Checking if Faculty is already in Section
		String checkerForFaculties = sec.getFaculties();
		String checkerList[] = checkerForFaculties.split("\\s+");
		List<String> finalChecker = new ArrayList<>();
		for (String i : checkerList) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("F") || characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
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
			return "This faculty is already present inside that section!";
		}
		else {
//			Adding Faculty to Section
			String addedFaculty = "FID#" + addedFacultyID;
			System.out.println("Added Faculty:" + addedFaculty);
			
//			Check if students of section currently empty
			if (getCurrentFacultiesOfSection.equals("")) {
				getCurrentFacultiesOfSection = addedFaculty;
			}
			else {
				getCurrentFacultiesOfSection = getCurrentFacultiesOfSection + " " +  addedFaculty;
			}
			
			System.out.println("Updated Faculties of Section: " + getCurrentFacultiesOfSection);
			
			sec.setFaculties(getCurrentFacultiesOfSection);
			sectionRepo.save(sec);
			
//			Adding Section to Faculty
			String addedSection = "SectionID#" + addedSectionID;
			System.out.println("Added Section: " + addedSection);
			
//			Check if sections of student currently empty
			if (getCurrentSectionsOfFaculty == null) {
				getCurrentSectionsOfFaculty = "";
			}
			
			if (getCurrentSectionsOfFaculty.equals("")) {
				getCurrentSectionsOfFaculty = addedSection;
			}
			else {
				getCurrentSectionsOfFaculty = getCurrentSectionsOfFaculty + " " + addedSection;
			}
			
			System.out.println("Updated Sections of Student: " + getCurrentSectionsOfFaculty);
			
			fac.setSections(getCurrentSectionsOfFaculty);
			facultyRepo.save(fac);
			
//			---------------------------------------------------------
//			---------------------------------------------------------
//			VACCINATION RATE LOGIC
			List<Faculty> allFaculties = facultyRepo.findAll();
			String test[] = getCurrentFacultiesOfSection.split("\\s+");
			System.out.println(test[0]);
			System.out.println(test);
			
			int vaccinated = 0;
			int notVaccinated = 0;
			int totalFaculties = 0;
			
			List<String> currentFacultiesInSection = new ArrayList<>();
//			Removing ID# from each instance in the array
			for (String i : test) {
				List<Character> chars = new ArrayList<>();
		        for (char ch : i.toCharArray()) {
		            String characterString = Character.toString(ch);

		            if (characterString.equals("F") || characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
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
				currentFacultiesInSection.add(numberString);
			}
			
			System.out.println(currentFacultiesInSection);
			
			
			for (Faculty i: allFaculties) {
				System.out.println("current id: " + i.getId());
				for(String code: currentFacultiesInSection) {
					System.out.println(code);
					if(code.equals(i.getId().toString())) {
						Boolean checker = i.getVaccineStatus();
						if (checker==true) {
							vaccinated+=1;
						}
						else {
							notVaccinated+=1;
						}
						totalFaculties+=1;
					}
				}
			}
			
			System.out.println("V:" + vaccinated);
			System.out.println("NV:" + notVaccinated);
			System.out.println("TOTAL:" + totalFaculties);
			
			double SV = vaccinated+notVaccinated;
			double SSV = vaccinated/SV;
			double SSSV = SSV*100;
			System.out.println(SSSV);
			
			sec.setNumberOfFaculty(totalFaculties);
			
			String facultyVaccinationRateFinal = Double.toString(SSSV) + "%";
			System.out.println(facultyVaccinationRateFinal);
			
			sec.setFacultyVaccinationRate(facultyVaccinationRateFinal);

			sectionRepo.save(sec);
			
			return "Faculty added to Section!\n Current Faculties:\n" + getCurrentFacultiesOfSection;
		}
		
		

	}

	public String removeFacultyFromSection(Long facultyID, Long sectionID) {
		Boolean exists = true;
		Section sec = sectionRepo.getById(sectionID);
		Faculty fac = facultyRepo.getById(facultyID);
		
		if (sec==null) {
			return "Given ID for Section does not exist!";
		}
		
		if (fac==null) {
			return "Given ID for Faculty does not exist!";
		}
		
		String currentFaculties = sec.getFaculties();
		Long removedFacultyID = fac.getId();
		Long removedSectionID = sec.getId();
		
		if (currentFaculties == null) {
			return "No faculties in current section!";
		}
		
//		CHECKING IF IT EXISTS
		String checkerForFaculties = sec.getFaculties();
		String checkerList[] = checkerForFaculties.split("\\s+");
		List<String> finalChecker = new ArrayList<>();
		for (String i : checkerList) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("F") || characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
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
			return "This faculty exists, but is not present inside this section!";
		}

		
//		Removing Faculty from Section

		String faculties[] = currentFaculties.split("\\s+");
		List<String> currentFacultiesInSection = new ArrayList<>();
		
//		Removing ID# from each instance in the array
		for (String i : faculties) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("F") || characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
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
			currentFacultiesInSection.add(numberString);
		}
		
//		Removes it
		for (String i : currentFacultiesInSection) {
			  if (i.equals(removedFacultyID.toString())) {
				  currentFacultiesInSection.remove(i);
				  break;

			  }
		}

		System.out.println(currentFacultiesInSection);
		

		String finalSectionFaculties = "";
		for (String i : currentFacultiesInSection) {
			if (i.equals("")){
				
			}
			else {
				if (finalSectionFaculties.equals("")) {
					finalSectionFaculties = "FID#" + i;
				}
				else {
					finalSectionFaculties = finalSectionFaculties + " " +  "FID#" + i;
				}
			}

		}
		
		System.out.println(finalSectionFaculties);
		
		sec.setFaculties(finalSectionFaculties);
		sectionRepo.save(sec);
		
//		Removing Section from Student
		String currentSectionsOfFaculty = fac.getSections();
		String sections[] = currentSectionsOfFaculty.split("\\s+");
		List<String> currentSectionsOfFacultyRaw = new ArrayList<>();
		
		for (String i : sections) {
			List<Character> chars = new ArrayList<>();
	        for (char ch : i.toCharArray()) {
	            String characterString = Character.toString(ch);

	            if (characterString.equals("S") || characterString.equals("e") || characterString.equals("c") || characterString.equals("t") ||  characterString.equals("i") || characterString.equals("o") ||  characterString.equals("n") ||characterString.equals("I") || characterString.equals("D") || characterString.equals("#")) {
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
			currentSectionsOfFacultyRaw.add(numberString);
		}
		
		for (String i : currentSectionsOfFacultyRaw) {
			  if (i.equals(removedSectionID.toString())) {
				  currentSectionsOfFacultyRaw.remove(i);
				  break;

			  }
		}
		
		String finalSectionsOfFaculty = "";
		for (String i: currentSectionsOfFacultyRaw) {
			if (i.equals("")) {
				
			}
			else {
				if (finalSectionsOfFaculty.equals("")) {
					finalSectionsOfFaculty =  "SectionID#" + i;
				}
				else {
					finalSectionsOfFaculty = finalSectionsOfFaculty + " " +  "SectionID#" +  i;
				}
			}
		}
		fac.setSections(finalSectionsOfFaculty);
		facultyRepo.save(fac);
		
		
//		---------------------------------
//		---------------------------------
//		VACCINATION RATE LOGIC
		String getCurrentFacultiesOfSection = sec.getFaculties();
		
		List<Faculty> allFaculties = facultyRepo.findAll();
		String test[] = getCurrentFacultiesOfSection.split("\\s+");
		System.out.println(test[0]);
		System.out.println(test);
		
		int vaccinated = 0;
		int notVaccinated = 0;
		int totalFaculties = 0;
		
		List<String> currentFacultiesInSection2 = new ArrayList<>();
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
			currentFacultiesInSection2.add(numberString);
		}
		
		System.out.println(currentFacultiesInSection2);
		
		
		for (Faculty i: allFaculties) {
			System.out.println("current id: " + i.getId());
			for(String code: currentFacultiesInSection) {
				System.out.println(code);
				if(code.equals(i.getId().toString())) {
					Boolean checker = i.getVaccineStatus();
					if (checker==true) {
						vaccinated+=1;
					}
					else {
						notVaccinated+=1;
					}
					totalFaculties+=1;
				}
			}
		}
		
		System.out.println("V:" + vaccinated);
		System.out.println("NV:" + notVaccinated);
		System.out.println("TOTAL:" + totalFaculties);
		
		double SV = vaccinated+notVaccinated;
		double SSV = vaccinated/SV;
		double SSSV = SSV*100;
		System.out.println(SSSV);
		
		sec.setNumberOfFaculty(totalFaculties);
		
		String facultyVaccinationRateFinal = Double.toString(SSSV) + "%";
		System.out.println(facultyVaccinationRateFinal);
		
		sec.setFacultyVaccinationRate(facultyVaccinationRateFinal);


		sectionRepo.save(sec);
		
		return "Faculty removed from Section!";
	}

}