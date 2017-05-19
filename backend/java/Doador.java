package sql;

import java.math.BigInteger;

public class Doador extends Usuario {
	
	private BigInteger nrCpf;
	private String tipoSanguineo;
	private Boolean podeDoar;
	
	public Doador(Integer id, BigInteger nrCpf, String nome, String email, String senha, String cidade, String tipoSanguineo, Boolean podeDoar) {
		super(id, nome, email, senha, cidade);
		setNrCpf(nrCpf);
		setTipoSanguineo(tipoSanguineo);
		setPodeDoar(podeDoar);
	}
	
	public BigInteger getNrCpf() {
		return nrCpf;
	}

	public String getTipoSanguineo() {
		return tipoSanguineo;
	}

	public Boolean getPodeDoar() {
		return podeDoar;
	}

	public void setNrCpf(BigInteger nrCpf) {
		this.nrCpf = nrCpf;
	}

	public void setTipoSanguineo(String tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}

	public void setPodeDoar(Boolean podeDoar) {
		this.podeDoar = podeDoar;
	}

	
}
