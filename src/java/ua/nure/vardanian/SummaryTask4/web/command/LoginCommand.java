package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.Role;
import ua.nure.vardanian.SummaryTask4.db.entity.User;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.db.exception.DBException;
import ua.nure.vardanian.SummaryTask4.db.service.UserService;
import ua.nure.vardanian.SummaryTask4.db.service.impl.UserServiceImpl;
import ua.nure.vardanian.SummaryTask4.web.ActionType;
import ua.nure.vardanian.SummaryTask4.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Login command.
 *
 * @author Akop Vardanian
 *
 */
public class LoginCommand extends Command {

    private static final long serialVersionUID = -4753703139582859573L;

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");

        String result = null;
        if (actionType == ActionType.POST) {
            result = doPost(request, response);
        } else {
            result = null;
        }

        LOG.debug("Command executed ");
        return result;
    }

    /**
     * User logins in system. As first page displays depends on the user role.
     *
     * @return path to the page.
     */
    private String doPost(HttpServletRequest request, HttpServletResponse response) throws DBException {
        String forward = null;

        HttpSession session = request.getSession();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UserService manager = new UserServiceImpl();
        User user = manager.getUserByLogin(login);
        LOG.trace("Found in DB: user --. " + user);

        if (user == null || !password.equals(user.getPassword())) {
            String lang = (String) request.getSession().getAttribute("lang");
            String errorMessage = "";
            if (lang == null || lang.equals("en")) {
                errorMessage = "Cannot find user with such login/password";
            } else if (lang.equals("uk")) {
                errorMessage = "Пользователь с таким логином/паролем не найден";
            }
            request.getSession().setAttribute("error", errorMessage);
            LOG.error("Cannot find user with such login/password");
        } else {
            Role userRole = Role.getRole(user);
            LOG.trace("Found in DB: userRole --> " + userRole);

            if (userRole == Role.ADMIN) {
                forward = Path.REDIRECT_TO_VIEW_ALL_DOCTORS;
            }

            if (userRole == Role.DOCTOR) {
                forward = Path.REDIRECT_TO_VIEW_PATIENTS_BY_DOCTOR_ID;
            }

            if (userRole == Role.NURSE) {
                forward = Path.REDIRECT_TO_VIEW_ALL_PATIENTS;
            }

            session.setAttribute("user", user);
            LOG.trace("Set the session attribute: user --> " + user);

            session.setAttribute("userRole", userRole);
            LOG.trace("Set the session attribute: userRole --> " + userRole);

            LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());
        }
        return forward;
    }
}