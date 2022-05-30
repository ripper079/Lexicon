import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




public class CLexiconUppgifter 
{
	// Återställa färg
    public static final String RESET = "\u001B[0m";
    //Sveriges flagga färger
    public static final String GUL = "\u001B[33m";
    public static final String BLA = "\u001B[34m";
    public static boolean toogleColor = false;    
    
    //Filnamn  (uppgift 8 & 9)
  	public static final String FILNAMN = "info.dat";
  	
  	public static final int ANTAL_UPPGIFTER = 16;
   
	
	public static void main(String[] args) 
	{	
		//Alla modifikationer till GUI objekter görs ENDAST i AWT tråden
		EventQueue.invokeLater(() -> 
		{
			setupFrameAndListeners();													
		});				
	}
	
	private static void setupFrameAndListeners() 
	{
		final CValFrame myFrame = new CValFrame("Lexicon Uppgifter");
		//JFrame egenskapar
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
		myFrame.setVisible(true);
	}		
	
	//Skriv ut Hello World
	public static void uppgift01()
    {
        System.out.println("Hello World");
    }
	
	//Skriv ut användarinfo
	public static void uppgift02(String pForNamn, String pEfterNamn, int pAlder) 
	{
		System.out.println("Förnamn: " + pForNamn);
		System.out.println("Efternamn: " + pEfterNamn);
		System.out.println("Ålder: " + pAlder);		
	}
	
	//Funktionen finns inte i JAVA API, måste ta till externa dependencies för att lösa detta snyggt
	//Denna lösning fungerar beroende på om konsol stödjer det (ANSI)
	//Lättare i c# :D
	public static void uppgift03() 
	{				
		toogleColor = !toogleColor;
		if (toogleColor)
			System.out.println(GUL + "GUL Text" + RESET);
		else
			System.out.println(BLA + "BLÅ TEXT" + RESET);
	}
	
	//Skriv ut dagens datum
	public static void uppgift04() 
	{
		//Fetchar från system klockan		
		System.out.println(LocalDate.now());
	}
	
	//Skriv ut vilket tal är större
	public static void uppgift05(int pNummerEtt, int pNummerTva) 
	{
		System.out.println("Vilket tal är störst: " + pNummerEtt + " eller " + pNummerTva + "?");
		
		if (pNummerEtt > pNummerTva)
			System.out.println(pNummerEtt + " är större");
		else if (pNummerEtt < pNummerTva)
			System.out.println(pNummerTva + " är större");
		else
			System.out.println("Talen är lika");
	}
	
	//Gissa spelet
	public static void uppgift06() 
	{
		//SE TILL att hogstaTal är större än minstaTal
		final int minstaTal = 1;
		final int hogstaTal = 100;
		
		int slumpTal = (int)(Math.random()* (hogstaTal-minstaTal + 1) + minstaTal);
		
		//1-100
		//int slumpTal = (int)(Math.random()*100) +1;
		
		final int maxAntalGissningar = 10;
		int antalGissningar = 0;
		int spelareGissning = 0;
		boolean vinst = false;
		//False positive, garbage collector löser detta[vi ska eventuellt använda denna igen]
		Scanner scanner = new Scanner(System.in);		
				
		System.out.println("Gissa rätt tal spelet. Du får " + maxAntalGissningar + " försök");
		System.out.println();
		//Antar att spelare är snäll och skriver in heltal
		for (int i = 0; i < maxAntalGissningar; i++) 
		{
			++antalGissningar;
			System.out.println("Gissa ett tal");
			spelareGissning = scanner.nextInt();			
			if (spelareGissning == slumpTal) 
			{
				vinst = true;
				//System.out.println("Grattis! Du gissade rätt!!!");
				//System.out.println(antalGissningar + " försök tog det");
				break;
			}
			if (spelareGissning > slumpTal)
				System.out.println("För stort!");
			else if (spelareGissning < slumpTal)
				System.out.println("För litet!");
				
			
			System.out.println();
		}
		System.out.println();
		//Betyder att du vann
		if (vinst) 
		{
			System.out.println("Grattis! Du gissade rätt!!!");
			System.out.println(antalGissningar + " försök tog det");
		}
			
		else 
		{
			System.out.println("SLUT SPELAT!!! Rätt tal var: " + slumpTal);
			System.out.println("Du har har förbrukat alla dina " + maxAntalGissningar + " försök. Bättre lycka nästa gång!");
		}								
	}
	
	//Spara text till fil
	public static void uppgift07(String pTextAttSpara) 
	{				
		FileWriter fileWriter;
		try 
		{
			fileWriter = new FileWriter(CLexiconUppgifter.FILNAMN);
			fileWriter.write(pTextAttSpara);
			System.out.println("Texten'" + pTextAttSpara + "' infogades i filen:" + CLexiconUppgifter.FILNAMN);
			fileWriter.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.err.println("Gick inte skapa/spara fil");
		}								
	}
	
	//Ladda text från fil
	public static void uppgift08() 
	{		
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(CLexiconUppgifter.FILNAMN));
			int radNummerIFil = 0;
			
			String radStrangIFil;
	        System.out.println("Fil innehåll");
			while ((radStrangIFil = br.readLine()) != null) 
			{
				radNummerIFil++;
				System.out.println(radStrangIFil);
	        }			
			System.out.println("Antal lästa rader: " + radNummerIFil);
			br.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			System.err.println("Gick inte läsa fil");
		}
	}
	
	//Matematiska funtioner
	public static void uppgift09(double pTal) 
	{		
		//Kanske skriva i uppgiften om man ska avrunda...
		System.out.println("√" + pTal + "= " + Math.sqrt(pTal));
		System.out.println(pTal + "^2" + "= " + Math.pow(pTal, 2));
		System.out.println(pTal + "^10" + "= " + Math.pow(pTal, 10));
	} 
	
	//MultiplikationsTabell
	public static void uppgift10() 
	{
		//Första halvan tabell
		for (int i = 1; i <= 10; i++) 
		{
			for (int j = 1; j <= 5; j++) 
			{
				System.out.print(j + "x" + i + "=" + (i*j) + "\t\t");				
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		//Andra halvan tabell
		for (int i = 1; i <= 10; i++) 
		{
			for (int j = 6; j <= 10; j++) 
			{
				System.out.print(j + "x" + i + "=" + (i*j) + "\t\t");				
			}
			System.out.println();
		}
	}
	
	//Skapar 2 array - Osorterad och en sorterad
	public static void uppgift11() 
	{
		final int antalElement = 15;		
		
		int[] slumpadeTal = new int[antalElement];
		int[] sorteradeTal = new int[antalElement];
		
		//Slumpa fram tal, [1 - 100]
		for (int i = 0; i < antalElement; i++) 			
			slumpadeTal[i] = (int)(Math.random()*100) +1;			
		
		//Kopiera över
		for (int i =0; i < antalElement; i++)
			sorteradeTal[i] = slumpadeTal[i];
		
		//Sortera tal genom 'buble sort'
		for (int i = 0; i < (antalElement-1); i++) 
		{
			for (int j = 0; j < (antalElement-i-1);j++) 
			{
				//Byt plats om efterföljande tal är större, vi flyttar minsta värdet till vänster hela tiden
				if (sorteradeTal[j] > sorteradeTal[j+1])
				{
					int temp = sorteradeTal[j];
					sorteradeTal[j]=sorteradeTal[j+1];
					sorteradeTal[j+1] = temp;
				}
			}
		}		
		
		System.out.println();
		System.out.println("Osorterad Array");
		for (int i = 0; i < antalElement; i++)
			System.out.print(slumpadeTal[i] + "\t");
		System.out.println();

		System.out.println("Sorterad Array");
		for (int i = 0; i < antalElement; i++)
			System.out.print(sorteradeTal[i] + "\t");
		System.out.println();
	} 
	
	//Är palindron
	public static boolean uppgift12(String pText) 
	{
		boolean arPalidrom = true;
		
		//Skapa kopia och convertera till STORA bokstäver
		String originalStrang = new StringBuffer(pText).toString().toUpperCase();
		//Skapa omvända kopia och convertera till STORA bokstäver
		String omvandStrang = new StringBuffer(pText).reverse().toString().toUpperCase();
				
		//Tecken ska matcha alla positioner
		for (int i = 0; i < pText.length();i++) 
		{
			char tecken1 = originalStrang.charAt(i);
			char tecken2 = omvandStrang.charAt(i);
			
			if (tecken1 != tecken2) 
			{
				arPalidrom = false;
				break;
			}
		}
		
		if (arPalidrom)
			System.out.println("Ordet '" + pText + "' är ett palindrom");
		else
			System.out.println("Ordet '" + pText + "' är INTE ett palindrom");
		
		return arPalidrom;
	}
	
	//Skriva ut siffror mellan 2 intervall
	public static void uppgift13(int pTalStart, int pTalSlut) 
	{		
		//Gör inget
		if (pTalStart == pTalSlut)
			System.out.println("Finns inger intervall - Samma nummer");
		else 
		{
			//Korrigerar om pTalSlut > pTalStart
			if (pTalStart > pTalSlut) 
			{
				int temp = pTalStart;
				pTalStart = pTalSlut;
				pTalSlut = temp;
			}			
			
			System.out.println("Siffror mellan talen " + pTalStart + " och " + pTalSlut + " är");
			int diffInterVal = pTalSlut-pTalStart;
			for (int i = 1; i < diffInterVal; i++)
				System.out.print((pTalStart+i) + " ");
			System.out.println();
		}		
	}
	
	//Sortera efter udda/jämna siffror
	//Mycket viktigt pSiffror i form av "23,11,67,89,12,30,4,900,21"
	//Annars fungerar inte split funktionen
	public static void uppgift14(String pSiffror)
	{
		System.out.println("Komma-separerade värden är:");
		System.out.println(pSiffror);
		System.out.println();
		
		ArrayList<Integer> listaTal = new ArrayList<Integer>();
		ArrayList<Integer> listaTalJamna = new ArrayList<Integer>();
		ArrayList<Integer> listaTalUdda = new ArrayList<Integer>();
		
		String[] arrayStrangar = pSiffror.split(",");
		for (String ettStrangTal : arrayStrangar)
			listaTal.add(Integer.parseInt(ettStrangTal));				//Typomvandla här
		
		//Ingen bubble sort här
		Collections.sort(listaTal);
		for (Integer tal : listaTal) 
		{
			if ((tal % 2) == 0)
				listaTalJamna.add(tal);
			//Måste vara udda
			else
				listaTalUdda.add(tal);
		}
		
		Collections.sort(listaTalJamna);
		Collections.sort(listaTalUdda);
		
		System.out.println("Udda värden");
		for (Integer ettUddaTal : listaTalUdda)
			System.out.print(ettUddaTal + " ");
		System.out.println();
		System.out.println("Jämna värden");
		for (Integer ettJamntTal : listaTalJamna)
			System.out.print(ettJamntTal + " ");
		System.out.println();
		System.out.println();
	}
	
	//Adderar alla tal
	//Mycket viktigt pSiffror i form av "23,11,67,89,12,30,4,900,21"
	//Annars fungerar inte split funktionen
	public static void uppgift15(String pSiffror) 
	{		
		System.out.println("Komma-separerade värden är:");
		System.out.println(pSiffror);
		System.out.println();
		
		int summa = 0;
		
		String[] arrayStrangar = pSiffror.split(",");
		for (String ettStrangTal : arrayStrangar)
			summa += Integer.parseInt(ettStrangTal);
		
		System.out.println("Total summa är: " + summa);		
		System.out.println();
		System.out.println();
	}
	
	public static void uppgift16(String pNamnKaraktar, String pNamnFiende) 
	{
		CCritter minKaraktar = new CCritter(pNamnKaraktar);
		CCritter minMotstandare = new CCritter(pNamnFiende);
		System.out.println("Din karaktär");
		System.out.println(minKaraktar);
		System.out.println();
		System.out.println("Din motståendare");
		System.out.println(minMotstandare);
		System.out.println();
	}
}

//Tror det generella namnet är det...
class CCritter
{
	private String mNamn;
	private int mHalsa;
	private int mStyrka;
	private int mTur;
	
	public CCritter(String pNamn) 
	{
		mNamn = pNamn;
		//Slumpa fram tal, [1 - 100]		
		mHalsa = (int)(Math.random()*100) +1;
		mStyrka = (int)(Math.random()*100) +1;
		mTur = (int)(Math.random()*100) +1;
	}
	
	public int getHalsa() 
	{
		return mHalsa;
	}
	
	public int getStyrka() 
	{
		return mStyrka;
	}
	
	public int getTur() 
	{
		return mTur;
	}
	
	public String getNamn() 
	{
		return mNamn;
	}

	@Override
	public String toString() 
	{
		return "Namn:" + mNamn + ", Hälsa:" + mHalsa + ", Styrka:" + mStyrka + ", Tur:" + mTur;
	}
}

class CValFrame extends JFrame
{
	public static final int BREDD = 400;
	public static final int HOJD = 250;
	
	public CValFrame(String pTitle)
	{
		super(pTitle);
		setSize(CValFrame.BREDD, CValFrame.HOJD);
		setResizable(false);
		
		CPanelVal panelKomponenter = new CPanelVal();
		
		getContentPane().add(panelKomponenter);
	}
}

class CPanelVal extends JPanel
{

	public CPanelVal() 
	{
		setupKnappar();
	}
	
	
	
	private void setupKnappar() 
	{
		JButton mBtnUppgift1 = new JButton("Uppgift 1");
		add(mBtnUppgift1);
		mBtnUppgift1.addActionListener(event -> CPanelVal.exekveraUppgift01());		
		
		JButton mBtnUppgift2 = new JButton("Uppgift 2");
		add(mBtnUppgift2);
		mBtnUppgift2.addActionListener(event -> CPanelVal.exekveraUppgift02());
		
		JButton mBtnUppgift3 = new JButton("Uppgift 3");
		add(mBtnUppgift3);
		mBtnUppgift3.addActionListener(event -> CPanelVal.exekveraUppgift03());
		
		JButton mBtnUppgift4 = new JButton("Uppgift 4");
		add(mBtnUppgift4);
		mBtnUppgift4.addActionListener(event -> CPanelVal.exekveraUppgift04());
		
		JButton mBtnUppgift5 = new JButton("Uppgift 5");
		add(mBtnUppgift5);
		mBtnUppgift5.addActionListener(event -> CPanelVal.exekveraUppgift05());
		
		JButton mBtnUppgift6 = new JButton("Uppgift 6");
		add(mBtnUppgift6);
		mBtnUppgift6.addActionListener(event -> CPanelVal.exekveraUppgift06());
		
		JButton mBtnUppgift7 = new JButton("Uppgift 7");
		add(mBtnUppgift7);
		mBtnUppgift7.addActionListener(event -> CPanelVal.exekveraUppgift07());
		
		JButton mBtnUppgift8 = new JButton("Uppgift 8");
		add(mBtnUppgift8);
		mBtnUppgift8.addActionListener(event -> CPanelVal.exekveraUppgift08());
		
		JButton mBtnUppgift9 = new JButton("Uppgift 9");
		add(mBtnUppgift9);
		mBtnUppgift9.addActionListener(event -> CPanelVal.exekveraUppgift09());
		
		JButton mBtnUppgift10 = new JButton("Uppgift 10");
		add(mBtnUppgift10);
		mBtnUppgift10.addActionListener(event -> CPanelVal.exekveraUppgift10());
		
		JButton mBtnUppgift11 = new JButton("Uppgift 11");
		add(mBtnUppgift11);
		mBtnUppgift11.addActionListener(event -> CPanelVal.exekveraUppgift11());
		
		JButton mBtnUppgift12 = new JButton("Uppgift 12");
		add(mBtnUppgift12);
		mBtnUppgift12.addActionListener(event -> CPanelVal.exekveraUppgift12());
		
		JButton mBtnUppgift13 = new JButton("Uppgift 13");
		add(mBtnUppgift13);
		mBtnUppgift13.addActionListener(event -> CPanelVal.exekveraUppgift13());
		
		JButton mBtnUppgift14 = new JButton("Uppgift 14");
		add(mBtnUppgift14);
		mBtnUppgift14.addActionListener(event -> CPanelVal.exekveraUppgift14());
		
		JButton mBtnUppgift15 = new JButton("Uppgift 15");
		add(mBtnUppgift15);
		mBtnUppgift15.addActionListener(event -> CPanelVal.exekveraUppgift15());
		
		JButton mBtnUppgift16 = new JButton("Uppgift 16");
		add(mBtnUppgift16);
		mBtnUppgift16.addActionListener(event -> CPanelVal.exekveraUppgift16());
		
		JButton mBtnStang = new JButton("Stäng Fönster");
		add(mBtnStang);
		mBtnStang.addActionListener(event -> CPanelVal.exekveraStangFonster());
	}
	
	public static void exekveraUppgift01() 
	{
		CLexiconUppgifter.uppgift01();		
	}
	
	public static void exekveraUppgift02() 
	{
		String fornamn = JOptionPane.showInputDialog(null, "Ditt förnamn", "Uppgift 2 - Användare info", JOptionPane.PLAIN_MESSAGE);
		String efternamn = JOptionPane.showInputDialog(null, "Ditt efternman", "Uppgift 2 - Användare info", JOptionPane.PLAIN_MESSAGE);
		String alderStr = JOptionPane.showInputDialog(null, "Din ålder", "Uppgift 2 - Användare info", JOptionPane.PLAIN_MESSAGE);
												
		//Data validation [Simpel]
		if (
				(fornamn == null) || (fornamn.length() == 0) || (fornamn.isBlank()) || 
				(efternamn == null) || (efternamn.length() == 0) || efternamn.isBlank() ||  
				(alderStr == null) || (alderStr.length() == 0) || alderStr.isBlank()
			)
			System.out.println("Felaktig input från Dialogruta!");
		else 
		{
			int alder = Integer.parseInt(alderStr);
			if (alder > 0)
				CLexiconUppgifter.uppgift02(fornamn, efternamn, alder);
			else
				System.out.println("Felaktig input[Ålder]");
		}											
	}
	
	public static void exekveraUppgift03() 
	{
		CLexiconUppgifter.uppgift03();
	}
	
	public static void exekveraUppgift04() 
	{
		CLexiconUppgifter.uppgift04();
	}
	
	public static void exekveraUppgift05() 
	{		
		String tal1Str = JOptionPane.showInputDialog(null, "Första talet", "Uppgift 5 - beräkna största talet", JOptionPane.PLAIN_MESSAGE);
		String tal2Str = JOptionPane.showInputDialog(null, "Andra talet", "Uppgift 5 - beräkna största talet", JOptionPane.PLAIN_MESSAGE);
		
		//Data validation [Simpel]
		if (
				(tal1Str == null) || (tal1Str.length() == 0) || (tal1Str.isBlank()) || 
				(tal2Str == null) || (tal2Str.length() == 0) || tal2Str.isBlank()
					
			)
			System.out.println("Felaktig input från Dialogruta!");
		else 
		{
			int tal1 = Integer.parseInt(tal1Str);
			int tal2 = Integer.parseInt(tal2Str);
			CLexiconUppgifter.uppgift05(tal1, tal2);
		}														
	}
	
	public static void exekveraUppgift06() 
	{
		CLexiconUppgifter.uppgift06();
	}
	
	public static void exekveraUppgift07() 
	{
		String textAttSpara = JOptionPane.showInputDialog(null, "Text sträng att spara i " + CLexiconUppgifter.FILNAMN, "Uppgift 7 - Spara text till fil", JOptionPane.PLAIN_MESSAGE);
				
		//Data validation [Simpel]
		if ( (textAttSpara == null) || (textAttSpara.length() == 0) || textAttSpara.isBlank() )
			System.out.println("Felaktig input från Dialogruta!");
		else 
			CLexiconUppgifter.uppgift07(textAttSpara);								
	}
	
	public static void exekveraUppgift08() 
	{
		CLexiconUppgifter.uppgift08();
	}
	
	public static void exekveraUppgift09() 
	{
		String talStr = JOptionPane.showInputDialog(null, "Skriv in ett decimaltal t.ex 5.0", "Uppgift 9 - Matematiska operationer", JOptionPane.PLAIN_MESSAGE);
		
		//Data validation [Simpel]
		if ( (talStr == null) || (talStr.length() == 0) || talStr.isBlank() )
			System.out.println("Felaktig input från Dialogruta!");
		else 
			CLexiconUppgifter.uppgift09(Double.parseDouble(talStr));				
	}
	
	public static void exekveraUppgift10() 
	{
		CLexiconUppgifter.uppgift10();
	}
	
	public static void exekveraUppgift11() 
	{
		CLexiconUppgifter.uppgift11();
	}
	
	public static void exekveraUppgift12() 
	{
		String testaPalindrom = JOptionPane.showInputDialog(null, "Skriv in ett ord", "Uppgift 12 - Palindrom test", JOptionPane.PLAIN_MESSAGE);
		
		//Data validation [Simpel]
		if ( (testaPalindrom == null) || (testaPalindrom.length() == 0) || testaPalindrom.isBlank() )
			System.out.println("Felaktig input från Dialogruta!");
		else 
			CLexiconUppgifter.uppgift12(testaPalindrom);										
	}
	
	public static void exekveraUppgift13() 
	{
		String tal1Str = JOptionPane.showInputDialog(null, "Första talet", "Uppgift 13 - Printa intervallserie", JOptionPane.PLAIN_MESSAGE);
		String tal2Str = JOptionPane.showInputDialog(null, "Andra talet", "Uppgift 13 - Printa intervallserie", JOptionPane.PLAIN_MESSAGE);						
		
		//Data validation [Simpel]
		if (
				(tal1Str == null) || (tal1Str.length() == 0) || (tal1Str.isBlank()) || 
				(tal2Str == null) || (tal2Str.length() == 0) || tal2Str.isBlank()					
			)
			System.out.println("Felaktig input från Dialogruta!");
		else 
		{
			int tal1 = Integer.parseInt(tal1Str);
			int tal2 = Integer.parseInt(tal2Str);
			
			CLexiconUppgifter.uppgift13(tal1, tal2);
		}													
	}
	
	public static void exekveraUppgift14() 
	{		
		String commaTal = JOptionPane.showInputDialog(null, "Komma separerade talserie '34,1,6,78,24,33,12,9,69' utan ''", "Uppgift 14 - Separera udda/jämna tal", JOptionPane.PLAIN_MESSAGE);
		
		//Data validation [Simpel]
		if ( (commaTal == null) || (commaTal.length() == 0) || commaTal.isBlank() )
			System.out.println("Felaktig input från Dialogruta!");
		else 
			CLexiconUppgifter.uppgift14(commaTal);								
	}
	
	public static void exekveraUppgift15() 
	{		
		String commaTal = JOptionPane.showInputDialog(null, "Komma separerade talserie '34,1,6,78,24,33,12,9,69' utan ''", "Uppgift 15 - Addera tal och beräka summa", JOptionPane.PLAIN_MESSAGE);
		
		//Data validation [Simpel]
		if ( (commaTal == null) || (commaTal.length() == 0) || commaTal.isBlank() )
			System.out.println("Felaktig input från Dialogruta!");
		else 
			CLexiconUppgifter.uppgift15(commaTal);							
	}
	
	public static void exekveraUppgift16() 
	{		
		String namnDinKaraktar = JOptionPane.showInputDialog(null, "Namn på din karaktär", "Uppgift 16 - Superhjälte", JOptionPane.PLAIN_MESSAGE);
		String namnMotstandare = JOptionPane.showInputDialog(null, "Namn på din motståndare", "Uppgift 16 - Superskurk", JOptionPane.PLAIN_MESSAGE);
				
		//Data validation [Simpel]
		if (
				(namnDinKaraktar == null) || (namnDinKaraktar.length() == 0) || (namnDinKaraktar.isBlank()) || 
				(namnMotstandare == null) || (namnMotstandare.length() == 0) || namnMotstandare.isBlank()
			)
			System.out.println("Felaktig input från Dialogruta!");
		else 
			CLexiconUppgifter.uppgift16(namnDinKaraktar, namnMotstandare);						
	}
	
	public static void exekveraStangFonster() 
	{
		 System.exit(0);		
	}
}
