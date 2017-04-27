import java.io.IOException;

public class Aplicacao {

	public static void main(String[] args) {
		ListaDeDoadoresService l;
		//Doador d = new Doador();
		
		try {
			l = new ListaDeDoadoresService();
			l.getLista().criarLista();
			System.out.println();
		} catch (IOException e) {
			System.out.println("Erro!");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Usuário ou Senha Inseridos são Inválidos!");
		} finally {
			
		}
		
	}
}
