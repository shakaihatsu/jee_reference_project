package jee.reference;

import jee.reference.meta.ExternalDependency;
import jee.reference.meta.JBOSS_AS7;
import jee.reference.meta.POI;
import jee.reference.meta.POITag;

public final class ApplicationPoi {
    private ApplicationPoi() {
    }

    @JBOSS_AS7
    @POI(tags = { POITag.JSON }, value = "Had to add jackson-jaxrs to pom to be able to use json (de)serializers.")
    public static final String JSON_SERIALIZER_DEPENDENCY = "jackson-mapper-asl";

    @ExternalDependency
    @JBOSS_AS7
    @POI(tags = { POITag.DATASOURCE },
        value = "JbossAS7 will look for datasources in *-ds.xml files on the classpath. This is not recommended for production environments though.")
    public static final String DATASOURCE = "admin-ds.xml";
}
