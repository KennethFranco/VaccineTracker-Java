package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entity.Vaccine;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long>
{
	public Vaccine findByName(String name);
	public Vaccine getById(Long id);
}	