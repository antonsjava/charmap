 /*
 * Copyright 2016 Anton Straka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sk.antons.charmap.alphabet;

import sk.antons.charmap.BTCharMapper;
import sk.antons.charmap.CharMapper;


/**
 * Helper class for transforming special Central Europe latin letters into 
 * corresponding ASII char.
 * 
 * @author antons
 */
public class CE2Ascii {

    private static final String fromChars = "\u0119\u00e7\u015a\u00d6\u0102\u0142\u0171\u00c7\u00e1\u00f6\u010c\u0131\u0151\u0164\u017e\u00c4\u00ce\u00dc\u00e5\u00ee\u00fc\u0105\u010f\u011e\u013d\u0147\u0158\u015f\u016f\u017b\u021a\u00c2\u00c6\u00cd\u00d4\u00da\u00df\u00e4\u00e6\u00ed\u00f4\u00fa\u00fd\u0104\u0107\u010e\u0118\u011b\u0130\u013a\u0141\u0144\u0150\u0155\u0159\u015e\u0161\u016e\u0170\u017a\u017d\u0219\u021b\u00c1\u0000\u00c5\u0000\u00c9\u0000\u00d3\u0000\u00d8\u0000\u00dd\u0000\u00e2\u0000\u0000\u0000\u00e9\u0000\u00f3\u0000\u00f8\u0000\u0000\u0000\u0103\u0000\u0106\u0000\u010d\u0000\u0000\u0000\u011a\u0000\u011f\u0000\u0139\u0000\u013e\u0000\u0143\u0000\u0148\u0000\u0154\u0000\u0000\u0000\u015b\u0000\u0160\u0000\u0165\u0000\u0000\u0000\u0179\u0000\u017c\u0000\u0218";
    private static final String toChars = "ecSOAluCaoCioTzAIUaiuadGLNRsuZTAEIOUSaeiouyAcDEeIlLnOrrSsUUzZstA\u0000A\u0000E\u0000O\u0000O\u0000Y\u0000a\u0000\u0000\u0000e\u0000o\u0000o\u0000\u0000\u0000a\u0000C\u0000c\u0000\u0000\u0000E\u0000g\u0000L\u0000l\u0000N\u0000n\u0000R\u0000\u0000\u0000s\u0000S\u0000t\u0000\u0000\u0000Z\u0000z\u0000S";

    private static CharMapper mapper = new BTCharMapper(fromChars, toChars);

    /**
     * Maps string with Central Europe special letters into string with ASCII 
     * letters only.
     * @param value input string 
     * @return transformed string
     */
    public static String map(String value) {
       return mapper.map(value);
    }

    /**
     * Provides singleton instance of CharMapper used for transformation. Useful
     * if you want to combine this CharMapper with another one in MultipleCharMapper. 
     * @return singleton instance.
     */
    public static CharMapper charMapper() {
        return mapper;
    }
    
}
