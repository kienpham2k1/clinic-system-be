package org.clinic.common_security.security.config;

import org.clinic.common_security.security.dto.RequestPermissions;
import org.clinic.common_security.security.enums.Permission;
import org.clinic.common_security.security.enums.Role;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class RulesConfig {
    private static final PathPatternParser patternParser = new PathPatternParser();
    public static final Set<PathPattern> WHITE_LIST = Set.of(
            patternParser.parse("/api/v1/auth/**")
    );


    public static final Map<PathPattern, RequestPermissions> RULES =
//            patternParser.parse("/api/v1/patients/**"),
//            new RequestPermissions(
//                    Set.of(Role.PATIENT, Role.DOCTOR),
//                    Map.of(HttpMethod.GET, Set.of(Permission.ADMIN_READ))
            Collections.emptyMap()

     ;
}
