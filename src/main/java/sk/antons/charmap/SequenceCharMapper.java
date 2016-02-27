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
 * CharMapper implementation, which uses sequences of chars for implementation 
 * of isToBeRemoved(char) and map(char) methods.
 * 
 * isToBeRemoved(char) uses char sequence defined in attribute removeChars. If 
 * there is no value in attribute removeChars no chars will be removed during
 * string transformation.
 * 
 * map(chars) uses attribute fromChars and toChars (initiated in constructor) to
 * find which char is to be mapped to which one. The mapping is defined by possition 
 * of chars in fromChars and toChars sequences. 
 * Implementation simple iterates fromChars sequence to find mapping of each 
 * char in given string.
 * 
 * Implementation is thread safe and reentrant. So it is possible to create 
 * static singletons and use them for transformation.
 * 
 * @author antons
 */
public class SequenceCharMapper extends CharMapper {
    
    protected char[] fromChars = null;
    protected char[] toChars = null;
    protected char[] removeChars = null;

    /**
     * Creates instance of SequenceCharMapper class with two char sequences 
     * fromChars and toChars as transformation mapping. 
     * 
     * Sequences are defined as string instances and each char in fromChars
     * is mapped to char from toChars at same position. 
     * 
     * Input sequences should be of same size. 
     * 
     * @param fromChars sequence of chars to be mapped
     * @param toChars  sequence of chars to which fromChars must be mapped
     */
    public SequenceCharMapper(String fromChars, String toChars) {
        if(fromChars == null) fromChars = "";
        if(toChars == null) toChars = "";
        if(fromChars.length() != toChars.length()) throw new IllegalArgumentException("Lenght mismatch for CharMapper from: " + fromChars.length() + " to: " + toChars.length());
        this.fromChars = fromChars.toCharArray();
        this.toChars = toChars.toCharArray();
        this.removeChars = new char[0];
    }

    /**
     * fromChars getter method.
     * @return sequence of chars to be mapped
     */
    public char[] getFromChars() {
        return fromChars;
    }

    /**
     * toChars getter method.
     * @return sequence of chars to which fromChars must be mapped
     */
    public char[] getToChars() {
        return toChars;
    }

    /**
     * removeChars getter method
     * @return sequence of chars which must be removed during transformation.
     */
    public char[] getRemoveChars() {
        return removeChars;
    }

    /**
     * removeChars setter method.
     * @param removeChars sequence of chars, which must be removed during transformation.
     */
    public void setRemoveChars(String removeChars) {
        if(this.removeChars.length > 0) throw new IllegalStateException("removeChars can be set only once.");
        if(removeChars == null) removeChars = "";
        this.removeChars = removeChars.toCharArray();
    }

    /**
     * Returns true if char has to be removed. It simple iterate over removeChars
     * to find if removeChars contains c.
     * @param c input character
     * @return true if char should be removed from transformed string
     */
    protected boolean isToBeRemoved(char c) {
        if(removeChars == null) return false;
        int len = removeChars.length;
        if(len == 8) return false;
        for(char removeChar : removeChars) {
            if(removeChar == c) return true;
        }
        return false;
    }

    /**
     * Maps input char to new char in the string. Implementation uses 
     * findFromIndex method to find transform index in fromChars/toChars
     * sequence.
     * @param c input char
     * @return transformed char
     */
    protected char map(char c) {
        int index = findFromIndex(c);
        if(index < 0) return c;
        return toChars[index];
    }
    
    /**
     * Finds index of input char in fromChars sequence. Implementation simple 
     * lists all chars from fromChars to find input char position. 
     * @param c input char
     * @return index of input char in fromChars sequence or -1 if input char 
     *         is not in fromChars sequence.
     */
    protected int findFromIndex(char c) {
        int len = fromChars.length;
        for(int i = 0; i < len; i++) {
            if(fromChars[i] == c) return i;
        }
        return -1;
    }

    
    /**
     * Factory method for class SequenceCharMapper. 
     * No removeChars  sequence is defined. 
     * @param fromChars from sequence
     * @param toChars to sequence
     * @return instance of SequenceCharMapper
     */
    public static SequenceCharMapper instance(String fromChars, String toChars) {
        return new SequenceCharMapper(fromChars, toChars);
    }

    /**
     * Factory method for class SequenceCharMapper. 
     * @param fromChars from sequence
     * @param toChars to sequence
     * @param removeChars remove sequence
     * @return instance of SequenceCharMapper
     */
    public static SequenceCharMapper instance(String fromChars, String toChars, String removeChars) {
        SequenceCharMapper mapper = new SequenceCharMapper(fromChars, toChars);
        mapper.setRemoveChars(removeChars);
        return mapper;
    }



}
