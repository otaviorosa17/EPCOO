package elementos.projeteis;

import elementos.*;
import elementos.entidades.*;
import lib.GameLib;
import game.Main;
import java.awt.Color;

public class Projectile extends Elemento {
	private double radius;
	private double [] VX;
	private double [] VY;


	protected Projectile(int [] states, double [] X, double [] Y, double [] VX, double [] VY, double radius) {
		super(states, X, Y);
		this.radius = radius;
		this.VX = VX;
		this.VY = VY;
		for(int i = 0; i < this.states.length; i++) this.states[i] = Main.INACTIVE;
	}

	public void explode(long currentTime, Entidade e) {
		for(int k = 0; k < this.getStates().length; k++){
			for(int i = 0; i < e.getStates().length; i++){
			
				if(e.getStates()[i] == Main.ACTIVE){
			
					double dx = e.getX()[i] - this.getX()[k];
					double dy = e.getY()[i] - this.getY()[k];
					double dist = Math.sqrt(dx * dx + dy * dy);
				
                    if(dist < e.getRadius()){
						e.animacaoExplode(currentTime, i);
                    }
				}
			}
		}
	}

	public void track(long delta) {
		for(int i = 0; i < this.getStates().length; i++){
				
			if(this.getStates()[i] == Main.ACTIVE){
				
				/* verificando se projétil saiu da tela */
				if(this.getY()[i] < 0 || this.getY()[i] > GameLib.HEIGHT ) {
					
					this.getStates()[i] = Main.INACTIVE;
				}
				else {
				
					this.getX()[i] += this.VX[i] * delta;
					this.getY()[i] += this.VY[i] * delta;
				}
			}
		}
	}

	public void draw(boolean is_player_projectile) {
		for(int i = 0; i < this.getStates().length; i++){
			
			if(this.getStates()[i] == Main.ACTIVE){
				if (is_player_projectile) {
					GameLib.setColor(Color.GREEN);
					GameLib.drawLine(this.getX()[i], this.getY()[i] - 5, this.getX()[i], this.getY()[i] + 5);
					GameLib.drawLine(this.getX()[i] - 1, this.getY()[i] - 3, this.getX()[i] - 1, this.getY()[i] + 3);
					GameLib.drawLine(this.getX()[i] + 1, this.getY()[i] - 3, this.getX()[i] + 1, this.getY()[i] + 3);
				}
				else {
					GameLib.setColor(Color.RED);
					GameLib.drawCircle(this.getX()[i], this.getY()[i], this.getRadius());
				}
			}
		}
	}

	public double[] getVX() {
		return VX;
	}
	public double[] getVY() {
		return VY;
	}
	public double getRadius() {
		return radius;
	}
	
	void initializeStates() {
		for(int i = 0; i < this.getStates().length; i++) this.states[i] = Main.INACTIVE;
	}
	
}
