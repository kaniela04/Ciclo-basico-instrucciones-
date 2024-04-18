package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CicloBasicoInstruccion {
    private List<Map<String, String>> memoria;
    private String pc, mar, mdr, icr, alu, unidadControl, acumulador;
    private boolean programaEnEjecucion;

    public CicloBasicoInstruccion() {
        this.memoria = new ArrayList<>();
        this.programaEnEjecucion=false;
    }

    public List<String> leerArchivo(String nombreArchivo) {
        List<String> instrucciones = new ArrayList<>();
        try {
            File archivo = new File(nombreArchivo);
            Scanner scanner = new Scanner(archivo);
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine().trim();
                instrucciones.add(linea);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo '" + nombreArchivo + "' no existe.");
        }
        return instrucciones;
    }

    public void procesarInstrucciones(List<String> instrucciones) {
        String[] linea;
        programaEnEjecucion = true;
        for (String instruccion: instrucciones) {
            if (!programaEnEjecucion)
                break;
            linea = instruccion.split(" ");
            switch (linea[0]) {
                case "SET":
                    this.set(linea[1], linea[2]);
                    break;
                case "ADD":
                    this.add(linea[1], linea[2], linea[3]);
                    break;
                case "LDR":
                    this.load(linea[1]);
                    break;
                case "STR":
                    this.store(linea[1]);
                    break;
                case "SHW":
                    this.show(linea[1]);
                    break;
                case "INC":
                    this.increment(linea[1]);
                    break;
                case "DEC":
                    this.decrement(linea[1]);
                    break;
                case "PAUSE":
                    this.pause();
                    break;
                case "END":
                    this.end();
                    break;
                default:
                    System.out.println("Instruccion erronea!!!");
                    break;
            }
        }
    }

    private void procesar(String tipoInstruccion, String memoria) {
        this.pc = memoria;
        this.mar = this.pc;
        System.out.println(tipoInstruccion + " " + this.mar);
        this.mdr = tipoInstruccion + " " + this.mar;
        this.icr = this.mdr;
        this.unidadControl = this.icr;
        this.mar = memoria;
        if (Objects.equals(tipoInstruccion, "STORE")) {
            this.mdr = String.valueOf(this.acumulador);

            for (Map<String, String> elemento : this.memoria) {
                if (elemento.containsKey(this.mar))
                    elemento.put(this.mar, this.mdr);

                if (elemento.containsKey(this.mar)) {
                    mdr = elemento.get(this.mar);
                    break;
                }
            }
        }
        for (Map<String, String> elemento : this.memoria) {
            if (elemento.containsKey(this.mar)) {
                mdr = elemento.get(this.mar);
                break;
            }
        }
        System.out.println(this.mdr);

    }

    private void set(String memoria, String valor) {
        boolean existeMemoria = false;
        for (Map<String, String> elemento: this.memoria) {
            existeMemoria = elemento.containsKey(memoria);
        }
        if (!existeMemoria) {
            Map<String, String> nuevoElemento = new HashMap<>();
            nuevoElemento.put(memoria, valor);
            this.memoria.add(nuevoElemento);
        }
    }

    private void load(String valor) {
        this.procesar("LOAD", valor);
        this.acumulador = this.mdr;
        System.out.println("Este es el acumulador " + this.acumulador);
    }

    private void add(String memoria1, String memoria2, String memoria3) {
        if (Objects.equals(memoria2, "NULL") && Objects.equals(memoria3, "NULL")) {
            this.procesar("ADD", memoria1);
            this.alu = this.acumulador;
            this.acumulador = this.mdr;
                this.acumulador = String.valueOf(Integer.parseInt(this.alu) + Integer.parseInt(this.acumulador));
            System.out.println("Este es el acumulador" + this.acumulador);
        } else {
            this.procesar("ADD", memoria1);
            this.acumulador = this.mdr;
            this.alu = this.acumulador;

            this.procesar("ADD", memoria2);
            this.acumulador = this.mdr;
            this.acumulador = String.valueOf(Integer.parseInt(this.alu) + Integer.parseInt(this.acumulador));
            if (!Objects.equals(memoria3, "NULL"))
                store(memoria3);
        }
    }

    private void store(String valor) {
        this.procesar("STORE", valor);
    }


    private void show(String memoria) {
        switch (memoria) {
            case "ACC":
                System.out.println("El ACC es " + this.acumulador);
                break;
            case "ICR":
                System.out.println("El ICR es " + this.icr);
                break;
            case "MAR":
                System.out.println("El MAR es " + this.mar);
                break;
            case "MDR":
                System.out.println("El MDR es " + this.mdr);
                break;
            case "UC":
                System.out.println("El UC es " + this.unidadControl);
                break;
            default:
                for (Map<String, String> elemento : this.memoria) {
                    if (elemento.containsKey(memoria)) {
                        String contenido = elemento.get(memoria);
                        System.out.println("Valor en la dirección de memoria " + memoria + ": " + contenido);
                        break;
                    }
                }
                break;
        }

    }

    private void pause() {
        System.out.println("En pausa");
        programaEnEjecucion = false;
    }

    private void increment(String memoria) {
        procesar("LOAD", memoria);
        int valor = Integer.parseInt(this.mdr);
        valor++;
        this.acumulador = String.valueOf(valor);
        System.out.println("Ha incrementado");
        System.out.println("El acumulador es " + this.acumulador);
    }


    private void decrement(String memoria) {
        procesar("LOAD", memoria);
        int valor = Integer.parseInt(this.mdr);
        valor--;
        this.acumulador = String.valueOf(valor);
        System.out.println("Ha disminuido");
        System.out.println("El acumulador es " + this.acumulador);
    }
    private void end() {
        System.out.println("End");
        System.out.println(this.acumulador);
        programaEnEjecucion = false;
        this.memoria = new ArrayList<>();
        this.pc="";
        this.mdr="";
        this.mar="";
        this.icr="";
        this.acumulador="";
        this.unidadControl="";
        this.alu="";
    }
}
