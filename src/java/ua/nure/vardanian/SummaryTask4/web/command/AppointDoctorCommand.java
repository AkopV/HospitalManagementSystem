package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.db.exception.DBException;
import ua.nure.vardanian.SummaryTask4.db.service.PatientService;
import ua.nure.vardanian.SummaryTask4.db.service.impl.PatientServiceImpl;
import ua.nure.vardanian.SummaryTask4.web.ActionType;
import ua.nure.vardanian.SummaryTask4.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Appoint doctor to patient command.
 *
 * @author Akop Vardanian
 *
 */
public class AppointDoctorCommand extends Command {

    private static final long serialVersionUID = 5425149288189344647L;

    private static final Logger LOG = Logger.getLogger(AppointDoctorCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) throws IOException, ServletException, AppException {
        LOG.debug("Command started");
        String result = null;

        if (ActionType.POST == actionType) {
            result = doPost(request, response);
        }
        LOG.debug("Command executed");

        return result;
    }

    /**
     * Redirect to view patients after submitting specified doctor to the
     * patient.
     *
     * @return path to view patients list.
     */
    private String doPost(HttpServletRequest request, HttpServletResponse response) throws DBException {
        // get doctor and patient id
        int patientId = Integer.parseInt(request.getParameter("patientId"));
        int doctorId = Integer.parseInt(request.getParameter("doctorId"));
        LOG.trace("Patient id: " + patientId + " Doctor id: " + doctorId);

        LOG.trace("Trying appoint doctor to the patient");
        PatientService manager = new PatientServiceImpl();
        manager.setDoctorToPatient(patientId, doctorId);
        LOG.trace("The doctor appointed to the patient");

        return Path.REDIRECT_TO_VIEW_ALL_PATIENTS;
    }
}