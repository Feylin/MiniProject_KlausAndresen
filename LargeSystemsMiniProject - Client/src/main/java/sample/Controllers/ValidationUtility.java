package sample.Controllers;

/**
 * Helper class for handling input validation
 * Created by Administrator on 20-05-2015.
 */
public class ValidationUtility {
    /**
     * Validate user text input
     * @param source user input
     * @return false if input is invalid otherwise return true
     */
    public static boolean ValidateTextInput(String source) {
        return !(source == null || source.trim().length() == 0);
    }
}
