package com.bjss.williamheng.bradford;

import java.util.stream.Stream;

/**
 * Created by William Heng(dev) on 15/10/15.
 */
public interface DataAnalyser {
    public Stream<DataEntry> extractData(Stream<String> rawLines);
    public DataEntry getResult(Stream<DataEntry> dataStream, Operation op);
}
