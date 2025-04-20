package de.uniwue.jpp.encoder;

import java.util.*;
import java.util.stream.Collectors;

public class FixedEncoder extends AbstractChainableEncoder {
    private HashMap<Character, Character> map;


    public FixedEncoder(Encoder delegate, HashMap<Character, Character> map) throws EncoderCreationException {
        super(delegate);

        if (map == null) throw new EncoderCreationException();
        for(Character e: map.keySet()){
            if(!listOfCharater().contains(e)|| !listOfCharater().contains(map.get(e)))
                throw new EncoderCreationException();
        }
        if (map.keySet().size() != 26) throw new EncoderCreationException();
        if (new HashSet<>(map.values()).size() != map.values().size()) throw new EncoderCreationException();
        this.map = map;

    }

    @Override
    public char encode(char c) throws EncoderInputException {
        if (!(c >= 'a' && c <= 'z')) throw new EncoderInputException();
        char charater = map.get(c);
        if(getDelegate()== null){
            return charater;

        }else{
            charater= getDelegate().encode(charater);
            for(Character c1: map.keySet()){
                if(charater==map.get(c1))return c1;
            }
        }
        return charater;
    }
    public HashMap<Character,Character> getMap(){
        return map;
    }

    @Override
    public void rotate(boolean carry) {
        this.getDelegate().rotate(carry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedEncoder that = (FixedEncoder) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(map);
    }

    @Override
    public String toString() {
        Map<Character,Character> sortierungCharater= new TreeMap<>(map);
        return String.format("%s", sortierungCharater.keySet().stream().map(e->e+"->"+sortierungCharater.get(e)).collect(Collectors.joining("\n")));
    }


    public List<Character> listOfCharater() {
        List<Character> alphabet = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.add(c);
        }
        return alphabet;
    }

// testing

}