package app.repository;
import org.springframework.data.jpa.repository.JpaRepository;


import app.entity.Faculty;
import app.entity.Student;


public interface FacultyRepository extends JpaRepository<Faculty, Long>
{
	public Faculty findByName(String name);
	public Faculty findByDeptName(String deptName);
	public Faculty findByVaccineName(String vaccineName);
	public Faculty findByVaccineStatus(Boolean vaccineStatus);
	public Faculty getById(long id);
}
