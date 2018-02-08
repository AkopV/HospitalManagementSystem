package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.Path;
import ua.nure.vardanian.SummaryTask4.db.TypeOfTreatment;
import ua.nure.vardanian.SummaryTask4.db.entity.HospitalCard;
import ua.nure.vardanian.SummaryTask4.db.entity.Patient;
import ua.nure.vardanian.SummaryTask4.db.entity.Treatment;
import ua.nure.vardanian.SummaryTask4.db.entity.User;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.db.exception.DBException;
import ua.nure.vardanian.SummaryTask4.db.service.PatientService;
import ua.nure.vardanian.SummaryTask4.db.service.UserService;
import ua.nure.vardanian.SummaryTask4.db.service.impl.PatientServiceImpl;
import ua.nure.vardanian.SummaryTask4.db.service.impl.UserServiceImpl;
import ua.nure.vardanian.SummaryTask4.web.ActionType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Complete the course of treatment command for specified patient.
 *
 * @author Akop Vardanian
 *
 */
public class CompleteTheCourseOfTreatmentCommand extends Command {

    private static final long serialVersionUID = -4997615009554946238L;

    private static final Logger LOG = Logger.getLogger(CompleteTheCourseOfTreatmentCommand.class);

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
     * Redirect to view discharged patients after completing course of treatment
     * for patient.
     *
     * @return path to view all discharged patients.
     */
    private String doPost(HttpServletRequest request, HttpServletResponse response) throws DBException {
        UserService manager = new UserServiceImpl();
        PatientService patientService = new PatientServiceImpl();

        // get current hospital card id from session
        int hospitalCardId = (int) request.getSession().getAttribute("hospitalCardId");
        LOG.trace("Hospital card id: " + hospitalCardId);

        // get all data about current patient
        HospitalCard hospitalCard = patientService.getHospitalCardById(hospitalCardId);
        List<Treatment> treatments = patientService.getTreatmentsByCardId(hospitalCardId);
        Patient patient = patientService.getPatientByHospitalCardId(hospitalCardId);
        LOG.trace("Found in db Patient: -->  id: " + patient.getId() + ", firstName: " + patient.getFirstName() + ", lastName: "
                + patient.getLastName() + ",date: " + patient.getBirthday() + ", cardId: " + patient.getCardId()
                + ", doctorID: " + patient.getDoctorId());
        User doctor = manager.getUserById(patient.getDoctorId());

        // write data to file
        try {
            PrintWriter pw = new PrintWriter(patient.getFirstName()
                    + patient.getLastName() + ".txt", "UTF-8");

            pw.write("FirstName: " + patient.getFirstName() + "\r\n");
            pw.write("LastName: " + patient.getLastName() + "\r\n");
            pw.write("Diagmosis: " + hospitalCard.getDiagnosis() + "\r\n");
            pw.write("Doctor: " + doctor.getFirstName() + " " + doctor.getLastName() + "\r\n");
            pw.write("Treatments:\r\n");

            // write all history of treatment
            for (Treatment t : treatments) {
                pw.write(" TypeOfTreatment: " + TypeOfTreatment.getTypeOfTreatment(t) + " | Name: "
                        + t.getPills() + " | isDone: " + t.isDone() + "\r\n");
            }
            pw.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            LOG.error("Cannot write data to file");
        }

        // complete course of treatment
        patientService.completeTheCourseOfTreatment(patient);

        return Path.REDIRECT_TO_VIEW_DISCHARGED_PATIENTS;
    }
}