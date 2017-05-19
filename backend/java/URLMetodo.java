package sql;
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

	@Override
	public void handle(Request request, Response response) {
		try {
			String path = request.getPath().getPath();
			String method = request.getMethod();

			if (path.startsWith("/cadastrar") && "POST".equals(method)) {
				if (dS.cadastrar(request))
					this.enviaResposta(Status.CREATED, response);
				else
					this.enviaResposta(Status.EXPECTATION_FAILED, response);
				
			} else if (path.startsWith("/logar") && "POST".equals(method)) {
				String dadosDoUsuario = dS.logar(request);
				if (dadosDoUsuario != null)
					this.enviaResposta(Status.OK, response, toJSON(dadosDoUsuario));
				else
					this.enviaResposta(Status.EXPECTATION_FAILED, response);
				
			} else if (path.startsWith("/atualizarCadastro") && "POST".equals(method)) {
				if (dS.atualizarCadastro(request))
					this.enviaResposta(Status.OK, response);
				else
					this.enviaResposta(Status.EXPECTATION_FAILED, response);
			} else if (path.startsWith("/validarVoucher") && "POST".equals(method)) {
				if (dS.validarVoucher(request))
					this.enviaResposta(Status.OK, response);
				else
					this.enviaResposta(Status.EXPECTATION_FAILED, response);
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public JSONObject toJSON(String dadosDoUsuario) throws JSONException {
		JSONObject json = new JSONObject(dadosDoUsuario);
		return json;
	}
	
	public static void main(String[] list) throws Exception {
//		To test the back-end, Azure's SQL credentials are needed, contact Master Eric to get then. 
//		dS = new DoadorService();
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
