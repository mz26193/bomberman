        
        /**
         * Simulacion de juego de Bomberman
         * 
         * Margarito Zarate Santiago
         * @version (21-04-2017)
         * 
         * En la parte inferior del programa se encuentra el método principal , en donde 
         * se pueden ingresar los valores con los que se realizará la simulación.
         * 
         * La matriz al inicio se imprime vacia .
         * La letra B simboliza una Bomba
         * La letra X simboliza una Bomba que estallo
         * El * simboliza un espacio vacío 
         */
        public class Bomberman
        {
        
            private int[][] coordenadas;       // matriz que almacena las coordenadas de las bombas
            private String [][] tablero;       // matriz de MxN que representa el tablero
            private int [][] bombasAlcanzadas; // Se guardan en una pila las bombas que van siendo alcanzadas
               
            private int filas;                // filas de la matriz
            private int columnas;             // columnas de la matriz
            private int radiacion;            // numero de posiciones que alcanza despues de la bomba que explota
            private int bombas;               // no de bombas
            private int bombasExplotadas;     // representa el total de bombas que explotaron 
            private int cimaPila=0;           // representa la pila de bombas alcanzadas
            
            /*declaracion de constantes;*/
            private static final String SIMBOLO_VACIO="* ";
            private static final String SIMBOLO_BOMBA="B ";
            private static final String SIMBOLO_EXPLOTA="X ";
            
            /* constructor*/
            
            public Bomberman(int fil,int col, int rad,int bom)
            {
                bombasExplotadas=0;
                filas=fil;
                columnas=col;
                radiacion=rad;
                bombas=bom;
            }
        
          
            //método que inicia la simulación, como parámetro se le pasa un arreglo de coordenadas
            public int iniciarJuegoBomberman(int[][] arregloCoordenadas)
            {
                 coordenadas=arregloCoordenadas;
                 tablero=new String [filas][columnas] ;
                //cargamos el tablero de MxN (Filas, Columnas)          
                   for(int i=0; i<filas;i++){
                    for (int j=0;j<columnas;j++){
                       tablero[i][j]=SIMBOLO_VACIO;
                    }
                }
                
                //colocar las bombas
                for(int c=0;c<coordenadas.length;c++)
                {
                 ubicarBomba(coordenadas[c][0]-1,coordenadas[c][1]-1);   
                }
                System.out.println("\nColocando bombas");
                imprimirTablero();
                
                //explotar la primer bomba             
                iniciarDetonaciones(); 
                return bombasExplotadas;
            }
            
            //método que provoca una reaccion en cadena
            public void iniciarDetonaciones(){             
                 //inicializamos el arreglo que guarda las coordenadas de las bombas alcanzadas
                 bombasAlcanzadas= new int [coordenadas.length][2];
                 //detona la primer bomba
                 //a las coordenadas les restamos una unidad para manipular la matriz desde su indice 0
                 System.out.println("\nIniciando detonaciones con una radiación de "+radiacion+" unidades"+
                 " en las coordenadas: "+ coordenadas[0][0]+" , "+coordenadas[0][1]);
                 
                 detonar(coordenadas[0][0]-1,coordenadas[0][1]-1);  
                 imprimirTablero();
                 System.out.println("\nTotal de bombas explotadas "+bombasExplotadas); 
  
            }
            //metodo que detona una a una cada bomba y incrementa el no. de bombas detonadas
             public void detonar(int x,int y){
                
                 //calcular radiacion hacia abajo 
                 int tempX=x;
                 int tempY=y;
                
                 
                 for (int f=0;f<radiacion;f++){
                     //radiacion abajo
                     if(abajo(tempX,tempY) ){
                         if(tablero[tempX+1][tempY].equals(SIMBOLO_BOMBA)){ //hay una bomba abajo?
                         agregarCoordenadaRadiacion(tempX+1,tempY);
                        }
                        tempX++; //para bajar incrementamos
                        }
                    
                    }
                    
                 //calcular radiacion hacia izquierda
                 //reiniciar 
                 tempX=x;
                 tempY=y;
                 for (int f=0;f<radiacion;f++){
                     //radiacion izquierda
                     if(izquierda(tempX,tempY)){
                         if(tablero[tempX][tempY-1].equals(SIMBOLO_BOMBA)){// hay bomba izquierda?
                          agregarCoordenadaRadiacion(tempX,tempY-1);
                        }
                         tempY--; //para ir a izq restamos
                        }
                    
                    }
                    
                 //calcular radiacion hacia arriba
                 //reiniciar
                 tempX=x;
                 tempY=y;
                 for (int f=0;f<radiacion;f++){
                     //radiacion arriba
                     if(arriba(tempX,tempY)){
                         if(tablero[tempX-1][tempY].equals(SIMBOLO_BOMBA)){ //hay bomba arriba
                          agregarCoordenadaRadiacion(tempX-1,tempY);
                        }
                         tempX--; //para subir restamos
                        }
                    
                    }
                    
                  //calcular radiacion hacia derecha
                  //reiniciar 
                 tempX=x;
                 tempY=y;
                 for (int f=0;f<radiacion;f++){
                     //radiacion derecha
                     if(derecha(tempX,tempY)){
                         if(tablero[tempX][tempY+1].equals(SIMBOLO_BOMBA)){
                         agregarCoordenadaRadiacion(tempX,tempY+1);
                        }
                         tempY++; //para ir a la derecha incrementamos
                        }
                    
                    }
                 tablero[x][y]=SIMBOLO_EXPLOTA;
                 bombasExplotadas++;
                 
                // recursivo                    
                 if(cimaPila>0){
                 cimaPila--;
                 detonar(bombasAlcanzadas[cimaPila][0],bombasAlcanzadas[cimaPila][1]);
                }
            }
            
            public void ubicarBomba(int x,int y){
               tablero[x][y]=SIMBOLO_BOMBA;
            }
            
            public void agregarCoordenadaRadiacion(int x,int y){
                bombasAlcanzadas[cimaPila][0]=x;
                bombasAlcanzadas[cimaPila][1]=y;
                cimaPila++;
            }
            
            public void imprimirTablero(){

                  for(int a=0; a<filas;a++){
                     System.out.print("\n");
                    for (int b=0;b<columnas;b++){
                       System.out.print(tablero[a][b]);
                    }
                }
                 System.out.print("\n");
            }
                       
            // metodo para verificar si es posible deszplazar hacia abajo
            public boolean abajo(int x,int y){
                
               if(x<filas-1){
                   return true;
                }else
                {
                    return false;
                }
            }
            
            // metodo para verificar si es posible deszplazar hacia la izquiera
            public boolean izquierda(int x,int y){
                 if(y>0){
                   return true;
                }else
                {
                    return false;
                }
            }
            
           // metodo para verificar si es posible deszplazar hacia arriba
            public boolean arriba(int x,int y){
                 if(x>0){
                   return true;
                }else
                {
                    return false;
                }
            }
            
            // metodo para verificar si es posible deszplazar hacia la derecha
              public boolean derecha(int x,int y){
                 if(y<columnas-1){
                   return true;
                }else
                {
                    return false;
                }
            }
         
         
            /* Metodo principal 
             * Indique las coordenadas 
             * 
             */
            public static void main (String [] args){
                //Construimos el juego con el tamaño de FILAS, COLUMNAS, RADIACION Y BOMBAS
                Bomberman bomberman = new Bomberman(8,8,5,10);
                //Iniciamos el juego indicando la posicion de las bombas        
                bomberman.iniciarJuegoBomberman(new int[][] {{6,6},{8,6},{8,1},{6,4},{5,1},{5,7},{4,5},{2,3},{2,7},{4,8},{1,6}});     
            }
        }
