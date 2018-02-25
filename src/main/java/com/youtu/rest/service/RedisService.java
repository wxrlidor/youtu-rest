package com.youtu.rest.service;

import com.youtu.common.pojo.YouTuResult;

public interface RedisService {

	YouTuResult syncContent(long contentCid);
}
