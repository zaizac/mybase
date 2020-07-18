package com.notify.sdk.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * The Interface Attachment.
 *
 * @author mary.jane
 * @since Jan 16, 2019
 */
// @JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include =
// JsonTypeInfo.As.PROPERTY, property = "@class")
// @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include =
// JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
@JsonSubTypes({ @Type(value = com.notify.sdk.model.ByteAttachment.class),
		@Type(value = com.notify.sdk.model.ReportAttachment.class),
		@Type(value = com.notify.sdk.model.DmAttachment.class) })
public interface Attachment {

}
