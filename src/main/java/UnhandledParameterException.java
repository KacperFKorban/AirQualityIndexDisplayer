/**
 * Exception thrown when an unhandled parameter is encountered
 */
public class UnhandledParameterException extends RuntimeException {
  public UnhandledParameterException(String s) {
    super("Wrong parameter " + s);
  }
}
