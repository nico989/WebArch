package it.unitn.disi.webarch.tinyhttpd;

import java.io.IOException;
import java.net.ServerSocket;

public class TinyHttpdAss {
    public static void main(String argv[])
            throws IOException {
        int port = 8888;
        if (argv.length > 0) port = Integer.parseInt(argv[0]);
        ServerSocket ss = new ServerSocket(port);
        System.out.println("Server is ready");
        while (true)
            new TinyHttpdConnectionAss(ss.accept());
    }
}