package ua.nure.vardanian.SummaryTask4.web;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.Path;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.web.command.Command;
import ua.nure.vardanian.SummaryTask4.web.command.CommandContainer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main servlet controller.
 *
 * @author Akop Vardanian
 *
 */
public class Controller extends HttpServlet {

    private static final long serialVersionUID = -8285837492767588942L;

    private static final Logger LOG = Logger.getLogger(Controller.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            process(request, response, ActionType.GET);
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            process(request, response, ActionType.POST);
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles all requests coming from the client by executing the specified
     * command name in a request. Implements PRG pattern by checking action type
     * specified by the invoked method.
     *
     * @param request
     * @param response
     * @param actionType
     * @throws IOException
     * @throws ServletException
     * @see ActionType
     */
    private void process(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
            throws IOException, ServletException, AppException {

        LOG.debug("Start processing in Controller");

        // extract command name from the request
        String commandName = request.getParameter("command");
        LOG.trace("Request parameter: 'command' = " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        LOG.trace("Obtained 'command' = " + command);

        // execute command and get forward address
        String path =  command.execute(request, response, actionType);

        if (path == null) {
            if (!commandName.equals("downloadFile")) {
                LOG.trace("Redirect to address = " + path);
                LOG.debug("Controller processing finished");
                response.sendRedirect(Path.PAGE_LOGIN);
            }
        } else {
            if (actionType == ActionType.GET) {
                LOG.trace("Forward to address = " + path);
                LOG.debug("Controller processing finished");
                RequestDispatcher disp = request.getRequestDispatcher(path);
                disp.forward(request, response);
            } else if (actionType == ActionType.POST) {
                LOG.trace("Redirect to address = " + path);
                LOG.debug("Controller processing finished");
                response.sendRedirect(path);
            }
        }
    }
}