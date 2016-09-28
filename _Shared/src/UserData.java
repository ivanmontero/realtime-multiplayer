import java.io.Serializable;

public class UserData implements Serializable {
    private static final long serialVersionUID = 3104041156703174123L;

    private int x, y;

    public UserData(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void updateData(UserData other){
        this.x = other.x;
        this.y = other.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static class UserDataBuilder {
        private int x, y;

        public UserDataBuilder(){}

        public UserDataBuilder x(int x){
            this.x = x;
            return this;
        }

        public UserDataBuilder y(int y){
            this.y = y;
            return this;
        }

        public UserData createUserData(){
            return new UserData(x, y);
        }
    }

}
