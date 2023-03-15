package com.clinicadental.service;

import com.clinicadental.dao.IDao;
import com.clinicadental.model.Odontologo;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class OdontologoService {

    private IDao<Odontologo> odontologoIDao;
    private static final Logger LOG = Logger.getLogger(OdontologoService.class);
    public OdontologoService() {}

    public OdontologoService(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    public void setOdontologoIDao(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo) throws Exception {
        odontologoIDao.guardar(odontologo);
        return odontologo;
    }
    public void actualizar(Odontologo o) throws SQLException {
        odontologoIDao.actualizar(o);
    }

    public void eliminarOdontologo(Integer id) throws SQLException {

        odontologoIDao.eliminar(id);
    }
    public Odontologo buscar(Integer id) throws Exception {
            return odontologoIDao.buscar(id);
    }

    public List<Odontologo> buscarTodos() throws Exception {
        return odontologoIDao.buscarTodos();
    }

}
