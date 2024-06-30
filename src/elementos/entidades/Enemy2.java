package elementos.entidades;

import lib.GameLib;
import game.Main;

public class Enemy2 extends Enemy {
	private double spawnX;
	private static int count = 0;

	public Enemy2(long currentTime) {
		super();
		this.setRadius(12.0);
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
 }

