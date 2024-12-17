package com.nangman.slack.common.util;

import com.nangman.slack.common.exception.CustomException;
import com.nangman.slack.common.exception.ExceptionType;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SlackUserUtil {

    @Value("${slack.bot.token}")
    private String slackBotToken;

    private final MethodsClient methodsClient;

    @Cacheable(cacheNames = "slackName")
    public List<User> getSlackUserList(){
        try {
            return methodsClient.usersList(request-> request.token(slackBotToken))
                    .getMembers();
        } catch (IOException | SlackApiException e) {
            throw new CustomException(ExceptionType.API_CALL_FAILED);
        }
    }
}
