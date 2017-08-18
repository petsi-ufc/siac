package br.ufc.petsi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.dao.ReportDAO;
import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.GeneralReport;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.model.Rating;
import br.ufc.petsi.model.RatingReport;
import br.ufc.petsi.model.ServiceReport;

@Controller
public class ReportController {
	
	@Inject
	private ReportDAO reportDAO;
	
	@Inject
	private ConsultationDAO consultationDAO;
	
	@RequestMapping("/relatorio/servico")
	public String gerarRelatorioPorServico(Model model, HttpSession session,
			 @DateTimeFormat(pattern="dd/MM/yyyy") Date dateBegin, 
			 @DateTimeFormat(pattern="dd/MM/yyyy") Date dateEnd, 
			 int serviceId, int professionalId,
			 @RequestParam("month") Integer month, @RequestParam("year") Integer year){ 
		
		ServiceReport serviceReport = null;
		if(year == null){
			serviceReport = reportDAO.getServiceReport(serviceId, professionalId, dateBegin, dateEnd, null, null);
		}else{
			serviceReport = reportDAO.getServiceReport(serviceId, professionalId, null, null, month, year);
			Calendar c = Calendar.getInstance();
			c.set(year, month-1, 1);
			dateBegin = c.getTime();
			
			if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) c.set(year, month-1, 31);
			else if(month == 2) c.set(year, month-1, 28);
			else c.set(year, month-1, 30);
			
			dateEnd = c.getTime();
		}
		
		Professional p = new Professional();
		p.setId(professionalId);
		
		List<Consultation> consultations = consultationDAO.getConsultationByPeriod(p, dateBegin, dateEnd);
		List<Rating> cancel = new ArrayList<Rating>();
		List<Rating> remarked = new ArrayList<Rating>();
		
		for(Consultation c: consultations){
			if(c.getState().equals(ConsultationState.CD)){
				cancel.add(new Rating(null, c.getReasonCancel(), 0));
			}else if(c.getState().equals(ConsultationState.RS)){
				remarked.add(new Rating(null, c.getReasonCancel(), 0));
			}
		}
		
		model.addAttribute("format", "pdf");
		model.addAttribute("dateBegin", dateBegin);
		model.addAttribute("dateEnd", dateEnd);
		model.addAttribute("service", serviceReport.getService());
		model.addAttribute("professional", serviceReport.getProfessional());
		model.addAttribute("total", serviceReport.getTotal());
		model.addAttribute("scheduled", serviceReport.getScheduled());
		model.addAttribute("unscheduled", serviceReport.getRescheduled());
		model.addAttribute("canceled", serviceReport.getCanceled());
		model.addAttribute("byMonth", serviceReport.getByMonth());
		model.addAttribute("SubReportByServiceLocation", "./ByService_subreport.jasper");
		
		model.addAttribute("reasonsCancel", cancel);
		model.addAttribute("reasonsRemarked", remarked);
		model.addAttribute("datasource", new JREmptyDataSource());

		return "byservice";
	 }	
	
	@RequestMapping("/relatorio/avaliacao")
	 public String gerarRelatorioAvaliacao(Model model, 
			 HttpSession session, 
			 @DateTimeFormat(pattern="dd/MM/yyyy") Date dateBegin, 
			 @DateTimeFormat(pattern="dd/MM/yyyy") Date dateEnd){ 
		
		dateBegin.setHours(0);
		dateEnd.setHours(23);
		
		List<Rating> ratings = new ArrayList<Rating>();
		
		Professional professional = ((Professional)session.getAttribute(Constants.USER_SESSION));
		
		Long professionalId = professional.getId();
		
		RatingReport ratingReport = reportDAO.getRatingReport(professionalId, dateBegin, dateEnd, null, null);
		ratings = ratingReport.getRatings();
		
		//Pegando as consultas
		ServiceReport serviceReport = reportDAO.getServiceReport(professional.getSocialService().getId(), professionalId, dateBegin, dateEnd, null, null);
		
		model.addAttribute("format", "pdf");
		model.addAttribute("dateBegin", dateBegin);
		model.addAttribute("name", professional.getName());
		model.addAttribute("dateEnd", dateEnd);
		model.addAttribute("average", ratingReport.getAverage());
		model.addAttribute("ratings", ratings);
		model.addAttribute("canceled", serviceReport.getCanceled());
		model.addAttribute("scheduled", serviceReport.getScheduled());
		model.addAttribute("remarked", serviceReport.getRescheduled());
		model.addAttribute("total", serviceReport.getTotal());
		model.addAttribute("RatingSubReportLocation", "./Rating_subreport.jasper");
		model.addAttribute("datasource", new JREmptyDataSource());

		return "rating";
	 }	
	
	
	@RequestMapping("/relatorio/avaliacaoMes")
	 public String gerarRelatorioAvaliacao(Model model, HttpSession session, @RequestParam Integer month, @RequestParam Integer year){ 
		
		List<Rating> ratings = new ArrayList<Rating>();
		
		Professional professional = ((Professional)session.getAttribute(Constants.USER_SESSION));
		
		Long professionalId = professional.getId();
		
		RatingReport ratingReport = reportDAO.getRatingReport(professionalId, null, null, month, year);
		ratings = ratingReport.getRatings();
		
		ServiceReport serviceReport = reportDAO.getServiceReport(professional.getSocialService().getId(), professionalId, null, null, month, year);
		
		Calendar c = Calendar.getInstance();
		c.set(year, month-1, 1);
		
		model.addAttribute("format", "pdf");
		model.addAttribute("dateBegin", c.getTime());
		model.addAttribute("name", professional.getName());
		
		if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) c.set(year, month-1, 31);
		else if(month == 2) c.set(year, month-1, 28);
		else c.set(year, month-1, 30);
		
		model.addAttribute("dateEnd", c.getTime());
		model.addAttribute("average", ratingReport.getAverage());
		model.addAttribute("ratings", ratings);
		model.addAttribute("canceled", serviceReport.getCanceled());
		model.addAttribute("scheduled", serviceReport.getScheduled());
		model.addAttribute("remarked", serviceReport.getRescheduled());
		model.addAttribute("total", serviceReport.getTotal());
		model.addAttribute("RatingSubReportLocation", "./Rating_subreport.jasper");
		model.addAttribute("datasource", new JREmptyDataSource());

		return "rating";
	 }
	

	@RequestMapping("/relatorio/geral")
	 public String gerarRelatorioGeral(Model model, HttpSession session, @DateTimeFormat(pattern="dd/MM/yyyy") Date dateBegin, @DateTimeFormat(pattern="dd/MM/yyyy") Date dateEnd, @RequestParam Integer month, @RequestParam Integer year){ 
		
		System.out.println("[GERAL] MONTH: "+month);
		System.out.println("[GERAL] YEAR: "+year);
		
		GeneralReport generalReport = null;
		if(year == null){
			generalReport = reportDAO.getGeneralReport(dateBegin, dateEnd, null, null);
		}else{
			generalReport = reportDAO.getGeneralReport(null, null, month, year);
			Calendar c = Calendar.getInstance();
			c.set(year, month-1, 1);
			dateBegin = c.getTime();
			
			if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) c.set(year, month-1, 31);
			else if(month == 2) c.set(year, month-1, 28);
			else c.set(year, month-1, 30);
			
			dateEnd = c.getTime();
		}
		
		model.addAttribute("format", "pdf");
		model.addAttribute("dateBegin", dateBegin);
		model.addAttribute("dateEnd", dateEnd);
		model.addAttribute("byMonth", generalReport.getByMonth());
		model.addAttribute("GeneralSubReportLocation", "./General_subreport.jasper");
		model.addAttribute("datasource", new JREmptyDataSource());

		return "geral";
	 }
}
