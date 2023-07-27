package myTwitter.commons.dto.response.tweet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetResponse<T> {
    private List<T> items;
    private HttpHeaders headers;
}