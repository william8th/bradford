package com.bjss.williamheng.bradford.test;

import com.bjss.williamheng.bradford.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by William Heng(dev) on 15/10/15.
 */
public class DataAnalyserTest {
    private final static Path PATH = Paths.get("bradforddata.txt");

    private final static String HEADER_OK = "" +
            "Bradford\n" +
    "Location 4149E 4352N, 134 metres amsl\n" +
    "Estimated data is marked with a * after the value.\n" +
    "Missing data (more than 2 days missing in month) is marked by  ---.\n" +
    "Sunshine data taken from an automatic Kipp & Zonen sensor marked with a #, otherwise sunshine data taken from a Campbell Stokes recorder.\n" +
    "yyyy  mm   tmax    tmin      af    rain     sun\n" +
    "degC    degC    days      mm   hours\n";

    private final static String DATA_1 = "   1909  11    7.5     1.7      10     ---    60.8";
    private final static String DATA_2 = "   2010   3    9.3     2.3       9    81.9   127.1*";
    private final static String DATA_3 = "   2010   2    4.0    -0.8      17    55.8    44.7";
    private final static String DATA_4 = "   2013   5   14.7     6.4       0    55.8   162.8*  Provisional";



    private DataAnalyser dataAnalyser;

    @Before
    public void setUp() throws Exception {
        this.dataAnalyser = new BradfordDataAnalyser();
    }

    @Test
    public void testNullInputStreamExtraction() {
        assertNull(this.dataAnalyser.extractData(null));
        assertNull(this.dataAnalyser.extractData(null));
    }

    @Test
    public void testValidDataExtraction() {
        Stream<String> testData = this.concatenateToStream(HEADER_OK, DATA_1, DATA_2);
        Stream<DataEntry> data = this.dataAnalyser.extractData(testData);
        assertNotNull(data);
        assertTrue(data.count() == 2);
    }

    @Test
    public void testGetYearOfHighestMaxTemp() {
        Stream<String> testData = this.concatenateToStream(HEADER_OK, DATA_1, DATA_2, DATA_3);
        Stream<DataEntry> data = this.dataAnalyser.extractData(testData);
        DataEntry result = this.dataAnalyser.getResult(data, Operation.OPERATION_HIGHEST_MAX_TEMP);
        Double expectedYear = 2010.0D;
        Double actualYear = result.get(DataEnum.YEAR);
        assertNotNull(actualYear);
        assertEquals(expectedYear, actualYear);
    }

    @Test
    public void testGetMonthOfLowestMaxTemp() {
        Stream<String> testData = this.concatenateToStream(HEADER_OK, DATA_1, DATA_2, DATA_4);
        Stream<DataEntry> data = this.dataAnalyser.extractData(testData);
        DataEntry result = this.dataAnalyser.getResult(data, Operation.OPERATION_LOWEST_MAX_TEMP);
        Double expectedMonth = 11.0D;
        Double actualMonth = result.get(DataEnum.MONTH);
        assertNotNull(actualMonth);
        assertEquals(expectedMonth, actualMonth);
    }

    @Test
    public void testGetYearOfHighestMinTemp() {
        Stream<String> testData = this.concatenateToStream(HEADER_OK, DATA_1, DATA_3, DATA_4);
        Stream<DataEntry> data = this.dataAnalyser.extractData(testData);
        DataEntry result = this.dataAnalyser.getResult(data, Operation.OPERATION_HIGHEST_MIN_TEMP);
        Double expectedYear = 2013.0D;
        Double actualYear = result.get(DataEnum.YEAR);
        assertNotNull(actualYear);
        assertEquals(expectedYear, actualYear);
    }

    @Test
    public void testGetMonthOfLowestMinTemp() {
        Stream<String> testData = this.concatenateToStream(HEADER_OK, DATA_1, DATA_2, DATA_3, DATA_4);
        Stream<DataEntry> data = this.dataAnalyser.extractData(testData);
        DataEntry result = this.dataAnalyser.getResult(data, Operation.OPERATION_LOWEST_MIN_TEMP);
        Double expectedMonth = 2.0D;
        Double actualMonth = result.get(DataEnum.MONTH);
        assertNotNull(actualMonth);
        assertEquals(expectedMonth, actualMonth);
    }

    @Test
    public void testGetYearOfHighestMaxTempWithFile() {
        try (Stream<String> testData = Files.lines(PATH)) {
            Stream<DataEntry> data = this.dataAnalyser.extractData(testData);
            DataEntry result = this.dataAnalyser.getResult(data, Operation.OPERATION_HIGHEST_MAX_TEMP);
            Double expectedYear = 2006.0D;
            Double actualYear = result.get(DataEnum.YEAR);
            assertNotNull(actualYear);
            assertEquals(expectedYear, actualYear);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Test
    public void testGetMonthOfLowestMinTempWithFile() {
        try (Stream<String> testData = Files.lines(PATH)) {
            Stream<DataEntry> data = this.dataAnalyser.extractData(testData);
            DataEntry result = this.dataAnalyser.getResult(data, Operation.OPERATION_LOWEST_MIN_TEMP);
            Double expectedMonth = 1.0D;
            Double actualMonth = result.get(DataEnum.MONTH);
            assertNotNull(actualMonth);
            assertEquals(expectedMonth, actualMonth);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Stream<String> concatenateToStream(String header, String... data) {
        StringBuilder sb = new StringBuilder(header);
        for (String datum : data) {
            sb.append(datum + "\n");
        }
        Stream<String> fakeData = Arrays.stream(sb.toString().split("\n"));
        return fakeData;
    }
}