package br.ufc.petsi.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import br.ufc.petsi.model.Group;
import br.ufc.petsi.model.Patient;

public class Teste {

	public static void main(String[] args) {
		
		Patient p = new Patient("1", "A", "@", null, null);
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
		System.out.println(gson.fromJson(teste, Group.class).getTitle());

	}

}
