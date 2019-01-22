/**
 * Enumeration for air pollution parameter
 */
public enum Param {
  CO,
  SO2,
  NO2,
  PM10,
  PM25,
  C6H6,
  O3;

  /**
   * Returns enum corresponding to given parameter code
   *
   * @param param parameter code as string
   * @return enum corresponding to given parameter code
   */
  public static Param fromString(String param) {
    String s = param.toLowerCase();
    switch(s) {
      case("co"):
        return CO;
      case("so2"):
        return SO2;
      case("no2"):
        return NO2;
      case("pm10"):
        return PM10;
      case("pm2.5"):
        return PM25;
      case("c6h6"):
        return C6H6;
      case("o3"):
        return O3;
    }
    throw new UnhandledParameterException(param);
  }

  /**
   * Returns limit value for a parameter
   *
   * @return limit value for a parameter
   */
  public Double limit() {
    switch(this) {
      case CO:
        return 10000.0;
      case SO2:
        return 350.0;
      case NO2:
        return 200.0;
      case PM10:
        return 50.0;
      case PM25:
        return 25.0;
      case C6H6:
        return 5.0;
      case O3:
        return 25.0;
    }
    return null;
  }
}
