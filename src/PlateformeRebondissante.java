//Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.scene.paint.Color;

public class PlateformeRebondissante extends Plateforme {

    /**
     * Constructeur de la plateforme
     * @param i numéro de la plateforme
     */
    public PlateformeRebondissante(int i) {
        super(i);
        this.color = Color.LIGHTGREEN;
    }


    /**
     * Teste si la méduse est en collision avec la plateforme
     * Si oui, la vitesse verticale de la méduse est amplifiée
     * @param other Méduse du jeu
     */
    @Override
    public void testCollision(Meduse other) {
        if (intersects(other) && Math.abs(other.posY + other.hauteur - this.posY) < 10 && other.vy > 0) {
            pushOut(other);
            other.vy *= -1.5;

            if (other.vy > -100) {
                other.vy = -100;
            }

            other.setPfTouchee(this);
            this.contactMeduse = true;
        }
    }
}
