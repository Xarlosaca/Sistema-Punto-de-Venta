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
public class TIntereses_clientes {

    private int Id;
    private String FechaInicial;
    private Double Deuda;
    private Double Mensual;
    private Double Intereses;
    private String Fecha;
    private Boolean Cancelado;
    private int IdCliente;

    public TIntereses_clientes() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getFechaInicial() {
        return FechaInicial;
    }

    public void setFechaInicial(String FechaInicial) {
        this.FechaInicial = FechaInicial;
    }

    public Double getDeuda() {
        return Deuda;
    }

    public void setDeuda(Double Deuda) {
        this.Deuda = Deuda;
    }

    public Double getMensual() {
        return Mensual;
    }

    public void setMensual(Double Mensual) {
        this.Mensual = Mensual;
    }

    public Double getIntereses() {
        return Intereses;
    }

    public void setIntereses(Double Intereses) {
        this.Intereses = Intereses;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public Boolean getCancelado() {
        return Cancelado;
    }

    public void setCancelado(Boolean Cancelado) {
        this.Cancelado = Cancelado;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int IdCliente) {
        this.IdCliente = IdCliente;
    }

   

}
