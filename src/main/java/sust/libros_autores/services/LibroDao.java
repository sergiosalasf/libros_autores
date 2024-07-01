package sust.libros_autores.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sust.libros_autores.models.Autor;
import sust.libros_autores.models.Libro;

@Component
public class LibroDao {

  @Autowired
  JdbcTemplate tpl;

  public ArrayList<Libro> getAll() throws SQLException {
    // 1. Pedimos una conexión al objeto JdbcTemplate
    Connection conn = tpl.getDataSource().getConnection();
    // 2. Creamos una consulta y la ejecutamos
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("select * from libros");
    // 3. Creamos una lista de libros vacía
    ArrayList<Libro> libros = new ArrayList<Libro>();
    // 4. Vamos llenando la lista con los registros de la respuesta SQL
    while (rs.next()) {
      libros.add(new Libro(
          rs.getInt("id"),
          rs.getString("titulo"),
          rs.getString("descripcion")));
    }
    return libros;
  }

  public Libro getById(int id) throws SQLException {
    // 1. Pedimos una conexión al objeto JdbcTemplate
    Connection conn = tpl.getDataSource().getConnection();
    // 2. Creamos una consulta y la ejecutamos
    PreparedStatement stmt = conn.prepareStatement(
        "select * from libros where id=?");
    stmt.setInt(1, id);
    ResultSet rs = stmt.executeQuery();
    // 3. Retornamos un objeto del tipo Libro
    rs.next();
    return new Libro(
        rs.getInt("id"),
        rs.getString("titulo"),
        rs.getString("descripcion"));
  }

  public void create(String titulo, String descripcion) {
    String consulta = "insert into libros (titulo, descripcion) values (?, ?)";
    tpl.update(consulta, titulo, descripcion);
  }

  public void addAutor(int libro_id, int autor_id) {
    String consulta = "insert into libroautor (libro_id, autor_id) values (?, ?)";
    tpl.update(consulta, libro_id, autor_id);
  }

  public void delete(int id) {
    String consulta = "delete from libros where id=?";
    tpl.update(consulta, id);
  }

  public ArrayList<Autor> getAutoresNoRelacionados(int libro_id) throws SQLException {
    // 1. Pedimos una conexión al objeto JdbcTemplate
    Connection conn = tpl.getDataSource().getConnection();
    // 2. Creamos una consulta y la ejecutamos
    PreparedStatement stmt = conn.prepareStatement(
        "select * from autores where id not in (select autores.id from autores join libroautor on autores.id = libroautor.autor_id where libro_id = ?)");
    stmt.setInt(1, libro_id);
    ResultSet rs = stmt.executeQuery();

    // 3. Creamos el arraylist y lo vamos llenando
    ArrayList<Autor> noRelacionados = new ArrayList<Autor>();
    while (rs.next()) {
      noRelacionados.add(new Autor(
          rs.getInt("id"), rs.getString("nombre"),
          rs.getString("apellido"), rs.getString("notas")));
    }
    return noRelacionados;
  }

  public ArrayList<Autor> getAutoresSiRelacionados(int libro_id) throws SQLException {
    // 1. Pedimos una conexión al objeto JdbcTemplate
    Connection conn = tpl.getDataSource().getConnection();
    // 2. Creamos una consulta y la ejecutamos
    PreparedStatement stmt = conn.prepareStatement(
        "select * from autores join libroautor on autores.id = libroautor.autor_id where libro_id = ?");
    stmt.setInt(1, libro_id);
    ResultSet rs = stmt.executeQuery();

    // 3. Creamos el arraylist y lo vamos llenando
    ArrayList<Autor> siRelacionados = new ArrayList<Autor>();
    while (rs.next()) {
      siRelacionados.add(new Autor(
          rs.getInt("id"), rs.getString("nombre"),
          rs.getString("apellido"), rs.getString("notas")));
    }
    return siRelacionados;
  }
}
