package com.manga.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "volumenes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Volumen implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_volumen;

    @Column(length = 70)
    private String nombre_volumen;

    @Lob
    private String descripcion;
    
    private Integer n_tomo;
    
    @Column(unique = true, length = 75)
    private String ISBN;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer stock_act;

    @Column(nullable = false)
    private Integer stock_min;

    @Column(unique = true)
    private String foto;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_manga")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "volumenes","id_manga"})
    private Manga manga;

    /**
	* 
	*/
	private static final long serialVersionUID = 1L;
}
