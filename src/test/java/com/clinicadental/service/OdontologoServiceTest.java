package com.clinicadental.service;

import com.clinicadental.dao.IDao;
import com.clinicadental.dao.impl.OdontologoIDAOH2;
import com.clinicadental.dao.impl.PacienteIDAOH2;
import com.clinicadental.model.Odontologo;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;
import java.util.List;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(JUnit4.class)
public class OdontologoServiceTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(OdontologoServiceTest.class);
    private static OdontologoService odontologoService = new OdontologoService(new OdontologoIDAOH2());

    @BeforeClass
    public static void cargaDataSet() throws Exception {
        Odontologo odonto = odontologoService.guardarOdontologo(new Odontologo("Perez", "Juan", "M-135"));
        Odontologo odonto1 = odontologoService.guardarOdontologo(new Odontologo("Caupolican", "Lautaro", "UHU-512"));
    }

    @Test
    public void testGuardar() throws Exception {
        Odontologo odonto = odontologoService.guardarOdontologo(new Odontologo("Jose Francisco", "de San Martin", "BQI-533"));

        Assert.assertNotNull(odontologoService.buscar(odonto.getId()));

    }

    @Test
    public void testEliminar() throws Exception {
        odontologoService.eliminarOdontologo(3);

        Assert.assertTrue(odontologoService.buscar(3) == null);
    }

    @Test
    public void testBuscarTodos() throws Exception {
        List<Odontologo> odontologos = odontologoService.buscarTodos();

        Assert.assertTrue(!odontologos.isEmpty());
        Assert.assertTrue(odontologos.size() > 0);
        LOG.info(odontologos);
    }

    @Test
    public void testActualizar() throws Exception {
        List<Odontologo> odontologos = odontologoService.buscarTodos();
        LOG.info(odontologos);
        Odontologo odonto = odontologoService.buscar(2);

        odonto.setMatricula("M-999");
        odonto.setNombre("JuanActualizado");
        odonto.setApellido("PerezActualizado");
        odontologoService.actualizar(odonto);

        Odontologo odonto1 = odontologoService.buscar(2);

        assertEquals(odonto.getMatricula(), odonto1.getMatricula());
        assertEquals(odonto.getNombre(), odonto1.getNombre());
        assertEquals(odonto.getApellido(), odonto1.getApellido());
    }
}