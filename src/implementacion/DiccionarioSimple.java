package implementacion;

import tdas.ConjuntoTDA;

public class DiccionarioSimple implements tdas.DiccionarioSimpleTDA {

	class nodo{
		int clave;
		int valor;
		nodo siguiente;
	}
	
	private nodo primero;
	
	@Override
	public void inicializar() {
		primero = null;
	}

	@Override
	public void agregar(int clave, int valor) {
		nodo actual = primero;
		while (actual != null && actual.clave != clave) {
			actual = actual.siguiente;
		}
		if (actual != null) {
			actual.valor = valor;
		} else {
			nodo nuevo = new nodo();
			nuevo.clave = clave;
			nuevo.valor = valor;
			nuevo.siguiente = primero;
			primero = nuevo;
		}
	}

	@Override
	public void eliminar(int clave) {
		if (primero == null) return;

		if (primero.clave == clave) {
			primero = primero.siguiente;
			return;
		}

		nodo actual = primero;
		while (actual.siguiente != null && actual.siguiente.clave != clave) {
			actual = actual.siguiente;
		}

		if (actual.siguiente != null) {
			actual.siguiente = actual.siguiente.siguiente;
		}
	}

	@Override
	public int recuperar(int clave) {
		nodo actual = primero;
		while (actual != null) {
			if (actual.clave == clave) return actual.valor;
			actual = actual.siguiente;
		}
		return -1; // clave no encontrada
	}


	@Override
	public ConjuntoTDA obtenerClaves() {
		Conjunto conjunto = new Conjunto();
		conjunto.inicializar();

		nodo actual = primero;
		while (actual != null) {
			conjunto.agregar(actual.clave);
			actual = actual.siguiente;
		}
		return conjunto;
	}
}
