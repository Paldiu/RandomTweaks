package ns.jovial.randomtweaks.timer;

public class Timer {
    private final long ONE_SECOND;
    private final long ONE_MINUTE;
    private final long ONE_HOUR;
    private final long AUTO_ITEM_CLEAN;

    public Timer() {
        ONE_SECOND = 20L;
        ONE_MINUTE = ONE_SECOND * 60L;
        ONE_HOUR = ONE_MINUTE * 60L;
        AUTO_ITEM_CLEAN = ONE_MINUTE * 10L; // 10 Minutes
    }

    public Timer(long second, long minute, long hour) {
        ONE_SECOND = second;
        ONE_MINUTE = ONE_SECOND * minute;
        ONE_HOUR = ONE_MINUTE * hour;
        AUTO_ITEM_CLEAN = ONE_MINUTE * 10L; // 10 Minutes
    }

    public Long currentTime() {
        return System.currentTimeMillis();
    }

    public Long second() {
        return ONE_SECOND;
    }

    public Long minute() {
        return ONE_MINUTE;
    }

    public Long hour() {
        return ONE_HOUR;
    }

    public Long clean() {
        return AUTO_ITEM_CLEAN;
    }
}
