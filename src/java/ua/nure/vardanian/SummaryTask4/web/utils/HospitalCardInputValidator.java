package ua.nure.vardanian.SummaryTask4.web.utils;

import ua.nure.vardanian.SummaryTask4.web.utils.Validation;

/**
 * Validator for hospital card input.
 *
 * @author Akop Vardanian
 *
 */
public class HospitalCardInputValidator {

    /**
     * Validate diagnosis input.
     *
     * @param diagnosis
     *            specified diagnosis.
     * @return true if validation success.
     */
    public static boolean validateDiagnosis(String diagnosis) {
        return Validation.isFilled(diagnosis);
    }

    /**
     * Validate add treatment input.
     *
     * @param pills
     *            specified name.
     * @return true if validation success.
     */
    public static boolean validateTreatmentParameters(String pills) {
        return Validation.isFilled(pills);
    }
}