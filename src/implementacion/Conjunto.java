package implementacion;
import java.util.Random;
import tdas.ConjuntoTDA;

public class Conjunto implements ConjuntoTDA {
	
	class nodo {
		int valor;
		nodo siguiente;
	}
	
	private nodo primero;
	private int cantidad;
	private Random r;
	
	@Override
	public void inicializar() {
		primero = null;
		cantidad = 0;
		r = new Random();
	}

	@Override
	public void agregar(int valor) {
		if (!pertenece(valor)) {
			nodo nuevo = new nodo();
			nuevo.valor = valor;
			nuevo.siguiente = primero;
			primero = nuevo;
			cantidad++;
		}
	}

	@Override
	public boolean pertenece(int valor) {
		nodo actual = primero;
		while (actual != null) {
			if (actual.valor == valor) return true;
			actual = actual.siguiente;
		}
		return false;
	}

	@Override
	public void sacar(int valor) {
		if (primero == null) return;

		if (primero.valor == valor) {
			primero = primero.siguiente;
			cantidad--;
			return;
		}

		nodo actual = primero;
		while (actual.siguiente != null && actual.siguiente.valor != valor) {
			actual = actual.siguiente;
		}

		if (actual.siguiente != null) {
			actual.siguiente = actual.siguiente.siguiente;
			cantidad--;
		}
	}

	@Override
	public int elegir() {
		if (primero == null) return -1;
		int index = r.nextInt(cantidad);
		nodo actual = primero;
		for (int i = 0; i < index; i++) {
			actual = actual.siguiente;
		}
		return actual.valor;
	}


	@Override
	public boolean estaVacio() {
		return cantidad == 0;
	}

}