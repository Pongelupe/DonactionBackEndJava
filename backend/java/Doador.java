
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;

public class Doador extends Usuario {
	
	private BigInteger nrCpf;
	private String tipoSanguineo;
	private Boolean podeDoar;
	
	public Doador(Integer id, BigInteger cpf, String nome, String email, String senha, String cidade, String tipoSanguineo, Boolean podeDoar) {
		super(id, nome, email, senha, cidade);
		setNrCpf(cpf);
		setTipoSanguineo(tipoSanguineo);
		setPodeDoar(podeDoar);
	}
	
	public Doador(BigInteger cpf, String nome, String email, String senha, String cidade, String tipoSanguineo, Boolean podeDoar) {
		super(nome, email, senha, cidade);
		setNrCpf(cpf);
		setTipoSanguineo(tipoSanguineo);
		setPodeDoar(podeDoar);
	}
	
	public Doador() {
		
	}
	
	public Boolean cadastrar(Connection connection, BigInteger cpf, String nome, String email, String senha, String cidade, String tipoSanguineo, Boolean podeDoar) 
			throws Exception {
		Boolean adicionado = true;
		String insertSql = 
				"INSERT INTO DOADOR (NMDOADOR, NRCPF, EMAILDOADOR, SENHADOADOR, CIDADEDOADOR, TIPOSANGUINEO, PODEDOAR) "
				+ "VALUES (?,?,?,?,?,?,?);";
		try (PreparedStatement prep = connection.prepareStatement(insertSql)) {
            prep.setString(1, nome);
            prep.setDouble(2, cpf.doubleValue());
            prep.setString(3, email);
            prep.setString(4, senha);
            prep.setString(5, cidade);
            prep.setString(6, tipoSanguineo);
            prep.setBoolean(7, podeDoar);
            int count = prep.executeUpdate();
            System.out.println("Inserida: " + count + " linha(s)");
		} catch (Exception e) {
			adicionado = false;
			e.printStackTrace();
		}
		return adicionado;
	}

	public Boolean atualizarCadastro(Connection connection, String email, String cidade, Integer cdDoador) throws Exception {
		Boolean atualizado = true;
		String updateSql = "UPDATE DOADOR " + "EMAILDOADOR = ?, CIDADEDOADOR = ?" + "WHERE CDDOADOR = ?";
		try (PreparedStatement prep = connection.prepareStatement(updateSql)) {
			prep.setString(1, email);
			prep.setString(2, cidade);
			prep.setInt(3, cdDoador);
			int count = prep.executeUpdate();
			System.out.println("Editada: " + count + " linha(s)");
		} catch (Exception e) {
			atualizado = false;
			e.printStackTrace();
		}
		return atualizado;
	}

	public Doador logar(Connection connection, String email, String senha) throws Exception {
		Doador doador;
		String selectSql = "SELECT * " + "FROM DOADOR " + "WHERE EMAILDOADOR = '" + email + "' AND SENHADOADOR = '"
				+ senha + "'";
		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(selectSql)) {
				doador = objetoUsuario(resultSet);				
		} catch (Exception e) {
			e.printStackTrace();
			doador = null;
		}
		return doador;
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

	public Collection<DoadorVoucher> historico(Connection connection, String cdDoador) throws Exception {
		Collection<DoadorVoucher> listaHistorico = new LinkedList<>();
		String selectSql = querySelectHistorico(cdDoador);
		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(selectSql)) {
				listaHistorico = objetoHistorico(resultSet, listaHistorico);
        } catch (Exception e) {
        	e.printStackTrace();
		}
		return listaHistorico;
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

	public Boolean validarVoucher(Connection connection, Integer cdDoador, String nmVoucher, String dtVoucher) throws Exception {
		Boolean ehValido = false;
		String selectSql = querySelectVoucher(nmVoucher, dtVoucher);
		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(selectSql)) {
			ehValido = (resultSet.next()) ? inserirDoadorVoucher(connection, resultSet, cdDoador, dtVoucher) : ehValido;
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
	private Boolean inserirDoadorVoucher(Connection connection, ResultSet resultSet, Integer cdDoador, String dtVoucher) throws Exception {
		Integer cdVoucher = new Integer(resultSet.getInt("CDVOUCHER"));
		String insertSql = 
				"INSERT INTO DOADORVOUCHER (CDVOUCHER, CDDOADOR, DTRECEBIMENTO) "
				+ "VALUES (?,?,?);";
		try (PreparedStatement prep = connection.prepareStatement(insertSql)) {
            prep.setInt(1, cdVoucher);
            prep.setInt(2, cdDoador);
            prep.setDate(3, java.sql.Date.valueOf(dtVoucher));
            int count = prep.executeUpdate();
            System.out.println("Inserida: " + count + " linha(s)");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (atualizarValidadeVoucher(connection, cdVoucher)) ? true : false;
	}
	private Boolean atualizarValidadeVoucher(Connection connection, Integer cdVoucher) throws Exception {
		Boolean atualizado = true;
		String updateSql = 
				  "UPDATE VOUCHER "
				+ "SET EHVALIDO = 0 "
				+ "WHERE CDVOUCHER = ?";
		try (PreparedStatement prep = connection.prepareStatement(updateSql)) {
            prep.setInt(1, cdVoucher);
            int count = prep.executeUpdate();
            System.out.println("Editada: " + count + " linha(s)");
		} catch (Exception e) {
			atualizado = false;
			e.printStackTrace();
		}
		return atualizado;
	}
	
	public BigInteger getNrCpf() {
		return nrCpf;
	}
	public String getTipoSanguineo() {
		return tipoSanguineo;
	}
	public Boolean getPodeDoar() {
		return podeDoar;
	}
	public void setNrCpf(BigInteger nrCpf) {
		this.nrCpf = nrCpf;
	}
	public void setTipoSanguineo(String tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}
	public void setPodeDoar(Boolean podeDoar) {
		this.podeDoar = podeDoar;
	}
}
