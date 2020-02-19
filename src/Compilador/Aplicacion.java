package Compilador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Aplicacion extends JFrame implements ActionListener{
    private JPanel centro;
    JToolBar jtbMainP;
    JButton jbtnNuevo,jbtnGuardar,jbtnAbrir,JbtnSalir,Scan,parser,TreeMode,JTriple;
    static JTextArea textarea1,weadetokens;
    JLabel jlerror;
    JFileChooser abrirArchivo;
    public Parser pars;
    
    private void bueno(){
        centro = new JPanel();
        centro.setLayout(null);
        textarea1=new JTextArea();
        textarea1.setFont(new Font("Consolas",Font.PLAIN,17));
        JScrollPane scrollpane1=new JScrollPane(textarea1);
        scrollpane1.setBounds(10,50,406,229);
        centro.add(scrollpane1);
        JLabel eti = new JLabel("Programa");
        eti.setBounds(10,20,100,30);
        centro.add(eti);
        parser = new JButton("Parser");
        parser.addActionListener(this);
        parser.setBounds(10,304,100,30);
        centro.add(parser);
        getContentPane().add(centro,BorderLayout.CENTER);
    }
    
    private void BarraHerramientas() {
        //configuracion general
        jtbMainP = new JToolBar();
	jtbMainP.setFloatable(false);
	jtbMainP.setToolTipText("Barra De Herramientas Principal");//muestra un globo donde aparrese el nombre  cuando se pone el cursor
       
    jbtnAbrir = new JButton("Abrir");
	jbtnAbrir.addActionListener(this);
	jbtnAbrir.setToolTipText("Abre Un Archivo Valido");
	jtbMainP.add(jbtnAbrir);
    
	getContentPane().add(jtbMainP,BorderLayout.NORTH);	
    }
    
    public Aplicacion(){
        super();
	getContentPane().setLayout(new BorderLayout());
	setSize(new Dimension(447, 530));
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setResizable(true);
	setTitle("");
        BarraHerramientas();
        bueno();
        setVisible(true);
    }
    
    public static void main(String[] args){
        Aplicacion mf = new Aplicacion();
        mf.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(e.getSource()==parser){
            if(textarea1.getText().equals("")){
                JOptionPane.showMessageDialog(null,"No puedes Compilar Codigo en Blanco");
            }else{
                try{
                    pars = new Parser(textarea1.getText().toString());
                    System.out.println(pars.toString());
                }catch(Exception err){
                   JOptionPane.showMessageDialog(null,"Error las clases deben de iniciar con la palabra class");
                }
            }
        }
        JButton btn = (JButton)e.getSource();
        if( btn.getText().equals( "Abrir" ) )
        {
            if( abrirArchivo == null ) abrirArchivo = new JFileChooser();
            //Con esto solamente podamos abrir archivos
            abrirArchivo.setFileSelectionMode( JFileChooser.FILES_ONLY );
 
            int seleccion = abrirArchivo.showOpenDialog( this );
 
            if( seleccion == JFileChooser.APPROVE_OPTION )
            {
                File f = abrirArchivo.getSelectedFile();
                try
                {
                    String nombre = f.getName();
                    String path = f.getAbsolutePath();
                    String contenido = getArchivo( path );
                    //Colocamos en el titulo de la aplicacion el 
                    //nombre del archivo
                    this.setTitle( nombre );
 
                    //En el editor de texto colocamos su contenido
                    textarea1.setText( contenido );
 
                }catch( Exception exp){}
            }
        }
    }
    //-------------------------Se obtiene el contenido del Archivo----------------//
    public String getArchivo( String ruta ){
        FileReader fr = null;
        BufferedReader br = null;
        //Cadena de texto donde se guardara el contenido del archivo
        String contenido = "";
        try
        {
            //ruta puede ser de tipo String o tipo File
            fr = new FileReader( ruta );
            br = new BufferedReader( fr );
 
            String linea;
            //Obtenemos el contenido del archivo linea por linea
            while( ( linea = br.readLine() ) != null ){ 
                contenido += linea + "\n";
            }
 
        }catch( Exception e ){  }
        //finally se utiliza para que si todo ocurre correctamente o si ocurre 
        //algun error se cierre el archivo que anteriormente abrimos
        finally
        {
            try{
                br.close();
            }catch( Exception ex ){}
        }
        return contenido;
    }
    //-----------------------------------------------------------------------------//
 
    static void setAtributo (int cntBC, String pila[]){
        System.out.println("Pila llega");
        String bytecode = "";
        for (int i = 0; i < cntBC; i++) {
            System.out.println(pila[i]);
            bytecode += pila[i]+"\n";
        }
        weadetokens.setText(bytecode);
    }   
}

