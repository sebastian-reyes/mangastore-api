package com.manga.api.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.activation.FileTypeMap;

import com.manga.api.interfaceService.IVolumenService;
import com.manga.api.model.Volumen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/volumen")
public class VolumenRestController {

	@Autowired
	private IVolumenService service;

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarManga(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		Volumen volumen = null;
		try {
			volumen = service.buscarVolumen(id);
			if (volumen != null) {
				return new ResponseEntity<>(volumen, HttpStatus.OK);
			} else {
				response.put("mensaje", "El volumen no se encontró en la base de datos.");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/foto/{id}")
	public ResponseEntity<?> mostrarFotoVolumen(@PathVariable Integer id) throws IOException {
		Volumen volumen = null;
		String nombre_foto = null;
		Map<String, Object> response = new HashMap<>();
		try {
			volumen = service.buscarVolumen(id);
			if (volumen != null) {
				nombre_foto = volumen.getFoto();
				if (nombre_foto != null) {
					File img = new File("src/main/resources/static/fotos/volumenes/" + nombre_foto);
					return ResponseEntity.ok()
							.contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img)))
							.body(Files.readAllBytes(img.toPath()));
				} else {
					response.put("mensaje", "El volumen que seleccionó no cuenta con foto.");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
				}
			} else {
				response.put("mensaje", "El volumen no se encontró en la base de datos.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ALMACEN" })
	@PostMapping("/registro")
	public ResponseEntity<?> registrararVolumen(@RequestBody Volumen vol) {
		Map<String, Object> response = new HashMap<>();
		Volumen nuevoVolumen = null;
		try {
			nuevoVolumen = service.guardarVolumen(vol);
			response.put("volumen", nuevoVolumen);
			response.put("mensaje", "El producto fue agregado correctamente.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro a la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ALMACEN" })
	@PostMapping("/registro/foto")
	public ResponseEntity<?> subirFotoManga(@RequestParam("foto") MultipartFile foto, @RequestParam("id") Integer id) {
		Volumen vol = service.buscarVolumen(id);
		Map<String, Object> response = new HashMap<>();
		if (!foto.isEmpty()) {
			String nombre_foto = foto.getOriginalFilename().replace(" ", "");
			Path ruta_foto = Paths.get("src\\main\\resources\\static\\fotos\\volumenes").resolve(nombre_foto)
					.toAbsolutePath();
			try {
				Files.copy(foto.getInputStream(), ruta_foto);
			} catch (Exception e) {
				response.put("mensaje", "Error al subir la imagen");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			String nombre_foto_anterior = vol.getFoto();
			if (nombre_foto_anterior != null && nombre_foto_anterior.length() > 0) {
				Path ruta_foto_anterior = Paths.get("src\\main\\resources\\static\\fotos\\volumenes")
						.resolve(nombre_foto_anterior).toAbsolutePath();
				File archivo_foto_anterior = ruta_foto_anterior.toFile();
				if (archivo_foto_anterior.exists() && archivo_foto_anterior.canRead()) {
					archivo_foto_anterior.delete();
				}
			}
			vol.setFoto(nombre_foto);
			service.guardarVolumen(vol);
			response.put("mensaje", "Ha subido correctamente la imagen " + nombre_foto);
			 return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} else {
			response.put("mensaje", "El campo foto no puede estar vacío");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
