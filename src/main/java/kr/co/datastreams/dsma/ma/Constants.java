package kr.co.datastreams.dsma.ma;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 22
 * Time: 오후 12:51
 * To change this template use File | Settings | File Templates.
 */
public class Constants {

    /**
     * Definition of sentence types and parts of speech
     */

    //*         CLASSIFICATION OF SENTENCE PATTERNS              */
    public static char SPTN_DECL = 'D';       //* declarative sentence */
    public static char SPTN_QUES =  'Q';      //* question sentence    */
    public static char SPTN_IMPR =  'I';       //* imperative sentence  */
    public static char SPTN_TITL =  'T';       //* title of a paragraph */



    /**----------------------  불규칙 변형 유형  ------------------------ */
    public static char IRR_TYPE_DI = 'd';  //* ㄷ 불규칙
    public static char IRR_TYPE_BI = 'b'; //* ㅂ 불규칙
    public static char IRR_TYPE_SI = 's'; //* ㅅ 불규칙
    public static char IRR_TYPE_HI = 'h'; //* ㅎ 불규칙
    public static char IRR_TYPE_RO = 'r'; //* 러 불규칙
    public static char IRR_TYPE_LO = 'l'; //* 르 불규칙
    public static char IRR_TYPE_OU = 'o'; // * 우 불규칙
    public static char IRR_TYPE_GU = 'g'; // *거라 불규칙
    public static char IRR_TYPE_NU = 'n'; // * 너라 불규칙
    public static char IRR_TYPE_YO = 'y'; // * 여 불규칙
    public static char IRR_TYPE_LI = 'L'; // * ㄹ 탈락
    public static char IRR_TYPE_UO = 'u'; //으 탈락
    public static char IRR_TYPE_AH = 'a'; // 아 탈락
    public static char IRR_TYPE_AE = 'e'; // 애 축약
    public static char IRR_TYPE_WA = 'w'; // 와 축약
    public static char IRR_TYPE_EI = 'e'; // 이 축약
    public static char IRR_TYPE_OE = 'O'; // 외 축약


    /**-----------------------  조사의 변이체 유형  ------------------------
     *
     *JOSA_VAR_WiAb	-- '은/는', '이/가', '을/를', '와/과', '아/야' 구분 정보
     *	'는/가/를/와/야'인 경우에 이 값이 set.
     *JOSA_VAR_Wz_tal	-- '로/으로' 구분 정보 (예) '학교로' --> '학교'+'으로'
     *	'으'가 탈락되어 복원된 경우에 이 값이 set.
     *JOSA_VAR_Wi_tal	-- 조사 '고'와 '이고', '라고'와 '이라고' 구분 정보
     *	'이'가 생략되어 복원한 경우에 이 값이 set.
     *JOSA_VAR_Wg_tal	-- 조사 '에서'의 '에' 생략 정보
     *	'에'가 생략되어 복원한 경우에 이 값이 set. '학교서' --> '학교'+'에서'
     *
     *JOSA_VAR_nameWi	-- 인명 뒤에 조음소 '이' 추가되는 경우
     *	'이'를 분석결과에서 삭제한 경우. '승식'+'(이)가'
     *	<참고> 이 경우는 항상 JOSA_VAR_WiAb 현상을 동반한다.
     *JOSA_VAR_preWi	-- 서술격 조사 '이' 앞에 조사가 오는 경우
     *	(예) '에서/부터/에서부터/대로' + '이' + '다'
     *JOSA_VAR_preWi2	-- 서술격 조사 '이' 앞에 조사 & '에' 탈락된 경우
     *	(예) '학교서다' --> '학교'+'(에)서'+'(이)'+'다'
     *
     *JOSA_VAR_Ag	-- '에게'의 변이체 '게'
     *JOSA_VAR_Bg	-- '에게'의 변이체 '께'
     *JOSA_VAR_hbDtg	-- '에게'의 변이체 '한테'
     *
     *$$$ 현재, '승식이한테'의 경우에 정보가 불충분한 점이 있음.
     *-------------------------  조사의 변이체 유형  ------------------------*/

    //	Values for 'jomi.josa'.
    public static int JOSA_VAR_WiAb	=1;
    public static int JOSA_VAR_Wz_tal	=2;
    public static int JOSA_VAR_Wi_tal	=3;
    public static int JOSA_VAR_Wg_tal	=4;

    public static int JOSA_VAR_nameWi	=5;
    public static int JOSA_VAR_preWi	=6;
    public static int JOSA_VAR_preWi2	=7;

    //Values for 'jomi.josaAgBg'.
    public static int JOSA_VAR_Ag	=1;
    public static int JOSA_VAR_Bg	=2;
    public static int JOSA_VAR_hbDtg	=3;

    /**---------------------  어말어미의 변이체 유형  ----------------------
     *
     *EOMI_VAR_Wb	-- '아'
     *EOMI_VAR_Wf	-- '어'
     *EOMI_VAR_Wj	-- '여' : '여/거/너/러/라'
     *EOMI_VAR_Wb_tal	-- '아' 탈락
     *EOMI_VAR_Wf_tal	-- '어' 탈락
     *EOMI_VAR_b	-- 'ㅏ'
     *EOMI_VAR_f	-- 'ㅓ'
     *EOMI_VAR_j	-- 'ㅕ'
     *EOMI_VAR_c	-- 'ㅐ'	---> '해서', '까매서/하얘서' 등 ㅎ-불규칙
     *EOMI_VAR_lc	-- 'ㅙ'	---> '되다'에만 적용
     *EOMI_VAR_If, Ib -- '러' 불규칙인 경우
     *
     *EOMI_VAR_Wz_tal	-- 종성 'ㄴ/ㄹ/ㅁ/ㅂ' 및 초성 'ㄹ/ㅁ'으로 시작되는 어미에서 '으' 탈락
     *	<주의> '나/냐/느'로 시작되는 어미들은 '으' 탈락으로 간주하지 않음
     *EOMI_VAR_Uz_tal	-- '습니다'에서 '스' 탈락
     *
     *EOMI_VAR_xv_Wf	-- 보조용언 앞에 오는 어미가 '아/어'
     *EOMI_VAR_xv_Al	-- 보조용언 앞에 오는 어미가 '고'
     *EOMI_VAR_xv_Ag	-- 보조용언 앞에 오는 어미가 '게'
     *
     *EOMI_VAR_Wi_tal	-- 무종성 용언 뒤에서 서술격 조사 '이' 생략
     *
     *$$$ '아/어'에 대한 변이체는 보조용언 앞에 오는 '아/어'에도 적용됨.
     *
     *-----------------------  어말어미의 변이체 유형  ----------------------*/

    //Values for 'jomi.eomi' or 'jomi.xomi'.
    public static int EOMI_VAR_Wb	=1;
    public static int EOMI_VAR_Wf	=2;
    public static int EOMI_VAR_Wj	=3;
    public static int EOMI_VAR_Wb_tal	=4;
    public static int EOMI_VAR_Wf_tal	=5;
    public static int EOMI_VAR_b	=6;
    public static int EOMI_VAR_f	=7;
    public static int EOMI_VAR_j	=8;
    public static int EOMI_VAR_c	=9;
    public static int EOMI_VAR_lc	=10;
    public static int EOMI_VAR_If	=11;
    public static int EOMI_VAR_Ib	=12;

    public static int EOMI_VAR_Wz_tal	=13;
    public static int EOMI_VAR_Uz_tal	=14;

    public static int EOMI_VAR_Wi_tal	=15;

    //Values for 'jomi.xomitype'.
    public static int EOMI_VAR_xv_Wf	=0; // 아/어
    public static int EOMI_VAR_xv_Al	=1; // 고
    public static int EOMI_VAR_xv_Ag	=2;  // 게
    public static int EOMI_VAR_xv_Xi	=11; // 지

    /**---------------------  선어말어미의 변이체 유형  ----------------------
     *
     *	선어말어미의 변이체 유형 --- 두 가지 정보를 표현
     *
     *		1. '시' 앞에 조음소 '으'가 오는 경우
     *		2. '았/었'의 변이체 정보
     *
     *	POMI_VAR_WbV	-- '았'
     *	POMI_VAR_WfV	-- '었'
     *	POMI_VAR_WjV	-- '였'
     *	POMI_VAR_V	-- 'ㅆ'
     *	POMI_VAR_bV	-- 'ㅏㅆ'
     *	POMI_VAR_fV	-- 'ㅓㅆ'
     *	POMI_VAR_jV	-- 'ㅕㅆ'
     *	POMI_VAR_cV	-- 'ㅐㅆ'	---> '했다'에만 적용됨
     *	POMI_VAR_lcV	-- 'ㅙㅆ'	---> '됐다'에만 적용됨
     *	POMI_VAR_IfV	-- '렀' ---> '러' 불규칙인 경우
     *	POMI_VAR_WzUi	-- '으시' & '었'
     *	POMI_VAR_WzUjV	-- '으시' & 'ㅕㅆ', 즉 '으셨'
     *
     *-----------------------  선어말어미의 변이체 유형  ----------------------*/

    //	Values for 'jomi.pomi'.
    public static int POMI_VAR_WbV	=1;
    public static int POMI_VAR_WfV	=2;
    public static int POMI_VAR_WjV	=3;
    public static int POMI_VAR_V	=4;
    public static int POMI_VAR_bV	=5;
    public static int POMI_VAR_fV	=6;
    public static int POMI_VAR_jV	=7;
    public static int POMI_VAR_cV	=8;
    public static int POMI_VAR_lcV	=9;
    public static int POMI_VAR_IfV	=10;
    public static int POMI_VAR_WzUi	=11;
    public static int POMI_VAR_WzUjV	=12;

    /**---------------------  조사/어미 외 기타 정보 ----------------------
     *
     *	RMA_RESULT --- the result is got from 'hangul.rma'
     *	GUESS_ABBR --- verb stem is guessed as abbr. 'ki/kg/Zi/...'
     *
     *	GUESS_CNOUN -- stem is guessed as noun + noun + ...
     *	GUESS_PNOUN	-- proper noun with Jongsong: articulative 'Wi' dropped.
     *
     *	GUESS_NPREF -- noun stem is guessed as prefix 'Gc/Ul' + noun
     *	GUESS_VPREF -- verb stem is guessed as prefix 'WbD/QlU' + verb
     *	GUESS_NVERB -- verb stem is guessed as noun + verb + ...
     *
     *-----------------------  조사/어미 외 기타 정보 ----------------------*/

    //	Values for 'jomi.zzz'.
    public static int RMA_RESULT	=1;
    public static int GUESS_ABBR	=2;

    public static int GUESS_CNOUN	=3;
    public static int GUESS_PNOUN	=4;

    public static int GUESS_NPREF	=5;
    public static int GUESS_VPREF	=6;
    public static int GUESS_NVERB	=7;
}
