package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.entity.Treatment;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.db.exception.DBException;
import ua.nure.vardanian.SummaryTask4.db.service.PatientService;
import ua.nure.vardanian.SummaryTask4.db.service.impl.PatientServiceImpl;
import ua.nure.vardanian.SummaryTask4.web.ActionType;
import ua.nure.vardanian.SummaryTask4.Path;
import ua.nure.vardanian.SummaryTask4.web.utils.HospitalCardInputValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Add treatment command.
 *
 * @author Akop Vardanian
 *
 */
public class AddTreatmentCommand extends Command {

    private static final long serialVersionUID = 3030648188960003099L;

    private static final Logger LOG = Logger.getLogger(AddTreatmentCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");

        String result = null;

        if (actionType == ActionType.POST) {
            result = doPost(request, response);
        } else {
            result = null;
        }

        LOG.debug("Command executed");
        return result;
    }

    /**
     * Redirect after submitting treatment add form.
     *
     * @return path to the view of patient hospital card if fields properly
     *         filled, otherwise redisplay the hospital card with error message.
     */
    private String doPost(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        int typeOfTreatmentId = Integer.parseInt(req.getParameter("typeOfTreatmentId"));
        int hospitalCardId = (int) req.getSession().getAttribute("hospitalCardId");
        String pills = req.getParameter("pills");
        LOG.trace("Fields in db: typeId --> " + typeOfTreatmentId +
                ", hospital card id --> " + hospitalCardId +
                ", pills --> " + pills);

        boolean valid = HospitalCardInputValidator.validateTreatmentParameters(pills);
        if (!valid) {
            LOG.trace("Invalid field");
            return Path.REDIRECT_TO_VIEW_HOSPITAL_CARD + "&error=notValidTreatment";
        }

        Treatment treatment = new Treatment(typeOfTreatmentId, hospitalCardId, pills);

        PatientService manager = new PatientServiceImpl();
        manager.addTreatment(treatment);
        LOG.trace("The treatment added to database");

        return Path.REDIRECT_TO_VIEW_HOSPITAL_CARD;
    }
}