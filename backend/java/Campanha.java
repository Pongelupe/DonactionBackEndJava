package sql;

import org.joda.time.DateTime;

public class Campanha {
	private Integer cdCampanha;
	private String nmCampanha;
	private String dsCampanha;
	private String cidadeCampanha;
	private DateTime dtInicio;
	private DateTime dtFim;
	
	Campanha(Integer cdCampanha, String nmCampanha, String dsCampanha, DateTime dtInicio, DateTime dtFim) {
		setCdCampanha(cdCampanha);
		setDsCampanha(dsCampanha);
		setDtInicio(dtInicio);
		setDtFim(dtFim);
	}

	public Integer getCdCampanha() {
		return cdCampanha;
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
	public DateTime getDtInicio() {
		return dtInicio;
	}
	public DateTime getDtFim() {
		return dtFim;
	}
	public void setCdCampanha(Integer cdCampanha) {
		this.cdCampanha = cdCampanha;
	}
	public void setNmCampanha(String nmCampanha) {
		this.nmCampanha = nmCampanha;
	}
	public void setDsCampanha(String dsCampanha) {
		this.dsCampanha = dsCampanha;
	}
	public void setDtInicio(DateTime dtInicio) {
		this.dtInicio = dtInicio;
	}
	public void setDtFim(DateTime dtFim) {
		this.dtFim = dtFim;
	}
	public void setCidadeCampanha(String cidadeCampanha) {
		this.cidadeCampanha = cidadeCampanha;
	}
}
