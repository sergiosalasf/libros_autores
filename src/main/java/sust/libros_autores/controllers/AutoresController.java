package sust.libros_autores.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sust.libros_autores.models.Autor;
import sust.libros_autores.models.Libro;
import sust.libros_autores.services.AutorDao;

@Controller
public class AutoresController {

  @Autowired
  AutorDao dao;

  @GetMapping(value = "/autores")
  public ModelAndView pantallaAutores() {
    ModelAndView vista = new ModelAndView("autores.html");

    List<Autor> autores = dao.getAll();
    System.out.println(autores);
    vista.addObject("autores", autores);

    return vista;
  }

  @PostMapping("/autores")
  public String crearAutor(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String notas) {
    System.out.println(nombre);
    System.out.println(notas);
    dao.create(nombre, apellido, notas);
    return "redirect:/autores";
  }

  @GetMapping("/autores/borrar/{id}")
  public String borrarAutor(@PathVariable int id) {
    // 1. Borramos al autor
    dao.delete(id);
    // 2. Redirigimos a la vista
    return "redirect:/autores";
  }
}
