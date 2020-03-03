package com.lace.util;

import java.util.Objects;

/**
 *
 * @author hackdaemon
 */
public class ResourceErrorMessage {

    private StringBuilder errorMessageBuffer;
    private String errorMessage;
    private static volatile ResourceErrorMessage instance;

    /**
     * Factory method for ResourceErrorMessage
     *
     * @return instance
     */
    public static ResourceErrorMessage getInstance() {
        ResourceErrorMessage localInstance = ResourceErrorMessage.instance;
        if (Objects.isNull(localInstance)) {
            synchronized (ResourceErrorMessage.class) {
                localInstance = ResourceErrorMessage.instance;
                if (Objects.isNull(localInstance)) {
                    localInstance = new ResourceErrorMessage();
                    ResourceErrorMessage.instance = localInstance;
                }
            }
        }
        return ResourceErrorMessage.instance;
    }

    /**
     *
     * @param objectId
     * @return errorMessage
     */
    public String getResourceNotFound(Long objectId) {
        errorMessageBuffer = new StringBuilder();
        errorMessage = errorMessageBuffer
                        .append("Resource not found on ")
                        .append(objectId)
                        .toString();
        return errorMessage;
    }

    /**
     *
     * @param objectId
     * @return errorMessage
     */
    public String getResourceNotFound(String objectId) {
        errorMessageBuffer = new StringBuilder();
        errorMessage = errorMessageBuffer
                        .append("Resource not found on ")
                        .append(objectId)
                        .toString();
        return errorMessage;
    }

    /**
     *
     * @param objectDescriptor
     * @return
     */
    public String getUnableToCreateResource(String objectDescriptor) {
        String errorMessageDescriptor = StringUtil.capitalizeString(objectDescriptor);
        String msgText = "Cannot create resource on %s, Resource already exists";
        errorMessage = String.format(msgText, errorMessageDescriptor);
        return errorMessage;
    }
}
