package implementacion;

import tdas.*;

public class ArbolPrecipitaciones implements ABBPrecipitacionesTDA {

	class nodoArbol {
		String campo;
		DiccionarioSimpleStringTDA mensualPrecipitaciones;
		ABBPrecipitacionesTDA hijoIzquierdo;
		ABBPrecipitacionesTDA hijoDerecho;
	}

	private nodoArbol raiz;

	@Override
	public void inicializar() {
		raiz = null;
	}

	@Override
	public void agregar(String valor) {
		if (raiz == null) {
			raiz = new nodoArbol();
			raiz.campo = valor;
			raiz.mensualPrecipitaciones = new DiccionarioSimpleString();
			raiz.mensualPrecipitaciones.inicializarDiccionario();

			raiz.hijoIzquierdo = new ArbolPrecipitaciones();
			raiz.hijoIzquierdo.inicializar();

			raiz.hijoDerecho = new ArbolPrecipitaciones();
			raiz.hijoDerecho.inicializar();
		} else if (valor.compareTo(raiz.campo) < 0) {
			raiz.hijoIzquierdo.agregar(valor);
		} else if (valor.compareTo(raiz.campo) > 0) {
			raiz.hijoDerecho.agregar(valor);
		}
	}

	@Override
	public void agregarMedicion(String valor, String anio, String mes, int dia, int precipitacion) {
		if (raiz == null) {
			agregar(valor);
		}

		if (valor.equals(raiz.campo)) {
			String periodo = anio + String.format("%02d", Integer.parseInt(mes));
			raiz.mensualPrecipitaciones.agregar(periodo, dia, precipitacion);
		} else if (valor.compareTo(raiz.campo) < 0) {
			raiz.hijoIzquierdo.agregarMedicion(valor, anio, mes, dia, precipitacion);
		} else {
			raiz.hijoDerecho.agregarMedicion(valor, anio, mes, dia, precipitacion);
		}
	}

	@Override
	public void eliminar(String valor) {
		if (raiz == null) return;

		if (valor.compareTo(raiz.campo) < 0) {
			raiz.hijoIzquierdo.eliminar(valor);
		} else if (valor.compareTo(raiz.campo) > 0) {
			raiz.hijoDerecho.eliminar(valor);
		} else {
			ArbolPrecipitaciones izq = (ArbolPrecipitaciones) raiz.hijoIzquierdo;
			ArbolPrecipitaciones der = (ArbolPrecipitaciones) raiz.hijoDerecho;

			if (izq.raiz == null && der.raiz == null) {
				raiz = null;
			} else if (izq.raiz != null && der.raiz == null) {
				raiz = izq.raiz;
			} else if (izq.raiz == null) {
				raiz = der.raiz;
			} else {
				String reemplazo = der.minimo();
				raiz.campo = reemplazo;
				raiz.hijoDerecho.eliminar(reemplazo);
			}
		}
	}

	private String minimo() {
		if (raiz == null) return null;
		if (((ArbolPrecipitaciones) raiz.hijoIzquierdo).raiz == null) return raiz.campo;
		return ((ArbolPrecipitaciones) raiz.hijoIzquierdo).minimo();
	}

	@Override
	public String raiz() {
		return raiz != null ? raiz.campo : null;
	}

	@Override
	public void eliminarMedicion(String valor, String anio, String mes, int dia) {
		if (raiz == null) return;

		if (valor.equals(raiz.campo)) {
			String periodo = anio + String.format("%02d", Integer.parseInt(mes));
			DiccionarioSimpleTDA dias = raiz.mensualPrecipitaciones.recuperar(periodo);
			if (dias != null) {
				dias.eliminar(dia);
			}
		} else if (valor.compareTo(raiz.campo) < 0) {
			raiz.hijoIzquierdo.eliminarMedicion(valor, anio, mes, dia);
		} else {
			raiz.hijoDerecho.eliminarMedicion(valor, anio, mes, dia);
		}
	}

	@Override
	public ColaStringTDA periodos() {
		ColaStringTDA cola = new ColaString();
		cola.inicializarCola();

		if (raiz != null && raiz.mensualPrecipitaciones != null) {
			ConjuntoStringTDA conjunto = raiz.mensualPrecipitaciones.claves();
			while (!conjunto.estaVacio()) {
				String periodo = conjunto.elegir();
				cola.acolar(periodo);
				conjunto.sacar(periodo);
			}
		}
		return cola;
	}

	@Override
	public ColaPrioridadTDA precipitaciones(String periodo) {
		ColaPrioridadTDA cola = new ColaPrioridad();
		cola.inicializarCola();

		if (raiz != null && raiz.mensualPrecipitaciones != null) {
			DiccionarioSimpleTDA dias = raiz.mensualPrecipitaciones.recuperar(periodo);
			if (dias != null) {
				ConjuntoTDA claves = dias.obtenerClaves();
				while (!claves.estaVacio()) {
					int dia = claves.elegir();
					int valor = dias.recuperar(dia);
					cola.acolarPrioridad(valor, dia);
					claves.sacar(dia);
				}
			}
		}
		return cola;
	}

	@Override
	public ABBPrecipitacionesTDA hijoIzq() {
		return raiz != null ? raiz.hijoIzquierdo : null;
	}

	@Override
	public ABBPrecipitacionesTDA hijoDer() {
		return raiz != null ? raiz.hijoDerecho : null;
	}

	@Override
	public boolean arbolVacio() {
		return raiz == null;
	}
}
