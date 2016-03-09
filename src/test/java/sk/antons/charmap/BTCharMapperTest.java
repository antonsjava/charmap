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
public class BTCharMapperTest {
	
    @Test
	public void map() throws Exception {
        String[] data = BTCharMapper.convertLinearToBT("abcd", "1234", (char)0);
        BTCharMapper mapper = BTCharMapper.instance(data[0], data[1], "ef");
        String value = mapper.map("abcdef");
        Assert.assertTrue("all", "1234".equals(value));
        value = mapper.map("rtyu");
        Assert.assertTrue("none", "rtyu".equals(value));
        value = mapper.map("abcdef abcdef");
        Assert.assertTrue("double", "1234 1234".equals(value));
        value = mapper.map(" abcdef abcdef ");
        Assert.assertTrue("middle", " 1234 1234 ".equals(value));
    }
    
    
}
