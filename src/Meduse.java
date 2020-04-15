//Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.*;
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

    private double ralentie = 0;
    private double boost = 0;
    private Plateforme pfTouchee = new Plateforme(-1);

    /**
     * Constructeur de la méduse
     */
    public Meduse() {
        this.largeur = 50;
        this.hauteur = 50;
        this.posX = (HighSeaTower.WIDTH - largeur) / 2;
        this.posY = HighSeaTower.HEIGHT - hauteur;
        this.ay = 1200;
    }


    /**
     * Met à jour la position et la vitesse de la méduse
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {

        // Physique du personnage
        super.update(dt);

        // Friction
        vx *= 0.95;

        // Bouge la méduse si la plateforme bouge
        if (pfTouchee != null) {
            posX += dt * pfTouchee.vx;
        }

        // Ralentie : vitesse max de 500px/s vers le haut
        if (isRalentie()) {
            if (vy < -500) vy = -500;
            ralentie -= dt;
        }

        // Boost : supersaut
        if (isBoost()) {
            vy = -800;
            boost -= dt;
        }

        // Mise à jour de l'image affichée
        tempsTotal += dt;
        int frame = (int) (tempsTotal * frameRate);

        if (gauche) {
            img = framesG[frame % framesG.length];
        } else {
            img = framesD[frame % framesD.length];
        }
    }


    /**
     * La méduse regarde à gauche et prend une accélération de 1200px/s vers la gauche
     */
    public void left() {
        this.gauche = true;
        ax = -1200;
    }


    /**
     * La méduse regarde à droite et prend une accélération de 1200px/s vers la droite
     */
    public void right() {
        this.gauche = false;
        ax = 1200;
    }


    /**
     * L'accélération horizontale prend fin
     */
    public void endAx() {
        ax = 0;
    }


    /**
     * La méduse saute si elle se trouve sur une plateforme
     */
    public void jump() {
        if (pfTouchee != null) {
            vy = -600;
        }
    }


    /**
     * Dessine la méduse sur le canvas
     * Si on est en mode debug, il y a un carré rouge qui englobe la méduse
     * Si la méduse est ralentie, un effet de flou est appliqué sur la méduse
     * Si la méduse est boostée, la méduse devient rouge
     *
     * @param context contexte graphique
     * @param fenetreX origine X de la fenêtre
     * @param fenetreY origine Y de la fenêtre
     */
    @Override
    public void draw(GraphicsContext context, double fenetreX, double fenetreY) {
        double xAffiche = posX - fenetreX;
        double yAffiche = posY - fenetreY;

        if (Jeu.debugMode) {
            context.setFill(Color.rgb(255,0,0,0.3));
            context.fillRect(xAffiche, yAffiche, largeur, hauteur);
        }

        if (isRalentie()) {
            MotionBlur motionBlur = new MotionBlur();
            context.setEffect(motionBlur);

        } else if (isBoost()){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setHue(0.3);
            context.setEffect(colorAdjust);
        }

        context.drawImage(img, xAffiche, yAffiche, largeur, hauteur);
        context.setEffect(null);
    }


    // Getters et setters

    public void setPfTouchee(Plateforme pfTouchee) {
        this.pfTouchee = pfTouchee;
    }

    public Plateforme getPfTouchee() {
        return this.pfTouchee;
    }

    public boolean isRalentie() {
        return ralentie > 0;
    }

    public void setRalentie() {
        this.ralentie = 1;
    }

    public boolean isBoost() {
        return boost > 0;
    }

    public void setBoost() {
        this.boost = 0.5;
    }
}
