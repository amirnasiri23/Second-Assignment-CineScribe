import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        runMenu();
    }
    public static void runMenu() throws IOException {

        Scanner scn = new Scanner(System.in);
        String userChoice;
        String  userText;

        while (true) {
            System.out.println("------------------Welcome------------------");
            System.out.print("1) Movie - 2) Actor - 3) End: ");
            userChoice = scn.nextLine();

            switch (userChoice) {
                case "1":
                    String title;
                    String userOrder;
                    System.out.print("Enter The Movie Title: ");
                    title = scn.nextLine();
                    Movie userMovie = new Movie(title);
                    String movieData = userMovie.getMovieData(title);

                    while (true) {
                        System.out.println("Information Of " + title);
                        showMovieMenu();
                        System.out.print("Enter Your Order: ");
                        userOrder = scn.nextLine();

                        switch (userOrder) {
                            case "1":
                                int imdbVotes = userMovie.getImdbVotesViaApi(movieData);
                                System.out.println(imdbVotes);
                                break;
                            case "2":
                                String rating = userMovie.getRatingViaApi(movieData);
                                System.out.println(rating);
                                break;
                            case "3":
                                String year = userMovie.getYear(movieData);
                                System.out.println(year);
                                break;
                            case "4":
                                String language = userMovie.getLanguage(movieData);
                                System.out.println(language);
                                break;
                            case "5":
                                exit();
                                break;
                        }
                    }
                case "2":
                    String title2;
                    String userOrder2;
                    System.out.print("Enter The Actor Name: ");
                    title2 = scn.nextLine();
                    Actors userActor = new Actors(title2);
                    String actorData = userActor.getActorData(title2);

                    while (true) {
                        System.out.println("Information Of " + title2);
                        showActorsMenu();
                        System.out.print("Enter Your Order: ");
                        userOrder2 = scn.nextLine();

                        switch (userOrder2) {
                            case "1":
                                double netWorth = userActor.getNetWorthViaApi(actorData);
                                System.out.println(netWorth);
                                break;
                            case "2":
                                boolean isAlive = userActor.isAlive(actorData);
                                System.out.println(isAlive);
                                if (!isAlive) {
                                    String dateOfDeath = userActor.getDateOfDeathViaApi(actorData);
                                    System.out.println("Date Of Death: " + dateOfDeath);
                                }
                                break;
                            case "3":
                                String gender = userActor.getGender(actorData);
                                System.out.println(gender);
                                break;
                            case "4":
                                double height = userActor.getHeight(actorData);
                                System.out.println(height);
                                break;
                            case "5":
                                exit();
                                break;
                        }
                    }
                case "3":
                    exit();
                    break;
                default:
                    System.out.println("Order Was Not Valid Try Again!");
                    break;
            }
        }
    }
    public static void exit() {
        System.out.println("Program Ending.....");
        System.exit(0);
    }

    public static void showMovieMenu() {
        System.out.println("-------------------Options-------------------");
        System.out.println("1) ImdbVotes");
        System.out.println("2) Rating");
        System.out.println("3) Year");
        System.out.println("4) Language");
        System.out.println("5) Exit");
    }

    public static void showActorsMenu() {
        System.out.println("-------------------Options-------------------");
        System.out.println("1) netWorth");
        System.out.println("2) isAlive");
        System.out.println("3) gender");
        System.out.println("4) height");
        System.out.println("5) Exit");
    }
}