import java.io.IOException;

import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

public final class ListaDeDoadoresService {

	private ListaDeDoadores lista;

	public ListaDeDoadoresService() throws IOException {
		setLista(new ListaDeDoadores("src/JSON_Doadores.txt"));
	}

	public Boolean adicionarDoador(Request request) throws IOException {
		Query query = request.getQuery();
		Integer id = getLista().getLista().size();
		String nome = query.get("nome");
		String email = query.get("email");
		String senha = query.get("senha");
		String cidade = query.get("cidade");
		String tipoSanguineo = query.get("tipoSanguineo");
		Boolean podeDoar = Boolean.parseBoolean(query.get("podeDoar"));
		return getLista().adicionarDoador(new Doador(id, nome, email, senha, cidade, tipoSanguineo, podeDoar));
	}

	public String logarConta(Request request) {
		Query query = request.getQuery();
		System.out.println("QUERY: " + query);
		String emailInformado = query.get("email");
		String senhaInformada = query.get("senha");
		Doador dadosDoUsuario = getLista().pesquisarDoador(emailInformado, senhaInformada);
		if (dadosDoUsuario != null)
			return ListaDeDoadores.gson.toJson(dadosDoUsuario, Doador.class);
		return null;
	}

	public Boolean alterarDadosCadastrados(Request request) throws IOException {
		Query query = request.getQuery();
		Integer id = query.getInteger("id");
		String email = query.get("email");
		String cidade = query.get("cidade");
		return getLista().alterarDadosDoador(id, email, cidade);
	}

	public ListaDeDoadores getLista() {
		return lista;
	}

	public void setLista(ListaDeDoadores lista) {
		this.lista = lista;
	}
}
