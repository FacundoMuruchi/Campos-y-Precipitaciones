package algoritmos;

import implementacion.ArbolPrecipitaciones;
import implementacion.ColaPrioridad;
import implementacion.ColaString;
import implementacion.DiccionarioSimple;
import tdas.*;

public class Algoritmos {
	private final ABBPrecipitacionesTDA arbol;

	public Algoritmos() {
		arbol = new ArbolPrecipitaciones();
		arbol.inicializar();
	}

	/**
	 * Agrega una medicion a un campo determinado, en una fecha determinada
	 * */
	public void agregarMedicion(String campo, int anio, int mes, int dia, int precipitacion) {
		arbol.agregar(campo);
		String anioStr = String.valueOf(anio);
		String mesStr = String.format("%02d", mes);
		arbol.agregarMedicion(campo, anioStr, mesStr, dia, precipitacion);
	}

	/**
	 * Elimina una medicions a un campo determinado, en una fecha determinada
	 * */
	public void eliminarMedicion(String campo, int anio, int mes, int dia) {
		String anioStr = String.valueOf(anio);
		String mesStr = String.format("%02d", mes);
		arbol.eliminarMedicion(campo, anioStr, mesStr, dia);
	}

	/**
	 * Elimina un campo determinado recibido como parametro
	 * */
	public void eliminarCampo(String campo) {
		arbol.eliminar(campo);
	}

	/**
	 * Devuelve una cola con prioridad con las precipitaciones promedio de cada dia de un mes y año
	 * determinado en todos los campos
	 * */
	public ColaPrioridadTDA medicionesMes(int anio, int mes) {
		ColaPrioridadTDA resultado = new ColaPrioridad();
		resultado.inicializarCola();
		String periodo = anio + String.format("%02d", mes);
		sumarMediciones(arbol, periodo, resultado);
		return resultado;
	}

	private void sumarMediciones(ABBPrecipitacionesTDA arbol, String periodo, ColaPrioridadTDA resultado) {
		if (arbol.arbolVacio()) return;
		ColaPrioridadTDA col = arbol.precipitaciones(periodo);
		while (!col.colaVacia()) {
			int dia = col.prioridad();
			int mm = col.primero();
			acumular(resultado, dia, mm);
			col.desacolar();
		}
		sumarMediciones(arbol.hijoIzq(), periodo, resultado);
		sumarMediciones(arbol.hijoDer(), periodo, resultado);
	}

	private void acumular(ColaPrioridadTDA resultado, int dia, int mm) {
		ColaPrioridadTDA aux = new ColaPrioridad();
		aux.inicializarCola();
		boolean encontrado = false;
		while (!resultado.colaVacia()) {
			int d = resultado.prioridad();
			int val = resultado.primero();
			resultado.desacolar();
			if (d == dia) {
				val += mm;
				encontrado = true;
			}
			aux.acolarPrioridad(val, d);
		}
		if (!encontrado) aux.acolarPrioridad(mm, dia);
		while (!aux.colaVacia()) {
			resultado.acolarPrioridad(aux.primero(), aux.prioridad());
			aux.desacolar();
		}
	}

	/**
	 * Devuelve una cola con prioridad con las precipitaciones de cada dia de un mes y año
	 * determinado en un campo determinado
	 * */
	public ColaPrioridadTDA medicionesCampoMes(String campo, int anio, int mes) {
		String periodo = anio + String.format("%02d", mes);
		return buscarCampo(arbol, campo, periodo);
	}

	private ColaPrioridadTDA buscarCampo(ABBPrecipitacionesTDA arbol, String campo, String periodo) {
		if (arbol.arbolVacio()) return new ColaPrioridad();
		if (arbol.raiz().equals(campo)) return arbol.precipitaciones(periodo);
		if (campo.compareTo(arbol.raiz()) < 0) return buscarCampo(arbol.hijoIzq(), campo, periodo);
		else return buscarCampo(arbol.hijoDer(), campo, periodo);
	}

	/**
	 * Devuelve el numero de mes donde mas llovio entre todos los meses de todos los años de cualquier campo
	 * */
	public ColaPrioridadTDA mesMasLluvioso() {
		DiccionarioSimple lluviasPorPeriodo = new DiccionarioSimple();
		lluviasPorPeriodo.inicializar();
		acumularLluvias(arbol, lluviasPorPeriodo);
		int maxLl = -1;
		int periodoMax = -1;
		ConjuntoTDA claves = lluviasPorPeriodo.obtenerClaves();
		while (!claves.estaVacio()) {
			int p = claves.elegir();
			int ll = lluviasPorPeriodo.recuperar(p);
			if (ll > maxLl) {
				maxLl = ll;
				periodoMax = p;
			}
			claves.sacar(p);
		}
		ColaPrioridadTDA resultado = new ColaPrioridad();
		resultado.inicializarCola();
		if (periodoMax != -1) resultado.acolarPrioridad(maxLl, periodoMax);
		return resultado;
	}

	private void acumularLluvias(ABBPrecipitacionesTDA arbol, DiccionarioSimple dic) {
		if (arbol.arbolVacio()) return;
		ColaStringTDA periodos = arbol.periodos();
		while (!periodos.colaVacia()) {
			String periodo = periodos.primero();
			int periodoInt = Integer.parseInt(periodo);
			ColaPrioridadTDA dias = arbol.precipitaciones(periodo);
			int total = 0;
			while (!dias.colaVacia()) {
				total += dias.primero();
				dias.desacolar();
			}
			int actual = dic.recuperar(periodoInt);
			dic.agregar(periodoInt, actual + total);
			periodos.desacolar();
		}
		acumularLluvias(arbol.hijoIzq(), dic);
		acumularLluvias(arbol.hijoDer(), dic);
	}

	/**
	 * Devuelve el promedio de precipitaciones caidas en un dia, mes y anio determinado en todos los campos
	 * */
	public float promedioLluviaEnUnDia(int anio, int mes, int dia) {
		String periodo = anio + String.format("%02d", mes);
		int[] acumulador = new int[2]; // acumulador[0] = suma, acumulador[1] = cantidad
		promedioDia(arbol, periodo, dia, sumaContador -> {
			acumulador[0] += sumaContador[0];
			acumulador[1] += sumaContador[1];
		});
		return acumulador[1] == 0 ? 0 : (float) acumulador[0] / acumulador[1];
	}

	private void promedioDia(ABBPrecipitacionesTDA arbol, String periodo, int dia, java.util.function.Consumer<int[]> acumulador) {
		if (arbol.arbolVacio()) return;
		ColaPrioridadTDA cola = arbol.precipitaciones(periodo);
		while (!cola.colaVacia()) {
			if (cola.prioridad() == dia) {
				acumulador.accept(new int[]{cola.primero(), 1});
				break;
			}
			cola.desacolar();
		}
		promedioDia(arbol.hijoIzq(), periodo, dia, acumulador);
		promedioDia(arbol.hijoDer(), periodo, dia, acumulador);
	}

	/**
	 * Devuelve el campo que recibio mas lluvia 
	 * */
	public String campoMasLLuvisoHistoria() {
		return campoConMasLluvia(arbol, new int[]{0}, "");
	}

	private String campoConMasLluvia(ABBPrecipitacionesTDA arbol, int[] max, String campoMax) {
		if (arbol.arbolVacio()) return campoMax;
		int total = 0;
		ColaStringTDA periodos = arbol.periodos();
		while (!periodos.colaVacia()) {
			String periodo = periodos.primero();
			ColaPrioridadTDA dias = arbol.precipitaciones(periodo);
			while (!dias.colaVacia()) {
				total += dias.primero();
				dias.desacolar();
			}
			periodos.desacolar();
		}
		if (total > max[0]) {
			max[0] = total;
			campoMax = arbol.raiz();
		}
		campoMax = campoConMasLluvia(arbol.hijoIzq(), max, campoMax);
		campoMax = campoConMasLluvia(arbol.hijoDer(), max, campoMax);
		return campoMax;
	}

	/**
	 * Devuelve los campos con una cantidad de lluvia en un periodo determinado que es mayor al
	 * promedio de lluvia en un periodo determinado
	 * */
	public ColaString camposConLLuviaMayorPromedio(int anio, int mes) {
		String periodo = anio + String.format("%02d", mes);
		float[] sumaYcantidad = new float[2]; // [0] suma total, [1] cantidad de campos
		calcularPromedioPorPeriodo(arbol, periodo, sumaYcantidad);
		float promedio = sumaYcantidad[1] == 0 ? 0 : sumaYcantidad[0] / sumaYcantidad[1];
		ColaString resultado = new ColaString();
		resultado.inicializarCola();
		agregarCamposMayores(arbol, periodo, promedio, resultado);
		return resultado;
	}

	private void calcularPromedioPorPeriodo(ABBPrecipitacionesTDA arbol, String periodo, float[] datos) {
		if (arbol.arbolVacio()) return;
		ColaPrioridadTDA dias = arbol.precipitaciones(periodo);
		int total = 0;
		while (!dias.colaVacia()) {
			total += dias.primero();
			dias.desacolar();
		}
		if (total > 0) {
			datos[0] += total;
			datos[1]++;
		}
		calcularPromedioPorPeriodo(arbol.hijoIzq(), periodo, datos);
		calcularPromedioPorPeriodo(arbol.hijoDer(), periodo, datos);
	}

	private void agregarCamposMayores(ABBPrecipitacionesTDA arbol, String periodo, float promedio, ColaStringTDA resultado) {
		if (arbol.arbolVacio()) return;
		int total = 0;
		ColaPrioridadTDA dias = arbol.precipitaciones(periodo);
		while (!dias.colaVacia()) {
			total += dias.primero();
			dias.desacolar();
		}
		if (total > promedio) {
			resultado.acolar(arbol.raiz());
		}
		agregarCamposMayores(arbol.hijoIzq(), periodo, promedio, resultado);
		agregarCamposMayores(arbol.hijoDer(), periodo, promedio, resultado);
	}
	
}
