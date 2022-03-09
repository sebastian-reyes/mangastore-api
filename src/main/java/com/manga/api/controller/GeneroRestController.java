package com.manga.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manga.api.interfaceService.IGeneroService;
import com.manga.api.model.Genero;

@RestController
@RequestMapping("/genero")
public class GeneroRestController {

	@Autowired
	private IGeneroService service;

	@GetMapping("/{nombre_genero}")
	public ResponseEntity<?> buscarGenero(@PathVariable String nombre_genero) {
		Genero genero = null;
		Map<String, Object> response = new HashMap<>();
		try {
			genero = service.findByNombre(nombre_genero);
			if (genero != null) {
				return new ResponseEntity<>(genero, HttpStatus.OK);
			} else {
				response.put("mensaje", "El género no fue encontrado");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
