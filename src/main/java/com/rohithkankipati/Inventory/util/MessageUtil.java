package com.rohithkankipati.Inventory.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {
	
	@Autowired
	private static MessageSource messageSource;
    
    public MessageUtil(MessageSource source) {
        MessageUtil.messageSource = source;
    }

    public static String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

}
