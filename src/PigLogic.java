import java.util.concurrent.ThreadLocalRandom;  //esta libreria permite crear un azar para los dados

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PigLogic {
	int resultadoJugador = 0; //cuenta el resultado acumulado del jugador
	int resultadoCPU = 0;
	int temp = 0; //da el resultado de la tira actual de dados que se puede acumular si se pasa
	int dado1 =0; 
	int dado2 = 0; //resultado dado2 cada vez que se tira
	

	public PigLogic(){
		
	}

    public int dado1(){
    int dado = ThreadLocalRandom.current().nextInt(1,6 + 1); //elije un númeral alazar entre 1 y 6
    return dado;
    }
    public int dado2(){
    int dado = ThreadLocalRandom.current().nextInt(1,6 + 1);
    return dado;
    }
    
    public int temporal(int dado1, int dado2){ //toma las instacias de los dados para así determinar una acción con su resultado
    	
    	int suma = 0; //lo que se devuelve
        
        if (dado1 == 6 && dado2 ==6){  //en este caso se pierde turno y puntaje (devuelve -1)
        suma = -1;
        } else {
           if (dado1 == 6 || dado2 == 6){ //se pierde puntaje  (devuelve 0)
               suma = 0;
           } else {
               suma = dado1 + dado2;  // se suma al temporal (devuelve el resultado de ambos dados
           }}
            
         return suma;
    }
        
    public int permanente(int temp){ //toma permanente y lo vuelve parte de el una vez se pasa el turno
        int suma = 0;
        if (temp != -1){  
            suma = suma + temp;
        } else {  //se asegura que temporal no le devuelva un dato invalido
            suma = 0;
        }
        return suma; 
    }
    
    public void inicio(){	  
 	   JFrame frame = new JFrame(); //objeto que contiene array el cual da las opciones de empezar el juego o salir de él
    	String[] options = new String[2];
    	options[0] = new String("Jugar");
    	options[1] = new String("Salir");
 	   int comienzo = JOptionPane.showOptionDialog(frame.getContentPane(),"Bienvenido a Pig!","Pig", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
 	   //interface que le presenta al jugador las opciones
 	   
 	   switch(comienzo){
 	   case 0:             //si elije jugar, comienza el juego
 		   this.juego();
 		   break;
 	   case 1:             //si elije salir, le presenta la opción de realmente salir o volver al menu
 	    	String[] opciones = new String[2];
 	    	opciones[0] = new String("Volver");
 	    	opciones[1] = new String("Salir");
 		    int x =JOptionPane.showOptionDialog(frame.getContentPane(),"Realmente desea salir?","Pig", 0,JOptionPane.INFORMATION_MESSAGE,null,opciones,null);
		    		    
 		   if (x == 0){         //vuelve al menu
 			   this.inicio();
 		   } else {            //sale del juego
 			   System.exit(0);
 		   }
 	   }
    }
    public void juego(){
		int d1 = dado1(); //genera una instancia de dado que sea igual a la que utiliza temporal para poder tamarla por separado
	    int d2 = dado2();
    	int valor = temporal(d1, d2);
    	if (resultadoJugador >= 100){ // revisa si se ha llegado al puntaje mayor de 100
    		JOptionPane.showMessageDialog(null, "Felicidades ha ganado!");  //termina el juego en gane y vuelve al menu principal
    		inicio();
    	} else {
    		if (resultadoCPU >=100){
    			JOptionPane.showMessageDialog(null, "Has perdido");
    			inicio();
    		} else {
    			JFrame frame = new JFrame(); //objecto formado por un array para asignar el nombre de los botones y su valor
    			String[] options = new String[2];
    	        options[0] = new String("Tirar");
    	        options[1] = new String("Pasar");
    	        int x = JOptionPane.showOptionDialog(frame.getContentPane(),"Jugador: "+resultadoJugador+"              " + "CPU: " + resultadoCPU + "\n" + "                          "+temp+ "\n"+ " Dado 1: "+dado1+"              Dado 2: "+dado2,"Pig", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
    	        //lo que el jugador ve
    	        
            switch (x){                    //actua segun el jugador tiro o paso en el turno
    		case 0:
    		    if (valor > 0){            // si los dados no poseen un 6, suma el resultado en el temporal
    		    	 temp = temp + valor;
    	    		    dado1 = d1;
    	    		    dado2 = d2;
    	    		    d1 = 0;
    	    		    d2 = 0;
    		    } else { 
    		    	if (valor == 0){       //si contienen un solo 6 borra el temporal y pasa el turno
    		    		temp = 0;
    		    		dado1 = d1;
    	    		    dado2 = d2;
    	    		    d1 = 0;
    	    		    d2 = 0;
    	    		    this.juegoCpu();
    		    	} else {              // si los dados dan dos 6 como resultado, borra temopral y el puntaje acumulado, luego pasa el turno 
    		    		temp = 0;
    		    		dado1 = d1;
    	    		    dado2 = d2;
    	    		    d1 = 0;
    	    		    d2 = 0;
    		    		resultadoJugador = 0;
    		    		this.juegoCpu();
    		    	}
    		    }

    		    valor = 0;
    		    juego();
    		    break;
    		    
    		case 1: //si se decide pasar el turno se suma el temporal al puntaje acumulado y se pasa el turno
    			dado1 = 0;
    		    dado2 = 0;
    		    resultadoJugador = resultadoJugador + permanente(temp);
    		    temp = 0;
    		    this.juegoCpu();
    		    break;
    	    }
    	  }
    	}
    }
    
      
   
   
	public void juegoCpu(){     //turno de la máquina
		
		for(int i =0; i < 3; i++){  //la máquina tira 3 veces durante su turno
			int d1 = this.dado1();
		    int d2 = this.dado2();
		    int valor = temporal(d1, d2);
		   
   		    if (valor > 0){            //los comportamientos son similares al turno del jugador
   		    	 temp = temp + valor;
   	    		    dado1 = d1;
   	    		    dado2 = d2;
   	    		    d1 = 0;
   	    		    d2 = 0;
   		    } else { 
   		    	if (valor == 0){
   		    		temp = 0;
   		    		dado1 = d1;
   	    		    dado2 = d2;
   	    		    d1 = 0;
   	    		    d2 = 0;
   	    		    
   		    	} else {
   		    		temp = 0;
   		    		dado1 = d1;
   	    		    dado2 = d2;
   	    		    d1 = 0;
   	    		    d2 = 0;
   		    		resultadoCPU = 0;
   		    		
   		    	}
   		    }
            resultadoCPU = resultadoCPU + temp;
            temp = 0;
   		    valor = 0;
   		    juego();  
	   }
	}
    public static void main(String[] args) { 
        PigLogic juego = new PigLogic(); 
        juego.inicio(); //ejecuta la instacia para comenzar el juego
    }
}


