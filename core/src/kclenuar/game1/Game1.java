package kclenuar.game1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class Game1 extends ApplicationAdapter {
	SpriteBatch batch;
	
	TextureAtlas textureAtlas;
	Array<AtlasRegion> regions;
	Sprite sprite;
	
	private float animTimer = 0;
	private float animInterval = 1/8f;
	private int maxAtlasRegions;
	private int currentAtlasRegion;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		textureAtlas = new TextureAtlas(Gdx.files.internal("yellow-ball.atlas"));
		regions = textureAtlas.getRegions();
		maxAtlasRegions = regions.size;
		currentAtlasRegion = 0;
		
		sprite = new Sprite(regions.first());
		sprite.setScale(4.0f);
		sprite.setPosition(100, 100);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		animTimer += Gdx.graphics.getDeltaTime();
		if (animTimer >= animInterval) {
		    animTimer -= animInterval;
		    ++currentAtlasRegion;
		    currentAtlasRegion = currentAtlasRegion == maxAtlasRegions ? 0 : currentAtlasRegion;
		    sprite.setRegion(regions.get(currentAtlasRegion));
		}
		
		batch.begin();
		sprite.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
