import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bulle extends Entity {

     /**
     * @param baseX est la position originale de x 
     * @param fenetreY est l'origine en l'axe y de la fenêtre
     */
    public Bulle(double baseX, double fenetreY) {
        this.color = Color.rgb(0, 0, 255, 0.4);
        this.largeur = Math.random() * 30 + 10;   // entre 10 et 40px
        this.hauteur = this.largeur;
        this.vy = -(Math.random() * 100 + 350); // entre 350 et 450px/s vers le haut
        this.posX = baseX + Math.random() * 40 - 20;    // position random dans [baseX - 20; baseX + 20]
        this.posY = fenetreY + HighSeaTower.HEIGHT;  // positionné juste en bas de l'écran
        System.out.println(fenetreY);
        System.out.println(posY);
    }

    public void update(double dt) {
        super.update(dt);
    }


    public void draw(GraphicsContext context, double fenetreX, double fenetreY) {

        double xAffiche = posX - fenetreX;
        double yAffiche = posY - fenetreY;

        context.setFill(color);

        context.fillOval(xAffiche, yAffiche, largeur, hauteur);
    }
}
