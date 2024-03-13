package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Witch {
    private Texture texture;
    private Vector2 position;
    private Rectangle bounds;

    public Witch(Vector2 position) {
        // Cargar la textura desde el archivo interno witch.png
        texture = new Texture(Gdx.files.internal("Witch/B_witch_idle.png"));

        this.position = position;
        this.bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public void update(float delta) {
        // Aquí puedes agregar lógica de actualización, como movimiento o interacción con otros objetos.
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    // Este método te permite cambiar la posición del Witch.
    public void setPosition(Vector2 position) {
        this.position = position;
        bounds.setPosition(position);
    }

    // Método para liberar recursos cuando ya no se necesiten.
    public void dispose() {
        texture.dispose();
    }
}
