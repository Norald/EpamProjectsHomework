package com.homework.epam.service.impl;

import com.homework.epam.annotation.Timed;
import com.homework.epam.aspect.Logging;
import com.homework.epam.service.Notebook;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RandomNotebook implements Notebook {
    private static final Logger LOG = LogManager.getLogger(Logging.class.getName());

    private String message = "Hello";
    //random amount of repeating. From 1 to 9;
    private int repeat = (int) (1 + (Math.random() * 9));

    public RandomNotebook() {

    }

    @Timed
    public void notateSomething() {
        for (int i = 0; i < repeat; i++) {
            LOG.info("Note =  " + message);
        }
    }
}
