package game;

import java.awt.Color;

import elementos.entidades.Enemy1;
import elementos.entidades.Enemy2;
import elementos.entidades.Player;
import elementos.background.Estrela;
import elementos.projeteis.EnemyProjectile;
import elementos.projeteis.PlayerProjectile;
import lib.GameLib;

public class Main {
	
	/* Constantes relacionadas aos estados que os elementos   */
	/* do jogo (player, projeteis ou inimigos) podem assumir. */
	
	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;
	

	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time.    */
	
	public static void busyWait(long time){
		
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	/* Encontra e devolve o primeiro índice do  */
	/* array referente a uma posição "inativa". */
	
	public static int findFreeIndex(int [] stateArray){
		
		int i;
		
		for(i = 0; i < stateArray.length; i++){
			
			if(stateArray[i] == INACTIVE) break;
		}
		
		return i;
	}
	
	/* Encontra e devolve o conjunto de índices (a quantidade */
	/* de índices é defnida através do parâmetro "amount") do */
	/* array, referentes a posições "inativas".               */ 

	public static int [] findFreeIndex(int [] stateArray, int amount){

		int i, k;
		int [] freeArray = { stateArray.length, stateArray.length, stateArray.length };
		
		for(i = 0, k = 0; i < stateArray.length && k < amount; i++){
				
			if(stateArray[i] == INACTIVE) { 
				
				freeArray[k] = i; 
				k++;
			}
		}
		
		return freeArray;
	}
	
	/* Método principal */
	
	public static void main(String [] args){

		/* Indica que o jogo está em execução */
		boolean running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */
		
		long delta;
		long currentTime = System.currentTimeMillis();

		/* inicializando player*/
		
		Player player = new Player(GameLib.WIDTH, GameLib.HEIGHT, currentTime);

		/* variáveis dos projéteis disparados pelo player */
		
		PlayerProjectile player_projectile = new PlayerProjectile();

		/* variáveis dos inimigos tipo 1 */
		
		Enemy1 enemy1 = new Enemy1(currentTime);
		
		/* variáveis dos inimigos tipo 2 */
		Enemy2 enemy2 = new Enemy2(currentTime);
		
		/* variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto tipo 2) */

		EnemyProjectile enemy_projectile = new EnemyProjectile();
		
		Estrela estrela1 = new Estrela(20, 20, 0.070, 0.0);
		Estrela estrela2 = new Estrela(50, 50, 0.045, 0.0);
		
		/* iniciado interface gráfica */
		
		GameLib.initGraphics();
		
		/*************************************************************************************************/
		/*                                                                                               */
		/* Main loop do jogo                                                                             */
		/*                                                                                               */
		/* O main loop do jogo possui executa as seguintes operações:                                    */
		/*                                                                                               */
		/* 1) Verifica se há colisões e atualiza estados dos elementos conforme a necessidade.           */
		/*                                                                                               */
		/* 2) Atualiza estados dos elementos baseados no tempo que correu desde a última atualização     */
		/*    e no timestamp atual: posição e orientação, execução de disparos de projéteis, etc.        */
		/*                                                                                               */
		/* 3) Processa entrada do usuário (teclado) e atualiza estados do player conforme a necessidade. */
		/*                                                                                               */
		/* 4) Desenha a cena, a partir dos estados dos elementos.                                        */
		/*                                                                                               */
		/* 5) Espera um período de tempo (de modo que delta seja aproximadamente sempre constante).      */
		/*                                                                                               */
		/*************************************************************************************************/
		
		while(running){
		
			/* Usada para atualizar o estado dos elementos do jogo    */
			/* (player, projéteis e inimigos) "delta" indica quantos  */
			/* ms se passaram desde a última atualização.             */
			
			delta = System.currentTimeMillis() - currentTime;
			
			/* Já a variável "currentTime" nos dá o timestamp atual.  */
			
			currentTime = System.currentTimeMillis();
			
			/***************************/
			/* Verificação de colisões */
			/***************************/
						
			if(player.getState() == ACTIVE){
				
				/* colisões player - projeteis (inimigo) */
				
				for(int i = 0; i < enemy_projectile.getStates().length; i++){
					
					double dx = enemy_projectile.getX()[i] - player.getX();
					double dy = enemy_projectile.getY()[i] - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);
					
					if(dist < (player.getRadius() + enemy_projectile.getRadius()) * 0.8){
						player.explode(currentTime);
					}
				}
			
				/* colisões player - inimigos */
							
				for(int i = 0; i < enemy1.getStates().length; i++){
					
					double dx = enemy1.getX()[i] - player.getX();
					double dy = enemy1.getY()[i] - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);
					
					if(dist < (player.getRadius() + enemy1.getRadius()) * 0.8){
						player.explode(currentTime);
					}
				}
				
				for(int i = 0; i < enemy2.getStates().length; i++){
					
					double dx = enemy2.getX()[i] - player.getX();
					double dy = enemy2.getY()[i] - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);
					
					if(dist < (player.getRadius() + enemy2.getRadius()) * 0.8){
						player.explode(currentTime);
					}
				}
			}
			
			/* colisões projeteis (player) - inimigos */
			
			for(int k = 0; k < player_projectile.getStates().length; k++){
				
				for(int i = 0; i < enemy1.getStates().length; i++){
										
					if(enemy1.getStates()[i] == ACTIVE){
					
						double dx = enemy1.getX()[i] - player_projectile.getX()[k];
						double dy = enemy1.getY()[i] - player_projectile.getY()[k];
						double dist = Math.sqrt(dx * dx + dy * dy);
						
						if(dist < enemy1.getRadius()){
							
							enemy1.getStates()[i] = EXPLODING;
							enemy1.getExplosion_start()[i] = currentTime;
							enemy1.getExplosion_end()[i] = currentTime + 500;
						}
					}
				}
				
				for(int i = 0; i < enemy2.getStates().length; i++){
					
					if(enemy2.getStates()[i] == ACTIVE){
						
						double dx = enemy2.getX()[i] - player_projectile.getX()[k];
						double dy = enemy2.getY()[i] - player_projectile.getY()[k];
						double dist = Math.sqrt(dx * dx + dy * dy);
						
						if(dist < enemy2.getRadius()){
							
							enemy2.getStates()[i] = EXPLODING;
							enemy2.getExplosion_start()[i] = currentTime;
							enemy2.getExplosion_end()[i] = currentTime + 500;
						}
					}
				}
			}
				
			/***************************/
			/* Atualizações de estados */
			/***************************/
			
			/* projeteis (player) */
			
			player_projectile.track(delta);
			
			/* projeteis (inimigos) */
			
			enemy_projectile.track(delta);

			/* inimigos tipo 1 */
			
			for(int i = 0; i < enemy1.getStates().length; i++){
				
				if(enemy1.getStates()[i] == EXPLODING){
					
					if(currentTime > enemy1.getExplosion_end()[i]){
						
						enemy1.getStates()[i] = INACTIVE;
					}
				}
				
				if(enemy1.getStates()[i] == ACTIVE){
					
					/* verificando se inimigo saiu da tela */
					if(enemy1.getY()[i] > GameLib.HEIGHT + 10) {
						
						enemy1.getStates()[i] = INACTIVE;
					}
					else {
					
						enemy1.getX()[i] += enemy1.getV()[i] * Math.cos(enemy1.getAngle()[i]) * delta;
						enemy1.getY()[i] += enemy1.getV()[i] * Math.sin(enemy1.getAngle()[i]) * delta * (-1.0);
						enemy1.getAngle()[i] += enemy1.getRV()[i] * delta;
						
						if(currentTime > enemy1.getNextShoot()[i] && enemy1.getY()[i] < player.getY()){
																							
							int free = findFreeIndex(enemy_projectile.getStates());
							
							if(free < enemy_projectile.getStates().length){
								
								enemy_projectile.getX()[free] = enemy1.getX()[i];
								enemy_projectile.getY()[free] = enemy1.getY()[i];
								enemy_projectile.getVX()[free] = Math.cos(enemy1.getAngle()[i]) * 0.45;
								enemy_projectile.getVY()[free] = Math.sin(enemy1.getAngle()[i]) * 0.45 * (-1.0);
								enemy_projectile.getStates()[free] = 1;
								
								enemy1.getNextShoot()[i] = (long) (currentTime + 200 + Math.random() * 500);
							}
						}
					}
				}
			}
			
			/* inimigos tipo 2 */
			
			for(int i = 0; i < enemy2.getStates().length; i++){
				
				if(enemy2.getStates()[i] == EXPLODING){
					
					if(currentTime > enemy2.getExplosion_end()[i]){
						
						enemy2.getStates()[i] = INACTIVE;
					}
				}
				
				if(enemy2.getStates()[i] == ACTIVE){
					
					/* verificando se inimigo saiu da tela */
					if(	enemy2.getX()[i] < -10 || enemy2.getX()[i] > GameLib.WIDTH + 10 ) {
						
						enemy2.getStates()[i] = INACTIVE;
					}
					else {
						
						boolean shootNow = false;
						double previousY = enemy2.getY()[i];
												
						enemy2.getX()[i] += enemy2.getV()[i] * Math.cos(enemy2.getAngle()[i]) * delta;
						enemy2.getY()[i] += enemy2.getV()[i] * Math.sin(enemy2.getAngle()[i]) * delta * (-1.0);
						enemy2.getAngle()[i] += enemy2.getRV()[i] * delta;
						
						double threshold = GameLib.HEIGHT * 0.30;
						
						if(previousY < threshold && enemy2.getY()[i] >= threshold) {
							
							if(enemy2.getX()[i] < GameLib.WIDTH / 2) enemy2.getRV()[i] = 0.003;
							else enemy2.getRV()[i] = -0.003;
						}
						
						if(enemy2.getRV()[i] > 0 && Math.abs(enemy2.getAngle()[i] - 3 * Math.PI) < 0.05){
							
							enemy2.getRV()[i] = 0.0;
							enemy2.getAngle()[i] = 3 * Math.PI;
							shootNow = true;
						}
						
						if(enemy2.getRV()[i] < 0 && Math.abs(enemy2.getAngle()[i]) < 0.05){
							
							enemy2.getRV()[i] = 0.0;
							enemy2.getAngle()[i] = 0.0;
							shootNow = true;
						}
																		
						if(shootNow){

							double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
							int [] freeArray = findFreeIndex(enemy_projectile.getStates(), angles.length);

							for(int k = 0; k < freeArray.length; k++){
								
								int free = freeArray[k];
								
								if(free < enemy_projectile.getStates().length){
									
									double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
									double vx = Math.cos(a);
									double vy = Math.sin(a);
										
									enemy_projectile.getX()[free] = enemy2.getX()[i];
									enemy_projectile.getY()[free] = enemy2.getY()[i];
									enemy_projectile.getVX()[free] = vx * 0.30;
									enemy_projectile.getVY()[free] = vy * 0.30;
									enemy_projectile.getStates()[free] = 1;
								}
							}
						}
					}
				}
			}
			
			/* verificando se novos inimigos (tipo 1) devem ser "lançados" */
			
			enemy1.throwNew(currentTime);
			
			/* verificando se novos inimigos (tipo 2) devem ser "lançados" */
			
			enemy2.throwNew(currentTime);
			
			/* Verificando se a explosão do player já acabou.         */
			/* Ao final da explosão, o player volta a ser controlável */
			player.reset(currentTime);
			
			/********************************************/
			/* Verificando entrada do usuário (teclado) */
			/********************************************/
			// vamo fazer um método do player pra isso dps, mas preisaria criar o objeto do projétil antes
			
			running = player.keys(delta, currentTime, player_projectile);
			
			/* Verificando se coordenadas do player ainda estão dentro	*/
			/* da tela de jogo após processar entrada do usuário.       */
			
			player.screenLimits();

			/*******************/
			/* Desenho da cena */
			/*******************/
			
			/* desenhando plano fundo distante */
			
			estrela1.draw(delta, Color.GRAY, 3);
			estrela2.draw(delta, Color.DARK_GRAY, 2);
						
			/* desenhando player */
			
			player.draw(currentTime);
				
			
			/* deenhando projeteis (player) */
			
			for(int i = 0; i < player_projectile.getStates().length; i++){
				
				if(player_projectile.getStates()[i] == ACTIVE){
					
					GameLib.setColor(Color.GREEN);
					GameLib.drawLine(player_projectile.getX()[i], player_projectile.getY()[i] - 5, player_projectile.getX()[i], player_projectile.getY()[i] + 5);
					GameLib.drawLine(player_projectile.getX()[i] - 1, player_projectile.getY()[i] - 3, player_projectile.getX()[i] - 1, player_projectile.getY()[i] + 3);
					GameLib.drawLine(player_projectile.getX()[i] + 1, player_projectile.getY()[i] - 3, player_projectile.getX()[i] + 1, player_projectile.getY()[i] + 3);
				}
			}
			
			/* desenhando projeteis (inimigos) */
		
			for(int i = 0; i < enemy_projectile.getStates().length; i++){
				
				if(enemy_projectile.getStates()[i] == ACTIVE){
	
					GameLib.setColor(Color.RED);
					GameLib.drawCircle(enemy_projectile.getX()[i], enemy_projectile.getY()[i], enemy_projectile.getRadius());
				}
			}
			
			/* desenhando inimigos (tipo 1) */
			
			for(int i = 0; i < enemy1.getStates().length; i++){
				
				if(enemy1.getStates()[i] == EXPLODING){
					
					double alpha = (currentTime - enemy1.getExplosion_start()[i]) / (enemy1.getExplosion_end()[i] - enemy1.getExplosion_start()[i]);
					GameLib.drawExplosion(enemy1.getX()[i], enemy1.getY()[i], alpha);
				}
				
				if(enemy1.getStates()[i] == ACTIVE){
			
					GameLib.setColor(Color.CYAN);
					GameLib.drawCircle(enemy1.getX()[i], enemy1.getY()[i], enemy1.getRadius());
				}
			}
			
			/* desenhando inimigos (tipo 2) */
			
			for(int i = 0; i < enemy2.getStates().length; i++){
				
				if(enemy2.getStates()[i] == EXPLODING){
					
					double alpha = (currentTime - enemy2.getExplosion_start()[i]) / (enemy2.getExplosion_end()[i] - enemy2.getExplosion_start()[i]);
					GameLib.drawExplosion(enemy2.getX()[i], enemy2.getY()[i], alpha);
				}
				
				if(enemy2.getStates()[i] == ACTIVE){
			
					GameLib.setColor(Color.MAGENTA);
					GameLib.drawDiamond(enemy2.getX()[i], enemy2.getY()[i], enemy2.getRadius());
				}
			}
			
			/* chamama a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */
			
			busyWait(currentTime + 5);
		}
		
		System.exit(0);
	}
}
