package test;

import algoritmos.Algoritmos;
import tdas.ColaPrioridadTDA;
import tdas.ColaStringTDA;

public class Test {
    public static void main(String[] args) {
        Algoritmos alg = new Algoritmos();

        // Test: agregarMedicion
        alg.agregarMedicion("Campo1", 2024, 5, 10, 15);
        alg.agregarMedicion("Campo1", 2024, 5, 11, 20);
        alg.agregarMedicion("Campo2", 2024, 5, 10, 30);
        alg.agregarMedicion("Campo2", 2024, 5, 12, 25);
        alg.agregarMedicion("Campo3", 2024, 5, 13, 1);
        alg.agregarMedicion("Campo6", 2024, 5, 10, 3454);
        alg.agregarMedicion("Campo3", 2024, 6, 5, 50);
        alg.agregarMedicion("Campo5", 2024, 6, 5, 550);

        // Test: promedioLluviaEnUnDia
        System.out.println("--- Promedio lluvia en dd/mm/yyyy ---");
        System.out.println(alg.promedioLluviaEnUnDia(2024, 5, 10));

        // Test: medicionesMes
        System.out.println("\n--- Mediciones del mes mm/yyyy ---");
        ColaPrioridadTDA colaMes = alg.medicionesMes(2024, 5);
        while (!colaMes.colaVacia()) {
            System.out.println("DIA " + colaMes.prioridad() + ": " + colaMes.primero() + " mm");
            colaMes.desacolar();
        }

        // Test: medicionesCampoMes
        System.out.println("\n--- Mediciones Campoxx en mm/yyyy ---");
        ColaPrioridadTDA campo2 = alg.medicionesCampoMes("Campo2", 2024, 5);
        while (!campo2.colaVacia()) {
            System.out.println("DIA " + campo2.prioridad() + ": " + campo2.primero() + " mm");
            campo2.desacolar();
        }

        // Test: mesMasLluvioso
        System.out.println("\n--- Mes más lluvioso ---");
        ColaPrioridadTDA lluvioso = alg.mesMasLluvioso();
        while (!lluvioso.colaVacia()) {
            System.out.println("Periodo: " + lluvioso.prioridad() + " - Lluvia Total: " + lluvioso.primero());
            lluvioso.desacolar();
        }

        // Test: campoMasLLuvisoHistoria
        System.out.println("\n--- Campo más lluvioso ---");
        System.out.println(alg.campoMasLLuvisoHistoria());

        // Test: camposConLLuviaMayorPromedio
        System.out.println("\n--- Campos con lluvia mayor al promedio en mm/yyyy ---");
        ColaStringTDA campos = alg.camposConLLuviaMayorPromedio(2024, 5);
        while (!campos.colaVacia()) {
            System.out.println(campos.primero());
            campos.desacolar();
        }

        // Test: eliminarMedicion
        alg.eliminarMedicion("Campo1", 2024, 5, 11);
        System.out.println("\n--- Campoxx luego de eliminar dd/mm/yyyy ---");
        ColaPrioridadTDA campo1 = alg.medicionesCampoMes("Campo1", 2024, 5);
        while (!campo1.colaVacia()) {
            System.out.println("DIA " + campo1.prioridad() + ": " + campo1.primero() + " mm");
            campo1.desacolar();
        }

        // Test: eliminarCampo
        alg.eliminarCampo("Campo2");
        System.out.println("\n--- Campo2 luego de ser eliminado ---");
        ColaPrioridadTDA campo2borrado = alg.medicionesCampoMes("Campo2", 2024, 5);
        while (!campo2borrado.colaVacia()) {
            System.out.println("DIA " + campo2borrado.prioridad() + ": " + campo2borrado.primero() + " mm");
            campo2borrado.desacolar();
        }
    }
}
