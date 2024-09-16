package it.uniroma3.model;

import java.sql.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Presidente {
	@Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	private String codiceFiscale;
	private String nome;
	private String cognome;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate nascita;
	private String luogoDiNascita;
	
	@OneToOne(cascade = CascadeType.DETACH)
	private Squadra squadra;
	
	@OneToOne
	private User useri;
	
	
	public User getUseri() {
		return useri;
	}
	public void setUseri(User useri) {
		this.useri = useri;
	}
	public Squadra getSquadra() {
		return squadra;
	}
	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public LocalDate getNascita() {
		return nascita;
	}
	public void setNascita(LocalDate nascita) {
		this.nascita = nascita;
	}
	public String getLuogoDiNascita() {
		return luogoDiNascita;
	}
	public void setLuogoDiNascita(String luogoDiNascita) {
		this.luogoDiNascita = luogoDiNascita;
	}
	@Override
	public int hashCode() {
		return Objects.hash(useri,codiceFiscale, cognome, id, luogoDiNascita, nascita, nome, squadra);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Presidente other = (Presidente) obj;
		return Objects.equals(useri, other.useri)&&Objects.equals(codiceFiscale, other.codiceFiscale)&&Objects.equals(squadra, other.squadra) && Objects.equals(cognome, other.cognome)
				&& Objects.equals(id, other.id) && Objects.equals(luogoDiNascita, other.luogoDiNascita)
				&& Objects.equals(nascita, other.nascita) && Objects.equals(nome, other.nome);
	}
	
	
	
}
