
import java.math.BigInteger;

public class EmprCampanha {
	
	private String nmCampanha;
	private String dsCampanha;
	private String prefVoucher;
	private String dtInicio;
	private String dtFim;
	private Integer qtdVouchers;
	private Integer vouchersUsados;
	private BigInteger vlrInvestimento;
		
	public EmprCampanha(String nmCampanha, String dsCampanha, String prefVoucher, String dtInicio, String dtFim, Integer qtdVouchers,
			Integer vouchersUsados, BigInteger vlrInvestimento) {
		this.nmCampanha = nmCampanha;
		this.dsCampanha = dsCampanha;
		this.prefVoucher = prefVoucher;
		this.dtInicio = dtInicio;
		this.dtFim = dtFim;
		this.qtdVouchers = qtdVouchers;
		this.vouchersUsados = vouchersUsados;
		this.vlrInvestimento = vlrInvestimento;
	}
	
	public String getNmCampanha() {
		return nmCampanha;
	}
	public String getDsCampanha() {
		return dsCampanha;
	}
	public String getPrefVoucher() {
		return prefVoucher;
	}
	public Integer getQtdVouchers() {
		return qtdVouchers;
	}
	public Integer getVouchersUsados() {
		return vouchersUsados;
	}
	public String getDtInicio() {
		return dtInicio;
	}
	public String getDtFim() {
		return dtFim;
	}
	public BigInteger getVlrInvestimento() {
		return vlrInvestimento;
	}
	public void setDsCampanha(String dsCampanha) {
		this.dsCampanha = dsCampanha;
	}
	public void setNmCampanha(String nmCampanha) {
		this.nmCampanha = nmCampanha;
	}
	public void setPrefVoucher(String prefVoucher) {
		this.prefVoucher = prefVoucher;
	}
	public void setQtdVouchers(Integer qtdVouchers) {
		this.qtdVouchers = qtdVouchers;
	}
	public void setVouchersUsados(Integer vouchersUsados) {
		this.vouchersUsados = vouchersUsados;
	}
	public void setDtInicio(String dtInicio) {
		this.dtInicio = dtInicio;
	}
	public void setDtFim(String dtFim) {
		this.dtFim = dtFim;
	}
	public void setVlrInvestimento(BigInteger vlrInvestimento) {
		this.vlrInvestimento = vlrInvestimento;
	}
	
	
}
