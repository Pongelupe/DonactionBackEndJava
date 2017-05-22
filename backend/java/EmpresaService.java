package sql;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import com.google.gson.Gson;

public class EmpresaService extends SQLMetodos<Empresa>{
	
	EmpresaService(String hostName, String dbName, String user, String pwd) {
		super(hostName, dbName, user, pwd);
	}
	
	public Boolean cadastrar(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Query query = request.getQuery();
		Boolean adicionado = true;
		String insertSql = 
				"INSERT INTO EMPRESA (NMEMPRESA, NRCNPJ, EMAILEMPRESA, SENHAEMPRESA, CIDADEEMPRESA) "
				+ "VALUES (?,?,?,?,?);";
		try (PreparedStatement prep = getConnection().prepareStatement(insertSql)) {
            prep.setString(1, query.get("nmEmpresa"));
            prep.setDouble(2, Double.parseDouble(query.get("nrCnpj")));
            prep.setString(3, query.get("emailEmpresa"));
            prep.setString(4, query.get("senhaEmpresa"));
            prep.setString(5, query.get("cidadeEmpresa"));
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
				"UPDATE EMPRESA "
				+ "EMAILEMPRESA = ?, CIDADEEMPRESA = ?"
				+ "WHERE CDEMPRESA = ?";
		try (PreparedStatement prep = getConnection().prepareStatement(updateSql)) {
            prep.setString(1, query.get("emailEmpresa"));
            prep.setString(2, query.get("cidadeEmpresa"));
            prep.setInt(3, request.getInteger("cdEmpresa"));
            int count = prep.executeUpdate();
            System.out.println("Editada: " + count + " linha(s)");
		} catch (Exception e) {
			atualizado = false;
			e.printStackTrace();
		}
		return atualizado;
	}

	public Boolean aderirCampanha(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Query query = request.getQuery();
		Boolean sucesso = true;
		String insertSql = 
				"INSERT INTO EMPRCAMPANHA (CDEMPRESA, CDCAMPANHA, PREFVOUCHER, DTINICIO) "
				+ "VALUES (?,?,?,?);";
		try (PreparedStatement prep = getConnection().prepareStatement(insertSql)) {
            prep.setInt(1, query.getInteger("cdEmpresa"));
            prep.setInt(2, query.getInteger("cdCampanha"));
            prep.setString(3, query.get("prefVoucher"));
            prep.setString(4, query.get("dtInicio"));
            int count = prep.executeUpdate();
            gerarVoucher(query.get("prefVoucher"), query.getInteger("qtdMinVoucher"));
            System.out.println("Inserida: " + count + " linha(s)");
		} catch (Exception e) {
			sucesso = false;
			e.printStackTrace();
		}
		return sucesso;
	}
	
	public String logar(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Gson gson = new Gson();
		Query query = request.getQuery();
		String email = query.get("emailEmpresa");
		String senha = query.get("senhaEmpresa");
		Empresa dadosDaEmpresa;
		String selectSql = 
				"SELECT * " 
                + "FROM EMPRESA "
				+ "WHERE EMAILEMPRESA = '" + email 
				+ "' AND SENHAEMPRESA = '" + senha + "'";
		try (Statement statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(selectSql)) {
			dadosDaEmpresa = objetoEmpresa(resultSet);
        } catch (Exception e) {
        	dadosDaEmpresa = null;
		}
		if (dadosDaEmpresa != null)
			return gson.toJson(dadosDaEmpresa, Empresa.class);
		return null;
	}
	
	public String historico(Request request) throws Exception {
		Collection<EmprCampanha> listaHistorico = new LinkedList<>();
		setConnection(DriverManager.getConnection(getUrl()));
		Gson gson = new Gson();
		Query query = request.getQuery();
		String cdEmpresa = query.get("cdEmpresa");
		String historicoEmpresa = null;
		String selectSql = querySelectHistorico(cdEmpresa);
		try (Statement statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(selectSql)) {
			listaHistorico = objetoHistorico(resultSet, listaHistorico);
        } catch (Exception e) {
        	e.printStackTrace();
		}
		if (!listaHistorico.isEmpty())
			historicoEmpresa = gson.toJson(listaHistorico);
		return historicoEmpresa;
	}

	public String campanhas(Request request) throws Exception {
		Collection<Campanha> listaCampanhas = new LinkedList<>();
		setConnection(DriverManager.getConnection(getUrl()));
		Gson gson = new Gson();
		Query query = request.getQuery();
		String cidadeEmpresa = query.get("cidadeEmpresa");
		String campanhas = null;
		String selectSql = querySelectCampanhas(cidadeEmpresa);
		try (Statement statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(selectSql)) {
			listaCampanhas = objetoCampanha(resultSet, listaCampanhas);
        } catch (Exception e) {
        	e.printStackTrace();
		}
		if (!listaCampanhas.isEmpty())
			campanhas = gson.toJson(listaCampanhas);
		return campanhas;
	}
	
	private String querySelectHistorico(String cdEmpresa) {
		return
			"SELECT C.NMCAMPANHA, "
			+ "C.DSCAMPANHA, "	
			+ "C.VLRINVESTIMENTO, "	
			+ "V.PREFVOUCHER, "
			+ "Count(CDVOUCHER) AS QTDVOUCHERS, " 
			+ "Count(CASE EHVALIDO WHEN '0' THEN 1 ELSE NULL END) AS VOUCHERSUSADOS, "
			+ "EC.DTINICIO, "
			+ "C.DTFIM "
			+ "FROM "
			+ "EMPRCAMPANHA EC "
			+ "INNER JOIN CAMPANHA C ON EC.CDCAMPANHA = C.CDCAMPANHA "
			+ "INNER JOIN VOUCHER V ON EC.PREFVOUCHER = V.PREFVOUCHER "
			+ "WHERE "
			+ "EC.CDEMPRESA = '" + cdEmpresa + "' "
			+ "GROUP BY C.NMCAMPANHA, C.DSCAMPANHA, C.VLRINVESTIMENTO, V.PREFVOUCHER, EC.DTINICIO, C.DTFIM";
	}
	private Collection<EmprCampanha> objetoHistorico(ResultSet resultSet, Collection<EmprCampanha> listaHistorico) throws Exception {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		while(resultSet.next()) {			
			String nmCampanha = resultSet.getString("NMCAMPANHA");
			String dsCampanha = resultSet.getString("DSCAMPANHA");
			String prefVoucher = resultSet.getString("PREFVOUCHER");
			String dtInicio = df.format(resultSet.getDate("DTINICIO"));
			String dtFim = df.format(resultSet.getDate("DTFIM"));
			Integer qtdVouchers = new Integer(resultSet.getInt("QTDVOUCHERS"));
			Integer vouchersUsados = new Integer(resultSet.getInt("VOUCHERSUSADOS"));
			BigInteger vlrInvestimento = new BigInteger(resultSet.getString("VLRINVESTIMENTO"));
			listaHistorico.add(new EmprCampanha(nmCampanha, dsCampanha, prefVoucher, dtInicio, dtFim, qtdVouchers, vouchersUsados, vlrInvestimento));
		}
		return listaHistorico;
	}
	
	private String querySelectCampanhas(String cidadeEmpresa) {
		return 
			"SELECT C.DSCAMPANHA, C.DTINICIO, C.DTFIM, C.CIDADECAMPANHA, C.NMCAMPANHA, C.VLRINVESTIMENTO, C.QTDMINVOUCHER "  
			+ "FROM CAMPANHA C "
			+ "INNER JOIN EMPRESA E ON C.CIDADECAMPANHA = E.CIDADEEMPRESA "
			+ "WHERE DTINICIO BETWEEN CONVERT (date, SYSDATETIME()) AND DTFIM " 
			+ "AND CIDADEEMPRESA = '" + cidadeEmpresa + "'";
	}	
	private Collection<Campanha> objetoCampanha(ResultSet resultSet, Collection<Campanha> listaCampanhas) throws Exception {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		while(resultSet.next()) {			
			String nmCampanha = resultSet.getString("NMCAMPANHA");
			String dsCampanha = resultSet.getString("DSCAMPANHA");
			String cidadeCampanha = resultSet.getString("CIDADECAMPANHA");
			String dtInicio = df.format(resultSet.getDate("DTINICIO"));
			String dtFim = df.format(resultSet.getDate("DTFIM"));
			Integer qtdMinVouchers = new Integer(resultSet.getInt("QTDMINVOUCHER"));
			BigInteger vlrInvestimento = new BigInteger(resultSet.getString("VLRINVESTIMENTO"));
			listaCampanhas.add(new Campanha(nmCampanha, dsCampanha, cidadeCampanha, dtInicio, dtFim, qtdMinVouchers, vlrInvestimento));
		}
		return listaCampanhas;
	}
	
	private Boolean gerarVoucher(String prefVoucher, Integer qtdMinVoucher) throws Exception {
		Collection<Voucher> listaVoucher = new ArrayList<>(qtdMinVoucher);
		SecureRandom random = new SecureRandom();
		for (int i = 0; i <= qtdMinVoucher; i++) {
			String suffix = new BigInteger(35, random).toString(32);
			String nmVoucher = qtdMinVoucher + suffix;
			listaVoucher.add(new Voucher(nmVoucher, prefVoucher, new Boolean(true)));
		}
		return cadastrarVoucher(listaVoucher);
	}
	private Boolean cadastrarVoucher(Collection<Voucher> listaVoucher) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Boolean adicionado = true;
		for(Voucher voucher : listaVoucher) {
			String nmVoucher = voucher.getNmVoucher();
			String prefVoucher = voucher.getPrefVoucher();
			Boolean ehValido = voucher.getEhValido();
			String insertSql = 
					"INSERT INTO VOUCHER (NMVOUCHER, PREFVOUCHER, EHVALIDO) "
					+ "VALUES (?,?,?);";
			try (PreparedStatement prep = getConnection().prepareStatement(insertSql)) {
	            prep.setString(1, nmVoucher);
	            prep.setString(2, prefVoucher);
	            prep.setBoolean(3, ehValido);
	            int count = prep.executeUpdate();
			} catch (Exception e) {
				adicionado = false;
				e.printStackTrace();
			}
		}
		return adicionado;
	}

	private Empresa objetoEmpresa(ResultSet resultSet) throws Exception {
		resultSet.next();
		Integer id = new Integer(resultSet.getInt("CDEMPRESA"));
		BigInteger cnpj = new BigInteger(resultSet.getString("NRCNPJ"));
		String nome = resultSet.getString("NMEMPRESA");
		String email = resultSet.getString("EMAILEMPRESA");
		String senha = resultSet.getString("SENHAEMPRESA");
		String cidade = resultSet.getString("CIDADEEMPRESA");
		return new Empresa(id, cnpj, nome, email, senha, cidade);
	}
}
