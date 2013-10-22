package com.datastreams.nlp.ma.dic;

import com.datastreams.nlp.common.conf.Configuration;
import com.datastreams.nlp.ma.conf.ConfKeys;
import com.datastreams.nlp.ma.conf.Config;

/**
 * 조사 사전
 * User: shkim
 * Date: 13. 8. 21
 * Time: 오전 8:54
 *
 */
final class JosaDic extends MapBaseDic implements ConfKeys {

    private static final JosaDic instance = new JosaDic();

    private JosaDic() {
        Configuration conf = Config.get();
        load(conf.get(KOREAN_JOSA_DIC));
    }

    static JosaDic getInstance() {
        return instance;
    }

}
