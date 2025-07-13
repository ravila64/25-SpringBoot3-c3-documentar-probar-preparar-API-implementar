package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosReservaConsulta;

public class ValidarPacienteSinOtraConsultaMismoDia {
   private ConsultaRepository repository;
   public void validar(DatosReservaConsulta datos){
      var fechaConsulta = datos.fecha();
      var primerHorario = fechaConsulta.withHour(7);
      var ultimoHorario = fechaConsulta.withHour(18);
      var pacienteTieneOtraConsulatMismoDia= repository.existsByPacienteIdAndFechaBetween(datos.idPaciente(), primerHorario, ultimoHorario);
      if (pacienteTieneOtraConsulatMismoDia){
         throw new ValidationException("Paciente ya tiene Cita el mismo dia ");
      }
   }
}
