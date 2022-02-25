package com.manga.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mangas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manga implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_manga;

    @Column(length = 60, unique = true)
    private String nombre_manga;

    @Column(unique = true)
    private String sinopsis;

    @Column(unique = true)
    private String foto;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_autor")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "mangas", "id_autor"})
    private Autor autor;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_genero")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "mangas", "id_genero", "descripcion"})
    private Genero genero;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_editorial")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "mangas", "id_editorial"})
    private Editorial editorial;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manga", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler","manga"})
    public List<Volumen> volumenes;

    /**
	* 
	*/
	private static final long serialVersionUID = 1L;
}
