import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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
    private boolean gauche = false ;
    private double frameRate = 8;
    private double tempsTotal = 0;

    private boolean parterre = true;

    public Meduse() {
        this.largeur = 50;
        this.hauteur = 50;
        this.posX = (HighSeaTower.WIDTH - largeur) / 2;
        this.posY = HighSeaTower.HEIGHT - hauteur;
        this.ay = 1200; //gravité: 1200px/s^2 vers le bas
    }

    public void update(double dt) {

        // Physique du personnage
        super.update(dt);

        // Mise à jour de l'image affichée
        tempsTotal += dt;
        int frame = (int) (tempsTotal * frameRate);

        if (gauche) {
            img = framesG[frame % framesG.length];
        } else {
            img = framesD[frame % framesD.length];
        }
    }


    public void left() {
        this.gauche = true;
        ax = -1200;
    }

    public void right() {
        this.gauche = false;
        ax = 1200;
    }

    public void endAcc() {
        ax = 0;
    }

    public void jump() {
        if (parterre) {
            vy = -600;  //vitesse instantanée de 600px/s vers le haut
        }
    }

    public void setParterre(boolean parterre) {
        this.parterre = parterre;
    }

    public boolean isParterre() {
        return this.parterre;
    }


    @Override
    public void draw(GraphicsContext context, double fenetreX, double fenetreY) {
        double xAffiche = posX - fenetreX;
        double yAffiche = posY - fenetreY;
        context.drawImage(img, xAffiche, yAffiche, largeur, hauteur);

        if (Jeu.debugMode) {
            context.setFill(Color.rgb(255,0,0,0.3));
            context.fillRect(xAffiche, yAffiche, largeur, hauteur);
        }
    }
}
