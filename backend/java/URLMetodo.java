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

	public static ListaDeDoadoresService doadoresService;

	@Override
	public void handle(Request request, Response response) {
		try {
			String path = request.getPath().getPath();
			String method = request.getMethod();

			if (path.startsWith("/adicionarDoador") && "POST".equals(method)) {
				if (doadoresService.adicionarDoador(request))
					this.enviaResposta(Status.CREATED, response);
				else
					this.enviaResposta(Status.EXPECTATION_FAILED, response);

			} else if (path.startsWith("/logarConta") && "POST".equals(method)) {
				String dadosDoUsuario = doadoresService.logarConta(request);
				if (dadosDoUsuario != null)
					this.enviaResposta(Status.OK, response, toJSON(dadosDoUsuario));
				else
					this.enviaResposta(Status.EXPECTATION_FAILED, response);

			} else if (path.startsWith("/alterarDadosCadastrados") && "POST".equals(method)) {
				if (doadoresService.alterarDadosCadastrados(request))
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

		doadoresService = new ListaDeDoadoresService();
		int porta = 8080;

		// Configura uma conexão soquete para o servidor HTTP.
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
