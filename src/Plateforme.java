import javafx.scene.paint.Color;

public abstract class Plateforme extends Entity{

    public Plateforme() {
        this.hauteur = 10;
        this.largeur = Math.random() * 95 - 80; // entre 80 et 175px
        double bordG = this.largeur / 2;
        double bordD = HighSeaTower.WIDTH - this.largeur / 2;
        this.posX = Math.random() * (bordD - bordG) + bordG;
    }
}
