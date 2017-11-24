package com.gerrymander.configuration;

import org.springframework.batch.item.ItemProcessor;

import com.gerrymander.beans.Votes;

public class VotesItemProcessor implements ItemProcessor<Votes, Votes> {

    @Override
    public Votes process(final Votes votes) throws Exception {
        return votes;
    }

}
