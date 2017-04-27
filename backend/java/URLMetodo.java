
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
			// Recupera a URL e o método utilizado.

			String path = request.getPath().getPath();
			String method = request.getMethod();
			String resposta;

			// Verifica qual ação está sendo chamada

			if (path.startsWith("/adicionarDoador") && "POST".equals(method)) {
				resposta = doadoresService.adicionarDoador(request);
				this.enviaResposta(Status.CREATED, response, resposta);

			} else if (path.startsWith("/logarConta") && "POST".equals(method)) {
				resposta = doadoresService.logarConta(request);
				this.enviaResposta(Status.OK, response, resposta);
			} else if (path.startsWith("/alterarDadosCadastrados") && "POST".equals(method)) {
				resposta = doadoresService.alterarDadosCadastrados(request);
				this.enviaResposta(Status.OK, response, resposta);
			} else {
				this.naoEncontrado(response, path);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void naoEncontrado(Response response, String path) throws Exception {
		JSONObject error = new JSONObject();
		error.put("error", "Não encontrado.");
		error.put("path", path);
		enviaResposta(Status.NOT_FOUND, response, error.toString());
	}

	private void enviaResposta(Status status, Response response, String str) throws Exception {

		long time = System.currentTimeMillis();

		response.setValue("Content-Type", "application/json");
		response.setValue("Server", "Controle de doadoresService (1.0)");
		response.setDate("Date", time);
		response.setDate("Last-Modified", time);
		response.setStatus(status);

	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		return json;
	}

	public static void main(String[] list) throws Exception {

		// Instancia o doadoresService Service
		doadoresService = new ListaDeDoadoresService();

		// Se você receber uma mensagem
		// "Address already in use: bind error",
		// tente mudar a porta.

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
