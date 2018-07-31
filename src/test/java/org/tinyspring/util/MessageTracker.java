package org.tinyspring.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangyingqi
 * @date 2018/7/30
 */
public class MessageTracker {
    private static List<String> MESSAGES = new ArrayList<>();

    public static void addMsg(String msg){MESSAGES.add(msg);}

    public static void cleanMsg(){MESSAGES.clear();}

    public static List<String> getMsgs(){return MESSAGES;}
}
