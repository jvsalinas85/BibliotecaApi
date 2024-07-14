package com.example.bibliotecaApi.services;

import com.example.bibliotecaApi.entities.Libro;
import com.example.bibliotecaApi.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> findAll(){
        return libroRepository.findAll();
    }

    public Optional<Libro> findById(Long id){
        return libroRepository.findById(id);
    }

    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    public void deleteById(Long id) {
        libroRepository.deleteById(id);
    }

    public List<Libro> findByAutorId(Long idAutor){
        return findAll().stream() //obtenemos todos los libros
                .filter(libro -> libro.getAutor().getId().equals(idAutor)) //por cada libro de la lista obtenemos el autor, obtenemos el Id y lo comparamos con el idAutor de parámetro
                .collect(Collectors.toList()); //el resultado lo pasamos a lista y es lo que se retorna al final
    }

    public List<String> findDistinctCategories() { //encontramos todos los libros, obtenemos categorias de cada uno, eliminamos duplicados y transformamos en lista
        return libroRepository.findAll()
                .stream()
                .map(Libro::getCategoria)
                .distinct()
                .toList();
    }

    public List<Libro> findByCategoria(String categoria) {
        return libroRepository.findByCategoria(categoria);
    }
}
