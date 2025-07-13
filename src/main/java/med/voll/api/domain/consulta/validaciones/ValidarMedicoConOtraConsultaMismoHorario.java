package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosReservaConsulta;

public class ValidarMedicoConOtraConsultaMismoHorario {
   private ConsultaRepository repository;
   public void validar(DatosReservaConsulta datos){
      var medicoTieneConsultaMismoHorario = repository.existsByMedicoIdAndFecha(datos.idMedico(), datos.fecha());
      if(medicoTieneConsultaMismoHorario){
        throw new ValidacionException("Medico ya tiene consulta en esa fecha y hora");
      }
   }
}
