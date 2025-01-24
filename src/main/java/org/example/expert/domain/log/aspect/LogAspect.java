package org.example.expert.domain.log.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.expert.domain.log.service.LogService;
import org.example.expert.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
    private final LogService logService;

    @Around(value = "@annotation(ManagerRegisterLog) && args(authUser, todoId, ..)", argNames = "jointPoint,authUser,todoId")
    public Object logManagerResister(ProceedingJoinPoint jointPoint, User authUser, Long todoId) throws Throwable {
        Long userId = authUser.getId();
        logService.saveLog(userId, todoId, "BEFORE");

        Object returnValue = jointPoint.proceed();

        logService.saveLog(userId, todoId, "AFTER");

        return returnValue;
    }
}
