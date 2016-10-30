import java.io.Serializable;

public class UserData implements Serializable {
    private static final long serialVersionUID = 3104041156703174123L;

    private int x, y;
    private String message;

    public UserData(int x, int y){
        this(x, y, "");
    }

    public UserData(int x, int y, String message) {
        this.x = x;
        this.y = y;
        this.message = message;
    }

    public void clientUpdateData(UserData other){
        this.x = other.x;
        this.y = other.y;
        this.message = other.message;
    }

    public void serverUpdateData(UserData other){
        this.x = other.x;
        this.y = other.y;
        if(other.hasMessage()) {
            this.message = other.message;
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean hasMessage() {
        return !message.isEmpty();
    }

    public String getMessage() {
        return message;
    }

    public static class UserDataBuilder {
        private int x, y;
        private String message;

        public UserDataBuilder(){}

        public UserDataBuilder x(int x){
            this.x = x;
            return this;
        }

        public UserDataBuilder y(int y){
            this.y = y;
            return this;
        }

        public UserDataBuilder message(String message) {
            this.message = message;
            return this;
        }

        public UserData createUserData(){
            return new UserData(x, y, message);
        }
    }

}
