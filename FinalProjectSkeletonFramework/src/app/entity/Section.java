package app.entity;
import java.util.Arrays;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Section {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String deptName; 
	
	@Column
	private String[] students;
	
	@Column
	private String[] faculties;
	
	@Column
	private int numberOfStudents;
	
	@Column 
	private int numberOfFaculty;
	
	@Column 
	private double studentVaccinationRate;
	
	@Column
	private double facultyVaccinationRate;
	
	@Column
	private double deptVaccinationRate;
	
	public Section() {
		
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

	public String[] getStudents() {
		return students;
	}

	public void setStudents(String[] students) {
		this.students = students;
	}

	public String[] getFaculties() {
		return faculties;
	}

	public void setFaculties(String[] faculties) {
		this.faculties = faculties;
	}

	public int getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	public int getNumberOfFaculty() {
		return numberOfFaculty;
	}

	public void setNumberOfFaculty(int numberOfFaculty) {
		this.numberOfFaculty = numberOfFaculty;
	}

	public double getStudentVaccinationRate() {
		return studentVaccinationRate;
	}

	public void setStudentVaccinationRate(double studentVaccinationRate) {
		this.studentVaccinationRate = studentVaccinationRate;
	}

	public double getFacultyVaccinationRate() {
		return facultyVaccinationRate;
	}

	public void setFacultyVaccinationRate(double facultyVaccinationRate) {
		this.facultyVaccinationRate = facultyVaccinationRate;
	}

	public double getDeptVaccinationRate() {
		return deptVaccinationRate;
	}

	public void setDeptVaccinationRate(double deptVaccinationRate) {
		this.deptVaccinationRate = deptVaccinationRate;
	}

	public Section(Long id, String name, String deptName, String[] students, String[] faculties, int numberOfStudents,
			int numberOfFaculty, double studentVaccinationRate, double facultyVaccinationRate,
			double deptVaccinationRate) {
		super();
		this.id = id;
		this.name = name;
		this.deptName = deptName;
		this.students = students;
		this.faculties = faculties;
		this.numberOfStudents = numberOfStudents;
		this.numberOfFaculty = numberOfFaculty;
		this.studentVaccinationRate = studentVaccinationRate;
		this.facultyVaccinationRate = facultyVaccinationRate;
		this.deptVaccinationRate = deptVaccinationRate;
	}

	@Override
	public String toString() {
		return "Section [id=" + id + ", name=" + name + ", deptName=" + deptName + ", students="
				+ Arrays.toString(students) + ", faculties=" + Arrays.toString(faculties) + ", numberOfStudents="
				+ numberOfStudents + ", numberOfFaculty=" + numberOfFaculty + ", studentVaccinationRate="
				+ studentVaccinationRate + ", facultyVaccinationRate=" + facultyVaccinationRate
				+ ", deptVaccinationRate=" + deptVaccinationRate + "]";
	}
	
	
	
}
