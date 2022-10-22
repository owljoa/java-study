package repository;

import java.util.HashMap;
import java.util.Map;
import model.User;
import util.ThreadUtil;

public class UserRepository {

  private final Map<Long, User> userIdToUserMap = new HashMap<>();

  public User findUserById(long userId) {
    ThreadUtil.sleep(80);
    return userIdToUserMap.get(userId);
  }

  public void save(User user) {
    userIdToUserMap.put(user.getId(), user);
  }

}
