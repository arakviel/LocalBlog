package com.arakviel.localblog.domain.contract;

import com.arakviel.localblog.domain.dto.UserAddDto;
import java.util.function.Supplier;

public interface SignUpService {

    void signUp(UserAddDto userAddDto, Supplier<String> waitForUserInput);
}
