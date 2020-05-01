package org.junit.jupiter.executioncondition;

import org.jobrunr.utils.StringUtils;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.AnnotatedElement;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

public class EnabledIfEnvVariableExistsExecutionCondition implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        Optional<AnnotatedElement> element = context.getElement();
        Optional<EnabledIfEnvVariableExists> enabledIfOptional = findAnnotation(element, EnabledIfEnvVariableExists.class);
        if (enabledIfOptional.isPresent()) {
            final EnabledIfEnvVariableExists envVariableExists = enabledIfOptional.get();
            final String envVariableName = envVariableExists.value();
            if (StringUtils.isNotNullOrEmpty(envVariableName)) {
                return ConditionEvaluationResult.enabled(String.format("Test enabled as env variable %s exists", envVariableName));
            }
            final String reason = String.format("Test disabled as env variable %s does not exist", envVariableName);
            System.err.println(reason);
            return ConditionEvaluationResult.disabled(reason);

        }
        return ConditionEvaluationResult.enabled("@RunTestBetween is not present.");
    }
}
