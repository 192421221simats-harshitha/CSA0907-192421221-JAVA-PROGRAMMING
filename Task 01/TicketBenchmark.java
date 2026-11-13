
public class TicketBenchmark {

    static final int RUNS = 5;

    // Method 1: Using String
    static long usingString(int tickets, int fields) {
        long totalLength = 0;

        for (int i = 0; i < tickets; i++) {
            String ticket = "";

            for (int j = 1; j <= fields; j++) {
                ticket += "FIELD" + j + "=VALUE" + j + ";";
            }

            totalLength += ticket.length();
        }

        return totalLength;
    }

    // Method 2: Using StringBuilder
    static long usingStringBuilder(int tickets, int fields) {
        long totalLength = 0;

        for (int i = 0; i < tickets; i++) {
            StringBuilder ticket = new StringBuilder();

            for (int j = 1; j <= fields; j++) {
                ticket.append("FIELD")
                      .append(j)
                      .append("=VALUE")
                      .append(j)
                      .append(";");
            }

            totalLength += ticket.length();
        }

        return totalLength;
    }

    // Method 3: Using StringBuffer
    static long usingStringBuffer(int tickets, int fields) {
        long totalLength = 0;

        for (int i = 0; i < tickets; i++) {
            StringBuffer ticket = new StringBuffer();

            for (int j = 1; j <= fields; j++) {
                ticket.append("FIELD")
                      .append(j)
                      .append("=VALUE")
                      .append(j)
                      .append(";");
            }

            totalLength += ticket.length();
        }

        return totalLength;
    }

    interface Test {
        long execute();
    }

    static double measure(Test test) {
        // Warm-up
        test.execute();

        long totalTime = 0;

        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();
            test.execute();
            long end = System.nanoTime();

            totalTime += (end - start);
        }

        return totalTime / (RUNS * 1_000_000.0);
    }

    static void runWorkload(String name, int tickets, int fields) {

        System.out.println("\n" + name);
        System.out.println("Tickets: " + tickets);
        System.out.println("Fields per ticket: " + fields);

        double stringTime = measure(
            () -> usingString(tickets, fields));

        double builderTime = measure(
            () -> usingStringBuilder(tickets, fields));

        double bufferTime = measure(
            () -> usingStringBuffer(tickets, fields));

        System.out.printf("String: %.3f ms%n", stringTime);
        System.out.printf("StringBuilder: %.3f ms%n", builderTime);
        System.out.printf("StringBuffer: %.3f ms%n", bufferTime);

        System.out.println("Estimated intermediate Strings: "
            + (long) tickets * fields
            + " for repeated concatenation (approximate)");
    }

    public static void main(String[] args) {

        System.out.println("RAILWAY E-TICKET BENCHMARK");
        System.out.println("Average execution time of 5 runs");

        runWorkload("WORKLOAD 1 - SMALL", 1, 5);

        runWorkload("WORKLOAD 2 - MEDIUM", 1000, 20);

        runWorkload("WORKLOAD 3 - LARGE", 10000, 50);

        System.out.println("\nBenchmark completed.");
    }
}
