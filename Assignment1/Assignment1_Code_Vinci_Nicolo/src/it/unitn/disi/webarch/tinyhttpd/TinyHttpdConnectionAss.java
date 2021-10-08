package it.unitn.disi.webarch.tinyhttpd;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import java.util.StringTokenizer;

class TinyHttpdConnectionAss extends Thread {

    private final StringBuilder outputFromReverse;
    private final Socket sock;
    private static final String token = "process";
    private static final String calledProg = "reverse";

    TinyHttpdConnectionAss(Socket s) {
        outputFromReverse = new StringBuilder();
        sock = s;
        setPriority(NORM_PRIORITY - 1);
        start();
    }

    @Override
    public void run() {
        System.out.println("=========");
        System.out.println("Connected on port " + sock.getPort());
        OutputStream output = null;
        try {
            // Initialize Input and Output stream
            BufferedReader d = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            output = sock.getOutputStream();
            PrintStream ps = new PrintStream(output);

            // Read incoming request
            String request = d.readLine();
            if (request == null) return;
            System.out.println("Request: " + request);

            // Read incoming headers
            String head;
            do {
                head = d.readLine();
                System.out.println("Header: " + head);
            } while (head != null && head.length() > 0);

            // Split request
            StringTokenizer st = new StringTokenizer(request, " /?");

            // Check token "process"
            boolean check = checkRequest(st);

            // Check program "reverse"
            String progToCall = returnProg(st);

            // Retrieve parameter
            String param = returnParam(st);

            if (check && Objects.nonNull(progToCall)) {
                // Format "reverse" into "Reverse"
                progToCall = progToCall.substring(0, 1).toUpperCase() + progToCall.substring(1);

                // Call "Reverse.Java" program
                callReverse(progToCall, param);

                // Initialize file readers
                FileInputStream fis1 = new FileInputStream("Documents/fragment1.html");
                FileInputStream fis2 = new FileInputStream("Documents/fragment2.html");

                int fileSize = fis1.available() + fis2.available();
                int responseLength = fileSize + outputFromReverse.length();

                // Write headers
                writeHeaders(ps, responseLength);

                // Initialize data for files
                byte[] data = new byte[fileSize];

                // Return upper html fragment
                fis1.read(data);
                output.write(data);
                fis1.close();

                // Return "Reverse" program output
                output.write(outputFromReverse.toString().getBytes(StandardCharsets.UTF_8));

                // Return lower html fragment
                fis2.read(data);
                output.write(data);
                fis2.close();
            } else {
                badRequest(ps, request);
            }
        } catch (IOException e) {
            System.out.println("Generic I/O error " + e);
        } finally {
            try {
                Objects.requireNonNull(output).close();
                sock.close();
            } catch (IOException ex) {
                System.out.println("I/O error on close" + ex);
            }
        }

    }

    private void callReverse(String prog, String param) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();

        // Build "Reverse" program path
        StringBuilder relativePath = new StringBuilder("src\\it\\unitn\\external\\");
        relativePath.append(prog);
        relativePath.append(".java");

        // Build command to launch "Reverse.java"
        StringBuilder cmd = new StringBuilder();
        cmd.append("java");
        cmd.append(" ");
        cmd.append(relativePath);
        cmd.append(" ");
        cmd.append(param);

        // Launch "Reverse.Java"
        processBuilder.command("cmd.exe", "/c", cmd.toString());

        // Initialize input stream
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // Read "Reverse.Java" output
        String line;
        while ((line = reader.readLine()) != null) {
            outputFromReverse.append(line).append("\n");
        }
    }

    private void writeHeaders(PrintStream ps, int responseLength) {
        ps.print("HTTP/1.1 200 OK\r\n");
        ps.print("Content-Length: " + responseLength + "\r\n");
        ps.print("Content-Type: text/html\r\n");
        ps.print("\r\n");
    }

    private void badRequest(PrintStream ps, String msg) {
        ps.print("HTTP/1.1 400 Bad Request\r\n\r\n");
        System.out.println("400 Bad Request: " + msg);
    }

    private boolean checkRequest(StringTokenizer st) {
        return (st.countTokens() >= 2) && st.nextToken().equals("GET") && st.nextToken().equals(token);
    }

    private String returnProg(StringTokenizer st) {
        String prog = st.nextToken();
        return prog.equals(calledProg) ? prog : null;
    }

    private String returnParam(StringTokenizer st) {
        String param = st.nextToken();
        return param.equals("HTTP") ? "" : param;
    }

}
