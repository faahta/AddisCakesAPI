package et.addis.home_cakes.util;


/**
 * Created by Fassil on 24/06/20.
 */
public class LogConstants {
    public static final String PARAMETER_2 = "{} {}";
    public static final String PARAMETER_3 = "{} {} {}";
    public static final String METHOD_START = " method start.";
    public static final String METHOD_END = " method end.";
    public static final String INPUT_PARAMETER = " input parameter ";

    public static final String UUID_KEY = "UUID";
    public static final String TRANSACTION_ID_KEY = "TRANSACTION_ID_KEY";

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;

    //log levels
    public enum LogLevel {
        INFO, DEBUG, WARNING, ERROR;
    }


}
