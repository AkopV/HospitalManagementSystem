package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.Role;
import ua.nure.vardanian.SummaryTask4.db.Specialization;
import ua.nure.vardanian.SummaryTask4.db.entity.User;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.db.exception.DBException;
import ua.nure.vardanian.SummaryTask4.db.service.UserService;
import ua.nure.vardanian.SummaryTask4.db.service.impl.UserServiceImpl;
import ua.nure.vardanian.SummaryTask4.web.ActionType;
import ua.nure.vardanian.SummaryTask4.Path;
import ua.nure.vardanian.SummaryTask4.web.utils.UserInputValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Add user command.
 *
 * @author Akop Vardanian
 */
public class AddUserCommand extends Command {

    private static final long serialVersionUID = 7193929052776473304L;

    private static final Logger LOG = Logger.getLogger(AddUserCommand.class);

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
     * Forwards to add user page.
     *
     * @return path to the add user page.
     */
    private String doGet(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        UserService manager = new UserServiceImpl();

        // get all roles for form
        List<Role> roles = manager.getRoles();
        LOG.trace("Found in db roles: --> " + roles);

        // get all specialization for form
        List<Specialization> specializations = manager.getSpecializations();
        LOG.trace("Found in db specializations: --> " + specializations);

        // set roles and specializations as attribute
        req.setAttribute("roles", roles);
        req.setAttribute("specializations", specializations);
        // error message if fields did not fill properly
        if (req.getParameter("error") != null) {
            String error = req.getParameter("error");
            String lang = (String) req.getSession().getAttribute("lang");
            if (lang == null || lang.equals("en")) {
                if (error.equals("dublicateLogin")) {
                    req.setAttribute("errorMessage", "This login is already in use");
                }
                if (error.equals("notValid")) {
                    req.setAttribute("errorMessage", "Incorrect input, try again");
                }
            } else if (lang.equals("ru")) {
                if (error.equals("dublicateLogin")) {
                    req.setAttribute("errorMessage", "Этот логин уже активирован");
                }
                if (error.equals("notValid")) {
                    req.setAttribute("errorMessage", "Не правильный ввод");
                }
            }
        }
        return Path.FORWARD_USER_ADD;
    }

    /**
     * Redirects to view all users after submitting add user form.
     *
     * @return path to the view of added user if fields properly filled,
     * otherwise redisplay add user page.
     * @throws IOException
     * @throws ServletException
     */
    private String doPost(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firsName");
        String lastName = request.getParameter("lastName");
        int roleId = 0;
        if (request.getParameter("roleId") != null) {
            roleId = Integer.parseInt(request.getParameter("roleId"));
        }
        int specializationId = 0;
        int countOfPatients = 0;
        if (request.getParameter("specializationId") != null) {
            specializationId = Integer.parseInt(request.getParameter("specializationId"));
        }
        LOG.trace("The fields got: " + login + " " + password + " " + firstName + " " + lastName + " " + roleId + " "
                + specializationId);

        // validation
        boolean valid = UserInputValidator.validateUserParameters(login, password, firstName, lastName);
        LOG.trace("Validation: " + valid);

        if (!valid) {
            return Path.REDIRECT_TO_VIEW_ADD_USER_FORM + "&error=notValid";
        }

        // create user entity
        User user = new User(
                login,
                password,
                firstName,
                lastName,
                roleId,
                specializationId,
                countOfPatients
        );

        LOG.trace("User created: " + user);

        // add user to database
        UserService manager = new UserServiceImpl();
        manager.addUser(user);

        LOG.trace("User added in database");

        return Path.REDIRECT_TO_VIEW_ALL_DOCTORS;
    }
}