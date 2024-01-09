package Erpegkraj.Grafika;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Przerozmierzacz {
    public static BufferedImage przerozmierzanie(BufferedImage img, int szerokość, int wysokość){
        Image image = img.getScaledInstance(szerokość,wysokość, Image.SCALE_SMOOTH);
        BufferedImage rysPoPrzeróbce = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = rysPoPrzeróbce.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();

        return rysPoPrzeróbce;
    }
}
