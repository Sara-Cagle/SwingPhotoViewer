package constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author Sara Cagle
 * @Date 11/12/16
 */
public class Templates {


    public final String left = "^(Z+)(S*)(X+)$";
    public final String right = "^(X+)(S*)(Z+)$";
    public final String pigtail = "^(W+)$";
    public final String loop = "^(A*)(Z+)(S+)(X+)(D+)(E+)(W+)(Q+)(A+)$";
    public final String tag1 = "^(Z*)(X*)(S+)(Z*)(X*)$"; //STRAIGHTISH LINE DOWN
    public final String tag2 = "^(W+)$";//Straight up //"^(X*)(E*)(D+)(E*)(X*)(S+)(Z*)(X*)$"; //RIGHT TO LEFT LINE, THEN STRAIGHT DOWN
    public final String tag3 = "^(X*)(E*)(D+)(E*)(X*)(S+)(Z*)(X*)(W+)$";
    public final String tag4 = "^$";
    public final HashMap<String, String> gestures = new HashMap<>();

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
