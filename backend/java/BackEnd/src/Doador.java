
public class Doador {
	private String nome, email, cidade, senha, tipoSang;
	private Boolean apto;
	private Boolean[] respostasCheckbox = new Boolean[4];

	public String getNome() {
		return nome;
	};

	public void setNome(String nome) {
		this.nome = nome;
	};

	public String getEmail() {
		return email;
	};

	public void setEmail(String email) {
		this.email = email;
	};

	public String getCidade() {
		return cidade;
	};

	public void setCidade(String cidade) {
		this.cidade = cidade;
	};

	public String getSenha() {
		return senha;
	};

	public void setSenha(String senha) {
		this.senha = senha;
	};

	public String gettipoSang() {
		return tipoSang;
	};

	public void settipoSang(String tipoSang) {
		this.tipoSang = tipoSang;
	};

	public Boolean getApto() {
		return apto;
	};

	public void setApto(Boolean apto) {
		this.apto = apto;
	};

	public Boolean[] getRespostasCheckbox() {
		return respostasCheckbox;
	};

	public void setRespostasCheckbox(Boolean[] respostasCheckbox) {
		this.respostasCheckbox = respostasCheckbox;
	};

	Doador() {
		this.setNome("John Doe");
		this.setEmail("shulambs@shulabs.com");
		this.setCidade("Shulambs");
		this.setSenha("qwerty");
		this.setApto(true);
	}

	public Doador(String nome, String email, String cidade, String senha, String tipoSang, Boolean apto,
			Boolean[] respostas) {
		this.setNome(nome);
		this.setEmail(email);
		this.setCidade(cidade);
		this.setSenha(senha);
		this.settipoSang(tipoSang);
		this.setApto(apto);
		this.setRespostasCheckbox(respostas);
	};

}
