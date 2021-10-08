package it.unitn.external;

public class Reverse {
    public static void main(String[] argv) {
        try {
            StringBuilder sb = new StringBuilder(argv[0]);
            System.out.println(sb.reverse());
        } catch (ArrayIndexOutOfBoundsException aioofb) {
            System.out.println("You must insert a string!");
        }
    }
}
