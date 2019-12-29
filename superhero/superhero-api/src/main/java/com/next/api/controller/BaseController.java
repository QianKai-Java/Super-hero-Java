package com.next.api.controller;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
public class BaseController {

    public Integer[] getGuessULikeArray(Integer counts) {

        Integer[] guessIndexArray = new Integer[5];

        for (int i = 0; i < guessIndexArray.length; i++) {

            int index = (int) (Math.random() * counts);

            if (ArrayUtils.contains(guessIndexArray, index)) {
                i--;
                continue;
            }
            guessIndexArray[i] = index;
        }

        return guessIndexArray;
    }
}