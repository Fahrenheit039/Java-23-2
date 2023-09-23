import java.util.HashMap;
import java.util.Map;

public class Lab2_1 {

    public static void main(String[] args) {
        Lab2_1 obj = new Lab2_1();

        String s = obj.maxUniqueSubstring_1("abcdbefghab123456789ab");
        System.out.println(s);
    }
    public String maxUniqueSubstring_1(String s){
        int len = s.length();

        HashMap<Character, Integer> chars = new HashMap<Character, Integer>();

        String answer = "";
        int index_startSubStr = 0;
        chars.clear();
        for (int i = 0; i < len; i++)
        {
            if (chars.isEmpty())
            {
                index_startSubStr = i;
                chars.put(s.charAt(i), i);
            }
            else
            {
                char c = s.charAt(i);
                if (chars.containsKey(c))
                {
                    if (answer.length() < chars.size()) answer = s.substring(index_startSubStr, index_startSubStr + chars.size());

                    HashMap<Character, Integer> charsTemp = new HashMap<Character, Integer>(chars);
                    for ( HashMap.Entry<Character, Integer> item : charsTemp.entrySet() ) // перебор элементов
                    {
//                        char k = item.getKey();
//                        int v = item.getValue();
                        if (item.getValue() < charsTemp.get(c)) chars.remove(item.getKey());
                    }

                    index_startSubStr = chars.get(c) + 1;
                    chars.put(c, i);
                }
                else
                {
                    chars.put(s.charAt(i), i);
                }
            }
        }

        if (answer.length() < chars.size()) answer = s.substring(index_startSubStr, index_startSubStr + chars.size());

        return answer;
    }

}

