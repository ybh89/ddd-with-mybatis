package com.demo.dddwithmybatis.v3.domain.model;

import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public class URL
{
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$");

    private final String url;

    private URL()
    {
        this.url = null;
    }

    public URL(String url)
    {
        if (Objects.isNull(url) || url.trim().isEmpty())
        {
            this.url = null;
            return;
        }
        validate(url);
        this.url = url;
    }

    private void validate(String url)
    {
        if (!URL_PATTERN.matcher(url).matches()) {
            throw new IllegalArgumentException("URL 포맷과 맞지 않습니다.");
        }
    }

    public String value()
    {
        return url;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URL url1 = (URL) o;
        return Objects.equals(url, url1.url);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(url);
    }
}
