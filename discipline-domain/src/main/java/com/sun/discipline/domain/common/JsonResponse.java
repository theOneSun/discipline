package com.sun.discipline.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunjian.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponse
{
    private boolean success;
    private String message;

    public static JsonResponse successJsonResponse(String message)
    {
        return new JsonResponse(true,message);
    }
    public static JsonResponse failureJsonResponse(String message)
    {
        return new JsonResponse(false,message);
    }
}
