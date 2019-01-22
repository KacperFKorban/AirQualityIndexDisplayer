import java.io.FileNotFoundException;
import java.net.MalformedURLException;

/**
 * Main class
 */
public class Main {

  /**
   * main function of the program
   *
   * @param args program arguments
   * @throws MalformedURLException if given a malformed url
   * @throws FileNotFoundException if given a non existing file
   */
  public static void main(String[] args) throws MalformedURLException, FileNotFoundException {
    Utils.handleInput(args);
  }
}
