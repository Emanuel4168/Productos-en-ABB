package com.ProductosABB;

public class NodoABB<T> {
	private NodoABB<T> subIzq;
	public T Info;
	private NodoABB<T> subDer;

	public NodoABB(T D) {
		Info = D;
		subDer = null;
		subIzq = null;
	}

	public NodoABB<T> getSubIzq() {
		return subIzq;
	}

	public void setSubIzq(NodoABB<T> Ap) {
		subIzq = Ap;
	}

	public NodoABB<T> getSubDer() {
		return subDer;
	}

	public void setSubDer(NodoABB<T> Ap) {
		subDer = Ap;
	}
}
