package org.margostino.datafetcher;

public class RequestedIndicator {
    private String indicatorName;
    private String indicatorType;

    public RequestedIndicator(String indicatorName, String indicatorType) {
        this.indicatorName = indicatorName;
        this.indicatorType = indicatorType;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public String getIndicatorType() {
        return indicatorType;
    }

    @Override
    public String toString() {
        return "RequestedIndicator{" +
                "indicatorName='" + indicatorName + '\'' +
                ", indicatorType='" + indicatorType + '\'' +
                '}';
    }
}
