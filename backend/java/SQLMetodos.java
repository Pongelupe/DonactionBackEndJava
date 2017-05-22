package sql;
import java.sql.Connection;
import org.simpleframework.http.Request;

public abstract class SQLMetodos<T> {
	private String hostName;
	private String dbName;
	private String user;
	private String password;
	private String url;
	private Connection connection = null;

	SQLMetodos(String hostName, String dbName, String user, String pwd) {
		setHostName(hostName);
		setDbName(dbName);
		setUser(user);
		setPassword(pwd);
		setUrl(String.format(
				"jdbc:sqlserver://%s.database.windows.net:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
				hostName, dbName, user, pwd));
	}
	
	public abstract Boolean cadastrar(Request request) throws Exception;
	
	public abstract Boolean atualizarCadastro(Request request) throws Exception;
	
	public abstract String logar(Request request) throws Exception;
	
	public abstract String historico(Request request) throws Exception;
		
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
