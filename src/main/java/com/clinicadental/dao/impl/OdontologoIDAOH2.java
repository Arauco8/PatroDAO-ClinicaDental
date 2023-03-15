package com.clinicadental.dao.impl;

import com.clinicadental.dao.IDao;
import com.clinicadental.model.Odontologo;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OdontologoIDAOH2 implements IDao<Odontologo> {

    private static final Logger LOG = Logger.getLogger(OdontologoIDAOH2.class);
    private final List<String> CAMPOS = List.of("matricula", "apellido","nombre");

    @Override
    public Odontologo guardar(Odontologo odontologo) throws SQLException {
        LOG.info("Processing save data Odontologo");
        try{
            PreparedStatement psGuardar = ConfigJDBC.getPreparedStatement(Objects.requireNonNull(ConfigJDBC.getConnection()),
                    StatementGenerator.getStatement("guardar", "odontologos", CAMPOS),
                    PreparedStatement.RETURN_GENERATED_KEYS);
            assert psGuardar != null;
            psGuardar.setString(1, odontologo.getMatricula());
            psGuardar.setString(2, odontologo.getApellido());
            psGuardar.setString(3, odontologo.getNombre());
            psGuardar.execute();

            ResultSet rs = psGuardar.getGeneratedKeys();
            if(rs.next()) {
                odontologo.setId(rs.getInt(1));
            }
            LOG.info("Finished execution of method 'guardar(Odontologo odontologo)' successfully");
        }catch(Exception e){
            LOG.error("Failed to save Odontologo", e);
            throw new SQLException("Failed to save Odontologo");
        }finally {
            ConfigJDBC.closeConnection();
        }
        return odontologo;
    }

    @Override
    public Odontologo buscar(Integer id) throws Exception {
        LOG.info("Processing the search");
        Odontologo odontologo = null;
        try{
            PreparedStatement psBuscar = ConfigJDBC.getPreparedStatement(Objects.requireNonNull(ConfigJDBC.getConnection()),
                    StatementGenerator.getStatement("selectWhereId", "odontologos", CAMPOS));
            assert psBuscar != null;
            psBuscar.setInt(1, id);
            ResultSet result = psBuscar.executeQuery();

            while (result.next()) {
                Integer idOdontologo = result.getInt("id");
                String matricula = result.getString("matricula");
                String apellido = result.getString("apellido");
                String nombre = result.getString("nombre");
                odontologo = new Odontologo(idOdontologo, matricula, apellido,nombre);
            }
            LOG.debug("Finished execution of method 'buscar(Integer id)' successfully");
        }catch(Exception e){
            LOG.error("Failed to search Odontologo", e);
        }finally {
            ConfigJDBC.closeConnection();
        }
        return odontologo;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        LOG.info("Processing the queryAll");
        List<Odontologo> odontologos = new ArrayList<>();
        try{
            PreparedStatement psBuscarTodos = ConfigJDBC.getPreparedStatement(Objects.requireNonNull(ConfigJDBC.getConnection()),
                    StatementGenerator.getStatement("selectAll", "odontologos", CAMPOS));
            assert psBuscarTodos != null;
            ResultSet result = psBuscarTodos.executeQuery();

            while (result.next()) {
                Integer idOdontologo = result.getInt("id");
                String matricula = result.getString("matricula");
                String apellido = result.getString("apellido");
                String nombre = result.getString("nombre");
                odontologos.add(new Odontologo(idOdontologo, matricula, apellido, nombre));
            }
            LOG.debug("Finished execution of method 'buscarTodos()' successfully");
        }catch(Exception e){
            LOG.error("Failed to search List of Odontologos", e);
        }finally {
            ConfigJDBC.closeConnection();
        }
        return odontologos;
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        LOG.info("Processing delete data Odontologo");
        try{
            PreparedStatement psEliminar = ConfigJDBC.getPreparedStatement(Objects.requireNonNull(ConfigJDBC.getConnection()),
                    StatementGenerator.getStatement("eliminar", "odontologos", CAMPOS));
            assert psEliminar != null;
            psEliminar.setInt(1, id);

            psEliminar.executeUpdate();
            LOG.debug("Finished execution of method 'eliminar(Long id)' successfully");
        }catch(Exception e){
            LOG.error("Failed to delete Odontologo", e);
            throw new SQLException("Failed to delete Odontologo");
        }finally {
            ConfigJDBC.closeConnection();
        }
    }

    @Override
    public void actualizar(Odontologo odontologo) throws SQLException {
        LOG.info("Processing delete data Odontologo");
        try{
            PreparedStatement psActualizar = ConfigJDBC.getPreparedStatement(Objects.requireNonNull(ConfigJDBC.getConnection()),
                    StatementGenerator.getStatement("update", "odontologos", CAMPOS));
            assert psActualizar != null;
            psActualizar.setString(1, odontologo.getMatricula());
            psActualizar.setString(2, odontologo.getApellido());
            psActualizar.setString(3, odontologo.getNombre());
            psActualizar.setInt(4, odontologo.getId());

            psActualizar.executeUpdate();
            LOG.debug("Finished execution of method Update successfully");
        }catch(Exception e){
            LOG.error("Failed to Updated Odontologo", e);
            throw new SQLException("Failed to Updated Odontologo");
        }finally {
            ConfigJDBC.closeConnection();
        }
    }
}
