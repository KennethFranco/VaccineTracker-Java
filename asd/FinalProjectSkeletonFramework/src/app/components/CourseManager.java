package app.components;
import java.util.List;


import org.springframework.stereotype.Component;

import app.entity.Faculty;
import app.entity.Course;
import app.entity.Vaccine;

@Component
public class CourseManager {

	public String createCourse(String name, String deptName) {
		// TODO Auto-generated method stub
		return "Course Created!";
	}

	public String deleteCourse(Long id) {
		// TODO Auto-generated method stub
		return "Course Deleted!";
	}

	public String viewCourseDetails(Long id) {
		// TODO Auto-generated method stub
		return "Course Details:";
	}

	public String updateCourse(Long id, String name, String deptName) {
		// TODO Auto-generated method stub
		return "Course Updated!";
	}

	public String addStudentToCourse(Long studentID, Long courseID) {
		// TODO Auto-generated method stub
		return "Student added to Course!";
	}

	public String removeStudentFromCourse(Long studentID, Long courseID) {
		// TODO Auto-generated method stub
		return "Student Removed from Course!";
	}

}
