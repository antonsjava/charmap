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
 * MultipleCharMapper combines functionality of more than one CharMapper into 
 * one transformation. In this way it is not necessary to iterate input string 
 * more than once. 
 * 
 * If you have more than one instance of CharMapper which you want to use 
 * MultipleCharMapper enable you to combine them. They are apply map(char)
 * method for each char in sequence asa they are provided.
 * 
 * Implementation is thread safe and reentrant. So it is possible to create 
 * static singletons and use them for transformation.
 * 
 * @author antons
 */
public class MultipleCharMapper extends CharMapper {
    
    protected CharMapper[] mappers = null;

    /**
     * Creates instance of MultipleCharMapper class. 
     * 
     * @param mappers list of CharMappers for whole char transformation
     */
    public MultipleCharMapper(CharMapper... mappers) {
        this.mappers = mappers;
    }

    /**
     * Maps input char by each mapper defined in this instance. Transformation 
     * is transient result of first transformation is input for second and so on.
     * @param c input char
     * @return transformed char
     */
    @Override
    protected char map(char c) {
        if(mappers == null) return c;
        for(CharMapper mapper : mappers) {
            c = mapper.map(c);
        }
        return c;
    }

    /**
     * Checks if char is to be removed during transformation by sequence of CharMappers. 
     * @param c input char
     * @return true if char has to be removed from string during transformation.
     */
    @Override
    protected boolean isToBeRemoved(char c) {
        if(mappers == null) return false;
        for(CharMapper mapper : mappers) {
            if(mapper.isToBeRemoved(c)) return true;
            c = mapper.map(c);
        }
        return false;
    }

    
    
    /**
     * Factory method for class MultipleCharMapper. 
     * @param mappers list of mappers
     * @return instance of MultipleCharMapper
     */
    public static MultipleCharMapper instance(CharMapper... mappers) {
        return new MultipleCharMapper(mappers);
    }
    
}
