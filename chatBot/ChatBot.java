import java.util.Scanner;

public class ChatBot {

    public static void main(String[] args) {
        Config config = new Config("config.txt");
        Scanner scanner = new Scanner(System.in);

        if (config.botName == null || config.serverUrl == null) {
            System.out.println("Configuration is missing. Exiting.");
            scanner.close();
            return;
        }

        System.out.println("Hello! I am " + config.botName + ". How can I help you?");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Create new blog post");
            System.out.println("2. View all blog posts");
            System.out.println("3. View site statistics");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Enter post title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter post content: ");
                        String content = scanner.nextLine();

                        String json = "{\"title\":\"" + escapeJson(title) + "\","
                                + "\"content\":\"" + escapeJson(content) + "\","
                                + "\"author\":\"" + escapeJson(config.botName) + "\"}";

                        String postUrl = config.serverUrl + "?api=blogs";
                        String createResponse = HttpClient.sendJsonPost(postUrl, json);
                        System.out.println("Server response:\n" + createResponse);
                        break;

                    case "2":
                        String viewUrl = config.serverUrl + "?api=blogs";
                        String viewResponse = HttpClient.sendGet(viewUrl);
                        System.out.println("Blog posts:\n" + viewResponse);
                        break;

                    case "3":
                        String statsUrl = config.serverUrl + "?api=stats";
                        String statsResponse = HttpClient.sendGet(statsUrl);
                        System.out.println("Statistics:\n" + statsResponse);
                        break;

                    case "4":
                        System.out.println("Goodbye!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static String escapeJson(String str) {
        return str.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
