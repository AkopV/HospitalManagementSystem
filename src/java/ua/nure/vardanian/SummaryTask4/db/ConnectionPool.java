package ua.nure.vardanian.SummaryTask4.db;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.exception.DBException;
import ua.nure.vardanian.SummaryTask4.db.exception.Messages;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Returns a DB connection from the Pool Connections. Before using this
 * method you must configure the Date Source and the Connections Pool in
 * your WEB_APP_ROOT/META-INF/context.xml file.
 *
 * @return DB connection.
 *
 * @author Akop Vardanian
 */
public class ConnectionPool {
    private static DataSource dataSource;

    /**
     * Data source without JNDI.
     *
     * @param dataSource
     *            specified data source.
     */
    public ConnectionPool(DataSource dataSource) {
        ConnectionPool.dataSource = dataSource;
    }

    private final static Logger LOG = Logger.getLogger(ConnectionPool.class);

    /**
     * Get free connection from pool.
     *
     * @return connection.
     */
    public static synchronized Connection getConnection() throws DBException {
        if (dataSource == null) {
            try {
                Context initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:/comp/env");
                dataSource = (DataSource) envContext.lookup("jdbc/db_hospital");
            } catch (NamingException e) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
            }
        }

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
        }
    }
}
