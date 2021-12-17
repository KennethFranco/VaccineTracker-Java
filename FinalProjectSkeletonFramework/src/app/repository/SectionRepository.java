package app.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Section;
import app.entity.Student;


public interface SectionRepository extends JpaRepository<Section, Long>
{
	public Section getById(Long id);
	public Section findByName(String name);
	public Section findByDeptName(String deptName);
}