/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddebbie.model.output;

import java.io.UnsupportedEncodingException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author Phinny
 */
@XmlAccessorType(value = XmlAccessType.NONE)
@XmlRootElement(name = "url")
public class XmlUrl {
    public enum Priority {
        HIGH("1.0"), MEDIUM("0.5");

        private String value;

        Priority(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @XmlElement
    private String loc;

    @XmlElement
    private String lastmod = new DateTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd"));

    @XmlElement
    private String changefreq = "weekly";

    @XmlElement
    private String priority;

    public XmlUrl() {
    }

    public XmlUrl(String loc, Priority priority) {
//        this.loc =  URLEncoder.encode(loc, "UTF-8");
        this.loc=loc;
        this.priority = priority.getValue();
    }

    public String getLoc() {
        return loc;
    }

    public String getPriority() {
        return priority;
    }

    public String getChangefreq() {
        return changefreq;
    }

    public String getLastmod() {
        return lastmod;
    }
}
