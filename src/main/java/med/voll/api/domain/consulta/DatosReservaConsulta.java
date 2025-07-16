package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record  DatosReservaConsulta(
      @NotNull
      Long idMedico,
      @NotNull
      Long idPaciente,
      @NotNull
      @Future LocalDateTime fecha,
      Especialidad especialidad
      //@JsonAlias("paciente_id")Long idPaciente,
      //@JsonAlias("medico_id") Long idMedico,
) {
}
