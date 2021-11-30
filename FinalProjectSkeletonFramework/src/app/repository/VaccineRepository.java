package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Vaccine;

public interface VaccineRepository extends JpaRepository<Vaccine, Long>
{
	public Vaccine findByName(String name);
}
