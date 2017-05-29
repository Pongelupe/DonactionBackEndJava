
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.json.JSONException;
import org.json.JSONObject;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerSocketProcessor;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class URLMetodo implements Container {

	public static DoadorService dS;
	public static EmpresaService eS;
	
	@Override
	public void handle(Request request, Response response) {
		try {
			String path = request.getPath().getPath();
			String method = request.getMethod();
			
			if (path.startsWith("/cadastrarDoador") && "POST".equals(method)) 
				cadastrarDoador(request, response);
			
			else if (path.startsWith("/cadastrarEmpresa") && "POST".equals(method))
				cadastrarEmpresa(request, response);
			
			else if (path.startsWith("/logarDoador") && "POST".equals(method))
				logarDoador(request, response);
	
			else if (path.startsWith("/logarEmpresa") && "POST".equals(method))
				logarEmpresa(request, response);
				
			else if (path.startsWith("/atualizarCadastroDoador") && "POST".equals(method))
				atualizarCadastroDoador(request, response);
			
			else if (path.startsWith("/atualizarCadastroEmpresa") && "POST".equals(method))
				atualizarCadastroEmpresa(request, response);
				
			else if (path.startsWith("/validarVoucher") && "POST".equals(method))
				validarVoucher(request, response);
				
			else if (path.startsWith("/historicoDoador") && "POST".equals(method))
				historicoDoador(request, response);

			else if (path.startsWith("/historicoEmpresa") && "POST".equals(method))
				historicoEmpresa(request, response);
			
			else if (path.startsWith("/campanhasDisponiveis") && "POST".equals(method))
				campanhasDisponiveis(request, response);
			
			else if (path.startsWith("/aderirCampanha") && "POST".equals(method))
				aderirCampanha(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cadastrarDoador(Request request, Response response) throws Exception {
		if (dS.cadastrar(request))
			this.enviaResposta(Status.CREATED, response);
		else
			this.enviaResposta(Status.EXPECTATION_FAILED, response);
	}
	private void cadastrarEmpresa(Request request, Response response) throws Exception {
		if (eS.cadastrar(request))
			this.enviaResposta(Status.CREATED, response);
		else
			this.enviaResposta(Status.EXPECTATION_FAILED, response);
	}
	private void logarDoador(Request request, Response response) throws Exception {
		String dadosDoUsuario = dS.logar(request);
		if (dadosDoUsuario != null)
			this.enviaResposta(Status.OK, response, toJSON(dadosDoUsuario));
		else
			this.enviaResposta(Status.EXPECTATION_FAILED, response);
	}
	private void logarEmpresa(Request request, Response response) throws Exception {
		String dadosDaEmpresa = eS.logar(request);
		if (dadosDaEmpresa != null)
			this.enviaResposta(Status.OK, response, toJSON(dadosDaEmpresa));
		else
			this.enviaResposta(Status.EXPECTATION_FAILED, response);
	}
	private void atualizarCadastroDoador(Request request, Response response) throws Exception {
		if (dS.atualizarCadastro(request))
			this.enviaResposta(Status.OK, response);
		else
			this.enviaResposta(Status.EXPECTATION_FAILED, response);
	}
	private void atualizarCadastroEmpresa(Request request, Response response) throws Exception {
		if (eS.atualizarCadastro(request))
			this.enviaResposta(Status.OK, response);
		else
			this.enviaResposta(Status.EXPECTATION_FAILED, response);
	}
	private void validarVoucher(Request request, Response response) throws Exception {
		if (dS.validarVoucher(request))
			this.enviaResposta(Status.OK, response);
		else
			this.enviaResposta(Status.EXPECTATION_FAILED, response);
	}
	private void historicoDoador(Request request, Response response) throws Exception {
		String historicoUsuario = dS.historico(request);
		if (historicoUsuario != null)
			this.enviaResposta(Status.OK, response, historicoUsuario);
		else
			this.enviaResposta(Status.EXPECTATION_FAILED, response);
	}
	private void historicoEmpresa(Request request, Response response) throws Exception {
		String historicoEmpresa = eS.historico(request);
		if (historicoEmpresa != null)
			this.enviaResposta(Status.OK, response, historicoEmpresa);
		else
			this.enviaResposta(Status.EXPECTATION_FAILED, response);
	}
	private void campanhasDisponiveis(Request request, Response response) throws Exception {
		String campanhas = eS.campanhas(request);
		if (campanhas != null)
			this.enviaResposta(Status.OK, response, campanhas);
		else
			this.enviaResposta(Status.EXPECTATION_FAILED, response);
	}
	private void aderirCampanha(Request request, Response response) throws Exception {
		if (eS.aderirCampanha(request))
			this.enviaResposta(Status.OK, response);
		else
			this.enviaResposta(Status.EXPECTATION_FAILED, response);
	}
	
	private void enviaResposta(Status status, Response response) throws Exception {
		PrintStream body = response.getPrintStream();
		long time = System.currentTimeMillis();
		response.setValue("Access-Control-Allow-Origin","*");
		response.setValue("Access-Control-Allow-Methods","GET,POST");
	    response.setValue("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
		response.setValue("Content-Type", "text/html");
		response.setValue("Server", "Controle de doadoresService (1.0)");
		response.setDate("Date", time);
		response.setDate("Last-Modified", time);
		response.setStatus(status);
		body.close();
	}
	
	private void enviaResposta(Status status, Response response, JSONObject json) throws Exception {
		PrintStream body = response.getPrintStream();
		long time = System.currentTimeMillis();
		response.setValue("Access-Control-Allow-Origin","*");
		response.setValue("Access-Control-Allow-Methods","GET,POST");
	    response.setValue("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
		response.setValue("Content-Type", "application/json");
		response.setValue("Server", "Controle de Login");
		response.setDate("Date", time);
		response.setDate("Last-Modified", time);
		response.setStatus(status);
		body.print(json);
		body.close();
	}

	private void enviaResposta(Status status, Response response, String jsonArray) throws Exception {
		PrintStream body = response.getPrintStream();
		long time = System.currentTimeMillis();
		response.setValue("Access-Control-Allow-Origin","*");
		response.setValue("Access-Control-Allow-Methods","GET,POST");
	    response.setValue("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
		response.setValue("Content-Type", "application/json");
		response.setValue("Server", "Controle de Login");
		response.setDate("Date", time);
		response.setDate("Last-Modified", time);
		response.setStatus(status);
		body.print(jsonArray);
		body.close();
	}
	
	public JSONObject toJSON(String dadosDoUsuario) throws JSONException {
		JSONObject json = new JSONObject(dadosDoUsuario);
		return json;
	}
	
	public static void main(String[] list) throws Exception {

		dS = new DoadorService();
		eS = new EmpresaService();
		
		int porta = 8080;

		Container container = new URLMetodo();
		ContainerSocketProcessor servidor = new ContainerSocketProcessor(container);
		Connection conexao = new SocketConnection(servidor);
		SocketAddress endereco = new InetSocketAddress(porta);
		conexao.connect(endereco);

		System.out.println("Tecle ENTER para interromper o servidor...");
		System.in.read();

		conexao.close();
		servidor.stop();
	}
}
