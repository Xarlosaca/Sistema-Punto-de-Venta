/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class Ticket {

    private final StringBuilder lineas = new StringBuilder();
    private final int maxCaracter = 40;
    private int stop;
    private final FormatDecimal _format = new FormatDecimal();

    public String LineasGuion() {
        String linea = "";
        for (int i = 0; i < maxCaracter; i++) {
            linea += "-";
        }
        return lineas.append(linea).append("\n").toString();
    }

    public String LineAstericos() {
        String asterisco = "";
        for (int i = 0; i < maxCaracter; i++) {
            asterisco += "*";
        }
        return lineas.append(asterisco).append("\n").toString();
    }

    public String LineaIgual() {
        String igual = "";
        for (int i = 0; i < maxCaracter; i++) {
            igual += "=";
        }
        return lineas.append(igual).append("\n").toString();
    }

    public void EncabezadosVenta(String columnas) {
        lineas.append(columnas).append("\n");
    }

    public void TextoIzquierda(String texto) {
        if (texto.length() > maxCaracter) {
            int caracterActual = 0;
            for (int i = texto.length(); i > maxCaracter; i -= maxCaracter) {
                lineas.append(texto.substring(caracterActual, maxCaracter)).append("\n");
                caracterActual += maxCaracter;
            }
            lineas.append(texto.substring(caracterActual, texto.length() - caracterActual)).append("\n");

        } else {
            lineas.append(texto).append("\n");
        }
    }

    public void TextoDerecho(String texto) {
        if (texto.length() > maxCaracter) {
            int caracterActual = 0;
            for (int i = texto.length(); i > maxCaracter; i -= maxCaracter) {
                lineas.append(texto.substring(caracterActual, maxCaracter)).append("\n");
                caracterActual += maxCaracter;
            }
            String espacios = "";
            for (int i = 0; i < (maxCaracter - texto.substring(caracterActual,
                    texto.length() - caracterActual).length()); i++) {
                espacios += " ";
            }
            lineas.append(espacios).append(texto.substring(caracterActual, texto.length() - caracterActual)).append("\n");
        } else {
            String espacios = "";
            for (int i = 0; i < (maxCaracter - texto.length()); i++) {
                espacios += " ";
            }
            lineas.append(espacios).append(texto).append("\n");
        }
    }

    public void TextoCentro(String texto) {
        if (texto.length() > maxCaracter) {
            int caracterActual = 0;
            for (int i = texto.length(); i > maxCaracter; i -= maxCaracter) {
                lineas.append(texto.substring(caracterActual, maxCaracter)).append("\n");
                caracterActual += maxCaracter;
            }
            String espacios = "";
            int centrar = (maxCaracter - texto.substring(caracterActual,
                    texto.length() - caracterActual).length()) / 2;
            for (int i = 0; i < centrar; i++) {
                espacios += " ";
            }
            lineas.append(espacios).append(texto.substring(caracterActual,
                    texto.length() - caracterActual)).append("\n");
        } else {
            String espacios = "";
            int centrar = (maxCaracter - texto.length()) / 2;
            for (int i = 0; i < centrar; i++) {
                espacios += " ";
            }
            lineas.append(espacios).append(texto).append("\n");
        }
    }

    public void TextoExtremo(String izquierdo, String derecho) {
        String der, izq, completo = "", espacio = "";
        if (izquierdo.length() > 18) {
            stop = izquierdo.length() - 18;
            izq = izquierdo.substring(stop, 19);
        } else {
            izq = izquierdo;
        }
        completo = izq;
        if (derecho.length() > 20) {
            stop = derecho.length() - 20;
            der = derecho.substring(stop, 20);
        } else {
            der = derecho;
        }
        int numEspacios = maxCaracter - (izq.length() + der.length());
        for (int i = 0; i < numEspacios; i++) {
            espacio += " ";
        }
        completo += espacio + derecho;
        lineas.append(completo).append("\n");
    }

    public void AgregarTotales(String texto, double total) {
        String resumen, valor, completo = "", espacio = "";
        if (texto.length() > 25) {
            resumen = texto.substring(stop, 25);
        } else {
            resumen = texto;
        }
        completo = resumen;
        valor = _format.decimal(total);
        int numEspacios = maxCaracter - (resumen.length() + valor.length());
        for (int i = 0; i < numEspacios; i++) {
            espacio += " ";
        }
        completo += espacio + valor;
        lineas.append(completo).append("\n");
    }

    public void AgregarArticulo(String articulo, Integer cant, Double precio, Double importe) {
        if (precio.toString().length() <= 5 && precio.toString().length() <= 7
                && importe.toString().length() <= 8) {
            String elemento = "", espacios = "";
            boolean bandera = false;
            int numEspacios = 0;
            if (articulo.length() > 20) {
                //Colocar a la derecha
                numEspacios = (5 - cant.toString().length());
                espacios = "";
                for (int i = 0; i < numEspacios; i++) {
                    espacios += " ";
                }
                elemento += espacios.toString();
                // colocar precio a la derecha
                numEspacios = (7 - precio.toString().length());
                espacios += "";
                for (int i = 0; i < numEspacios; i++) {
                    espacios = " ";
                }
                elemento += espacios + importe.toString();
                int caracterActual = 0;
                for (int i = articulo.length(); i > 20; i -= 20) {
                    if (bandera) {
                        lineas.append(articulo.substring(caracterActual, 20)).append("\n");
                    } else {
                        lineas.append(articulo.substring(caracterActual, 20)).append(elemento).append("\n");
                        bandera = true;
                    }
                }
                lineas.append(articulo.substring(caracterActual, articulo.length() - caracterActual)).append("\n");
            } else {
                for (int i = 0; i < (20 - articulo.length()); i++) {
                    //Agrega espacios para poner el valor de la articulo
                    espacios += " ";
                }
                elemento = articulo + espacios;
                //colocar a la derecha
                numEspacios = (5 - cant.toString().length());
                espacios = "";
                for (int i = 0; i < numEspacios; i++) {
                    //Agregar espacios para poner el valor de articulo
                    espacios += " ";
                }
                elemento += espacios + cant.toString();
                //colocar a la derecha
                numEspacios = (5 - precio.toString().length());
                espacios = "";
                for (int i = 0; i < numEspacios; i++) {
                    //Agregar espacios para poner el valor de precio
                    espacios += " ";
                }
                elemento += espacios + precio.toString();
                lineas.append(elemento).append("\n");

            }
        } else {
            lineas.append("Los valores ingresados para esta fila").append("\n");
            lineas.append("superan las columnas soportadas por esta ").append("\n");
        }
    }

    public void print() {
        //Especificamos el tipo de dato a imprimir 
        //Tipo:bytes;Subtipo:autodetectado
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        PrintService deafaultService = PrintServiceLookup.lookupDefaultPrintService();
        PrintService service = ServiceUI.printDialog(null, 700, 200, printService,
                deafaultService, flavor, pras);
        byte[] bytes;
        bytes = lineas.toString().getBytes();
        Doc doc = new SimpleDoc(bytes, flavor, null);
        //creando trabajo de impresion
        DocPrintJob job = service.createPrintJob();
        try {
            //el metodo print imprime
            job.print(doc, null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al imprimir:" + e.getMessage());
        }
    }
}
