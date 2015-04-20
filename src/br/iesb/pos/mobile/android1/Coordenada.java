package br.iesb.pos.mobile.android1;

public class Coordenada {

	// horizontal (A - J)
	private Latitude latidude;

	// vertical (1 - 10)
	private Longitude longitude;

	public Coordenada(Latitude latidude, Longitude longitude) {
		super();
		this.latidude = latidude;
		this.longitude = longitude;
	}

	public Coordenada(int latidude, int longitude) {
		super();
		this.latidude = Latitude.paraLatitude(latidude);
		this.longitude = Longitude.paraLongitude(longitude);
	}

	public Latitude getLatidude() {
		return latidude;
	}

	public Longitude getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return "Coordenada [latidude=" + latidude + ", longitude=" + longitude
				+ "]";
	}

}
