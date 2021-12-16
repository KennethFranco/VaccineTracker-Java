package app.entity;
import java.util.Arrays;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String deptName;
	
	@Column
	private String students;
	
	@Column 
	private int numberOfStudents;
	
	@Column
	private String studentVaccinationRate;
	
	@Column
	private double deptVaccinationRate;
	
	public Course() {
		
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

	public String getStudents() {
		return students;
	}

	public void setStudents(String string) {
		this.students = string;
	}

	public int getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	public String getStudentVaccinationRate() {
		return studentVaccinationRate;
	}

	public void setStudentVaccinationRate(String studentVaccinationRateFinal) {
		this.studentVaccinationRate = studentVaccinationRateFinal;
	}

	public double getDeptVaccinationRate() {
		return deptVaccinationRate;
	}

	public void setDeptVaccinationRate(double deptVaccinationRate) {
		this.deptVaccinationRate = deptVaccinationRate;
	}

	public Course(Long id, String name, String deptName, String students, int numberOfStudents,
			String studentVaccinationRate, double deptVaccinationRate) {
		super();
		this.id = id;
		this.name = name;
		this.deptName = deptName;
		this.students = students;
		this.numberOfStudents = numberOfStudents;
		this.studentVaccinationRate = studentVaccinationRate;
		this.deptVaccinationRate = deptVaccinationRate;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", deptName=" + deptName + ", students=" + students
				+ ", numberOfStudents=" + numberOfStudents + ", studentVaccinationRate=" + studentVaccinationRate
				+ ", deptVaccinationRate=" + deptVaccinationRate + "]";
	}

	
	
	
	
}
