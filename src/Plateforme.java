import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Plateforme extends Entity{

    public Plateforme(int i) {
        this.hauteur = 10;
        this.largeur = Math.random() * 95 + 80; // entre 80 et 175px
        this.posX = Math.random() * (HighSeaTower.WIDTH - this.largeur / 2);
        this.posY = HighSeaTower.HEIGHT - 90 - i * 100;
    }

    public void update(double dt) {
        super.update(dt);
    }

    @Override
    public void draw(GraphicsContext context, double fenetreX, double fenetreY) {
        double xAffiche = posX - fenetreX;
        double yAffiche = posY - fenetreY;

        context.setFill(color);
        context.fillRect(xAffiche, yAffiche, largeur, hauteur);
    }
}
