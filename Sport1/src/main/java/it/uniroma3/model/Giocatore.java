package it.uniroma3.model;

import java.sql.Date;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
public class Giocatore {
	@Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	
	private String nome;
	private String cognome;
	private Date data;
	private String luogoDiNascita;
	private String ruolo;
	private Date dataInizio;
	private Date dataFine;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "squadra_id")
	private Squadra squadra;
	
	public Squadra getSquadra() {
		return squadra;
	}
	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}
	public String getLuogoDiNascita() {
		return luogoDiNascita;
	}
	public void setLuogoDiNascita(String luogoDiNascita) {
		this.luogoDiNascita = luogoDiNascita;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Date getDataFine() {
		return dataFine;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	@Override
	public int hashCode() {
		return Objects.hash(squadra,cognome, data, dataFine, dataInizio, id, luogoDiNascita, nome, ruolo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		return Objects.equals(squadra, other.squadra) &&Objects.equals(cognome, other.cognome) && Objects.equals(data, other.data)
				&& Objects.equals(dataFine, other.dataFine) && Objects.equals(dataInizio, other.dataInizio)
				&& Objects.equals(id, other.id) && Objects.equals(luogoDiNascita, other.luogoDiNascita)
				 && Objects.equals(nome, other.nome)
				&& Objects.equals(ruolo, other.ruolo);
	}
	
	
	
}
