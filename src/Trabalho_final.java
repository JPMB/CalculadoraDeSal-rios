import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.io.PrintWriter;
import java.text.DecimalFormat;



public class Trabalho_final {

	public static final String  DATE_FORMAT = "yyyyMMddkkmmss";

	public static String date() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(cal.getTime());
	}


	public static void main( String[] args )
	{
		//Adicionar funcionarios 
		Funcionario[] funcionarios = new Funcionario[ 100 ];
		int num_funcionarios = 0;
		int pos;

		try	{

			Scanner scanner = new Scanner( new File("funcionarios.txt") );
			scanner.useDelimiter( "\\s*:\\s*|\\s*\n\\s*" );
			
			//Returns true if this scanner has another token in its input.
			for(pos = 0;  scanner.hasNext();  pos++ )
			{
				funcionarios[pos] = new Funcionario();
				funcionarios[pos].id          = scanner.nextInt();
				funcionarios[pos].nome        = scanner.next();
				funcionarios[pos].experiencia = scanner.nextInt();
				num_funcionarios++;
			}
			scanner.close();
		}
		catch( FileNotFoundException ex )
		{
			ex.printStackTrace();

		}

		//Adicionar entregas
		Entrega[] entregas = new Entrega [200];
		int posE;
		int num_entregas = 0;

		try	{
			Scanner scanner = new Scanner( new File("entregas.txt") );
			scanner.useDelimiter( "\\s*:\\s*|\\s*\n\\s*" );
			for (posE=0; scanner.hasNext(); posE++ ) {
				entregas[posE] = new Entrega();
				entregas[posE].identidade = scanner.nextInt();
				entregas[posE].ZE = scanner.next();
				entregas[posE].peso = scanner.nextInt();
				num_entregas++;
			}
			scanner.close();
		}
		catch( FileNotFoundException ex )
		{
			ex.printStackTrace();
		}

		try {
			PrintWriter pw = new PrintWriter(date() + ".txt");
			double RemuneracaoBase = 500.5;

			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			otherSymbols.setDecimalSeparator('.'); 
			DecimalFormat df = new DecimalFormat("0.000",otherSymbols);
			for( pos = 0;  pos < num_funcionarios;  pos++ ) {
				int zA = 0;
				int zB = 0;
				int zC = 0;
				int total = 0;
				double TA = 0;
				double Sliquido = 0;
				double Imposto = 0;
				double Ptotal = 0;
				double PA = 0;
				double PB = 0;
				double PC = 0;


				double SB = RemuneracaoBase + (funcionarios[pos].experiencia * 5);
				for( posE = 0;  posE < num_entregas;  posE++ ) {
					if (entregas[posE].identidade == funcionarios[pos].id) {
						total ++;
						Ptotal += entregas[posE].peso;
						if (entregas[posE].ZE.equals("A")) {
							zA++;
							PA += entregas[posE].peso;

						}
						else if (entregas[posE].ZE.equals("B")) {
							zB++;
							PB += entregas[posE].peso;
						}
						else if(entregas[posE].ZE.equals("C")) {
							zC++;
							PC += entregas[posE].peso;
						}
					}
				}
				double bonus = (zA * 0.5) + (zB * 0.75) + (zC * 1.5);
				double Sbruto = SB + bonus;
				
				if (Sbruto < 505) {
					TA = 1;
					Imposto = Sbruto * TA;
					Sliquido = Sbruto - Imposto;
				}
				else if (Sbruto >= 505 && Sbruto < 1000) {
					TA = 0.10;
					Imposto = Sbruto * TA;
					Sliquido = Sbruto - Imposto;
				}
				else if (Sbruto >= 1000 && Sbruto < 1500) {
					TA = 0.15;
					Imposto = Sbruto * TA;
					Sliquido = Sbruto - Imposto;
				}
				else if (Sbruto >= 1500) {
					TA = 0.20;
					Imposto = Sbruto * TA;
					Sliquido = Sbruto - Imposto;
				}


				//\r\n (CRLF) is the standard text file line break on DOS and Windows.

				pw.print( funcionarios[pos].nome + " | " + funcionarios[pos].id + " | " + funcionarios[pos].experiencia + "\r\n" + 
						total + " | " + zA + " | " + zB + " | " + zC + "\r\n" + 
						df.format(Ptotal/1000) + " | " + df.format(PA/1000) + " | " + df.format(PB/1000) + " | " + df.format(PC/1000) + "\r\n" +
						df.format(SB) + "\r\n" + 
						bonus + "\r\n" + 
						df.format(Sbruto) + "\r\n" + 
						(int) (TA * 100) + "%" + " | " + df.format(Imposto) + "\r\n" + 
						df.format(Sliquido) + "\r\n\r\n");


			}
			pw.close();
		}
		catch ( FileNotFoundException ex ){
			ex.printStackTrace();

		}

	}
}







