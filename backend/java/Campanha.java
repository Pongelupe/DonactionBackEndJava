
import java.math.BigInteger;

public class Campanha {
	private String nmCampanha;
	private String dsCampanha;
	private String cidadeCampanha;
	private String dtInicio;
	private String dtFim;
	private Integer qtdMinVoucher;
	private BigInteger vlrInvestimento;

	public Campanha(String nmCampanha, String dsCampanha, String cidadeCampanha, String dtInicio, String dtFim,
			Integer qtdMinVoucher, BigInteger vlrInvestimento) {
		this.nmCampanha = nmCampanha;
		this.dsCampanha = dsCampanha;
		this.cidadeCampanha = cidadeCampanha;
		this.dtInicio = dtInicio;
		this.dtFim = dtFim;
		this.qtdMinVoucher = qtdMinVoucher;
		this.vlrInvestimento = vlrInvestimento;
	}
	
	public String getNmCampanha() {
		return nmCampanha;
	}
	public String getDsCampanha() {
		return dsCampanha;
	}
	public String getCidadeCampanha() {
		return cidadeCampanha;
	}
	public String getDtInicio() {
		return dtInicio;
	}
	public String getDtFim() {
		return dtFim;
	}
	public Integer getQtdMinVoucher() {
		return qtdMinVoucher;
	}
	public BigInteger getVlrInvestimento() {
		return vlrInvestimento;
	}
	public void setNmCampanha(String nmCampanha) {
		this.nmCampanha = nmCampanha;
	}
	public void setDsCampanha(String dsCampanha) {
		this.dsCampanha = dsCampanha;
	}
	public void setCidadeCampanha(String cidadeCampanha) {
		this.cidadeCampanha = cidadeCampanha;
	}
	public void setDtInicio(String dtInicio) {
		this.dtInicio = dtInicio;
	}
	public void setDtFim(String dtFim) {
		this.dtFim = dtFim;
	}
	public void setQtdMinVoucher(Integer qtdMinVoucher) {
		this.qtdMinVoucher = qtdMinVoucher;
	}
	public void setVlrInvestimento(BigInteger vlrInvestimento) {
		this.vlrInvestimento = vlrInvestimento;
	}
	
	
}
