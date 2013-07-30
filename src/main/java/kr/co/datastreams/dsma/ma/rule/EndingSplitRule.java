package kr.co.datastreams.dsma.ma.rule;

import kr.co.datastreams.dsma.ma.model.Variant;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 24
 * Time: 오후 5:14
 * To change this template use File | Settings | File Templates.
 */
public interface EndingSplitRule {
    public boolean canHandle();
    public Variant split();
}
