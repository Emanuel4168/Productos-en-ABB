package com.ProductosABB;

public class ArbolBinarioBusqueda <T> {
	private NodoABB<T> Raiz;
	public  T          Dr;
	public ArbolBinarioBusqueda(){
		Raiz=null;
	}
	public NodoABB<T> getRaiz(){
		return Raiz;
	}
	public boolean Inserta( T Dato){
		if(Dato==null)
			return false;
		if(Raiz==null){
			Raiz=new NodoABB<T>(Dato);
			return true;
		}
		return Inserta(Raiz,Dato);
	}
		
	private boolean Inserta(NodoABB<T> Raiz, T Dato){
		String IdDato=Dato.toString();
		String IdRaiz=Raiz.Info.toString();
		if(IdDato.compareToIgnoreCase(IdRaiz)==0)
			return false;
		if(IdDato.compareToIgnoreCase(IdRaiz)<0){
			if(Raiz.getSubIzq()!=null)
				return Inserta(Raiz.getSubIzq(),Dato);
			else{
				
				Raiz.setSubIzq(new NodoABB<T>(Dato));
				return true;
			}
		}
		else {
			if(Raiz.getSubDer()!=null)
				return Inserta(Raiz.getSubDer(),Dato);
			else{
				
				Raiz.setSubDer(new NodoABB<T>(Dato));
				return true;
			}			
		}
	}
	public boolean RetiraRecursivo( T Dato) {
		if(Raiz==null)
			return false;
		return RetiraRecursivo(Raiz, Dato);
	}
	private boolean RetiraRecursivo(NodoABB<T> R, T Dato) {
		if (R==null)
			return false;

		String Llave=Dato.toString();

		if(Llave.compareTo( R.Info.toString()  )  < 0   )
		{
			return RetiraRecursivo(R.getSubIzq(),Dato);
		}
		else {
			if(  Llave.compareTo( R.Info.toString()  )  > 0   ) {
				return RetiraRecursivo(R.getSubDer(),Dato);
			}
			// Encontro el nodo
			Dr=R.Info;
			if(R.getSubIzq() != null && R.getSubDer() != null) {
				NodoABB<T> Temp=R.getSubDer();
				while(Temp.getSubIzq()!=null)
					Temp=Temp.getSubIzq();
				R.Info=Temp.Info;
				Dato=Temp.Info;
				return RetiraRecursivo(R.getSubDer(),Dato);
			}

			else {

				if(R.getSubDer() == null)
					R.setSubIzq(R.getSubIzq());
				else
					R.setSubDer(R.getSubDer());
			}
			return true;
		}
	}
	
	public boolean Borrar(T Dato){
		if(Raiz==null)
			return false;
		return Borrar(Raiz, Dato);
	}
	private boolean Borrar(NodoABB<T> R,T Infor){
		boolean b=true;
	  	NodoABB<T> Ant=null;
	  	String Llave=Infor.toString();
	  	while (R != null) {
	  	   if ( Llave.compareTo( R.Info.toString()  )  < 0 )
	  	   {
	  	      Ant=R;
	  	      R=R.getSubIzq();
	  	      b=false;
	  	   }
	  	   else
	 	          if ( Llave.compareTo( R.Info.toString()  )  > 0 )
	 	          {
	 	          	 Ant=R;
	  	         R=R.getSubDer();
	  	         b=true;
	  	      }
	  	      else
	  	         break;// Localizo el nodo
	  	}
	  	if (R==null)
	  	   return false;
	  	if (R.getSubIzq() != null && R.getSubDer() != null)  // Tiene dos hijos
	  	{
	  	    // Buscamos el nodo mas pequeño del subárbol derecho
	  		NodoABB<T> Temp=R.getSubDer();
	  		NodoABB<T> Aux=R;
	  		boolean RamaIzq=false;
	  		while (Temp.getSubIzq() != null)
	  		{
	  		   Aux=Temp;
	  		   Temp=Temp.getSubIzq();
	  		   RamaIzq=true;
	  		}
	  	    // sustituyo valor
	  	    Dr = R.Info;
	  		R.Info = Temp.Info;
	          // Elimino el nodo que sustituye
	  		if (RamaIzq)
	  		{
	  		   if (Temp.getSubIzq() == null)
	  		      Aux.setSubIzq(Temp.getSubDer());
	  		   else
	  		      Aux.setSubIzq(Temp.getSubIzq());
	  		}
	  		else
	  		{
	  		      Aux.setSubDer(Temp.getSubDer());
	  		}
	          return true;
	  	}
	  	else
	  	{
	  		if (R==Raiz)//Borrando la raiz y puede tener uno o cero hijos
	  		{
	  			Dr=R.Info;
	  			if (R.getSubIzq()==null)
	  			   Raiz=R.getSubDer();
	  			else
	  			   Raiz=R.getSubIzq();
	  	    }
	  	    else // Resto de nodos con cero o un hijo
	  	    {
	  	    	Dr=R.Info;
	  		   if (R.getSubIzq() == null)
	  		      if (b)
	  		         Ant.setSubDer(R.getSubDer());
	  		      else
	  		         Ant.setSubIzq(R.getSubDer());
	  		   else
	  		      if (b)
	  		         Ant.setSubDer(R.getSubIzq());
	  		      else
	  		         Ant.setSubIzq(R.getSubIzq());
	  		}
	  	}
	  	return true;		
		
		
	}
	int Alto;
	public int Altura(){
		if(Raiz==null)
			return 0;
		Alto=0;
		return Altura(Raiz,1);
	}
	private int Altura(NodoABB<T> Raiz,int Nivel){
		if(Raiz==null)
			return Nivel;

		Altura(Raiz.getSubIzq(),Nivel+1);
		if(Nivel>Alto)
			Alto=Nivel;
		Altura(Raiz.getSubDer(),Nivel+1);

		return Alto;
	}	
	public void InOrden(NodoABB<T> Raiz){
		if(Raiz==null)
			return;
		InOrden(Raiz.getSubIzq());
		System.out.println(Raiz.Info);
		InOrden(Raiz.getSubDer());
	}
	public void PreOrden(NodoABB<T> Raiz){
		if(Raiz==null)
			return;
		System.out.println(Raiz.Info);
		PreOrden(Raiz.getSubIzq());
		PreOrden(Raiz.getSubDer());
	}
	public void PostOrden(NodoABB<T> Raiz){
		if(Raiz==null)
			return;
		PostOrden(Raiz.getSubIzq());
		PostOrden(Raiz.getSubDer());
		System.out.println(Raiz.Info);
	}
	public int Length(){
		return Length(Raiz);
	}
	public int Length(NodoABB<T> Raiz ){
		if(Raiz==null)
			return 0;
		return Length(Raiz.getSubIzq())+1+Length(Raiz.getSubDer());
	}
	
}	
