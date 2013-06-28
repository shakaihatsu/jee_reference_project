package jee.reference;

import jee.reference.meta.TODO;
import jee.reference.meta.TODOTag;

public final class ApplicationTodo {
    private ApplicationTodo() {
    }

    @TODO(tags = { TODOTag.MISSING_FEATURE, TODOTag.INTERCEPTORS_1_1 }, value = "Add example for AROUND_CONSTRUCT.")
    public static final String AROUND_CONSTRUCT = "Interceptors 1.1";

    @TODO(tags = { TODOTag.MISSING_FEATURE, TODOTag.INTERCEPTORS_1_1 },
        value = "Add example for InvocationContext in @PostConstruct or @PreDestroy using the common interceptor interface.")
    public static final String INVOCATION_CONTEXT_IN_LIFECYCLE_METHODS = "Interceptors 1.1";

    @TODO(tags = { TODOTag.MISSING_FEATURE, TODOTag.JPA_2_1 }, value = "Add example for @Converter. Probably Person.deleted is a good candidate.")
    public static final String JPA_CONVERTER = "JPA 2.1";

    @TODO(tags = { TODOTag.MISSING_FEATURE, TODOTag.JPA_2_1 }, value = "Add example for unsynchronized persistence context.")
    public static final String UNSYNCHRONIZED_PERSISTENCE_CONTEXT = "JPA 2.1";

    @TODO(tags = { TODOTag.MISSING_FEATURE, TODOTag.JPA_2_1 }, value = "Add example for schema generation.")
    public static final String SCHEMA_GENERATION = "JPA 2.1";
}
