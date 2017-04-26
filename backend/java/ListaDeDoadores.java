import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class ListaDeDoadores {
	private List<Doador> lista;
	private Doador doador;
	private Arquivo arquivo;
	private BufferedReader buffReader;
	private static Gson gson = new Gson();

	ListaDeDoadores(String caminhoArquivo) throws IOException {
		setLista(new LinkedList<>());
		arquivo = new Arquivo(caminhoArquivo);
		setBuffReader(arquivo.abrirArquivo());
	}

	public List<Doador> criarLista() throws IOException {
		String linha = getBuffReader().readLine();
		JsonParser parser = new JsonParser();
		JsonArray array = parser.parse(linha).getAsJsonArray();
		for (int i = 0; i < array.size(); i++) {
			getLista().add((gson.fromJson(array.get(i), Doador.class)));
		}
		return getLista();
	}

	public void salvarLista() throws IOException {
		String json = gson.toJson(getLista());
		arquivo.adicionarNoArquivo(json);
		System.out.println(json);
	}

	public boolean adicionarDoador(Doador doador) throws IOException {
		Boolean foiAdicionado = getLista().add(doador);
		salvarLista();
		return foiAdicionado;
	}

	public Doador pesquisarDoador(String emailInformado, String senhaInformada) {
		Integer indexDoador = -1;
		Integer i = 0;
		if (!getLista().isEmpty() && checarValidadeDados(emailInformado, senhaInformada)) {
			while (indexDoador == -1 && i < getLista().size()) {
				String emailCadastrado = getLista().get(i).getEmail();
				String senhaCadastrada = getLista().get(i).getSenha();
				indexDoador = compararDadosInformados(emailInformado, emailCadastrado, senhaInformada, senhaCadastrada,
						i);
				i++;
			}
		}
		if (indexDoador.equals(-1))
			return null;
		return getLista().get(indexDoador);
	}

	private Boolean checarValidadeDados(String emailInformado, String senhaInformada) {
		Boolean dadosSaoValidos = true;
		if (Objects.equals(emailInformado, "") || Objects.equals(senhaInformada, ""))
			dadosSaoValidos = false;
		return dadosSaoValidos;
	}

	private Integer compararDadosInformados(String emailInformado, String emailCadastrado, String senhaInformada,
			String senhaCadastrada, Integer i) {
		if (emailInformado.equalsIgnoreCase(emailCadastrado) && Objects.equals(senhaInformada, senhaCadastrada))
			return i;
		return -1;
	}

	public BufferedReader getBuffReader() {
		return buffReader;
	}

	public void setBuffReader(BufferedReader buffReader) {
		this.buffReader = buffReader;
	}

	public Doador getDoador() {
		return doador;
	}

	public void setDoador(Doador doador) {
		this.doador = doador;
	}

	public List<Doador> getLista() {
		return lista;
	}

	public void setLista(List<Doador> lista) {
		this.lista = lista;
	}

}
