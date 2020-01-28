package kclenuar.game1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Game1 extends ApplicationAdapter {
	SpriteBatch batch;
	
	TextureAtlas textureAtlas;
	
	Animation<TextureRegion> animation;
	
	MouseInput mouse;
	
	float time;
	int x;
	float t2 = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		Array<TextureRegion> animFrames = new Array<>();
		
		// data about circle and animation
        int radius = 32;
        int totalFrames = 30;
        int halfFrames = totalFrames/2;
        int minY = radius;
        int maxY = 3 * radius;
        
        // loop to draw each frame of animation
		Pixmap p = new Pixmap(2*radius+1, 4*radius+1, Pixmap.Format.RGBA8888);
		for (int i = 0; i < totalFrames; ++i) {
	        
		    // calc position of circle in y=x^2 curve
	        int circleYPos = (int)convert(Math.pow(i-halfFrames, 2), Math.pow(halfFrames, 2), 0, maxY, minY);
	        
	        // circleXPos is radius, since pixmap is 2 radii wide
            p.setColor(1f,1f,0f,1f);
	        p.fillCircle(radius, circleYPos, radius);
	        
		    animFrames.add(new TextureRegion(new Texture(p)));
		    
		    //reset
		    p.setColor(0f,0f,0f,0f);
		    p.fill();
		}
		
		p.dispose();
		animation = new Animation<>(1/60f, animFrames);
		
		mouse = new MouseInput();
		Gdx.input.setInputProcessor(mouse);
	}
	
	// (ub2-lb2)(x-lb1)/(ub1-lb1) + lb2
	/**
	 * Converts x from the lb1 to ub1 range into the lb2 to ub2 range
	 * @param x value in first range
	 * @param ub1 upper bound of first range
	 * @param lb1 lower bound of first range
	 * @param ub2 upper bound of second range
	 * @param lb2 upper bound of second range
	 * @return x converted to second range
	 */
	private static double convert(double x, double ub1, double lb1, double ub2, double lb2) {
	    double r1 = ub1 - lb1;
	    double r2 = ub2 - lb2;
	    return r2 * (x-lb1) / r1 + lb2;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float d = Gdx.graphics.getDeltaTime();
		time += d;
		t2 += d;
		if (t2 > 1/60f) {
		    t2 = 0f;
		    x += mouse.b ? 3 : 1; // speed up side movement if lmb is down
		    x = x >= 500 ? 0 : x;
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
