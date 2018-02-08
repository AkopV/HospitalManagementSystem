package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.Path;
import ua.nure.vardanian.SummaryTask4.db.TypeOfTreatment;
import ua.nure.vardanian.SummaryTask4.db.entity.HospitalCard;
import ua.nure.vardanian.SummaryTask4.db.entity.Treatment;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.db.exception.DBException;
import ua.nure.vardanian.SummaryTask4.db.service.PatientService;
import ua.nure.vardanian.SummaryTask4.db.service.impl.PatientServiceImpl;
import ua.nure.vardanian.SummaryTask4.web.ActionType;
import ua.nure.vardanian.SummaryTask4.web.utils.HospitalCardInputValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Hospital card command.
 *
 * @author Akop Vardanian
 *
 */
public class HospitalCardCommand extends Command {

    private static final long serialVersionUID = 2829257657461264381L;

    private static final Logger LOG = Logger.getLogger(HospitalCardCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");

        String result = null;

        if (ActionType.GET == actionType) {
            result = doGet(request, response);
        } else if (ActionType.POST == actionType) {
            result = doPost(request, response);
        }

        LOG.debug("Command executed");

        return result;
    }

    /**
     * Forward to hospital card page.
     *
     * @return path to hospital card page.
     */
    private String doGet(HttpServletRequest request, HttpServletResponse response) throws DBException {
        PatientService manager = new PatientServiceImpl();
        int hospitalCardId = 0;
        if (request.getParameter("hospitalCardId") == null) {
            hospitalCardId = (int) request.getSession().getAttribute("hospitalCardId");
        } else {
            hospitalCardId = Integer.parseInt(request.getParameter("hospitalCardId"));
            request.getSession().setAttribute("hospitalCardId", hospitalCardId);
        }
        LOG.trace("Hospital card id: " + hospitalCardId);

        HospitalCard hospitalCard = manager.getHospitalCardById(hospitalCardId);
        LOG.trace("Found in db: hospital card --> " + hospitalCard);

        List<Treatment> treatments = manager.getTreatmentsByCardId(hospitalCardId);
        LOG.trace("Found in db: treatments --> " + treatments);

        // get type of treatments for add treatment form
        List<TypeOfTreatment> typeOfTreatments = manager.getTypesOfTreatment();
        LOG.trace("Found in db: type of treatments --> " + typeOfTreatments);

        request.setAttribute("hospitalCard", hospitalCard);
        request.setAttribute("treatments", treatments);
        request.getSession().setAttribute("typesOfTreatments", typeOfTreatments);

        if (request.getParameter("error") != null) {
            String lang = (String) request.getSession().getAttribute("lang");
            String errorMessage = "";
            if (lang == null || lang.equals("en")) {
                errorMessage = "Incorrect input value, try again";
            } else if (lang.equals("ru")) {
                errorMessage = "Не правильно указаны входные данные, попробуйте еще раз";
            }
            request.setAttribute("errorMessage", errorMessage);
        }

        return Path.FORWARD_HOSPITAL_CARD;
    }

    /**
     * Redirect to view current hospital card.
     *
     * @return path to view hospital card.
     */
    private String doPost(HttpServletRequest request, HttpServletResponse response) throws DBException {
        PatientService manager = new PatientServiceImpl();

        String diagnosis = request.getParameter("diagnosis");

        boolean valid = HospitalCardInputValidator.validateDiagnosis(diagnosis);
        if (!valid) {
            LOG.trace("Diagnosis is not valid");
            return Path.REDIRECT_TO_VIEW_HOSPITAL_CARD + "&error=notValidDiagnosis";
        }

        LOG.trace("Diagnosis: " + diagnosis);

        int hospitalCardId = (int) request.getSession().getAttribute("hospitalCardId");
        LOG.trace("Hospital card id: " + hospitalCardId);

        manager.updateDiagnosisInHospitalCard(hospitalCardId, diagnosis);
        LOG.trace("Diagnosis updated");

        return Path.REDIRECT_TO_VIEW_HOSPITAL_CARD;
    }
}