import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

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
			String jsonUsuario;

			if (path.startsWith("/adicionarDoador") && "POST".equals(method)) {
				if (doadoresService.adicionarDoador(request))
					this.enviaResposta(Status.CREATED, response);
				else
					this.enviaResposta(Status.EXPECTATION_FAILED, response);

			} else if (path.startsWith("/logarConta") && "POST".equals(method)) {
				jsonUsuario = doadoresService.logarConta(request);
				if (jsonUsuario != null)
					this.enviaResposta(Status.OK, response);
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
