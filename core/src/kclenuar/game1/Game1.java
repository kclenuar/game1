package kclenuar.game1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Game1 extends ApplicationAdapter {
	SpriteBatch batch;
	
	TextureAtlas textureAtlas;
	
	Animation<AtlasRegion> animation;
	
	MouseInput mouse;
	
	float time;
	int x;
	float t2 = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		textureAtlas = new TextureAtlas(Gdx.files.internal("yellow-ball.atlas"));
		animation = new Animation<>(1/8f, textureAtlas.getRegions());
		
		mouse = new MouseInput();
		Gdx.input.setInputProcessor(mouse);
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
		    x += mouse.b ? 3 : 1; // speed up side movement if lmb is down
		    x = x >= 300 ? 0 : x;
		}
		
		batch.begin();
		batch.draw(animation.getKeyFrame(time, true), x, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	private class MouseInput extends InputAdapter {
	    
	    boolean b = false;
	    
	    //temporarily place window size in this class
	    private int w, h;
	    
	    @Override
	    public boolean keyDown(int keyCode) {
	        boolean processed = false;
	        
	        // full screen button
	        if (keyCode == Keys.F) {
	            if (Gdx.graphics.isFullscreen()) {
	                Gdx.graphics.setWindowedMode(w, h);
	            } else {
	                w = Gdx.graphics.getWidth();
	                h = Gdx.graphics.getHeight();
	                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
	            }
	            processed = true;
	        }
	        // exit application button
	        else if (keyCode == Keys.ESCAPE) {
	            Gdx.app.exit();
	            processed = true;
	        }
	        
	        return processed;
	    }
	    
	    @Override
	    public boolean touchDown(int x, int y, int p, int button) {
	        if (button == Buttons.LEFT) {
	            b = true;
	            return true;
	        } else return false;
	    }
	    
	    @Override
	    public boolean touchUp(int x, int y, int p, int button) {
	        if (button == Buttons.LEFT) {
	            b = false;
	            return true;
	        } else return false;
	    }
	}
}
