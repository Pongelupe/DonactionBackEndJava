
import java.math.BigInteger;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.LinkedList;

import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import com.google.gson.Gson;

public class DoadorService extends SQLConnection<Doador> {
	
	private Doador doador;
	
	DoadorService(String hostName, String dbName, String user, String pwd) {
		super(hostName, dbName, user, pwd);
	}
	
	DoadorService() {
		super();
	}
	
	public Boolean cadastrar(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Query query = request.getQuery();
        String nome = query.get("nmDoador");
        BigInteger cpf = new BigInteger(query.get("nrCpf"));
        String email = query.get("emailDoador");
        String senha = query.get("senhaDoador");
        String cidade = query.get("cidadeDoador");
        String tipoSanguineo = query.get("tipoSanguineo");
        Boolean podeDoar = new Boolean(query.get("podeDoar"));
        setDoador(new Doador());
		return getDoador().cadastrar(getConnection(), cpf, nome, email, senha, cidade, tipoSanguineo, podeDoar);
	}

	public Boolean atualizarCadastro(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Query query = request.getQuery();
		String email = query.get("emailDoador");
		String cidade = query.get("cidadeDoador");
		Integer cdDoador = new Integer(query.get("cdDoador"));
		setDoador(new Doador());
		return getDoador().atualizarCadastro(getConnection(), email, cidade, cdDoador);
	}
	
	public String logar(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Query query = request.getQuery();
		Gson gson = new Gson();
		String email = query.get("emailDoador");
		String senha = query.get("senhaDoador");
		setDoador(new Doador());
		Doador dadosDoDoador = getDoador().logar(getConnection(), email, senha);
		if (dadosDoDoador != null)
			return gson.toJson(dadosDoDoador, Doador.class);
		return null;
	}
	

	public String historico(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Gson gson = new Gson();
		Query query = request.getQuery();
		String cdDoador = query.get("cdDoador");
		String historicoDoador = null;
		setDoador(new Doador());
		Collection<DoadorVoucher> listaHistorico = new LinkedList<>();
		listaHistorico = getDoador().historico(getConnection(), cdDoador);
		if (!listaHistorico.isEmpty())
			historicoDoador = gson.toJson(listaHistorico);
		return historicoDoador;
	}
	
	
	public Boolean validarVoucher(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Query query = request.getQuery();
		Integer cdDoador = query.getInteger("cdDoador");
		String nmVoucher = query.get("nmVoucher");
		String dtVoucher = query.get("dtVoucher");
		setDoador(new Doador());
		return getDoador().validarVoucher(getConnection(), cdDoador, nmVoucher, dtVoucher);
	}
	
	public Doador getDoador() {
		return doador;
	}
	public void setDoador(Doador doador) {
		this.doador = doador;
	}
	
}
