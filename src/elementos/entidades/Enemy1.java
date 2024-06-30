package elementos.entidades;

import lib.GameLib;
import elementos.projeteis.EnemyProjectile;
import game.Main;

public class Enemy1 extends Enemy {
	private long [] nextShoot;

	public Enemy1(long currentTime) {
		super(9.0);
		this.setNextEnemy(currentTime + 2000);
		nextShoot = new long[10];
	}

	public long[] getNextShoot() {
		return nextShoot;
	}
	
	public void throwNew(long currentTime) {
		if(currentTime > this.getNextEnemy()){
				
			int free = Main.findFreeIndex(this.getStates());
							
			if(free < this.getStates().length){
				
				this.getX()[free] = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
				this.getY()[free] = -10.0;
				this.getV()[free] = 0.20 + Math.random() * 0.15;
				this.getAngle()[free] = 3 * Math.PI / 2;
				this.getRV()[free] = 0.0;
				this.getStates()[free] = Main.ACTIVE;
				this.nextShoot[free] = currentTime + 500;
				this.setNextEnemy(currentTime + 500);
			}
		}
	}

	public void status(Long currentTime, Long delta, EnemyProjectile enemy_projectile, Player player){
		for(int i = 0; i < this.getStates().length; i++){
				
			if(this.getStates()[i] == Main.EXPLODING){
				
				if(currentTime > this.getExplosion_end()[i]){
					
					this.getStates()[i] = Main.INACTIVE;
				}
			}
			
			if(this.getStates()[i] == Main.ACTIVE){
				
				/* verificando se inimigo saiu da tela */
				if(this.getY()[i] > GameLib.HEIGHT + 10) {
					
					this.getStates()[i] = Main.INACTIVE;
				}
				else {
				
					this.getX()[i] += this.getV()[i] * Math.cos(this.getAngle()[i]) * delta;
					this.getY()[i] += this.getV()[i] * Math.sin(this.getAngle()[i]) * delta * (-1.0);
					this.getAngle()[i] += this.getRV()[i] * delta;
					
					if(currentTime > this.getNextShoot()[i] && this.getY()[i] < player.getY()[0]){
																						
						int free = Main.findFreeIndex(enemy_projectile.getStates());
						
						if(free < enemy_projectile.getStates().length){
							
							enemy_projectile.getX()[free] = this.getX()[i];
							enemy_projectile.getY()[free] = this.getY()[i];
							enemy_projectile.getVX()[free] = Math.cos(this.getAngle()[i]) * 0.45;
							enemy_projectile.getVY()[free] = Math.sin(this.getAngle()[i]) * 0.45 * (-1.0);
							enemy_projectile.getStates()[free] = 1;
							
							this.getNextShoot()[i] = (long) (currentTime + 200 + Math.random() * 500);
						}
					}
				}
			}
		}
	}
}

