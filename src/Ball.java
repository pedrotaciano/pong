import java.awt.*;
import java.util.Random;

/**
	Esta classe representa a bola usada no jogo. A classe princial do jogo (Pong)
	instancia um objeto deste tipo quando a execução é iniciada.
*/

public class Ball {
	private double cx;
	private double cy;
	private double width;
	private double height;
	private Color color;
	private double speed;
	private double coordenateX;
	private double coordenateY;

	/**
		Construtor da classe Ball. Observe que quem invoca o construtor desta classe define a velocidade da bola 
		(em pixels por millisegundo), mas não define a direção deste movimento. A direção do movimento é determinada 
		aleatóriamente pelo construtor.

		@param cx coordenada x da posição inicial da bola (centro do retangulo que a representa).
		@param cy coordenada y da posição inicial da bola (centro do retangulo que a representa).
		@param width largura do retangulo que representa a bola.
		@param height altura do retangulo que representa a bola.
		@param color cor da bola.
		@param speed velocidade da bola (em pixels por millisegundo).
	*/

	public Ball(double cx, double cy, double width, double height, Color color, double speed){
		
		this.cx = cx;
		this.cy = cy;
		this.width = width;
		this.height = height;
		this.color = color;
		this.speed = speed;

		this.coordenateX = this.randomizeDirection();
		this.coordenateY = this.randomizeDirection();
	}

	private double randomizeDirection(){
		Random random = new Random();
		int randomInt = random.nextInt(2);

		if(randomInt == 0){
			return -(Math.abs(this.speed));
		} else return (Math.abs(this.speed));
	}

	/**
		Método chamado sempre que a bola precisa ser (re)desenhada.
	*/

	public void draw(){

		GameLib.setColor(this.color);
		GameLib.fillRect(this.cx, this.cy, this.width, this.height);
	}

	/**
		Método chamado quando o estado (posição) da bola precisa ser atualizado.
		
		@param delta quantidade de millisegundos que se passou entre o ciclo anterior de atualização do jogo e o atual.
	*/

	public void update(long delta){

		this.cx += delta * this.coordenateX;
		this.cy += delta * this.coordenateY;
	}

	/**
		Método chamado quando detecta-se uma colisão da bola com um jogador.
	
		@param playerId uma string cujo conteúdo identifica um dos jogadores.
	*/

	public void onPlayerCollision(String playerId){
		if(playerId.equals("Player 1")){
			this.coordenateX = Math.abs(this.speed);

		} else {
			this.coordenateX = - Math.abs(this.speed);
		}
	}

	/**
		Método chamado quando detecta-se uma colisão da bola com uma parede.

		@param wallId uma string cujo conteúdo identifica uma das paredes da quadra.
	*/

	public void onWallCollision(String wallId){
		switch (wallId){
			case "Top":
				this.coordenateY = Math.abs(this.speed);
				break;
			case "Bottom":
				this.coordenateY = -Math.abs(this.speed);
				break;
			case "Left":
				this.coordenateX = Math.abs(this.speed);
				break;
			case "Right":
				this.coordenateX = -Math.abs(this.speed);
				break;
		}
	}

	/**
		Método que verifica se houve colisão da bola com uma parede.

		@param wall referência para uma instância de Wall contra a qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/
	
	public boolean checkCollision(Wall wall){
		String wallId = wall.getId();

		switch (wallId){
			case "Top":
				double ballTop = this.cy - (this.height / 2);
				double wallBottom = wall.getCy() + (wall.getHeight() / 2);

				if(ballTop <= wallBottom){
					return true;
				}
				break;
			case "Bottom":
				double ballBottom = this.cy + (this.height / 2);
				double wallTop = wall.getCy() - (wall.getHeight() / 2);

				if(ballBottom >= wallTop){
					return true;
				}
				break;
			case "Left":
				double ballLeft = this.cx - (this.height / 2);
				double wallRight = wall.getCx() + (wall.getWidth() / 2);

				if(ballLeft <= wallRight){
					return true;
				}
				break;
			case "Right":
				double ballRight = this.cx + (this.width / 2);
				double wallLeft = wall.getCx() - (wall.getWidth() / 2);

				if(ballRight >= wallLeft){
					return true;
				}
				break;
		}
		return false;
	}

	/**
		Método que verifica se houve colisão da bola com um jogador.

		@param player referência para uma instância de Player contra o qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/	

	public boolean checkCollision(Player player){
		// check bola
		double ballBottom = this.cy + (this.height / 2);
		double ballTop = this.cy - (this.height / 2);

		double ballRight = this.cx + (this.width / 2);
		double ballLeft = this.cx - (this.width / 2);

		// check player
		double playerBottom = player.getCy() + (player.getHeight() / 2);
		double playerTop = player.getCy() - (player.getHeight() / 2);

		double playerRight = player.getCx() + (player.getWidth() / 2);
		double playerLeft = player.getCx() - (player.getWidth() / 2);

		// check colisoes
		boolean rightColision = ballLeft <= playerRight;
		boolean leftColision = ballRight >= playerLeft;

		// check bola rebatida
		// bola rebatida se a bola estiver entre o topo e a base do jogador
		boolean ballHit = ballBottom >= playerTop && ballTop <= playerBottom;

		return rightColision && leftColision && ballHit;
	}

	/**
		Método que devolve a coordenada x do centro do retângulo que representa a bola.
		@return o valor double da coordenada x.
	*/
	
	public double getCx(){

		return this.cx;
	}

	/**
		Método que devolve a coordenada y do centro do retângulo que representa a bola.
		@return o valor double da coordenada y.
	*/

	public double getCy(){

		return this.cy;
	}

	/**
		Método que devolve a velocidade da bola.
		@return o valor double da velocidade.

	*/

	public double getSpeed(){

		return this.speed;
	}

}
