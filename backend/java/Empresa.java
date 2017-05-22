package sql;

import java.math.BigInteger;

public class Empresa extends Usuario {
	
	private BigInteger nrCnpj;

	public Empresa(Integer id, BigInteger cnpj, String nome, String email, String senha, String cidade) {
		super(id, nome, email, senha, cidade);
		setNrCnpj(cnpj);
	}

	public BigInteger getNrCnpj() {
		return nrCnpj;
	}

	public void setNrCnpj(BigInteger nrCnpj) {
		this.nrCnpj = nrCnpj;
	}
}
