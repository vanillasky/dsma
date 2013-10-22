package com.datastreams.nlp.ma.dic;

import com.datastreams.nlp.common.conf.Configuration;
import com.datastreams.nlp.ma.conf.ConfKeys;
import com.datastreams.nlp.ma.conf.Config;

/**
 * 접미사 사전
 *
 * User: shkim
 * Date: 13. 10. 14
 * Time: 오후 4:05
 *
 */
final class SuffixDic extends MapBaseDic implements ConfKeys {

    private static final SuffixDic instance = new SuffixDic();

    private SuffixDic() {
        Configuration conf = Config.get();
        load(conf.get(KOREAN_SUFFIX_DIC));
    }


    public static SuffixDic getInstance() {
        return instance;
    }
}
