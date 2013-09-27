package kr.co.datastreams.dsma.ma.model;

/**
 *
 * User: shkim
 * Date: 13. 7. 22
 * Time: 오후 12:57
 *
 */
public enum CharType {
    SPACE(0),
    HANGUL(1),
    HANJA(2),
    ALPHABET(3),
    NUMBER(4),
    SYMBOL(5),
    COMBINED(6),
    EMOTICON(7),
    ETC(8),
    FILTERED(100)
    ;

    private int value;
    private CharType(int i) {
        value = i;
    }
}
