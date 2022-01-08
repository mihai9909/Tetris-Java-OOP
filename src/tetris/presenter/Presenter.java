package tetris.presenter;

import java.util.Calendar;
import java.util.Date;

public class Presenter {
    String nickname;
    private static Presenter presenter;
    private Presenter(){

    }

    public static Presenter getInstance(){
        if(presenter == null){
            presenter = new Presenter();
        }
        return presenter;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname(){
        return nickname;
    }

    public Date getCurrentDate(){
        return new java.sql.Date(Calendar.getInstance().getTime().getTime());
    }
}
