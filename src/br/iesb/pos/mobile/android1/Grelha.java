package br.iesb.pos.mobile.android1;

import java.util.Arrays;
import java.util.Random;

public class Grelha {

	private static final int TAMANHO_MAXIMO = 10;

	private Random random = new Random();

	// Legenda: A = Agua, P = Porta avioes, N = Navio, T = Tiro
	private char plano[][];
	private int tentativas = 0;
	private int pontuacaoMaxima = 0;
	private int pontuacao = 0;

	public Grelha(int tentativas) {
		this.tentativas = tentativas;
		this.plano = new char[TAMANHO_MAXIMO][TAMANHO_MAXIMO];
		for (int i = 0; i < plano.length; i++) {
			Arrays.fill(plano[i], '_');
		}
		this.pontuacaoMaxima += posicionarPortaAvioes(this.plano, 1);
		this.pontuacaoMaxima += posicionarNavios(this.plano, 4, 1);
		this.pontuacaoMaxima += posicionarNavios(this.plano, 3, 2);
		this.pontuacaoMaxima += posicionarNavios(this.plano, 2, 3);
		this.pontuacaoMaxima += posicionarNavios(this.plano, 1, 4);
	}

	private int posicionarPortaAvioes(char[][] plano, int qtde) {

		Coordenada centro = new Coordenada(
				random.nextInt(TAMANHO_MAXIMO - 2) + 1,
				random.nextInt(TAMANHO_MAXIMO - 2) + 1);

		int x = centro.getLongitude().ordinal();
		int y = centro.getLatidude().ordinal();
		plano[y][x] = 'P';
		plano[y + 1][x] = 'P';
		plano[y - 1][x] = 'P';
		plano[y - 1][x - 1] = 'P';
		plano[y - 1][x + 1] = 'P';

		return qtde * 5;

	}

	private int posicionarNavios(char[][] plano, int canos, int qtde) {

		int resultado = qtde * canos;
		
		do {

			Coordenada coord = new Coordenada(random.nextInt(TAMANHO_MAXIMO),
					random.nextInt(TAMANHO_MAXIMO));
			boolean horizontal = Boolean.valueOf(random.nextInt(2) > 0);

			if (ehPosicaoValida(plano, canos, coord, horizontal)) {
				preencherEspaco(plano, canos, coord, horizontal);
				qtde--;
			}

		} while (qtde > 0);

		return resultado;

	}

	private void preencherEspaco(char[][] plano, int canos, Coordenada coord,
			boolean horizontal) {

		int x = coord.getLongitude().ordinal();
		int y = coord.getLatidude().ordinal();
		if (horizontal) {
			for (int i = x; i < x + canos; i++) {
				plano[y][i] = 'N';
			}
		} else {
			for (int i = y; i < y + canos; i++) {
				plano[i][x] = 'N';
			}
		}

	}

	private boolean ehPosicaoValida(char[][] plano, int canos,
			Coordenada coord, boolean horizontal) {
		int x = coord.getLongitude().ordinal();
		int y = coord.getLatidude().ordinal();
		boolean posicaoValida = false;
		boolean vaiUltrapassarLimiteHorizontal = (x + canos >= TAMANHO_MAXIMO);
		boolean vaiUltrapassarLimiteVertical = (y + canos >= TAMANHO_MAXIMO);
		if (!vaiUltrapassarLimiteHorizontal && !vaiUltrapassarLimiteVertical) {
			posicaoValida = !vaiColidirComNavio(plano, canos, coord, horizontal);
		}

		return posicaoValida;
	}

	private boolean vaiColidirComNavio(char[][] plano, int canos,
			Coordenada coord, boolean horizontal) {
		int x = coord.getLongitude().ordinal();
		int y = coord.getLatidude().ordinal();
		boolean vaiColidir = false;
		if (horizontal) {
			for (int i = x; i < x + canos; i++) {
				if (plano[y][i] == 'N' || plano[y][i] == 'P') {
					vaiColidir = true;
					break;
				}
			}
		} else {
			for (int i = y; i < y + canos; i++) {
				if (plano[i][x] == 'N' || plano[i][x] == 'P') {
					vaiColidir = true;
					break;
				}
			}
		}
		return vaiColidir;
	}

	public void exibirTentativas() {

		exibir("_AT");

	}

	public void exibirTudo() {

		exibir("_APNT");

	}

	private void exibir(String filtroCaracteres) {
		System.out.print('\t');
		for (int x = 0; x < plano.length; x++) {
			System.out.print(Longitude.paraLongitude(x).descricao() + "\t");
		}
		System.out.println();
		for (int y = 0; y < plano.length; y++) {
			System.out.print(Latitude.paraLatitude(y).descricao() + "\t");
			for (int x = 0; x < plano.length; x++) {
				if (filtroCaracteres.contains(String.valueOf(plano[y][x]))) {
					System.out.print(plano[y][x] + "\t");
				} else {
					System.out.print("_\t");
				}
			}
			System.out.println();
		}
	}

	public String atirar(Coordenada coord) {
		String resultado = "GAME OVER";
		if (!gameOver()) {
			int x = coord.getLongitude().ordinal();
			int y = coord.getLatidude().ordinal();
			switch (this.plano[y][x]) {
			case '_':
				resultado = "AGUA";
				this.plano[y][x] = 'A';
				this.tentativas--;
				break;
			case 'T':
				resultado = "ALVO JA ACERTADO";
				break;

			case 'A':
				resultado = "TIRO JA DISPERDICADO";
				break;

			case 'N':
				resultado = "TIRO - NAVIO";
				this.plano[y][x] = 'T';
				this.pontuacao++;
				this.tentativas--;
				break;

			case 'P':
				resultado = "TIRO - PORTA-AVIAO";
				this.plano[y][x] = 'T';
				this.pontuacao++;
				this.tentativas--;
				break;
			}
		}
		return resultado;
	}

	public int tentativasRestantes() {
		return this.tentativas;
	}

	public int pontuacao() {
		return this.pontuacao;
	}
	
	public int pontuacaoMaxima() {
		return this.pontuacaoMaxima;
	}
	
	public boolean gameOver() {
		return (tentativas <= 0 || pontuacao >= pontuacaoMaxima);
	}
	
	public boolean vencedor() {
		return pontuacao >= pontuacaoMaxima;
	}

}
