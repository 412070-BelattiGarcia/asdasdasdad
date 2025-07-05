package ar.edu.utn.frc.tup.piii.models.repository;

import ar.edu.utn.frc.tup.piii.models.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {


}
