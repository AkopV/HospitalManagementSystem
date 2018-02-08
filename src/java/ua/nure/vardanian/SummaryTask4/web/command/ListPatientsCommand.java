package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.entity.Patient;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.db.service.PatientService;
import ua.nure.vardanian.SummaryTask4.db.service.impl.PatientServiceImpl;
import ua.nure.vardanian.SummaryTask4.web.ActionType;
import ua.nure.vardanian.SummaryTask4.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * List of all patients in the hospital command.
 *
 * @author Akop Vardanian
 *
 */
public class ListPatientsCommand extends Command {

    private static final long serialVersionUID = -5027420562923431724L;

    private static final Logger LOG = Logger.getLogger(ListPatientsCommand.class);

    /**
     * Serializable comparator used with TreeMap container. When the servlet
     * container tries to serialize the session it may fail because the session
     * can contain TreeMap object with not serializable comparator.
     *
     * @author Akop Vardanian
     *
     */
    private static class CompareById implements Comparator<Patient>, Serializable {
        @Override
        public int compare(Patient o1, Patient o2) {
            if (o1.getId() > o2.getId()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static class CompareByPatientFirstName implements Comparator<Patient>, Serializable {
        @Override
        public int compare(Patient o1, Patient o2) {
            return o1.getFirstName().compareTo(o2.getFirstName());
        }
    }

    private static Comparator<Patient> compareById = new CompareById();

    private static Comparator<Patient> compareByPatientFirstName = new CompareByPatientFirstName();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");
        String result = null;
        if (ActionType.GET == actionType) {
            result = doGet(request, response);
        }
        LOG.debug("Command executed");
        return result;
    }

    /**
     * Forward to list of all patients page.
     *
     * @return path to page.
     */
    private String doGet(HttpServletRequest req, HttpServletResponse resp) {
        PatientService manager = new PatientServiceImpl();
        List<Patient> patients = null;
        patients = manager.getPatients();

        LOG.trace("Count of patients: " + patients.size());

        Collections.sort(patients, compareById);

        Collections.sort(patients, compareByPatientFirstName);

        req.setAttribute("patients", patients);

        return Path.FORWARD_VIEW_ALL_PATIENTS;
    }
}