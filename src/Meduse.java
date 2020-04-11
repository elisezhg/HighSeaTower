import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Meduse extends Entity {

    // Charge les images de l’animation dans un tableau
    private Image[] framesG = new Image[]{
            new Image("res/jellyfish1g.png"),
            new Image("res/jellyfish2g.png"),
            new Image("res/jellyfish3g.png"),
            new Image("res/jellyfish4g.png"),
            new Image("res/jellyfish5g.png"),
            new Image("res/jellyfish6g.png")
    };

    private Image[] framesD = new Image[]{
            new Image("res/jellyfish1.png"),
            new Image("res/jellyfish2.png"),
            new Image("res/jellyfish3.png"),
            new Image("res/jellyfish4.png"),
            new Image("res/jellyfish5.png"),
            new Image("res/jellyfish6.png")
    };

    private Image img = framesD[0];
    private boolean gauche = false;
    double frameRate = 8;
    double tempsTotal = 0;

    public Meduse() {
        this.largeur = 50;
        this.hauteur = 50;
        this.posX = 20;
        this.posY = 20;
    }

    public void update(double dt) {
        tempsTotal += dt;
        int frame = (int) (tempsTotal * frameRate);

        if (gauche) {
            img = framesG[frame % framesG.length];
        } else {
            img = framesD[frame % framesD.length];
        }
    }

    public void setGauche(boolean bool) {
        this.gauche = bool;
    }


    public void draw(GraphicsContext context, double fenetreX, double fenetreY) {
        double xAffiche = posX - fenetreX;
        double yAffiche = posY - fenetreY;
        context.drawImage(img, xAffiche, yAffiche, largeur, hauteur);
    }
}
