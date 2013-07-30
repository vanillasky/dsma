package kr.co.datastreams.dsma.ma.api;

import kr.co.datastreams.dsma.ma.model.Variant;

/**
 * 어미 처리기
 *
 * User: shkim
 * Date: 13. 7. 29
 * Time: 오후 3:52
 *
 */
public interface EndingSplitter {

    /**
     * 어미 분리 결과를 Variant 객체에 담아서 반환한다.<br/>
     * 필요한 경우 어간과 어미의 원형을 복원한다.<br/>
     *
     * @param stem - 어간부
     * @param ending - 어미부
     * @return Parsed stem and ending, Variant.EMPTY if ending not found
     */
    public Variant splitEnding(String stem, String ending);


    /**
     * 선어말 어미 분리 결과를 Variant 객체에 담아서 반환한다.<br/>
     * 선어말 어미는 어미를 분리한 후에 분리된 어간부를 대상으로 처리한다.<br/>
     *
     * 결합관계:<br/>
     * 으 -> 시 -> 었 -> 었 -> 겠
     * 으 -> 시 -> 였 -> 었 -> 겠
     * 으 -> 시 -> 았 -> 었 -> 겠
     * 으 -> 시 -> ㅆ -> 었 -> 겠
     * 으 -> 셨 -> 었 -> 겠
     *
     * @param word - 입력어절
     * @return Parsed stem and prefinal ending, Variant.EMPTY if ending not found
     */
    public Variant splitPrefinal(String word);
}
