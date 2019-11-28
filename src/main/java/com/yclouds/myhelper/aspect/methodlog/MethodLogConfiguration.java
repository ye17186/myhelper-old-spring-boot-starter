package com.yclouds.myhelper.aspect.methodlog;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.common.collect.Maps;
import com.yclouds.myhelper.aspect.MethodLogPoint;
import com.yclouds.myhelper.aspect.methodlog.model.MethodLogModel;
import com.yclouds.myhelper.aspect.methodlog.model.RequestLogModel;
import com.yclouds.myhelper.constants.RequestConsts;
import com.yclouds.myhelper.utils.DateUtils;
import com.yclouds.myhelper.utils.IdGenUtils;
import com.yclouds.myhelper.utils.JsonUtils;
import com.yclouds.myhelper.utils.RequestUtils;
import com.yclouds.myhelper.utils.StringPool;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.util.CollectionUtils;

/**
 * @author ye17186
 * @version 2019/7/1 10:21
 */
@Aspect
public class MethodLogConfiguration extends AbstractMethodLogConfiguration {

    private static final String HIDDEN = "*****";

    /**
     * 方法进入时间
     */
    private ThreadLocal<Long> entryTime = new ThreadLocal<>();

    /**
     * 进入到方法前，记录下进入时间
     */
    @Before(value = "@annotation(point)")
    public void before(MethodLogPoint point) {
        entryTime.set(System.currentTimeMillis());
    }

    @AfterReturning(value = "@annotation(point)", returning = "ret")
    public void afterReturning(JoinPoint joinPoint, MethodLogPoint point, Object ret) {

        configurer.afterReturn(buildLog(joinPoint, point, ret, null));
        entryTime.remove();
    }

    @AfterThrowing(value = "@annotation(point)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, MethodLogPoint point, Throwable ex) {
        configurer.afterThrow(buildLog(joinPoint, point, null, ex));
        entryTime.remove();
    }

    private MethodLogModel buildLog(JoinPoint joinPoint, MethodLogPoint point, Object ret,
        Throwable ex) {

        // 需要记录发起Http请求信息
        if (point.includeHttpRequest()) {

            HttpServletRequest request = RequestUtils.getRequest();

            RequestLogModel requestLogModel = new RequestLogModel();
            if (request != null) {
                requestLogModel
                    .setRequestId((String) request.getAttribute(RequestConsts.REQUEST_ID));
                requestLogModel.setRequestUrl(request.getRequestURL().toString());
                requestLogModel.setRequestMethod(request.getMethod());
                requestLogModel.setClientIp(RequestUtils.getIP(request));
            } else {
                requestLogModel.setRequestId(IdGenUtils.nextIdStr());
                requestLogModel.setRequestUrl(StringPool.EMPTY);
                requestLogModel.setRequestMethod(StringPool.EMPTY);
                requestLogModel.setClientIp(StringPool.EMPTY);
            }
            setCommonField(requestLogModel, joinPoint, point, ret, ex);
            return requestLogModel;
        } else {
            MethodLogModel logModel = new MethodLogModel();
            setCommonField(logModel, joinPoint, point, ret, ex);
            return logModel;
        }
    }

    private void setCommonField(MethodLogModel logModel, JoinPoint joinPoint, MethodLogPoint point,
        Object ret, Throwable ex) {

        boolean hasEx = ex != null;
        boolean ignoreRet = point.ignoreResult();

        logModel.setAction(point.action());
        logModel
            .setParams(handleParam(joinPoint.getArgs(), Arrays.asList(point.sensitiveParams())));
        logModel.setResult(hasEx ? ex.getMessage() : (ignoreRet ? HIDDEN : ret));
        logModel.setRetType(hasEx ? RetTypeEnum.EX : RetTypeEnum.OK);
        logModel.setStartTime(DateUtils.toLocalDateTime(entryTime.get()));
        logModel.setDuration(System.currentTimeMillis() - entryTime.get());
    }

    /**
     * 处理方法入参
     *
     * @param args 原参数
     * @param sensitive 敏感参数列表
     */
    private static JsonNode handleParam(Object[] args, List<String> sensitive) {

        Map<String, Object> map = Maps.newLinkedHashMap();
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                map.put("arg" + (i + 1), args[i]);
            }
        }
        JsonNode source = JsonUtils.obj2JsonNode(map);

        if (!CollectionUtils.isEmpty(sensitive)) {
            handleSensitiveParams(source, sensitive);
        }
        return source;
    }

    /**
     * 参数递归脱敏
     *
     * @param node Json节点
     * @param sensitive 敏感参数列表
     */
    private static void handleSensitiveParams(JsonNode node, List<String> sensitive) {

        if (JsonNodeType.OBJECT.equals(node.getNodeType())) {
            Iterator<Entry<String, JsonNode>> iterator = node.fields();
            while (iterator.hasNext()) {
                Entry<String, JsonNode> entry = iterator.next();
                if (sensitive.contains(entry.getKey())) {
                    entry.setValue(new TextNode(HIDDEN));
                }
            }
        }
        node.forEach(item -> handleSensitiveParams(item, sensitive));
    }
}
