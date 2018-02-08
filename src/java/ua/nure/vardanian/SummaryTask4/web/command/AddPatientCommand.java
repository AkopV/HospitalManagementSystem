package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.entity.Patient;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.db.exception.DBException;
import ua.nure.vardanian.SummaryTask4.db.service.PatientService;
import ua.nure.vardanian.SummaryTask4.db.service.impl.PatientServiceImpl;
import ua.nure.vardanian.SummaryTask4.web.ActionType;
import ua.nure.vardanian.SummaryTask4.Path;
import ua.nure.vardanian.SummaryTask4.web.utils.PatientInputValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 * Add patient command.
 *
 * @author Akop Vardanian
 */
public class AddPatientCommand extends Command {

    private static final long serialVersionUID = -6488954337103977244L;

    private static final Logger LOG = Logger.getLogger(AddPatientCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");

        String result = null;

        if (ActionType.GET == actionType) {
            result = doGet(request, response);
        } else if (ActionType.POST == actionType) {
            result = doPost(request, response);
        }
        return result;
    }

    /**
     * Forwards to add patient form.
     *
     * @return path to the add patient page.
     */
    private String doGet(HttpServletRequest req, HttpServletResponse resp) {
        LOG.trace("Show addPatientForm.jsp");

        if (req.getParameter("error") != null) {
            String lang = (String) req.getSession().getAttribute("lang");
            String errorMessage = "";
            if (lang == null || lang.equals("en")) {
                errorMessage = "Incorrect input value or date, try again";
            } else if (lang.equals("ru")) {
                errorMessage = "Не правильныу входные данные или дата, попробуйте еще раз";
            }
            req.setAttribute("errorMessage", errorMessage);
        }
        return Path.FORWARD_PATIENT_ADD;
    }

    /**
     * Redirects user after submitting add patient form.
     *
     * @return path to the view of added patients if fields properly filled,
     * otherwise redisplay add patient page.
     */
    private String doPost(HttpServletRequest request, HttpServletResponse response) throws DBException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Date selectedDate = null;

        try {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date"));
            selectedDate = new Date(date.getTime());
        } catch (ParseException e) {
            LOG.trace("No patient with a such date birthday");
            e.printStackTrace();
        }

        boolean valid = PatientInputValidator.validatePatientParameters(firstName, lastName, selectedDate);

        if (valid) {
            LOG.trace("Obtain fields: " + firstName + ", " + lastName + ", " + selectedDate);

            Patient patient = new Patient(firstName, lastName, selectedDate);

            PatientService manager = new PatientServiceImpl();
            int hospitalCardId = manager.addHospitalCard();
            patient.setCardId(hospitalCardId);
            manager.addPatient(patient);
            LOG.trace("The patient added to database");
        } else {
            LOG.trace("Field did not properly fill");
            return Path.REDIRECT_TO_VIEW_ADD_PATIENT_FORM + "&error=notValid";
        }
        return Path.REDIRECT_TO_VIEW_ALL_PATIENTS;
    }
}