package sust.libros_autores.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import sust.libros_autores.models.Autor;

@Component
public class AutorDao {

  @Autowired
  JdbcTemplate tpl;

  public List<Autor> getAll() {
    return tpl.query("select * from autores", new AutorMapper());
  }

  public void create(String nombre, String apellido, String notas) {
    String consulta = "insert into autores (nombre, apellido, notas) values (?, ?, ?)";
    tpl.update(consulta, nombre, apellido, notas);
  }

  public void delete(int id) {
    String consulta = "delete from autores where id=?";
    tpl.update(consulta, id);
  }

}
