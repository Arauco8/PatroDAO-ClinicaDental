package com.clinicadental.dao.impl;

import com.clinicadental.dao.IDao;
import com.clinicadental.model.Domicilio;
import com.clinicadental.model.Paciente;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PacienteIDAOH2 implements IDao<Paciente> {

    private static final Logger LOG = Logger.getLogger(PacienteIDAOH2.class);
    private final List<String> CAMPOS = List.of("documento", "apellido","nombre","fechaIngreso","domicilioId");
    private DomicilioIDAOH2 domicilioIDAOH2 = new DomicilioIDAOH2();
    @Override
    public Paciente guardar(Paciente paciente) throws SQLException {
        LOG.info("Processing save data Paciente");
        try{
            //Comenzamos a Trabajar con el Domicilio
            // Guardo el domicilio que está dentro del objeto paciente que inserte
            Domicilio domicilio = paciente.getDomicilio();
            //Creo un objeto de la clase DomicilioDAOH2 para poder usar el metodo guardar,
            //de esta manera, el domicilio se guarda en la base de datos y me devuelve el id
            //que se le asigno. Entonces yo no creo el ID, sino que es autogenerado por el metodo
            //Guardar de la clase DomicilioDAOH2
            DomicilioIDAOH2 domicilioIDAOH2 = new DomicilioIDAOH2();
            domicilio = domicilioIDAOH2.guardar(domicilio);
            // paciente.getDomicilio().setId(domicilio.getId()); establece el ID del objeto Domicilio
            // asociado a paciente con el ID obtenido del objeto domicilio. Esto puede ser útil,
            // por ejemplo, cuando se está actualizando información de un paciente y su domicilio.
            paciente.getDomicilio().setId(domicilio.getId());


            PreparedStatement psGuardar = ConfigJDBC.getPreparedStatement(Objects.requireNonNull(ConfigJDBC.getConnection()),
                    StatementGenerator.getStatement("guardar", "pacientes", CAMPOS),
                    PreparedStatement.RETURN_GENERATED_KEYS);
            assert psGuardar != null;
            psGuardar.setString(1, paciente.getDocumento());
            psGuardar.setString(2, paciente.getApellido());
            psGuardar.setString(3, paciente.getNombre());
            psGuardar.setDate(4, Date.valueOf(paciente.getFechaIngreso()));

            //Ya tengo el id del domicilio, lo seteo en el objeto paciente que voy a guardar.
            psGuardar.setInt(5, paciente.getDomicilio().getId());

            //Ejecuto la consulta SQL
            psGuardar.execute();

            //Obtengo el id generado
            ResultSet rs = psGuardar.getGeneratedKeys();
            if(rs.next()) {
                paciente.setId(rs.getInt(1));
            }
            LOG.info("Finished execution of method save Paciente successfully");
        }catch(Exception e){
            LOG.error("Failed to save Paciente", e);
            throw new SQLException("Failed to save Paciente");
        }finally {
            ConfigJDBC.closeConnection();
        }
        return paciente;
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        LOG.info("Processing delete data Paciente");
        try{
            PreparedStatement psEliminar = ConfigJDBC.getPreparedStatement(Objects.requireNonNull(ConfigJDBC.getConnection()),
                    StatementGenerator.getStatement("eliminar", "pacientes", CAMPOS));
            assert psEliminar != null;
            psEliminar.setInt(1, id);

            psEliminar.executeUpdate();
            LOG.debug("Finished execution of method delete Paciente successfully");
        }catch(Exception e){
            LOG.error("Failed to delete Paciente", e);
            throw new SQLException("Failed to delete Paciente");
        }finally {
            ConfigJDBC.closeConnection();
        }
    }

    @Override
    public void actualizar(Paciente paciente) throws SQLException {
        LOG.info("Processing delete data Paciente");
        try{
            DomicilioIDAOH2 domicilioIDAOH2 = new DomicilioIDAOH2();
            domicilioIDAOH2.actualizar(paciente.getDomicilio());

            PreparedStatement psActualizar = ConfigJDBC.getPreparedStatement(Objects.requireNonNull(ConfigJDBC.getConnection()),
                    StatementGenerator.getStatement("update", "pacientes", CAMPOS));
            assert psActualizar != null;
            psActualizar.setString(1, paciente.getDocumento());
            psActualizar.setString(2, paciente.getApellido());
            psActualizar.setString(3, paciente.getNombre());
            psActualizar.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            psActualizar.setInt(5, paciente.getDomicilio().getId());

            DomicilioIDAOH2 domicilioDaoH2 = new DomicilioIDAOH2();
            domicilioDaoH2.actualizar(paciente.getDomicilio());

            psActualizar.setInt(6, paciente.getId());

            psActualizar.executeUpdate();
            LOG.debug("Finished execution of method Update successfully");
        }catch(Exception e){
            LOG.error("Failed to Updated Paciente", e);
            throw new SQLException("Failed to Updated Paciente");
        }finally {
            ConfigJDBC.closeConnection();
        }
    }

    @Override
    public Paciente buscar(Integer id) throws Exception {
        LOG.info("Processing the search");
        Paciente paciente = null;
        try{
            PreparedStatement psBuscar = ConfigJDBC.getPreparedStatement(ConfigJDBC.getConnection(),
                    StatementGenerator.getStatement("selectWhereId", "pacientes", CAMPOS));
            assert psBuscar != null;
            psBuscar.setInt(1, id);
            ResultSet result = psBuscar.executeQuery();

            while (result.next()) {
                Integer idPaciente = result.getInt("id");
                String documento = result.getString("documento");
                String apellido = result.getString("apellido");
                String nombre = result.getString("nombre");
                LocalDate fechaIngreso = result.getDate("fechaIngreso").toLocalDate();
                int idDomicilio = result.getInt("domicilioId");
                Domicilio domicilio = domicilioIDAOH2.buscar(idDomicilio);
                paciente = new Paciente(idPaciente, documento, apellido,nombre,fechaIngreso,domicilio);
            }
            LOG.debug("Finished execution of method 'buscar(Integer id)' successfully");
        }catch(Exception e){
            LOG.error("Failed to search Odontologo", e);
        }finally {
            ConfigJDBC.closeConnection();
        }
        return paciente;
    }

    @Override
    public List<Paciente> buscarTodos() {
        LOG.info("Processing the queryAll");
        List<Paciente> paciente = new ArrayList<>();
        try{
            PreparedStatement psBuscarTodos = ConfigJDBC.getPreparedStatement(Objects.requireNonNull(ConfigJDBC.getConnection()),
                    StatementGenerator.getStatement("selectAll", "pacientes", CAMPOS));
            assert psBuscarTodos != null;
            ResultSet result = psBuscarTodos.executeQuery();

            while (result.next()) {
                Integer idPaciente = result.getInt("id");
                String documento = result.getString("documento");
                String apellido = result.getString("apellido");
                String nombre = result.getString("nombre");
                LocalDate fechaIngreso = result.getDate("fechaIngreso").toLocalDate();
                int idDomicilio = result.getInt("domicilioId");
                Domicilio domicilio = domicilioIDAOH2.buscar(idDomicilio);
                //Con el domicilio_id traemos el domicilio de la tabla domicilio a traves de DAO de Domicilios
                paciente.add(new Paciente(idPaciente, documento, apellido,nombre,fechaIngreso,domicilio));
            }
            LOG.debug("Finished execution of method 'buscarTodos()' successfully");
        }catch(Exception e){
            LOG.error("Failed to search List of Paciente", e);
        }finally {
            ConfigJDBC.closeConnection();
        }
        return paciente;
    }
}
