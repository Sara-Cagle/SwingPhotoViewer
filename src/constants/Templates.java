package constants;

import java.util.HashMap;

/**
 * Templates
 *
 * Definitions of different gesture templates.
 *
 * @Author Sara Cagle
 * @Date 11/12/16
 */
public class Templates {


    public final String left = "^[ASZ]+[XSD]+$"; //left carrot
    public final String right = "^[XSD]+[SAZ]+$"; //right carrot
    public final String pigtail = "^[QAZ]+[AZS]+[EDX]+[EWQ]+[WQA]+[QAZ]+[XSD]+$"; //same as loop, but then southeast
    public final String loop = "^[QAZ]+[AZS]+[EDX]+[EWQ]+[WQA]+[QAZ]+$";//starts in middle ish of top circle, goes counter clockwise
    public final String tag1 = "[SXD]+[EWD]+";//downward carrot, starting from left to right \/
    public final String tag2 = "[WED]+[SDX]+";//upward carrot, starting from left to right /\
    public final String tag3 = "[SXD]+[EWD]+[SXD]+";//zigzag up down, start up left, then down, then up, then down \/\
    public final String tag4 = "[EWD]+[SXD]+[EWD]+";//zigzag up down, start down left, then up, then down, then up /\/
    public final HashMap<String, String> gestures = new HashMap<>();

    /**
     * Templates constructor
     *
     * Assigns the templates to their names.
     */
    public Templates(){
        gestures.put(left, "left");
        gestures.put(right, "right");
        gestures.put(pigtail, "pigtail");
        gestures.put(loop, "loop");
        gestures.put(tag1, "tag1");
        gestures.put(tag2, "tag2");
        gestures.put(tag3, "tag3");
        gestures.put(tag4, "tag4");

    }
}
//North = W
//South = S
//West = A
//East = D
//Northwest = Q
//Northeast = E
//Southwest = Z
//Southeast = X
