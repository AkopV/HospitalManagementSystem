package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.web.ActionType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * Download discharged patient data command.
 *
 * @author Akop Vardanian
 *
 */
public class DownloadFileCommand extends Command {

    private static final long serialVersionUID = -5888512148544025804L;

    private static final Logger LOG = Logger.getLogger(DownloadFileCommand.class);

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
     * Download specified file.
     *
     * @return
     * @throws IOException
     */
    private String doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.debug("DownloadFileCommand starts");

        String patientName = request.getParameter("firstName");
        String patientLastName = request.getParameter("lastName");
        LOG.trace("First name: " + patientName + ", Last name: " + patientLastName);

        // to obtain the bytes for unsafe characters
        String fileName = URLEncoder.encode(patientName + patientLastName + ".txt", "UTF-8");
        String filePath = patientName + patientLastName + ".txt";

        response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"" + fileName));
        File file = new File(filePath);

        // This should send the file to browser
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();

        LOG.debug("DownloadFileCommand finished");
        return null;
    }
}