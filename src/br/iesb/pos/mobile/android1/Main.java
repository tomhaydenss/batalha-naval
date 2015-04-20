package br.iesb.pos.mobile.android1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	private static final int NUMERO_TENTATIVAS = 30;

	public static void main(String[] args) throws IOException {
		Grelha grelha = new Grelha(NUMERO_TENTATIVAS);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));

		while (!grelha.gameOver()) {
			grelha.exibirTudo();

			System.out
					.print("\n\nInforme a coordenada (ex. A10) ou S para SAIR: ");

			String linha = reader.readLine();

			if ("S".equalsIgnoreCase(linha)) {
				System.exit(1);
			}

			try {

				String lat = linha.substring(0, 1);
				String lon = linha.substring(1);
				Latitude latidude = Latitude.parse(lat);
				Longitude longitude = Longitude.parse(lon);
				Coordenada coord = new Coordenada(latidude, longitude);

				String resultado = grelha.atirar(coord);

				System.out.println("\n\nResultado: " + resultado);
				System.out.println("Tentativas Restantes: "
						+ grelha.tentativasRestantes());
				System.out.println("Pontuacao: " + grelha.pontuacao() + "/"
						+ grelha.pontuacaoMaxima() + "\n\n");

			} catch (IllegalArgumentException e) {
				System.err.print("Coordenada invalida: " + linha + "\n\n");
			}

		}

		boolean venceu = grelha.vencedor();
		System.out
				.println("__________________________________________________________________________________");
		System.out.println("\n\t\t\t\tFIM DE JOGO\n\n\n");
		grelha.exibirTudo();
		System.out
				.println("\n\n\t\t\t\tVOCE " + (venceu ? "VENCEU" : "PERDEU"));
		System.out
				.println("__________________________________________________________________________________");

	}

}
