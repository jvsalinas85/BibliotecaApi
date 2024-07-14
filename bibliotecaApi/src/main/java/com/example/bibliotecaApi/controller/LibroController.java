package com.example.bibliotecaApi.controller;

import com.example.bibliotecaApi.entities.Libro;
import com.example.bibliotecaApi.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/api/libros")
@RestController
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public List<Libro> getAllLibros(){
        return libroService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibroById(@PathVariable Long id){
        return libroService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Libro crearLibro(@RequestBody Libro libro){
        return libroService.save(libro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id){
        libroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //actualizar libro
    @PutMapping("/{id}")
    public ResponseEntity<Libro> updateLibro(@PathVariable Long id, @RequestBody Libro libro){
        return libroService.findById(id)
                .map(existingLibro -> {
                    libro.setId(existingLibro.getId()); //si existe el libro que estamos buscando, actualizamos el id del libro encontrado con el id del libro existente
                    return ResponseEntity.ok(libroService.save(libro)); //guarde el libro
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/autor/{idAutor}")
    public List<Libro> getLibrosByAutorId(@PathVariable Long idAutor){
        return libroService.findByAutorId(idAutor);
    }

    @GetMapping("/categorias")
    public List<String> getCategorias(){
        return libroService.findDistinctCategories();
    }

    @GetMapping("/categorias/{categoria}")
    public List<Libro> getLibrosByCategoria(@PathVariable String categoria){
        return libroService.findByCategoria(categoria);
    }

}