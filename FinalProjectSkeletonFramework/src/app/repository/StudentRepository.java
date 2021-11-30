package app.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Student;
import app.entity.Vaccine;

public interface StudentRepository extends JpaRepository<Student, Long>
{
	public Student findByName(String name);
	public Student findByYear(int year);
	public Student findByCourse(String course);
	public Student findByDeptName(String deptName);
	public Student findByVaccineName(String vaccineName);
	public Student findByVaccineStatus(Boolean vaccineStatus);
}
