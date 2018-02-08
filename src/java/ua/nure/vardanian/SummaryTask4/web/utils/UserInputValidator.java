package ua.nure.vardanian.SummaryTask4.web.utils;

/**
 * User input validator.
 *
 * @author Akop Vardanian
 *
 */
public class UserInputValidator {
    /**
     * Validate user input data.
     *
     * @param login
     *            specified login
     * @param password
     *            specified password
     * @param firstName
     *            specified first name
     * @param lastName
     *            specified last name.
     * @return true if validation success.
     */
    public static boolean validateUserParameters(String login, String password, String firstName, String lastName) {
        return Validation.isFilled(login, password, firstName, lastName);
    }
}