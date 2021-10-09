/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Conexion.Consult;
import Library.*;
import Models.Cliente.*;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

/**
 *
 * @author ASUS
 */
public class ClientesVM extends Consult {

    private String _accion = "insert", _mony;
    private final ArrayList<JLabel> _label;
    private final ArrayList<JTextField> _textField;
    private final JCheckBox _ChB_Credito;
    private final JTable _tableCliente, _tableReporte;
    private DefaultTableModel modelo1, modelo2;
    private final JSpinner _spinnerPaginas;
    private JRadioButton _radioCuotas, _radioInteres;
    private int idCliente = 0;
    private int reg_porPagina = 10;
    private int _numPagina = 1;
    public int seccion;
    private final FormatDecimal _format;
    private Paginador<TClientes> _paginadorClientes;
    private Paginador<TClientes> _paginadorReportes;
    private final Codigos _codigos;

    public ClientesVM(Object[] objects, ArrayList<JLabel> label, ArrayList<JTextField> textField) throws SQLException {
        _label = label;
        _textField = textField;
        _ChB_Credito = (JCheckBox) objects[0];
        _tableCliente = (JTable) objects[1];
        _spinnerPaginas = (JSpinner) objects[2];
        _tableReporte = (JTable) objects[3];
        _radioCuotas = (JRadioButton) objects[4];
        _radioInteres = (JRadioButton) objects[5];
        _format = new FormatDecimal();
        _mony = ConfigurationVM.Mony;
        _codigos = new Codigos();
        restablecer();
        RestablecerReport();
        //reportesClientes(7);
        //new Conexion();
    }

    // <editor-fold defaultstate="collapsed" desc="CODIGO REGISTRAR CLIENTE">
    public void RegistrarCliente() {
        if (_textField.get(0).getText().equals("")) {
            _label.get(0).setText("Ingrese la Cedula");
            _label.get(0).setForeground(Color.RED);
            _textField.get(0).requestFocus();
        } else {
            if (_textField.get(1).getText().equals("")) {
                _label.get(1).setText("Ingrese el Nombre");
                _label.get(1).setForeground(Color.RED);
                _textField.get(1).requestFocus();
            } else {
                if (_textField.get(2).getText().equals("")) {
                    _label.get(2).setText("Ingrese El Apellido");
                    _label.get(2).setForeground(Color.RED);
                    _textField.get(2).requestFocus();
                } else {
                    if (_textField.get(3).getText().equals("")) {
                        _label.get(3).setText("Ingrese el Email");
                        _label.get(3).setForeground(Color.RED);
                        _textField.get(3).requestFocus();
                    } else {
                        if (!Objetos.eventos.isEmail(_textField.get(3).getText())) {
                            _label.get(3).setText("Ingrese un Email valido");
                            _label.get(3).setForeground(Color.RED);
                            _textField.get(3).requestFocus();
                        } else {
                            if (_textField.get(4).getText().equals("")) {
                                _label.get(4).setText("Ingrese el Telefono");
                                _label.get(4).setForeground(Color.RED);
                                _textField.get(4).requestFocus();
                            } else {
                                if (_textField.get(5).getText().equals("")) {
                                    _label.get(5).setText("Ingrese la Dirección");
                                    _label.get(5).setForeground(Color.RED);
                                    _textField.get(5).requestFocus();
                                } else {
                                    int count;
                                    List<TClientes> listEmail = clientes().stream()
                                            .filter(u -> u.getEmail().equals(_textField.get(3).getText()))
                                            .collect(Collectors.toList());
                                    count = listEmail.size();

                                    List<TClientes> listIdCliente = clientes().stream()
                                            .filter(u -> u.getCedula().equals(_textField.get(0).getText()))
                                            .collect(Collectors.toList());
                                    count += listIdCliente.size();
                                    try {
                                        switch (_accion) {
                                            case "insert":

                                                if (count == 0) {
                                                    SaveData();
                                                } else {
                                                    if (!listEmail.isEmpty()) {
                                                        _label.get(3).setText("El Email ya esa registrado");
                                                        _label.get(3).setForeground(Color.RED);
                                                        _textField.get(3).requestFocus();
                                                    }
                                                    if (!listIdCliente.isEmpty()) {
                                                        _label.get(0).setText("La cedula ya esta registrada");
                                                        _label.get(0).setForeground(Color.RED);
                                                        _textField.get(0).requestFocus();
                                                    }
                                                }

                                                break;
                                            case "update":
                                                if (count == 2) {
                                                    if (listEmail.get(0).getID() == idCliente
                                                            && listIdCliente.get(0).getID() == idCliente) {
                                                        SaveData();
                                                    } else {
                                                        if (listIdCliente.get(0).getID() != idCliente) {
                                                            _label.get(0).setText("La Cedula ya esta registrada");
                                                            _label.get(0).setForeground(Color.RED);
                                                            _textField.get(0).requestFocus();
                                                        }
                                                        if (listEmail.get(0).getID() != idCliente) {
                                                            _label.get(3).setText("La Email ya esta registrado");
                                                            _label.get(3).setForeground(Color.RED);
                                                            _textField.get(3).requestFocus();
                                                        }
                                                    }
                                                } else {
                                                    if (count == 0) {
                                                        SaveData();
                                                    } else {
                                                        if (!listIdCliente.isEmpty()) {
                                                            if (listIdCliente.get(0).getID() == idCliente) {
                                                                SaveData();
                                                            } else {
                                                                _label.get(0).setText("La Cedula ya esta registrada");
                                                                _label.get(0).setForeground(Color.RED);
                                                                _textField.get(0).requestFocus();
                                                            }
                                                        }
                                                        if (!listEmail.isEmpty()) {
                                                            if (listEmail.get(0).getID() == idCliente) {
                                                                SaveData();
                                                            } else {
                                                                _label.get(3).setText("EL email ya esta registrada");
                                                                _label.get(3).setForeground(Color.RED);
                                                                _textField.get(3).requestFocus();
                                                            }
                                                        }
                                                    }
                                                }
                                                break;
                                        }
                                    } catch (SQLException ex) {
                                        JOptionPane.showMessageDialog(null, ex);
                                    }

                                }
                            }
                        }

                    }
                }
            }
        }
    }

    private void SaveData() throws SQLException {
        try {
            final QueryRunner qr = new QueryRunner(true);
            getConn().setAutoCommit(false);
            byte[] image = UploadImage.getImageByte();
            if (image == null) {
                image = Objetos.uploadImage.getTransFoto(_label.get(6));
            }
            switch (_accion) {
                case "insert":
                    String sqlCliente1 = "INSERT INTO tclientes(Cedula,Nombre, Apellido,Email,"
                            + "Telefono,Direccion,Credito,Fecha,Imagen) VALUES (?,?,?,?,?,?,?,?,?)";
                    Object[] dataCliente1 = {
                        _textField.get(0).getText(),
                        _textField.get(1).getText(),
                        _textField.get(2).getText(),
                        _textField.get(3).getText(),
                        _textField.get(4).getText(),
                        _textField.get(5).getText(),
                        _ChB_Credito.isSelected(),
                        new Calendario().getFecha(),
                        image,};
                    qr.insert(getConn(), sqlCliente1, new ColumnListHandler(), dataCliente1);
                    String sqlReport = "INSERT INTO treportes_clientes(Deuda,Mensual,Cambio,"
                            + "DeudaActual,FechaDeuda,UltimoPago,FechaPago,Ticket,FechaLimite,IdCliente)"
                            + " VALUES(?,?,?,?,?,?,?,?,?,?)";
                    List<TClientes> cliente = clientes();
                    Object[] dataReport = {
                        500.00,
                        0,
                        0,
                        0,
                        "--/--/--",
                        0,
                        "--/--/--",
                        "0000000000",
                        "--/--/--",
                        cliente.get(cliente.size() - 1).getID(),};
                    qr.insert(getConn(), sqlReport, new ColumnListHandler(), dataReport);
                    String sqlReportInteres = "INSERT INTO treportes_intereses_clientes "
                            + "(Intereses,Pago,Cambio,Cuotas,InteresFecha,TicketIntereses,"
                            + "IdCliente) VALUES (?,?,?,?,?,?,?)";
                    Object[] dataReportInteres = {
                        0,
                        0,
                        0,
                        0,
                        "--/--/--",
                        "000000000",
                        cliente.get(cliente.size() - 1).getID(),};
                    qr.insert(getConn(), sqlReportInteres, new ColumnListHandler(), dataReportInteres);
                    break;
                case "update":
                    Object[] dataCliente2 = {
                        _textField.get(0).getText(),
                        _textField.get(1).getText(),
                        _textField.get(2).getText(),
                        _textField.get(3).getText(),
                        _textField.get(4).getText(),
                        _textField.get(5).getText(),
                        _ChB_Credito.isSelected(),
                        //new Calendario().getFecha(),
                        image,};
                    String sqlCliente2 = "UPDATE tclientes SET Cedula = ?,Nombre = ?, Apellido=?,"
                            + "Email = ?, Telefono = ?, Direccion = ?, Credito = ?,"
                            + "Imagen = ? WHERE ID =" + idCliente;
                    qr.update(getConn(), sqlCliente2, dataCliente2);
                    break;
            }

            getConn().commit();
            restablecer();
            JOptionPane.showConfirmDialog(null, "Datos Ingresados Correctamente", "CORRECTO", JOptionPane.DEFAULT_OPTION);
        } catch (SQLException ex) {
            getConn().rollback();
            JOptionPane.showMessageDialog(null, ex);
            //System.out.println("Eroor de inserccion aqui ahora");
        }

    }

    public void SearchClientes(String campo) {
        List<TClientes> clientesFilter;
        String[] titulos = {"id", "Nid", "Nombre", "Apellido", "Email", "Direccion", "Telefono", "Credito", "Image"};
        modelo1 = new DefaultTableModel(null, titulos);
        int inicio = (_numPagina - 1) * reg_porPagina;
        if (campo.equals("")) {
            clientesFilter = clientes().stream()
                    .skip(inicio).limit(reg_porPagina)
                    .collect(Collectors.toList());
        } else {
            clientesFilter = clientes().stream()
                    .filter(C -> C.getCedula().startsWith(campo)
                    || C.getNombre().startsWith(campo)
                    || C.getApellido().startsWith(campo))
                    .skip(inicio).limit(reg_porPagina)
                    .collect(Collectors.toList());
        }
        if (!clientesFilter.isEmpty()) {
            clientesFilter.forEach(item -> {
                Object[] Registros = {
                    item.getID(),
                    item.getCedula(),
                    item.getNombre(),
                    item.getApellido(),
                    item.getEmail(),
                    item.getDireccion(),
                    item.getTelefono(),
                    item.isCredito(),
                    item.getImagen(),};
                modelo1.addRow(Registros);
            });
        }
        _tableCliente.setModel(modelo1);
        _tableCliente.setRowHeight(30);
        _tableCliente.getColumnModel().getColumn(0).setMaxWidth(0);
        _tableCliente.getColumnModel().getColumn(0).setMinWidth(0);
        _tableCliente.getColumnModel().getColumn(0).setPreferredWidth(0);
        _tableCliente.getColumnModel().getColumn(8).setMaxWidth(0);
        _tableCliente.getColumnModel().getColumn(8).setMaxWidth(0);
        _tableCliente.getColumnModel().getColumn(8).setPreferredWidth(0);
        _tableCliente.getColumnModel().getColumn(7).setCellRenderer(new Render_CheckBox());

    }

    public void GetCliente() {
        _accion = "update";
        int filas = _tableCliente.getSelectedRow();
        idCliente = (Integer) modelo1.getValueAt(filas, 0);
        _textField.get(0).setText((String) modelo1.getValueAt(filas, 1));
        _textField.get(1).setText((String) modelo1.getValueAt(filas, 2));
        _textField.get(2).setText((String) modelo1.getValueAt(filas, 3));
        _textField.get(3).setText((String) modelo1.getValueAt(filas, 4));
        _textField.get(4).setText((String) modelo1.getValueAt(filas, 5));
        _textField.get(5).setText((String) modelo1.getValueAt(filas, 6));
        _ChB_Credito.setSelected((Boolean) modelo1.getValueAt(filas, 7));
        Objetos.uploadImage.byteImage(_label.get(6), (byte[]) modelo1.getValueAt(filas, 8));
    }

    public final void restablecer() {
        seccion = 1;
        _accion = "insert";
        _textField.get(0).setText("");
        _textField.get(1).setText("");
        _textField.get(2).setText("");
        _textField.get(3).setText("");
        _textField.get(4).setText("");
        _textField.get(5).setText("");
        _ChB_Credito.setSelected(false);
        _ChB_Credito.setForeground(new Color(102, 102, 102));
        _label.get(0).setText("CI");
        _label.get(0).setForeground(new Color(102, 102, 102));

        _label.get(1).setText("Nombre");
        _label.get(1).setForeground(new Color(102, 102, 102));

        _label.get(2).setText("Apellido");
        _label.get(2).setForeground(new Color(102, 102, 102));

        _label.get(3).setText("Email");
        _label.get(3).setForeground(new Color(102, 102, 102));

        _label.get(4).setText("Telefono");
        _label.get(4).setForeground(new Color(102, 102, 102));

        _label.get(5).setText("Direccion");
        _label.get(5).setForeground(new Color(102, 102, 102));

        _label.get(6).setIcon(new ImageIcon(getClass().getClassLoader().getResource("Resources/logo_persona.png")));
        listClientes = clientes();
        if (!listClientes.isEmpty()) {
            _paginadorClientes = new Paginador<TClientes>(listClientes,
                    _label.get(7), reg_porPagina);
        }
        SpinnerNumberModel model = new SpinnerNumberModel(
                new Integer(10),
                new Integer(1),
                new Integer(100),
                new Integer(1)
        );
        _spinnerPaginas.setModel(model);
        SearchClientes("");
    }

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="CODIGO DE PAGOS Y REPORTES">
    private int _interesCuotas = 0, _idReport, _idClienteReport;
    private Double _intereses = 0.0, _deudaAcutal = 0.0, _interesPago = 0.0;
    private Double _interesesPagos = 0.0, _cambio = 0.0, _interesesCliente = 0.0;
    private Double _pago = 0.0, _mensual = 0.0, _deudaActualCliente = 0.0, _deuda;
    private String _ticketCuota, nameCliente;
    private List<TIntereses_clientes> _listIntereses;

    public void SearchReportes(String valor) {
        List<TClientes> clientesFilter;
        String[] titulos = {"id", "Nid", "Nombre", "Apellido", "Email", "Direccion", "Telefono"};
        modelo2 = new DefaultTableModel(null, titulos);
        int inicio = (_numPagina - 1) * reg_porPagina;
        if (valor.equals("")) {
            clientesFilter = clientes().stream()
                    .skip(inicio).limit(reg_porPagina)
                    .collect(Collectors.toList());
        } else {
            clientesFilter = clientes().stream()
                    .filter(C -> C.getCedula().startsWith(valor)
                    || C.getNombre().startsWith(valor)
                    || C.getApellido().startsWith(valor))
                    .skip(inicio).limit(reg_porPagina)
                    .collect(Collectors.toList());
        }
        if (!clientesFilter.isEmpty()) {
            clientesFilter.forEach(item -> {
                Object[] Registros = {
                    item.getID(),
                    item.getCedula(),
                    item.getNombre(),
                    item.getApellido(),
                    item.getEmail(),
                    item.getDireccion(),
                    item.getTelefono(), //item.isCredito(),
                // item.getImagen(),
                };
                modelo2.addRow(Registros);
            });
        }
        _tableReporte.setModel(modelo2);
        _tableReporte.setRowHeight(30);
        _tableReporte.getColumnModel().getColumn(0).setMaxWidth(0);
        _tableReporte.getColumnModel().getColumn(0).setMinWidth(0);
        _tableReporte.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void GetReportCliente() {
        int filas = _tableReporte.getSelectedRow();
        _idClienteReport = (Integer) modelo2.getValueAt(filas, 0);
        List<TReportes_clientes> clienteFilter = reportesClientes(_idClienteReport);
        if (!clienteFilter.isEmpty()) {
            TReportes_clientes cliente = clienteFilter.get(0);
            _idReport = cliente.getIdReporte();
            nameCliente = cliente.getNombre() + " " + cliente.getApellido();
            _label.get(8).setText(nameCliente);
            _deudaAcutal = (Double) cliente.getDeudaActual();
            _deuda = (Double) cliente.getDeuda();
            //deuda actual
            _label.get(9).setText(_mony + _format.decimal(_deudaAcutal));
            //Ultimo pago
            _label.get(10).setText(_mony + _format.decimal((Double) cliente.getUltimoPago()));
            //Ticket
            _ticketCuota = cliente.getTicket();
            _label.get(11).setText(_ticketCuota);
            //FechaPago
            _label.get(12).setText(cliente.getFechaPago());
            //cuotas por mes
            _label.get(13).setText(_mony + _format.decimal((Double) cliente.getMensual()));

            _listIntereses = InteresesClientes().stream()
                    .filter(u -> u.getIdCliente() == _idClienteReport)
                    .collect(Collectors.toList());
            if (_listIntereses.isEmpty()) {
                _label.get(14).setText(_mony + "0.00");
                _label.get(15).setText("0");
                _label.get(16).setText("0000000000");
                _label.get(17).setText("--/--/--");
            } else {
                _interesCuotas = 0;
                _intereses = 0.0;
                _listIntereses.forEach(item -> {
                    _intereses += item.getIntereses();
                    _interesCuotas++;
                });
                //aqui error
                _label.get(14).setText(_mony + _format.decimal(_intereses));
                _label.get(15).setText(String.valueOf(_interesCuotas));
                _label.get(16).setText(cliente.getTicketIntereses());
                _label.get(17).setText(cliente.getInteresFecha());
            }

        }

    }

    public void Pagos() {
        if (!_textField.get(6).getText().isEmpty()) {
            _label.get(19).setText("Ingrese le pago");
            if (_idReport == 0) {
                _label.get(19).setText("Seleccione un cliente");
            } else {
                if (_radioInteres.isSelected()) {
                    if (!_textField.get(7).getText().isEmpty()) {
                        int cantCuotas = Integer.valueOf(_textField.get(7).getText());
                        if (cantCuotas <= _interesCuotas) {
                            if (!_textField.get(6).getText().isEmpty()) {
                                _interesPago = _format.reconstruir(_textField.get(6).getText());
                                if (_interesPago >= _interesesPagos) {
                                    _cambio = _interesPago - _interesesPagos;
                                    _label.get(19).setText("Cambio para el cliente : " + _mony + _format.decimal(_cambio));
                                    _interesesCliente = _intereses - _interesesPagos;
                                    _label.get(14).setText(_mony + _format.decimal(_interesesCliente));
                                } else {
                                    _label.get(19).setText("El pago debe ser : " + _mony + _format.decimal(_interesesPagos));
                                    _interesesCliente = _intereses - _interesesPagos;
                                    _label.get(14).setText(_mony + _format.decimal(_interesesCliente));
                                }
                            }
                        } else {
                            _label.get(19).setText("Cuotas invalidas");
                        }
                    } else {
                        _label.get(19).setText("Ingrese el numero de cuotas");
                        _textField.get(7).requestFocus();

                    }
                } else if (_radioCuotas.isSelected()) {
                    if (!_textField.get(6).getText().isEmpty()) {
                        _pago = _format.reconstruir(_textField.get(6).getText());
                        TReportes_clientes dataReport = ReporteCliente().stream()
                                .filter(u -> u.getIdReporte() == _idReport)
                                .collect(Collectors.toList()).get(0);
                        _mensual = dataReport.getMensual();
                        if (_pago > _mensual) {
                            if (Objects.equals(_pago, _deudaAcutal) || _pago > _deudaAcutal) {
                                _cambio = _pago - _deudaAcutal;
                                _label.get(19).setText("Cambio para el cliente : " + _mony
                                        + _format.decimal(_cambio));
                                _label.get(9).setText(_mony + "0.00");
                                _deudaAcutal = 0.0;
                            } else {
                                _cambio = _pago - _mensual;
                                _label.get(19).setText("Cambio para el cliente : " + _mony
                                        + _format.decimal(_cambio));
                                _deudaActualCliente = _deudaAcutal - _mensual;
                                _label.get(9).setText(_mony + _format.decimal(_deudaActualCliente));
                            }
                        } else if (Objects.equals(_pago, _mensual)) {
                            _deudaActualCliente = _deudaAcutal - _mensual;
                            _label.get(9).setText(_mony + _format.decimal(_deudaActualCliente));
                        }
                    }
                }
            }
        } else {
            _label.get(19).setText("Ingresar el pago");
            _label.get(9).setText(_mony + _format.decimal(_deudaAcutal));
            _label.get(14).setText(_mony + _format.decimal(_intereses));
        }
    }

    public void CuotasIntereses() {

        if (Objects.equals(_idReport, 0)) {
            _label.get(19).setText("Seleccione un Cliente");
        } else {
            if (_deudaAcutal > 0 || _intereses > 0) {
                _label.get(19).setText("Ingrese el Pago");
                if (null != _textField.get(7)) {
                    if (_textField.get(7).getText().isEmpty()) {
                        _label.get(14).setText(_mony + _format.decimal(_intereses));
                        _label.get(15).setText(String.valueOf(_interesCuotas));
                        _label.get(18).setText(_mony + "0.00");
                        _label.get(19).setText("Ingrese el Pago");
                    } else {
                        _label.get(18).setText(_mony + "0.00");
                        int cantCuotas = Integer.valueOf(_textField.get(7).getText());
                        if (cantCuotas <= _interesCuotas) {
                            _label.get(19).setText("Ingrese el Pago");
                            if (!_listIntereses.isEmpty()) {
                                _interesesPagos = 0.0;
                                for (int i = 0; i < cantCuotas; i++) {
                                    _interesesPagos += _listIntereses.get(i).getIntereses();
                                }
                                int cuotas = _interesCuotas - cantCuotas;
                                double intereses = _intereses - _interesesPagos;
                                _label.get(14).setText(_mony + _format.decimal(intereses));
                                _label.get(15).setText(String.valueOf(cuotas));
                                _label.get(18).setText(_mony + _format.decimal(_interesesPagos));
                            }
                        } else {
                            _label.get(19).setText("Cuotas Invalida");
                            _textField.get(7).requestFocus();
                        }
                    }
                }
                Pagos();
            } else {
                _label.get(19).setText("El cliente no contine deuda");
            }

        }
    }

    public void EjecutarPago() throws SQLException {
        final QueryRunner qr = new QueryRunner(true);
        if (Objects.equals(_idReport, 0)) {
            _label.get(19).setText("Seleccione un cliente");
        } else {
            if (_textField.get(6).getText().isEmpty()) {
                _label.get(19).setText("Ingrese el pago");
                _textField.get(6).requestFocus();
            } else {
                String Fecha = new Calendario().getFecha();
                //relalizar consultas al usuraio que inicia sesion
                if (_radioCuotas.isSelected()) {
                    if (_pago >= _mensual) {
                        try {
                            getConn().setAutoCommit(false);
                            String dateNow = new Calendario().addMes(1);
                            String _fechalimite = Objects.equals(_deudaAcutal, 0) ? "--/--/--" : dateNow;
                            String ticket = _codigos.codesTickets(_ticketCuota);
                            String query1 = "INSERT INTO tpagos_clientes (Deuda,Saldo,Pago,Cambio,"
                                    + "Fecha,FechaLimite,Ticket,IdUsuario,Usuario,IdCliente)VALUES (?,?,?,?,?,?,?,?,?,?)";
                            Object[] data1 = {
                                _deuda, _deudaActualCliente, _pago, _cambio, dateNow, _fechalimite, ticket, 1, "Cfsaca", _idClienteReport
                            };
                            qr.insert(getConn(), query1, new ColumnListHandler(), data1);

                            String query2 = "UPDATE treportes_clientes SET DeudaActual = ?,FechaDeuda=?,"
                                    + "UltimoPago=?,Cambio=?,FechaPago=?,Ticket=?,"
                                    + "FechaLimite=? WHERE IdReporte =" + _idReport;

                            Object[] data2 = {_deudaActualCliente, dateNow, _pago, _cambio, dateNow, ticket, _fechalimite};
                            qr.update(getConn(), query2, data2);

                            Ticket Ticket1 = new Ticket();
                            Ticket1.TextoCentro("Sistema de ventas cfsaca");
                            Ticket1.TextoIzquierda("Direccion");
                            Ticket1.TextoIzquierda("Zamora");
                            Ticket1.TextoIzquierda("Tel 0000000000");
                            Ticket1.LineasGuion();
                            Ticket1.TextoCentro("FACTURA");
                            Ticket1.LineasGuion();
                            Ticket1.TextoIzquierda("Factura: " + ticket);
                            Ticket1.TextoIzquierda("Cliente: " + nameCliente);
                            Ticket1.TextoIzquierda("fecha: " + Fecha);
                            //Usuario
                            //Ticket1.TextoIzquierda("fecha: Usuario" );
                            Ticket1.LineasGuion();
                            Ticket1.TextoCentro("Su credito : " + _mony + _format.decimal(_deuda));
                            Ticket1.TextoExtremo("Cuotas por 12 meses: ", _mony + _format.decimal(_mensual));
                            Ticket1.TextoExtremo("Deuda anterior", _mony + _format.decimal(_deudaAcutal));
                            Ticket1.TextoExtremo("Pago:", _mony + _format.decimal(_pago));
                            Ticket1.TextoExtremo("Cambio:", _mony + _format.decimal(_cambio));
                            Ticket1.TextoExtremo("Deuda actual:", _mony + _format.decimal(_deudaActualCliente));
                            Ticket1.TextoExtremo("Proximo pago:", _fechalimite);
                            Ticket1.TextoCentro("CFSACA");
                            Ticket1.print();
                            getConn().commit();
                            RestablecerReport();
                        } catch (Exception e) {
                            getConn().rollback();
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                } else if (_radioInteres.isSelected()) {

                }
            }
        }
    }

    public void RestablecerReport() {
        _idReport = 0;
        _interesCuotas = 0;
        _intereses = 0.0;
        _interesPago = 0.0;
        _deudaAcutal = 0.0;
        _interesesPagos = 0.0;
        _cambio = 0.0;
        _interesesCliente = 0.0;
        _pago = 0.0;
        _mensual = 0.0;
        _deudaActualCliente = 0.0;
        _ticketCuota = "000000000";
        _label.get(14).setText(_mony + "0.00");
        _label.get(15).setText("0");
        _label.get(16).setText("0000000000");
        _label.get(17).setText("--/--/--");
        _label.get(19).setText("Ingrese el pago");
        _textField.get(6).setText("");
        _textField.get(7).setText("");

        listClientesReportes = clientes();
        if (!listClientesReportes.isEmpty()) {
            _paginadorReportes = new Paginador<>(listClientesReportes,
                    _label.get(7), reg_porPagina);
        }
        SearchReportes("");
    }
    //</editor-fold>
    private List<TClientes> listClientes;
    private List<TClientes> listClientesReportes;
    private List<TReportes_clientes> listReportes;

    public void Paginador(String metodo) {
        switch (metodo) {
            case "Primero":
                switch (seccion) {
                    case 1:
                        if (!listClientes.isEmpty()) {
                            _numPagina = _paginadorClientes.primero();
                        }
                        break;
                    case 2:
                        if (!listClientesReportes.isEmpty()) {
                            _numPagina = _paginadorReportes.primero();
                        }
                        break;
                }
                break;
            case "Anterior":
                switch (seccion) {
                    case 1:
                        if (!listClientes.isEmpty()) {
                            _numPagina = _paginadorClientes.anterior();
                        }
                        break;
                    case 2:
                        if (!listClientesReportes.isEmpty()) {
                            _numPagina = _paginadorReportes.anterior();
                        }
                        break;
                }
                break;
            case "Siguiente":
                switch (seccion) {
                    case 1:
                        if (!listClientes.isEmpty()) {
                            _numPagina = _paginadorClientes.siguiente();
                        }
                        break;
                    case 2:
                        if (!listClientesReportes.isEmpty()) {
                            _numPagina = _paginadorReportes.siguiente();
                        }
                        break;
                }
                break;
            case "Ultimo":
                switch (seccion) {
                    case 1:
                        if (!listClientes.isEmpty()) {
                            _numPagina = _paginadorClientes.ultimo();
                        }
                        break;
                    case 2:
                        if (!listClientesReportes.isEmpty()) {
                            _numPagina = _paginadorReportes.ultimo();
                        }
                        break;
                }
                break;
        }
        switch (seccion) {
            case 1:
                SearchClientes("");
                break;
            case 2:
                SearchReportes("");
                break;
        }
    }

    public void Registro_Paginas() {
        _numPagina = 1;
        Number caja = (Number) _spinnerPaginas.getValue();
        reg_porPagina = caja.intValue();
        switch (seccion) {
            case 1:
                if (!listClientes.isEmpty()) {
                    _paginadorClientes = new Paginador<>(listClientes,
                            _label.get(7), reg_porPagina);

                }
                SearchClientes("");
                break;
            case 2:
                if (!listClientesReportes.isEmpty()) {
                    _paginadorReportes = new Paginador<>(listClientesReportes,
                            _label.get(7), reg_porPagina);

                }
                SearchReportes("");
                break;

        }

    }
}
