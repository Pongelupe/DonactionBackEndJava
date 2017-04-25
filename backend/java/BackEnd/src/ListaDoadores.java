import java.util.LinkedList;

public class ListaDoadores<T> {
	private LinkedList<T> lista;

	public void inserirDoador(T d) {
		lista.add(d);
	};

	public T buscaDoador(int index) {
		return lista.get(index);
	};

	public T atualizarDadosDoador(int index) {
		return lista.remove(index);
	};

	public ListaDoadores() {
		this.lista = new LinkedList<>();
	};
}
