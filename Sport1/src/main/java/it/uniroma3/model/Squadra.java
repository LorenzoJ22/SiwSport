package it.uniroma3.model;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
public class Squadra {
	@Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	
	private String nome;
	private Date anno;
	private String indirizzo;
	
	@OneToMany(mappedBy= "squadra",orphanRemoval = true)
	private List<Giocatore> giocatori;

	
	
	@OneToOne(cascade = CascadeType.DETACH)
	private Presidente presidente;
	
	public List<Giocatore> getGiocatori() {
		return giocatori;
	}
	
	public void setGiocatori(List<Giocatore> giocatori) {
		this.giocatori = giocatori;
	}	
	
	public Presidente getPresidente() {
		return presidente;
	}

	public void setPresidente(Presidente presidente) {
		this.presidente = presidente;
	}

	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo= indirizzo;
	}
	public Date getAnno() {
		return anno;
	}
	public void setAnno(Date anno) {
		this.anno= anno;
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
	
	
	@Override
	public int hashCode() {
		return Objects.hash(anno, id, indirizzo, nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Squadra other = (Squadra) obj;
		return Objects.equals(anno, other.anno) && Objects.equals(id, other.id)
				&& Objects.equals(indirizzo, other.indirizzo) && Objects.equals(nome, other.nome) && Objects.equals(presidente, other.presidente);
	}
}
