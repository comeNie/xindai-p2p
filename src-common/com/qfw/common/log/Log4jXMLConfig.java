package com.qfw.common.log;
import java.net.URL;

import org.apache.log4j.xml.DOMConfigurator;

/**
 * 单实例 读取log4j配置文件，初始化log4j
 * 
 */
public class Log4jXMLConfig
{
    private static Log4jXMLConfig instance;

    public static synchronized Log4jXMLConfig initia()
    {
        if (instance == null)
        {
            return new Log4jXMLConfig();
        }
        return instance;
    }

    private Log4jXMLConfig()
    {
        final URL url = Log4jXMLConfig.class.getResource("log4j.xml");
        DOMConfigurator.configure(url);
    }
}
