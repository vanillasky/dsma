package com.datastreams.nlp.ma.dic;

import com.datastreams.nlp.common.conf.Configuration;
import com.datastreams.nlp.ma.conf.ConfKeys;
import com.datastreams.nlp.ma.conf.Config;

/**
 * 접두사 사전
 * User: shkim
 * Date: 13. 10. 14
 * Time: 오후 3:55
 *
 */
final class PrefixDic extends MapBaseDic implements ConfKeys {

    private static final PrefixDic instance = new PrefixDic();

    private PrefixDic() {
        Configuration conf = Config.get();
        load(conf.get(KOREAN_PREFIX_DIC));
    }

    public static PrefixDic getInstance() {
        return instance;
    }
}
