package com.sun.discipline.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sunjian.
 */
@Data
@ConfigurationProperties(prefix = "discipline.security")
public class SecurityProperties
{
    private BrowserProperties browserProperties = new BrowserProperties();
}
