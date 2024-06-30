package elementos.entidades;

import lib.GameLib;
import game.Main;

public class Enemy1 extends Enemy {
	private long [] nextShoot;

	public Enemy1(long currentTime) {
		super();
		this.setRadius(9.0);
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
}

