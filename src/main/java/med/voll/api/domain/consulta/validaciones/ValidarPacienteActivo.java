package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.paciente.PacienteRepository;

public class ValidarPacienteActivo {

   private PacienteRepository repository;
   public void validar(DatosReservaConsulta datos) {
      var pacienteActivo = repository.findActivoById(datos.idPaciente());
      if(!pacienteActivo){
         throw new ValidacionException("Consulta no puede ser reservada por Paciente Inactivo");
      }
   }
}
