//Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Plateforme extends Entity{

    // Si la plateforme est en contact avec la méduse ou pas
    protected boolean contactMeduse = false;


    /**
     * Constructeur de la plateforme
     * Selon son numéro, on choisit la position horizontale adaptée
     * @param i numéro de la plateforme
     */
    public Plateforme(int i) {
        this.color = Color.rgb(230, 134, 58);
        this.hauteur = 10;
        this.largeur = Math.random() * 95 + 80; // entre 80 et 175px
        this.posX = Math.random() * (HighSeaTower.WIDTH - this.largeur);
        this.posY = HighSeaTower.HEIGHT - 90 - i * 100;

        // Plateforme qui se trouve sous la méduse au début
        if (i == -1) {
            this.posX = 0;
            this.posY = HighSeaTower.HEIGHT;
            this.largeur = HighSeaTower.WIDTH; // fait toute la largeur de l'écran
        }

        // Mode difficile -> une vitesse horizontale entre -200px/s et 200px/s
        if (Jeu.currentMode == 2) {
            this.vx = Math.random() * 400 - 200;
        }
    }


    /**
     * La collision avec une plateforme a lieu seulement si :
     * - Il y a une intersection entre la plateforme et le personnage
     * - La collision a lieu entre la plateforme et le *bas de la méduse*
     * seulement
     * - La vitesse va vers le bas (le personnage est en train de tomber,
     * pas en train de sauter)
     *
     * @param other la méduse
     */
    public void testCollision(Meduse other) {
        if (intersects(other) && Math.abs(other.posY + other.hauteur - posY) < 10 && other.vy > 0) {
            pushOut(other);
            other.vy = 0;
            other.setPfTouchee(this);
            this.contactMeduse = true;
        }
    }


    /**
     * Repousse le personnage vers le haut (sans déplacer la
     * plateforme)
     */
    public void pushOut(Meduse other) {
        other.posY = this.posY - other.hauteur;
    }


    /**
     * Met à jour la position et vitesse de la plateforme
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {
        super.update(dt);
    }


    /**
     * Dessine la plateforme sur le canvas
     * @param context contexte graphique
     * @param fenetreX origine X de la fenêtre
     * @param fenetreY origine Y de la fenêtre
     */
    @Override
    public void draw(GraphicsContext context, double fenetreX, double fenetreY) {
        double xAffiche = posX - fenetreX;
        double yAffiche = posY - fenetreY;

        // Si on est en mode debug et que la plateforme est en contact avec la méduse
        if (Jeu.debugMode && this.contactMeduse) {
            context.setFill(Color.YELLOW);

        // Sinon, couleur régulière
        } else {
            context.setFill(color);
        }

        context.fillRect(xAffiche, yAffiche, largeur, hauteur);
    }


    // Getters et setters

    public boolean isContactMeduse() {
        return contactMeduse;
    }

    public void setContactMeduse(boolean contactMeduse) {
        this.contactMeduse = contactMeduse;
    }
}
