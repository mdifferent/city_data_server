package com.bjucd.mobilesignal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by I015703 on 5/30/2019.
 */
public class PolygonUtils {

    static Logger logger = LoggerFactory.getLogger(PolygonUtils.class);

    public static List<Double[]> polygon2CoordList(String polygon) {
        if (polygon.startsWith("POLYGON")) {
            polygon = polygon.replace("POLYGON", "")
                    .replace("((", "")
                    .replace("))", "").trim();
            String[] polygonPairs = polygon.split(", ");
            List<Double[]> result = Arrays.stream(polygonPairs).map(pair -> pair.split(" "))
                    .map(strPair -> new Double[]{Double.parseDouble(strPair[0]), Double.parseDouble(strPair[1])}
                    )
                    .collect(Collectors.toList());
            return result;
        }
        return null;
    }
}
