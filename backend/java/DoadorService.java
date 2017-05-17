package sql;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;
import com.google.gson.Gson;

public class DoadorService extends SQLMetodos<Doador> {
	
	DoadorService(String hostName, String dbName, String user, String pwd) {
		super(hostName, dbName, user, pwd);
	}
	
	@Override
	public Boolean insert(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Query query = request.getQuery();
		Boolean adicionado = true;
		String insertSql = 
				"INSERT INTO DOADOR (NMDOADOR, NRCPF, EMAILDOADOR, SENHADOADOR, CIDADEDOADOR, TIPOSANGUINEO, PODEDOAR) "
				+ "VALUES (?,?,?,?,?,?,?);";
		try (PreparedStatement prep = getConnection().prepareStatement(insertSql)) {
            prep.setString(1, query.get("nmDoador"));
            prep.setInt(2, query.getInteger("nrCpf"));
            prep.setString(3, query.get("emailDoador"));
            prep.setString(4, query.get("senhaDoador"));
            prep.setString(5, query.get("cidadeDoador"));
            prep.setString(6, query.get("tipoSanguineo"));
            prep.setString(7, query.get("podeDoar"));
            int count = prep.executeUpdate();
            System.out.println("Inserida: " + count + " linha(s)");
		} catch (Exception e) {
			adicionado = false;
		}
		return adicionado;
	}

	@Override	
	public Boolean update(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Query query = request.getQuery();
		Boolean atualizado = true;
		String updateSql = 
				  "UPDATE DOADOR "
				+ "SET EMAILDOADOR = ?, CIDADEDOADOR = ? "
				+ "WHERE CDDOADOR = ?";
		try (PreparedStatement prep = getConnection().prepareStatement(updateSql)) {
            prep.setString(1, query.get("emailDoador"));
            prep.setString(2, query.get("cidadeDoador"));
            prep.setInt(3, query.getInteger("cdDoador"));
            int count = prep.executeUpdate();
            System.out.println("Editada: " + count + " linha(s)");
		} catch (Exception e) {
			atualizado = false;
		}
		return atualizado;
	}
	
	@Override
	public String select(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Gson gson = new Gson();
		Query query = request.getQuery();
		String email = query.get("emailDoador");
		String senha = query.get("senhaDoador");
		Doador dadosDoUsuario;
		String selectSql = "SELECT * " 
                + "FROM DOADOR "
				+ "WHERE EMAILDOADOR = '" + email 
				+ "' AND SENHADOADOR = '" + senha + "'";
		try (Statement statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(selectSql)) {
			dadosDoUsuario = createObject(resultSet);
        } catch (Exception e) {
        	dadosDoUsuario = null;
		}
		if (dadosDoUsuario != null)
			return gson.toJson(dadosDoUsuario, Doador.class);
		return null;
	}
	
	@Override
	public Doador createObject(ResultSet resultSet) throws Exception {
		resultSet.next();
		Integer id = new Integer(resultSet.getInt("CDDOADOR"));
		Integer nrCpf = new Integer(resultSet.getInt("NRCPF"));
		String nome = resultSet.getString("NMDOADOR");
		String email = resultSet.getString("EMAILDOADOR");
		String senha = resultSet.getString("SENHADOADOR");
		String cidade = resultSet.getString("CIDADEDOADOR");
		String tipoSanguineo = resultSet.getString("TIPOSANGUINEO");
		Boolean podeDoar = resultSet.getBoolean("PODEDOAR");
		return new Doador(id, nrCpf, nome, email, senha, cidade, tipoSanguineo, podeDoar);
	}
}
