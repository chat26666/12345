package com.privateperson.distributedlock.redis.service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LettuceService {

	private final StringRedisTemplate redisTemplate;
	private static final DefaultRedisScript<Long> script = new DefaultRedisScript<>(
		"if redis.call('get', KEYS[1]) == ARGV[1] then " +
			" return redis.call('del', KEYS[1]) else return 0 end",
		Long.class
	);


	public Optional<String> tryLock(String key) {

		String token = UUID.randomUUID().toString();

		Boolean ok = redisTemplate.opsForValue().setIfAbsent(key, token, Duration.ofSeconds(300));

		return Boolean.TRUE.equals(ok) ? Optional.of(token) : Optional.empty();
	}

	public boolean unLock(String key, String token) {

		Long result = redisTemplate.execute(script, List.of(key), token);

		return result.equals(1L);
	}
}
