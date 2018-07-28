# CharMap


CharMap is simple Java library with API for transforming strings in char by char way.
It simplyfy code, where you neew to replace some chars in string into onother ones.

## Motivation

Real motivation for this API was class CE2Ascii from this library. It enables you 
to replace some special Slovak characters (like 'ô') into pure ascii characters (like
'o' in this case) 

I had some trouble with such characters in some cases where you need text used in more 
than one encoding. For example you need to create filename from user input which is 
used in solaris/windows and by several programs. Believe me you don't want to have 
letter 'ô' in such name. On other side you want to have that name readable. 

Then I found useful to provide implementation of the CE2Ascii class as set of 
general mapper classes to simplify such kind of tasks. 


## CharMapper

CharMapper class is base of all implementing classes. It implements only one method
for string transformation. 
```java
  String value = ...
  CharMapper anMapper = ...
  String newValue = anMapper.map(value);
```
The method creates new string from input one. During copying chars each char is check
if must be removed (isToBeRemoved() method) and if not it is mapped to another one (map()).

Subclasses implements isToBeRemoved(char) and map(char) methods to change behaviour of 
string mapping. 

## SequenceCharMapper

SequenceCharMapper implements char by char mapping by using two strings of same length. 
Chars are mapped by same position in strings. Also set of chars to be removed is defined 
by string. 
```java
  CharMapper anMapper = SequenceCharMapper.instance(".\\", "-/", ":;\n\r")
```
This mapper converts each '.' to '-' and '\\' to '/' and chars ':', ';', '\n', '\r' will be 
striped out.

## BTCharMapper

SequenceCharMapper must iterate whole char sequence to find whether char must be mapped or not. 
BTCharMapper is little modification of SequenceCharMapper. It requires that mapping fromChars 
sequence is ordered in binary tree form. In this way it is possible to iterate sequence faster. 
So if you have long mapped sequence it is better to use BTCharMapper. 

removeChars sequence is iterate sequentially (normally thera are only few chars to be explicitly 
removed.)

It is recomended to use BTCharMapper for long charmap sequences. And it is recomended to convert 
sequences to BT form in compile time. 

You can use method BTCharMapper.convertLinearToBT() to order mapping sequence in binary tree.

You can write an simple code for transforming chars stored in file (first two lines as fromChars 
and toChars) into new file with chars in binary tree order. (for simplicity it usess also utilities 
from jaul project)
```java
import java.util.List;
import sk.antons.charmap.BTCharMapper;
import sk.antons.jaul.Is;
import sk.antons.jaul.Split;
import sk.antons.jaul.binary.Unicode;
import sk.antons.jaul.util.TextFile;

public class AlphabetFile {
  
  private static void simpleFileEscape(String filename) {
    List<String> lines = Split.file(filename, "utf-8").byLinesToList();
    String fromLine = lines.get(0);
    String toLine = lines.get(1);
    String[] newLines = BTCharMapper.convertLinearToBT(fromLine, toLine, (char)0);
    StringBuilder sb = new StringBuilder();
    sb.append("    String fromChars = \"").append(Unicode.escapeJava(newLines[0])).append("\";"));
    sb.append('\n');
    sb.append("    String toChars = \"").append(Unicode.escapeJava(newLines[1])).append("\";"));
    TextFile.save(filename + ".escaped", "utf-8", sb.toString());
  }

  public static void main(String[] params) {
    simpleFileEscape("c:/tmp/_bordel/slovak.alphabet");
  }
}
```
If you want to create BTCharMapper from plain sequences in runtime you can use instanceFromNoBT() 
factory methods to create BTCharMapper instance. 


## MultipleCharMapper

If you already have some CharMappers you want to use them in sequence you can use it 
using MultipleCharMapper. The class allows you to combine implemented functionality 
for char mapping and removing but string is converted only once. 
```java
    CharMapper filenameMapper = MultipleCharMapper.instance(
      CE2Ascii.charMapper()
      , SequenceCharMapper.instance("\\/ ", "___", ";:&")
      , new CharMapper() {
          protected boolean isToBeRemoved(char c) { return (c < 32) || (c > 126); }
          protected char map(char c) { return c; }
      }
    );
```
This example combine 
 - CE2Ascii mapper.
 - Mapper mapping slash, backslash and space into underline and remoces some special chars.
 - Maper which keeps onlu printable ascii chars.

## CE2Ascii and EE2Ascii

CE2Ascii was main reason for this API. I need to transform some special 
characters from Slovak alphabet into pure ASCII chars. So text is readable 
and some third party libraries has no problems with such chars. 

There are many mappings for that alphabet and I also add some other characters 
to ensure clear text. So I decided to use BTCharMapper as internal implementation 
of mapping. 

As I found, that after 20 years I completely forget azbuka EE2Ascii is just try. 

CE2Ascii mapping
```
# slovak
from:ÁáÄäČčĎďÉéÍíĹĺĽľŇňÓóÔôŔŕŠšŤťÚúÝýŽž
  to:AaAaCcDdEeIiLlLlNnOoOoRrSsTtUuYyZz

# czech
from:ÁáČčĎďÉéĚěÍíŇňŘřŠšŤťÚúŮůÝýŽž
  to:AaCcDdEeEeIiNnRrSsTtUuUuYyZz

# polish
from:ĄąĆćĘęŁłŃńÓóŚśŹźŻż
  to:AaCcEeLlNnOoSsZzZz

# hungarian
from:ÁáÉéÍíÓóÖöŐőÚúÜüŰű
  to:AaEeIiOoOoOoUuUuUu

# german
from:ÄäÖöÜüß
  to:AaOoUuS

# svedish
from:ÅåÄäÖö
  to:AaAaOo

# norveg
from:ÆæØøÅå
  to:EeOoAa

# roman
from:ĂăÂâÎîȘșȚț
  to:AaAaIiSsTt

# sorbian
from:ČčĆćĚěŁłŃńÓóŘřŔŕŠšŚśŽžŹź
  to:CcCcEeLlNnOoRrRrSsSsZzZz

# turkish
from:ÇçĞğİıÖöŞşÜü
  to:CcGgIiOoSsUu

# ukranian
from:ĆćĎďŁłŃńŔŕŚśŤťŹźŻż
  to:CcDdLlNnRrSsTtZzZz
```

EE2Ascii mapping
```
# ukraine
from:АаЯяБбЦцЦцЧчХхДдДдЕеЄєЄєФфҐґГгІіЇїЙйКкЛлЛлМмНнНнОойоПпРрРрСсСсШшЩщТтТтУуЮюВвВвИиийЗзЗзЖж
  to:AaJjBbCcCcCcHhDdDdEeEeJjFfGgGgIiJjJjKkLlLlMmNnNnOoioPpRrRrSsSsSsSsTtTtUuJjVvWwYyYyZzZzZz

# russia
from:АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЭэЮюЯяІіѲѳѴѵЫы
  to:AaBbVvGgDdEeEeZzZzIiJjKkLlMmNnOoPpRrSsTtUuFfHhCcCcSsSsEeJjJjIiFfIiYy

# belarus
from:АаБбЦцЦцЧчДдДдзжЭэФфҐґГгХхІіЙйКкЛлМмНнОоПпРрСсШшТтУуЎўВвЫыЗзЖж
  to:AaBbCcCcCcDdDdzzEeFfGgHhHhIiJjKkLlMmNnOoPpRrSsSsTtUuUuVvYyZzZz

```

## Maven usage

```
   <dependency>
      <groupId>com.github.antonsjava</groupId>
      <artifactId>charmap</artifactId>
      <version>1.0</version>
   </dependency>
```



