package game;

import elementos.entidades.Enemy1;
import elementos.entidades.Enemy2;
import elementos.entidades.Player;
import elementos.background.*;
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
		
		Player player = new Player(currentTime);

		/* variáveis dos projéteis disparados pelo player */
		
		PlayerProjectile player_projectile = new PlayerProjectile();

		/* variáveis dos inimigos tipo 1 */
		
		Enemy1 enemy1 = new Enemy1(currentTime);
		
		/* variáveis dos inimigos tipo 2 */
		Enemy2 enemy2 = new Enemy2(currentTime);
		
		/* variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto tipo 2) */

		EnemyProjectile enemy_projectile = new EnemyProjectile();
		
		Estrela1 estrela1 = new Estrela1();
		Estrela2 estrela2 = new Estrela2();
		
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
						
			
			/* colisões projeteis (player) - inimigos */
			player.explode(currentTime, enemy1);
			player.explode(currentTime, enemy2);

			player_projectile.explode(currentTime, enemy1);
			player_projectile.explode(currentTime, enemy2);
			
			enemy_projectile.explode(currentTime, player);
				
			/***************************/
			/* Atualizações de estados */
			/***************************/
			
			/* projeteis (player) */
			
			player_projectile.track(delta);
			
			/* projeteis (inimigos) */
			
			enemy_projectile.track(delta);
						
			/* inimigos tipo 1 */
						
			enemy1.status(currentTime, delta, enemy_projectile, player);
						
			/* inimigos tipo 2 */

			enemy2.status(currentTime, delta, enemy_projectile, player);
			
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
			
			estrela1.draw(delta);
			estrela2.draw(delta);
						
			/* desenhando player */
			
			player.draw(currentTime);
			
			player_projectile.draw(true);
			enemy_projectile.draw(false);

			enemy1.draw(currentTime, 1);
			enemy2.draw(currentTime, 2);
			
			/* chamama a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */
			
			busyWait(currentTime + 5);
		}
		
		System.exit(0);
	}
}
