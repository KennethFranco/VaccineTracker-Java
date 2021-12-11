package app.components;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Section;
import app.entity.Student;
import app.entity.Vaccine;
import app.repository.SectionRepository;
import app.repository.StudentRepository;

@Component
public class SectionManager {

	@Autowired
	private SectionRepository sectionRepo;
	
	@Autowired
	private StudentRepository studentRepo;
	
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
		return s;
	}

	public String deleteSection(Long id) {
		sectionRepo.deleteById(id);
		return "Section Deleted!";
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

	public String addFacultyToSection(Long facultyID, Long sectionID) {
		// TODO Auto-generated method stub
		return "Faculty added to Section!";
	}

	public String removeFacultyFromSection(Long facultyID, Long sectionID) {
		// TODO Auto-generated method stub
		return "Faculty removed from Section!";
	}

}
