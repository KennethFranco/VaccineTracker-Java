package app.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Course;
import app.entity.Student;


public interface CourseRepository extends JpaRepository<Course, Long>
{
	public Course findByName(String name);
	public Course findByDeptName(String deptName);
	public Course getById(Long courseID);
}