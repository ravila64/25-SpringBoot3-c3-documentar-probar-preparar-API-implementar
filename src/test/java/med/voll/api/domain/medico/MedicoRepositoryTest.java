package med.voll.api.domain.medico;

import jakarta.persistence.EntityManager;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.MotivoCancelamiento;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
// se quitan las 2 siguinetes anotaciones si utiliza la base de datos H2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")  // se necesita profle de test
class MedicoRepositoryTest {

   @Autowired
   private MedicoRepository medicoRepository;
   @Autowired
   private EntityManager em;

   @Test
   @DisplayName("Deberia devolver null cuando el medico buscado existe pero no esta disponible")
   void elegirMedicoAleatorioDisponibleEnLaFechaEscenario1() {
      // busca lunes siguiente de la fecha actual a las 10am
      // given  o arrange
      var lunesSiguienteAlas10Am = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
      var medico = registrarMedico("Medico1","medico@algo.com", "123456", Especialidad.CARDIOLOGIA);
      var paciente = registrarPaciente("Paciente1","paciente1@xyz.com", "123456");
      registrarConsulta(medico,paciente,lunesSiguienteAlas10Am,MotivoCancelamiento.OTROS);
      //when o act
      var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA, lunesSiguienteAlas10Am);
      //then o assert
      assertThat(medicoLibre).isNull();
   }
   // nota se hace rollback apenas termina el test, quedando base de datps sin los datos de la ultima prueba
   @Test
   @DisplayName("Deberia devolver medico cuando medico esta disponible en esa fecha")
   void elegirMedicoAleatorioDisponibleEnLaFechaEscenario2() {
      // given  o arrange = dado un cierto contexto, O ARREGLO
      // busca lunes siguiente de la fecha actual a las 10am
      var lunesSiguienteAlas10Am = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
      var medico = registrarMedico("Medico1","medico@algo.com", "123456", Especialidad.CARDIOLOGIA);
      //var paciente = registrarPaciente("Paciente1","paciente1@xyz.com", "123456");
      //registrarConsulta(medico,paciente,lunesSiguienteAlas10Am,MotivoCancelamiento.OTROS);
      //when o act  = cuando ejecuto, O ACTO
      var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA, lunesSiguienteAlas10Am);
      //then o assert = entonces muestro resultado O ACERTAR
      assertThat(medicoLibre).isEqualTo(medico);
   }

   private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha, MotivoCancelamiento motivoCancelamiento) {
      em.persist(new Consulta(null, medico, paciente,fecha,null));   // param null, medico, paciente, fecha => dice redundante
   }

   private Medico registrarMedico(String nombre, String email,
                                  String documento, Especialidad especialidad) {
      var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
      em.persist(medico);
      return medico;
   }

   private Paciente registrarPaciente(String nombre, String email, String documento) {
      var paciente = new Paciente(datosPaciente( nombre, email, documento));
      em.persist(paciente);
      return paciente;
   }

   private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
      return new DatosRegistroMedico(nombre, email, "1245451", documento, especialidad, datosDireccion());
   }

   private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
      return new DatosRegistroPaciente(
            nombre, email, "12145789", documento, datosDireccion()
      );
   }

   private DatosDireccion datosDireccion() {
      return new DatosDireccion(
            "Calle xz",
            "distrito y",
            "Ciudad z",
            "1222",
            "1"
      );
   }
}