
public abstract class Usuario {

	private Integer id;
	private String nome;
	private String email;
	private String senha;
	private String cidade;
	
	public Usuario(Integer id, String nome, String email, String senha, String cidade) {
		setId(id);
		setNome(nome);
		setEmail(email);
		setSenha(senha);
		setCidade(cidade);
	}
	
	public Usuario(String nome, String email, String senha, String cidade) {
		setNome(nome);
		setEmail(email);
		setSenha(senha);
		setCidade(cidade);
	}
	
	public Usuario() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public String getCidade() {
		return cidade;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
}
