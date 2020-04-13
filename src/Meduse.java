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
    double frameRate = 8;
    double tempsTotal = 0;

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

    public void testCollision(Plateforme other) {
        /**
         * La collision avec une plateforme a lieu seulement si :
         *
         * - Il y a une intersection entre la plateforme et le personnage
         *
         * - La collision a lieu entre la plateforme et le *bas du personnage*
         * seulement
         *
         * - La vitesse va vers le bas (le personnage est en train de tomber,
         * pas en train de sauter)
         */
        if (intersects(other) && other instanceof PlateformeSolide) {
            pushOut(other);
            if (vy > 0) {this.vy = 0;} else {this.vy *= -1;}
            this.parterre = true;
            other.setContactMeduse(true);

        } else if (intersects(other) && Math.abs(this.posY + hauteur - other.posY) < 10 && vy > 0) {

            pushOut(other);

            if (other instanceof PlateformeRebondissante) {
                this.vy *= -1.5;
                if (vy > -100) {
                    vy = -100;
                }

            } else if (other instanceof PlateformeAccelerante) {
                this.vy = 0;
                Jeu.vitesseAcceleree = true;
            } else {
                this.vy = 0;
            }

            this.parterre = true;
            other.setContactMeduse(true);
        }
    }

    public boolean intersects(Plateforme other) {
        return !( // Un des carrés est à gauche de l’autre
                posX + largeur < other.posX || other.posX + other.largeur < this.posX
                 // Un des carrés est en haut de l’autre
                || posY + hauteur < other.posY || other.posY + other.hauteur < this.posY);
    }

    /**
     * Repousse le personnage dans la direction adaptée (sans déplacer la
     * plateforme)
     */
    public void pushOut(Plateforme other) {

        double deltaY ;
        double deltaX ;
        if (other instanceof PlateformeSolide) {
            // TODO: pushOut dans toutes les directions pour plateformes solides
            deltaX = posX - Math.max(
                    other.getPosX() - other.largeur / 2,
                    Math.min(posX, other.getPosX() + other.largeur / 2));
            deltaY = posY - Math.max(
                    other.getPosY() - other.hauteur / 2,
                    Math.min(posY, other.getPosY() + other.largeur / 2));
        } else {
            deltaY = this.posY + this.hauteur - other.posY;
            deltaX = 0;
        }

        this.posY -= deltaY;
        this.posX -= deltaX;
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
