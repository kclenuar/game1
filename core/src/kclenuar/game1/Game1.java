package kclenuar.game1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Game1 extends ApplicationAdapter {
	SpriteBatch batch;
	
	TextureAtlas textureAtlas;
	
	Animation<AtlasRegion> animation;
	
	float time;
	int x;
	float t2 = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		textureAtlas = new TextureAtlas(Gdx.files.internal("yellow-ball.atlas"));
		animation = new Animation<>(1/8f, textureAtlas.getRegions());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float d = Gdx.graphics.getDeltaTime();
		time += d;
		t2 += d;
		if (t2 > 1/8f) {
		    t2 = 0f;
		    x += 1;
		    x = x >= 200 ? 0 : x;
		}
		
		batch.begin();
		batch.draw(animation.getKeyFrame(time, true), x, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
