/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Conexion.Consult;
import Models.TConfiguration;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JRadioButton;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

/**
 *
 * @author ASUS
 */
public class ConfigurationVM extends Consult {

    public static String Mony;
    private static List<JRadioButton> _radio;

    public ConfigurationVM() {

        TypeMoney();

    }

    public ConfigurationVM(List<JRadioButton> radio) {
        //this.sqlConfig = "INSERT INTO tconfiguration(TypeMoney) VALUES(?)";
        _radio = radio;
        RadioEvent();
        TypeMoney();
    }

    private void RadioEvent() {
        _radio.get(0).addActionListener((ActionEvent e) -> {
            TypeMoney("$", _radio.get(0).isSelected());
        });
        _radio.get(1).addActionListener((ActionEvent e) -> {
            TypeMoney("Sol", _radio.get(1).isSelected());
        });
        _radio.get(2).addActionListener((ActionEvent e) -> {
            TypeMoney("Peso", _radio.get(2).isSelected());
        });
    }

    private String sqlConfig;

    private void TypeMoney() {
        sqlConfig = "INSERT INTO tconfiguration(TypeMoney) VALUES(?)";
        List<TConfiguration> config = config();
        final QueryRunner qr = new QueryRunner(true);

        if (config.isEmpty()) {
            Mony = "$";
            Object[] dataConfig = {Mony};
            try {
                qr.insert(getConn(), sqlConfig, new ColumnListHandler(), dataConfig);
            } catch (SQLException ex) {
                System.out.println("Error : " + ex);
            }
        } else {
            //int count = config.size();
            //count--;
            TConfiguration data = config.get(0);
            Mony = data.getTypeMoney();
            switch (Mony) {
                case "$":
                    _radio.get(0).setSelected(true);
                    break;
                case "Sol":
                    _radio.get(1).setSelected(true);
                    break;
                case "Peso":
                    _radio.get(2).setSelected(true);
                    break;

            }
        }
    }

    private void TypeMoney(String typeMoney, Boolean valor) {
        final QueryRunner qr = new QueryRunner(true);
        if (valor) {
            try {
                List<TConfiguration> config = config();
                if (config.isEmpty()) {
                    sqlConfig = "INSERT INTO tconfiguration(TypeMoney) VALUES(?)";
                    Mony = typeMoney;
                    Object[] dataConfig = {Mony};
                    qr.insert(getConn(), sqlConfig, new ColumnListHandler(), dataConfig);

                } else {
                    // int count = config.size();
                    //count--;
                    TConfiguration data = config.get(0);
                    sqlConfig = "UPDATE tconfiguration SET TypeMoney = ? WHERE ID =" + data.getID();

                    if (data.getTypeMoney().equals(typeMoney)) {
                        Mony = typeMoney;
                    } else {
                        Mony = typeMoney;
                        Object[] dataConfig = {Mony};
                        qr.update(getConn(), sqlConfig, dataConfig);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error : " + ex);
            }
        }
    }

}
