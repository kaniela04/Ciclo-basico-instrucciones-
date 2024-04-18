package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CicloBasicoInstruccion cbi = new CicloBasicoInstruccion();
        // Programa 1
        List<String> instrucciones1 = cbi.leerArchivo("src/main/resources/programa1.txt");
        if (instrucciones1 != null) {
            System.out.println("Procesando Programa 1:");
            cbi.procesarInstrucciones(instrucciones1);
            System.out.println();
        }

        // Programa 2
        List<String> instrucciones2 = cbi.leerArchivo("src/main/resources/programa2.txt");
        if (instrucciones2 != null) {
            System.out.println("Procesando Programa 2:");
            cbi.procesarInstrucciones(instrucciones2);
            System.out.println();
        }

        // Programa 3
        List<String> instrucciones3 = cbi.leerArchivo("src/main/resources/programa3.txt");
        if (instrucciones3 != null) {
            System.out.println("Procesando Programa 3:");
            cbi.procesarInstrucciones(instrucciones3);
            System.out.println();
        }

        // Programa 4
        List<String> instrucciones4 = cbi.leerArchivo("src/main/resources/programa4.txt");
        if (instrucciones4 != null) {
            System.out.println("Procesando Programa 4:");
            cbi.procesarInstrucciones(instrucciones4);
            System.out.println();
        }
  }
}