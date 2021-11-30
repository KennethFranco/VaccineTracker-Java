package app.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Section;
import app.entity.Student;


public interface SectionRepository extends JpaRepository<Section, Long>
{
	public Section findByName(String name);
	public Section findByDeptName(String deptName);
}
