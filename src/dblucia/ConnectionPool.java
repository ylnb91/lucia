package dblucia;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public final class ConnectionPool {

	private static ConnectionPool instance;

	private ComboPooledDataSource ds;

	private ConnectionPool() throws Exception {
		DBConfig dbconfig = new DBConfig();
		ds = new ComboPooledDataSource();
		ds.setDriverClass(dbconfig.getDriverClass());
		ds.setJdbcUrl(dbconfig.getConnectionUrl());
		ds.setUser(dbconfig.getUsername());
		ds.setPassword(dbconfig.getPassword());
		ds.setInitialPoolSize(dbconfig.getInitialPoolSize());
		ds.setMaxPoolSize(dbconfig.getMaxPoolSize());
		ds.setMinPoolSize(dbconfig.getMinPoolSize());
		ds.setMaxStatements(dbconfig.getMaxStatements());
		ds.setAcquireIncrement(dbconfig.getAcquireIncrement());
		ds.setIdleConnectionTestPeriod(dbconfig.getIdleConnectionTestPeriod());
		ds.setMaxIdleTime(dbconfig.getMaxIdleTime());
		ds.setAutoCommitOnClose(dbconfig.getAutoCommitOnClose());
		ds.setPreferredTestQuery(dbconfig.getPreferredTestQuery());
		ds.setTestConnectionOnCheckout(dbconfig.getTestConnectionOnCheckout());
		ds.setTestConnectionOnCheckin(dbconfig.getTestConnectionOnCheckin());
		ds.setAcquireRetryAttempts(dbconfig.getAcquireRetryAttempts());
		ds.setAcquireRetryDelay(dbconfig.getAcquireRetryDelay());
		ds.setBreakAfterAcquireFailure(dbconfig.getBreakAfterAcquireFailure());
	}

	public static final ConnectionPool getInstance() {
		if (instance == null) {
			try {
				instance = new ConnectionPool();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public final Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void finalize() throws Throwable {
		DataSources.destroy(ds);
		super.finalize();
	}

}
