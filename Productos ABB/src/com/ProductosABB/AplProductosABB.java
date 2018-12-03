package com.ProductosABB;

import java.util.*;
import java.util.function.Function;

public class AplProductosABB {
	private class Producto {
		public int IdProducto; // valores de 100-5000
		public int Existencia;
		public String toString () {return Rutinas.PonCeros(IdProducto,5);}
		
		public Producto(int id, int existencia) {
			IdProducto = id;
			Existencia = existencia;
		}
	}
	
	private interface IOperacion{
		boolean operar(Producto p, int x);
	}

	
	private AplProductosABB() {
		ArbolBinarioBusqueda<Producto> arbol = new ArbolBinarioBusqueda();
		Scanner scan = new Scanner(System.in);
		int opcion = 0, id =0, entrada = 0;
		Float porcentaje;
		Producto p = null;
		
		while(opcion != 11) {
			displayMenu();
			opcion=scan.nextInt();
			switch(opcion) {
			case 1:
				if(arbol.Inserta(new Producto(Rutinas.nextInt(100,5000),Rutinas.nextInt(100))))
					System.out.println("producto insertado\n");
				else 
					System.out.println("no fué posible insertar el producto\n");
				break;
			case 2:
				System.out.println("Ingrese id del producto a retirar:");
				id = scan.nextInt();
				p = new Producto(id,0);
				if(buscar(arbol,p,(pr,x) -> {
					if(pr.Existencia == 0)
						return arbol.Borrar(pr);
					return false;
				},0))
					System.out.println("Producto eliminado\n");
				else
					System.out.println("El producto no ha sido eliminado\n");
				break;
			case 3:
				System.out.println("Ingrese id del producto:");
				id = scan.nextInt();
				System.out.println("Ingrese entrada del producto:");
				entrada = scan.nextInt();
				p = new Producto(id,0);
				if(buscar(arbol,p,(pr,x) -> {
					pr.Existencia += x;
					return true;
				},entrada))
					System.out.println("Entrada registrada\n");
				else
					System.out.println("El producto no ha sido encontrado\n");
				
				break;
			case 4:
				System.out.println("Ingrese id de referencia:");
				id = scan.nextInt();
				if(recorrer(arbol.getRaiz(),(pr,x) -> {
					if(pr.IdProducto > x)
						pr.Existencia += Rutinas.nextInt(1,100);
					return true;
				},id))
					System.out.println("Operacion realizada\n");
				else
					System.out.println("");
				break;
			case 5:
				System.out.println("Ingrese id del producto:");
				id = scan.nextInt();
				System.out.println("Ingrese salida del producto:");
				entrada = scan.nextInt();
				p = new Producto(id,0);
				if(buscar(arbol,p,(pr,x) ->{ 
					pr.Existencia -= x;
					return true;
				},entrada))
					System.out.println("Salida Registrada\n");
				else
					System.out.println("El producto no ha sido eliminado, es posible que no exista");
				break;
			case 6:
				if(recorrer(arbol.getRaiz(),(pr,x) -> {
					if(pr.Existencia == 100)
						return arbol.Borrar(pr);
					return false;
				},0))
					System.out.println("Producto(s) retirados");
				else
					System.out.println("No fue posible realizar la operación");
				break;
			case 7:
				porcentaje = (arbol.Length()/4900)*100f;
				System.out.println(porcentaje+" %");
				break;
			case 8:
				if(arbol.getRaiz() != null)
					imprimirDescendente(arbol.getRaiz());
				else
					System.out.println("Arbol vacio");
				break;
			case 9:
				System.out.println("Ingrese id del producto a buscar:");
				id = scan.nextInt();
				p = new Producto(id,0);
				if(buscar(arbol,p, (pr,x)-> {
					return true;
				},0))
					mostrarSecuencia(arbol,p);
				else
					System.out.println("El producto no existe\n");
				break;
			case 10:
				if(arbol.getRaiz() != null)
					consultaNivelesInferiores(arbol);
				else
					System.out.println("Arbol vacio");
				break;
			case 12:
				System.out.println("Ingrese nivel a consultar:");
				id = scan.nextInt();
				System.out.println(nodosEnUnNivel(arbol.getRaiz(),id,1));
				break;
			default: System.out.println("opción inválida\n");
			}
		}
	}

	public static void main (String[] a) {
		new AplProductosABB();
	}
	
	private void displayMenu() {
		System.out.println("1.Insertar un producto.\n"+
	                       "2.Retirar un producto.\n"+
				           "3.Registrar entrada a un producto.\n"+
	                       "4.Registrar entrada a todos los productos con IdProducto mayor a X\n"+
				           "5.Retirar existencia de un producto\n"+
	                       "6.Retirar todos los productos con existencia igual a 100\n"+
				           "7.Consulta: porcentaje de productos registrados\n"+
	                       "8.Consulta: productos registrados\n"+
				           "9.Consulta: proporcionado un id, mostrar la ubicación del producto en el árbol\n"+ 
				           "10. Consulta: niveles inferiores que tienen más nodos que el nivel superior.\n"+
				           "11.Salir");
	}
	
	private boolean recorrer(NodoABB<Producto> raiz,IOperacion op, int x) {
		if(raiz == null)
			return false;
		recorrer(raiz.getSubIzq(),op,x);
		recorrer(raiz.getSubDer(),op,x);
		return op.operar(raiz.Info,x);
	}
	
	private boolean buscar(ArbolBinarioBusqueda<Producto> arbol, Producto producto,IOperacion op,int x) {
		NodoABB<Producto> raiz = arbol.getRaiz();
		String idDato = producto.toString();
		while(raiz != null) {
			if(idDato.compareTo(raiz.Info.toString()) == 0)
				break;
			if(idDato.compareTo(raiz.Info.toString()) < 0) {
				raiz = raiz.getSubIzq();
				continue;
			}
				raiz = raiz.getSubDer();
		}
		
		if(raiz != null) 
			return op.operar(raiz.Info,x);
		else
			return false;
	}
	
	private boolean mostrarSecuencia(ArbolBinarioBusqueda<Producto> arbol, Producto producto) {
		NodoABB<Producto> raiz = arbol.getRaiz();
		int nivel = 0;
		if (raiz == null || producto == null)
			return false;
		String idDato = producto.toString();
		while(raiz != null) {
			nivel ++;
			if(raiz == arbol.getRaiz())
				System.out.println("\tRaiz");
			if(idDato.compareTo(raiz.Info.toString()) == 0)
				break;
			if(idDato.compareTo(raiz.Info.toString()) < 0) {
				System.out.println("nivel: "+nivel+"\tIzquierda");
				raiz = raiz.getSubIzq();
				continue;
			}
			System.out.println("nivel: "+nivel+"\tDerecha");
			raiz = raiz.getSubDer();
		}
		if(nivel <= arbol.Length()) {
			System.out.println(raiz.Info.IdProducto+"\t"+raiz.Info.Existencia);
			return true;
		}
		return false;
	}

	private int nodosEnUnNivel(NodoABB<Producto> raiz,int nivel, int c) {
		if(raiz == null)
			return 0;
		return nivel == c? 1:0+
			   nodosEnUnNivel(raiz.getSubIzq(),nivel,c+1)+
			   nodosEnUnNivel(raiz.getSubDer(),nivel,c+1);
	}
	
	private void imprimirDescendente(NodoABB<Producto> raiz) {
		if(raiz == null)
			return;
		imprimirDescendente(raiz.getSubDer());
		System.out.println(raiz.Info.IdProducto+"\t"+raiz.Info.Existencia);
		imprimirDescendente(raiz.getSubIzq());
	}

	private void consultaNivelesInferiores(ArbolBinarioBusqueda<Producto> arbol) {
		byte niveles = (byte) arbol.Altura();
		for(byte i= niveles; i > 1; i--) {
			if(nodosEnUnNivel(arbol.getRaiz(),i,1) < nodosEnUnNivel(arbol.getRaiz(),i-1,1))
				System.out.println("El nivel "+i+" tiene menos nodos que el nivel superior");
		}
	}
}
