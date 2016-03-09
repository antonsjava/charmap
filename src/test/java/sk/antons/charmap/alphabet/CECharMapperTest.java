/*
 * Copyright 2015 Anton Straka
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

import static javax.management.Query.value;
import sk.antons.charmap.*;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author antons
 */
public class CECharMapperTest {
	
    @Test
	public void map() throws Exception {
        String value = "Aa\u00e1\u00e4bc\u010c\u010dd\u010fEe\u00e9fghi\u00edjkLl\u013a\u013emn\u0147\u0148o\u00f3\u00f4pQqr\u0155s\u0161t\u0165u\u00favwxy\u00fdz\u017d\u017e";
        String result = "AaaabcCcddEeefghiijkLlllmnNnooopQqrrssttuuvwxyyzZz";

        Assert.assertTrue("all", result.equals(CE2Ascii.map(value)));
    }
    
    
}
