package Erpegkraj;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Interfejs{

    public static JFrame okno = new JFrame();

    public static void main(String[] args) throws IOException {

        /*String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for(String s : fonts){
            System.out.println(s);
        }*/

        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setResizable(false);
        okno.setTitle("Erpegkraj");
        okno.setUndecorated(true);



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
}