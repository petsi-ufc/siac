package br.ufc.petsi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufc.petsi.dao.ReportDAO;
import br.ufc.petsi.model.DetailByMonth;
import br.ufc.petsi.model.GeneralReport;
import br.ufc.petsi.model.Rating;
import br.ufc.petsi.model.RatingReport;
import br.ufc.petsi.model.ServiceReport;

@Controller
public class ReportController {
	
	@Inject
	private ReportDAO reportDAO;
	
	@RequestMapping("/relatorio/servico")
	 public String gerarRelatorioPorServico(Model model, HttpSession session){ 
		
		
		List<DetailByMonth> detail = new ArrayList<DetailByMonth>();
		
		
		// ****** modificar o recebimento das variáveis *****************
		int serviceId = 1;
		int professionalId = 1;
		Date dateBegin = new Date();
		Date dateEnd = new Date();
		//*******************
		
		
		ServiceReport serviceReport = reportDAO.getServiceReport(serviceId, professionalId, dateBegin, dateEnd);

		model.addAttribute("format", "pdf");
		model.addAttribute("dateBegin", dateBegin);
		model.addAttribute("dateEnd", dateEnd);
		model.addAttribute("service", serviceReport.getService());
		model.addAttribute("professional", serviceReport.getProfessional());
		model.addAttribute("total", serviceReport.getTotal());
		model.addAttribute("scheduled", serviceReport.getScheduled());
		model.addAttribute("unscheduled", serviceReport.getUnscheduled());
		model.addAttribute("canceled", serviceReport.getCanceled());
		model.addAttribute("byMonth", serviceReport.getByMonth());
		model.addAttribute("SubReportByServiceLocation", "./ByService_subreport.jasper");
		model.addAttribute("datasource", new JREmptyDataSource());

		return "byservice";
	 }	
	
	@RequestMapping("/relatorio/avaliacao")
	 public String gerarRelatorioAvaliacao(Model model, HttpSession session){ 
		
		List<Rating> ratings = new ArrayList<Rating>();
		
		// ****** modificar o recebimento das variáveis *****************
		@SuppressWarnings("deprecation")
		Date dateBegin = new Date(2015, 8, 8);
		@SuppressWarnings("deprecation")
		Date dateEnd = new Date(2015, 10, 10);
		int profissionalId = 1;
		//*******************
		
		RatingReport ratingReport = reportDAO.getRatingReport(profissionalId, dateBegin, dateEnd);
		ratings = ratingReport.getRatings();
		
		model.addAttribute("format", "pdf");
		model.addAttribute("dateBegin", dateBegin);
		model.addAttribute("dateEnd", dateEnd);
		model.addAttribute("average", ratingReport.getAverage());
		model.addAttribute("ratings", ratings);
		model.addAttribute("RatingSubReportLocation", "./Rating_subreport.jasper");
		model.addAttribute("datasource", new JREmptyDataSource());

		return "rating";
	 }	

	@RequestMapping("/relatorio/geral")
	 public String gerarRelatorioGeral(Model model, HttpSession session){ 

		List<DetailByMonth> d = new ArrayList<DetailByMonth>();
		
		// ****** modificar o recebimento das variáveis *****************
		Date dateBegin = new Date();
		Date dateEnd = new Date();
		//*******************
		
		GeneralReport generalReport = reportDAO.getGeneralReport(dateBegin, dateEnd);
		
		model.addAttribute("format", "pdf");
		model.addAttribute("dateBegin", dateBegin);
		model.addAttribute("dateEnd", dateEnd);
		model.addAttribute("byMonth", generalReport.getByMonth());
		model.addAttribute("GeneralSubReportLocation", "./General_subreport.jasper");
		model.addAttribute("datasource", new JREmptyDataSource());

		return "geral";
	 }
}
