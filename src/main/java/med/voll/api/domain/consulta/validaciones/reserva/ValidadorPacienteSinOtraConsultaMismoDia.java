package med.voll.api.domain.consulta.validaciones.reserva;

import jakarta.validation.ValidationException;
import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSinOtraConsultaMismoDia implements ValidadorDeConsultas {

   @Autowired
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

   @Component
   public static class ValidadorPacienteActivo  implements ValidadorDeConsultas {

      @Autowired
      private PacienteRepository repository;

      public void validar(DatosReservaConsulta datos) {
         var pacienteActivo = repository.findActivoById(datos.idPaciente());
         if(!pacienteActivo){
            throw new ValidacionException("Consulta no puede ser reservada por Paciente Inactivo");
         }
      }
   }
}
