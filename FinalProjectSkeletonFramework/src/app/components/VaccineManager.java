package app.components;

import java.util.List;

import org.springframework.stereotype.Component;

import app.entity.Vaccine;

@Component
public class VaccineManager {

	public String createVaccine(String name, String manufacturer) {
		return "Vaccine Created!";
	}

	public String deleteVaccine(Long id) {
		// TODO Auto-generated method stub
		return "Vaccine Deleted!";
	}

	public String viewVaccineDetails(Long id) {
		// TODO Auto-generated method stub
		return "Vaccine Details:";
	}

	public List<Vaccine> viewAllVaccines() {
		return null;
	}

	public String updateVaccine(Long id, String name, String manufacturer) {
		// TODO Auto-generated method stub
		return "Vaccine Updated!";
	}



}
