package cn.org.rookie.mapper.utils;

public class JoinTableFlag {

    private static final ThreadLocal<Boolean> THREAD_LOCAL = new ThreadLocal<>();

    public static void on() {
        THREAD_LOCAL.set(true);
    }

    public static void off() {
        THREAD_LOCAL.set(false);
    }

    public static boolean getFlag() {
        return THREAD_LOCAL.get() == null || THREAD_LOCAL.get();
    }

}
