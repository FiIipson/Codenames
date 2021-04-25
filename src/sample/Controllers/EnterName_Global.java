package sample.Controllers;

import static sample.Controllers.ErrorCode.NAME_MISSING;
import static sample.Controllers.ErrorCode.NAME_TOO_LONG;
import static sample.Controllers.ErrorCode.NAMES_NOT_PAIRWISE_DISTINCT;

public class EnterName_Global {
    public static int MAX_NAME_LENGTH = 25;
    public static String blueLeaderName = "";
    public static String blueOperativeName = "";
    public static String redLeaderName = "";
    public static String redOperativeName = "";
    public static String hintString = "";
    public static int hintNumber = 0;
    public static boolean red = true;
    public static boolean operative = false;
    public static String currentError = "Everything seems to be fine. This shouldn't have happened.";
}
