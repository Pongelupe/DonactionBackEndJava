import java.io.IOException;

import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

public final class ListaDeDoadoresService {

	private ListaDeDoadores lista;
	
	public ListaDeDoadoresService() throws IOException {
		setLista(new ListaDeDoadores("src/JSON_Doadores.txt"));
	}

	public boolean adicionarDoador(Request request) throws IOException {
		Query q = request.getQuery();
		String nome = q.get("nome");
		String email = q.get("email");
		String senha = q.get("senha");
		String cidade = q.get("cidade");
		String tipoSanguineo = q.get("tipoSanguineo");
		Boolean podeDoar = Boolean.parseBoolean(q.get("podeDoar"));
		return getLista().adicionarDoador(new Doador(nome, email, senha, cidade, tipoSanguineo, podeDoar));
	}
	
	public Doador logarConta(Request request) {
		Query q = request.getQuery();
		String emailInformado = q.get("email");
		String senhaInformada = q.get("senha");
		return getLista().pesquisarDoador(emailInformado, senhaInformada);
	}

	public ListaDeDoadores getLista() {
		return lista;
	}

	public void setLista(ListaDeDoadores lista) {
		this.lista = lista;
	}
}
