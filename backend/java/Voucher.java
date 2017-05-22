package sql;

public class Voucher {
	
	private String nmVoucher;
	private String prefVoucher;
	private Boolean ehValido;
	
	public Voucher(String nmVoucher, String prefVoucher, Boolean ehValido) {
		this.nmVoucher = nmVoucher;
		this.prefVoucher = prefVoucher;
		this.ehValido = ehValido;
	}

	public String getNmVoucher() {
		return nmVoucher;
	}
	public String getPrefVoucher() {
		return prefVoucher;
	}
	public Boolean getEhValido() {
		return ehValido;
	}
	public void setNmVoucher(String nmVoucher) {
		this.nmVoucher = nmVoucher;
	}
	public void setPrefVoucher(String prefVoucher) {
		this.prefVoucher = prefVoucher;
	}
	public void setEhValido(Boolean ehValido) {
		this.ehValido = ehValido;
	}
	
	
}
