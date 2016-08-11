package br.ufc.petsi.dao;

import java.util.Date;

import br.ufc.petsi.model.GeneralReport;
import br.ufc.petsi.model.RatingReport;
import br.ufc.petsi.model.ServiceReport;

public interface ReportDAO {
	public RatingReport getRatingReport(Long professionalId, Date dateBegin, Date dateEnd);
	public ServiceReport getServiceReport(long serviceId, long professionalId, Date dateBegin, Date dateEnd);
	public GeneralReport getGeneralReport(Date dateBegin, Date dateEnd);
}
