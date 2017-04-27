import java.io.IOException;

import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

public final class ListaDeDoadoresService {

	private ListaDeDoadores lista;

	public ListaDeDoadoresService() throws IOException {
		setLista(new ListaDeDoadores("src/JSON_Doadores.txt"));
	}

	public String adicionarDoador(Request request) throws IOException {
		Doador d = null;
		Query q = request.getQuery();
		Integer id = getLista().getLista().indexOf((getLista().getLista().size()) + 1);
		String nome = q.get("nome");
		String email = q.get("email");
		String senha = q.get("senha");
		String cidade = q.get("cidade");
		String tipoSanguineo = q.get("tipoSanguineo");
		Boolean podeDoar = Boolean.parseBoolean(q.get("podeDoar"));
		d = new Doador(id, nome, email, senha, cidade, tipoSanguineo, podeDoar);
		getLista().adicionarDoador(d);
		return d.toString();
	}

	public String logarConta(Request request) {
		Query query = request.getQuery();
		String emailInformado = query.get("email");
		String senhaInformada = query.get("senha");
		return ListaDeDoadores.gson.toJson(getLista().pesquisarDoador(emailInformado, senhaInformada), Doador.class);
	}

	public String alterarDadosCadastrados(Request request) throws IOException {
		Query query = request.getQuery();
		Integer id = query.getInteger("id");
		String email = query.get("email");
		String cidade = query.get("cidade");
		if (getLista().alterarDadosDoador(id, email, cidade)) {
			return "Dados alterados com sucesso!";
		} else
			return "Doador não cadastrado!";
	}

	public ListaDeDoadores getLista() {
		return lista;
	}

	public void setLista(ListaDeDoadores lista) {
		this.lista = lista;
	}
}
