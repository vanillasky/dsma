package com.datastreams.nlp.ma.dic;

import com.datastreams.nlp.common.conf.Configuration;
import com.datastreams.nlp.ma.conf.ConfKeys;
import com.datastreams.nlp.ma.conf.Config;
import com.datastreams.nlp.ma.constants.PosTag;

import java.io.PrintWriter;
import java.util.Iterator;

/**
 * 어휘 형태소 사전
 * 명사, 부사, 감탄사, 관형사, 확장사전의 어휘를 수록한다.
 *
 * User: shkim
 * Date: 13. 7. 18
 * Time: 오후 4:44
 *
 */
final class FixedWordDic extends TrieBaseDic implements ConfKeys {


    private static final FixedWordDic INSTANCE = new FixedWordDic();

    private FixedWordDic() {
        super(new PosTagComposer());
        Configuration conf = Config.get();
        load(conf.get(KOREAN_DICTIONARY_FILE));
        load(conf.get(KOREAN_EXTENSION_DIC));
    }

    public static TrieBaseDic getInstnace() {
        return INSTANCE;
    }

}
