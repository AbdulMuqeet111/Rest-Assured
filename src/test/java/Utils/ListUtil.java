package Utils;

import domain.User;

import java.util.List;

public class ListUtil{
    public Object findInList(String finder, List<User> objects) {

        for (User user : objects) {
            if ((user).getUsername().equals(finder)) {
                return user;
            }
        }
        return null;
    }
}
