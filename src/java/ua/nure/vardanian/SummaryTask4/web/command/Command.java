package ua.nure.vardanian.SummaryTask4.web.command;

import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.web.ActionType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Main interface for the Command pattern implementation.
 *
 * @author Akop Vardanian
 */
public abstract class Command implements Serializable {

    private static final long serialVersionUID = 9062922003224281868L;

    /**
     * Execution method for command.
     *
     * @return Address to go once the command is executed.
     */
    public abstract String execute(HttpServletRequest request,
                                   HttpServletResponse response, ActionType actionType) throws IOException, ServletException,
            AppException;

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

