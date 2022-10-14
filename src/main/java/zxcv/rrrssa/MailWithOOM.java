package zxcv.rrrssa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static zxcv.rrrssa.Util.log;

public class MailWithOOM {

  private Map<Integer, List<String>> map = new HashMap<>();

  public MailWithOOM() {
    log.accept("Main ready");
  }

  public void send(long index, int userId, String message) {

    map.putIfAbsent(userId, new ArrayList<>());
    var list = map.get(userId);
    list.add(message + " :: " + list.stream().limit(10).toList().toString());
  }

}
