package sust.libros_autores.services;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sust.libros_autores.models.Autor;

public class AutorMapper implements RowMapper {
  @Override
  public Object mapRow(ResultSet rs, int numfilas) throws SQLException {
    Autor a = new Autor(
        rs.getInt("id"),
        rs.getString("nombre"),
        rs.getString("apellido"),
        rs.getString("notas"));
    return a;
  }
}
