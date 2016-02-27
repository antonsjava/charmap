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
 * Helper class for transforming special Eastern Europe azbuka letters into 
 * corresponding ASII char. (As I forget azbuka it is only nice try)
 * 
 * @author antons
 */
public class EE2Ascii {

    private static final String fromChars = "\u0432\u041d\u0446\u0413\u0427\u043c\u0456\u0407\u0418\u0422\u042e\u0437\u0441\u044d\u0474\u0404\u0411\u0416\u041b\u0420\u0425\u042b\u0430\u0435\u043a\u043f\u0444\u0449\u0451\u0472\u0490\u0401\u0406\u0410\u0412\u0415\u0417\u041a\u041c\u041f\u0421\u0424\u0426\u0429\u042d\u042f\u0431\u0434\u0436\u0439\u043b\u043e\u0440\u0443\u0445\u0448\u044b\u044f\u0454\u045e\u0473\u0475\u0491\t\u0000\u0000\u0000\u040e\u0000\u0000\u0000\u0414\u0000\u0000\u0000\u0419\u0000\u0000\u0000\u041e\u0000\u0000\u0000\u0423\u0000\u0000\u0000\u0428\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0433\u0000\u0000\u0000\u0438\u0000\u0000\u0000\u043d\u0000\u0000\u0000\u0442\u0000\u0000\u0000\u0447\u0000\u0000\u0000\u044e\u0000\u0000\u0000\u0457";
    private static final String toChars = "vNcGCmiJYTJzseIEBZLRHYaekpfseFGEIAVEZKMPSFCSEJbdzjloruhsyjeufig\t\u0000\u0000\u0000U\u0000\u0000\u0000D\u0000\u0000\u0000J\u0000\u0000\u0000O\u0000\u0000\u0000U\u0000\u0000\u0000S\u0000\u0000\u0000\u0000\u0000\u0000\u0000g\u0000\u0000\u0000y\u0000\u0000\u0000n\u0000\u0000\u0000t\u0000\u0000\u0000c\u0000\u0000\u0000j\u0000\u0000\u0000j";

    
    private static BTCharMapper mapper = new BTCharMapper(fromChars, toChars);

    static {
        mapper.setRemoveChars("\u044c\u042a\u042c\u0462\u0463");
    }
    
    /**
     * Maps string with Eastern Europe special letters into string with ASCII 
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
