package com.dreamus.lolpark.purchase.service.impl;

import com.dreamus.lolpark.purchase.constants.MessageConstants;
import com.dreamus.lolpark.purchase.domain.User;
import com.dreamus.lolpark.purchase.repository.UserRepository;
import com.dreamus.lolpark.purchase.service.UserService;
import com.dreamus.lolpark.purchase.support.exception.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser(Integer id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundedException(MessageConstants.NO_USER_DATA));
    }
}
