package com.jongtix.book.springboot.domain.user;

import java.util.List;

public interface UserRepositoryCustom {

    public List<User> findByName(String name);

    public List<User> findAllOrderBy(String sort);

    public List<User> findAllOrderByDesc(String sort);

}
