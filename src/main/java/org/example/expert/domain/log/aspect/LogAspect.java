package org.example.expert.domain.log.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.expert.domain.log.service.LogService;
import org.example.expert.domain.user.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
    private final LogService logService;

    @Around(value = "@annotation(ManagerRegisterLog) && args(userId, todoId, ..)")
    @Transactional
    public Object logManagerResister(ProceedingJoinPoint jointPoint, final long userId, final long todoId) throws Throwable {
        String name = jointPoint.getSignature().getName();
        logService.saveLog(userId, todoId, "BEFORE: " + name);

        Object returnValue;
        try {
            returnValue = jointPoint.proceed();
        } catch(Exception e) {
            logService.saveLog(userId, todoId, "AFTER FAIL: " + name + ", ERROR: " + e.toString());
            throw e;
        }

        logService.saveLog(userId, todoId, "AFTER SUCCESS: " + name);
        return returnValue;
    }
}
