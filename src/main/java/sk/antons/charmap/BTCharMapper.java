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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BTCharMapper is SequenceCharMapper with another logic for finding character 
 * mapping.
 * 
 * Instead of iterating fromChars sequence to find if character should be 
 * mapped it expects that fromChars sequence is stored in binary tree format. 
 * This predict is used for finding mapped char in sequence. 
 * 
 * This is useful if you have sequences longer than 10 chars. 
 * 
 * You can use helper method convertLinearToBT() to build binary tree formated 
 * sequences from simple sequences. As BT organized sequence can have 'empty' 
 * places it is necessary to define char which will be on that empty places. by 
 * default is is (char)0.
 * 
 * Implementation is thread safe and reentrant. So it is possible to create 
 * static singletons and use them for transformation.
 * 
 * @author antons
 */
public class BTCharMapper extends SequenceCharMapper {

    protected char noneChar = 0;

    /**
     * BTCharMapper constructor. Uses (char)0 as none char.
     * @param fromChars fromChars sequence in binary tree form.
     * @param toChars toChars corresponding to fromChars by it's possition.
     */
    public BTCharMapper(String fromChars, String toChars) {
        super(fromChars, toChars);
        this.noneChar = 0;
    }
    
    /**
     * BTCharMapper constructor.
     * @param fromChars fromChars sequence in binary tree form.
     * @param toChars toChars corresponding to fromChars by it's position.
     * @param noneChar char placed in fromChars and toChars in empty position.
     */
    public BTCharMapper(String fromChars, String toChars, char noneChar) {
        super(fromChars, toChars);
        this.noneChar = noneChar;
    }

    /**
     * Getter for noneChar attribute.
     * @return noneChar value
     */
    public char getNoneChar() {
        return noneChar;
    }


    private static void setItem(List<Character> list, int index, Character c) {
        while(list.size() <= index) list.add(null);
        Character cc = list.set(index, c);
        if(cc != null) throw new IllegalArgumentException("Conflict with character '"+c+"' and '"+cc+"'");
    }
    
    private static void convertLinearToBT(List<Character> linearChars, List<Character> btChars, int startIndex, int endIndex, int toIndex) {
        if(startIndex > endIndex) return;
        if(startIndex == endIndex) {
            Character c = linearChars.get(startIndex);
            setItem(btChars, toIndex, c);
            return;
        }

        int middleIndex = (startIndex + endIndex)/2;
        if((startIndex + endIndex) % 2 > 0) middleIndex++;

        Character c = linearChars.get(middleIndex);
        setItem(btChars, toIndex, c);
        convertLinearToBT(linearChars, btChars, startIndex, middleIndex - 1, toIndex*2 + 1);
        convertLinearToBT(linearChars, btChars, middleIndex + 1, endIndex, toIndex*2 + 2);
    }
    
    /**
     * Helper method for sorting linear fromChars/toIndex sequence into binary 
     * tree format.
     * 
     * fromChars and toChars should be of same length and fromChars should 
     * contain no duplicite chars and no noneChar.
     * 
     * @param fromChars input sequence
     * @param toChars input sequence
     * @param noneChar char to be placed on empty leaves of binary tree. 
     * @return array of two string first one is new fromChars in BT form and 
     *         second one is corresponding toChars sequence.
     */
    public static String[] convertLinearToBT(String fromChars, String toChars, char noneChar) {
        CharMapper  translator = new SequenceCharMapper(fromChars, toChars);
        List<Character> list = new ArrayList<Character>();
        for(int i = 0; i < fromChars.length(); i++) {
            char c = fromChars.charAt(i);
            list.add(c);
        }
        Collections.sort(list);
        for(int i = 0; i < (list.size() - 1); i++) {
            char c = list.get(i);
            char cc = list.get(i+1);
            if(c == cc) throw new IllegalArgumentException("Duplicite char '"+c+"' in fromChars.");
        }
        List<Character> newlist = new ArrayList<Character>();
        convertLinearToBT(list, newlist, 0, list.size()-1, 0);
        char[] source = new char[newlist.size()];
        for(int i = 0; i < source.length; i++) {
            Character c = newlist.get(i);
            if(c == null) source[i] = noneChar;
            else source[i] = c;
        }    

        String newFrom = new String(source);
        String newTo = translator.map(newFrom);
        return new String[] {newFrom, newTo};
    }

    
    /**
     * Finds position of input char in fromChars sequence using it's binary tree 
     * format.
     * @param c input char
     * @return index of input char in fromChars sequence of -1 if id is not there.
     */
    @Override
    protected int findFromIndex(char c) {
        int len = fromChars.length;
        int index = 0;
        while(index < len) {
            char cc = fromChars[index];
            if(c == cc) return index;
            if(c < cc) index = index*2+1;
            else index = index*2+2;
        }
        return -1;
    }

    
    /**
     * Factory method for class BTCharMapper. 
     * No removeChars  sequence is defined. 
     * @param fromChars from sequence in BT form
     * @param toChars to sequence
     * @return instance of BTCharMapper
     */
    public static BTCharMapper instance(String fromChars, String toChars) {
        return new BTCharMapper(fromChars, toChars);
    }

    /**
     * Factory method for class BTCharMapper. 
     * @param fromChars from sequence in BT form
     * @param toChars to sequence
     * @param removeChars remove sequence
     * @return instance of BTCharMapper
     */
    public static BTCharMapper instance(String fromChars, String toChars, String removeChars) {
        BTCharMapper mapper = new BTCharMapper(fromChars, toChars);
        mapper.setRemoveChars(removeChars);
        return mapper;
    }

    /**
     * Factory method for class BTCharMapper. 
     * No removeChars  sequence is defined. 
     * @param fromChars from sequence (any sequence)
     * @param toChars to sequence
     * @return instance of BTCharMapper
     */
    public static BTCharMapper instanceFromNoBT(String fromChars, String toChars) {
        String[] data = convertLinearToBT(fromChars, toChars, (char)0);
        BTCharMapper mapper = new BTCharMapper(data[0], data[1]);
        return new BTCharMapper(fromChars, toChars);
    }

    /**
     * Factory method for class BTCharMapper. 
     * @param fromChars from sequence in any form
     * @param toChars to sequence
     * @param removeChars remove sequence
     * @return instance of BTCharMapper
     */
    public static BTCharMapper instanceFromNoBT(String fromChars, String toChars, String removeChars) {
        String[] data = convertLinearToBT(fromChars, toChars, (char)0);
        BTCharMapper mapper = new BTCharMapper(data[0], data[1]);
        mapper.setRemoveChars(removeChars);
        return mapper;
    }

    
}
