package br.ufc.petsi.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Frequency;
import br.ufc.petsi.model.FrequencyList;
import br.ufc.petsi.model.Group;
import br.ufc.petsi.model.Patient;

public class Teste {

	public static void main(String[] args) {
		
		/*Patient p = new Patient("1", "A", "@", null, null);
		Patient p2 = new Patient("2", "B", "@", null, null);
		Patient p3 = new Patient("3", "C", "@", null, null);
		
		List<Patient> list = new ArrayList();
		list.add(p);
		list.add(p2);
		list.add(p3);
		
		Group g = new Group();
		g.setPatients(list);
		g.setTitle("Teste");
		
		String teste = "{\"title\":\"Teste\",\"patients\":[{\"id\":0,\"cpf\":\"1\",\"name\":\"A\",\"email\":\"@\"},{\"id\":0,\"cpf\":\"2\",\"name\":\"B\",\"email\":\"@\"},{\"id\":0,\"cpf\":\"3\",\"name\":\"C\",\"email\":\"@\"}],\"openGroup\":false}";
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(g));
		System.out.println(gson.fromJson(teste, Group.class).getTitle());*/
		
		
		/*List<String> emls = new ArrayList<String>();
		emls.add("a");
		emls.add("b");
		emls.add("c");
		
		String[] emails = new String[emls.size()];
		
		for (int i = 0; i < emls.size(); i++) {
			emails[i] = emls.get(i);
		}
		
		for (String string : emails) {
			System.out.println(string);, \"consultation\":{\"id\":67}, \"consultation\":{\"id\":67}
		}*/
		
		String teste = "{\"frequencyList\":[{\"group\":{\"id\":71,},\"patient\":{\"id\":60},\"presence\":true,\"consultation\":{\"id\":67}},{\"group\":{\"id\":71},\"patient\":{\"id\":57},\"presence\":false,\"consultation\":{\"id\":67}}]}";
		String teste2 = "{\"frequencyList\":[{\"id\":null,\"group\":{\"id\":71,\"title\":null,\"patients\":null,\"openGroup\":true,\"patientLimit\":0,\"listConsultations\":null},\"patient\":{\"id\":60},\"presence\":true,\"consultation\":{\"id\":67}},{\"id\":null,\"group\":{\"id\":71,\"title\":null,\"patients\":null,\"openGroup\":true,\"patientLimit\":0,\"listConsultations\":null},\"patient\":{\"id\":57},\"presence\":false,\"consultation\":{\"id\":67}}]}";
		
		
		List<Frequency> freq = new ArrayList<Frequency>();
		Frequency f1 = new Frequency();
		Group g1 = new Group();
		g1.setId(1L);
		Patient p1 = new Patient();
		p1.setId(1L);
		Consultation c1 = new Consultation();
		c1.setId(1L);
		f1.setGroup(g1);
		f1.setPatient(p1);
		f1.setConsultation(c1);
		f1.setPresence(true);
		
		Frequency f2 = new Frequency();
		Group g2 = new Group();
		g2.setId(2L);
		Patient p2 = new Patient();
		p2.setId(2L);
		Consultation c2 = new Consultation();
		c2.setId(2L);
		f2.setGroup(g2);
		f2.setPatient(p2);
		f2.setConsultation(c2);
		f2.setPresence(false);
		
		freq.add(f1);
		freq.add(f2);
		
		ObjectMapper mapper = new ObjectMapper();
		try{
			FrequencyList frequencyList = mapper.readValue(teste2, FrequencyList.class);
			System.out.println(frequencyList);
			for(Frequency f: frequencyList.getFrequencyList()){
				System.out.println("[CONSULTATION]: "+f.getConsultation().getId());
				System.out.println("[PATIENT]: "+f.getPatient().getId());
				System.out.println("[GROUP]: "+f.getGroup().getId());
				System.out.println("[PRESENCE]: "+f.isPresence());
			}
			//System.out.println(mapper.writeValueAsString(freq));
		}catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}

}
