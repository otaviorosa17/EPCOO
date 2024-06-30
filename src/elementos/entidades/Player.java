package elementos.entidades;

import java.awt.Color;

import static lib.GameLib.*;
import static game.Main.*;
import elementos.projeteis.*;

public class Player extends Entidade {
	private double [] VX;
	private double [] VY;
	private long nextShot;
	protected PlayerProjectile p;

	public Player(long currentTime) {
		super(new int[1], new double[1], new double[1], new double[1], new double[1], 12.0);
		this.states[0] = ACTIVE;					// estados		
		this.X[0] = WIDTH / 2.0;				// coordenadas x
		this.Y[0] = HEIGHT * 0.90;				// coordenadas y
		this.VX = new double[1];
		this.VY = new double[1];
		this.VX[0] = 0.25;
		this.VY[0] = 0.25;
		this.explosion_start[0] = 0;					// instantes dos inícios das explosões
		this.explosion_end[0] = 0;						// instantes dos finais da explosões
		this.nextShot = currentTime;
	}

	public long getNextShot() {
		return nextShot;
	}

	public void animacaoExplode(long currentTime, int i) {
		states[0] = EXPLODING;
		explosion_start[0] = currentTime;
		explosion_end[0] = currentTime + 2000;
	}

	public void explode(long currentTime, Entidade colide) {
		if(this.getStates()[0] == ACTIVE){
				
			for(int i = 0; i < colide.getStates().length; i++){
				
				double dx = colide.getX()[i] - this.getX()[0];
				double dy = colide.getY()[i] - this.getY()[0];
				double dist = Math.sqrt(dx * dx + dy * dy);
				
				if(dist < (this.getRadius() + colide.getRadius()) * 0.8){
					animacaoExplode(currentTime, i);
				}
			}
		}
	}

	public void reset(long currentTime) {
		if(states[0] == EXPLODING){

			if(currentTime > explosion_end[0]){
				
				states[0] = ACTIVE;
			}
		}
	}

	public boolean keys(long delta, long currentTime, PlayerProjectile p) {
		if(this.states[0] == ACTIVE){
			if(iskeyPressed(KEY_UP)) Y[0] -= delta * VY[0];
			if(iskeyPressed(KEY_DOWN)) Y[0] += delta * VY[0];
			if(iskeyPressed(KEY_LEFT)) X[0] -= delta * VX[0];
			if(iskeyPressed(KEY_RIGHT)) X[0] += delta * VY[0];
			if(iskeyPressed(KEY_CONTROL)) {
				
				if(currentTime > this.getNextShot()){
					
					int free = findFreeIndex(p.getStates());
											
					if(free < p.getStates().length){
						
						p.getX()[free] = this.X[0];
						p.getY()[free] = this.Y[0] - 2 * this.radius;
						p.getVX()[free] = 0.0;
						p.getVY()[free] = -1.0;
						p.getStates()[free] = 1;
						this.nextShot = currentTime + 100;
					}
				}	
			}
			if(iskeyPressed(KEY_ESCAPE)) return false;
		}
		return true;
	}

	public void screenLimits() {
		if(X[0] < 0.0) X[0] = 0.0;
		if(X[0] >= WIDTH) X[0] = WIDTH - 1;
		if(Y[0] < 25.0) Y[0] = 25.0;
		if(Y[0] >= HEIGHT) Y[0] = HEIGHT - 1;
	}

	public void draw(long currentTime) {	
		if(states[0] == EXPLODING){
				
			double alpha = (currentTime - explosion_start[0]) / (explosion_end[0] - explosion_start[0]);
			drawExplosion(X[0], Y[0], alpha);
		}
		else{
			
			setColor(Color.BLUE);
			drawPlayer(X[0], Y[0], radius);
		}
	}
}
