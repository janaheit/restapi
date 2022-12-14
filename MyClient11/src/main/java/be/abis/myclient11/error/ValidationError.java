package be.abis.myclient11.error;

public class ValidationError {

    private String name;
    private String reason;

    public ValidationError() {
    }

    public ValidationError(String name, String reason) {
        this();
        this.name = name;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ValidationError{" +
                "reason='" + reason + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
