package ua.nure.vardanian.SummaryTask4.web.utils;


import java.util.Date;

/**
 * Validator for add patient input.
 *
 * @author Akop Vardanian
 */
public class PatientInputValidator {

    /**
     * Validate patient input data.
     *
     * @param firstName specified first name.
     * @param lastName  specified last name.
     * @param birthday  specified birthday.
     * @return true if validation success.
     */
    public static boolean validatePatientParameters(String firstName, String lastName, Date birthday) {
        return (Validation.isFilled(firstName, lastName) && validateDate(birthday));
    }

    /**
     * Validate date.
     *
     * @param birthday specified date.
     * @return true if validation success.
     */
    private static boolean validateDate(Date birthday) {
        if (birthday == null) {
            return false;
        }

        if (birthday.after(new Date())) {
            return false;
        }
        return true;
    }
}