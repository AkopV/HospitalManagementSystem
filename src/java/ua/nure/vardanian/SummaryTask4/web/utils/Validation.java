package ua.nure.vardanian.SummaryTask4.web.utils;

/**
 * Validation class contains common methods to validate data.
 *
 * @author Akop Vardanian
 *
 */
public class Validation {

    private static final String filledRegex = "^[а-яА-ЯёЁa-zA-Z][а-яА-ЯёЁa-zA-Z0-9-_\\.]{1,20}$";

    /**
     * Validate fields.
     *
     * @param values
     *            specified fields.
     * @return true if the validate success.
     */
    public static boolean isFilled (String... values) {
        for (String s : values) {
            System.out.println(s + s.matches(filledRegex));
            if (!s.matches(filledRegex)) {
                return false;
            }
        }
        return true;
    }
}