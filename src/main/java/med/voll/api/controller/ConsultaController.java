package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.consulta.ReservaDeConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

   @Autowired
   private ReservaDeConsultas reserva;

   @PostMapping
   @Transactional
   public ResponseEntity reservar(@RequestBody @Valid DatosReservaConsulta datos){
      var detalleConsulta = reserva.reservar(datos);
      return ResponseEntity.ok(detalleConsulta);
      //return  ResponseEntity.ok(new DatosDetalleConsulta(null, datos.idMedico(), datos.idPaciente(), datos.fecha()));
   }

   @DeleteMapping
   @Transactional
   public ResponseEntity cancelar(@RequestBody @Valid DatosCancelamientoConsulta datos) {
      reserva.cancelar(datos);
      return ResponseEntity.noContent().build();
   }

}
