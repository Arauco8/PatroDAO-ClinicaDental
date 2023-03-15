package com.clinicadental;


import com.clinicadental.dao.impl.ConfigJDBC;
import com.clinicadental.dao.impl.OdontologoIDAOH2;
import com.clinicadental.model.Odontologo;
import com.clinicadental.service.OdontologoService;

public class Main {

    public static void main(String[] args) throws Exception {
        ConfigJDBC.createDB();

        Odontologo odontologo = new Odontologo("Maximiliano Emanuel", "Leiva", "895");
        Odontologo odontologo1 = new Odontologo("Facundo Gabriel", "Leiva", "528");

        OdontologoService odontologoService = new OdontologoService();
        odontologoService.setOdontologoIDao(new OdontologoIDAOH2());
        System.out.println(odontologoService.guardarOdontologo(odontologo));
        System.out.println(odontologoService.guardarOdontologo(odontologo1));
        System.out.println("----------------------------------------------------------------");
        System.out.println(odontologoService.buscar(1)+"\n");
        System.out.println("----------------------------------------------------------------");
        System.out.println(odontologoService.buscarTodos()+"\n");
        System.out.println("----------------------------------------------------------------");
        odontologoService.eliminarOdontologo(1);
        System.out.println(odontologoService.buscarTodos()+"\n");

    }
}