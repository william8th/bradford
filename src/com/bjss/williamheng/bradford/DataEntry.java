package com.bjss.williamheng.bradford;

import java.util.Arrays;

/**
 * Created by William Heng(dev) on 16/10/15.
 */
public class DataEntry {
    private static final String PATTERN = "[\\s\\*#]+";
    public String[] data;

    public DataEntry(final String row) {
        this.data = row.trim().split(PATTERN);
    }

    public Double get(final DataEnum enumIndex) {
        int index = enumIndex.getInt();
        if (index >= data.length) {
            return null;
        }

        String result = this.data[index];
        if (result.isEmpty() || "---".equals(result)) {
            return null;
        }

        Double data = Double.parseDouble(result);
        return data;

    }

    @Override
    public String toString() {
        return "DataEntry{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}