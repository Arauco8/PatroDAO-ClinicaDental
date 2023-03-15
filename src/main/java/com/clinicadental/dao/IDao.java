package com.clinicadental.dao;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {
    public T guardar(T t) throws SQLException;
    public void eliminar(Integer id) throws SQLException;

    public void actualizar(T t) throws SQLException;
    public T buscar(Integer id) throws Exception;
    public List<T> buscarTodos();

}
