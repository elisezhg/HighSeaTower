//Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.scene.canvas.GraphicsContext;

/**
 * Représente le controleur du jeu, gère les intéractions entre l'affichage et la logique
 */
public class Controleur {

    private Jeu jeu;

    /**
     * Constructeur du controleur
     */
    public Controleur() {
        this.jeu = new Jeu(this);
    }


    // Manipulations et mise à jour du jeu

    public void draw(GraphicsContext context) {
        jeu.draw(context);
    }

    public void update(double deltaTime) {
        jeu.update(deltaTime);
    }

    public void up() {
        jeu.up();
    }

    public void left() {
        jeu.left();
    }

    public void right() {
        jeu.right();
    }

    public void down() {
        jeu.down();
    }

    public void endAx() {
        jeu.endAx();
    }

    public void switchDebug() {
        jeu.switchDebug();
    }

    public void nvPartie() {
        this.jeu = new Jeu(this);
    }

    public void switchMenu() {
        jeu.switchMenu();
    }
}
