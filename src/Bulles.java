import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bulles extends Entity {
    private Color color = Color.rgb(0, 0, 255, 0.4);
    private double[] baseX = new double[3];

    public Bulles(int width) {
        this.largeur = Math.random() * 30 + 10;   // entre 10 et 40px
        this.hauteur = this.largeur;
        this.vy = Math.random() * 100 + 350; // entre 350 et 450px/s

        for (int i = 0; i < baseX.length; i++) {
            baseX[i] = Math.random() * width; // entre 0 et width
        }
    }

    public void update(double dt) {
        /*for (double y : baseY) {
            y += dt * vitesse;
        }*/
    }


    public void draw(GraphicsContext context, double fenetreX, double fenetreY) {
/*
        double xAffiche = posX - fenetreX;
        double yAffiche = posY - fenetreY;

        context.setFill(color);
        for (double x : baseX) {
            for (int j = 0; j < 5; j++) {
                double posXRandom = x + Math.random() * 40 - 20;
                context.fillOval(posXRandom, 20, largeur, hauteur);
            }
        }*/
    }


}
