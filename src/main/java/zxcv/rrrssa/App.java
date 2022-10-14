package zxcv.rrrssa;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import static zxcv.rrrssa.Util.instrumentation;
import static zxcv.rrrssa.Util.random;

public class App {

  public static void main(String[] args) throws Throwable {
    var app = new App();
    app.installAgent();
    app.case1(app.getInstanceOf(MailWithOOM.class));
  }

  private void installAgent() {
    instrumentation = ByteBuddyAgent.install();
  }

  private <T> T getInstanceOf(Class<T> clazz) throws Throwable {
    var instance = new ByteBuddy()
      .subclass(clazz)
      .method(ElementMatchers.any())
      .intercept(MethodDelegation.to(Interceptor.class))
      .make()
      .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
      .getLoaded()
      .getConstructor()
      .newInstance();
    return instance;
  }

  private void case1(MailWithOOM mail) {
    for (var i = 0L; i < Integer.MAX_VALUE; i++) {
      var userId = random.nextInt(10);
      mail.send(i, userId, "Test " + 1);
    }
  }

}
