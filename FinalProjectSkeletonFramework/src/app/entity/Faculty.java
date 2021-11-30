package app.entity;
import java.util.Arrays;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Faculty {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String deptName;
	
	@Column
	private String[] sections;
	
	@Column
	private String vaccineName;
	
	@Column
	private Boolean vaccineStatus;
	
	public Faculty() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String[] getSections() {
		return sections;
	}

	public void setSections(String[] sections) {
		this.sections = sections;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

	public Boolean getVaccineStatus() {
		return vaccineStatus;
	}

	public void setVaccineStatus(Boolean vaccineStatus) {
		this.vaccineStatus = vaccineStatus;
	}

	public Faculty(Long id, String name, String deptName, String[] sections, String vaccineName,
			Boolean vaccineStatus) {
		super();
		this.id = id;
		this.name = name;
		this.deptName = deptName;
		this.sections = sections;
		this.vaccineName = vaccineName;
		this.vaccineStatus = vaccineStatus;
	}

	@Override
	public String toString() {
		return "Faculty [id=" + id + ", name=" + name + ", deptName=" + deptName + ", sections="
				+ Arrays.toString(sections) + ", vaccineName=" + vaccineName + ", vaccineStatus=" + vaccineStatus + "]";
	}
	
	
	
}
