package com.privateperson.distributedlock.aop;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.privateperson.distributedlock.annotation.DistributedLock;
import com.privateperson.distributedlock.redis.service.LettuceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class DistributedLockAop {


	private final LettuceService lettuceService;
	private final ExpressionParser parser = new SpelExpressionParser();
	private static final String LOCK = "LOCK";

	@Around("@annotation(lockAnn)")
	public Object around(ProceedingJoinPoint pjp, DistributedLock lockAnn) throws Throwable {

		MethodSignature sig = (MethodSignature) pjp.getSignature();
		Method method = sig.getMethod();
		String[] paramNames = new DefaultParameterNameDiscoverer()
			.getParameterNames(method);
		Object[] args = pjp.getArgs();

		EvaluationContext ctx = new StandardEvaluationContext();
		for (int i = 0; i < args.length; i++) {
			ctx.setVariable(paramNames[i], args[i]);
		}

		String spel    = lockAnn.key();
		Long lockKey = parser
			.parseExpression(spel)
			.getValue(ctx, Long.class);

		String key = LOCK + lockKey;
		int retries        = lockAnn.retry();
		long retryDelayMs  = lockAnn.retryDelay() + ThreadLocalRandom.current().nextLong(0, 9000);

		String token = null;

		for (int attempt = 0; attempt < retries; attempt++) {
			Optional<String> maybe = lettuceService.tryLock(key);
			if (maybe.isPresent()) {
				token = maybe.get();
				break;
			}
			Thread.sleep(retryDelayMs);
		}


		if (token == null) {
			throw new RuntimeException(
				String.format("Distributed lock 획득 실패 (key=%s) after %d retries", key, retries)
			);
		}

		//System.out.println(key);
		//System.out.println(token);

		try {
			return pjp.proceed();
		} finally {
			boolean released = lettuceService.unLock(key, token);
			if (!released) {
				log.warn("Distributed lock 해제 실패 key={} token={}", key, token);
			}
		}

	}
}
