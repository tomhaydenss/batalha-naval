package br.iesb.pos.mobile.android1;

public enum Latitude {

	_A, _B, _C, _D, _E, _F, _G, _H, _I, _J;

	public int paraIndice() {
		return this.ordinal();
	}
	
	public static Latitude paraLatitude(int indice) {
		return Latitude.values()[indice];
	}
	
	public static Latitude parse(String valor) {
		String str = "_" + valor.toUpperCase();
		return Latitude.valueOf(str);
	}
	
	public String descricao() {
		return this.name().substring(1);
	}

}
