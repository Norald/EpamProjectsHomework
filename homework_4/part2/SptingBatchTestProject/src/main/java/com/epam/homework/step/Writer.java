package com.epam.homework.step;

import java.util.List;

import com.epam.homework.model.User;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

/**
 * Empty writer
 */

public class Writer implements ItemWriter<User> {

    public void write(List<? extends User> messages) throws Exception {
    }

}
