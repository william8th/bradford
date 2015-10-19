package com.bjss.williamheng.bradford;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by William Heng(dev) on 15/10/15.
 */
public class BradfordDataAnalyser implements DataAnalyser {

    @Override
    public Stream<DataEntry> extractData(Stream<String> rawLines) {
        if (rawLines == null) {
            return null;
        }

        final Stream<DataEntry> dataLines = rawLines
                .skip(7)  // Assuming that the description is always 7 lines
                .map(x -> new DataEntry(x));

        return dataLines;
    }

    @Override
    public DataEntry getResult(Stream<DataEntry> dataStream, final Operation op) {
        dataStream = dataStream.filter(x -> x.get(op.getDataType()) != null);  // Filter out data entries that have values to compare
        Optional<DataEntry> result = null;

        switch (op) {
            case OPERATION_HIGHEST_MAX_TEMP:
            case OPERATION_HIGHEST_MIN_TEMP:
                result = dataStream
                        .reduce( (a, b) -> (a.get(op.getDataType()) > b.get(op.getDataType()) ? a : b) );
                break;

            case OPERATION_LOWEST_MAX_TEMP:
            case OPERATION_LOWEST_MIN_TEMP:
                result = dataStream
                        .reduce( (a, b) -> (a.get(op.getDataType()) < b.get(op.getDataType()) ? a : b) );
                break;

        }

        if (result.isPresent())
            return result.get();
        else
            return null;
    }
}
