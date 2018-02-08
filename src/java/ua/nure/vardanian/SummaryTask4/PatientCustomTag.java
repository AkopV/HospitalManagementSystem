package ua.nure.vardanian.SummaryTask4;

import ua.nure.vardanian.SummaryTask4.db.entity.Patient;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class PatientCustomTag extends SimpleTagSupport {

    private Patient patient;

    public Patient getPatient() { return patient; }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.println("<td>" + patient.getFirstName() + "</td>");
        out.println("<td>" + patient.getLastName() + "</td>");
        out.println("<td>" + patient.getBirthday() + "</td>");
    }
}