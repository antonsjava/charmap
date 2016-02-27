/*
 * 
 */
package sk.antons.charmap;

import sk.antons.charmap.alphabet.CE2Ascii;

/**
 *
 * @author antons
 */
public class FilenameTesik {
 
    private static void filename() {
        String value = "& toto je ; jano \r\n Jano ide do prdele \u013e\u0161\u010d\u0165\u017e\u00fd\u00e1\u00ed\u00e9";

        CharMapper filenameMapper = MultipleCharMapper.instance(
            CE2Ascii.charMapper()
            , SequenceCharMapper.instance("\\/ ", "___", ";:&")
            , new CharMapper() {
                protected boolean isToBeRemoved(char c) {
                    if((c < 32) || (c > 126)) return true;
                    return false;
                }
                protected char map(char c) { return c; }
            }
        );

        System.out.println(" -- " + value);
        System.out.println(" -- " + filenameMapper.map(value));
    }
    
    public static void main(String[] params) {
        filename();
    }
}
