import javafx.scene.canvas.GraphicsContext;

public class Controleur {
    private Jeu jeu;

    public Controleur() {
        this.jeu = new Jeu();
    }

    void draw(GraphicsContext context) {
        jeu.draw(context);
    }

    void update(double deltaTime) {
        jeu.update(deltaTime);
    }

    void jump() {
        //jeu.jump();
    }

    void left() {
        //jeu.left();
    }

    void right() {
        //jeu.right();
    }
}
