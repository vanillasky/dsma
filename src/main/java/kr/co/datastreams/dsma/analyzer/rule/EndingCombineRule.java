package kr.co.datastreams.dsma.analyzer.rule;

import kr.co.datastreams.dsma.model.Variant;

/**
 *
 * User: shkim
 * Date: 13. 7. 24
 * Time: 오후 5:14
 *
 */
public interface EndingCombineRule {
    public boolean canHandle();
    public Variant split();
}
