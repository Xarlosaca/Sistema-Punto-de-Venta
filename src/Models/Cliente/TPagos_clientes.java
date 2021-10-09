/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Cliente;

/**
 *
 * @author ASUS
 */
public class TPagos_clientes {

    public int Id;
    public int IdCliente;
    public double Deuda;
    public double Saldo;
    public double Pago;
    public double Cambio;
    public String Fecha;
    public String FechaLimite;
    public String Ticket;
    public int IdUsuario;
    public String Usuario;

    public TPagos_clientes() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int IdCliente) {
        this.IdCliente = IdCliente;
    }

    public double getDeuda() {
        return Deuda;
    }

    public void setDeuda(double Deuda) {
        this.Deuda = Deuda;
    }

    public double getSaldo() {
        return Saldo;
    }

    public void setSaldo(double Saldo) {
        this.Saldo = Saldo;
    }

    public double getPago() {
        return Pago;
    }

    public void setPago(double Pago) {
        this.Pago = Pago;
    }

    public double getCambio() {
        return Cambio;
    }

    public void setCambio(double Cambio) {
        this.Cambio = Cambio;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public String getFechaLimite() {
        return FechaLimite;
    }

    public void setFechaLimite(String FechaLimite) {
        this.FechaLimite = FechaLimite;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String Ticket) {
        this.Ticket = Ticket;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

}
