package zxcv.rrrssa;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;

import static zxcv.rrrssa.Util.count;

public class Interceptor {

  @RuntimeType
  public static Object interceptWithReturn(@This Object self,
                                           @Super Object zuper,
                                           @Origin Method method,
                                           @AllArguments Object[] args,
                                           @SuperMethod Method superMethod) throws Throwable {
    Util.metric(self, method, count, 10_000, 4);
    return superMethod.invoke(self, args);
  }

}
