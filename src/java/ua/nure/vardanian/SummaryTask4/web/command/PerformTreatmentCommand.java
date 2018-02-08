package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.Path;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.db.exception.DBException;
import ua.nure.vardanian.SummaryTask4.db.service.PatientService;
import ua.nure.vardanian.SummaryTask4.db.service.impl.PatientServiceImpl;
import ua.nure.vardanian.SummaryTask4.web.ActionType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * To perform treatment for current patient command. Invokes when doctor or
 * nurse finished treatment.
 *
 * @author Akop Vardanian
 *
 */
public class PerformTreatmentCommand extends Command {

    private static final long serialVersionUID = -4281725510691179854L;

    private static final Logger LOG = Logger.getLogger(PerformTreatmentCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");

        String result = null;

        if (ActionType.POST == actionType) {
            result = doPost(request, response);
        }

        LOG.debug("Command executed");
        return result;
    }

    /**
     * Redirect to view hospital card current patient.
     *
     * @return path to view hospital card.
     */
    private String doPost(HttpServletRequest request, HttpServletResponse response) throws DBException {
        int treatmentId = Integer.parseInt(request.getParameter("id"));
        LOG.trace("Treatment id: " + treatmentId);

        PatientService manager = new PatientServiceImpl();
        manager.finishTreatment(treatmentId);

        return Path.REDIRECT_TO_VIEW_HOSPITAL_CARD;
    }
}