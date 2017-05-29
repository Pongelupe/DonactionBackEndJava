
import java.sql.Connection;

public abstract class SQLConnection<T> {
	private String hostName;
	private String dbName;
	private String user;
	private String password;
	private String url;
	private Connection connection = null;

	SQLConnection(String hostName, String dbName, String user, String pwd) {
		setHostName(hostName);
		setDbName(dbName);
		setUser(user);
		setPassword(pwd);
		setUrl(String.format(
				"jdbc:sqlserver://%s.database.windows.net:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
				hostName, dbName, user, pwd));
	}
	
	SQLConnection() {
		this("donaction", "DonactionDB", "donaction@donaction", "goDonate!");
	}
	
	public String getHostName() {
		return hostName;
	}
	public String getDbName() {
		return dbName;
	}
	public String getUser() {
		return user;
	}
	public String getPassword() {
		return password;
	}
	public String getUrl() {
		return url;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
