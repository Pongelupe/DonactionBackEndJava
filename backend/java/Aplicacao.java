import java.io.IOException;

public class Aplicacao {

	public static void main(String[] args) {
		ListaDeDoadores lista;
				
		try {
			lista = new ListaDeDoadores("src/JSON_Doadores.txt");
			lista.criarLista();
			System.out.println();
			lista.salvarLista();
			System.out.println();
		} catch (IOException e) {
			System.out.println("Erro!");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Usuário ou Senha Inseridos são Inválidos!");
		} finally {
			
		}
		
	}
}
