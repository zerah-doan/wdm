package report.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public final class ResultSenderUtil {
    //region CONFIG
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String ELASTICSEARCH_URL = "http://localhost:9200";
    private static final String RESULT_INDEX = "/result";
    private static final String META_INDEX = "/meta";
    private static final String META_TYPE = "/current";
    //endregion

    //region INFO
    private static String release = "";
    private static String cycle = "";
    private static boolean isEnable;
    private static ObjectMapper mapper = new ObjectMapper();
    //endregion

    private ResultSenderUtil() {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new ISO8601DateFormat());
    }

    //region METHODS
    public static void setVersionInfo() {
        try {
            final String resObj = Unirest.get(ELASTICSEARCH_URL + META_INDEX + META_TYPE + "/_search?q=*").asJson().getBody().getObject().toString();
            release = JsonPath.read(resObj, "$.hits.hits[0]._source.release");
            cycle = JsonPath.read(resObj, "$.hits.hits[0]._source.cycle");
            isEnable = JsonPath.read(resObj, "$.hits.hits[0]._source.isEnable");
        } catch (Exception e) {
            release = "";
            cycle = "";
            isEnable = false;
        }
    }

    public static void sendTestResult(final TestResult result) {
        try {
            if (isEnable) {
                result.setVersion(release);
                result.setCycle(cycle);

                final String testId = isExistingTest(result);
                if ("".equals(testId)) {
                    createNewTestResult(result);
                } else {
                    updateExistingTestResult(testId, result);
                }
            }
        } catch (UnirestException | JsonProcessingException e) {
        }
    }
    //endregion

    private static String isExistingTest(final TestResult result) throws UnirestException {
        String testId = "";
        try {
            final String resObj = Unirest.get(ELASTICSEARCH_URL + RESULT_INDEX + "/" + release + "_" + cycle + "/_search?q=testName:" + result.getTestName()).asJson()
                    .getBody().getObject().toString();

            if (!"0".equals(JsonPath.read(resObj, "$.hits.total"))) {
                testId = JsonPath.read(resObj, "$.hits.hits[0]._id");
            }
        } catch (PathNotFoundException e) {
        }
        return testId;
    }

    private static void updateExistingTestResult(final String testId, final TestResult testStatus) throws JsonProcessingException, UnirestException {
        Unirest.post(ELASTICSEARCH_URL + RESULT_INDEX + "/" + release + "_" + cycle + "/" + testId + "/_update")
                .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                .body("{\"doc\" :" + mapper.writeValueAsString(testStatus) + "}").asJson();
    }

    private static void createNewTestResult(final TestResult testStatus) throws JsonProcessingException, UnirestException {
        Unirest.post(ELASTICSEARCH_URL + RESULT_INDEX + "/" + release + "_" + cycle)
                .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                .body(mapper.writeValueAsString(testStatus)).asJson();

    }
}
