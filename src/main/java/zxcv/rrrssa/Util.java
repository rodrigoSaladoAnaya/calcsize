package zxcv.rrrssa;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Util {

  public static Instrumentation instrumentation;
  public static final Consumer<String> log = System.out::println;
  public static final Random random = new Random();
  public static final AtomicInteger count = new AtomicInteger(0);

  public static void metric(Object self, Method method, AtomicInteger count, int logGap, int calcLevel) throws ClassNotFoundException {
    if (count.incrementAndGet() % logGap == 0) {
      var clazzName = self.getClass().getName().split("\\$")[0];
      var clazz = Class.forName(clazzName);
      method.setAccessible(true);
      var size = CalculateSize.calc(clazz, self, calcLevel);
      var runtime = Runtime.getRuntime();
      var usedMemory = runtime.totalMemory() - runtime.freeMemory();
      var totalFreeMemory = runtime.maxMemory() - usedMemory;
      log.accept("(" + count + ") totalMemory: " + runtime.totalMemory() + ", maxMemory: " + runtime.maxMemory() + ", freeMemory: " + runtime.freeMemory() + ", totalFreeMemory: " + totalFreeMemory + ", usedMemory: " + usedMemory + "\n" +
        "    [" + clazzName + "] Size : " + size);
    }
  }
}
