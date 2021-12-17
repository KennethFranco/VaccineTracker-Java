package app.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Department;
import app.entity.Faculty;
import app.entity.Student;


public interface DepartmentRepository extends JpaRepository<Department, Long>
{
	public Department findByName(String name);
	public Department getById(long id);
}
