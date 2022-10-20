package com.TFG.tempo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class ControllerLogger {
  public static void logMethod(String name, String methodName) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss - ");
    Date date = new Date(System.currentTimeMillis());
    Logger.getGlobal().info("[INFO Controller] ///\\\\\\ Tempo " + formatter.format(date) +
        name + " - " + methodName);
  }
}
