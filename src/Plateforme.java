import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Plateforme extends Entity{

    private boolean contactMeduse = false;

    public Plateforme(int i) {
        this.color = Color.rgb(230, 134, 58);
        this.hauteur = 10;
        this.largeur = Math.random() * 95 + 80; // entre 80 et 175px
        this.posX = Math.random() * (HighSeaTower.WIDTH - this.largeur);
        this.posY = HighSeaTower.HEIGHT - 90 - i * 100;

        if (i == -1) {
            posX = 0;
            posY = HighSeaTower.HEIGHT;
            largeur = HighSeaTower.WIDTH;
        }
    }


    public boolean isContactMeduse() {
        return contactMeduse;
    }

    public void setContactMeduse(boolean contactMeduse) {
        this.contactMeduse = contactMeduse;
    }

    public void update(double dt) {
        super.update(dt);
    }

    @Override
    public void draw(GraphicsContext context, double fenetreX, double fenetreY) {
        double xAffiche = posX - fenetreX;
        double yAffiche = posY - fenetreY;

        if (Jeu.debugMode && this.contactMeduse) {
            context.setFill(Color.YELLOW);
        } else {
            context.setFill(color);
        }

        context.fillRect(xAffiche, yAffiche, largeur, hauteur);
    }
}
