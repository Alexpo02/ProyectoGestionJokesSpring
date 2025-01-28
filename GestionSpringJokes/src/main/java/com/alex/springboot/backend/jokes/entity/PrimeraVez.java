package com.alex.springboot.backend.jokes.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "primera_vez")
public class PrimeraVez implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autogenerar el ID
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "programa", nullable = false)
    private String programa;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    // Relación 1:1 con la entidad Jokes
    @OneToOne
    @JoinColumn(name = "idjoke", referencedColumnName = "id", nullable = true)
    private Jokes joke;
    
    @OneToMany(mappedBy = "primeraVez", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefonos> telefonos;


    // Constructor vacío (obligatorio para JPA)
    public PrimeraVez() {}

    // Constructor con parámetros
    public PrimeraVez(Integer id, String programa, LocalDate fechaEmision, Jokes joke) {
        this.id = id;
        this.programa = programa;
        this.fechaEmision = fechaEmision;
        this.joke = joke;
    }

    public List<Telefonos> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<Telefonos> telefonos) {
		this.telefonos = telefonos;
	}

	// Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Jokes getJoke() {
        return joke;
    }

    public void setJoke(Jokes joke) {
        this.joke = joke;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
