package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
   Page<Paciente> findAllByActivoTrue(Pageable paginacion);

   //@Query("select p Paciente p where p.id = :id and p.activo=1")
   //Paciente findActivoById(@NotNull Long id);

   boolean findActivoById(Long idPaciente);

}
