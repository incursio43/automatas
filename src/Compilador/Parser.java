package Compilador;

import javax.swing.JOptionPane;
import ArbolSintactico.*;

import java.util.ArrayList;
import java.util.Vector;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils.*;

public class Parser {
    Programax p = null;
    String byteString;
    private int lineaNo = 0;
    private String tipo = null;
    private String identificador=null;
    private String valor=null;
    private boolean equals = false;
    boolean bandera = false;
    private final Scanner s;
    private ArrayList<Token> tablaSimbolos = new ArrayList();
    final int classx = 1, 
    		booleanx = 2, 
    		intx = 3, 
    		floatx = 4, 
    		untilx = 5, 
    		dox = 6, 
    		sirx = 7, 
    		truex = 8, 
    		falsex = 9, 
    		menorx = 10,
    		masx = 11, 
    		menosx = 12, 
    		porx = 13, 
    		igualx = 14, 
    		pix = 15, 
    		pdx = 16, 
    		li = 17, /*{*/
    		ld = 18 , /*}*/
    		semicolon = 19, 
    		eofx = 20, 
    		id = 21,
    		stringx = 22;
    @SuppressWarnings("unused")
	private int tknCode, tokenEsperado;
    private String token, tokenActual, log;
    
    public void validaTablaSimbolos(String token, int code) {
    	if(tipo == null && code == 1 || code == 2 || code == 3 || code == 4 || code == 22) {
    		tipo = token;
    	}
    	if(tipo != null && identificador == null && code == 21 ) {
    		identificador = token;
    	}
    	if(tipo != null && identificador != null && code != 14) {
    		Token tok = new Token(tipo,identificador,lineaNo,null,null,null);
    		tipo = null;
    		identificador = null;
    		equals = false;
    		tablaSimbolos.add(tok);
    	}
    	if(tipo != null && identificador != null && code == 14) {
    		equals = true;
    	} 
    	if(tipo != null && identificador != null && equals == true && code == 21) {
    		Token tok = new Token(tipo,identificador,lineaNo,token,null,null);
    		tablaSimbolos.add(tok);
    		tipo = null;
    		identificador = null;
    		equals = false;
    	}
    	if(tipo == null && identificador == null && equals == false && code == 21) {
    		System.out.println(token + code);
    		for(int i = 0; i<=tablaSimbolos.size(); i++) {
    			if(tablaSimbolos.get(i).getNombre() == token) {
    				bandera = true;
    	    		identificador = token;
    	    		System.out.println(tablaSimbolos.get(i).getNombre() + "  " + token);
    	    		System.out.println("valida Indetificador" + identificador);
    			}
    		}
    	}
    	if(tipo == null && identificador != null && code == 14  ) {
    		System.out.println("holis");
    		equals = true;
    	}
    	if(tipo == null && identificador != null && equals == true && code == 21) {
    		tablaSimbolos.forEach((n) -> ingresaValor(n, token));
    	}
    }
    public void ingresaValor(Token tok, String token) {
    	if(tok.getNombre()== identificador) {
    		tok.setValor(token);
    		tipo = null;
    		identificador = null;
    		equals = false;
    	}
    }
    public void impresionTablaSimbolos() {
    	tablaSimbolos.forEach((n) -> System.out.println(n));
    }
    
    public Parser(String codigo) {  
        s = new Scanner(codigo);
        token = s.getToken(true);
        tknCode = stringToCode(token);
        if(tknCode == 21 || tknCode == 17 || tknCode== 18) {
        	lineaNo++;
        }
        validaTablaSimbolos(token,tknCode);
        p = P();
        impresionTablaSimbolos();
    }
    
    //INICIO DE ANÁLISIS SINTÁCTICO
    public void advance() {
        token = s.getToken(true);
        tknCode = stringToCode(token);
        if(tknCode == 21 || tknCode == 17 || tknCode== 18) {
        	lineaNo++;
        }
        validaTablaSimbolos(token,tknCode);
    }
    
    public void eat(int t) {
        tokenEsperado = t;
        System.out.println("Linea numero: "+lineaNo);
        System.out.println("Token obtenido: "+tknCode);
        System.out.println("Token esperado: "+t);
        System.out.println("Tipo de token: "+s.getTipoToken());
        System.out.println(token);
        System.out.println("-------------------");
        if(tknCode == t) {
            setLog("Token: " + token + "\n" + "Tipo:  "+ s.getTipoToken());
            advance();
        }
        else{
            error(token, "token tipo:"+t);
        }
    }
    
    public Programax P() {
    	eat(classx);
    	eat(id);
    	eat(li);
        Declarax d = D();
        eat(li);
        Statx s = S();
        eat(ld);
        eat(ld);
        eat(eofx);
        success();
        return new Programax(d,s);     
    }
    
    public Declarax D() {

        Declarax d2;
    	if(tknCode == booleanx)
        {
            Typex t = T();
            String s = token;
            eat(id);
            Idx i1 = new Idx(token);
            eat(semicolon);
            d2 = D();
            return new Declarax(t, s);

        } else if(tknCode == intx)
        {
            Typex t = T();
            String s = token;
            eat(id);
            Idx i1 = new Idx(token);
            eat(semicolon);
            d2 = D();
            return new Declarax(t, s);

        } else if(tknCode == floatx )
        {
            Typex t = T();
            String s = token;
            eat(id);
            Idx i1 = new Idx(token);
            eat(semicolon);
            d2 = D();
            return new Declarax(t, s);

        }
        else if(tknCode == stringx )
        {
            Typex t = T();
            String s = token;
            eat(id);
            Idx i1 = new Idx(token);
            eat(semicolon);
            d2 = D();
            return new Declarax(t, s);

        }
      else{
    	  return null;
    	  }
    }
 
    public Typex T() {
        if(tknCode == intx) {
            eat(intx);
            return new Typex("int");
        }
        else if(tknCode == booleanx) {
            eat(booleanx);
            return new Typex("boolean");
        }
        else if(tknCode == floatx) {
        	eat(floatx);
        	return new Typex("float");
        }
        else if(tknCode == stringx) {
        	eat(stringx);
        	return new Typex("string");
        }
        else{
            error(token, "(int / boolean / float / string)");
            return null;
        }
    }
    
    public Statx S() { //return statement

    	Statx s1,s2;
        if(tknCode == li) {
            	eat(li);
            	s1 = S();
            	eat(ld);
                return null;

        }  else if (tknCode == dox)
        {
            	eat(dox);
            	eat(li);
            	s1 = S();
            	eat(untilx);
            	eat(pix);
            	Expx e1;
            	e1 = E();
            	eat(pdx);
            	eat(semicolon);
            	eat(ld);
            	return new Dox(s1, e1);
                
        }  else if (tknCode == sirx)
        {
            	Readx read;
                eat(sirx);
                eat(pix);
                Expx e2;
                e2 = E();
                eat(pdx);
                eat(semicolon);
                s1 = S();
                return new Readx(e2);
 
		}  else if (tknCode == id)
		{
            	Idx i;
            	Expx e3;
            	eat(id); 
            	i = new Idx(tokenActual);
            	eat(igualx);
            	e3 = E ();
            	eat(semicolon);
            	s1 = S();
                return new Asignax(i,e3);
        }
        return null;
    }

    
    public Expx E() {
        Idx i1, i2;
        String comp1, comp2;
       
        if(tknCode == id) {
            comp1 = token;
            i1 = new Idx(token);
            eat(id); 
            if(tknCode == menorx) 
            {
            		eat(menorx);
                    comp2 = token;
                    i2 = new Idx(comp2);
                    eat(id); //(tokenActual)
                    
                    System.out.println("Operación: " + comp1 + "<" + comp2);
                    return new Menorx(i1, i2);
            }  
            
            else if(tknCode == masx) 
            {
                    eat(masx);
                    comp2 = token;
                    i2 = new Idx(comp2);
                    eat(id); //(tokenActual)
                    System.out.println("Operación: " + comp1 + "+" + comp2);
                    return new Sumax(i1, i2);

            }
            else if(tknCode == menosx) 
            {   	
                	eat(menosx);
                    comp2 = token;
                    i2 = new Idx(comp2);
                    eat(id); //(tokenActual)
                    System.out.println("Operación: " + comp1 + "-" + comp2);
                    return new Restax(i1, i2);
                    
            }
            else if(tknCode == porx)
            {
                	
                	eat(porx);
                    comp2 = token;
                    i2 = new Idx(comp2);
                    eat(id); //(tokenActual)
                    System.out.println("Operación: " + comp1 + "*" + comp2);
                    return new Multix(i1, i2);
                     
            }
            return null;
       }
        else if(tknCode == truex){
           eat(truex);
           return null;
       }
        else if(tknCode == falsex){
           eat(falsex);
           return null;
       }
        else{
           error(token, "( < | + | - | * | / true / false / id / Integer / Float)");

           return null;
       }
    }
    
   
    //FIN DEL ANÁLISIS SINTÁCTICO
    
    
    
    public void error(String token, String t) {
        switch(JOptionPane.showConfirmDialog(null,
                "Error sintactico:\n"
                        + "El token:("+ token + ") no concuerda con la gramatica del lenguaje,\n"
                        + "se espera: " + t + ".\n"
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
    
    public void success() {
        switch(JOptionPane.showConfirmDialog(null,
                "El programa a terminado de analizar .\n"
                        + "¿Desea cerrar el programa?",
                "No ocurrieron errores",
                JOptionPane.YES_NO_OPTION)) {
            case JOptionPane.NO_OPTION:
                int e = (int) 1.1;
                break;
                    
            case JOptionPane.YES_OPTION:
                System.exit(0);
                break;
        }
    }
    
    public int stringToCode(String t) {
        int codigo = 0;
        switch(t) {
            case "class": codigo=1; break;    
            case "boolean": codigo=2; break;
            case "int": codigo=3; break;
            case "float": codigo=4; break;
            case "until": codigo=5; break;
            case "do": codigo=6; break;
            case "system.in.readln": codigo=7; break;
            case "true": codigo=8; break;
            case "false": codigo=9; break;
            case "<": codigo=10; break;
            case "+": codigo=11; break;
            case "-": codigo=12; break;
            case "*": codigo=13; break;
            case "=": codigo=14; break;
            case "(": codigo=15; break;
            case ")": codigo=16; break;
            case "{": codigo=17; break;
            case "}": codigo=18; break;
            case ";": codigo=19; break;
            case "<eof>": codigo=20;break;
            default: codigo=21; break;
            case "String": codigo=22;break;
        }
        return codigo;
    }
     
    //Métodos para recoger la información de los tokens para luego mostrarla
    public void setLog(String l) {
        if(log == null) {
            log = l + "\n \n";
        }
        else{
            log=log + l + "\n \n";
        }      
    }
     
    public String getLog() {
        return log;
    }   
    
}
