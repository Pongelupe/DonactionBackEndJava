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
	private Integer indexDoador;
	public static Gson gson = new Gson();

	ListaDeDoadores(String caminhoArquivo) throws IOException {
		arquivo = new Arquivo(caminhoArquivo);
		setLista(new LinkedList<>());
		setBuffReader(arquivo.abrirArquivo());
		criarLista();
		setIndexDoador(-1);
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

	public Boolean adicionarDoador(Doador doador) throws IOException {
		Boolean foiAdicionado = getLista().add(doador);
		salvarLista();
		return foiAdicionado;
	}

	public Doador pesquisarDoador(String emailInformado, String senhaInformada) {
		Integer i = 0;
		setIndexDoador(-1);
		if (!getLista().isEmpty() && dadosSaoValidos(emailInformado, senhaInformada)) {
			while (indexDoador.equals(-1) && i < getLista().size()) {
				String emailCadastrado = getLista().get(i).getEmail();
				String senhaCadastrada = getLista().get(i).getSenha();
				setIndexDoador(dadosSaoIguais(emailInformado, emailCadastrado, senhaInformada, senhaCadastrada,i));
				i++;
			}
		}
		if (getIndexDoador().equals(-1))
			return null;
		return getLista().get(getIndexDoador());
	}

	public Doador removerDoador(String emailInformado, String senhaInformada) {
		if (pesquisarDoador(emailInformado, senhaInformada) == null)
			return null;
		return getLista().remove(getIndexDoador().intValue());
	}
	
	public Boolean alterarDadosDoador(Integer id, String email, String cidade) throws IOException {
		Boolean dadosForamAlterados = false;
		if (id < getLista().size()) {
			Doador doador = getLista().get(id);
			doador.setEmail(email);
			doador.setCidade(cidade);
			dadosForamAlterados = true;
		}
		salvarLista();
		return dadosForamAlterados;
	}
	
	private Boolean dadosSaoValidos(String emailInformado, String senhaInformada) {
		Boolean dadosSaoValidos = true;
		if (Objects.equals(emailInformado, "") || Objects.equals(senhaInformada, ""))
			dadosSaoValidos = false;
		return dadosSaoValidos;
	}

	private Integer dadosSaoIguais(String emailInformado, String emailCadastrado, String senhaInformada,
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

	public Integer getIndexDoador() {
		return indexDoador;
	}

	public void setIndexDoador(Integer indexDoador) {
		this.indexDoador = indexDoador;
	}

	
}
