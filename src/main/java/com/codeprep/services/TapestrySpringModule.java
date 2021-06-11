package com.codeprep.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

public class TapestrySpringModule
{
    @ApplicationDefaults
    @Contribute(SymbolProvider.class)
    public static void configureTapestryHotelBooking(MappedConfiguration<String, String> configuration) 
    {
        configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0.0-SNAPSHOT");
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
    }
}
