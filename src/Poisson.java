//Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.scene.image.Image;

public class Poisson extends Item {

    /**
     * Constructeur du poisson
     * @param pf plateforme sur laquelle se trouve le poisson
     */
    public Poisson(Plateforme pf) {
        super(pf);
        img = new Image("res/fish.png");
    }


    /**
     * Teste si la méduse est en collision avec le poisson
     * Si oui, la méduse gagne un boost
     * @param other Méduse du jeu
     */
    @Override
    public void testCollision(Meduse other) {
        if (intersects(other)) {
            other.setBoost();   // la méduse est boostée
            visible = false;    // le poisson n'est plus visible
        }
    }
}
