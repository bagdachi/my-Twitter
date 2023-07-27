package myTwitter.commons.dto.response.tweet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import myTwitter.commons.dto.response.user.CommonUserResponse;

@Data
public class TweetListResponse {
    private Long id;
    private String name;
    private String altWallpaper;
    private String wallpaper;
    private CommonUserResponse listOwner;
    private Long membersSize;
    @JsonProperty("isPrivate")
    private boolean isPrivate;
}
