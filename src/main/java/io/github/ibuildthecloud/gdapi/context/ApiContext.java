package io.github.ibuildthecloud.gdapi.context;

import io.github.ibuildthecloud.gdapi.factory.SchemaFactory;
import io.github.ibuildthecloud.gdapi.id.IdFormatter;
import io.github.ibuildthecloud.gdapi.id.IdentityFormatter;
import io.github.ibuildthecloud.gdapi.request.ApiRequest;
import io.github.ibuildthecloud.gdapi.url.DefaultUrlBuilder;
import io.github.ibuildthecloud.gdapi.url.NullUrlBuilder;
import io.github.ibuildthecloud.url.UrlBuilder;

public class ApiContext {

    private static final ThreadLocal<ApiContext> TL = new ThreadLocal<ApiContext>();

    ApiRequest apiRequest;
    IdFormatter idFormatter = new IdentityFormatter();
    SchemaFactory schemaFactory;
    Object policy;

    protected ApiContext() {
        super();
    }

    public static ApiContext getContext() {
        return TL.get();
    }

    public static ApiContext newContext() {
        ApiContext context = new ApiContext();
        TL.set(context);
        return context;
    }

    public static void remove() {
        TL.remove();
    }

    public static UrlBuilder getUrlBuilder() {
        ApiContext context = ApiContext.getContext();
        if ( context != null ) {
            UrlBuilder writer = context.getApiRequest().getUrlBuilder();
            if ( writer == null ) {
                writer = new DefaultUrlBuilder(context.getApiRequest(), context.getSchemaFactory());
                context.getApiRequest().setUrlBuilder(writer);
            }
            return writer;
        }
        return new NullUrlBuilder();
    }

    public ApiRequest getApiRequest() {
        return apiRequest;
    }

    public void setApiRequest(ApiRequest apiRequest) {
        this.apiRequest = apiRequest;
    }

    public SchemaFactory getSchemaFactory() {
        return schemaFactory;
    }

    public void setSchemaFactory(SchemaFactory schemaFactory) {
        this.schemaFactory = schemaFactory;
    }

    public Object getPolicy() {
        return policy;
    }

    public void setPolicy(Object policy) {
        this.policy = policy;
    }

    public IdFormatter getIdFormatter() {
        return idFormatter;
    }

    public void setIdFormatter(IdFormatter idFormatter) {
        this.idFormatter = idFormatter;
    }

}