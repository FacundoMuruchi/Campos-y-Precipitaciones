package implementacion;
import java.util.Random;
import tdas.ConjuntoStringTDA;

public class ConjuntoString implements ConjuntoStringTDA {

	class nodo {
		String valor;
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
	public void agregar(String valor) {
		if (!pertenece(valor)) {
			nodo nuevo = new nodo();
			nuevo.valor = valor;
			nuevo.siguiente = primero;
			primero = nuevo;
			cantidad++;
		}
	}

	@Override
	public void sacar(String valor) {
		if (primero == null) return;

		if (primero.valor.equals(valor)) {
			primero = primero.siguiente;
			cantidad--;
			return;
		}

		nodo actual = primero;
		while (actual.siguiente != null && !actual.siguiente.valor.equals(valor)) {
			actual = actual.siguiente;
		}

		if (actual.siguiente != null) {
			actual.siguiente = actual.siguiente.siguiente;
			cantidad--;
		}
	}

	@Override
	public String elegir() {
		if (primero == null) return null;
		int index = r.nextInt(cantidad);
		nodo actual = primero;
		for (int i = 0; i < index; i++) {
			actual = actual.siguiente;
		}
		return actual.valor;
	}

	@Override
	public boolean pertenece(String valor) {
		nodo actual = primero;
		while (actual != null) {
			if (actual.valor.equals(valor)) return true;
			actual = actual.siguiente;
		}
		return false;
	}

	@Override
	public boolean estaVacio() {
		return cantidad == 0;
	}

}
