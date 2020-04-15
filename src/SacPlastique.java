//Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.scene.image.Image;

public class SacPlastique extends Item {

    /**
     * Constructeur du sac plastique
     * @param pf plateforme sur laquelle se trouve le sac plastique
     */
    public SacPlastique(Plateforme pf) {
        super(pf);
        img = new Image("res/plasticbag.png");
    }


    /**
     * Teste si la méduse est en collision avec le sac
     * Si oui, la méduse est ralentie
     * @param other Méduse du jeu
     */
    @Override
    public void testCollision(Meduse other) {
        if (intersects(other)) {
            other.setRalentie();    // la méduse est ralentie
            visible = false;        // le sac n'est plus visible
        }
    }
}
