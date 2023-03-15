package com.clinicadental.service;

import com.clinicadental.dao.IDao;
import com.clinicadental.model.Paciente;

import java.sql.SQLException;
import java.util.List;

public class PacienteService {
    private IDao<Paciente> pacienteIDao;

    public PacienteService(IDao<Paciente> pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }

    public Paciente guardar(Paciente p) throws SQLException {
        return pacienteIDao.guardar(p);
    }
    public Paciente buscar(int id) throws Exception {
        return pacienteIDao.buscar(id);
    }
    public List<Paciente> buscarTodos(){
        return pacienteIDao.buscarTodos();
    }
    public void eliminar(int id) throws SQLException {
        pacienteIDao.eliminar(id);
    }

    public void actualizar(Paciente p) throws SQLException {
        pacienteIDao.actualizar(p);
    }
}
