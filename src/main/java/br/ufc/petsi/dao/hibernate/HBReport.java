package br.ufc.petsi.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.ReportDAO;
import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.DetailByMonth;
import br.ufc.petsi.model.GeneralReport;
import br.ufc.petsi.model.Rating;
import br.ufc.petsi.model.RatingReport;
import br.ufc.petsi.model.ServiceReport;

@Repository
public class HBReport implements ReportDAO{


	@PersistenceContext
	private EntityManager manager;

	public RatingReport getRatingReport(Long professionalId, Date dateBegin, Date dateEnd, Integer month, Integer year) {
		
		RatingReport ratingReport = new RatingReport();
		
		Query query = (Query) manager.createQuery("SELECT avg(r.rating) FROM Rating r, Consultation c WHERE c.professional.id = :professionalId AND r.consultation.id = c.id");
		query.setParameter("professionalId", professionalId);
		
		Object result = query.getSingleResult();
		double rating = 0;
		
		if(result != null)
			rating = (double) result;
		
		ratingReport.setAverage(rating);
		
		Query query2 = null;
		if(dateBegin != null && dateEnd != null){
			query2 = (Query) manager.createQuery("SELECT r FROM Rating r, Consultation c WHERE r.consultation.id = c.id and c.professional.id = :professionalId and c.dateInit >= :dateBegin and c.dateInit <= :dateEnd");
			query2.setParameter("dateBegin", dateBegin);
			query2.setParameter("dateEnd", dateEnd);
		}else {
			query2 = (Query) manager.createQuery("SELECT r FROM Rating r, Consultation c WHERE r.consultation.id = c.id and c.professional.id = :professionalId and extract(MONTH FROM c.dateInit) = :m and extract(YEAR FROM c.dateInit) = :y");
			query2.setParameter("m", month);
			query2.setParameter("y", year);
		}
		query2.setParameter("professionalId", professionalId);
		
		
		ratingReport.setRatings((List<Rating>)query2.getResultList());
		
		return ratingReport;
	}
	
	public ServiceReport getServiceReport(long serviceId, long professionalId, Date dateBegin, Date dateEnd, Integer month, Integer year) {
		
		ServiceReport serviceReport = new ServiceReport();
		
		Query query = (Query) manager.createQuery("SELECT u.name FROM users u WHERE u.id = :professionalId");
		query.setParameter("professionalId", professionalId);
		serviceReport.setProfessional((String)query.getSingleResult());
		
		query = (Query) manager.createQuery("SELECT name FROM SocialService WHERE id = :serviceId");
		query.setParameter("serviceId", serviceId);
		serviceReport.setService((String)query.getSingleResult());
		
		if(dateBegin != null && dateEnd != null){
			query = (Query) manager.createQuery("SELECT count(c.id) FROM Consultation c WHERE c.professional.id = :professionalId and c.state != 'FR' and c.dateInit BETWEEN :dateBegin and :dateEnd");
			query.setParameter("dateBegin", dateBegin);
			query.setParameter("dateEnd", dateEnd);
		}else{
			query = (Query) manager.createQuery("SELECT count(c.id) FROM Consultation c WHERE c.professional.id = :professionalId and c.state != 'FR' and extract(MONTH FROM c.dateInit) = :m and extract(YEAR FROM c.dateInit) = :y");
			query.setParameter("m", month);
			query.setParameter("y", year);
		}
		
		query.setParameter("professionalId", professionalId);
		Long total = (Long) query.getSingleResult();
		serviceReport.setTotal(total.intValue());
		
		if(dateBegin != null && dateEnd != null){
			query = (Query) manager.createQuery("SELECT count(c.id) FROM Consultation c WHERE c.professional.id = :professionalId and c.state = 'RD' and c.dateInit BETWEEN :dateBegin and :dateEnd");
			query.setParameter("dateBegin", dateBegin);
			query.setParameter("dateEnd", dateEnd);
		}else{
			query = (Query) manager.createQuery("SELECT count(c.id) FROM Consultation c WHERE c.professional.id = :professionalId and c.state = 'RD' and extract(MONTH FROM c.dateInit) = :m and extract(YEAR FROM c.dateInit) = :y");
			query.setParameter("m", month);
			query.setParameter("y", year);	
		}
		query.setParameter("professionalId", professionalId);
		Long scheduled = (Long) query.getSingleResult();
		serviceReport.setScheduled(scheduled.intValue());
		
		
		if(dateBegin != null && dateEnd != null){
			query = (Query) manager.createQuery("SELECT count(c.id) FROM Consultation c WHERE c.professional.id = :professionalId and c.state = 'RS' and c.dateInit BETWEEN :dateBegin and :dateEnd");			
			query.setParameter("dateBegin", dateBegin);
			query.setParameter("dateEnd", dateEnd);
		}else{
			query = (Query) manager.createQuery("SELECT count(c.id) FROM Consultation c WHERE c.professional.id = :professionalId and c.state = 'RS' and extract(MONTH FROM c.dateInit) = :m and extract(YEAR FROM c.dateInit) = :y");
			query.setParameter("m", month);
			query.setParameter("y", year);
		}
		query.setParameter("professionalId", professionalId);
		Long rescheduled = (Long) query.getSingleResult();
		serviceReport.setRescheduled(rescheduled.intValue());
		
		
		if(dateBegin != null && dateEnd != null){
			query = (Query) manager.createQuery("SELECT count(c.id) FROM Consultation c WHERE c.professional.id = :professionalId and c.state = 'UD' and c.dateInit BETWEEN :dateBegin and :dateEnd");
			query.setParameter("dateBegin", dateBegin);
			query.setParameter("dateEnd", dateEnd);
		}else{
			query = (Query) manager.createQuery("SELECT count(c.id) FROM Consultation c WHERE c.professional.id = :professionalId and c.state = 'UD' and extract(MONTH FROM c.dateInit) = :m and extract(YEAR FROM c.dateInit) = :y");
			query.setParameter("m", month);
			query.setParameter("y", year);
		}
		query.setParameter("professionalId", professionalId);
		Long unscheduled = (Long) query.getSingleResult();
		serviceReport.setUnscheduled(unscheduled.intValue());
		
		if(dateBegin != null && dateEnd != null){
			query = (Query) manager.createQuery("SELECT count(c.id) FROM Consultation c WHERE c.professional.id = :professionalId and c.state = 'CD' and c.dateInit BETWEEN :dateBegin and :dateEnd");
			query.setParameter("dateBegin", dateBegin);
			query.setParameter("dateEnd", dateEnd);
		}else{
			query = (Query) manager.createQuery("SELECT count(c.id) FROM Consultation c WHERE c.professional.id = :professionalId and c.state = 'CD' and extract(MONTH FROM c.dateInit) = :m and extract(YEAR FROM c.dateInit) = :y");
			query.setParameter("m", month);
			query.setParameter("y", year);
		}
		query.setParameter("professionalId", professionalId);
		Long canceled = (Long) query.getSingleResult();
		serviceReport.setCanceled(canceled.intValue());
		
		Query query2 = null;
		if(dateBegin != null && dateEnd != null){
			query2 = (Query) manager.createQuery("SELECT to_char(c.dateInit, 'MM/YYYY'), "
					+ "sum(case when c.state = 'RD' then 1 else 0 end), "
					+ "sum(case when c.state = 'RS' then 1 else 0 end), "
					+ "sum(case when c.state = 'CD' then 1 else 0 end), "
					+ "sum(case when c.state != 'FR' then 1 else 0 end) FROM Consultation c "
					+ "WHERE c.professional.id = :professionalId and c.dateInit BETWEEN :dateBegin and :dateEnd "
					+ "GROUP BY to_char(c.dateEnd, 'YYYY/MM'),  to_char(c.dateInit, 'MM/YYYY') "
					+ "ORDER BY to_char(c.dateEnd, 'YYYY/MM')");
			query2.setParameter("dateBegin", dateBegin);
			query2.setParameter("dateEnd", dateEnd);	
		}else{
			query2 = (Query) manager.createQuery("SELECT to_char(c.dateInit, 'MM/YYYY'), "
					+ "sum(case when c.state = 'RD' then 1 else 0 end), "
					+ "sum(case when c.state = 'RS' then 1 else 0 end), "
					+ "sum(case when c.state = 'CD' then 1 else 0 end), "
					+ "sum(case when c.state != 'FR' then 1 else 0 end) FROM Consultation c "
					+ "WHERE c.professional.id = :professionalId and extract(MONTH FROM c.dateInit) = :m and extract(YEAR FROM c.dateInit) = :y "
					+ "GROUP BY to_char(c.dateEnd, 'YYYY/MM'),  to_char(c.dateInit, 'MM/YYYY') "
					+ "ORDER BY to_char(c.dateEnd, 'YYYY/MM')");
			query2.setParameter("m", month);
			query2.setParameter("y", year);	
		}
		query2.setParameter("professionalId", professionalId);
		List<Object[]> list = query2.getResultList();

		List <DetailByMonth> detail = new ArrayList<DetailByMonth>();
		DetailByMonth d;
		
		String numberOfMonth;
		int totalByMonth;
		int scheduledByMonth;
		int unscheduledByMonth;
		int canceledByMonth;
		
		Long help;
		
		for (Object[] object : list) {
			numberOfMonth = (String) object[0];
			
			help = (Long) object[1];
			scheduledByMonth = help.intValue();
			
			help = (Long) object[2];
			unscheduledByMonth = help.intValue();
			
			help = (Long) object[3];
			canceledByMonth = help.intValue();

			help = (Long) object[4];
			totalByMonth = help.intValue();
			
			d = new DetailByMonth(numberOfMonth, totalByMonth, scheduledByMonth, unscheduledByMonth, canceledByMonth);
			detail.add(d);
		}
		
		serviceReport.setByMonth(detail);
		
		return serviceReport;
	}
	
	public GeneralReport getGeneralReport(Date dateBegin,Date dateEnd, Integer month, Integer year) {
		
		GeneralReport generalReport = new GeneralReport();
		Query query = null;
		 
		if(dateBegin != null && dateEnd != null){
			query = (Query) manager.createQuery("SELECT to_char(c.dateInit, 'MM/YYYY'), "
					+ "sum(case when c.state = 'RD' then 1 else 0 end), "
					+ "sum(case when c.state = 'RS' then 1 else 0 end), "
					+ "sum(case when c.state = 'CD' then 1 else 0 end), "
					+ "sum(case when c.state != 'FR' then 1 else 0 end) FROM Consultation c "
					+ "WHERE c.dateInit BETWEEN :dateBegin and :dateEnd "
					+ "GROUP BY to_char(c.dateEnd, 'YYYY/MM'),  to_char(c.dateInit, 'MM/YYYY') "
					+ "ORDER BY to_char(c.dateEnd, 'YYYY/MM')");
			query.setParameter("dateBegin", dateBegin);
			query.setParameter("dateEnd", dateEnd);
		}else{
			query = (Query) manager.createQuery("SELECT to_char(c.dateInit, 'MM/YYYY'), "
					+ "sum(case when c.state = 'RD' then 1 else 0 end), "
					+ "sum(case when c.state = 'RS' then 1 else 0 end), "
					+ "sum(case when c.state = 'CD' then 1 else 0 end), "
					+ "sum(case when c.state != 'FR' then 1 else 0 end) FROM Consultation c "
					+ "WHERE extract(MONTH FROM c.dateInit) = :m and extract(YEAR FROM c.dateInit) = :y "
					+ "GROUP BY to_char(c.dateEnd, 'YYYY/MM'),  to_char(c.dateInit, 'MM/YYYY') "
					+ "ORDER BY to_char(c.dateEnd, 'YYYY/MM')");
			query.setParameter("m", month);
			query.setParameter("y", year);
		}
		
		List<Object[]> list = query.getResultList();

		List <DetailByMonth> detail = new ArrayList<DetailByMonth>();
		DetailByMonth d;
		
		String numberOfMonth;
		int totalByMonth;
		int scheduledByMonth;
		int unscheduledByMonth;
		int canceledByMonth;
		
		Long help;
		
		for (Object[] object : list) {
			numberOfMonth = (String) object[0];
			
			help = (Long) object[1];
			scheduledByMonth = help.intValue();
			
			help = (Long) object[2];
			unscheduledByMonth = help.intValue();
			
			help = (Long) object[3];
			canceledByMonth = help.intValue();

			help = (Long) object[4];
			totalByMonth = help.intValue();
			
			d = new DetailByMonth(numberOfMonth, totalByMonth, scheduledByMonth, unscheduledByMonth, canceledByMonth);
			detail.add(d);
		}
		
		generalReport.setByMonth(detail);	
		
		return generalReport;
	}
	
	
}
