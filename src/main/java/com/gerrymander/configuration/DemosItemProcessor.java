package com.gerrymander.configuration;

import org.springframework.batch.item.ItemProcessor;

import com.gerrymander.beans.*;

public class DemosItemProcessor implements ItemProcessor<Demographic, Demographic> {

    @Override
    public Demographic process(final Demographic demos) throws Exception {
        return demos;
    }
}

