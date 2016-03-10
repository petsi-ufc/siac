package br.ufc.petsi.session;

import javax.servlet.http.HttpSession;

public class CurrentSession {
	private static HttpSession session;

	public static HttpSession getSession() {
		return session;
	}

	public static void setSession(HttpSession session) {
		CurrentSession.session = session;
	}
	
}
