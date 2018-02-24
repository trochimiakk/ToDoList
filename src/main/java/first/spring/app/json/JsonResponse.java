package first.spring.app.json;

public class JsonResponse {

    private String response;

    public JsonResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
