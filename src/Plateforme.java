import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Plateforme extends Entity{

    protected boolean contactMeduse = false;

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

    public void testCollision(Meduse other) {
        if (intersects(other) && Math.abs(other.posY + other.hauteur - posY) < 10 && other.vy > 0) {
            pushOut(other);
            other.vy = 0;
            other.setParterre(true);
            this.contactMeduse = true;
        }
    }

    public boolean intersects(Meduse other) {
        return !( // Un des carrés est à gauche de l’autre
                other.posX + other.largeur < posX || posX + largeur < other.posX
                        // Un des carrés est en haut de l’autre
                        || other.posY + other.hauteur < posY || posY + hauteur < other.posY);
    }

    /**
     * Repousse le personnage vers le haut (sans déplacer la
     * plateforme)
     */
    public void pushOut(Meduse other) {
        other.posY = this.posY - other.hauteur;
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
