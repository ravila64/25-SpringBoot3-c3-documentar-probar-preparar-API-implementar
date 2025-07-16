package med.voll.api.domain.consulta.validaciones.reserva;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorConsultaConAnticipacionReserva")
public class ValidadorConsultaConAnticipacion implements ValidadorDeConsultas {
   public void validar(DatosReservaConsulta datos) {
      var fechaConsulta = datos.fecha();
      var ahora = LocalDateTime.now();
      var diferecniaEnMinutos = Duration.between(ahora, fechaConsulta).toMinutes();
      if(diferecniaEnMinutos < 30 ){
         throw new ValidacionException("Horario seleccionado com menos a 30 minutos de anticipación ");
      }
   }
}
