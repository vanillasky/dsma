import kr.co.datastreams.dsma.dic.DictionaryReader;
import kr.co.datastreams.dsma.dic.FileDicReader;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 12
 * Time: 오후 5:58
 * To change this template use File | Settings | File Templates.
 */
public class EmoticonPatternTest {

    @Test
    public void testNegatives() throws Exception {
        DictionaryReader reader = new FileDicReader("dic/emoticon_patterns.dic");
        String line = reader.readLine();
        System.out.println(line);
        //String exp = .get("emoticon.negative");

        String exp = "[ㅜㅠㅡ-][_.]?[ㅜㅡㅠ-]{1,}[;:]*";
        String[] exprs = {"ㅜ.ㅜ", "ㅜㅜ", "ㅜ_ㅜ", "ㅜㅜㅜㅜ", "ㅠㅠㅠㅠ", "ㅜㅠ", "ㅠㅠ", "-_-", "-_-;;", " ㅜ_ㅠ"};
        Pattern pattern = Pattern.compile(exp);
        for (String each : exprs) {
            Matcher m = pattern.matcher(each);
            assertTrue(m.find());
            System.out.println(m.group());

        }

    }
}
