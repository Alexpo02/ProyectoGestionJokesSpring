package com.alex.springboot.backend.jokes.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "telefonos")
public class Telefonos {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autogenerado
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "numero", nullable = false, length = 15)
    private String numero;

    // Relación M:1 con PrimeraVez
    @ManyToOne
    @JoinColumn(name = "idprimeravez", referencedColumnName = "id", nullable = false)
    private PrimeraVez primeraVez;  // Cada teléfono está asociado a una "PrimeraVez"

    // Constructor vacío (obligatorio para JPA)
    public Telefonos() {}

    // Constructor con parámetros
    public Telefonos(Integer id, String numero, PrimeraVez primeraVez) {
        this.id = id;
        this.numero = numero;
        this.primeraVez = primeraVez;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public PrimeraVez getPrimeraVez() {
        return primeraVez;
    }

    public void setPrimeraVez(PrimeraVez primeraVez) {
        this.primeraVez = primeraVez;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
