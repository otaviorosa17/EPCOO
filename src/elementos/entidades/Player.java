package elementos.entidades;

import java.awt.Color;
import lib.GameLib;
import game.Main;
import elementos.projeteis.PlayerProjectile;

public class Player {
	private int state;								// estado
	private double X;								// coordenada x
	private double Y;								// coordenada y
	private double VX;								// velocidade no eixo x
	private double VY;								// velocidade no eixo y
	private double radius;							// raio (tamanho aproximado do player)
	private double explosion_start;						// instante do início da explosão
	private double explosion_end;						// instante do final da explosão
	private long nextShot;

	public Player(double GamelibWidth, double GamelibHeight, long currentTime) {
		this.state = Main.ACTIVE;
		this.X = GamelibWidth/ 2;					// coordenada x
		this.Y = GamelibHeight * 0.90;				// coordenada y
		this.VX = 0.25;								// velocidade no eixo x
		this.VY = 0.25;								// velocidade no eixo y
		this.radius = 12.0;							// raio (tamanho aproximado do player)
		this.explosion_start = 0;						// instante do início da explosão
		this.explosion_end = 0;						// instante do final da explosão
		this.nextShot = currentTime;
	}
	public double getVX() {
		return VX;
	}
	public double getVY() {
		return VY;
	}
	public double getX() {
		return X;
	}
	public double getY() {
		return Y;
	}
	public double getExplosion_end() {
		return explosion_end;
	}
	public double getExplosion_start() {
		return explosion_start;
	}
	public long getNextShot() {
		return nextShot;
	}
	public double getRadius() {
		return radius;
	}
	public int getState() {
		return state;
	}
	public void explode(long currentTime) {
		state = Main.EXPLODING;
		explosion_start = currentTime;
		explosion_end = currentTime + 2000;
	}
	public void reset(long currentTime) {
		if(state == Main.EXPLODING){

			if(currentTime > explosion_end){
				
				state = Main.ACTIVE;
			}
		}
	}

	public boolean keys(long delta, long currentTime, PlayerProjectile p) {
		if(this.state == Main.ACTIVE){
			if(GameLib.iskeyPressed(GameLib.KEY_UP)) Y -= delta * VY;
			if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) Y += delta * VY;
			if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) X -= delta * VX;
			if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) X += delta * VY;
			if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
				
				if(currentTime > this.getNextShot()){
					
					int free = Main.findFreeIndex(p.getStates());
											
					if(free < p.getStates().length){
						
						p.getX()[free] = this.X;
						p.getY()[free] = this.Y - 2 * this.radius;
						p.getVX()[free] = 0.0;
						p.getVY()[free] = -1.0;
						p.getStates()[free] = 1;
						this.nextShot = currentTime + 100;
					}
				}	
			}
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) return false;
		}
		return true;
	}

	public void screenLimits() {
		if(X < 0.0) X = 0.0;
		if(X >= GameLib.WIDTH) X = GameLib.WIDTH - 1;
		if(Y < 25.0) Y = 25.0;
		if(Y >= GameLib.HEIGHT) Y = GameLib.HEIGHT - 1;
	}

	public void draw(long currentTime) {	
		if(state == Main.EXPLODING){
				
			double alpha = (currentTime - explosion_start) / (explosion_end - explosion_start);
			GameLib.drawExplosion(X, Y, alpha);
		}
		else{
			
			GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(X, Y, radius);
		}
	}
}
