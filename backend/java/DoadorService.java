package sql;

import java.math.BigInteger;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;

import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import com.google.gson.Gson;

public class DoadorService extends SQLMetodos<Doador> {
	
	DoadorService(String hostName, String dbName, String user, String pwd) {
		super(hostName, dbName, user, pwd);
	}
	
	public Boolean cadastrar(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Query query = request.getQuery();
		Boolean adicionado = true;
		String insertSql = 
				"INSERT INTO DOADOR (NMDOADOR, NRCPF, EMAILDOADOR, SENHADOADOR, CIDADEDOADOR, TIPOSANGUINEO, PODEDOAR) "
				+ "VALUES (?,?,?,?,?,?,?);";
		try (PreparedStatement prep = getConnection().prepareStatement(insertSql)) {
            prep.setString(1, query.get("nmDoador"));
            prep.setDouble(2, Double.parseDouble(query.get("nrCpf")));
            prep.setString(3, query.get("emailDoador"));
            prep.setString(4, query.get("senhaDoador"));
            prep.setString(5, query.get("cidadeDoador"));
            prep.setString(6, query.get("tipoSanguineo"));
            prep.setString(7, query.get("podeDoar"));
            int count = prep.executeUpdate();
            System.out.println("Inserida: " + count + " linha(s)");
		} catch (Exception e) {
			adicionado = false;
			e.printStackTrace();
		}
		return adicionado;
	}

	public Boolean atualizarCadastro(Request request) throws Exception {
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
			e.printStackTrace();
		}
		return atualizado;
	}
	
	public String logar(Request request) throws Exception {
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
			dadosDoUsuario = objetoUsuario(resultSet);
        } catch (Exception e) {
        	dadosDoUsuario = null;
        	e.printStackTrace();
		}
		if (dadosDoUsuario != null)
			return gson.toJson(dadosDoUsuario, Doador.class);
		return null;
	}

	public String historico(Request request) throws Exception {
		Collection<DoadorVoucher> listaHistorico = new LinkedList<>();
		setConnection(DriverManager.getConnection(getUrl()));
		Gson gson = new Gson();
		Query query = request.getQuery();
		String cdDoador = query.get("cdDoador");
		String selectSql = querySelectHistorico(cdDoador);
		String historicoDoador = null;
		try (Statement statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(selectSql)) {
			listaHistorico = objetoHistorico(resultSet, listaHistorico);
        } catch (Exception e) {
        	e.printStackTrace();
		}
		if (!listaHistorico.isEmpty())
			historicoDoador = gson.toJson(listaHistorico);
		return historicoDoador;
	}
	
	private Doador objetoUsuario(ResultSet resultSet) throws Exception {
		resultSet.next();
		Integer id = new Integer(resultSet.getInt("CDDOADOR"));
		BigInteger nrCpf = new BigInteger(resultSet.getString("NRCPF"));
		String nome = resultSet.getString("NMDOADOR");
		String email = resultSet.getString("EMAILDOADOR");
		String senha = resultSet.getString("SENHADOADOR");
		String cidade = resultSet.getString("CIDADEDOADOR");
		String tipoSanguineo = resultSet.getString("TIPOSANGUINEO");
		Boolean podeDoar = resultSet.getBoolean("PODEDOAR");
		return new Doador(id, nrCpf, nome, email, senha, cidade, tipoSanguineo, podeDoar);
	}
	
	private Collection<DoadorVoucher> objetoHistorico(ResultSet resultSet, Collection<DoadorVoucher> listaHistorico) throws Exception {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		while(resultSet.next()) {
			String nmCampanha = resultSet.getString("NMCAMPANHA");
			String nmEmpresa = resultSet.getString("NMEMPRESA");
			String dtInicio = df.format(resultSet.getDate("DTINICIO"));
			String dtFim = df.format(resultSet.getDate("DTFIM"));
			listaHistorico.add(new DoadorVoucher(nmCampanha, nmEmpresa, dtInicio, dtFim));			
		}
		return listaHistorico;
	}
	
	public Boolean validarVoucher(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Query query = request.getQuery();
		Boolean ehValido = false;
		Integer cdDoador = query.getInteger("cdDoador");
		String nmVoucher = query.get("nmVoucher");
		String dtVoucher = query.get("dtVoucher");
		String selectSql = querySelectVoucher(nmVoucher, dtVoucher);
		try (Statement statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(selectSql)) {
			ehValido = (resultSet.next()) ? inserirDoadorVoucher(resultSet, cdDoador, dtVoucher) : ehValido;
        } catch (Exception e) {
        	e.printStackTrace();
		}
		return ehValido;
	}
	
	private String querySelectVoucher(String nmVoucher, String dtVoucher) {
		return
				"SELECT  V.CDVOUCHER, " 
				+ "V.NMVOUCHER, " 		
				+ "V.EHVALIDO, " 		
				+ "C.DTINICIO, " 		
		        + "C.DTFIM "			
		        + "FROM VOUCHER V "		
		        + "INNER JOIN EMPRCAMPANHA E ON V.PREFVOUCHER = E.PREFVOUCHER "
		        + "INNER JOIN CAMPANHA C ON E.CDCAMPANHA = C.CDCAMPANHA "		
		        + "WHERE NMVOUCHER = '" + nmVoucher + "' "							
		        + "AND '" + dtVoucher + "' " + "BETWEEN C.DTINICIO AND C.DTFIM";
	}
	
	private String querySelectHistorico(String cdDoador) {
		return
				"SELECT C.NMCAMPANHA, " 
				+ "E.NMEMPRESA, " 		
				+ "C.DTINICIO, " 				
		        + "C.DTFIM "			
		        + "FROM DOADOR D "
		        + "INNER JOIN DOADORVOUCHER DV ON D.CDDOADOR = DV.CDDOADOR "
		        + "INNER JOIN VOUCHER V ON DV.CDVOUCHER = V.CDVOUCHER "
		        + "INNER JOIN EMPRCAMPANHA EC ON V.PREFVOUCHER = EC.PREFVOUCHER "
		        + "INNER JOIN EMPRESA E ON EC.CDEMPRESA = E.CDEMPRESA "
		        + "INNER JOIN CAMPANHA C ON EC.CDCAMPANHA = C.CDCAMPANHA "		
		        + "WHERE D.CDDOADOR = '" + cdDoador + "' ";							
	}
	
	private Boolean inserirDoadorVoucher(ResultSet resultSet, Integer cdDoador, String dtVoucher) throws Exception {
		Integer cdVoucher = new Integer(resultSet.getInt("CDVOUCHER"));
		String insertSql = 
				"INSERT INTO DOADORVOUCHER (CDVOUCHER, CDDOADOR, DTRECEBIMENTO) "
				+ "VALUES (?,?,?);";
		try (PreparedStatement prep = getConnection().prepareStatement(insertSql)) {
            prep.setInt(1, cdVoucher);
            prep.setInt(2, cdDoador);
            prep.setDate(3, java.sql.Date.valueOf(dtVoucher));
            int count = prep.executeUpdate();
            System.out.println("Inserida: " + count + " linha(s)");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (atualizarValidadeVoucher(cdVoucher)) ? true : false;
	}
	
	private Boolean atualizarValidadeVoucher(Integer cdVoucher) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Boolean atualizado = true;
		String updateSql = 
				  "UPDATE VOUCHER "
				+ "SET EHVALIDO = 0 "
				+ "WHERE CDVOUCHER = ?";
		try (PreparedStatement prep = getConnection().prepareStatement(updateSql)) {
            prep.setInt(1, cdVoucher);
            int count = prep.executeUpdate();
            System.out.println("Editada: " + count + " linha(s)");
		} catch (Exception e) {
			atualizado = false;
			e.printStackTrace();
		}
		return atualizado;
	}
	
}
