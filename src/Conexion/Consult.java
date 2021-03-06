/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Models.Cliente.*;
import Models.TConfiguration;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 *
 * @author ASUS
 */
public class Consult extends Conexion {

    private QueryRunner QR = new QueryRunner();

    public List<TClientes> clientes() {
        List<TClientes> cliente = new ArrayList();
        try {
            cliente = (List<TClientes>) QR.query((Connection) getConn(), "SELECT * FROM tclientes",
                    new BeanListHandler(TClientes.class));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error aqui: " + ex);
        }
        return cliente;
    }

    public List<TReportes_clientes> reportesClientes(int idCliente) {
        String where = " WHERE tclientes.ID =" + idCliente;
        List<TReportes_clientes> reportes = new ArrayList();
        String condicion1 = " tclientes.ID = treportes_clientes.IdCliente ";
        String condicion2 = " tclientes.ID = treportes_intereses_clientes.IdCliente ";

        String campos = " tclientes.ID,tclientes.Cedula,tclientes.Nombre,tclientes.Apellido,"
                + "treportes_clientes.idReporte,treportes_clientes.DeudaActual,"
                + "treportes_clientes.FechaDeuda,treportes_clientes.UltimoPago,"
                + "treportes_clientes.FechaPago, treportes_clientes.Ticket,"
                + "treportes_clientes.Deuda,treportes_clientes.Mensual,treportes_clientes.Cambio,"
                + "treportes_clientes.FechaLimite,treportes_intereses_clientes.Intereses,"
                + "treportes_intereses_clientes.Pago,treportes_intereses_clientes.Cambio,"
                + "treportes_intereses_clientes.Cuotas,treportes_intereses_clientes.InteresFecha,"
                + "treportes_intereses_clientes.TicketIntereses";
        try {
            reportes = (List<TReportes_clientes>) QR.query(getConn(),
                    "SELECT" + campos + " FROM tclientes Inner Join treportes_clientes ON"
                    + condicion1 + "Inner Join treportes_intereses_clientes ON" + condicion2 + where,
                    new BeanListHandler(TReportes_clientes.class));
        } catch (SQLException ex) {
            System.out.println("Error : " + ex);
        }
        return reportes;
    }

    public List<TConfiguration> config() {
        List<TConfiguration> config = new ArrayList();

        try {
            config = (List<TConfiguration>) QR.query((Connection) getConn(), "SELECT * FROM tconfiguration",
                    new BeanListHandler(TConfiguration.class));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex);
        }
        return config;
    }

    public List<TIntereses_clientes> InteresesClientes() {
        List<TIntereses_clientes> intereses = new ArrayList();
        try {
            intereses = (List<TIntereses_clientes>) QR.query(getConn(), "SELECT *FROM tintereses_clientes",
                    new BeanListHandler(TIntereses_clientes.class));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }
        return intereses;
    }

    public List<TReportes_clientes> ReporteCliente() {
        List<TReportes_clientes> reporte = new ArrayList();
        try {
            reporte = (List<TReportes_clientes>) QR.query(getConn(), "SELECT * FROM treportes_clientes",
                    new BeanListHandler(TReportes_clientes.class));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }
        return reporte;
    }
}
