package com.clinicadental.dao.impl;

import com.clinicadental.dao.IDao;
import com.clinicadental.model.Domicilio;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DomicilioIDAOH2 implements IDao<Domicilio> {

    private static final Logger LOG = Logger.getLogger(DomicilioIDAOH2.class);
    private final List<String> CAMPOS = List.of("calle","numero","localidad","provincia");
    @Override
    public Domicilio guardar(Domicilio domicilio) throws SQLException {
        LOG.info("Processing save data Domicilio");
        try{
            //1 Levantar el driver y Conectarnos
            //2 Crear una sentencia especificando que el ID lo auto incrementa la base de datos y que nos devuelva esa Key es decir ID
            PreparedStatement psGuardar = ConfigJDBC.getPreparedStatement(Objects.requireNonNull(ConfigJDBC.getConnection()),
                    StatementGenerator.getStatement("guardar","domicilios",CAMPOS),
                    PreparedStatement.RETURN_GENERATED_KEYS);
            assert psGuardar != null;
            //No le vamos a pasar el ID ya que hicimos que fuera autoincremental en la base de datos
            //preparedStatement.setInt(1,domicilio.getId());
            psGuardar.setString(1, domicilio.getCalle());
            psGuardar.setString(2, domicilio.getNumero());
            psGuardar.setString(3, domicilio.getLocalidad());
            psGuardar.setString(4, domicilio.getProvincia());
            //3 Ejecutar una sentencia SQL y obtener los ID que se autogeneraron en la base de datos
            psGuardar.executeUpdate();

            ResultSet rs = psGuardar.getGeneratedKeys();
            if(rs.next()){
                domicilio.setId(rs.getInt(1));
            }
            LOG.info("Finished execution of method 'guardar(Domicilio domicilio)' successfully");
        }catch(Exception e){
            LOG.error("Failed to saved Domicilio", e);
            throw new SQLException("Failed to save Domicilio");
        }finally{
            ConfigJDBC.closeConnection();
        }
        return domicilio;
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        LOG.info("Processing delete data Domilicio");
        try {
            PreparedStatement psEliminar = ConfigJDBC.getPreparedStatement(Objects.requireNonNull(ConfigJDBC.getConnection()),
                    StatementGenerator.getStatement("eliminar", "domicilios", CAMPOS));
            assert psEliminar != null;
            psEliminar.setInt(1, id);
            psEliminar.execute();
            LOG.info("Finished executing of method 'Eliminar' successfully");
        }catch(Exception e){
            LOG.error("Failed to delete Domicilio", e);
            throw new SQLException("Failed to delete Domicilio");
        }finally{
            ConfigJDBC.closeConnection();
        }

    }

    @Override
    public void actualizar(Domicilio domicilio) throws SQLException {
        LOG.info("Processing delete data Odontologo");
        try{
            PreparedStatement psActualizar = ConfigJDBC.getPreparedStatement(Objects.requireNonNull(ConfigJDBC.getConnection()),
                    StatementGenerator.getStatement("update", "domicilios", CAMPOS));
            assert psActualizar != null;
            psActualizar.setString(1, domicilio.getCalle());
            psActualizar.setString(2, domicilio.getNumero());
            psActualizar.setString(3, domicilio.getLocalidad());
            psActualizar.setString(4, domicilio.getProvincia());
            psActualizar.setInt(5, domicilio.getId());

            psActualizar.executeUpdate();
            LOG.debug("Finished execution of method Update successfully");
        }catch(Exception e){
            LOG.error("Failed to Update Domicilio", e);
            throw new SQLException("Failed to Update Domicilio");
        }finally {
            ConfigJDBC.closeConnection();
        }
    }

    @Override
    public Domicilio buscar(Integer id) throws Exception {
        LOG.info("Processing the search");
        Domicilio domicilio = null;
        try{
            PreparedStatement psBuscar = ConfigJDBC.getPreparedStatement(Objects.requireNonNull(ConfigJDBC.getConnection()),
                    StatementGenerator.getStatement("selectWhereId", "domicilios", CAMPOS));
            assert psBuscar != null;
            psBuscar.setInt(1, id);
            ResultSet result = psBuscar.executeQuery();

            while (result.next()) {
                Integer idDomicilio = result.getInt("id");
                String calle = result.getString("calle");
                String numero = result.getString("numero");
                String localidad = result.getString("localidad");
                String provincia = result.getString("provincia");

                domicilio = new Domicilio(idDomicilio, calle, numero,localidad,provincia);
            }
            LOG.debug("Finished execution of method 'buscar(Long id)' successfully");
        }catch(Exception e){
            LOG.error("Failed to search Domicilio", e);
        }finally {
            ConfigJDBC.closeConnection();
        }
        return domicilio;
    }

    @Override
    public List<Domicilio> buscarTodos() {
        LOG.info("Processing the queryAll");
        List<Domicilio> domicilios = new ArrayList<>();
        try{
            PreparedStatement psBuscarTodos = ConfigJDBC.getPreparedStatement(Objects.requireNonNull(ConfigJDBC.getConnection()),
                    StatementGenerator.getStatement("selectAll", "domicilios", CAMPOS));
            assert psBuscarTodos != null;
            ResultSet result = psBuscarTodos.executeQuery();

            while (result.next()) {
                Integer idDomicilio = result.getInt("id");
                String calle = result.getString("calle");
                String numero = result.getString("numero");
                String localidad = result.getString("localidad");
                String provincia = result.getString("provincia");

                domicilios.add(new Domicilio(idDomicilio, calle, numero,localidad,provincia));
            }
            LOG.debug("Finished execution of method 'buscarTodos()' successfully");
        }catch(Exception e){
            LOG.error("Failed to search List of Odontologos", e);
        }finally {
            ConfigJDBC.closeConnection();
        }
        return  domicilios;
    }
}
