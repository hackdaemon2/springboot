package com.lace.util;

import com.lace.model.User;
import org.springframework.data.domain.Page;

/**
 *
 * @author hackdaemon
 */
public class UserPage {

  private final Page<User> user;

  public UserPage(Page<User> user) {
    this.user = user;
  }

  public int getPageIndex() {
    return user.getNumber() + 1;
  }

  public int getPageSize() {
    return user.getSize();
  }

  public boolean hasNext() {
    return user.hasNext();
  }

  public boolean hasPrevious() {
    return user.hasPrevious();
  }

  public int getTotalPages() {
    return user.getTotalPages();
  }

  public long getTotalElements() {
    return user.getTotalElements();
  }

  public Page<User> getUsers() {
    return user;
  }

  public boolean indexOutOfBounds() {
    return getPageIndex() < 0 || getPageIndex() > getTotalElements();
  }

}
