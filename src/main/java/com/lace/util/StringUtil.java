package com.lace.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author hackdaemon
 */
public class StringUtil {

  public static final String capitalizeString(String word) {
    String firstLetter = null;
    String substring = null;
    if (!word.equals("")) {
      firstLetter = String.valueOf(word.charAt(0));
      firstLetter = firstLetter.toUpperCase();
      substring = word.substring(1).toLowerCase();
      word = firstLetter.concat(substring);
    }
    return word;
  }

  public static final String titleCaseString(String sentence) {
    List<String> formattedTokens;
    String[] sentenceTokens;
    List<String> ignoreWordsList;
    int index = 0;
    int previousIndex = 0;
    String[] ignoreList = {
      "to", "in", "this", "of", "the", "a", "at",
      "and", "for", "with", "by", "or", "not", "is"
    };
    ignoreWordsList = Arrays.asList(ignoreList);
    sentenceTokens = sentence.split(" ");
    formattedTokens = new ArrayList<>();
    for (index = 0; index <= sentenceTokens.length; index++) {
      if (!ignoreWordsList.contains(sentenceTokens[index].toLowerCase())) {
        formattedTokens.add(capitalizeString(sentenceTokens[index]));
      } else {
        if (index == 0) {
          formattedTokens.add(capitalizeString(sentenceTokens[index]));
        } else {
          previousIndex = index - 1;
          if (sentenceTokens[previousIndex].contains(":")) {
            formattedTokens.add(capitalizeString(sentenceTokens[index]));
          } else {
            formattedTokens.add(sentenceTokens[index].toLowerCase());
          }
        }
      }
    }
    return null;
  }
}
