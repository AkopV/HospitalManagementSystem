package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.web.ActionType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LanguageCommand extends Command {

    private static final long serialVersionUID = 1033026785532522351L;

    private static final Logger LOG = Logger.getLogger(LanguageCommand.class);

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
     * Redirect to current url where language command has been called.
     *
     * @return path to current url.
     */
    private String doPost(HttpServletRequest request, HttpServletResponse response) {
        // action
        final String action = "controller?";

        // get parameters
        String url = request.getParameter("url");
        String lang = request.getParameter("language");
        LOG.trace("url: " + url + ", lang: " + lang);

        // set language
        request.getSession().setAttribute("lang", lang);
        LOG.trace("Language has been changed to " + lang);

        // if language changes on login page
        if (url.equals("command=logout") || url.equals("")) {
            return null;
        }

        return action + url;
    }
}
