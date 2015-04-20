package br.iesb.pos.mobile.android1;

public enum Longitude {

	_1, _2, _3, _4, _5, _6, _7, _8, _9, _10;

	public int paraIndice() {
		return this.ordinal();
	}

	public static Longitude paraLongitude(int indice) {
		return Longitude.values()[indice];
	}

	public static Longitude parse(String valor) {
		String str = "_" + valor.toUpperCase();
		return Longitude.valueOf(str);
	}

	public String descricao() {
		return this.name().substring(1);
	}
}
