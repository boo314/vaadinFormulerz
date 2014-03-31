package com.example.vaadindemo.model;

public class Person {

	private String imie;
	private String email;
	private Integer rokUrodzenia;

	public Person() {
		imie = "";
		email = "";
		rokUrodzenia = 0;
	}

	public Person(String imie, String email, Integer rokUrodzenia) {
		super();
		this.imie = imie;
		this.email = email;
		this.rokUrodzenia = rokUrodzenia;
	}

	@Override
	public String toString() {
		return "Person [imie=" + imie + ", nazwisko=" + email
				+ ", rokUrodzenia=" + rokUrodzenia + "]";
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRokUrodzenia() {
		return rokUrodzenia;
	}

	public void setRokUrodzenia(Integer rokUrodzenia) {
		this.rokUrodzenia = rokUrodzenia;
	}
}
