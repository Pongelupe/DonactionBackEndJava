
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Arquivo {

	private static String caminhoArquivo;
	private File arquivo;
	private FileReader fileReader;
	private FileWriter fileWriter;
	private BufferedReader buffReader;
	private BufferedWriter buffWriter;

	Arquivo(String caminhoArquivo) {
		Arquivo.caminhoArquivo = caminhoArquivo;	
	}

	public BufferedReader abrirArquivo() throws IOException {
		setArquivo(new File(Arquivo.caminhoArquivo));
		setFileReader(new FileReader(arquivo));
		setBuffReader(new BufferedReader(fileReader));
		return getBuffReader();
	}

	public void imprimirArquivo() throws IOException {
		BufferedReader bufferedReader = abrirArquivo();
		String linha;
		while ((linha = bufferedReader.readLine()) != null) {
			System.out.println(linha);
		}
	}

	public void adicionarNoArquivo(String elemento) throws IOException {
		setFileWriter(new FileWriter(getArquivo()));
		setBuffWriter(new BufferedWriter(getFileWriter()));
		getBuffWriter().write(elemento);
		getBuffWriter().close();
		getFileWriter().close();
	}

	public File getArquivo() {
		return arquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	public FileWriter getFileWriter() {
		return fileWriter;
	}

	public void setFileWriter(FileWriter fileWriter) {
		this.fileWriter = fileWriter;
	}

	public BufferedWriter getBuffWriter() {
		return buffWriter;
	}

	public void setBuffWriter(BufferedWriter buffWriter) {
		this.buffWriter = buffWriter;
	}

	public FileReader getFileReader() {
		return fileReader;
	}
	
	public void setFileReader(FileReader fileReader) {
		this.fileReader = fileReader;
	}
	
	public BufferedReader getBuffReader() {
		return buffReader;
	}

	public void setBuffReader(BufferedReader buffReader) {
		this.buffReader = buffReader;
	}

}
