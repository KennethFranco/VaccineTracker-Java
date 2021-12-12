package app.rest.controllers;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.components.FacultyManager;
import app.components.StudentManager;
import app.components.VaccineManager;
import app.components.SectionManager;
import app.components.CourseManager;
import app.components.DepartmentManager;
import app.entity.Faculty;
import app.entity.Student;
import app.entity.Vaccine;
import app.entity.Section;
import app.entity.Course;
import app.entity.Department;

@Component
@Path("/ateneo")
public class AteneoVaccineController {
	
	@Autowired
	private VaccineManager vm;
	
	@Autowired
	private StudentManager sm;
	
	@Autowired
	private FacultyManager fm;
	
	@Autowired
	private SectionManager secm;
	
	@Autowired
	private CourseManager cm;
	
	@Autowired
	private DepartmentManager dm;
	
//	VACCINE
	
	@POST
	@Path("/createVaccineDetails")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String createVaccine(@FormParam("name") String name, @FormParam("manufacturer") String manufacturer) 
	{
		return vm.createVaccine(name, manufacturer);
	}
	
	@POST
	@Path("/deleteVaccineDetails")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String createVaccine(@FormParam("id") Long id) 
	{
		return vm.deleteVaccine(id);
	}
	
	@GET
	@Path("/viewVaccineDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Optional<Vaccine> viewVaccineDetails(@QueryParam("id") Long id)
	{
		return vm.viewVaccineDetails(id);
	}
	
    @GET
    @Path("/viewAllVaccines")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vaccine> viewAllVaccines()
    {
    	return vm.viewAllVaccines();
    }
    
	@POST
	@Path("/updateVaccineDetails")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String updateVaccine(@FormParam("id") Long id, @FormParam("name") String name, @FormParam("manufacturer") String manufacturer) 
	{
		return vm.updateVaccine(id, name, manufacturer);
	}
    
    
//    STUDENT
    
	@POST
	@Path("/createStudentDetails")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String createStuduent(@FormParam("name") String name, @FormParam("year") int year, @FormParam("vaccineID") Long vaccineID) 
	{
		return sm.createStudent(name, year, vaccineID);
	}
	
	@POST
	@Path("/deleteStudentDetails")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String deleteStudent(@FormParam("id") Long id) 
	{
		return sm.deleteStudent(id);
	}
	
	@GET
	@Path("/viewStudentDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewStudentDetails(@QueryParam("id") Long id)
	{
		return sm.viewStudentDetails(id);
	}
	
    @GET
    @Path("/viewAllStudents")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> viewAllStudents()
    {
    	return sm.viewAllStudents();
    }
    
	@POST
	@Path("/updateStudentDetails")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String updateStuduent(@FormParam("id") Long id, @FormParam("name") String name, @FormParam("year") int year, @FormParam("vaccineID") Long vaccineID) 
	{
		return sm.updateStudent(id, name, year, vaccineID);
	}
    
    
    
//	FACULTY
    
	@POST
	@Path("/createFacultyDetails") 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String createFaculty(@FormParam("name") String name, @FormParam("vaccineName") String vaccineName, @FormParam("vaccineStatus") Boolean vaccineStatus) 
	{
		return fm.createFaculty(name, vaccineName, vaccineStatus);
	}
	
	@POST
	@Path("/deleteFacultyDetails")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String deleteFaculty(@FormParam("id") Long id) 
	{
		return fm.deleteFaculty(id);
	}
	
	@GET
	@Path("/viewFacultyDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Optional<Faculty> viewFacultyDetails(@QueryParam("id") Long id)
	{
		return fm.viewFacultyDetails(id);
	}
	
    @GET
    @Path("/viewAllFaculties")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Faculty> viewAllSFaculty()
    {
    	return fm.viewAllFaculty();
    }
    
	@POST
	@Path("/updateFacultyDetails") 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String updateFaculty(@FormParam("id") Long id, @FormParam("name") String name, @FormParam("vaccineName") String vaccineName, @FormParam("vaccineStatus") Boolean vaccineStatus) 
	{
		return fm.updateFaculty(id, name, vaccineName, vaccineStatus);
	}
    
//  SECTION
    
    @POST
	@Path("/createSectionDetails") 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String createSection(@FormParam("name") String name) 
	{
		return secm.createSection(name);
	}
	
	@POST
	@Path("/deleteSectionDetails")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String deleteSection(@FormParam("id") Long id) 
	{
		return secm.deleteSection(id);
	}
	
    @POST
	@Path("/addStudentToSection") 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String addStudentToSection(@FormParam("studentID") Long studentID, @FormParam("sectionID") Long sectionID) 
	{
		return secm.addStudentToSection(studentID, sectionID);
	}
    
    @POST
	@Path("/removeStudentFromSection") 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String removeStudentFromSection(@FormParam("studentID") Long studentID, @FormParam("sectionID") Long sectionID) 
	{
		return secm.removeStudentFromSection(studentID, sectionID);
	}
    
    @POST
	@Path("/addFacultyToSection") 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String addFacultyToSection(@FormParam("facultyID") Long facultyID, @FormParam("sectionID") Long sectionID) 
	{
		return secm.addFacultyToSection(facultyID, sectionID);
	}
    
    @POST
	@Path("/removeFacultyFromSection") 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String removeFacultyFromSection(@FormParam("facultyID") Long facultyID, @FormParam("sectionID") Long sectionID) 
	{
		return secm.removeFacultyFromSection(facultyID, sectionID);
	}
    
	
	@GET
	@Path("/viewSectionDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewSectionDetails(@QueryParam("id") Long id)
	{
		return secm.viewSectionDetails(id);
	}
	
    @POST
	@Path("/updateSectionDetails") 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String updateSection(@FormParam("id") Long id, @FormParam("name") String name) 
	{
		return secm.updateSection(id, name);
	}
	
//	COURSE
	
	@POST
	@Path("/createCourseDetails") 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String createCourse(@FormParam("name") String name, @FormParam("deptName") String deptName) 
	{
		return cm.createCourse(name, deptName);
	}
	
	@POST
	@Path("/deleteCourseDetails")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String deleteCourse(@FormParam("id") Long id) 
	{
		return cm.deleteCourse(id);
	}
	
	@POST
	@Path("/addStudentToCourse") 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String addStudentToCourse(@FormParam("studentID") Long studentID, @FormParam("courseID") Long courseID) 
	{
		return cm.addStudentToCourse(studentID, courseID);
	}
	
	@POST
	@Path("/removeStudentFromCourse") 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String removeStudentFromCourse(@FormParam("studentID") Long studentID, @FormParam("courseID") Long courseID) 
	{
		return cm.removeStudentFromCourse(studentID, courseID);
	}
	
	@GET
	@Path("/viewCourseDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewCourseDetails(@QueryParam("id") Long id)
	{
		return cm.viewCourseDetails(id);
	}
	
	@POST
	@Path("/updateCourseDetails") 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String updateCourse(@FormParam("id") Long id, @FormParam("name") String name, @FormParam("deptName") String deptName) 
	{
		return cm.updateCourse(id, name, deptName);
	}
	
//	DEPARTMENT
	
	@POST
	@Path("/createDepartmentDetails") 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String createDepartment(@FormParam("name") String name) 
	{
		return dm.createDepartment(name);
	}
	
	@POST
	@Path("/deleteDepartmentDetails")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String deleteDepartment(@FormParam("id") Long id) 
	{
		return dm.deleteDepartment(id);
	}
	
	@POST
	@Path("/addStudentToDepartment")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String addStudentToDepartment(@FormParam("studentID") Long studentID, @FormParam("departmentID") Long departmentID) 
	{
		return dm.addStudentToDepartment(studentID, departmentID);
	}
	
	@POST
	@Path("/removeStudentFromDepartment")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String removeStudentFromDepartment(@FormParam("studentID") Long studentID, @FormParam("departmentID") Long departmentID) 
	{
		return dm.removeStudentFromDepartment(studentID, departmentID);
	}
	
	@POST
	@Path("/addFacultyToDepartment")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String addFacultyToDepartment(@FormParam("facultyID") Long facultyID, @FormParam("departmentID") Long departmentID) 
	{
		return dm.addFacultyToDepartment(facultyID, departmentID);
	}

	@POST
	@Path("/removeFacultyFromDepartment")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String removeFacultyFromDepartment(@FormParam("facultyID") Long facultyID, @FormParam("departmentID") Long departmentID) 
	{
		return dm.removeFacultyFromDepartment(facultyID, departmentID);
	}
	
	@GET
	@Path("/viewDepartmentDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewDepartmentDetails(@QueryParam("id") Long id)
	{
		return dm.viewDepartmentDetails(id);
	}
	
	@POST
	@Path("/updateDepartmentDetails") 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public String updateDepartment(@FormParam("id") Long id, @FormParam("name") String name) 
	{
		return dm.updateDepartment(id, name);
	}
	
	
}
