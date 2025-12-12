package controladorCorreos;

import java.util.ArrayList;
import java.util.Date;

import modelo.Correo;
import vista.VistaGeneralCorreo;


public class MainCorreos {


	public static void main(String[] args) {

		VistaGeneralCorreo vistaGeneral = new VistaGeneralCorreo("example@gmail.com");
		ArrayList<Correo> correos = new ArrayList<>();
		Correo correo = new Correo("fwwqfeq", "fegjewgiew", new Date(), "GWEJGNWI");
		correos.add(correo);
		vistaGeneral.cargarCorreos(correos);
		vistaGeneral.setVisible(true);
		vistaGeneral.getBotonEnviarCorreo().addActionListener(new OyenteBotonEnviar(vistaGeneral.getCorreo()));
		vistaGeneral.getEmailTabla().addMouseListener(new OyenteTabla(vistaGeneral.getEmailTabla(), correos));

		
	}

}
