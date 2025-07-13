package med.voll.api.domain.consulta;

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
) {
}
