package sql;

public class Doador extends Usuario {
	
	private Integer nrCpf;
	private String tipoSanguineo;
	private Boolean podeDoar;
	
	public Doador(Integer id, Integer nrCpf, String nome, String email, String senha, String cidade, String tipoSanguineo, Boolean podeDoar) {
		super(id, nome, email, senha, cidade);
		setNrCpf(nrCpf);
		setTipoSanguineo(tipoSanguineo);
		setPodeDoar(podeDoar);
	}
	

	
	public Integer getNrCpf() {
		return nrCpf;
	}

	public String getTipoSanguineo() {
		return tipoSanguineo;
	}

	public Boolean getPodeDoar() {
		return podeDoar;
	}

	public void setNrCpf(Integer nrCpf) {
		this.nrCpf = nrCpf;
	}

	public void setTipoSanguineo(String tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}

	public void setPodeDoar(Boolean podeDoar) {
		this.podeDoar = podeDoar;
	}

	
}
