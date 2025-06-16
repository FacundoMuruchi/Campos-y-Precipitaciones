package implementacion;

import tdas.ConjuntoStringTDA;
import tdas.DiccionarioSimpleStringTDA;
import tdas.DiccionarioSimpleTDA;

public class DiccionarioSimpleString implements DiccionarioSimpleStringTDA {

	class nodo{
		String periodo;
		DiccionarioSimpleTDA precipitacionesMes;
		nodo siguiente;
	}
	
	private nodo primero;
	
	@Override
	public void inicializarDiccionario() {
		primero = null;
	}

	@Override
	public void agregar(String periodo, int dia, int cantidad) {
		nodo actual = primero;
		while (actual != null && !actual.periodo.equals(periodo)) {
			actual = actual.siguiente;
		}
		if (actual == null) {
			actual = new nodo();
			actual.periodo = periodo;
			actual.precipitacionesMes = new DiccionarioSimple();
			actual.precipitacionesMes.inicializar();
			actual.siguiente = primero;
			primero = actual;
		}
		actual.precipitacionesMes.agregar(dia, cantidad);
	}

	@Override
	public void eliminar(String periodo) {
		if (primero == null) return;
		if (primero.periodo.equals(periodo)) {
			primero = primero.siguiente;
			return;
		}
		nodo actual = primero;
		while (actual.siguiente != null && !actual.siguiente.periodo.equals(periodo)) {
			actual = actual.siguiente;
		}
		if (actual.siguiente != null) {
			actual.siguiente = actual.siguiente.siguiente;
		}
	}

	@Override
	public DiccionarioSimpleTDA recuperar(String periodo) {
		nodo actual = primero;
		while (actual != null) {
			if (actual.periodo.equals(periodo)) return actual.precipitacionesMes;
			actual = actual.siguiente;
		}
		return null;
	}

	@Override
	public ConjuntoStringTDA claves() {
		ConjuntoString conjunto = new ConjuntoString();
		conjunto.inicializar();
		nodo actual = primero;
		while (actual != null) {
			conjunto.agregar(actual.periodo);
			actual = actual.siguiente;
		}
		return conjunto;
	}
}
