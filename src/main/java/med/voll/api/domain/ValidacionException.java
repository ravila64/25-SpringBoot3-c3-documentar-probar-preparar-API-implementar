package med.voll.api.domain;

// se cambio Throwable por RuntimeException
public class ValidacionException extends RuntimeException {
   public ValidacionException(String mensaje) {
      super(mensaje);
   }
}
