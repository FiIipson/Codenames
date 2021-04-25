package sample.Controllers;

import static sample.Controllers.ErrorCode.NAME_MISSING;
import static sample.Controllers.ErrorCode.NAME_TOO_LONG;
import static sample.Controllers.ErrorCode.NAMES_NOT_PAIRWISE_DISTINCT;

public class Global {
    public static int MAX_NAME_LENGTH = 25;
    public static String blueLeaderName = "";
    public static String blueOperativeName = "";
    public static String redLeaderName = "";
    public static String redOperativeName = "";
    public static String hintString = "";
    public static int hintNumber = 0;

    public static String errorMessage (ErrorCode e) {
        if (e == NAME_MISSING)
            return "Please enter all the names.";
        if (e == NAME_TOO_LONG)
            return  "The names cannot be longer than " + MAX_NAME_LENGTH + " characters.";
        if (e == NAMES_NOT_PAIRWISE_DISTINCT)
            return "At least two of the names are the same.";
        return "Everything seems to be fine. This shouldn't have happened.";
    }

    public static ErrorCode validate (String ... names) {
        //name missing check, name too long check
        for (String name : names) {
            if (name.equals("")) return NAME_MISSING;
            if (name.length() > MAX_NAME_LENGTH) return ErrorCode.NAME_TOO_LONG;
        }
        //pairwise distinct check
        for (int i = 0; i < names.length - 1; ++i) {
            for (int j = i + 1; j < names.length; ++j) {
                if (names[i].equals(names[j])) return ErrorCode.NAMES_NOT_PAIRWISE_DISTINCT;
            }
        }

        return ErrorCode.NAMES_OK;
    }
}
