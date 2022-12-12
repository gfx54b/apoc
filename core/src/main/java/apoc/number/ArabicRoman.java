package apoc.number;


import org.neo4j.procedure.Description;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.UserFunction;

import static apoc.number.ArabicRoman.RomanNumerals.getRoman;
import static apoc.number.ArabicRoman.RomanNumerals.toArabic;

public class ArabicRoman {

    @UserFunction("apoc.number.romanToArabic")
    @Description("Converts the given Roman numbers to Arabic numbers.")
    public Number romanToArabic(final @Name("romanNumber") String number) {
        if (number == null || number.isEmpty()) return 0;
        return toArabic(number.toUpperCase());
    }

    @UserFunction("apoc.number.arabicToRoman")
    @Description("Converts the given Arabic numbers to Roman numbers.")
    public String arabicToRoman(final @Name("number") Object value) {
        Number number = validateNumberParam(value);
        if (number == null) return null;
        return getRoman(number.intValue());
    }

    private Number validateNumberParam(Object number) {
        return number instanceof Number ? (Number) number : null;
    }

    static class RomanNumerals {
        private static String roman[] = {"M","XM","CM","D","XD","CD","C","XC","L","XL","X","IX","V","IV","I"};

        public static String getRoman(int number) {
            int arab[] = {1000, 990, 900, 500, 490, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
            StringBuilder result = new StringBuilder();
            int i = 0;
            while (number > 0 || arab.length == (i - 1)) {
                while ((number - arab[i]) >= 0) {
                    number -= arab[i];
                    result.append(roman[i]);
                }
                i++;
            }
            return result.toString();
        }

        static int toArabic(String number) {
            if (number == null || number.isEmpty()) return 0;
            if (number.startsWith("M")) return 1000 + toArabic(number.substring(1));
            if (number.startsWith("CM")) return 900 + toArabic(number.substring(2));
            if (number.startsWith("D")) return 500 + toArabic(number.substring(1));
            if (number.startsWith("CD")) return 400 + toArabic(number.substring(2));
            if (number.startsWith("C")) return 100 + toArabic(number.substring(1));
            if (number.startsWith("XC")) return 90 + toArabic(number.substring(2));
            if (number.startsWith("L")) return 50 + toArabic(number.substring(1));
            if (number.startsWith("XL")) return 40 + toArabic(number.substring(2));
            if (number.startsWith("X")) return 10 + toArabic(number.substring(1));
            if (number.startsWith("IX")) return 9 + toArabic(number.substring(2));
            if (number.startsWith("V")) return 5 + toArabic(number.substring(1));
            if (number.startsWith("IV")) return 4 + toArabic(number.substring(2));
            if (number.startsWith("I")) return 1 + toArabic(number.substring(1));
            return 0;
        }
    }
}
