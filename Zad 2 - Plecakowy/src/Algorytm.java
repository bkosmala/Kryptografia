import java.util.Arrays;


public class Algorytm {
	
	private int[] jawnyBitowo = {};
	private long[] zaszyfrowany = {};
	private long[] odkodowane = {};
	
	private long[] kluczPrywatny;
	private long[] kluczPubliczny;
	private long M;
	private long W;
	private long W_1;
	
	private int dlugoscKlucza = 24;
	
	public String tekstJawnyNaBity(String tekst)
	{
		// zamiana tekstu jawnego na ci¹g bitów
		int [] wynik = new int[tekst.length()*16];
		for(int k=0;k<tekst.length();k++){
			char znak = tekst.charAt(k);
			//System.out.println((int)tekst.charAt(k));
			for(int i=0;i<16;i++){
				wynik[16*k+(15-i)]=znak%2;
				znak/=2;
			}	
			/*for(int i=0;i<16;i++){
				System.out.print(wynik[k*16+i]);
			}*/
			//System.out.println("");
		}
		this.jawnyBitowo = wynik;
		return this.getBitowoToString(this.jawnyBitowo);
	}
	
	private void generujKlucze()
	{
		this.kluczPrywatny = new long[this.dlugoscKlucza];
		this.kluczPubliczny = new long[this.dlugoscKlucza];
		
		long suma=0;
		// generowanie ci¹gu super-rosn¹cego
		int c = 3; //parametr do generownia ci¹gu
		
		System.out.println("");
		System.out.println("Klucz prywatny: wektor super-rosnacy");
		for(int i = 0; i<this.dlugoscKlucza;i++)
		{
			this.kluczPrywatny[i] = suma + (1 + (int)(Math.random()*c));
			suma += this.kluczPrywatny[i];
			System.out.print(kluczPrywatny[i] + " ");	
		}
		System.out.println("");	
		
		// szukam M takiego, ¿e M > sumy wyrazów wektora super rosn¹cego
		// oraz bêd¹cego liczb¹ nieparzyst¹, by potem ³atwiej by³o znalezc W takie, ¿e NWD(M,W) = 1
		this.M = suma;
		do{
			this.M = this.M + 1 ;	
		} while(Long.lowestOneBit(this.M) != 1);
		// mo¿na pozbyæ siê lowestOneBit - nie jest jednak potrzebna - jeœli m nie jest parzyste, to w czêsto tak
		System.out.println("M = " + this.M);
		
		// szukam W takiego, ¿e M/2 < W < M i NWD(M,W) = 1
		this.W = (long)(this.M/2)+ (1 + (int)(Math.random()*3));
		//this.W = (int)(this.M/2)+1;
		// wprowadzi³em pewn¹ dodatkow¹ losowoœæ do wyznaczania W poniewa¿ W zawsze mia³o postaæ dok³adnie tak¹ jak wy¿ej
		while(NWD(this.M,this.W) != 1) {this.W++;}
		System.out.println("W = " + this.W);
		System.out.println("");
		
		System.out.println("Klucz publiczny");
		for(int i = 0; i<this.dlugoscKlucza;i++)
		{
			this.kluczPubliczny[i] = (this.kluczPrywatny[i]*this.W)%this.M;
			System.out.print(kluczPubliczny[i] + " ");	
		}
		
		System.out.println("");
		
	}
	
	private long NWD(long a, long b)
	{
	    while(a != b) {
	        while(a>b) {
	            a = a-b;
	        }
	        while(b>a) {
	            b = b-a;
	        }
	    }
	    return a;	
	}
	
	private long[] zakoduj()
	{	
		generujKlucze();

		int dlugosc = (int) Math.ceil(((double)(this.jawnyBitowo.length ))/ this.dlugoscKlucza);
		this.zaszyfrowany = new long[dlugosc];
		
		System.out.println("");
		System.out.println("Zaszyfrowana wiadomoœæ :");
		for(int i = 0; i< dlugosc;i++)
		{
			//System.out.println(i);
			this.zaszyfrowany[i] = 0;
			for(int k = 0; k<this.dlugoscKlucza && (i*this.dlugoscKlucza)+k<this.jawnyBitowo.length; k++)
			{
				this.zaszyfrowany[i] += this.kluczPubliczny[k]*this.jawnyBitowo[(i*this.dlugoscKlucza)+k];			
			}
			System.out.print(this.zaszyfrowany[i] + " ");		
		}
		
		return this.zaszyfrowany;
	}
		
	public String getZakodowany()
	{
		this.zakoduj();	
		String tekst = "";	
		for(int i = 0; i < this.zaszyfrowany.length; i++)
		{
			tekst += String.valueOf(this.zaszyfrowany[i]) + " ";
		}
		return tekst;
	}
	
	private int[] odkoduj()
	{
		this.W_1 = odwrotnoscModulo(this.W,this.M);
		this.odkodowane = new long[this.zaszyfrowany.length];
		
		System.out.println("Odkodowywanie");
		for(int i = 0; i<this.zaszyfrowany.length;i++)
		{
			this.odkodowane[i] = (this.zaszyfrowany[i]*this.W_1)%this.M;
			System.out.print(this.odkodowane[i] + " ");
		}
		System.out.println("");
		
		int[] odkodowaneBitowo = new int[this.dlugoscKlucza*this.odkodowane.length];
		
		for(int k=0;k<this.odkodowane.length;k++){
			long liczba = this.odkodowane[k];
			//System.out.println(liczba);
			for(int i = 0; i<this.dlugoscKlucza ;i++){
				if(liczba>=this.kluczPrywatny[this.dlugoscKlucza-1-i])
				{
				odkodowaneBitowo[this.dlugoscKlucza*k+(this.dlugoscKlucza-1-i)]=1;
				liczba -= this.kluczPrywatny[this.dlugoscKlucza-1-i];
				}
				else
				{
				odkodowaneBitowo[this.dlugoscKlucza*k+(this.dlugoscKlucza-1-i)]=0;
				}
				System.out.print(odkodowaneBitowo[this.dlugoscKlucza*k+(this.dlugoscKlucza-1-i)]);
			}	
		}
		System.out.println("");
		
		return odkodowaneBitowo;
		
	}
	
	private long odwrotnoscModulo(long w2, long m2) {
		long u,w,x,z,q;

		  u = 1; w = w2;
		  x = 0; z = m2;
		  while(w!=0)
		  {
		    if(w < z)
		    {
		      q = u; u = x; x = q;
		      q = w; w = z; z = q;
		    }
		    q = w / z;
		    u -= q * x;
		    w -= q * z;
		  }
		  if(z == 1)
		  {
		    if(x < 0) x += m2;
		    System.out.println("");
		    System.out.println("Odwrotnosc (W^-1) to "+x+"\n");
		    System.out.println("");
		  }
		  else System.out.print("BRAK!");

		return x;
	}
	
	public String odkodowanyTekst()
	{
		int[] bitowo = odkoduj();
		String wynik = "";
		char znak = 0;
		int temp = 1;
		
		// zamiana bitów na znaki (16 bitów na znak)
		
		for(int i = 0; i<bitowo.length;i++ )
		{
			znak = 0;
			temp = 1;
			for(int j = 15; j>=0 && (i*16+j)<bitowo.length; j--)
			{
				System.out.print(bitowo[i*16+j]);
				if(bitowo[i*16+j]==1){ znak += temp;}
				temp *= 2;	
			}
			if(znak>0) wynik += String.valueOf(znak);
		}

		return wynik;		
	}
	
	
	public String getBitowoToString(int[] bitowo)
	{
		return Arrays.toString(bitowo).replace("[", "").replace(", ", "").replace("]","");
		
	}
	
	public void setDeszyfrowany(String t)
	{
		//System.out.println(t);
		String [] tab = t.split(" ");
		this.zaszyfrowany = new long[tab.length];
		for(int i = 0; i< tab.length;i++)
		{
			this.zaszyfrowany[i] = Long.valueOf(tab[i]);
		}
	}
}
