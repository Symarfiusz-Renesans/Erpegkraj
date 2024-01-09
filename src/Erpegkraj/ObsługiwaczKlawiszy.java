package Erpegkraj;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ObsługiwaczKlawiszy implements KeyListener {

    public boolean góraWciśnięta, dółWciśnięty, lewoWciśnięte, prawoWciśnięte, potwierdźWciśnięte, cofnijWciśnięte;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int kod = e.getKeyCode();
        if (kod == KeyEvent.VK_W){
            góraWciśnięta = true;
        }
        if (kod == KeyEvent.VK_S){
            dółWciśnięty = true;
        }
        if (kod == KeyEvent.VK_D){
            prawoWciśnięte = true;
        }
        if (kod == KeyEvent.VK_A){
            lewoWciśnięte = true;
        }
        if (kod == KeyEvent.VK_ENTER){
            potwierdźWciśnięte = true;
        } else if (kod == KeyEvent.VK_ESCAPE) {
            cofnijWciśnięte = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int kod = e.getKeyCode();
        if (kod == KeyEvent.VK_W){
            góraWciśnięta = false;
        }
        if (kod == KeyEvent.VK_S){
            dółWciśnięty = false;
        }
        if (kod == KeyEvent.VK_D){
            prawoWciśnięte = false;
        }
        if (kod == KeyEvent.VK_A){
            lewoWciśnięte = false;
        }
        if (kod == KeyEvent.VK_ENTER) {
            potwierdźWciśnięte = false;
        } else if (kod == KeyEvent.VK_ESCAPE) {
            cofnijWciśnięte = false;
        }
    }
}
