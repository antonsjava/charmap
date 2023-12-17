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
package sk.antons.charmap;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author antons
 */
public class CharMapperTest {
	
    @Test
	public void map() throws Exception {
        CharMapper mapper = new CharMapper() {

            @Override
            protected boolean isToBeRemoved(char c) { return false; }

            @Override
            protected char map(char c) {
                if(('a' <= c) && (c <= 'z')) return (char)(c - 'a' + 'A');
                return c;
            }
        };
        String value = mapper.map("abcdef");
        Assert.assertTrue("all", "ABCDEF".equals(value));
        value = mapper.map("12");
        Assert.assertTrue("none", "12".equals(value));
        value = mapper.map("abcdef abcdef");
        Assert.assertTrue("double", "ABCDEF ABCDEF".equals(value));
        value = mapper.map(" abcdef abcdef ");
        Assert.assertTrue("middle", " ABCDEF ABCDEF ".equals(value));
    }
    
    
}
