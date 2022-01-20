/*
 * 
 */
package sk.antons.charmap.alphabet;


/**
 *
 * @author antons
 */
public class SE2AsciiTestik {

    private static String speedTestik(String value) {
        //System.out.println("before: " + value);
        long start = System.currentTimeMillis();
        String newvalue = CE2Ascii.map(value);
        long end = System.currentTimeMillis();
        System.out.println(" translate speed " + (end - start));
        //System.out.println("after:  " + newvalue);
        return newvalue;
    }
    
    private static String speedTestikAny(String value) {
        //System.out.println("before: " + value);
        long start = System.currentTimeMillis();
        String newvalue = Any2Ascii.map(value);
        long end = System.currentTimeMillis();
        System.out.println(" translate speed " + (end - start));
        //System.out.println("after:  " + newvalue);
        return newvalue;
    }

    private static void speedTestik() {
        StringBuilder sb = new StringBuilder();
        char c = ' ';
        for(int i = 0; i < 80000; i++) {
            sb = sb.append(c++);
        }
        String value = sb.toString();
        //String value = "Jano ide do prdele \u013e\u0161\u010d\u0165\u017e\u00fd\u00e1\u00ed\u00e9";
        StringBuilder sb2 = new StringBuilder();
        for(int i = 0; i < 10; i++) {
            sb2.append(speedTestik(value).length());
        }
        System.out.println(sb2.toString());
    }
    
    private static void speedTestikAny() {
        StringBuilder sb = new StringBuilder();
        char c = ' ';
        for(int i = 0; i < 80000; i++) {
            sb = sb.append(c++);
        }
        String value = sb.toString();
        //String value = "Jano ide do prdele \u013e\u0161\u010d\u0165\u017e\u00fd\u00e1\u00ed\u00e9";
        StringBuilder sb2 = new StringBuilder();
        for(int i = 0; i < 10; i++) {
            sb2.append(speedTestikAny(value).length());
        }
        System.out.println(sb2.toString());
    }
    
    public static void main(String[] params) {
        speedTestikAny();
        speedTestik();
    }
    
}
