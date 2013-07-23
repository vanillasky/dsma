package kr.co.datastreams.dsma.util;

/**
 *
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오후 7:55
 *
 */
public class PhonemeIndexer {

    public static final int getChoIndex(char ch) {
        byte ret = -1;
        switch(ch) {
            case 12593:
                ret = 0;
                break;
            case 12594:
                ret = 1;
            case 12595:
            case 12597:
            case 12598:
            case 12602:
            case 12603:
            case 12604:
            case 12605:
            case 12606:
            case 12607:
            case 12608:
            case 12612:
            default:
                break;
            case 12596:
                ret = 2;
                break;
            case 12599:
                ret = 3;
                break;
            case 12600:
                ret = 4;
                break;
            case 12601:
                ret = 5;
                break;
            case 12609:
                ret = 6;
                break;
            case 12610:
                ret = 7;
                break;
            case 12611:
                ret = 8;
                break;
            case 12613:
                ret = 9;
                break;
            case 12614:
                ret = 10;
                break;
            case 12615:
                ret = 11;
                break;
            case 12616:
                ret = 12;
                break;
            case 12617:
                ret = 13;
                break;
            case 12618:
                ret = 14;
                break;
            case 12619:
                ret = 15;
                break;
            case 12620:
                ret = 16;
                break;
            case 12621:
                ret = 17;
                break;
            case 12622:
                ret = 18;
        }

        return ret;
    }




    public static final int getJungIndex(char ch) {
        byte ret = -1;
        switch(ch) {
            case 12623:
                ret = 0;
                break;
            case 12624:
                ret = 1;
                break;
            case 12625:
                ret = 2;
                break;
            case 12626:
                ret = 3;
                break;
            case 12627:
                ret = 4;
                break;
            case 12628:
                ret = 5;
                break;
            case 12629:
                ret = 6;
                break;
            case 12630:
                ret = 7;
                break;
            case 12631:
                ret = 8;
                break;
            case 12632:
                ret = 9;
                break;
            case 12633:
                ret = 10;
                break;
            case 12634:
                ret = 11;
                break;
            case 12635:
                ret = 12;
                break;
            case 12636:
                ret = 13;
                break;
            case 12637:
                ret = 14;
                break;
            case 12638:
                ret = 15;
                break;
            case 12639:
                ret = 16;
                break;
            case 12640:
                ret = 17;
                break;
            case 12641:
                ret = 18;
                break;
            case 12642:
                ret = 19;
                break;
            case 12643:
                ret = 20;
        }

        return ret;
    }

    public static final int getJongIndex(char ch) {
        byte ret = -1;
        switch(ch) {
            case 0:
                ret = 0;
                break;
            case 32:
                ret = 0;
                break;
            case 12593:
                ret = 1;
                break;
            case 12594:
                ret = 2;
                break;
            case 12595:
                ret = 3;
                break;
            case 12596:
                ret = 4;
                break;
            case 12597:
                ret = 5;
                break;
            case 12598:
                ret = 6;
                break;
            case 12599:
                ret = 7;
                break;
            case 12601:
                ret = 8;
                break;
            case 12602:
                ret = 9;
                break;
            case 12603:
                ret = 10;
                break;
            case 12604:
                ret = 11;
                break;
            case 12605:
                ret = 12;
                break;
            case 12606:
                ret = 13;
                break;
            case 12607:
                ret = 14;
                break;
            case 12608:
                ret = 15;
                break;
            case 12609:
                ret = 16;
                break;
            case 12610:
                ret = 17;
                break;
            case 12612:
                ret = 18;
                break;
            case 12613:
                ret = 19;
                break;
            case 12614:
                ret = 20;
                break;
            case 12615:
                ret = 21;
                break;
            case 12616:
                ret = 22;
                break;
            case 12618:
                ret = 23;
                break;
            case 12619:
                ret = 24;
                break;
            case 12620:
                ret = 25;
                break;
            case 12621:
                ret = 26;
                break;
            case 12622:
                ret = 27;
        }

        return ret;
    }
}
