package dblucia;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfig {
	Properties pro = new Properties();

	public DBConfig() {
		InputStream is = getClass().getResourceAsStream("/dbconfig.properties");
		try {
			pro.load(is);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public String getDriverClass() {
		return pro.getProperty("jdbc.connection.driver_class");
	}

	public String getUrl() {
		return pro.getProperty("jdbc.url");
	}
	
	public String getDatabase() {
		return pro.getProperty("jdbc.database");
	}

	public String getConnectionUrl() {
		return pro.getProperty("jdbc.url") + pro.getProperty("jdbc.database");
	}

	public String getUsername() {
		return pro.getProperty("jdbc.username");
	}

	public String getPassword() {
		return pro.getProperty("jdbc.password");
	}

	public int getInitialPoolSize() {
		return Integer.parseInt(pro.getProperty("jdbc.initialPoolSize"));
	}

	public int getMaxPoolSize() {
		return Integer.parseInt(pro.getProperty("jdbc.maxPoolSize"));
	}

	public int getMinPoolSize() {
		return Integer.parseInt(pro.getProperty("jdbc.minPoolSize"));
	}

	public int getMaxStatements() {
		return Integer.parseInt(pro.getProperty("jdbc.maxStatements"));
	}
	
	public int getAcquireIncrement() {
		return Integer.parseInt(pro.getProperty("jdbc.acquireIncrement"));
	}

	public int getIdleConnectionTestPeriod() {
		return Integer.parseInt(pro
				.getProperty("jdbc.idleConnectionTestPeriod"));
	}

	public int getMaxIdleTime() {
		return Integer.parseInt(pro.getProperty("jdbc.maxIdleTime"));
	}

	public boolean getAutoCommitOnClose() {
		return Boolean.parseBoolean(pro.getProperty("jdbc.autoCommitOnClose"));
	}

	public String getPreferredTestQuery() {
		return pro.getProperty("jdbc.preferredTestQuery");
	}

	public boolean getTestConnectionOnCheckout() {
		return Boolean.parseBoolean(pro
				.getProperty("jdbc.testConnectionOnCheckout"));
	}

	public boolean getTestConnectionOnCheckin() {
		return Boolean.parseBoolean(pro
				.getProperty("jdbc.testConnectionOnCheckin"));
	}

	public int getAcquireRetryAttempts() {
		return Integer.parseInt(pro.getProperty("jdbc.acquireRetryAttempts"));
	}

	public int getAcquireRetryDelay() {
		return Integer.parseInt(pro.getProperty("jdbc.acquireRetryDelay"));
	}

	public boolean getBreakAfterAcquireFailure() {
		return Boolean.parseBoolean(pro
				.getProperty("jdbc.breakAfterAcquireFailure"));
	}
}
