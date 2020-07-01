package io.spin.status.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class SortUtil {

    public List sortListAsc(List list, Method method) {
        Collections.sort(list, (pre, post) -> {
            try {
                if (method.invoke(pre) == null) return (method.invoke(post) == null) ? 0 : -1;
                if (method.invoke(post) == null) return 1;
                return String.valueOf(method.invoke(pre)).compareTo(
                        String.valueOf(method.invoke(post))
                );
            } catch (IllegalAccessException | InvocationTargetException e) {
                return 0;
            }
        });
        return list;
    }

    public List sortListDesc(List list, Method method) { //최신순
        Collections.reverse(sortListAsc(list, method));
        return list;
    }

}
