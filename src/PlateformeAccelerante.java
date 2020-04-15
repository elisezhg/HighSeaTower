//Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.scene.paint.Color;

public class PlateformeAccelerante extends Plateforme {

    /**
     * Constructeur de la plateforme
     * @param i numéro de la plateforme
     */
    public PlateformeAccelerante(int i) {
        super(i);
        this.color = Color.rgb(230, 221, 58);
    }


    /**
     * Teste si la méduse est en collision avec la plateforme
     * Si oui, la vitesse du jeu est accélérée
     * @param other Méduse du jeu
     */
    @Override
    public void testCollision(Meduse other) {
        if (intersects(other) && Math.abs(other.posY + other.hauteur - this.posY) < 10 && other.vy > 0) {
            pushOut(other);
            other.vy = 0;
            Jeu.vitesseAcceleree = true;

            other.setPfTouchee(this);
            this.contactMeduse = true;
        }
    }
}
