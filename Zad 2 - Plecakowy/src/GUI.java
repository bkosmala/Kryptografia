import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


public class GUI extends JFrame{
		
	private JLabel tekstJawnyLbl;
	private JLabel tekstJawnyBitowoLbl;
	private JLabel tekstZaszyfrowanyLbl;
	private JLabel tekstOdkodowanyLbl;
	
	private JTextField tekstJawny;
	private JTextField tekstOdkodowany;
	
	private JTextArea tekstJawnyBitowo;
	private JTextArea tekstZaszyfrowany;
	
	private JButton bitowoBtn;
	private JButton szyfrujBtn;
	private JButton odkodujBtn;

	
	
	public GUI(final Algorytm plecakowy){
		Container c = getContentPane(); 
		setSize(330, 360);
		setMinimumSize(new Dimension(640,500));
		setTitle("Zadanie 2 - algorytm plecakowy");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.setLayout(null);
		
		tekstJawnyLbl = new JLabel("Tekst jawny:");
		tekstJawnyLbl.setBounds(10, 10, 300, 30);
		c.add(tekstJawnyLbl);
		
		tekstJawny = new JTextField();
		tekstJawny.setBounds(10, 40, 600, 30);
		c.add(tekstJawny);
		
		szyfrujBtn = new JButton("Szyfruj");
		szyfrujBtn.setBounds(460, 80, 150, 30);
		c.add(szyfrujBtn);
		
		tekstJawnyBitowoLbl = new JLabel("Tekst jawny w postaci bitowej:");
		tekstJawnyBitowoLbl.setBounds(10, 80, 300, 30);
		c.add(tekstJawnyBitowoLbl);
		
		tekstJawnyBitowo = new JTextArea();
		tekstJawnyBitowo.setLineWrap(true);
		tekstJawnyBitowo.setBounds(10, 120, 600, 90);
		c.add(tekstJawnyBitowo);
				
		tekstZaszyfrowanyLbl = new JLabel("Tekst zaszyfrowany:");
		tekstZaszyfrowanyLbl.setBounds(10, 220, 300, 30);
		c.add(tekstZaszyfrowanyLbl);
		
		tekstZaszyfrowany = new JTextArea();
		tekstZaszyfrowany.setLineWrap(true);
		tekstZaszyfrowany.setBounds(10, 260, 600, 90);
		c.add(tekstZaszyfrowany);
		
		odkodujBtn = new JButton("Odkoduj");
		odkodujBtn.setBounds(460, 360, 150, 30);
		c.add(odkodujBtn);
		
		tekstOdkodowanyLbl = new JLabel("Tekst odkodowany:");
		tekstOdkodowanyLbl.setBounds(10, 360, 300, 30);
		c.add(tekstOdkodowanyLbl);
		
		tekstOdkodowany = new JTextField();
		tekstOdkodowany.setBounds(10, 400, 600, 30);
		c.add(tekstOdkodowany);
		
		//Listenery
		
		szyfrujBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0){	
				String jawny=tekstJawny.getText();
				tekstJawnyBitowo.setText(plecakowy.tekstJawnyNaBity(jawny));
				tekstZaszyfrowany.setText(plecakowy.getZakodowany());
			}
		});
		
		odkodujBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0)
			{
				plecakowy.setDeszyfrowany(tekstZaszyfrowany.getText());
				tekstOdkodowany.setText(plecakowy.odkodowanyTekst());
			}
		});
		
		
		
		
		/*zaszyfrujPlik = new JButton("Zaszyfruj plik");
		zaszyfrujPlik.setBounds(10, 10, 150, 30);
		c.add(zaszyfrujPlik);
		
		odszyfrujPlik = new JButton("Odszyfruj plik");
		odszyfrujPlik.setBounds(10, 50, 150, 30);
		c.add(odszyfrujPlik);
		
		zaszyfrujTekst = new JButton("Zaszyfruj teskt");
		zaszyfrujTekst.setBounds(10, 140, 150, 30);
		c.add(zaszyfrujTekst);
		
		odszyfrujTekst = new JButton("Odszyfruj tekst");
		odszyfrujTekst.setBounds(10, 220, 150, 30);
		c.add(odszyfrujTekst);
		
		zaszyfrujPlik.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0){
				
				//odczytujemy plik jawny
				String jawna;
				wybor = new JFileChooser();
				int w =wybor.showOpenDialog(GUI.this);
				
				//plik= new File("in.xml");
				if(wybor.getSelectedFile()!=null && w==0){
				jawny=wybor.getSelectedFile();
				try {
					jawna = plik.odczytajPlik(jawny);
				//zapisujemy plik jawny w postaci bitowej
				kod.zapiszJawnyBitowo(jawna);//to tablicy jawna
				//zakoduj plik , przerabia plik jawna na zakodowana
				kod.zakoduj(); 
				w =wybor.showSaveDialog(GUI.this);
				if(wybor.getSelectedFile()!=null && w==0){
				zaszyfrowany=wybor.getSelectedFile();
				//zapisuje tablice int to pliku jako string
				plik.zapiszPlik(zaszyfrowany,kod.zakodowana);//do pliku zaszyfrowany
				}
				} catch (IOException e) {
					e.printStackTrace();
				}
				}
				
				
			}
		});
		odszyfrujPlik.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0){
				//odczytuje plik zakodowany
				String zaszyfrowana;
				wybor = new JFileChooser();
				int w =wybor.showOpenDialog(GUI.this);
				
				if(wybor.getSelectedFile()!=null && w==0){
					zaszyfrowany=wybor.getSelectedFile();
					
				try {
				
				zaszyfrowana = plik.odczytajPlik(zaszyfrowany);
				//odczytane bity zaszyfrowane przekazuje do tablicy odkodowana
				kod.odkoduj(zaszyfrowana);
				//przekszta³camy tablica na string
				String odkodowana =kod.odczytajWiadomosc(kod.odkodowana);
				System.out.println("Tutaj"+odkodowana+"  ");
				
				//zapisanie stringu do pliku
				w =wybor.showSaveDialog(GUI.this);
				if(wybor.getSelectedFile()!=null && w==0){
					odszyfrowany=wybor.getSelectedFile();
					plik.zapiszWiadomosc(odszyfrowany,odkodowana);
				}
				} catch (IOException e) {
					e.printStackTrace();
				}
				}
			}
		});
		zaszyfrujTekst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				String jawna=textJawny.getText();
				kod.zapiszJawnyBitowo(jawna);//to tablicy jawna
				kod.zakoduj(); 
				textZaszyfrowany.setText(kod.odczytajKod(kod.zakodowana));
				
			}
		});
		odszyfrujTekst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				String zaszyfrowana=textZaszyfrowany.getText();
				System.out.println("Zaszyfrowana"+zaszyfrowana);
				kod.odkodujTekst(zaszyfrowana);
				
				String odkodowana = kod.odczytajWiadomosc(kod.odkodowana);
				textOdszyfrowany.setText(odkodowana);
				
				
			}
		});
		////////////////////pola z danymi///////
		textJawny = new JTextField();
		textJawny.setBounds(10, 100, 300, 30);
		c.add(textJawny);
		
		textZaszyfrowany = new JTextField();
		textZaszyfrowany.setBounds(10, 180, 300, 30);
		c.add(textZaszyfrowany);
		
		textOdszyfrowany = new JTextField();
		textOdszyfrowany.setBounds(10, 260, 300, 30);
		c.add(textOdszyfrowany);
		/////////menu//////////////
		JMenuItem kluczJawnyWczytaj=new JMenuItem("Wczytaj jawny");
		JMenuItem kluczJawnyZapisz=new JMenuItem("Zapisz jawny");
		JMenuItem kluczTajnyWczytaj=new JMenuItem("Wczytaj tajny");
		JMenuItem kluczTajnyZapisz=new JMenuItem("Zapisz tajny");
		JMenuItem kluczGeneruj=new JMenuItem("Generuj");
		
		kluczJawnyZapisz.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				wybor = new JFileChooser();
				int w =wybor.showSaveDialog(GUI.this);
				
				if(wybor.getSelectedFile()!=null && w==0){
					kluczJawny=wybor.getSelectedFile();
					try {
						kod.zapiszKluczJawny(kluczJawny);
					} catch (IOException e) {
						e.printStackTrace();
					}
				
				}
			}
		});
		
		kluczJawnyWczytaj.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				wybor = new JFileChooser();
				int w =wybor.showOpenDialog(GUI.this);
				
				if(wybor.getSelectedFile()!=null && w==0){
					kluczJawny=wybor.getSelectedFile();
					try {
						kod.wczytajKluczJawny(kluczJawny);
					} catch (IOException e) {
						e.printStackTrace();
					}
				
				}
				
			}
		});
		kluczTajnyWczytaj.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				wybor = new JFileChooser();
				int w =wybor.showOpenDialog(GUI.this);
				
				if(wybor.getSelectedFile()!=null && w==0){
					kluczTajny=wybor.getSelectedFile();
					try {
						kod.wczytajKluczTajny(kluczTajny);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		kluczTajnyZapisz.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				wybor = new JFileChooser();
				int w =wybor.showSaveDialog(GUI.this);
				
				if(wybor.getSelectedFile()!=null && w==0){
					kluczTajny=wybor.getSelectedFile();
					try {
						kod.zapiszKluczTajny(kluczTajny);
					} catch (IOException e) {
						e.printStackTrace();
					}
				
				}
				
			}
		});
		kluczGeneruj.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				kod.generujKlucze();
			}
		});
		JMenuItem zakoncz=new JMenuItem("Zakoñcz");
		zakoncz.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.exit(0);
			}
		});
		
		meni2.add(kluczJawnyWczytaj);
		meni2.add(kluczJawnyZapisz);
		meni2.add(kluczTajnyWczytaj);
		meni2.add(kluczTajnyZapisz);
		meni2.add(kluczGeneruj);
		meni.add(zakoncz);
		mbar.add(meni);
		mbar.add(meni2);
		setJMenuBar(mbar);*/
		setVisible(true);
	}

}
