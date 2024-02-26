package Erpegkraj;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class Interfejs{

    public static JFrame okno = new JFrame();

    public static Font Judical;

    public static void main(String[] args) throws IOException {

        /*String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for(String s : fonts){
            System.out.println(s);
        }*/

        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setResizable(false);
        okno.setTitle("Erpegkraj");
        okno.setUndecorated(true);

        InputStream is = Interfejs.class.getResourceAsStream("/czcionka/Judical.ttf");
        try{
            Judical = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch(FontFormatException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        PanelGry panelGry = new PanelGry();
        okno.add(panelGry);

        okno.pack();

        panelGry.przygotujGrę();
        panelGry.zacznijWątekGry();

        okno.setLocationRelativeTo(null);
        okno.setVisible(true);
    }

    public static void ustawOkno(boolean czyPełnoekranowy){
    }
    public static Font ustalCzcionkę(){
        return Judical.deriveFont(Font.PLAIN, 22.5f);
    }
}