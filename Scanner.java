package Compilador;


import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class Scanner {
    
    //DECLARACIONES
    private int lineaNo, k;
    private String[] tokens = null;
    private String tipoToken;
    private String token;
    private final String[] reservadas = {"Class","boolean","int","float","until","do","system.in.readln","true","false"};
    private final String[] operadores = {"<","+","-","*","="};
    private final String[] delimitador = {"(",")","{","}",";","'"};
    
    //CONSTRUCTOR
    public Scanner(String codigo) {
        tokens = codigo.split("\\s+");
        System.out.println(tokens);
        lineaNo = 0; //Aun sin implementar...
        k=0;
        token = "";
    }
    
    //METODO que retorna tokens validos al parser
    public String getToken(boolean b) {
        boolean tokenValido = false;
        token = tokens[k];
        if(b) {
            if(k < tokens.length-1) {
                k++;
            }
        }
                
        //VERIFICACICION LEXXICA
        //Palabras reservadas:
        for (String reservada : reservadas) {
            if (token.equalsIgnoreCase(reservada)) {
                tokenValido = true;
                setTipoToken("Palabra reservada", b);
                break;
            }
        }
            //Operadores:
        if(!tokenValido) {
            for(String operador : operadores) {
                if(token.equals(operador)) {
                    tokenValido = true;
                    setTipoToken("Operador", b);
                    break;
                }
            }
        }
            //Delimitador:
        if(!tokenValido) {
            for (String delimitador: delimitador) {
                if(token.equals(delimitador)) {
                    tokenValido = true;
                    setTipoToken("Delimitador", b);
                    break;
                }
            }
        }
        
            //Identificadores:
        if(!tokenValido) {
            if(validaIdentificador(token)) {
                tokenValido = true;
                setTipoToken("Identificador", b);
            }
        }
        
        if(!tokenValido) {
            if(validaInteger(token)) {
                tokenValido = true;
                setTipoToken("Integer", b);
            }
        }
        if(!tokenValido) {
            if(validaBoolean(token)) {
                tokenValido = true;
                setTipoToken("Boolean", b);
            }
        }
        
        if(!tokenValido) {
            if(validaFloat(token)) {
                tokenValido = true;
                setTipoToken("Float", b);
            }
        }
        
        if(!tokenValido) {
            if(validaString(token)) {
                tokenValido = true;
                setTipoToken("String", b);
            }
        }
        
        //Final of program
        if (!tokenValido) {
            if (token.equals("<eof>")) {
                tokenValido = true;
                setTipoToken("Final", b);
            }
        }
        
            //Error:
        if(!tokenValido) {
            error("el token \"" + token + "\" es invalido para el lenguaje.");
            return "TOKEN INVALIDO";
        }
        return token;
    }
    
    public boolean validaIdentificador(String t) {
        boolean tokenValido = false;
        char[] charArray;
        charArray = t.toCharArray();
        int i=0;
        
       //Validacion del primer caracter:
        if((charArray[i]>='a' && charArray[i]<='z') || 
                (charArray[i] >= 'A' && charArray[i] <= 'Z') ||
                (charArray[i]=='_') || (charArray[i]=='-') || (charArray[i]>='0' && charArray[i]<='9')){
            tokenValido = true;
        }
        //Validacion del resto del token (si su longitud es mayor a 1):
        if(t.length() > 1 && tokenValido) {
            for(int j=1 ; j<charArray.length ; j++) {
                if((charArray[j]>='a' && charArray[j]<='z') || 
                (charArray[j] >= 'A' && charArray[j] <= 'Z') ||
                (charArray[j]=='_') || (charArray[j]=='-') || (charArray[j]>='0' && charArray[j]<='9')){
                    tokenValido = true;
                }
            }
        }
        else if(t.length() > 1 && tokenValido) {
            tokenValido = false;
        }
        return tokenValido;
    }
    
    public boolean validaFloat(String t) {
   	 boolean tokenValido = false;
   	 t = token;
   	 if(token.matches("^[+][0-9]+([.][0-9]+)?$") || token.matches("^[-][0-9]+([.][0-9]+)?$")){
            tokenValido = true;
        }else {
       	 tokenValido = false;
        }
   	 return tokenValido; 
   }
    
    public boolean validaString(String t) {
     	 boolean tokenValido = false;
     	 t = token;
     	 if (token.matches("['A'-'Z'] ['a'-'z'].*")){
              tokenValido = true;
          }else {
         	 tokenValido = false;
          }
     	 return tokenValido;
     }
    
    public boolean validaInteger(String t) {
  	 boolean tokenValido = false;
  	 t = token;
  	 if (token.matches("^[0-9]+$")){
           tokenValido = true;
       }else {
      	 tokenValido = false;
       }
  	 return tokenValido;
    }
    public boolean validaBoolean(String t) {
      	 boolean tokenValido = false;
      	 t = token;
      	 if(token.matches("true") || token.matches("false")){
               tokenValido = true;
           }else {
          	 tokenValido = false;
           }
      	 return tokenValido;
      	 
      }
    
    public boolean isNumeric(String str) {
        return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("")==false);
    }
    
    public void setTipoToken(String tipo, boolean b) {
        if(b) {
            tipoToken = tipo;
            }
    }
       
    public String getTipoToken() {
        return tipoToken;
    }
    
    public String checkNextToken() {
        return tokens[k];
    }
    
    public void error(String error) {
        switch(JOptionPane.showConfirmDialog(null,
                "Error lexico: " + error + ".\n"
                        + "¿Desea detener la ejecucion?",
                "Ha ocurrido un error",
                JOptionPane.YES_NO_OPTION)) {
            case JOptionPane.NO_OPTION:
                int e = (int) 1.1;
                break;
                    
            case JOptionPane.YES_OPTION:
                System.exit(0);
                break;
        }
    }
    
}
