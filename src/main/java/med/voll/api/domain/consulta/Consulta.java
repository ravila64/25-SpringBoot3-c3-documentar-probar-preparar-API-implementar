package med.voll.api.domain.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Table(name="consultas")
@Entity(name="Consulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Consulta {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne(fetch= FetchType.LAZY)
   @JoinColumn(name="medico_id")
   private Medico medico;

   @ManyToOne(fetch= FetchType.LAZY)
   @JoinColumn(name="paciente_id")
   private Paciente paciente;

   private LocalDateTime fecha;

   @Column(name = "motivo_cancelamiento")
   @Enumerated(EnumType.STRING)
   private MotivoCancelamiento motivoCancelamiento;

//   public Consulta(Long id, Medico medico, Paciente paciente, LocalDateTime fecha, MotivoCancelamiento motivoCancelamiento) {
//      this.id = id;
//      this.medico = medico;
//      this.paciente = paciente;
//      this.fecha = fecha;
//      this.motivoCancelamiento = motivoCancelamiento;
//   }

   public void cancelar(MotivoCancelamiento motivo) {
      this.motivoCancelamiento = motivo;
   }
}
