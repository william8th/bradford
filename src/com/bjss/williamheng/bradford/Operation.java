package com.bjss.williamheng.bradford;

/**
 * Created by William Heng(dev) on 16/10/15.
 */
public enum Operation {
    OPERATION_HIGHEST_MAX_TEMP(0, DataEnum.MAX_TEMP),
    OPERATION_LOWEST_MAX_TEMP(1, DataEnum.MAX_TEMP),
    OPERATION_HIGHEST_MIN_TEMP(2, DataEnum.MIN_TEMP),
    OPERATION_LOWEST_MIN_TEMP(3, DataEnum.MIN_TEMP);

    private final int opType;
    private final DataEnum dataType;
    private Operation(final int op, final DataEnum dType) {
        this.opType = op;
        this.dataType = dType;
    }

    public DataEnum getDataType() {
        return this.dataType;
    }
}
