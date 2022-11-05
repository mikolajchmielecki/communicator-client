import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        while(true) {
            System.out.println("Wybierz pozycję menu: \n1. Rejestracja\n2. Logowanie\n3. Wyślij wiadomość\n4. Wyświetl listę użytkowników\n5. Wyświetl listę konwersjacji\n6. Nowa konwersjacja\n7. Nowe wiadomości\n8. Wiadomości z konwersacji\n9. Koniec");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    break;
                case "7":
                    break;
                case "8":
                    break;
                case "9":
                    return;
            }
        }
    }
}
