
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class Empresa extends Usuario {
	
	private BigInteger nrCnpj;

	public Empresa(Integer id, BigInteger cnpj, String nome, String email, String senha, String cidade) {
		super(id, nome, email, senha, cidade);
		setNrCnpj(cnpj);
	}
	
	public Empresa(BigInteger cnpj, String nome, String email, String senha, String cidade) {
		super(nome, email, senha, cidade);
		setNrCnpj(cnpj);
	}
	
	public Empresa() {
		
	}
	

	public Boolean cadastrar(Connection connection, BigInteger cnpj, String nome, String email, String senha, String cidade) throws Exception {
		Boolean adicionado = true;
		String insertSql = "INSERT INTO EMPRESA (NMEMPRESA, NRCNPJ, EMAILEMPRESA, SENHAEMPRESA, CIDADEEMPRESA) "
				+ "VALUES (?,?,?,?,?);";
		try (PreparedStatement prep = connection.prepareStatement(insertSql)) {
			prep.setString(1, nome);
			prep.setDouble(2, cnpj.doubleValue());
			prep.setString(3, email);
			prep.setString(4, senha);
			prep.setString(5, cidade);
			int count = prep.executeUpdate();
			System.out.println("Inserida: " + count + " linha(s)");
		} catch (Exception e) {
			adicionado = false;
			e.printStackTrace();
		}
		return adicionado;
	}

	public Boolean atualizarCadastro(Connection connection, String email, String cidade, Integer cdEmpresa) throws Exception {
		Boolean atualizado = true;
		String updateSql = "UPDATE EMPRESA " + "EMAILEMPRESA = ?, CIDADEEMPRESA = ?" + "WHERE CDEMPRESA = ?";
		try (PreparedStatement prep = connection.prepareStatement(updateSql)) {
			prep.setString(1, email);
			prep.setString(2, cidade);
			prep.setInt(3, cdEmpresa);
			int count = prep.executeUpdate();
			System.out.println("Editada: " + count + " linha(s)");
		} catch (Exception e) {
			atualizado = false;
			e.printStackTrace();
		}
		return atualizado;
	}
	
	public Empresa logar(Connection connection, String email, String senha) throws Exception {
		Empresa empresa;
		String selectSql = "SELECT * " + "FROM EMPRESA " + "WHERE EMAILEMPRESA = '" + email + "' AND SENHAEMPRESA = '"
				+ senha + "'";
		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(selectSql)) {
				empresa = objetoUsuario(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
			empresa = null;
		}
		return empresa;
	}
	private Empresa objetoUsuario(ResultSet resultSet) throws Exception {
		resultSet.next();
		Integer id = new Integer(resultSet.getInt("CDEMPRESA"));
		BigInteger cnpj = new BigInteger(resultSet.getString("NRCNPJ"));
		String nome = resultSet.getString("NMEMPRESA");
		String email = resultSet.getString("EMAILEMPRESA");
		String senha = resultSet.getString("SENHAEMPRESA");
		String cidade = resultSet.getString("CIDADEEMPRESA");
		return new Empresa(id, cnpj, nome, email, senha, cidade);
	}
	
	public Collection<EmprCampanha> historico(Connection connection, String cdEmpresa) throws Exception {
		String selectSql = querySelectHistorico(cdEmpresa);
		Collection<EmprCampanha> listaHistorico = new LinkedList<>();
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(selectSql)) {
					listaHistorico = objetoHistorico(resultSet, listaHistorico);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaHistorico;
	}
	private String querySelectHistorico(String cdEmpresa) {
		return "SELECT C.NMCAMPANHA, " + "C.DSCAMPANHA, " + "C.VLRINVESTIMENTO, " + "V.PREFVOUCHER, "
				+ "Count(CDVOUCHER) AS QTDVOUCHERS, "
				+ "Count(CASE EHVALIDO WHEN '0' THEN 1 ELSE NULL END) AS VOUCHERSUSADOS, " + "EC.DTINICIO, "
				+ "C.DTFIM " + "FROM " + "EMPRCAMPANHA EC " + "INNER JOIN CAMPANHA C ON EC.CDCAMPANHA = C.CDCAMPANHA "
				+ "INNER JOIN VOUCHER V ON EC.PREFVOUCHER = V.PREFVOUCHER " + "WHERE " + "EC.CDEMPRESA = '" + cdEmpresa
				+ "' " + "GROUP BY C.NMCAMPANHA, C.DSCAMPANHA, C.VLRINVESTIMENTO, V.PREFVOUCHER, EC.DTINICIO, C.DTFIM";
	}
	private Collection<EmprCampanha> objetoHistorico(ResultSet resultSet, Collection<EmprCampanha> listaHistorico)
			throws Exception {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		while (resultSet.next()) {
			String nmCampanha = resultSet.getString("NMCAMPANHA");
			String dsCampanha = resultSet.getString("DSCAMPANHA");
			String prefVoucher = resultSet.getString("PREFVOUCHER");
			String dtInicio = df.format(resultSet.getDate("DTINICIO"));
			String dtFim = df.format(resultSet.getDate("DTFIM"));
			Integer qtdVouchers = new Integer(resultSet.getInt("QTDVOUCHERS"));
			Integer vouchersUsados = new Integer(resultSet.getInt("VOUCHERSUSADOS"));
			BigInteger vlrInvestimento = new BigInteger(resultSet.getString("VLRINVESTIMENTO"));
			listaHistorico.add(new EmprCampanha(nmCampanha, dsCampanha, prefVoucher, dtInicio, dtFim, qtdVouchers,
					vouchersUsados, vlrInvestimento));
		}
		return listaHistorico;
	}
	
	public Boolean aderirCampanha(Connection connection, Integer cdEmpresa, Integer cdCampanha, String prefVoucher, String dtInicio, Integer qtdMinVoucher) throws Exception {
		Boolean sucesso = true;
		String insertSql = "INSERT INTO EMPRCAMPANHA (CDEMPRESA, CDCAMPANHA, PREFVOUCHER, DTINICIO) "
				+ "VALUES (?,?,?,?);";
		try (PreparedStatement prep = connection.prepareStatement(insertSql)) {
			prep.setInt(1, cdEmpresa);
			prep.setInt(2, cdCampanha);
			prep.setString(3, prefVoucher);
			prep.setString(4, dtInicio);
			prep.executeUpdate();
			gerarVoucher(connection, prefVoucher, qtdMinVoucher);
		} catch (Exception e) {
			sucesso = false;
			e.printStackTrace();
		}
		return sucesso;
	}
	private Boolean gerarVoucher(Connection connection, String prefVoucher, Integer qtdMinVoucher) throws Exception {
		Collection<Voucher> listaVoucher = new ArrayList<>(qtdMinVoucher);
		SecureRandom random = new SecureRandom();
		for (int i = 0; i <= qtdMinVoucher; i++) {
			String suffix = new BigInteger(35, random).toString(32).toUpperCase();
			String nmVoucher = prefVoucher + suffix;
			listaVoucher.add(new Voucher(nmVoucher, prefVoucher, new Boolean(true)));
		}
		return cadastrarVoucher(connection, listaVoucher);
	}
	private Boolean cadastrarVoucher(Connection connection, Collection<Voucher> listaVoucher) throws Exception {
		Boolean adicionado = true;
		for (Voucher voucher : listaVoucher) {
			String nmVoucher = voucher.getNmVoucher();
			String prefVoucher = voucher.getPrefVoucher();
			Boolean ehValido = voucher.getEhValido();
			String insertSql = "INSERT INTO VOUCHER (NMVOUCHER, PREFVOUCHER, EHVALIDO) " + "VALUES (?,?,?);";
			try (PreparedStatement prep = connection.prepareStatement(insertSql)) {
				prep.setString(1, nmVoucher);
				prep.setString(2, prefVoucher);
				prep.setBoolean(3, ehValido);
				prep.executeUpdate();
			} catch (Exception e) {
				adicionado = false;
				e.printStackTrace();
			}
		}
		return adicionado;
	}
	
	public Collection<Campanha> campanhas(Connection connection, String cidadeEmpresa) throws Exception {
		Collection<Campanha> listaCampanhas = new LinkedList<>();
		String selectSql = querySelectCampanhas(cidadeEmpresa);
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(selectSql)) {
			listaCampanhas = objetoCampanha(resultSet, listaCampanhas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!listaCampanhas.isEmpty())
			return listaCampanhas;
		return null;
	}
	private String querySelectCampanhas(String cidadeEmpresa) {
		return "SELECT C.DSCAMPANHA, C.DTINICIO, C.DTFIM, C.CIDADECAMPANHA, C.NMCAMPANHA, C.VLRINVESTIMENTO, C.QTDMINVOUCHER "
				+ "FROM CAMPANHA C " + "INNER JOIN EMPRESA E ON C.CIDADECAMPANHA = E.CIDADEEMPRESA "
				+ "WHERE DTINICIO BETWEEN CONVERT (date, SYSDATETIME()) AND DTFIM " + "AND CIDADEEMPRESA = '"
				+ cidadeEmpresa + "'";
	}
	private Collection<Campanha> objetoCampanha(ResultSet resultSet, Collection<Campanha> listaCampanhas)
			throws Exception {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		while (resultSet.next()) {
			String nmCampanha = resultSet.getString("NMCAMPANHA");
			String dsCampanha = resultSet.getString("DSCAMPANHA");
			String cidadeCampanha = resultSet.getString("CIDADECAMPANHA");
			String dtInicio = df.format(resultSet.getDate("DTINICIO"));
			String dtFim = df.format(resultSet.getDate("DTFIM"));
			Integer qtdMinVouchers = new Integer(resultSet.getInt("QTDMINVOUCHER"));
			BigInteger vlrInvestimento = new BigInteger(resultSet.getString("VLRINVESTIMENTO"));
			listaCampanhas.add(new Campanha(nmCampanha, dsCampanha, cidadeCampanha, dtInicio, dtFim, qtdMinVouchers,
					vlrInvestimento));
		}
		return listaCampanhas;
	}
	
	public BigInteger getNrCnpj() {
		return nrCnpj;
	}
	public void setNrCnpj(BigInteger nrCnpj) {
		this.nrCnpj = nrCnpj;
	}
	
	
}
