package elementos.entidades;

import lib.GameLib;
import elementos.projeteis.EnemyProjectile;
import game.Main;

public class Enemy2 extends Enemy {
	private double spawnX;
	private static int count = 0;

	public Enemy2(long currentTime) {
		super(12.0);
		this.setNextEnemy(currentTime);
		this.spawnX = GameLib.WIDTH * 0.20;
		count++;
	}

	public double getSpawnX() {
		return spawnX;
	}

	public void throwNew(long currentTime) {
		if(currentTime > this.getNextEnemy()){
				
			int free = Main.findFreeIndex(this.getStates());
							
			if(free < this.getStates().length){
				
				this.getX()[free] = this.getSpawnX();
				this.getY()[free] = -10.0;
				this.getV()[free] = 0.42;
				this.getAngle()[free] = (3 * Math.PI) / 2;
				this.getRV()[free] = 0.0;
				this.getStates()[free] = Main.ACTIVE;

				count++;
				
				if(count < 10){
					
					this.setNextEnemy(currentTime + 120);
				}
				else {
					count = 0;
					spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
					this.setNextEnemy((long) (currentTime + 3000 + Math.random() * 3000));
				}
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
				if(	this.getX()[i] < -10 || this.getX()[i] > GameLib.WIDTH + 10 ) {
					
					this.getStates()[i] = Main.INACTIVE;
				}
				else {
					
					boolean shootNow = false;
					double previousY = this.getY()[i];
											
					this.getX()[i] += this.getV()[i] * Math.cos(this.getAngle()[i]) * delta;
					this.getY()[i] += this.getV()[i] * Math.sin(this.getAngle()[i]) * delta * (-1.0);
					this.getAngle()[i] += this.getRV()[i] * delta;
					
					double threshold = GameLib.HEIGHT * 0.30;
					
					if(previousY < threshold && this.getY()[i] >= threshold) {
						
						if(this.getX()[i] < GameLib.WIDTH / 2) this.getRV()[i] = 0.003;
						else this.getRV()[i] = -0.003;
					}
					
					if(this.getRV()[i] > 0 && Math.abs(this.getAngle()[i] - 3 * Math.PI) < 0.05){
						
						this.getRV()[i] = 0.0;
						this.getAngle()[i] = 3 * Math.PI;
						shootNow = true;
					}
					
					if(this.getRV()[i] < 0 && Math.abs(this.getAngle()[i]) < 0.05){
						
						this.getRV()[i] = 0.0;
						this.getAngle()[i] = 0.0;
						shootNow = true;
					}
																	
					if(shootNow){

						double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
						int [] freeArray = Main.findFreeIndex(enemy_projectile.getStates(), angles.length);

						for(int k = 0; k < freeArray.length; k++){
							
							int free = freeArray[k];
							
							if(free < enemy_projectile.getStates().length){
								
								double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
								double vx = Math.cos(a);
								double vy = Math.sin(a);
									
								enemy_projectile.getX()[free] = this.getX()[i];
								enemy_projectile.getY()[free] = this.getY()[i];
								enemy_projectile.getVX()[free] = vx * 0.30;
								enemy_projectile.getVY()[free] = vy * 0.30;
								enemy_projectile.getStates()[free] = 1;
							}
						}
					}
				}
			}
		}
	}
 }

