package com.myTwitter.userservice.feign;

import org.springframework.cloud.openfeign.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static myTwitter.commons.constants.FeignConstants.TWEET_SERVICE;
import static myTwitter.commons.constants.PathConstants.*;

@FeignClient(value = TWEET_SERVICE, path = API_V1_TWEETS, configuration = FeignClientsConfiguration.class)
public interface TweetClient {


    @GetMapping(ID_TWEET_ID)
    Boolean isTweetExists(@PathVariable("tweetId") Long tweetId);


    @GetMapping(COUNT_TEXT)
    Long getTweetCountByText(@PathVariable("text") String text);

    default Long defaultTweetCount(Throwable throwable) {
        return 0L;
    }
}