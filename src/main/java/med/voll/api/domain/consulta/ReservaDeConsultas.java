package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.validaciones.cancelamiento.ValidadorCancelamientoDeConsulta;
import med.voll.api.domain.consulta.validaciones.reserva.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaDeConsultas {

   @Autowired
   private MedicoRepository medicoRepository;
   @Autowired
   private PacienteRepository pacienteRepository;
   @Autowired   // esto inyectar dependencias
   private ConsultaRepository consultaRepository;
   @Autowired
   private List<ValidadorCancelamientoDeConsulta> validadoresCancelamiento;

   @Autowired
   private List<ValidadorDeConsultas> validadores;

   public DatosDetalleConsulta reservar(DatosReservaConsulta datos){

      if(!pacienteRepository.existsById(datos.idPaciente())){
         throw new ValidacionException("No existe un paciente con el id informado");
      }

      if(datos.idMedico()!=null && !medicoRepository.existsById(datos.idMedico())){
         throw new ValidacionException("No existe un medico");
      }

      // validaciones, recorrer la lista y ejecutar validar, de todos los validadores
      validadores.forEach(v->v.validar(datos));

      // vamos verificar un medico si se paso como null y elegir medico
      var medico = elegirMedico(datos);
      //var medico = medicoRepository.findById(datos.idMedico()).get();
      // get() trae el objeto y no optional
      if(medico==null){
         throw new ValidacionException("No existe un medico disponible en ese horario");
      }
      var paciente = pacienteRepository.findById(datos.idPaciente()).get();
      //var consulta = new Consulta(null, medico, paciente, datos.fecha());
      var consulta = new Consulta(null, medico, paciente, datos.fecha(), null);
      consultaRepository.save(consulta);
      return new DatosDetalleConsulta(consulta);
   }

   // ide coloca Object porque vaiable es var, para este caso es tipo Mediico
   private Medico elegirMedico(DatosReservaConsulta datos) {
      if(datos.idMedico()!=null){
         // reference, trae GetById()+get() = Objeto Medico
         return medicoRepository.getReferenceById(datos.idMedico());
      }
      if(datos.especialidad()==null){
         throw new ValidacionException("Es necesario elegir una especialidad del medico");
      }
      return medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(datos.especialidad(),
            datos.fecha());
   }

   public void cancelar(DatosCancelamientoConsulta datos) {
      if (!consultaRepository.existsById(datos.idConsulta())) {
         throw new ValidacionException("Id de la consulta informado no existe!");
      }
      validadoresCancelamiento.forEach(v -> v.validar(datos));
      var consulta = consultaRepository.getReferenceById(datos.idConsulta());
      consulta.cancelar(datos.motivo());
   }

}
