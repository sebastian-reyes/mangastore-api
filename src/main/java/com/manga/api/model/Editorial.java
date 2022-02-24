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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "editoriales")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Editorial implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_editorial;
    
    @Column(length = 50, unique = true)
    private String nombre_editorial;
    
    @Column(length = 50, unique= true)
    private String pais;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "editorial", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler","editorial"})
    private List<Manga> mangas;

    /**
	* 
	*/
	private static final long serialVersionUID = 1L;
}
