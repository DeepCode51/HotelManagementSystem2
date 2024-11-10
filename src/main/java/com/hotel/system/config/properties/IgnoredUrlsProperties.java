package com.hotel.system.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyanzhao
 * @date 2022/04/22
 */
@Configuration
@ConfigurationProperties(prefix = "ignored")
public class IgnoredUrlsProperties {

    private List<String> urls = new ArrayList<>();

    public IgnoredUrlsProperties() {
    }

    public List<String> getUrls() {
        return this.urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof IgnoredUrlsProperties)) return false;
        final IgnoredUrlsProperties other = (IgnoredUrlsProperties) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$urls = this.getUrls();
        final Object other$urls = other.getUrls();
        if (this$urls == null ? other$urls != null : !this$urls.equals(other$urls)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof IgnoredUrlsProperties;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $urls = this.getUrls();
        result = result * PRIME + ($urls == null ? 43 : $urls.hashCode());
        return result;
    }

    public String toString() {
        return "IgnoredUrlsProperties(urls=" + this.getUrls() + ")";
    }
}
