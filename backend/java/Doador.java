
public class Doador {
	private String nome, email, senha, cidade, tipoSanguineo;
	private Boolean apto;

	Doador() {
		this("John Doe", "shulambs@shulambs.com", "qwerty", "Shulambs", "O+", true);
	};

	Doador(String nome, String email, String senha, String cidade, String tipoSanguineo, Boolean apto) {
		this.setNome(nome);
		this.setEmail(email);
		this.setSenha(senha);
		this.setCidade(cidade);
		this.setTipoSanguineo(tipoSanguineo);
		this.setApto(apto);
	};
	
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

	public String getTipoSanguineo() {
		return tipoSanguineo;
	};

	public void setTipoSanguineo(String tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	};

	public Boolean getApto() {
		return apto;
	};

	public void setApto(Boolean apto) {
		this.apto = apto;
	};

}
