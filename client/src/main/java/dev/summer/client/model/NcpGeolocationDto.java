package dev.summer.client.model;

public class NcpGeolocationDto {
    // GeolocationData가 실제 데이터, NcpGeolocationDto는 전송되는 응답의 형태
    private Long returnCode;
    private String requestId;
    private GeolocationData geoLocation;

    public NcpGeolocationDto() {
    }

    public NcpGeolocationDto(Long returnCode, String requestId, GeolocationData geoLocation) {
        this.returnCode = returnCode;
        this.requestId = requestId;
        this.geoLocation = geoLocation;
    }

    public Long getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Long returnCode) {
        this.returnCode = returnCode;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public GeolocationData getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeolocationData geoLocation) {
        this.geoLocation = geoLocation;
    }

    @Override
    public String toString() {
        return "NcpGeolocationDto{" +
                "returnCode=" + returnCode +
                ", requestId='" + requestId + '\'' +
                ", geoLocation=" + geoLocation +
                '}';
    }
}
