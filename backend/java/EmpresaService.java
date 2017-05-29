
import java.math.BigInteger;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.LinkedList;

import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import com.google.gson.Gson;

public class EmpresaService extends SQLConnection<Empresa> {

	private Empresa empresa;
	
	EmpresaService(String hostName, String dbName, String user, String pwd) {
		super(hostName, dbName, user, pwd);
	}

	EmpresaService() {
		super();
	}

	public Boolean cadastrar(Request request) throws Exception {
		Query query = request.getQuery();
		BigInteger cnpj = new BigInteger(query.get("nrCnpj"));
		String nome = query.get("nmEmpresa");
		String email = query.get("emailEmpresa");
		String senha = query.get("senhaEmpresa");
		String cidade = query.get("cidadeEmpresa");
		setEmpresa(new Empresa(cnpj, nome, email, senha, cidade));
		setConnection(DriverManager.getConnection(getUrl()));
		return getEmpresa().cadastrar(getConnection(), cnpj, nome, email, senha, cidade);
	}

	public Boolean atualizarCadastro(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Query query = request.getQuery();
		String email = query.get("emailEmpresa");
		String cidade = query.get("cidadeEmpresa");
		Integer cdEmpresa = new Integer(query.get("cdEmpresa"));
		setEmpresa(new Empresa());
		return getEmpresa().atualizarCadastro(getConnection(), email, cidade, cdEmpresa);
	}

	public String logar(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		setEmpresa(new Empresa());
		Query query = request.getQuery();
		Gson gson = new Gson();
		String email = query.get("emailEmpresa");
		String senha = query.get("senhaEmpresa");
		Empresa dadosDaEmpresa = getEmpresa().logar(getConnection(), email, senha);	
		if (dadosDaEmpresa != null)
			return gson.toJson(dadosDaEmpresa, Empresa.class);
		return null;
	}
	
	public String historico(Request request) throws Exception {
		Collection<EmprCampanha> listaHistorico = new LinkedList<>();
		setEmpresa(new Empresa());
		setConnection(DriverManager.getConnection(getUrl()));
		Gson gson = new Gson();
		Query query = request.getQuery();
		String cdEmpresa = query.get("cdEmpresa");
		String historicoEmpresa = null;
		listaHistorico = getEmpresa().historico(getConnection(), cdEmpresa);
		if (!listaHistorico.isEmpty())
			historicoEmpresa = gson.toJson(listaHistorico);
		return historicoEmpresa;
	}
	
	public Boolean aderirCampanha(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Query query = request.getQuery();
		setEmpresa(new Empresa());
		String prefVoucher = query.get("prefVoucher");
		String dtInicio = query.get("dtInicio");
		Integer cdEmpresa = new Integer(query.getInteger("cdEmpresa"));
		Integer cdCampanha = new Integer(query.getInteger("cdCampanha"));
		Integer qtdMinVoucher = new Integer(query.getInteger("qtdMinVoucher"));
		return getEmpresa().aderirCampanha(getConnection(), cdEmpresa, cdCampanha, prefVoucher, dtInicio, qtdMinVoucher);			
	}
	
	public String campanhas(Request request) throws Exception {
		setConnection(DriverManager.getConnection(getUrl()));
		Gson gson = new Gson();
		Query query = request.getQuery();
		String cidadeEmpresa = query.get("cidadeEmpresa");
		String campanhas = null;
		setEmpresa(new Empresa());
		Collection<Campanha> listaCampanhas = getEmpresa().campanhas(getConnection(), cidadeEmpresa);
		if (!listaCampanhas.isEmpty())
			campanhas = gson.toJson(listaCampanhas);
		return campanhas;
	}

	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}
