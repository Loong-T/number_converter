package nerd_is.in.number_converter;

import java.util.ArrayList;

/**
 * Created by Nerd on 2014/4/27 0027.
 */
public class Utils {

    public static ArrayList<String> binary2AllTypes(String binaryString) {
        ArrayList<String> list = new ArrayList<String>();
        Long longBits = Long.parseLong(binaryString, 2);

        // Decimal (Double)
        Double d = Double.longBitsToDouble(longBits);
        list.add(d.toString());

        // Binary (not modified)
        list.add(binaryString);

        // Hex
        list.add(Long.toString(longBits, 16).toUpperCase());

        // Short
        list.add(String.valueOf(longBits.shortValue()));

        // Int
        list.add(String.valueOf(longBits.intValue()));

        // Float
        list.add(String.valueOf(d.floatValue()));

        // Double
        list.add(d.toString());

        return list;
    }

}
