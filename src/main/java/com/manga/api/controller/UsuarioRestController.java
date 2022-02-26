package com.manga.api.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.FileTypeMap;

import com.manga.api.interfaceService.IUsuarioService;
import com.manga.api.model.Rol;
import com.manga.api.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = { "*" })
public class UsuarioRestController {

    @Autowired
    private IUsuarioService service;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Secured({ "ROLE_ADMIN" })
    @GetMapping("/page/{page}")
    public Page<Usuario> listarUsuarios(@PathVariable Integer page) {
        return service.paginacionUsuarios(PageRequest.of(page, 15));
    }

    @PostMapping("/registro/cliente")
    public ResponseEntity<?> registrarCliente(@RequestBody Usuario u) {
        Usuario nuevoUsuario = null;
        List<Rol> roles = new ArrayList<>();
        roles.add(new Rol(2, "ROLE_USER", null));
        String password = passwordEncoder.encode(u.getPassword());
        Map<String, Object> response = new HashMap<>();
        try {
            u.setPassword(password);
            u.setRoles(roles);
            nuevoUsuario = service.guardarUsuario(u);
            response.put("usuario", nuevoUsuario);
            response.put("mensaje", "El usuario fue registrado correctamente");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el registro a la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Integer id) {
        Usuario usuario = null;
        Map<String, Object> response = new HashMap<>();
        try {
            usuario = service.buscarUsuario(id);
            if (usuario != null) {
                return new ResponseEntity<>(usuario, HttpStatus.OK);
            } else {
                response.put("mensaje", "El usuario no se encontró en la base de datos.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta a la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/foto/{id}")
    public ResponseEntity<?> obtenerFotoUsuario(@PathVariable Integer id) throws IOException {
        Usuario usuario = null;
        String nombre_foto = null;
        Map<String, Object> response = new HashMap<>();
        try {
            usuario = service.buscarUsuario(id);
            if (usuario != null) {
                nombre_foto = usuario.getFoto();
                if (nombre_foto != null) {
                    File img = new File("src/main/resources/static/fotos/usuarios/" + nombre_foto);
                    return ResponseEntity.ok()
                            .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img)))
                            .body(Files.readAllBytes(img.toPath()));
                } else {
                    response.put("mensaje", "El usuario que seleccionó no cuenta con foto.");
                    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
                }
            } else {
                response.put("mensaje", "El usuario no se encontró en la base de datos.");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta a la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
