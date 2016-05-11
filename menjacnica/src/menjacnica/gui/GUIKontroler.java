package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {
	
	private static DodajKursGUI dodajKurs;
	private static IzvrsiZamenuGUI izvrsiZamenu;
	private static ObrisiKursGUI obrisiKurs;
	private static MenjacnicaGUI menjacnica;
	private static Menjacnica sistem;
	private static Valuta valuta;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sistem=new Menjacnica();
					menjacnica=new MenjacnicaGUI();					
					menjacnica.setVisible(true);
					menjacnica.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(menjacnica.getContentPane(),
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(menjacnica.getContentPane(),
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(menjacnica.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnica.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(menjacnica.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				menjacnica.prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnica.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	public static void prikaziDodajKursGUI() {
		dodajKurs = new DodajKursGUI(menjacnica);
		dodajKurs.setVisible(true);
		dodajKurs.setLocationRelativeTo(null);
		
	}

	public static void prikaziObrisiKursGUI() {
		
		if (menjacnica.getTable().getSelectedRow() != -1) {
			
			MenjacnicaTableModel model = (MenjacnicaTableModel)(menjacnica.getTable().getModel());		
			valuta = model.vratiValutu(menjacnica.getTable().getSelectedRow());
			obrisiKurs = new ObrisiKursGUI(menjacnica,model.vratiValutu(menjacnica.getTable().getSelectedRow()));
			obrisiKurs.setVisible(true);
			obrisiKurs.setLocationRelativeTo(null);
			prikaziValutu();
			
		}
	}
	
	public static void prikaziIzvrsiZamenuGUI() {
		if (menjacnica.getTable().getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(menjacnica.getTable().getModel());
			valuta = model.vratiValutu(menjacnica.getTable().getSelectedRow());
			izvrsiZamenu = new IzvrsiZamenuGUI(menjacnica,
					model.vratiValutu(menjacnica.getTable().getSelectedRow()));
			izvrsiZamenu.setVisible(true);
			izvrsiZamenu.setLocationRelativeTo(null);
			prikaziValutuIzvrsiZamenu();
			
		}
	}
	
	public static void unesiKurs
	(String naziv, String skraceniNaziv, Integer sifra, String prodajni, String kupovni, String srednji) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			
				valuta.setNaziv(naziv);
				valuta.setSkraceniNaziv(skraceniNaziv);
				valuta.setSifra(sifra);
				valuta.setProdajni(Double.parseDouble(prodajni));
				valuta.setKupovni(Double.parseDouble(kupovni));
				valuta.setSrednji(Double.parseDouble(srednji));
			
			
			// Dodavanje valute u kursnu listu
			 sistem.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			menjacnica.prikaziSveValute();
			
			dodajKurs.dispose();
			
		} catch (Exception e1) {			
			JOptionPane.showMessageDialog(dodajKurs.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void obrisiValutu() {
		try{
			sistem.obrisiValutu(valuta);
			
			menjacnica.prikaziSveValute();
			obrisiKurs.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(obrisiKurs.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void prikaziValutu() {
		// Prikaz podataka o valuti
		
		obrisiKurs.getTextFieldNaziv().setText(valuta.getNaziv());
		obrisiKurs.getTextFieldSkraceniNaziv().setText(valuta.getSkraceniNaziv());
		obrisiKurs.getTextFieldSifra().setText(""+valuta.getSifra());
		obrisiKurs.getTextFieldProdajniKurs().setText(""+valuta.getProdajni());
		obrisiKurs.getTextFieldKupovniKurs().setText(""+valuta.getKupovni());
		obrisiKurs.getTextFieldSrednjiKurs().setText(""+valuta.getSrednji());				
	}
	
	public static void prikaziValutuIzvrsiZamenu(){
		izvrsiZamenu.getTextFieldProdajniKurs().setText(""+valuta.getProdajni());
		izvrsiZamenu.getTextFieldKupovniKurs().setText(""+valuta.getKupovni());
		izvrsiZamenu.getTextFieldValuta().setText(valuta.getSkraceniNaziv());
	}
	

	public static void izvrsiZamenu(){
		try{
			double konacniIznos = 
					sistem.izvrsiTransakciju(valuta,
							izvrsiZamenu.getRdbtnProdaja().isSelected(), 
							Double.parseDouble(izvrsiZamenu.getTextFieldIznos().getText()));
		
			izvrsiZamenu.getTextFieldKonacniIznos().setText(""+konacniIznos);
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(izvrsiZamenu.getContentPane(), e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
	}
	}
	public static List<Valuta> vratiKursnuListu() {		
		return sistem.vratiKursnuListu();
	}

}
