package app.components;

import java.util.List;

import org.springframework.stereotype.Component;

import app.entity.Vaccine;

@Component
public class VaccineManager {

	@Autowired
	VaccineRepository vaccineRepo;
	
	public String createVaccine(String name) {
		Vaccine newVaccine = new Vaccine();
		newVaccine.setName(name);
		newVaccine.setManufacturer("");
		newVaccine = vaccineRepo.save(newVaccine);
		return "Vaccine Created!";
	}

	public String deleteVaccine(Long id) {
		vaccineRepo.deleteById(id);
		return "Vaccine Deleted!";
	}

	public Optional<Vaccine> viewVaccineDetials(Long id)
	{
		Optional<Vaccine> vaccineDetails = vaccineRepo.findById(id);
		return vaccineDetails;
	}

	public List<Vaccine> viewAllVaccines() {
		return vaccineRepo.findAll();
	}

	public String updateVaccine(Long id, String name, String manufacturer) {
		Vaccine updateVaccine = vaccineRepo.getById(id);
		updateVaccine.setName(name);
		vaccineRepo.save(updateVaccine);
		return "Vaccine Updated!";
	}


}
