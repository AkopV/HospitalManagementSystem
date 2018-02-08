package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.Path;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.web.ActionType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Invokes when user want to logout.
 *
 * @author Akop Vardanian
 *
 */
public class LogoutCommand extends Command {

    private static final long serialVersionUID = 1080754278307930094L;

    private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

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
     * Forward to login page after logout.
     *
     * @return path to login page
     */
    private String doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return Path.PAGE_LOGIN;
    }
}
