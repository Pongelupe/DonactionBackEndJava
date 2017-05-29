
public class DoadorVoucher {
	
	private String nmCampanha;
	private String nmEmpresa;
	private String dtInicio;
	private String dtFim;
	
	public DoadorVoucher(String nmCampanha, String nmEmpresa, String dtInicio, String dtFim) {
		this.nmCampanha = nmCampanha;
		this.nmEmpresa = nmEmpresa;
		this.dtInicio = dtInicio;
		this.dtFim = dtFim;
	}
	
	
	public String getNmCampanha() {
		return nmCampanha;
	}
	public String getNmEmpresa() {
		return nmEmpresa;
	}
	public String getDtInicio() {
		return dtInicio;
	}
	public String getDtFim() {
		return dtFim;
	}
	public void setNmCampanha(String nmCampanha) {
		this.nmCampanha = nmCampanha;
	}
	public void setNmEmpresa(String nmEmpresa) {
		this.nmEmpresa = nmEmpresa;
	}
	public void setDtInicio(String dtInicio) {
		this.dtInicio = dtInicio;
	}
	public void setDtFim(String dtFim) {
		this.dtFim = dtFim;
	}
	
	
}
