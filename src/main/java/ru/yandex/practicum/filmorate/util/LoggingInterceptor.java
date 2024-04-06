package ru.yandex.practicum.filmorate.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) { // Проверяем, что метод контроллера
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(LogThis.class)) { // Среди найденных методов, ищем тот, над которым стоит наша аннотация `LogThis`
                logger.info("Before executing " + handlerMethod.getMethod().getName() + " method"); // Код, который должен быть выполнен перед входом в метод. Тут же можно пользоваться параметрами `request` и `response` для каких-то дополнительных целей
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(LogThis.class)) {
                logger.info("After executing " + handlerMethod.getMethod().getName() + " method"); // Код, который должен быть выполнен после выхода из метода. 
            }
        }
    }
}
