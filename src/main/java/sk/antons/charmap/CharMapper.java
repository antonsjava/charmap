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

package sk.antons.charmap;

/**
 * Abstract class which defines functionality for string transformation in char 
 * by char manner. 
 * 
 * Method map(String) iterates input string char by char and build new string.
 * Each char is transformed by abstract method map(char). 
 * 
 * If method isToBeRemoved(char) returns true char will be skipped during 
 * transformation.
 * 
 * inherited classes overwrite isToBeRemoved(char) and map(char) methods to 
 * fulfill mapping
 * 
 * @author antons
 */
public abstract class CharMapper {
    
    /**
     * Converts input string by iterating it's chars and map them using map(char) method.
     * @param value input string
     * @return transformed string
     */
    public String map(String value) {
        if(value == null) return null;
        if(value.length() == 0) return value;
        StringBuilder sb = new StringBuilder(value.length());
        int len = value.length();
        for(int i = 0; i < len; i++) {
            char c = value.charAt(i);
            if(isToBeRemoved(c)) continue;
            sb.append(map(c));
        }
        return sb.toString();

    }    

    /**
     * Returns true if char has to be removed. 
     * @param c input character
     * @return true if char should be removed from transformed string
     */
    protected abstract boolean isToBeRemoved(char c);

    /**
     * Maps input char to new char in the string. 
     * @param c input char
     * @return transformed char
     */
    protected abstract char map(char c);
    
}
