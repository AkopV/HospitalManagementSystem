package ua.nure.vardanian.SummaryTask4;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.nure.vardanian.SummaryTask4.db.ConnectionPool;
import ua.nure.vardanian.SummaryTask4.db.entity.Patient;
import ua.nure.vardanian.SummaryTask4.db.entity.Treatment;
import ua.nure.vardanian.SummaryTask4.db.entity.User;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.db.exception.DBException;
import ua.nure.vardanian.SummaryTask4.db.service.PatientService;
import ua.nure.vardanian.SummaryTask4.db.service.UserService;
import ua.nure.vardanian.SummaryTask4.db.service.impl.PatientServiceImpl;
import ua.nure.vardanian.SummaryTask4.db.service.impl.UserServiceImpl;

import java.sql.Date;


public class DBManagerTest {

    private static final Logger LOG = Logger.getLogger(DBManagerTest.class);

    private static User testUser;

    private static int hospitalCardId;

    private static Patient testPatient;

    private static Treatment testTreatment;

    private UserService userService = new UserServiceImpl();

    private PatientService patientService = new PatientServiceImpl();

    public DBManagerTest() throws DBException {
    }


    @BeforeClass
    public static void beforeClass() {
        testUser = new User("testlogin", "testpassword", "testfirstname", "testlastname", 2, 0, 0);
        testPatient = new Patient("test", "test", new Date(2006, 2, 2));
        testTreatment = new Treatment(0l, 1, hospitalCardId, "test", false);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setURL("jdbc:mysql://localhost:3306/db_hospital?useEncoding=true&amp;characterEncoding=UTF-8");
            dataSource.setUser("root");
            dataSource.setPassword("mysql2017");
            new ConnectionPool(dataSource);
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot get DataSource without JNDI");
        }
    }

    @Test
    public void testGetDoctors() {
        Assert.assertEquals(userService.getDoctors().size() != 0, true);
    }

    @Test
    public void testGetDoctorsBySpecialization() throws DBException {
        Assert.assertNotNull(userService.getDoctorsBySpecialization(1));
    }

    @Test
    public void testGetRoles() {
        Assert.assertEquals(userService.getRoles().size() != 0, true);
    }

    @Test
    public void testGetSpecializations() {
        Assert.assertEquals(userService.getSpecializations().size() != 0, true);
    }

    @Test
    public void testAddUser() throws AppException {
        userService.addUser(testUser);
    }

    @Test
    public void testGetUserByLogin() {
        Assert.assertNotNull(userService.getUserByLogin("admin"));
    }

    @Test
    public void testGetUserById() {
        Assert.assertNotNull(userService.getUserById(1));
    }

    @Test
    public void testGetPatients() {
        Assert.assertNotNull(patientService.getPatients());
    }

    @Test
    public void testGetTypesOfTreatment() {
        Assert.assertEquals(patientService.getTypesOfTreatment().size() != 0, true);
    }

    @Test
    public void testGetPatientsByDoctorId() {
        Assert.assertNotNull(patientService.getPatientsByDoctorId(2));
    }

    @Test
    public void testAddPatientByHospitalCard() throws DBException {
        hospitalCardId = patientService.addHospitalCard();

        testPatient.setCardId(hospitalCardId);
        patientService.addPatient(testPatient);
    }

    @Test
    public void testSetDoctorToPatient() {
        patientService.setDoctorToPatient(0, 2);
    }

    @Test
    public void testUpdateDiagnosisInHospitalCard() {
        patientService.updateDiagnosisInHospitalCard(hospitalCardId, "testDiagnosis");
    }

    @Test
    public void testAddTreatmentAndGetTreatmentByHospitalCardId() throws DBException {
        testTreatment.setHospitalCardId(hospitalCardId);
        patientService.addTreatment(testTreatment);
        Assert.assertNotNull(patientService.getTreatmentsByCardId(hospitalCardId));
    }

    @Test
    public void testFinishTreatment() throws DBException {
        patientService.finishTreatment(testTreatment.getId());
    }

    @Test
    public void testGetDischargedPatientsByDoctorId() {
        Assert.assertTrue(patientService.getDischargedPatientsByDoctorId(2).size() > 0);
    }

    @Test
    public void testCompleteCourseOfTreatment() {
        patientService.completeTheCourseOfTreatment(testPatient);
    }

    @Test
    public void testGetHospitalCardById() {
        int id = 2;
        if (hospitalCardId != 0) {
            id = hospitalCardId;
        }
        Assert.assertNotNull(patientService.getHospitalCardById(id));
    }

    @Test
    public void testGetPatientByHospitalCardId() {
        int id = 2;
        if (hospitalCardId != 0) {
            id = hospitalCardId;
        }
        Assert.assertNotNull(patientService.getPatientByHospitalCardId(id));
    }
}