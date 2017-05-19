package sql;

import org.joda.time.DateTime;

public class EmprCampanha {
	
	private Integer cdEmpresa;
	private Integer cdCampanha;
	private Integer nmCampanha;
	private Integer cdVoucher;
	private DateTime dtParticipacao;
	
	EmprCampanha(Integer cdEmpresa, Integer cdCampanha, Integer nmCampanha, Integer cdVoucher, DateTime dtParticipacao) {
		setCdEmpresa(cdEmpresa);
		setCdCampanha(cdCampanha);
		setNmCampanha(nmCampanha);
		setCdVoucher(cdVoucher);
		setDtParticipacao(dtParticipacao);
	}

	public Integer getCdEmpresa() {
		return cdEmpresa;
	}
	public Integer getCdCampanha() {
		return cdCampanha;
	}
	public Integer getNmCampanha() {
		return nmCampanha;
	}
	public Integer getCdVoucher() {
		return cdVoucher;
	}
	public DateTime getDtParticipacao() {
		return dtParticipacao;
	}
	public void setCdEmpresa(Integer cdEmpresa) {
		this.cdEmpresa = cdEmpresa;
	}
	public void setCdCampanha(Integer cdCampanha) {
		this.cdCampanha = cdCampanha;
	}
	public void setNmCampanha(Integer nmCampanha) {
		this.nmCampanha = nmCampanha;
	}
	public void setCdVoucher(Integer cdVoucher) {
		this.cdVoucher = cdVoucher;
	}
	public void setDtParticipacao(DateTime dtParticipacao) {
		this.dtParticipacao = dtParticipacao;
	}
}
