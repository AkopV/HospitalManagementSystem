package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Holder for all commands.<br/>
 *
 * @author Akop Vardanian
 *
 */
public class CommandContainer {

    private static final Logger LOG = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<>();

    static {
        // common commands
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("language", new LanguageCommand());
        commands.put("noCommand", new NoCommand());

        // doctor commands
        commands.put("listPatientsByDoctorId", new ListPatientsByDoctorCommand());
        commands.put("hospitalCard", new HospitalCardCommand());
        commands.put("addTreatment", new AddTreatmentCommand());
        commands.put("completeTreatment", new PerformTreatmentCommand());
        commands.put("listDischargedPatients", new ListDischargedPatientsCommand());
        commands.put("completeCourseOfTreatment", new CompleteTheCourseOfTreatmentCommand());
        commands.put("downloadFile", new DownloadFileCommand());

        // admin commands
        commands.put("listDoctors", new ListDoctorsCommand());
        commands.put("listDoctorsBySpecialization", new ListDoctorsBySpecializationCommand());
        commands.put("addUser", new AddUserCommand());
        commands.put("addPatient", new AddPatientCommand());
        commands.put("appointDoctor", new AppointDoctorCommand());
        commands.put("listPatients", new ListPatientsCommand());

        LOG.debug("Command container was successfully initialized");
        LOG.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }
}