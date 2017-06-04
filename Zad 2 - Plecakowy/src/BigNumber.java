import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BigNumber {
	
	private int[] liczba;
	private int dlugoscLiczby;
	
	public BigNumber(String cyfry)
	{
		this.dlugoscLiczby = cyfry.length();
		this.liczba = new int[this.dlugoscLiczby];
		
		for(int i = 0; i< this.dlugoscLiczby; i++ )
		{
			this.liczba[i] = Integer.parseInt(cyfry.substring(i, i+1));
		}
	}
	public BigNumber(int[] cyfry)
	{
		this.dlugoscLiczby = cyfry.length;
		this.liczba = cyfry;
	}
	
	public BigNumber dodaj(BigNumber a, BigNumber b)
	{
		/*List<Integer> wynik = new ArrayList<Integer>();
		do
		wynik.add(0,5);*/
		return null;
	
	}
	
	public String toString()
	{
		return Arrays.toString(this.liczba).replace("[", "").replace(", ", "").replace("]","");
	}

}
