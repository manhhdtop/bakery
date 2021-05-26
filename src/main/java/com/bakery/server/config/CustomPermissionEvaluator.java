package com.bakery.server.config;

import com.bakery.server.entity.ActionEntity;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, targetDomainObject.toString().toUpperCase(), permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, targetType.toUpperCase(), permission.toString().toUpperCase());
    }

    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        List<ActionEntity> actions = principal.getRoles().stream()
                .map(e -> new ArrayList<>(e.getActions())).flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        Map<String, ActionEntity> actionMap = actions.stream().collect(Collectors.toMap(ActionEntity::getCode, Function.identity()));

        String targetAction = targetType + "." + permission;
        return actionMap.containsKey(targetAction);
    }
}
