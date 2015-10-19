package com.bjss.williamheng.bradford;

/**
 * Created by William Heng(dev) on 16/10/15.
 */
public enum DataEnum {
    YEAR(0),
    MONTH(1),
    MAX_TEMP(2),
    MIN_TEMP(3),
    AF_DAYS(4),
    RAIN(5),
    SUN(6);

    private final int index;
    private DataEnum(int index) {
        this.index = index;
    }

    public final int getInt() {
        return this.index;
    }
}
