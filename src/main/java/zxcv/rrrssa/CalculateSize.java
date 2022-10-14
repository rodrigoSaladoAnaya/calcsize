package zxcv.rrrssa;

import java.lang.reflect.InaccessibleObjectException;
import java.util.List;
import java.util.Map;

import static zxcv.rrrssa.Util.instrumentation;
import static zxcv.rrrssa.Util.log;

public class CalculateSize {

  public static long calc(Class<?> clazz, Object obj, int level) {
    if (obj == null) {
      return 0;
    }
    if (level <= 0) {
      return 0;
    }
    long size = 0;
    try {
      if (obj instanceof Map<?, ?> map) {
        for (var entry : map.entrySet()) {
          size += calc(entry.getClass(), entry.getValue(), level - 1);
        }
      }
      if (obj instanceof List<?> list) {
        for (var item : list) {
          size += calc(item.getClass(), item, level - 1);
        }
      } else {
        var fields = clazz.getDeclaredFields();
        for (var filed : fields) {
          filed.setAccessible(true);
          var value = filed.get(obj);
          if (value != null) {
            size += instrumentation.getObjectSize(value) + calc(value.getClass(), value, level - 1);
          }
          //log.accept("-> [" + value.getClass() + "] " + filed.getName() + ": " + value); //DEBUG
        }
      }
    } catch (IllegalAccessException | InaccessibleObjectException ignore) {
      log.accept("ERR " + obj.getClass() + " -> " + ignore); //add new --add-opens
    }
    return size;
  }
}
