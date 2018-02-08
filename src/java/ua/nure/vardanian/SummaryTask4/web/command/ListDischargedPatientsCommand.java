package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.entity.Patient;
import ua.nure.vardanian.SummaryTask4.db.entity.User;
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
import java.util.Collection;

/**
 * List discharged patients command.
 *
 * @author Akop Vardanian
 */
public class ListDischargedPatientsCommand extends Command {

    private static final long serialVersionUID = 3315634504142628538L;

    private static final Logger LOG = Logger.getLogger(ListDischargedPatientsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");

        String result = null;

        if (ActionType.GET == null) {
            result = doGet(request, response);
        }

        LOG.debug("Command executed");

        return doGet(request, response);
    }

    /**
     * Forward to discharged patients page.
     *
     * @return path to discharged patients page.
     */
    private String doGet(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        User doctor = (User) req.getSession().getAttribute("user");
        long doctorId = doctor.getId();
        LOG.trace("Doctor id: " + doctorId);

        // find discharged patients by current doctor id
        PatientService manager = new PatientServiceImpl();

        Collection<Patient> dischargedPatients = manager.getDischargedPatientsByDoctorId((int) doctorId);
        LOG.trace("Found in db: discharged patients --> " + dischargedPatients);

        req.setAttribute("patients", dischargedPatients);

        return Path.FORWARD_VIEW_DISCHARGED_PATIENTS;
    }
}