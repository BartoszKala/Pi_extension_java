package Zadanie1_Edycja2010;
import java.io.*;
import java.util.*;
import java.util.regex.*;

// Zadanie 1- edycja 2010
public class Klasa {
    private double promien;
    private double wartoscX;
    private double wartoscY;
    private double promien2;
    private double wartoscX2;
    private double wartoscY2;
    private String plik;

    public Klasa() {
        this.plik = "dane1.txt";
        pobierz();
        zapisz();
    }

    public Klasa(String plik) {
        this.plik = plik;
        pobierz();
        zapisz();
    }

    private void pobierz() {
        List<Double> liczby = new ArrayList<>();
        String wzorzec = "-?([1-9]\\d*|([1-9]\\d*\\.\\d+|0\\.\\d+|0))";
        String wzorzec1 = wzorzec + "\\s";
        String wzor = wzorzec1 + wzorzec1 + wzorzec;
        Pattern pattern = Pattern.compile(wzor);

        try (BufferedReader reader = new BufferedReader(new FileReader(plik))) {
            String linia;
            if ((linia = reader.readLine()) == null) {
                System.out.println("Plik jest pusty.");
                System.exit(0);
            }


            String[] liczbyPierwszaLinia = linia.split("\\s");
            for (String liczbaStr : liczbyPierwszaLinia) {
                if (!liczbaStr.matches(wzorzec)) {
                    System.out.println("Błąd w zapisie w pliku.");
                    System.exit(0);
                }
                double liczba = Double.parseDouble(liczbaStr);
                liczby.add(liczba);
            }


                while ((linia = reader.readLine()) != null) {
                    Matcher matcher = pattern.matcher(linia);
                    if (matcher.matches()) {
                        String[] liczbyBezSpacji = linia.split("\\s");
                        for (String liczbaStr : liczbyBezSpacji) {
                            double liczba = Double.parseDouble(liczbaStr);
                            liczby.add(liczba);
                        }
                    } else {
                        System.out.println("Błąd w zapisie w pliku.");
                        System.exit(0);
                    }
                }


            if (liczby.size() == 6) {
                promien = liczby.get(0);
                promien2 = liczby.get(3);

                if(promien<=0||promien2<=0)
                {
                    System.out.println("Niepoprawna wartość promienia, musi być liczbą dodatnią");
                    System.exit(0);
                }

                wartoscX = liczby.get(1);
                wartoscY = liczby.get(2);
                wartoscX2 = liczby.get(4);
                wartoscY2 = liczby.get(5);
            } else {
                System.out.println("Błąd przy przypisywaniu wartości. Niepoprawna liczba elementów.");
                System.exit(0);
            }

        }
        catch(FileNotFoundException e) {
            System.out.printf("Nie odnaleziono pliku: %s", plik);
           // e.printStackTrace();
            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("Błąd podczas odczytu pliku.");
            //e.printStackTrace();
            System.exit(0);
        }
        catch (NumberFormatException e) {
            System.out.println("Błąd podczas przetwarzania liczby.");
            //e.printStackTrace();
            System.exit(0);
        }
    }

    public String oblicz() {
        double odleglosc = Math.sqrt(Math.pow(wartoscX2 - wartoscX, 2) + Math.pow(wartoscY2 - wartoscY, 2));
        double sumaPromieni = promien + promien2;
        double roznicaPromieni = Math.abs(promien-promien2);


        if (odleglosc== 0 && promien == promien2) {
            return "Okręgi są identyczne, mają nieskończenie wiele punktów wspólnych\n";
        }
        else if(odleglosc==sumaPromieni||roznicaPromieni==odleglosc)//1 pkt. wsp.
        {
            double a = (Math.pow(promien, 2) - Math.pow(promien2, 2) + Math.pow(odleglosc, 2)) / (2 * odleglosc);
            double h = Math.sqrt(Math.pow(promien, 2) - Math.pow(a, 2));

            double x = wartoscX + a * (wartoscX2 - wartoscX) / odleglosc + h * (wartoscY2 - wartoscY) / odleglosc;
            double y = wartoscY + a * (wartoscY2 - wartoscY) / odleglosc - h * (wartoscX2 - wartoscX) / odleglosc;


            return String.format("(%.2f; %.2f)\n", x, y);


        } else if (odleglosc<sumaPromieni && roznicaPromieni<odleglosc) {// 2 pkt. wsp.

            double a = (Math.pow(promien, 2) - Math.pow(promien2, 2) + Math.pow(odleglosc, 2)) / (2 * odleglosc);
            double h = Math.sqrt(Math.pow(promien, 2) - Math.pow(a, 2));

            double x = wartoscX + a * (wartoscX2 - wartoscX) / odleglosc + h * (wartoscY2 - wartoscY) / odleglosc;
            double y = wartoscY + a * (wartoscY2 - wartoscY) / odleglosc - h * (wartoscX2 - wartoscX) / odleglosc;

            double x2 = wartoscX + a * (wartoscX2 - wartoscX) / odleglosc - h * (wartoscY2 - wartoscY) / odleglosc;
            double y2 = wartoscY + a * (wartoscY2 - wartoscY) / odleglosc + h * (wartoscX2 - wartoscX) / odleglosc;

            return String.format("(%.2f; %.2f)   (%.2f; %.2f)\n", x, y,x2,y2);

        }
        else{// 0 pkt. wsp.
            return"Okręgi nie mają punktów wspólnych\n";
        }

    }

    public void zapisz() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("wynik1.txt"))) {
            writer.write(oblicz());

        } catch (FileNotFoundException e) {
            System.out.println("Nie można utworzyć pliku do zapisu: " + e.getMessage());
            // e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Błąd zapisu pliku: " + e.getMessage());
            //e.printStackTrace();
            System.exit(0);
        }
    }

}
